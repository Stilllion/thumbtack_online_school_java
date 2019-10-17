package net.thumbtack.school.hiring.request.employer;

import net.thumbtack.school.hiring.exception.ServerErrorCode;
import net.thumbtack.school.hiring.exception.ServerException;
import net.thumbtack.school.hiring.server.Skill;

import java.util.List;
import java.util.UUID;

public class AddVacancyDtoRequest
{
    String name;
    // предполагаемый оклад
    int salary;
    // список требований к работникам
    List<Skill> requirements;

    String employerEmail;
    String companyName;
    String employerFirstName;
    String employerLastName;

    UUID token;

    public AddVacancyDtoRequest(UUID token, String name, int salary, List<Skill> requirements)
    {
        this.name = name;
        this.salary = salary;
        this.requirements = requirements;
        this.token = token;
    }

    public boolean validate() throws ServerException
    {
        if(name.equals("") || name == null){
            throw new ServerException(ServerErrorCode.WRONG_VACANCY_NAME);
        }

        if(salary <= 0){
            throw new ServerException(ServerErrorCode.WRONG_VACANCY_SALARY);
        }

        for(Skill s : requirements){
            if(s.getName().equals("") || s.getName() == null){
                throw new ServerException(ServerErrorCode.WRONG_SKILL_NAME);
            }

            if(s.getLevel() < 1 || s.getLevel() > 5){
                throw new ServerException(ServerErrorCode.WRONG_SKILL_LEVEL);
            }
        }

        return true;
    }

    public String getName() {
        return name;
    }

    public int getSalary() {
        return salary;
    }

    public List<Skill> getRequirements() {
        return requirements;
    }

    public String getEmployerEmail() {
        return employerEmail;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getEmployerFirstName() {
        return employerFirstName;
    }

    public String getEmployerLastName() {
        return employerLastName;
    }

    public UUID getToken() {
        return token;
    }
}
