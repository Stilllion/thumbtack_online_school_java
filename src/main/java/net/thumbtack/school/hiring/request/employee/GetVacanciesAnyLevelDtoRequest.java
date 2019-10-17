package net.thumbtack.school.hiring.request.employee;

import net.thumbtack.school.hiring.exception.ServerErrorCode;
import net.thumbtack.school.hiring.exception.ServerException;

import java.util.UUID;

public class GetVacanciesAnyLevelDtoRequest
{
    UUID token;

    public GetVacanciesAnyLevelDtoRequest(UUID token)
    {
        this.token = token;
    }

    public UUID getToken() {
        return token;
    }

    public boolean validate() throws ServerException
    {
        if(token == null){
            throw new ServerException(ServerErrorCode.WRONG_TOKEN);
        }

        return true;
    }
}
