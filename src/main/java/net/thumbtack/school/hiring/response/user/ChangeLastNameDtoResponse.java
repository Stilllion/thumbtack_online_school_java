package net.thumbtack.school.hiring.response.user;

public class ChangeLastNameDtoResponse
{
    String newLastName;
    String error = null;

    public ChangeLastNameDtoResponse(String newLastName)
    {
        this.newLastName = newLastName;
    }

    public ChangeLastNameDtoResponse()
    {
        newLastName = null;
    }

    public void setError(String error)
    {
        this.error = error;
    }

    public String getNewLogin() {
        return newLastName;
    }

    public String getNewLastName()
    {
        return newLastName;
    }

    public void setNewLastName(String newLastName)
    {
        this.newLastName = newLastName;
    }

    public void setNewLogin(String newLogin) {
        this.newLastName = newLogin;
    }

    public String getError()
    {
        return error;
    }



}
