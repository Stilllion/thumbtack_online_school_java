package net.thumbtack.school.hiring.request.user;

import net.thumbtack.school.hiring.exception.ServerErrorCode;
import net.thumbtack.school.hiring.exception.ServerException;

import java.util.UUID;

public class ChangePasswordDtoRequest
{
    UUID token;
    String newPassword;

    public ChangePasswordDtoRequest(UUID token, String newPassword)
    {
        this.token = token;
        this.newPassword = newPassword;
    }

    public boolean validate() throws ServerException
    {
        if(token == null){
            throw new ServerException(ServerErrorCode.WRONG_FIRSTNAME);
        }

        if(newPassword == null || newPassword.equals("")){
            throw new ServerException(ServerErrorCode.WRONG_PASSWORD);
        }

        return true;
    }

    public UUID getToken() {
        return token;
    }

    public String getNewPassword() {
        return newPassword;
    }
}
