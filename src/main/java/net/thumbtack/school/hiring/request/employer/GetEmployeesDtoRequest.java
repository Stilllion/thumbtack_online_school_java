package net.thumbtack.school.hiring.request.employer;

import net.thumbtack.school.hiring.exception.ServerErrorCode;
import net.thumbtack.school.hiring.exception.ServerException;

import java.util.UUID;

public class GetEmployeesDtoRequest
{
    String vacancyName;
    UUID token;

    public GetEmployeesDtoRequest(UUID token, String vacancyName)
    {
        this.token = token;
        this.vacancyName = vacancyName;
    }

    public String getVacancyName() {
        return vacancyName;
    }

    public UUID getToken() {
        return token;
    }

    public void validate() throws ServerException
    {
        if(vacancyName == null || vacancyName.equals("")){
            throw new ServerException(ServerErrorCode.WRONG_VACANCY_NAME);
        }

        if(token == null){
            throw new ServerException(ServerErrorCode.WRONG_TOKEN);
        }
    }
}
