package net.thumbtack.school.hiring.request.employee;

import net.thumbtack.school.hiring.exception.ServerErrorCode;
import net.thumbtack.school.hiring.exception.ServerException;
import net.thumbtack.school.hiring.server.Server;

import java.util.UUID;

public class GetVacanciesRequiredLevelDtoRequest
{
    UUID token;

    public GetVacanciesRequiredLevelDtoRequest(UUID token)
    {
        this.token = token;
    }

    public UUID getToken() {
        return token;
    }

    public boolean validate() throws ServerException
    {
        if(token == null){
            throw  new ServerException(ServerErrorCode.WRONG_TOKEN);
        }

        return true;
    }
}
