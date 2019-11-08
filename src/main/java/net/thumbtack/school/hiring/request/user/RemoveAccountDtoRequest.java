package net.thumbtack.school.hiring.request.user;

import net.thumbtack.school.hiring.exception.ServerErrorCode;
import net.thumbtack.school.hiring.exception.ServerException;

import java.util.UUID;

public class RemoveAccountDtoRequest
{
    UUID token;

    public RemoveAccountDtoRequest(UUID token)
    {
        this.token = token;
    }

    public void validate() throws ServerException
    {
        if(token == null){
            throw new ServerException(ServerErrorCode.WRONG_TOKEN);
        }
    }

    public UUID getToken()
    {
        return token;
    }
}
