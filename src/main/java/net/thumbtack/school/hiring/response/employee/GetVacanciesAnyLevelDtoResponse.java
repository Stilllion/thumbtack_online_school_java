package net.thumbtack.school.hiring.response.employee;

import net.thumbtack.school.hiring.server.Vacancy;

import java.util.ArrayList;
import java.util.List;

public class GetVacanciesAnyLevelDtoResponse
{
    List<Vacancy> vacancies;

    String error;

    public GetVacanciesAnyLevelDtoResponse()
    {
        vacancies = null;
        error = null;
    }

    public List<Vacancy> getVacancies()
    {
        if(vacancies == null){
            vacancies = new ArrayList<>();
        }

        return vacancies;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

}
