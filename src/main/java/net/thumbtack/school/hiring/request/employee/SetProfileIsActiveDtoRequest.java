package net.thumbtack.school.hiring.request.employee;

import net.thumbtack.school.hiring.exception.ServerErrorCode;
import net.thumbtack.school.hiring.exception.ServerException;

import java.util.UUID;

public class SetProfileIsActiveDtoRequest
{
    UUID token;
    boolean isActive;

    public SetProfileIsActiveDtoRequest(UUID token)
    {
        this.token = token;
    }

    public void validate() throws ServerException
    {
        if(token == null){
            throw new ServerException(ServerErrorCode.WRONG_TOKEN);
        }
    }

    public UUID getToken() {
        return token;
    }

    public void setToken(UUID token) {
        this.token = token;
    }

    public boolean isActive()
    {
        return isActive;
    }
}
