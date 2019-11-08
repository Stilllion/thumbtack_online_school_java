package net.thumbtack.school.hiring.service;

import com.google.gson.Gson;
import net.thumbtack.school.hiring.daoimpl.EmployerDaoImpl;
import net.thumbtack.school.hiring.exception.ServerErrorCode;
import net.thumbtack.school.hiring.exception.ServerException;
import net.thumbtack.school.hiring.request.employer.*;
import net.thumbtack.school.hiring.request.user.RemoveAccountDtoRequest;
import net.thumbtack.school.hiring.response.GetVacanciesDtoResponse;
import net.thumbtack.school.hiring.response.employer.*;
import net.thumbtack.school.hiring.response.user.RemoveAccountDtoResponse;
import net.thumbtack.school.hiring.server.Employee;
import net.thumbtack.school.hiring.server.Employer;
import net.thumbtack.school.hiring.server.Skill;
import net.thumbtack.school.hiring.server.Vacancy;

import java.util.ArrayList;
import java.util.List;

public class EmployerService
{
    EmployerDaoImpl employerDaoObj;
    Gson gson = new Gson();

    public EmployerService()
    {
        employerDaoObj = new EmployerDaoImpl();
    }

    public String registerEmployer(String registerRequestJson)
    {
        Employer registeredEmployer;
        RegisterEmployerDtoRequest regEmployerDto = gson.fromJson(registerRequestJson, RegisterEmployerDtoRequest.class);
        RegisterEmployerDtoResponse response = new RegisterEmployerDtoResponse();

        try{
            regEmployerDto.validate();
            registeredEmployer = new Employer(regEmployerDto.getCompanyName(), regEmployerDto.getAddress(),
                    regEmployerDto.getEmail(), regEmployerDto.getFirstName(), regEmployerDto.getLastName(),
                    regEmployerDto.getLogin(), regEmployerDto.getPassword());

            employerDaoObj.InsertEmployer(registeredEmployer);

        } catch (ServerException exception){
            response.setError(exception.getErrorCode().getErrorString());
            return gson.toJson(response);
        }

        registeredEmployer.setToken(response.getToken());

        return gson.toJson(response);
    }

    public String getRegisteredEmployers()
    {
        return gson.toJson(employerDaoObj.getRegisteredEmployers());
    }

    public String addVacancy(String requestJson)
    {
        AddVacancyDtoRequest req = gson.fromJson(requestJson, AddVacancyDtoRequest.class);
        AddVacancyDtoResponse response = new AddVacancyDtoResponse();

        try{
            req.validate();
        } catch (ServerException e){
            response.setError(e.getErrorCode().getErrorString());
            return gson.toJson(response);
        }

        Vacancy v = new Vacancy(req.getName(), req.getSalary(), req.getRequirements());

        for (Employer e : employerDaoObj.getRegisteredEmployers()){
            if(e.getToken().equals(req.getToken())){
                try{
                    // fill in employer contact data
                    v.setCompanyName(e.getCompanyName());
                    v.setEmployerEmail(e.getEmail());
                    v.setEmployerFirstName(e.getFirstName());
                    v.setEmployerLastName(e.getLastName());

                    employerDaoObj.addVacancy(e, v);

                    response.setAddedVacancies(employerDaoObj.getVacancyList(employerDaoObj.getEmployer(req.getToken())));

                    return gson.toJson(response);

                } catch (ServerException exception) {
                    response.setError(exception.getErrorCode().getErrorString());
                    return gson.toJson(response);
                }
            }
        }

        response.setError(ServerErrorCode.EMPLOYER_NOT_FOUND.getErrorString());
        return gson.toJson(response);
    }

