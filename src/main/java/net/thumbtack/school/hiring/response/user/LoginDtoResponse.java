package net.thumbtack.school.hiring.response.user;

import java.util.UUID;

public class LoginDtoResponse
{
    UUID token;
    String error;

    public LoginDtoResponse()
    {
        token = UUID.randomUUID();
        error = null;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
        token = null;
    }

    public UUID getToken()
    {
        return token;
    }
}