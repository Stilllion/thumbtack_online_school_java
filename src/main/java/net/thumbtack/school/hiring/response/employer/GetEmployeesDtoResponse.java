package net.thumbtack.school.hiring.response.employer;

import net.thumbtack.school.hiring.server.Employee;

import java.util.List;

public class GetEmployeesDtoResponse
{
    List<Employee> employeeList;

    String error;

    public GetEmployeesDtoResponse(){}

    public List<Employee> getEmployeeList()
    {
        return employeeList;
    }

    public void setEmployeeList(List<Employee> employeeList) {
        this.employeeList = employeeList;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
        employeeList = null;
    }
}
