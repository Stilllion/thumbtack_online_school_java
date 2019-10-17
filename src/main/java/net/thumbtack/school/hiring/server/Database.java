package net.thumbtack.school.hiring.server;

import net.thumbtack.school.hiring.exception.ServerErrorCode;
import net.thumbtack.school.hiring.exception.ServerException;

import java.util.*;

public class Database {
    private static Database dbInstance = new Database();
    private static List<Employer> registeredEmployers = new ArrayList<>();
    private static List<Employee> registeredEmployees = new ArrayList<>();

    private static Map<Employer, List<Vacancy>> vacancies = new HashMap<>();
    private static Map<Employee, List<Skill>> skills = new HashMap<>();

    public static Database getInstance()
    {
        return dbInstance;
    }

    private Database(){}

    public void InsertEmployer(Employer e) throws ServerException
    {
        if (registeredEmployers.contains(e)){
            throw new ServerException(ServerErrorCode.DUPLICATE_EMPLOYER);
        }
        registeredEmployers.add(e);
    }

    public void InsertEmployee(Employee e) throws ServerException
    {
        if (registeredEmployees.contains(e)){
            throw new ServerException(ServerErrorCode.DUPLICATE_EMPLOYEE);
        }
        registeredEmployees.add(e);
        // Add empty skill list to insert into one later
        List<Skill> skillArray = new ArrayList<>();
        skills.put(e, skillArray);
    }

    public List<Employer> getRegisteredEmployers()
    {
        return registeredEmployers;
    }

    public List<Employee> getRegisteredEmployees()
    {
        return registeredEmployees;
    }

    public List<User> getRegisteredUsers()
    {
        List<User> retList = new ArrayList<>();

        retList.addAll(registeredEmployees);
        retList.addAll(registeredEmployers);

        return retList;
    }

    public Employer getEmployer(UUID token) throws ServerException
    {
        Employer retEmployer = null;

        for(Employer e : registeredEmployers){
            if(e.getToken().equals(token)){
                retEmployer =  e;
            }
        }

        if(retEmployer == null){
            throw new ServerException(ServerErrorCode.EMPLOYER_NOT_FOUND);
        }

        return retEmployer;
    }

    public Employee getEmployee(UUID token) throws ServerException
    {
        Employee retEmployee = null;

        for(Employee e : registeredEmployees){
            if(e.getToken().equals(token)){
                retEmployee =  e;
             }
        }

        if(retEmployee == null){
            throw new ServerException(ServerErrorCode.EMPLOYEE_NOT_FOUND);
        }

        return retEmployee;
    }

    public void addVacancy(Employer e, Vacancy v) throws ServerException
    {
        // Check for dublicates

        if(vacancies.containsValue(v)){
            throw new ServerException(ServerErrorCode.DUPLICATE_VACANCY);
        }

        if(vacancies.get(e) == null){
            vacancies.put(e, new ArrayList<Vacancy>());
        }

        // TODO: Maybe we should change Vacancies container to Set<Vacancies>

        vacancies.get(e).add(v);
    }

    //
    // TODO: ALso add methods for inserting Array of skills (if there is one already, just move elements over to it)
    //

    public void addEmployeeSkill(Employee e, Skill s)
    {
        skills.get(e).add(s);
    }

    public Vacancy getVacancy(Employer e, String vacancyName) throws ServerException
    {
        Vacancy retVacancy = null;

        for(Vacancy v : vacancies.get(e)){
            if(v.getName().equals(vacancyName)){
                retVacancy = v;
                break;
            }
        }

        if(retVacancy == null){
            throw new ServerException(ServerErrorCode.VACANCY_NOT_FOUND);
        }

        return retVacancy;
    }

    public List<Vacancy> getVacancyList(Employer e)
    {
        return vacancies.get(e);
    }

    public List<Skill> getSkills(Employee e)
    {
        return skills.get(e);
    }

    public List<Vacancy> getVacancies(Employer e) throws ServerException
    {
        if(!vacancies.containsKey(e)){
            throw new ServerException(ServerErrorCode.VACANCY_NOT_FOUND);
        }

        return vacancies.get(e);
    }

    public void removeAccountEmployer(Employer e) throws ServerException
    {
        if(!registeredEmployers.contains(e)|| !vacancies.containsKey(e)){
            throw new ServerException(ServerErrorCode.EMPLOYER_NOT_FOUND);
        }

        registeredEmployers.remove(e);
        vacancies.remove(e);
    }

    public void removeAccountEmployee(Employee e) throws ServerException
    {
        if(!registeredEmployees.contains(e)|| !skills.containsKey(e)){
            throw new ServerException(ServerErrorCode.EMPLOYEE_NOT_FOUND);
        }

        registeredEmployees.remove(e);
        skills.remove(e);
    }

    public void reset()
    {
        registeredEmployers.clear();
        registeredEmployees.clear();
        vacancies.clear();
        skills.clear();
    }
}