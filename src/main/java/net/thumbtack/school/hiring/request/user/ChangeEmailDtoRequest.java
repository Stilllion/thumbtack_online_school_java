package net.thumbtack.school.hiring.request.user;

import net.thumbtack.school.hiring.exception.ServerErrorCode;
import net.thumbtack.school.hiring.exception.ServerException;

import java.util.UUID;

public class ChangeEmailDtoRequest
{
    UUID token;
    String newEmail;

    public ChangeEmailDtoRequest(UUID token, String neEmail)
    {
        this.token = token;
        this.newEmail = newEmail;
    }

    public boolean validate() throws ServerException
    {
        if(token == null){
            throw new ServerException(ServerErrorCode.WRONG_TOKEN);
        }

        if(newEmail == null || newEmail.equals("")){
            throw new ServerException(ServerErrorCode.WRONG_EMAIL);
        }

        return true;
    }

    public UUID getToken() {
        return token;
    }

    public String getNewEmail() {
        return newEmail;
    }
}
