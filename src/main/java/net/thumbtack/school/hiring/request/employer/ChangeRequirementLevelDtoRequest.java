package net.thumbtack.school.hiring.request.employer;

import net.thumbtack.school.hiring.exception.ServerErrorCode;
import net.thumbtack.school.hiring.exception.ServerException;
import net.thumbtack.school.hiring.server.Skill;

import java.util.UUID;

public class ChangeRequirementLevelDtoRequest
{
    String vacancyName;
    String requirementName;
    int level;
    UUID token;

    public ChangeRequirementLevelDtoRequest(UUID token, String vacancyName, int level)
    {
        this.token = token;
        this.vacancyName = vacancyName;
        this.level = level;
    }

    public String getVacancyName() {
        return vacancyName;
    }

    public void setVacancyName(String vacancyName) {
        this.vacancyName = vacancyName;
    }

    public int getLevel()
    {
        return level;
    }

    public String getRequirementName() {
        return requirementName;
    }

    public UUID getToken() {
        return token;
    }

    public void validate() throws ServerException
    {
        if(vacancyName == null || vacancyName.equals("")){
            throw new ServerException(ServerErrorCode.WRONG_VACANCY_NAME);
        }

        if(level < 1 || level > 5){
            throw new ServerException(ServerErrorCode.WRONG_SKILL_LEVEL);
        }

        if(token == null){
            throw new ServerException(ServerErrorCode.WRONG_TOKEN);
        }
    }
}
