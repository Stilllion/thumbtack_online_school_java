package net.thumbtack.school.hiring.server.employer;

import com.google.gson.Gson;
import net.thumbtack.school.hiring.request.employer.AddVacancyDtoRequest;
import net.thumbtack.school.hiring.request.employer.RegisterEmployerDtoRequest;
import net.thumbtack.school.hiring.request.employer.SetVacancyStatusDtoRequest;
import net.thumbtack.school.hiring.response.employer.AddVacancyDtoResponse;
import net.thumbtack.school.hiring.response.employer.RegisterEmployerDtoResponse;
import net.thumbtack.school.hiring.response.employer.SetVacancyStatusDtoResponse;
import net.thumbtack.school.hiring.server.Server;
import net.thumbtack.school.hiring.server.Skill;
import net.thumbtack.school.hiring.server.TestDao;
import net.thumbtack.school.hiring.server.Vacancy;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.BrokenBarrierException;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class TestSetVacancyStatus
{
    Server s = new Server();
    Gson gson = new Gson();
    TestDao testDao = new TestDao();
    UUID employerToken;

    public void registerEmployer()
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
        addVacReq = new AddVacancyDtoRequest(employerToken, "Java programmer", 32768, new ArrayList<Skill>());
        s.addVacancy(gson.toJson(addVacReq));
    }

    @Test
    public void testSetVacancyStatus()
    {
        registerEmployer();

        SetVacancyStatusDtoRequest request = new SetVacancyStatusDtoRequest(employerToken, "Java programmer", false);
        SetVacancyStatusDtoResponse response = gson.fromJson(s.setVacancyStatus(gson.toJson(request)), SetVacancyStatusDtoResponse.class);

        assertEquals(false, response.isActive());

        request = new SetVacancyStatusDtoRequest(employerToken, "Java programmer", true);
        response = gson.fromJson(s.setVacancyStatus(gson.toJson(request)), SetVacancyStatusDtoResponse.class);

        assertEquals(true, response.isActive());

        testDao.resetDatabase();
    }
}
