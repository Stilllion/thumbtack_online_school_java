package net.thumbtack.school.ttschool;

public enum TrainingErrorCode
{
    TRAINEE_WRONG_FIRSTNAME("First name String is empty or null"),
    TRAINEE_WRONG_LASTNAME("Last name String is empty or null"),
    TRAINEE_WRONG_RATING("Rating is less then 1 or greater then 5"),
    TRAINEE_NOT_FOUND("Could not find specified trainee"),
    DUPLICATE_TRAINEE("Trainee already exists"),
    GROUP_NOT_FOUND("No such group"),
    EMPTY_TRAINEE_QUEUE("Queue is empty"),
    SCHOOL_WRONG_NAME("No such school"),
    DUPLICATE_GROUP_NAME("Group already exists"),
    GROUP_WRONG_NAME("Wrong group name"),
    GROUP_WRONG_ROOM("Wrong group room number");

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
