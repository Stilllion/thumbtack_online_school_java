package net.thumbtack.school.hiring.request.employer;

import net.thumbtack.school.hiring.exception.ServerErrorCode;
import net.thumbtack.school.hiring.exception.ServerException;

import java.util.UUID;

public class RemoveVacancyDtoRequest
{
    UUID token;
    String vacancyName;

    public RemoveVacancyDtoRequest(UUID token, String vacancyName)
    {
        this.token = token;
        this.vacancyName = vacancyName;
    }

    public void validate() throws ServerException
    {
        if(token == null){
            throw new ServerException(ServerErrorCode.WRONG_TOKEN);
        }

        if(vacancyName.equals("") || vacancyName == null){
            throw new ServerException(ServerErrorCode.WRONG_VACANCY_NAME);
        }
    }
}
