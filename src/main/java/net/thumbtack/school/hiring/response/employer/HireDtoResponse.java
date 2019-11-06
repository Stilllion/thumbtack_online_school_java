package net.thumbtack.school.hiring.response.employer;

public class HireDtoResponse
{
    String result;
    String error;

    public HireDtoResponse()
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

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
