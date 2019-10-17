package net.thumbtack.school.hiring.response.employer;

import net.thumbtack.school.hiring.server.Vacancy;

public class RemoveVacancyDtoResponse
{
    Vacancy removedVacancy;
    String error;

    public RemoveVacancyDtoResponse(Vacancy removedVacancy)
    {
        this.removedVacancy = removedVacancy;
    }

    public Vacancy getRemovedVacancy()
    {
        return removedVacancy;
    }
}
