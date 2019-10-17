package net.thumbtack.school.hiring.response.employer;

import net.thumbtack.school.hiring.server.Employee;

import java.util.List;

public class GetEmployeesAllSkillsRequiredLevelDtoResponse
{
    List<Employee> employeeList;
    String error;

    public GetEmployeesAllSkillsRequiredLevelDtoResponse(List<Employee> employeeList)
    {
        this.employeeList = employeeList;
    }

    public GetEmployeesAllSkillsRequiredLevelDtoResponse(String error)
    {
        this.error = error;
        employeeList = null;
    }

    public List<Employee> getEmployeeList()
    {
        return employeeList;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
