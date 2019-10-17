package net.thumbtack.school.hiring.request.user;

import net.thumbtack.school.hiring.exception.ServerErrorCode;
import net.thumbtack.school.hiring.exception.ServerException;
import net.thumbtack.school.hiring.server.Server;

import java.util.UUID;

public class ChangeFirstNameDtoRequest
{
    UUID token;
    String newFirstName;

    public ChangeFirstNameDtoRequest(UUID token, String newFirstName)
    {
        this.token = token;
        this.newFirstName = newFirstName;
    }

    public boolean validate() throws ServerException
    {
        if(token == null){
            throw new ServerException(ServerErrorCode.WRONG_TOKEN);
        }

        if(newFirstName == null || newFirstName.equals("")){
            throw new ServerException(ServerErrorCode.WRONG_FIRSTNAME);
        }

        return true;
    }

    public UUID getToken() {
        return token;
    }

    public String getNewFirstName() {
        return newFirstName;
    }
}
