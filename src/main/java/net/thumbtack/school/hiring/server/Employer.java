package net.thumbtack.school.hiring.server;

import java.util.Objects;

public class Employer extends User
{
    String companyName;
    String adress;

    public Employer(String companyName, String adress, String email, String firstName, String lastName, String login, String password)
    {
        super(email, login, password, firstName, lastName);
        this.companyName = companyName;
        this.adress = adress;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Employer employer = (Employer) o;
        return Objects.equals(companyName, employer.companyName) &&
                Objects.equals(adress, employer.adress);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), companyName, adress);
    }
}
