package net.thumbtack.school.hiring.request.user;

import net.thumbtack.school.hiring.exception.ServerErrorCode;
import net.thumbtack.school.hiring.exception.ServerException;

import java.util.UUID;

public class ChangeLastNameDtoRequest
{
    UUID token;
    String newLastName;

    public ChangeLastNameDtoRequest(UUID token, String newLastName)
    {
        this.token = token;
        this.newLastName = newLastName;
    }

    public boolean validate() throws ServerException
    {
        if(token == null){
            throw new ServerException(ServerErrorCode.WRONG_TOKEN);
        }

        if(newLastName == null || newLastName.equals("")){
            throw new ServerException(ServerErrorCode.WRONG_LASTNAME);
        }

        return true;
    }

    public UUID getToken() {
        return token;
    }

    public String getNewLastName() {
        return newLastName;
    }
}
