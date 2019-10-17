package net.thumbtack.school.hiring.request.user;

import net.thumbtack.school.hiring.exception.ServerErrorCode;
import net.thumbtack.school.hiring.exception.ServerException;

import java.util.UUID;

public class LogoutDtoRequest
{
    UUID token;

    public LogoutDtoRequest(UUID token)
    {
        this.token = token;
    }

    public boolean validate() throws ServerException
    {
        if(token == null){
            throw new ServerException(ServerErrorCode.WRONG_TOKEN);
        }

        return true;
    }

    public UUID getToken()
    {
        return token;
    }
}
