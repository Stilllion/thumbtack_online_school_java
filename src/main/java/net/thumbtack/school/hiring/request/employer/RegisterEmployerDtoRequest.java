package net.thumbtack.school.hiring.request.employer;

import net.thumbtack.school.hiring.exception.ServerErrorCode;
import net.thumbtack.school.hiring.exception.ServerException;

public class RegisterEmployerDtoRequest
{
    private String companyName;
    private String address;
    private String email;
    private String firstName;
    private String lastName;
    private String login;
    private String password;

    public RegisterEmployerDtoRequest(String companyName, String address, String email, String firstName, String lastName,
                                      String login, String password)
    {
        this.companyName = companyName;
        this.address = address;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.login = login;
        this.password = password;
    }

    public boolean validate() throws ServerException
    {
        if(companyName.equals("") || companyName == null){
            throw new ServerException(ServerErrorCode.WRONG_COMPNAY_NAME);
        }

        if(address.equals("") || address == null){
            throw new ServerException(ServerErrorCode.WRONG_ADRESS);
        }

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

        return true;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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
