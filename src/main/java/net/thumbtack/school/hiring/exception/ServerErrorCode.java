package net.thumbtack.school.hiring.exception;

public enum ServerErrorCode
{
    WRONG_FIRSTNAME("First name String is empty or null"),
    WRONG_LASTNAME("Last name String is empty or null"),
    WRONG_COMPNAY_NAME("Company name String is empty or null"),
    WRONG_LOGIN("Login String is empty or null"),
    WRONG_PASSWORD("Password String is empty or null"),
    WRONG_EMAIL("Email String is empty or null"),
    WRONG_ADRESS("Adress String is empty or null"),
    DUPLICATE_EMPLOYEE("Specified employee already exists"),
    DUPLICATE_EMPLOYER("Specified employer already exists"),
    WRONG_TOKEN("Token is null"),
    EMPLOYER_NOT_FOUND("Specified employer could not be found"),
    DUPLICATE_VACANCY("Specified vacancy already exists"),
    EMPLOYEE_NOT_FOUND("Specified employee could not be found"),
    WRONG_VACANCY_NAME("Vacancy name is null or empty"),
    WRONG_VACANCY_SALARY("Vacancy salary is less or equals zero"),
    WRONG_SKILL_NAME("Skill name is null or empty"),
    WRONG_SKILL_LEVEL("Skill level should be between 1 and 5"),
    TOKEN_NOT_FOUND("Specified token could not be found"),
    PASSWORD_MISMATCH("Password doesn't match"),
    USER_NOT_FOUND("You are not registered on this server"),
    VACANCY_NOT_FOUND("Specified vacancy could not be found");

    String errorString;

    ServerErrorCode(String errorString)
    {
        this.errorString = errorString;
    }

    public String getErrorString()
    {
        return errorString;
    }
}
