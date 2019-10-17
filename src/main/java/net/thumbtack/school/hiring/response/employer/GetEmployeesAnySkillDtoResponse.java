package net.thumbtack.school.hiring.response.employer;

import net.thumbtack.school.hiring.server.Employee;

import java.util.List;

public class GetEmployeesAnySkillDtoResponse
{
    List<Employee> employeeList;

    GetEmployeesAnySkillDtoResponse(List<Employee> employeeList)
    {
        this.employeeList = employeeList;
    }

    public List<Employee> getEmployeeList()
    {
        return employeeList;
    }
}
