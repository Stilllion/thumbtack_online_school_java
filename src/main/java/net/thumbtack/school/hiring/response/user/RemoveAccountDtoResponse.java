package net.thumbtack.school.hiring.response.user;

public class RemoveAccountDtoResponse
{
    String result;
    String error;

    public RemoveAccountDtoResponse()
    {
        result = null;
        error = null;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getError()
    {
        return error;
    }

    public void setError(String error)
    {
        this.error = error;
    }

}
