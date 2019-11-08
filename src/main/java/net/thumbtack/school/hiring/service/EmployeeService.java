package net.thumbtack.school.hiring.service;

import com.google.gson.Gson;
import net.thumbtack.school.hiring.daoimpl.EmployeeDaoImpl;
import net.thumbtack.school.hiring.exception.ServerErrorCode;
import net.thumbtack.school.hiring.exception.ServerException;
import net.thumbtack.school.hiring.request.employee.*;
import net.thumbtack.school.hiring.request.user.RemoveAccountDtoRequest;
import net.thumbtack.school.hiring.response.GetVacanciesDtoResponse;
import net.thumbtack.school.hiring.response.employee.*;
import net.thumbtack.school.hiring.response.user.RemoveAccountDtoResponse;
import net.thumbtack.school.hiring.server.Employee;
import net.thumbtack.school.hiring.server.Employer;
import net.thumbtack.school.hiring.server.Skill;
import net.thumbtack.school.hiring.server.Vacancy;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

            Skill s = new Skill(req.getName(), req.getLevel());

            for(Employee e : employeeDaoObj.getRegisteredEmployees()){
                if(e.getToken().equals(req.getToken())){
                    employeeDaoObj.addSkill(e, s);
                    response.setEmployeeSkills(employeeDaoObj.getSkills(e));
                    return gson.toJson(response);
                }
            }

        } catch (ServerException e){
            response.setError(e.getErrorCode().getErrorString());
            return gson.toJson(response);
        }

        response.setError(ServerErrorCode.USER_NOT_FOUND.getErrorString());
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
    public String getVacanciesRequiredLevel(String requestJson)
    {
        GetVacanciesDtoRequest request = gson.fromJson(requestJson, GetVacanciesDtoRequest.class);
        GetVacanciesDtoResponse response = new GetVacanciesDtoResponse();

        try{
            request.validate();

            Employee requestSender = employeeDaoObj.getEmployee(request.getToken());
            List<Skill> skills = employeeDaoObj.getSkills(requestSender);

            for (Employer e : employeeDaoObj.getRegisteredEmployers()) {
                List<Vacancy> vacancies = employeeDaoObj.getVacancies(e);
                for(Vacancy v : vacancies){
                    if( hasRequiredSkills(skills, v.getRequirements()) && v.isActive()){
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
        GetVacanciesDtoRequest request = gson.fromJson(requestJson, GetVacanciesDtoRequest.class);
        GetVacanciesDtoResponse response = new GetVacanciesDtoResponse();

        try {
            request.validate();

            Employee requestSender = employeeDaoObj.getEmployee(request.getToken());
            List<Skill> skills = employeeDaoObj.getSkills(requestSender);

            for (Employer e : employeeDaoObj.getRegisteredEmployers()) {
                List<Vacancy> vacancies = employeeDaoObj.getVacancies(e);
                for (Vacancy v : vacancies) {
                    if (hasMandatorySkills(skills, v.getRequirements()) && v.isActive()) {
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
    public String getVacanciesAnyLevel(String requestJson)
    {
        GetVacanciesDtoRequest request = gson.fromJson(requestJson, GetVacanciesDtoRequest.class);
        GetVacanciesDtoResponse response = new GetVacanciesDtoResponse();

        try {
            request.validate();

            Employee requestSender = employeeDaoObj.getEmployee(request.getToken());
            List<Skill> skills = employeeDaoObj.getSkills(requestSender);

            for (Employer e : employeeDaoObj.getRegisteredEmployers()) {

                List<Vacancy> vacancies = employeeDaoObj.getVacancies(e);

                for (Vacancy v : vacancies) {
                    if (hasSkillsOnAnyLevel(skills, v.getRequirements()) && v.isActive()) {
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
    private int hasAnySkill(List<Skill> skills, List<Skill> requirements)
    {
        int numberOfRequrementsCovered = 0;

        for (Skill requiredSkill : requirements) {
            for (Skill s : skills) {
                if(s.getName().equals(requiredSkill.getName()) && s.getLevel() >= requiredSkill.getLevel()){
                    numberOfRequrementsCovered += 1;
                }
            }

        }

        return numberOfRequrementsCovered;
    }

    // TODO IMPLEMENT THIS METHOD
    public String getVacanciesAnySkill(String requestJson)
    {
        // Maps Vacancies to the number of covered requirements
        Map<Vacancy, Integer> vacancyToCoveredRequirements = new HashMap<>();
        List<Skill> skills;
        int coveredRequirements = 0;

        GetVacanciesDtoRequest request = gson.fromJson(requestJson, GetVacanciesDtoRequest.class);
        GetVacanciesDtoResponse response = new GetVacanciesDtoResponse();

        try {
            request.validate();
            Employee requestSender = employeeDaoObj.getEmployee(request.getToken());
            skills = employeeDaoObj.getSkills(requestSender);

            for (Employer e : employeeDaoObj.getRegisteredEmployers()) {
                List<Vacancy> vacancies = employeeDaoObj.getVacancies(e);
                for (Vacancy v : vacancies) {
                    coveredRequirements = hasAnySkill(skills, v.getRequirements());
                    if (coveredRequirements > 0 && v.isActive())
                    {
                        vacancyToCoveredRequirements.put(v, coveredRequirements);
                    }
                }
            }

            int maxNumber = -9999;
            Vacancy maxRequirementsCovered = null;
            for (int i = 0; i < vacancyToCoveredRequirements.size(); ++i) {
                for (Vacancy v : vacancyToCoveredRequirements.keySet()) {
                    if (vacancyToCoveredRequirements.get(v) > maxNumber){
                        maxNumber = vacancyToCoveredRequirements.get(v);
                        maxRequirementsCovered = v;
                    }
                }

                response.getVacancies().add(maxRequirementsCovered);
                vacancyToCoveredRequirements.remove(maxRequirementsCovered);
                maxNumber = -9999;
            }

        } catch (ServerException exception){
            response.setError(exception.getErrorCode().getErrorString());
            return gson.toJson(response);
        }

        return gson.toJson(response);
    }

    public String removeSkill(String jsonRequest)
	{
		RemoveSkillDtoRequest request = gson.fromJson(jsonRequest, RemoveSkillDtoRequest.class);
        UpdateEmployeeProfileDtoResponse response = new UpdateEmployeeProfileDtoResponse();

        try{
            request.validate();

			for(Employee e : employeeDaoObj.getRegisteredEmployees()){
				if(e.getToken().equals(request.getToken())){
					employeeDaoObj.removeSkill(e, request.getSkillName());
					return gson.toJson(response);
				}
			}
        } catch(ServerException e) {
            response.setError(e.getErrorCode().getErrorString());
            return gson.toJson(response);
        }

		response.setError(ServerErrorCode.TOKEN_NOT_FOUND.getErrorString());
        return gson.toJson(response);
	}

    public String changeSkillLevel(String jsonRequest)
    {
		ChangeSkillLevelDtoRequest request = gson.fromJson(jsonRequest, ChangeSkillLevelDtoRequest.class);
        UpdateEmployeeProfileDtoResponse response = new UpdateEmployeeProfileDtoResponse();

        try{
            request.validate();

			for(Employee e : employeeDaoObj.getRegisteredEmployees()){
				if(e.getToken().equals(request.getToken())){
					int newLevel = employeeDaoObj.setSkillLevel(e, request.getSkillName(), request.getSkillLevel());
					response.setResult(request.getSkillName() + " level is now set to " + newLevel);
					return gson.toJson(response);
				}
			}
        } catch(ServerException e) {
            response.setError(e.getErrorCode().getErrorString());
            return gson.toJson(response);
        }

		response.setError(ServerErrorCode.TOKEN_NOT_FOUND.getErrorString());
        return gson.toJson(response);
    }

    public String setIsActive(String jsonRequest)
    {
        SetProfileIsActiveDtoRequest request = gson.fromJson(jsonRequest, SetProfileIsActiveDtoRequest.class);
        UpdateEmployeeProfileDtoResponse response = new UpdateEmployeeProfileDtoResponse();

        try{
            request.validate();

			for(Employee e : employeeDaoObj.getRegisteredEmployees()){
				if(e.getToken().equals(request.getToken())){
					if(e.setActive(request.isActive())){
                        response.setResult("Your profile is now active");
                    } else {
					    response.setResult("Your profile is now inactive");
                    }
					return gson.toJson(response);
				}
			}
        } catch(ServerException e) {
            response.setError(e.getErrorCode().getErrorString());
            return gson.toJson(response);
        }

		response.setError(ServerErrorCode.TOKEN_NOT_FOUND.getErrorString());
        return gson.toJson(response);
    }

    public String removeAccount(String jsonRequest)
    {
        RemoveAccountDtoRequest request = gson.fromJson(jsonRequest,  RemoveAccountDtoRequest.class);
        RemoveAccountDtoResponse response = new RemoveAccountDtoResponse();

        try{
            request.validate();

            for(Employee e : employeeDaoObj.getRegisteredEmployees()) {
                if (e.getToken().equals(request.getToken())) {
                    employeeDaoObj.removeAccount(e);
                    response.setResult("Your account has been removed");
                }
            }
        } catch (ServerException e){
            response.setError(e.getErrorCode().getErrorString());
            return gson.toJson(response);
        }

        return gson.toJson(response);
    }
}