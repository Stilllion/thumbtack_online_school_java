package net.thumbtack.school.hiring.service;

import com.google.gson.Gson;
import net.thumbtack.school.hiring.daoimpl.EmployeeDaoImpl;
import net.thumbtack.school.hiring.exception.ServerException;
import net.thumbtack.school.hiring.request.employee.*;
import net.thumbtack.school.hiring.request.user.RemoveAccountDtoRequest;
import net.thumbtack.school.hiring.response.employee.*;
import net.thumbtack.school.hiring.response.user.RemoveAccountDtoResponse;
import net.thumbtack.school.hiring.server.*;

import java.util.ArrayList;
import java.util.List;

public class EmployeeService
{
    EmployeeDaoImpl employeeDaoObj;
    Gson gson = new Gson();

    public EmployeeService()
    {
        employeeDaoObj = new EmployeeDaoImpl();
    }

    public String getRegisteredEmployees()
    {
        return gson.toJson(employeeDaoObj.getRegisteredEmployees());
    }

    public  String registerEmployee(String registerRequestJson)
    {
        Employee registeredEmployee;

        RegisterEmployeeDtoRequest regEmployeeDto = gson.fromJson(registerRequestJson, RegisterEmployeeDtoRequest.class);
        RegisterEmployeeDtoResponse response = new RegisterEmployeeDtoResponse();

        try{
            regEmployeeDto.validate();

            registeredEmployee = new Employee(regEmployeeDto.getEmail(), regEmployeeDto.getFirstName(), regEmployeeDto.getLastName(),
                                          regEmployeeDto.getLogin(), regEmployeeDto.getPassword());

            registeredEmployee.setToken(response.getToken());
            registeredEmployee.setOnline(true);

            employeeDaoObj.InsertEmployee(registeredEmployee);
            response.setRegisteredEmployee(employeeDaoObj.getEmployee(registeredEmployee.getToken()));
        } catch(ServerException e) {
            response.setError(e.getErrorCode().getErrorString());
            return gson.toJson(response);
        }

        return gson.toJson(response);
    }

    public String AddSkill(String addSkillRequestJson)
    {
        AddSkillDtoRequest req = gson.fromJson(addSkillRequestJson,  AddSkillDtoRequest.class);
        AddSkillDtoResponse response = new AddSkillDtoResponse();

        try{
            req.validate();
        } catch (ServerException e){
            response.setError(e.getErrorCode().getErrorString());
            return gson.toJson(response);
        }

        Skill s = new Skill(req.getName(), req.getLevel());

        for(Employee e : employeeDaoObj.getRegisteredEmployees()){
            if(e.getToken().equals(req.getToken())){
                employeeDaoObj.addSkill(e, s);
                response.setEmployeeSkills(employeeDaoObj.getSkills(e));
            }
        }

        return gson.toJson(response);
    }

    private boolean hasRequiredSkills(List<Skill> skills, List<Skill> requirements)
    {
        int numberOfRequrementsCovered = 0;

        for (Skill requiredSkill : requirements) {
            for (Skill s : skills) {
                if(s.getName().equals(requiredSkill.getName())){
                    if (s.getLevel() >= requiredSkill.getLevel()) {
                        numberOfRequrementsCovered += 1;
                    }
                }
            }
        }

        return numberOfRequrementsCovered == requirements.size();
    }

    // В любой момент потенциальный работник может получить
    // список всех вакансий работодателей, для которых его набор умений
    // соответствует требованиям работодателя на уровне не ниже уровня, указанного в требовании
    public String getVacanciesRequiredLevel(String requestJsonString)
    {
        GetVacanciesRequiredLevelDtoRequest request = gson.fromJson(requestJsonString, GetVacanciesRequiredLevelDtoRequest.class);
        GetVacanciesRequiredLevelDtoResponse response = new GetVacanciesRequiredLevelDtoResponse();

        try{
            request.validate();

            Employee requestSender = employeeDaoObj.getEmployee(request.getToken());
            List<Skill> skills = employeeDaoObj.getSkills(requestSender);

            for (Employer e : employeeDaoObj.getRegisteredEmployers()) {
                List<Vacancy> vacancies = employeeDaoObj.getVacancies(e);
                for(Vacancy v : vacancies){
                    if( hasRequiredSkills(skills, v.getRequirements())){
                        response.getVacancies().add(v);
                    }
                }
            }
        } catch (ServerException e){
            response.setError(e.getErrorCode().getErrorString());
            gson.toJson(response);
        }

        return gson.toJson(response);
    }

    private boolean hasMandatorySkills(List<Skill> skills, List<Skill> requirements)
    {
        int numberOfReruirmentsCovered = 0;
        int numberOfMandatoryRequirments = 0;
        // Determine number of mandatory requirments
        for (int i = 0; i < requirements.size(); ++i) {
            if(requirements.get(i).isMandatory()){
                numberOfMandatoryRequirments += 1;
            }
        }

        for (Skill requiredSkill : requirements) {
            for (Skill s : skills) {
                if(s.getName().equals(requiredSkill.getName()) && requiredSkill.isMandatory()){
                    if (s.getLevel() >= requiredSkill.getLevel()) {
                        numberOfReruirmentsCovered += 1;
                    }
                }
            }
        }

        return numberOfReruirmentsCovered == numberOfMandatoryRequirments;
    }

