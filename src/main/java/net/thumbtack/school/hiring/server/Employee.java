package net.thumbtack.school.hiring.server;

public class Employee extends User
{
    boolean isActive;

    public Employee(String email, String firstName, String lastName,
                    String login, String password)
    {
        super(email, login, password, firstName, lastName);
        isActive = true;
    }

    public Employee(){}

    public boolean setActive(boolean isActive)
    {
        this.isActive = isActive;
        return isActive;
    }

    public boolean isActive()
    {
        return  isActive;
    }
}
