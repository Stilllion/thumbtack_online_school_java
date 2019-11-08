package net.thumbtack.school.hiring.daoimpl;

import net.thumbtack.school.hiring.dao.EmployeeDao;
import net.thumbtack.school.hiring.exception.ServerException;
import net.thumbtack.school.hiring.server.*;

import java.util.List;
import java.util.UUID;

public class EmployeeDaoImpl implements EmployeeDao
{
    private Database db = Database.getInstance();

    public void InsertEmployee(Employee e) throws ServerException
    {
        db.InsertEmployee(e);
    }

    public List<Employee> getRegisteredEmployees()
    {
        return db.getRegisteredEmployees();
    }

    public void addSkill(Employee e, Skill s)
    {
        db.addEmployeeSkill(e, s);
    }

    public List<Employer> getRegisteredEmployers()
    {
        return db.getRegisteredEmployers();
    }

    public Employee getEmployee(UUID token) throws ServerException
    {
        return db.getEmployee(token);
    }

    public List<Skill> getSkills(Employee e) throws ServerException
    {
        return db.getSkills(e);
    }

    public List<Vacancy> getVacancies(Employer e) throws ServerException
    {
        return db.getVacancies(e);
    }

    public void removeSkill(Employee e, String skillName)
    {
        db.removeSkill(e, skillName);
    }

    public int setSkillLevel(Employee e, String skillName, int newSkillLevel) throws ServerException
    {
        return db.setSkillLevel(e, skillName, newSkillLevel);
    }

    public void removeAccount(Employee e) throws ServerException
    {
        db.removeAccountEmployee(e);
    }
}
