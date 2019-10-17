package net.thumbtack.school.hiring.response.user;

import net.thumbtack.school.hiring.server.User;

public class RemoveAccountDtoResponse
{
    User removedAccount;
    String error;

    public RemoveAccountDtoResponse()
    {
        error = null;
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
