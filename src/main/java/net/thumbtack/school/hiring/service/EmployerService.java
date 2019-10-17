package net.thumbtack.school.hiring.service;

import net.thumbtack.school.hiring.daoimpl.EmployerDaoImpl;
import net.thumbtack.school.hiring.exception.ServerErrorCode;
import net.thumbtack.school.hiring.exception.ServerException;
import net.thumbtack.school.hiring.request.employer.AddRequirementDtoRequest;
import net.thumbtack.school.hiring.request.employer.*;
import net.thumbtack.school.hiring.request.user.RemoveAccountDtoRequest;
import net.thumbtack.school.hiring.response.employer.*;
import net.thumbtack.school.hiring.response.user.RemoveAccountDtoResponse;
import net.thumbtack.school.hiring.server.*;
import com.google.gson.Gson;

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

    // TODO inconsistent code. everywhere else i construct new Response object right away and then use setError() when needed

    public String registerEmployer(String registerRequestJsonString)
    {
        Employer registeredEmployer;
        RegisterEmployerDtoRequest regEmployerDto = gson.fromJson(registerRequestJsonString, RegisterEmployerDtoRequest.class);
        RegisterEmployerDtoResponse response = new RegisterEmployerDtoResponse();

        try{
            regEmployerDto.validate();
        } catch (ServerException e){
            response.setError(e.getErrorCode().getErrorString());
            return gson.toJson(response);
        }

        registeredEmployer = new Employer(regEmployerDto.getCompanyName(), regEmployerDto.getAddress(),
                    regEmployerDto.getEmail(), regEmployerDto.getFirstName(), regEmployerDto.getLastName(),
                    regEmployerDto.getLogin(), regEmployerDto.getPassword());

        try {
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

    public String addVacancy(String requestJsonString)
    {
        AddVacancyDtoRequest req = gson.fromJson(requestJsonString, AddVacancyDtoRequest.class);
        AddVacancyDtoResponse response = new AddVacancyDtoResponse();

        try{
            req.validate();
        } catch (ServerException e){
            response.setError(e.getErrorCode().getErrorString());
            return gson.toJson(response);
        }

        Vacancy v = new Vacancy(req.getName(), req.getSalary(), req.getRequirements());

        // Find employer with specified token
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
        RemoveRequirementDtoResponse response = new RemoveRequirementDtoResponse();

        try{
            request.validate();

            List<Skill> vacancyRequirements = null;

            for(Employer e : employerDaoObj.getRegisteredEmployers()){
                if(e.getToken().equals(request.getToken())){
                    vacancyRequirements = employerDaoObj.getVacancy(e, request.getVacancyName()).getRequirements();
                    break;
                }
            }

            for(Skill s : vacancyRequirements){
                if(s.getName().equals(request.getRequirementName())){
                    vacancyRequirements.remove(s);
                }
            }

        } catch (ServerException e){
            response.setError(e.getErrorCode().getErrorString());
            return gson.toJson(response);
        }

        response.setResult("Requirement " + request.getRequirementName() +
                " was removed from " + request.getVacancyName() + " requirements list");

        return gson.toJson(response);
    }

    public String addRequirement(String jsonRequest)
    {
        AddRequirementDtoRequest request = gson.fromJson(jsonRequest, AddRequirementDtoRequest.class);
        AddRequirementDtoResponse response = new AddRequirementDtoResponse();

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

    public String getVacanciesAll(String jsonRequest)
    {
        GetVacanciesAllDtoRequest request = gson.fromJson(jsonRequest, GetVacanciesAllDtoRequest.class);
        GetVacanciesAllDtoResponse response = new GetVacanciesAllDtoResponse();

        try{
            request.validate();

            List<Vacancy> vacancies = null;

            for(Employer e : employerDaoObj.getRegisteredEmployers()){
                if(e.getToken().equals(request.getToken())){
                    response.setAllVacancies(employerDaoObj.getVacancyList(e));
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
        GetVacanciesActiveDtoRequest request = gson.fromJson(jsonRequest, GetVacanciesActiveDtoRequest.class);
        GetVacanciesActiveDtoResponse response = new  GetVacanciesActiveDtoResponse();

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
                    response.getVacanciesActive().add(v);
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
        GetVacanciesInactiveDtoRequest request = gson.fromJson(jsonRequest, GetVacanciesInactiveDtoRequest.class);
        GetVacanciesInactiveDtoResponse response = new GetVacanciesInactiveDtoResponse();

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
                    response.getVacanciesInactive().add(v);
                }
            }

        } catch (ServerException e){
            response.setError(e.getErrorCode().getErrorString());
            return gson.toJson(response);
        }

        return gson.toJson(response);
    }

    public String setVacancyActive(String jsonRequest)
    {
        SetVacancyActiveDtoRequest request = gson.fromJson(jsonRequest, SetVacancyActiveDtoRequest.class);
        SetVacancyActiveDtoResponse response = new SetVacancyActiveDtoResponse();

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
                    v.setActive(true);
                    break;
                }
            }
        } catch (ServerException e){
            response.setError(e.getErrorCode().getErrorString());
            return gson.toJson(response);
        }

        response.setResult("Vacancy " + request.getVacancyName() + " is now Active");
        return gson.toJson(response);
    }

   /* public String setVacancyInactive(String jsonRequest)
    {
        SetVacancyInactiveDtoRequest request = gson.fromJson(jsonRequest, SetVacancyInactiveDtoRequest.class);
        SetVacancyInactiveDtoResponse response = new SetVacancyInactiveDtoResponse();

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
                    v.setActive(true);
                    break;
                }
            }
        } catch (ServerException e){
            response.setError(e.getErrorCode().getErrorString());
            return gson.toJson(response);
        }

        response.setResult("Vacancy " + request.getVacancyName() + " is now Active");
        return gson.toJson(response);
    }*/


   public String changeRequirementLevel(String jsonRequest)
   {
       ChangeRequirementLevelDtoRequest request = gson.fromJson(jsonRequest, ChangeRequirementLevelDtoRequest.class);
       ChangeRequirementLevelDtoResponse response = new ChangeRequirementLevelDtoResponse();

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
        ChangeRequirementNameDtoResponse response = new ChangeRequirementNameDtoResponse();

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


    private boolean hasRequiredSkills(Employee e, List<Skill> requirements)
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

    public String getEmployeesAllSkillsRequiredLevel(String requestJsonString)
    {
        GetEmployeesRequiredLevelDtoRequest req = gson.fromJson(requestJsonString, GetEmployeesRequiredLevelDtoRequest.class);
        GetEmployeesAllSkillsRequiredLevelDtoResponse response;

        List<Employee> retEmployees = new ArrayList<>();
        List<Skill> requirements;

        try{
            req.validate();
            Employer requestSender = employerDaoObj.getEmployer(req.getToken());
            requirements = employerDaoObj.getVacancy(requestSender, req.getVacancyName()).getRequirements();
        } catch (ServerException e){
            response = new GetEmployeesAllSkillsRequiredLevelDtoResponse(e.getErrorCode().getErrorString());
            return gson.toJson(response);
        }

        for (Employee e : employerDaoObj.getRegisteredEmployees()) {
            if( hasRequiredSkills(e, requirements)){
                retEmployees.add(e);
            }
        }

        response = new GetEmployeesAllSkillsRequiredLevelDtoResponse(retEmployees);
        return gson.toJson(response);
    }

    // список всех потенциальных работников,
    // имеющих все обязательные требования на уровне не ниже уровня, указанного в требовании
    private boolean hasMandatorySkills(Employee e, List<Skill> requirements)
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
    public String getEmployeesAllMandatorySkills(String requestJsonString)
    {
        GetEmployeesAllMandatorySkillsDtoRequest req = gson.fromJson(requestJsonString, GetEmployeesAllMandatorySkillsDtoRequest.class);
        GetEmployeesAllMandatorySkillsDtoResponse response;

        List<Employee> retEmployees = new ArrayList<>();
        List<Skill> requirements;

        try{
            req.validate();
            Employer requestSender = employerDaoObj.getEmployer(req.getToken());
            requirements = employerDaoObj.getVacancy(requestSender, req.getVacancyName()).getRequirements();
        } catch (ServerException e){
            response = new GetEmployeesAllMandatorySkillsDtoResponse(e.getErrorCode().getErrorString());
            return gson.toJson(response);
        }

        for (Employee e : employerDaoObj.getRegisteredEmployees()) {
            if( hasMandatorySkills(e, requirements)){
                retEmployees.add(e);
            }
        }

        response = new GetEmployeesAllMandatorySkillsDtoResponse(retEmployees);
        return gson.toJson(response);
    }

    // список всех потенциальных работников, имеющих все необходимые для этой вакансии умения на любом уровне
    private boolean hasSkillsOnAnyLevel(Employee e, List<Skill> requirements)
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
    public String getEmployeesAnyLevel(String requestJsonString)
    {
        GetEmployeesAnyLevelDtoRequest req = gson.fromJson(requestJsonString, GetEmployeesAnyLevelDtoRequest.class);
        GetEmployeesAnyLevelDtoResponse response;

        List<Employee> retEmployees = new ArrayList<>();
        List<Skill> requirements;

        try{
            req.validate();
            Employer requestSender = employerDaoObj.getEmployer(req.getToken());
            requirements = employerDaoObj.getVacancy(requestSender, req.getVacancyName()).getRequirements();
        } catch (ServerException e){
            response = new GetEmployeesAnyLevelDtoResponse(e.getErrorCode().getErrorString());
            return gson.toJson(response);
        }

        for (Employee e : employerDaoObj.getRegisteredEmployees()) {
            if( hasSkillsOnAnyLevel(e, requirements)){
                retEmployees.add(e);
            }
        }

        response = new GetEmployeesAnyLevelDtoResponse(retEmployees);

        return gson.toJson(response);
    }

    // список всех потенциальных работников,
    // имеющих хотя бы одно  необходимое для этой вакансии умение на уровне не ниже уровня, указанного в требовании
    private boolean hasAnySkill(Employee e, List<Skill> requirements)
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
    public String getEmployeesAnySkill(String requestJsonString)
    {
        GetEmployeesAnySkillDtoRequest req = gson.fromJson(requestJsonString, GetEmployeesAnySkillDtoRequest.class);
        // Construct request object, validate
        List<Employee> retEmployees = new ArrayList<>();
        List<Skill> requirements;

        try{
            req.validate();
            Employer requestSender = employerDaoObj.getEmployer(req.getToken());
            requirements = employerDaoObj.getVacancy(requestSender, req.getVacancyName()).getRequirements();
        } catch (ServerException e){
            return gson.toJson(e.getErrorCode());
        }

        for (Employee e : employerDaoObj.getRegisteredEmployees()) {
            if( hasAnySkill(e, requirements)){
                retEmployees.add(e);
            }
        }

        return gson.toJson(retEmployees);
    }

  /*  public String removeAccount(String jsonRequest)
    {
        RemoveAccountDtoRequest req = gson.fromJson(jsonRequest,  RemoveAccountDtoRequest.class);

        try{
            req.validate();
        } catch (ServerException e){
            return gson.toJson(e.getErrorCode().getErrorString());
        }

        Employer accountToBeRemoved = null;

        for(Employer e : employerDaoObj.getRegisteredEmployers()){
            if(e.getToken().equals(req.getToken())){
                accountToBeRemoved = e;
                break;
            }
        }

        if(accountToBeRemoved == null){
            return gson.toJson("error: Account not found");
        }

        try{
            employerDaoObj.removeAccount(accountToBeRemoved);
        } catch (ServerException e){
            return gson.toJson(e.getErrorCode().getErrorString());
        }

        return gson.toJson(new RemoveAccountDtoResponse(accountToBeRemoved));
    }*/
}