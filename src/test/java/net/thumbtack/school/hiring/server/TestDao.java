package net.thumbtack.school.hiring.server;

import net.thumbtack.school.hiring.exception.ServerException;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public class TestDao
{
    private Database db = Database.getInstance();

    public TestDao(){}

    public List<Employer> getRegisteredEmployers()
    {
        return db.getRegisteredEmployers();
    }

    public List<Employee> getRegisteredEmployees()
    {
        return db.getRegisteredEmployees();
    }

    public List<User> getRegisteredUsers()
    {
        return db.getRegisteredUsers();
    }

    public Vacancy getVacancy(Employer e, String vacancyName) throws ServerException
    {
        return db.getVacancy(e, vacancyName);
    }

    public List<Skill> getSkills(Employee e) throws ServerException
    {
        return db.getSkills(e);
    }

    public Employer getEmployer(UUID token) throws ServerException
    {
        return db.getEmployer(token);
    }

    public void addSkill(Employee e, Skill s)
    {
        db.addEmployeeSkill(e, s);
    }

    public Employee getEmployee(UUID token) throws ServerException
    {
        return db.getEmployee(token);
    }

    public List<Vacancy> getVacancies(Employer e) throws ServerException
    {
        return db.getVacancies(e);
    }

    public Map<Employer, List<Vacancy>> getVacanciesAll()
    {
        return db.getVacanciesAll();
    }

    public Map<Employee, List<Skill>> getSkillsAll()
    {
        return db.getSkillsAll();
    }

    public void resetDatabase()
    {
        db.reset();
    }

}
