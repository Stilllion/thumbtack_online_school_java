package net.thumbtack.school.hiring.request.employee;

import net.thumbtack.school.hiring.exception.ServerErrorCode;
import net.thumbtack.school.hiring.exception.ServerException;

public class RegisterEmployeeDtoRequest
{
    private String email;
    private String firstName;
    private String lastName;
    private String login;
    private String password;

    public RegisterEmployeeDtoRequest(String email, String firstName, String lastName, String login, String password)
    {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.login = login;
        this.password = password;
    }

    public void validate() throws ServerException
    {
        if(email.equals("") || email == null){
            throw new ServerException(ServerErrorCode.WRONG_EMAIL);
        }

        if(firstName.equals("") || firstName == null){
            throw new ServerException(ServerErrorCode.WRONG_FIRSTNAME);
        }

        if(lastName.equals("") || lastName == null){
            throw new ServerException(ServerErrorCode.WRONG_LASTNAME);
        }

        if(login.equals("") || login == null){
            throw new ServerException(ServerErrorCode.WRONG_LOGIN);
        }

        if(password.equals("") || password == null){
            throw new ServerException(ServerErrorCode.WRONG_PASSWORD);
        }

        // Password requirements
        if(password.length() < 6){
            throw new ServerException(ServerErrorCode.PASWWORD_TOOSHORT);
        }
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }
}
