package net.thumbtack.school.hiring.dao;

import net.thumbtack.school.hiring.exception.ServerException;
import net.thumbtack.school.hiring.server.Employee;

public interface EmployeeDao
{
    void InsertEmployee(Employee e) throws ServerException;
}
