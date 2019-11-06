package net.thumbtack.school.hiring.server.employer;

import com.google.gson.Gson;
import net.thumbtack.school.hiring.request.employee.GetVacanciesDtoRequest;
import net.thumbtack.school.hiring.request.employer.AddVacancyDtoRequest;
import net.thumbtack.school.hiring.request.employer.RegisterEmployerDtoRequest;
import net.thumbtack.school.hiring.response.GetVacanciesDtoResponse;
import net.thumbtack.school.hiring.response.employer.AddVacancyDtoResponse;
import net.thumbtack.school.hiring.response.employer.RegisterEmployerDtoResponse;
import net.thumbtack.school.hiring.server.Server;
import net.thumbtack.school.hiring.server.Skill;
import net.thumbtack.school.hiring.server.TestDao;
import net.thumbtack.school.hiring.server.Vacancy;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TestGetAllEmployerVacancies
{
    Server s = new Server();
    Gson gson = new Gson();
    TestDao testDao = new TestDao();
    UUID employerToken;

    public List<Vacancy> addVacancies()
    {
        RegisterEmployerDtoRequest regReq;
        RegisterEmployerDtoResponse regResp;

        AddVacancyDtoRequest addVacReq;
        AddVacancyDtoResponse addVacResp;

        List<Vacancy> availableVacancies = new ArrayList<>();

        // Register Employer
        regReq = new RegisterEmployerDtoRequest("Thekla Inc.", "San Francisco", "blow@jon.com",
                "Jonathan", "Blow", "jb", "qwerty123");

        regResp = gson.fromJson(s.registerEmployer(gson.toJson(regReq)), RegisterEmployerDtoResponse.class);
        employerToken = regResp.getToken();

        // Add vacancies
        addVacReq = new AddVacancyDtoRequest(employerToken, "Java middle", 32768, new ArrayList<Skill>());
        s.addVacancy(gson.toJson(addVacReq));

        addVacReq = new AddVacancyDtoRequest(employerToken, "Jai programmer", 32768, new ArrayList<Skill>());
        s.addVacancy(gson.toJson(addVacReq));

        addVacReq = new AddVacancyDtoRequest(employerToken, "FullStack Dev", 32768, new ArrayList<Skill>());
        s.addVacancy(gson.toJson(addVacReq));

        addVacReq = new AddVacancyDtoRequest(employerToken, "Java junior", 1023, new ArrayList<Skill>());
        addVacResp = gson.fromJson(s.addVacancy(gson.toJson(addVacReq)), AddVacancyDtoResponse.class);

        availableVacancies.addAll(addVacResp.getAddedVacancies());

        return availableVacancies;
    }

    @Test
    public void getAllEmployerVacancies()
    {
        List<Vacancy> addedVacancies = addVacancies();

        GetVacanciesDtoRequest requestAllVacancies = new GetVacanciesDtoRequest(employerToken);
        GetVacanciesDtoResponse responseAllVacancies = gson.fromJson(s.getEmployerVacanciesAll(gson.toJson(requestAllVacancies)),
                GetVacanciesDtoResponse.class);

        assertEquals(4, responseAllVacancies.getVacancies().size());
        assertEquals(addedVacancies, responseAllVacancies.getVacancies());

        testDao.resetDatabase();
    }
}
