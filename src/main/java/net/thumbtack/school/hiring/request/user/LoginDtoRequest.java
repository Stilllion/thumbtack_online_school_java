package net.thumbtack.school.hiring.request.user;

import net.thumbtack.school.hiring.exception.ServerErrorCode;
import net.thumbtack.school.hiring.exception.ServerException;

public class LoginDtoRequest
{
    String login;
    String password;

    public LoginDtoRequest(String login, String password)
    {
        this.login = login;
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public void validate() throws ServerException
    {
        if(login == null || login.equals("")){
            throw new ServerException(ServerErrorCode.WRONG_LOGIN);
        }

        if(password == null || password.equals("")){
            throw new ServerException(ServerErrorCode.WRONG_PASSWORD);
        }
    }
}
