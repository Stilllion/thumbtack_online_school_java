package net.thumbtack.school.hiring.request.employer;

import net.thumbtack.school.hiring.exception.ServerErrorCode;
import net.thumbtack.school.hiring.exception.ServerException;

import java.util.UUID;

public class ChangeRequirementNameDtoRequest
{
    String vacancyName;
    String requirementName;
    String newRequirementName;
    UUID token;

    public ChangeRequirementNameDtoRequest(UUID token, String vacancyName, String requirementName, String newRequirementName)
    {
        this.token = token;
        this.vacancyName = vacancyName;
        this.requirementName = requirementName;
        this.newRequirementName = newRequirementName;
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

    public String getNewRequirementName() {
        return newRequirementName;
    }

    public void setNewRequirementName(String newRequirementName) {
        this.newRequirementName = newRequirementName;
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

        if(newRequirementName == null || newRequirementName.equals("")){
            throw new ServerException(ServerErrorCode.WRONG_SKILL_NAME);
        }

        if(token == null){
            throw new ServerException(ServerErrorCode.WRONG_TOKEN);
        }
    }
}
