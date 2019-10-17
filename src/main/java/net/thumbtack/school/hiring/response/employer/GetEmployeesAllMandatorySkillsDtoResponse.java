package net.thumbtack.school.hiring.response.employer;

import net.thumbtack.school.hiring.server.Employee;

import java.util.List;

public class GetEmployeesAllMandatorySkillsDtoResponse
{
    List<Employee> employeeList;
    String error;

    public GetEmployeesAllMandatorySkillsDtoResponse(List<Employee> employeeList)
    {
        this.employeeList = employeeList;
    }

    public GetEmployeesAllMandatorySkillsDtoResponse(String error)
    {
        this.error = error;
        this.employeeList = null;
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
