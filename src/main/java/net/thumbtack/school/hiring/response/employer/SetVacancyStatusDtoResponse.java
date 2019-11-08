package net.thumbtack.school.hiring.response.employer;

public class SetVacancyStatusDtoResponse
{
    String vacancyName;
    boolean isActive;
    String error;

    public SetVacancyStatusDtoResponse()
    {
        error = null;
        vacancyName = null;
    }

    public String getVacancyName() {
        return vacancyName;
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

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
