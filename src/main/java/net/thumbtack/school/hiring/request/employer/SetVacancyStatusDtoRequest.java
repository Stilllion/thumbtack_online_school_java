package net.thumbtack.school.hiring.request.employer;

import net.thumbtack.school.hiring.exception.ServerErrorCode;
import net.thumbtack.school.hiring.exception.ServerException;

import java.util.UUID;

public class SetVacancyStatusDtoRequest
{
    UUID token;
    String vacancyName;
    boolean isActive;

    public SetVacancyStatusDtoRequest(UUID token, String vacancyName, boolean isActive)
    {
        this.token = token;
        this.isActive = isActive;
        this.vacancyName = vacancyName;
    }

    public UUID getToken() {
        return token;
    }

    public String getVacancyName() {
        return vacancyName;
    }

    public void setToken(UUID token) {
        this.token = token;
    }

    public void setVacancyName(String vacancyName) {
        this.vacancyName = vacancyName;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public void validate() throws ServerException
    {
        if(token == null){
            throw new ServerException(ServerErrorCode.WRONG_TOKEN);
        }

        if(vacancyName == null || vacancyName.equals("")){
            throw new ServerException(ServerErrorCode.WRONG_VACANCY_NAME);
        }
    }
}
