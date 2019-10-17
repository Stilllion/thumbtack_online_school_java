package net.thumbtack.school.hiring.response.employer;

import net.thumbtack.school.hiring.server.Vacancy;

public class ChangeRequirementLevelDtoResponse
{
    Vacancy updatedVacancy;
    String error;

    public ChangeRequirementLevelDtoResponse()
    {
        error = null;
        updatedVacancy = null;
    }

    public Vacancy getUpdatedVacancy() {
        return updatedVacancy;
    }

    public void setUpdatedVacancy(Vacancy updatedVacancy) {
        this.updatedVacancy = updatedVacancy;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
