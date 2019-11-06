package net.thumbtack.school.hiring.response;

import net.thumbtack.school.hiring.server.Vacancy;

import java.util.ArrayList;
import java.util.List;

public class GetVacanciesDtoResponse
{
    List<Vacancy> vacancies;

    String error;

    public GetVacanciesDtoResponse()
    {
        error = null;
        vacancies = null;
    }

    public List<Vacancy> getVacancies()
    {
        if(vacancies == null){
            vacancies = new ArrayList<>();
        }
        return vacancies;
    }

    public void setVacancies(List<Vacancy> vacancies) {
        this.vacancies = vacancies;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}