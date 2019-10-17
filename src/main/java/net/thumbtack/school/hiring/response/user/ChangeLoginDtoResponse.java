package net.thumbtack.school.hiring.response.user;

public class ChangeLoginDtoResponse
{
    String newLogin;
    String error = null;

    public ChangeLoginDtoResponse(String newLogin)
    {
        this.newLogin = newLogin;
    }

    public ChangeLoginDtoResponse()
    {
        newLogin = null;
    }

    public void setError(String error)
    {
        this.error = error;
    }

    public String getError()
    {
        return error;
    }

    public String getNewLogin()
    {
        return newLogin;
    }

    public void setNewLogin(String newLogin) {
        this.newLogin = newLogin;
    }
}
