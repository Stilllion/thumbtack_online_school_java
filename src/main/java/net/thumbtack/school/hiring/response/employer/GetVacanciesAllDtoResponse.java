package net.thumbtack.school.hiring.response.employer;

import net.thumbtack.school.hiring.response.employee.GetVacanciesAnyLevelDtoResponse;
import net.thumbtack.school.hiring.server.Vacancy;

import java.util.List;

public class GetVacanciesAllDtoResponse
{
    List<Vacancy> allVacancies;

    String error;

    public GetVacanciesAllDtoResponse()
    {
        error = null;
        allVacancies = null;
    }

    public List<Vacancy> getAllVacancies() {
        return allVacancies;
    }

    public void setAllVacancies(List<Vacancy> allVacancies) {
        this.allVacancies = allVacancies;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
