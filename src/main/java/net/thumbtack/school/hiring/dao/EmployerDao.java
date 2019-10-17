package net.thumbtack.school.hiring.dao;

import net.thumbtack.school.hiring.exception.ServerException;
import net.thumbtack.school.hiring.server.Employer;

public interface EmployerDao
{
    void InsertEmployer(Employer employer) throws ServerException;
}
