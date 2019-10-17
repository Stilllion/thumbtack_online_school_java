package net.thumbtack.school.hiring.request.employer;

import net.thumbtack.school.hiring.exception.ServerErrorCode;
import net.thumbtack.school.hiring.exception.ServerException;

import java.util.UUID;

public class GetVacanciesAllDtoRequest
{
    UUID token;

    public GetVacanciesAllDtoRequest(UUID token)
    {
        this.token = token;
    }

    public UUID getToken() {
        return token;
    }

    public void validate() throws ServerException
    {
        if(token == null){
            throw new ServerException(ServerErrorCode.WRONG_TOKEN);
        }
    }
}
