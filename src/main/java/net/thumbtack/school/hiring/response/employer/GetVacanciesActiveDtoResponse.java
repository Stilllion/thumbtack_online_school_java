package net.thumbtack.school.hiring.response.employer;

import net.thumbtack.school.hiring.server.Vacancy;

import java.util.List;

public class GetVacanciesActiveDtoResponse
{
    List<Vacancy> vacanciesActive;

    String error;

    public GetVacanciesActiveDtoResponse()
    {
        error = null;
        vacanciesActive = null;
    }

    public List<Vacancy> getVacanciesActive() {
        return vacanciesActive;
    }

    public void setVacanciesActives(List<Vacancy> allVacancies) {
        this.vacanciesActive = vacanciesActive;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