    // список всех вакансий работодателей, для которых его набор умений соответствует обязательным требованиям
    // работодателя на уровне не ниже уровня, указанного в требовании
    public String getVacanciesMandatorySkills(String requestJson)
    {
        GetVacanciesMandatorySkillsDtoRequest request = gson.fromJson(requestJson, GetVacanciesMandatorySkillsDtoRequest.class);
        GetVacanciesMandatorySkillsDtoResponse response = new GetVacanciesMandatorySkillsDtoResponse();

        try {
            request.validate();

            Employee requestSender = employeeDaoObj.getEmployee(request.getToken());
            List<Skill> skills = employeeDaoObj.getSkills(requestSender);

            for (Employer e : employeeDaoObj.getRegisteredEmployers()) {
                List<Vacancy> vacancies = employeeDaoObj.getVacancies(e);
                for (Vacancy v : vacancies) {
                    if (hasMandatorySkills(skills, v.getRequirements())) {
                        response.getVacancies().add(v);
                    }
                }
            }
        } catch (ServerException exception) {
            response.setError(exception.getErrorCode().getErrorString());
            return gson.toJson(response);
        }

        return gson.toJson(response);
    }

    // список всех вакансий работодателей,
    // для которых его набор умений соответствует требованиям работодателя на любом уровне
    private boolean hasSkillsOnAnyLevel(List<Skill> skills, List<Skill> requirements)
    {
        int numberOfRequrementsCovered = 0;

        for (Skill requiredSkill : requirements) {
            for (Skill s : skills) {
                if(s.getName().equals(requiredSkill.getName())){
                    numberOfRequrementsCovered += 1;
                }
            }
        }

        return numberOfRequrementsCovered == requirements.size();
    }

    // список всех потенциальных работников, имеющих все необходимые для этой вакансии умения на любом уровне
    public String getVacanciesAnyLevel(String requestJsonString)
    {
        GetVacanciesAnyLevelDtoRequest request = gson.fromJson(requestJsonString, GetVacanciesAnyLevelDtoRequest.class);
        GetVacanciesAnyLevelDtoResponse response = new GetVacanciesAnyLevelDtoResponse();

        try {
            request.validate();

            Employee requestSender = employeeDaoObj.getEmployee(request.getToken());
            List<Skill> skills = employeeDaoObj.getSkills(requestSender);

            for (Employer e : employeeDaoObj.getRegisteredEmployers()) {

                List<Vacancy> vacancies = employeeDaoObj.getVacancies(e);

                for (Vacancy v : vacancies) {
                    if (hasSkillsOnAnyLevel(skills, v.getRequirements())) {
                        response.getVacancies().add(v);
                    }
                }
            }
        } catch (ServerException exception){
            response.setError(exception.getErrorCode().getErrorString());
            return gson.toJson(response);
        }

        return gson.toJson(response);
    }

    // список всех вакансий работодателей, для которых работник имеет хотя бы одно умение
    // из списка в требовании работодателя на уровне не ниже уровня, указанного в требовании.
    // В этом случае список выдается отсортированным по числу найденных умений,
    // то есть в начале списка приводятся те вакансии работодателей, для которых работник имеет большее число умений.
    private boolean hasAnySkill(List<Skill> skills, List<Skill> requirements)
    {
        for (Skill requiredSkill : requirements) {
            for (Skill s : skills) {
                if(s.getName().equals(requiredSkill.getName())){
                    if (s.getLevel() < requiredSkill.getLevel()) {
                        return false;
                    }
                }
            }
        }
        return true;
    }
    // список всех потенциальных работников,
    // имеющих хотя бы одно  необходимое для этой вакансии умение на уровне не ниже уровня, указанного в требовании
    public String getVacanciesAnySkill(String requestJsonString) {
        List<Vacancy> retVacancies = new ArrayList<>();
        List<Skill> skills;

        GetVacanciesAnyLevelDtoRequest request = gson.fromJson(requestJsonString, GetVacanciesAnyLevelDtoRequest.class);
        GetVacanciesAnyLevelDtoResponse response = new  GetVacanciesAnyLevelDtoResponse();

        try {
            request.validate();
            Employee requestSender = employeeDaoObj.getEmployee(request.getToken());
            skills = employeeDaoObj.getSkills(requestSender);

            for (Employer e : employeeDaoObj.getRegisteredEmployers()) {
                List<Vacancy> vacancies = employeeDaoObj.getVacancies(e);
                for (Vacancy v : vacancies) {
                    if (hasSkillsOnAnyLevel(skills, v.getRequirements())) {
                        response.getVacancies().add(v);
                    }
                }
            }
        } catch (ServerException exception){
            response.setError(exception.getErrorCode().getErrorString());
            return gson.toJson(response);
        }

        return gson.toJson(response);
    }

   /* public String removeAccount(String jsonRequest)
    {
        RemoveAccountDtoRequest req = gson.fromJson(jsonRequest,  RemoveAccountDtoRequest.class);

        try{
            req.validate();
        } catch (ServerException e){
            return gson.toJson(e.getErrorCode().getErrorString());
        }

        Employee accountToBeRemoved = null;

        for(Employee e : employeeDaoObj.getRegisteredEmployees()) {
            if (e.getToken().equals(req.getToken())) {
                accountToBeRemoved = e;
            }
        }

        if(accountToBeRemoved == null){
            return gson.toJson("error: Account not found");
        }

        try {
            employeeDaoObj.removeAccount(accountToBeRemoved);
        } catch (ServerException e){
            return gson.toJson(e.getErrorCode().getErrorString());
        }

        return gson.toJson(new RemoveAccountDtoResponse(accountToBeRemoved));
    }*/
}