    public String removeRequirement(String jsonRequest)
    {
        RemoveRequirementDtoRequest request = gson.fromJson(jsonRequest, RemoveRequirementDtoRequest.class);
        UpdateVacancyDtoResponse response = new UpdateVacancyDtoResponse();

        try{
            request.validate();

            List<Skill> vacancyRequirements = null;

            Employer requestSender = null;

            for(Employer e : employerDaoObj.getRegisteredEmployers()){
                if(e.getToken().equals(request.getToken())){
                    vacancyRequirements = employerDaoObj.getVacancy(e, request.getVacancyName()).getRequirements();
                    requestSender = e;
                    break;
                }
            }

            for(Skill s : vacancyRequirements){
                if(s.getName().equals(request.getRequirementName())){
                    vacancyRequirements.remove(s);
                    response.setUpdatedVacancy(employerDaoObj.getVacancy(requestSender, request.getVacancyName()));
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

    public String addRequirement(String jsonRequest)
    {
        AddRequirementDtoRequest request = gson.fromJson(jsonRequest, AddRequirementDtoRequest.class);
        UpdateVacancyDtoResponse response = new UpdateVacancyDtoResponse();

        try{
            request.validate();

            List<Vacancy> vacancies = null;

            for(Employer e : employerDaoObj.getRegisteredEmployers()){
                if(e.getToken().equals(request.getToken())){
                    vacancies = employerDaoObj.getVacancyList(e);
                    break;
                }
            }

            for(Vacancy v : vacancies){
                if(v.getName().equals(request.getVacancyName())){
                    v.getRequirements().add(request.getNewSkill());

                    response.setUpdatedVacancy(v);
                    break;
                }
            }
        } catch (ServerException e){
            response.setError(e.getErrorCode().getErrorString());
            return gson.toJson(response);
        }

        return gson.toJson(response);
    }

    public String removeVacancy(String jsonRequest)
    {
        RemoveVacancyDtoRequest request = gson.fromJson(jsonRequest, RemoveVacancyDtoRequest.class);
        UpdateVacancyDtoResponse response = new UpdateVacancyDtoResponse();

        try{
            request.validate();

            List<Vacancy> vacancies = null;

            for(Employer e : employerDaoObj.getRegisteredEmployers()){
                if(e.getToken().equals(request.getToken())){
                   vacancies = employerDaoObj.getVacancyList(e);

                   for(Vacancy v : vacancies){
                       if(v.getName().equals(request.getVacancyName())){
                           vacancies.remove(v);
                       }
                   }
                }
            }
        } catch (ServerException e){
            response.setError(e.getErrorCode().getErrorString());
            return gson.toJson(response);
        }

        return gson.toJson(response);
    }

    public String getVacanciesAll(String jsonRequest)
    {
        GetVacanciesDtoRequest request = gson.fromJson(jsonRequest, GetVacanciesDtoRequest.class);
        GetVacanciesDtoResponse response = new GetVacanciesDtoResponse();

        try{
            request.validate();

            List<Vacancy> vacancies = null;

            for(Employer e : employerDaoObj.getRegisteredEmployers()){
                if(e.getToken().equals(request.getToken())){
                    response.setVacancies(employerDaoObj.getVacancyList(e));
                    break;
                }
            }
        } catch (ServerException e){
            response.setError(e.getErrorCode().getErrorString());
            return gson.toJson(response);
        }

        return gson.toJson(response);
    }

    public String getVacanciesActive(String jsonRequest)
    {
        GetVacanciesDtoRequest request = gson.fromJson(jsonRequest, GetVacanciesDtoRequest.class);
        GetVacanciesDtoResponse response = new GetVacanciesDtoResponse();

        try{
            request.validate();

            List<Vacancy> vacancies = null;

            for(Employer e : employerDaoObj.getRegisteredEmployers()){
                if(e.getToken().equals(request.getToken())){
                    vacancies = employerDaoObj.getVacancyList(e);
                    break;
                }
            }

            for(Vacancy v : vacancies){
                if(v.isActive()){
                    response.getVacancies().add(v);
                }
            }

        } catch (ServerException e){
            response.setError(e.getErrorCode().getErrorString());
            return gson.toJson(response);
        }

        return gson.toJson(response);
    }

    public String getVacanciesInactive(String jsonRequest)
    {
        GetVacanciesDtoRequest request = gson.fromJson(jsonRequest, GetVacanciesDtoRequest.class);
        GetVacanciesDtoResponse response = new GetVacanciesDtoResponse();

        try{
            request.validate();

            List<Vacancy> vacancies = null;

            for(Employer e : employerDaoObj.getRegisteredEmployers()){
                if(e.getToken().equals(request.getToken())){
                    vacancies = employerDaoObj.getVacancyList(e);
                    break;
                }
            }

            for(Vacancy v : vacancies){
                if(!v.isActive()){
                    response.getVacancies().add(v);
                }
            }

        } catch (ServerException e){
            response.setError(e.getErrorCode().getErrorString());
            return gson.toJson(response);
        }

        return gson.toJson(response);
    }

    public String setVacancyStatus(String jsonRequest)
    {
        SetVacancyStatusDtoRequest request = gson.fromJson(jsonRequest, SetVacancyStatusDtoRequest.class);
        SetVacancyStatusDtoResponse response = new SetVacancyStatusDtoResponse();

        try{
            request.validate();

            List<Vacancy> vacancies = null;

            for(Employer e : employerDaoObj.getRegisteredEmployers()){
                if(e.getToken().equals(request.getToken())){
                    vacancies = employerDaoObj.getVacancyList(e);
                    break;
                }
            }

            for(Vacancy v : vacancies){
                if(v.getName().equals(request.getVacancyName())){
                    v.setActive(request.isActive());
                    response.setVacancyName(v.getName());
                    response.setActive(v.isActive());

                    return gson.toJson(response);
                }
            }
        } catch (ServerException e){
            response.setError(e.getErrorCode().getErrorString());
            return gson.toJson(response);
        }

        response.setError(ServerErrorCode.VACANCY_NOT_FOUND.getErrorString());
        return gson.toJson(response);
    }

   public String changeRequirementLevel(String jsonRequest)
   {
       ChangeRequirementLevelDtoRequest request = gson.fromJson(jsonRequest, ChangeRequirementLevelDtoRequest.class);
       UpdateVacancyDtoResponse response = new UpdateVacancyDtoResponse();

       try{
           request.validate();

           List<Vacancy> vacancies = null;
           Vacancy updatedVacancy = null;

           for(Employer e : employerDaoObj.getRegisteredEmployers()){
               if(e.getToken().equals(request.getToken())){
                   vacancies = employerDaoObj.getVacancyList(e);
                   break;
               }
           }

           List<Skill> requirements = null;

           for(Vacancy v : vacancies){
               if(v.getName().equals(request.getVacancyName())){
                   requirements = v.getRequirements();
                   updatedVacancy = v;
                   break;
               }
           }

           for(Skill s : requirements){
               if(s.getName().equals(request.getRequirementName())){
                   s.setLevel(request.getLevel());
               }
           }

           response.setUpdatedVacancy(updatedVacancy);

       } catch (ServerException e){
           response.setError(e.getErrorCode().getErrorString());
           return gson.toJson(response);
       }

       return gson.toJson(response);
   }

    public String changeRequirementName(String jsonRequest)
    {
        ChangeRequirementNameDtoRequest request = gson.fromJson(jsonRequest, ChangeRequirementNameDtoRequest.class);
        UpdateVacancyDtoResponse response = new UpdateVacancyDtoResponse();

        try{
            request.validate();

            List<Vacancy> vacancies = null;
            Vacancy updatedVacancy = null;

            for(Employer e : employerDaoObj.getRegisteredEmployers()){
                if(e.getToken().equals(request.getToken())){
                    vacancies = employerDaoObj.getVacancyList(e);
                    break;
                }
            }

            List<Skill> requirements = null;

            for(Vacancy v : vacancies){
                if(v.getName().equals(request.getVacancyName())){
                    requirements = v.getRequirements();
                    updatedVacancy = v;
                    break;
                }
            }

            for(Skill s : requirements){
                if(s.getName().equals(request.getRequirementName())){
                    s.setName(request.getNewRequirementName());
                }
            }

            response.setUpdatedVacancy(updatedVacancy);

        } catch (ServerException e){
            response.setError(e.getErrorCode().getErrorString());
            return gson.toJson(response);
        }

        return gson.toJson(response);
    }


    private boolean hasRequiredSkills(Employee e, List<Skill> requirements) throws ServerException
    {
        if(employerDaoObj.getSkills(e).isEmpty()){
            return false;
        }

        int numberOfRequrementsCovered = 0;

        for (Skill requiredSkill : requirements) {
            for (Skill s : employerDaoObj.getSkills(e)) {
                if(s.getName().equals(requiredSkill.getName())){
                    if (s.getLevel() >= requiredSkill.getLevel()) {
                        numberOfRequrementsCovered += 1;
                    }
                }
            }
        }

        return numberOfRequrementsCovered == requirements.size();
    }

    public String getEmployeesAllSkillsRequiredLevel(String requestJson)
    {
        GetEmployeesDtoRequest req = gson.fromJson(requestJson, GetEmployeesDtoRequest.class);
        GetEmployeesDtoResponse response = new GetEmployeesDtoResponse();

        List<Employee> retEmployees = new ArrayList<>();
        List<Skill> requirements;

        try{
            req.validate();
            Employer requestSender = employerDaoObj.getEmployer(req.getToken());
            requirements = employerDaoObj.getVacancy(requestSender, req.getVacancyName()).getRequirements();

            for (Employee e : employerDaoObj.getRegisteredEmployees()) {
                if( hasRequiredSkills(e, requirements) && e.isActive()){
                    retEmployees.add(e);
                }
            }

        } catch (ServerException e){
            response .setError(e.getErrorCode().getErrorString());
            return gson.toJson(response);
        }

        response.setEmployeeList(retEmployees);
        return gson.toJson(response);
    }

    // список всех потенциальных работников,
    // имеющих все обязательные требования на уровне не ниже уровня, указанного в требовании
    private boolean hasMandatorySkills(Employee e, List<Skill> requirements) throws ServerException
    {
        if(employerDaoObj.getSkills(e).isEmpty()){
            return false;
        }

        int numberOfRequirementsCovered = 0;
        int numberOfMandatoryRequirements = 0;
        // Determine number of mandatory requirements
        for (int i = 0; i < requirements.size(); ++i) {
            if(requirements.get(i).isMandatory()){
                numberOfMandatoryRequirements += 1;
            }
        }

        for (Skill requiredSkill : requirements) {
            for (Skill s : employerDaoObj.getSkills(e)) {
                if(s.getName().equals(requiredSkill.getName()) && requiredSkill.isMandatory()){
                    if (s.getLevel() >= requiredSkill.getLevel()) {
                        numberOfRequirementsCovered += 1;
                    }
                }
            }
        }

        return numberOfRequirementsCovered == numberOfMandatoryRequirements;
    }
    // список всех потенциальных работников,
    // имеющих все обязательные требования на уровне не ниже уровня, указанного в требовании
    public String getEmployeesAllMandatorySkills(String requestJson)
    {
        GetEmployeesDtoRequest req = gson.fromJson(requestJson, GetEmployeesDtoRequest.class);
        GetEmployeesDtoResponse response = new GetEmployeesDtoResponse();

        List<Employee> retEmployees = new ArrayList<>();
        List<Skill> requirements;

        try{
            req.validate();
            Employer requestSender = employerDaoObj.getEmployer(req.getToken());
            requirements = employerDaoObj.getVacancy(requestSender, req.getVacancyName()).getRequirements();

            for (Employee e : employerDaoObj.getRegisteredEmployees()) {
                if( hasMandatorySkills(e, requirements) && e.isActive()){
                    retEmployees.add(e);
                }
            }

        } catch (ServerException e){
            response.setError(e.getErrorCode().getErrorString());
            return gson.toJson(response);
        }



        response.setEmployeeList(retEmployees);
        return gson.toJson(response);
    }

    // список всех потенциальных работников, имеющих все необходимые для этой вакансии умения на любом уровне
    private boolean hasSkillsOnAnyLevel(Employee e, List<Skill> requirements) throws ServerException
    {
        if(employerDaoObj.getSkills(e).isEmpty()){
            return false;
        }

        int numberOfRequrementsCovered = 0;

        for (Skill requiredSkill : requirements) {
            for (Skill s : employerDaoObj.getSkills(e)) {
                if(s.getName().equals(requiredSkill.getName())){
                    numberOfRequrementsCovered += 1;
                }
            }
        }

        return numberOfRequrementsCovered == requirements.size();
    }

    // список всех потенциальных работников, имеющих все необходимые для этой вакансии умения на любом уровне
    public String getEmployeesAnyLevel(String requestJson)
    {
        GetEmployeesDtoRequest req = gson.fromJson(requestJson, GetEmployeesDtoRequest.class);
        GetEmployeesDtoResponse response = new GetEmployeesDtoResponse();

        List<Employee> retEmployees = new ArrayList<>();
        List<Skill> requirements;

        try{
            req.validate();
            Employer requestSender = employerDaoObj.getEmployer(req.getToken());
            requirements = employerDaoObj.getVacancy(requestSender, req.getVacancyName()).getRequirements();

            for (Employee e : employerDaoObj.getRegisteredEmployees()) {
                if( hasSkillsOnAnyLevel(e, requirements) && e.isActive()){
                    retEmployees.add(e);
                }
            }

        } catch (ServerException e){
            response.setError(e.getErrorCode().getErrorString());
            return gson.toJson(response);
        }

        response.setEmployeeList(retEmployees);
        return gson.toJson(response);
    }

    // список всех потенциальных работников,
    // имеющих хотя бы одно  необходимое для этой вакансии умение на уровне не ниже уровня, указанного в требовании
    private boolean hasAnySkill(Employee e, List<Skill> requirements) throws ServerException
    {
        if(employerDaoObj.getSkills(e).isEmpty()){
            return false;
        }

        for (Skill requiredSkill : requirements) {
            for (Skill s : employerDaoObj.getSkills(e)) {
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
    public String getEmployeesAnySkill(String requestJson)
    {
        GetEmployeesDtoRequest request = gson.fromJson(requestJson, GetEmployeesDtoRequest.class);
        GetEmployeesDtoResponse response = new GetEmployeesDtoResponse();

        List<Employee> retEmployees = new ArrayList<>();
        List<Skill> requirements;

        try{
            request.validate();
            Employer requestSender = employerDaoObj.getEmployer(request.getToken());
            requirements = employerDaoObj.getVacancy(requestSender, request.getVacancyName()).getRequirements();

            for (Employee e : employerDaoObj.getRegisteredEmployees()) {
                if( hasAnySkill(e, requirements) && e.isActive()){
                    retEmployees.add(e);
                }
            }

        } catch (ServerException e){
            response.setError(e.getErrorCode().getErrorString());
            return gson.toJson(response);
        }

        response.setEmployeeList(retEmployees);
        return gson.toJson(response);
    }

    public String hire(String jsonRequest)
    {
        HireDtoRequest request = gson.fromJson(jsonRequest, HireDtoRequest.class);
        HireDtoResponse response = new HireDtoResponse();

        try{
            request.validate();
            Employer requestSender = null;

            for(Employer e : employerDaoObj.getRegisteredEmployers()){
                if(e.getToken().equals(request.getToken())){
                    requestSender = e;
                    break;
                }
            }
            // set Vacancy Inactive
            for(Vacancy v : employerDaoObj.getVacancyList(requestSender)){
                if(v.getName().equals(request.getVacancyName())){
                    v.setActive(false);
                }
            }

            // set Employee inactive
            for(Employee e : employerDaoObj.getRegisteredEmployees()){
                if(e.getEmail().equals(request.getEmployeeEmail())){
                    e.setActive(false);
                }
            }

            response.setResult("Employee " + request.getEmployeeEmail() + " hired. Vacancy " +
                    request.getVacancyName() + " is no longer active");

        } catch (ServerException e){
            response.setError(e.getErrorCode().getErrorString());
            return gson.toJson(response);
        }

        return gson.toJson(response);
    }

    public String removeAccount(String jsonRequest)
    {
        RemoveAccountDtoRequest request = gson.fromJson(jsonRequest, RemoveAccountDtoRequest.class);
        RemoveAccountDtoResponse response = new RemoveAccountDtoResponse();

        try{
            request.validate();

            for(Employer e : employerDaoObj.getRegisteredEmployers()){
                if(e.getToken().equals(request.getToken())){

                    employerDaoObj.removeAccount(e);

                    response.setResult("Your account has been deleted");
                }
            }
        } catch (ServerException e){
            response.setError(e.getErrorCode().getErrorString());
            return gson.toJson(response);
        }

        return gson.toJson(response);
    }
}