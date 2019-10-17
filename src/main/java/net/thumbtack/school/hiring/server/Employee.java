package net.thumbtack.school.hiring.server;

public class Employee extends User
{
    public Employee(String email, String firstName, String lastName,
                    String login, String password)
    {
        super(email, login, password, firstName, lastName);
    }
}
