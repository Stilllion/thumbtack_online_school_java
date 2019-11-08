package net.thumbtack.school.hiring.daoimpl;

import net.thumbtack.school.hiring.dao.EmployerDao;
import net.thumbtack.school.hiring.exception.ServerException;
import net.thumbtack.school.hiring.server.*;

import java.util.List;
import java.util.UUID;

public class EmployerDaoImpl implements EmployerDao
{
    private Database db = Database.getInstance();

    public void InsertEmployer(Employer e) throws ServerException
    {
        db.InsertEmployer(e);
    }

    public List<Employer> getRegisteredEmployers()
    {
        return db.getRegisteredEmployers();
    }

    public List<Employee> getRegisteredEmployees()
    {
        return db.getRegisteredEmployees();
    }

    public Vacancy getVacancy(Employer e, String vacancyName) throws ServerException
    {
        return db.getVacancy(e, vacancyName);
    }

    public List<Vacancy> getVacancyList(Employer e) throws ServerException
    {
        return db.getVacancyList(e);
    }

    public List<Skill> getSkills(Employee e) throws ServerException
    {
        return db.getSkills(e);
    }

    public void addVacancy(Employer e, Vacancy v) throws ServerException
    {
        db.addVacancy(e, v);
    }

    public Employer getEmployer(UUID token) throws ServerException
    {
        return db.getEmployer(token);
    }

    public void removeAccount(Employer e) throws ServerException
    {
        db.removeAccountEmployer(e);
    }
}
