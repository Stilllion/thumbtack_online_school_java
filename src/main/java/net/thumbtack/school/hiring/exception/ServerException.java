package net.thumbtack.school.hiring.exception;

public class ServerException extends Exception
{
    ServerErrorCode errorCode;

    public ServerException(ServerErrorCode errorCode)
    {
        this.errorCode = errorCode;
    }

    public ServerErrorCode getErrorCode()
    {
        return errorCode;
    }
}
