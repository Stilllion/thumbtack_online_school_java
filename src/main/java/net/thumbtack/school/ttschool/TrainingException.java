package net.thumbtack.school.ttschool;

public class TrainingException extends Exception
{
    TrainingErrorCode errorCode;

    public TrainingException(TrainingErrorCode errorCode)
    {
        this.errorCode = errorCode;
    }

    public TrainingErrorCode getErrorCode()
    {
        return errorCode;
    }
}
