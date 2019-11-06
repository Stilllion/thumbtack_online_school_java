package net.thumbtack.school.hiring.request.user;

import net.thumbtack.school.hiring.exception.ServerErrorCode;
import net.thumbtack.school.hiring.exception.ServerException;

import java.util.UUID;

public class ChangeAccountDataDtoRequest
{
    UUID token;
    String data;

    public ChangeAccountDataDtoRequest(UUID token, String data)
    {
        this.token = token;
        this.data = data;
    }

    public void validate() throws ServerException
    {
        if(token == null){
            throw new ServerException(ServerErrorCode.WRONG_TOKEN);
        }

        if(data == null || data.equals("")){
            throw new ServerException(ServerErrorCode.WRONG_DATA);
        }
    }

    public UUID getToken() {
        return token;
    }

    public String getData() {
        return data;
    }
}
