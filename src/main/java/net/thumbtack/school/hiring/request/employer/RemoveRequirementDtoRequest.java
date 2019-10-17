package net.thumbtack.school.hiring.request.employer;

import net.thumbtack.school.hiring.exception.ServerErrorCode;
import net.thumbtack.school.hiring.exception.ServerException;

import java.util.UUID;

public class RemoveRequirementDtoRequest
{
    String vacancyName;
    String requirementName;
    UUID token;

    public RemoveRequirementDtoRequest(UUID token, String vacancyName, String requirementName)
    {
        this.token = token;
        this.vacancyName = vacancyName;
        this.requirementName = requirementName;
    }

    public String getVacancyName() {
        return vacancyName;
    }

    public void setVacancyName(String vacancyName) {
        this.vacancyName = vacancyName;
    }

    public String getRequirementName() {
        return requirementName;
    }

    public void setRequirementName(String requirementName) {
        this.requirementName = requirementName;
    }

    public UUID getToken() {
        return token;
    }

    public void validate() throws ServerException
    {
        if(vacancyName == null || vacancyName.equals("")){
            throw new ServerException(ServerErrorCode.WRONG_VACANCY_NAME);
        }

        if(requirementName == null || requirementName.equals("")){
            throw new ServerException(ServerErrorCode.WRONG_SKILL_NAME);
        }

        if(token == null){
            throw new ServerException(ServerErrorCode.WRONG_TOKEN);
        }
    }
}
