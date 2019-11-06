package net.thumbtack.school.hiring.response.employee;

public class UpdateEmployeeProfileDtoResponse
{
    String error;
    String result;

    public UpdateEmployeeProfileDtoResponse()
    {
        error = null;
        result = null;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}


