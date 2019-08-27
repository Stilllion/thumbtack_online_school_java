package net.thumbtack.school.colors;

public enum ColorErrorCode
{
    WRONG_COLOR_STRING("Wrong color"), NULL_COLOR("String is null");
    String errorString;

    ColorErrorCode(String errorString)
    {
        this.errorString = errorString;
    }

    String getErrorString()
    {
        return errorString;
    }

}
