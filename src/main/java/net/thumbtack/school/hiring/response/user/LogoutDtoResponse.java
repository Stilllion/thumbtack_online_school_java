package net.thumbtack.school.hiring.response.user;

public class LogoutDtoResponse
{
    String error;
    String result;

    public LogoutDtoResponse()
    {
        error = null;
        result = "You are now offline. Your previous token is no longer valid";
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        result = null;
        this.error = error;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
