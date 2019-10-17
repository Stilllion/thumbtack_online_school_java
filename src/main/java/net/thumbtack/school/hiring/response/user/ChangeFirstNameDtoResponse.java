package net.thumbtack.school.hiring.response.user;

public class ChangeFirstNameDtoResponse
{
    String newFirstName;
    String error;

    public ChangeFirstNameDtoResponse (String newFirstName)
    {
        this.newFirstName = newFirstName;
        error = null;
    }

    public ChangeFirstNameDtoResponse ()
    {
        newFirstName = null;
    }

    public void setError(String error)
    {
        this.error = error;
    }

    public void setNewFirstName(String newFirstName)
    {
        this.newFirstName = newFirstName;
    }

    public String getError()
    {
        return error;
    }

    public String getNewFirstName()
    {
        return newFirstName;
    }


}
