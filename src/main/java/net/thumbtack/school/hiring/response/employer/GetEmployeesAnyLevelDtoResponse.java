package net.thumbtack.school.hiring.response.employer;

import net.thumbtack.school.hiring.server.Employee;

import java.util.List;

public class GetEmployeesAnyLevelDtoResponse
{
    List<Employee> employeeList;
    String error;

    public GetEmployeesAnyLevelDtoResponse(List<Employee> employeeList)
    {
        this.employeeList = employeeList;
    }

    public GetEmployeesAnyLevelDtoResponse(String error)
    {
        this.error = error;
        this.employeeList = null;
    }

    public List<Employee> getEmployeeList()
    {
        return employeeList;
    }
}
