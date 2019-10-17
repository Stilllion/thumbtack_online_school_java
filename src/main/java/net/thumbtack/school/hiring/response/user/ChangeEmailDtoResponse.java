package net.thumbtack.school.hiring.response.user;

public class ChangeEmailDtoResponse
{
    String newEmail;
    String error = null;

    public ChangeEmailDtoResponse(String newLogin)
    {
        this.newEmail = newLogin;
    }

    public ChangeEmailDtoResponse()
    {
        newEmail = null;
    }

    public void setError(String error)
    {
        this.error = error;
    }

    public String getError()
    {
        return error;
    }

    public String getNewEmail() {
        return newEmail;
    }

    public void setNewEmail(String newEmail) {
        this.newEmail = newEmail;
    }
}
