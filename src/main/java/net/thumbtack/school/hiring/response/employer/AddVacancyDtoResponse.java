package net.thumbtack.school.hiring.response.employer;

import net.thumbtack.school.hiring.server.Vacancy;

import java.util.List;

public class AddVacancyDtoResponse
{
    List<Vacancy> addedVacancies;
    String error;

    public AddVacancyDtoResponse()
    {
        error = null;
        addedVacancies = null;
    }

    public List<Vacancy> getAddedVacancies()
    {
        return addedVacancies;
    }

    public void setAddedVacancies(List<Vacancy> addedVacancies) {
        this.addedVacancies = addedVacancies;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
        addedVacancies = null;
    }
}
