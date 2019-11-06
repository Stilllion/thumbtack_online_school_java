package net.thumbtack.school.hiring.response.user;

public class ChangeAccountDataDtoResponse
{
    String data;
    String error;

    public ChangeAccountDataDtoResponse ()
    {
        this.data = null;
        error = null;
    }

    public void setError(String error)
    {
        this.error = error;
    }

    public void setData(String data)
    {
        this.data = data;
    }

    public String getError()
    {
        return error;
    }

    public String getData()
    {
        return data;
    }


}
