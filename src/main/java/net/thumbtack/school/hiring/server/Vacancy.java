package net.thumbtack.school.hiring.server;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

public class Vacancy implements Serializable
{
    // Зарегистрированный работодатель может добавить свои вакансии. В вакансии указываются
    // название должности
    String name;
    // предполагаемый оклад
    int salary;
    // список требований к работникам
    List<Skill> requirements;

    boolean isActive;

    String employerEmail;
    String companyName;
    String employerFirstName;
    String employerLastName;

    public Vacancy(String name, int salary, List<Skill> requirements)
    {
        this.name = name;
        this.salary = salary;
        this.requirements = requirements;

        isActive = true;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public List<Skill> getRequirements() {
        return requirements;
    }

    public void setRequirements(List<Skill> requirements) {
        this.requirements = requirements;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public void setEmployerEmail(String employerEmail) {
        this.employerEmail = employerEmail;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public void setEmployerFirstName(String employerFirstName) {
        this.employerFirstName = employerFirstName;
    }

    public void setEmployerLastName(String employerLastName) {
        this.employerLastName = employerLastName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vacancy vacancy = (Vacancy) o;
        return salary == vacancy.salary &&
                isActive == vacancy.isActive &&
                Objects.equals(name, vacancy.name) &&
                Objects.equals(requirements, vacancy.requirements) &&
                Objects.equals(employerEmail, vacancy.employerEmail) &&
                Objects.equals(companyName, vacancy.companyName) &&
                Objects.equals(employerFirstName, vacancy.employerFirstName) &&
                Objects.equals(employerLastName, vacancy.employerLastName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, salary, requirements, isActive, employerEmail, companyName, employerFirstName, employerLastName);
    }
}
