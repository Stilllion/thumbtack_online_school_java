package net.thumbtack.school.hiring.request.employer;

import net.thumbtack.school.hiring.exception.ServerErrorCode;
import net.thumbtack.school.hiring.exception.ServerException;
import net.thumbtack.school.hiring.server.Skill;

import java.util.UUID;

public class AddRequirementDtoRequest
{
    String vacancyName;
    Skill newSkill;
    UUID token;

    public AddRequirementDtoRequest(UUID token, String vacancyName, Skill newSkill)
    {
        this.token = token;
        this.vacancyName = vacancyName;
        this.newSkill = newSkill;
    }

    public String getVacancyName() {
        return vacancyName;
    }

    public void setVacancyName(String vacancyName) {
        this.vacancyName = vacancyName;
    }

    public Skill getNewSkill() {
        return newSkill;
    }

    public void setNewSkill(Skill newSkill) {
        this.newSkill = newSkill;
    }

    public UUID getToken() {
        return token;
    }

    public void validate() throws ServerException
    {
        if(vacancyName == null || vacancyName.equals("")){
            throw new ServerException(ServerErrorCode.WRONG_VACANCY_NAME);
        }

        if(newSkill.getName() == null || newSkill.getName().equals("")){
            throw new ServerException(ServerErrorCode.WRONG_SKILL_NAME);
        }

        if(newSkill.getLevel() < 1 || newSkill.getLevel() > 5){
            throw new ServerException(ServerErrorCode.WRONG_SKILL_LEVEL);
        }

        if(token == null){
            throw new ServerException(ServerErrorCode.WRONG_TOKEN);
        }
    }
}
