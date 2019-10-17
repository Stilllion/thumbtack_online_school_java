package net.thumbtack.school.hiring.response.user;

public class ChangePasswordDtoResponse
{
    String newPassword;
    String error = null;

    public ChangePasswordDtoResponse(String newPassword)
    {
        this.newPassword = newPassword;
    }

    public ChangePasswordDtoResponse()
    {
        newPassword = null;
    }

    public void setError(String error)
    {
        this.error = error;
    }

    public String getError()
    {
        return error;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
