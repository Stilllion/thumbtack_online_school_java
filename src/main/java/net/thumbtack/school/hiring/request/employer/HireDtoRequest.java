package net.thumbtack.school.hiring.request.employer;

import net.thumbtack.school.hiring.exception.ServerErrorCode;
import net.thumbtack.school.hiring.exception.ServerException;

import java.util.UUID;

public class HireDtoRequest
{
    String employeeEmail;
    String vacancyName;
    UUID token;

    public HireDtoRequest(UUID token, String employeeEmail, String vacancyName)
    {
        this.token = token;
        this.employeeEmail = employeeEmail;
        this.vacancyName = vacancyName;
    }

    public void validate() throws ServerException
    {
        if(token == null){
            throw new ServerException(ServerErrorCode.WRONG_TOKEN);
        }

        if(vacancyName == null || vacancyName.equals("")){
            throw new ServerException(ServerErrorCode.WRONG_VACANCY_NAME);
        }

        if(employeeEmail == null || employeeEmail.equals("")){
            throw new ServerException(ServerErrorCode.WRONG_EMAIL);
        }
    }

    public String getEmployeeEmail() {
        return employeeEmail;
    }

    public void setEmployeeEmail(String employeeEmail) {
        this.employeeEmail = employeeEmail;
    }

    public String getVacancyName() {
        return vacancyName;
    }

    public void setVacancyName(String vacancyName) {
        this.vacancyName = vacancyName;
    }

    public UUID getToken() {
        return token;
    }

    public void setToken(UUID token) {
        this.token = token;
    }
}
