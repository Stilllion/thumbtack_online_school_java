package net.thumbtack.school.hiring.response.employer;

import java.util.UUID;

public class RegisterEmployerDtoResponse
{
    UUID token;
    String error;
    public RegisterEmployerDtoResponse()
    {
        token = UUID.randomUUID();
        error = null;
    }

    public RegisterEmployerDtoResponse(String error)
    {
        this.error = error;
        token = null;
    }

    public UUID getToken()
    {
        return token;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
        token = null;
    }
}
