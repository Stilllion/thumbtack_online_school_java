package net.thumbtack.school.hiring.response.employer;

import net.thumbtack.school.hiring.server.Vacancy;

import java.util.List;

public class GetVacanciesInactiveDtoResponse
{
    List<Vacancy> vacanciesInactive;

    String error;

    public GetVacanciesInactiveDtoResponse()
    {
        error = null;
        vacanciesInactive = null;
    }

    public List<Vacancy> getVacanciesInactive() {
        return vacanciesInactive;
    }

    public void setVacanciesInactive(List<Vacancy> vacanciesInactive) {
        this.vacanciesInactive = vacanciesInactive;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
