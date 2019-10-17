package net.thumbtack.school.hiring.response.employer;

public class RemoveRequirementDtoResponse
{
    String result;
    String error;

    public RemoveRequirementDtoResponse()
    {
        error = null;
        result = null;
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
