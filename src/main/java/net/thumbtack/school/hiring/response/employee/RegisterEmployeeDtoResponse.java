package net.thumbtack.school.hiring.response.employee;

import net.thumbtack.school.hiring.server.Employee;

import java.util.UUID;

public class RegisterEmployeeDtoResponse
{
    UUID token;
    Employee registeredEmployee;
    String error;

    public RegisterEmployeeDtoResponse()
    {
        error = null;
        token = null;
        registeredEmployee = null;
    }

    public UUID getToken()
    {
        if(token == null){
            token = UUID.randomUUID();
        }

        return token;
    }

    public Employee getRegisteredEmployee()
    {
        return registeredEmployee;
    }

    public void setToken(UUID token) {
        this.token = token;
    }

    public void setRegisteredEmployee(Employee registeredEmployee) {
        this.registeredEmployee = registeredEmployee;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
        token = null;
        registeredEmployee = null;
    }
}