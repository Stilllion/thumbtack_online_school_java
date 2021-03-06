package net.thumbtack.school.hiring.server;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

public class User implements Serializable
{
    String email;
    String firstName;
    String lastName;
    String middleName;
    String login;
    String password;

    UUID token;
    boolean isOnline;

    public User(){}

    public User(String email, String login, String password, String firstName, String lastName)
    {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.login = login;
        this.password = password;
    }

    public User(String email, String login, String password, String firstName, String lastName, String middleName)
    {
        this(email, login, password, firstName, lastName);
        this.middleName = middleName;
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

    public String getMiddleName()
    {
        return middleName;
    }

    public void setMiddleName(String middleName)
    {
        this.middleName = middleName;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isOnline() {
        return isOnline;
    }

    public void setOnline(boolean online) {
        isOnline = online;
    }

    public void setToken(UUID token)
    {
        this.token = token;
    }

    public UUID getToken()
    {
        return token;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return isOnline == user.isOnline &&
                Objects.equals(email, user.email) &&
                Objects.equals(firstName, user.firstName) &&
                Objects.equals(lastName, user.lastName) &&
                Objects.equals(middleName, user.middleName) &&
                Objects.equals(login, user.login) &&
                Objects.equals(password, user.password) &&
                Objects.equals(token, user.token);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, firstName, lastName, middleName, login, password, token, isOnline);
    }
}
