package net.thumbtack.school.ttschool;

public enum TrainingErrorCode
{
    TRAINEE_WRONG_FIRSTNAME("First name String is empty or null"),
    TRAINEE_WRONG_LASTNAME("Last name String is empty or null"),
    TRAINEE_WRONG_RATING("Rating is less then 1 or greater then 5");

    String errorString;

    TrainingErrorCode(String errorString)
    {
        this.errorString = errorString;
    }

    String getErrorString()
    {
        return errorString;
    }
}
