package net.thumbtack.school.hiring.server.employer;

import com.google.gson.Gson;
import net.thumbtack.school.hiring.request.employer.*;
import net.thumbtack.school.hiring.response.GetVacanciesDtoResponse;
import net.thumbtack.school.hiring.response.employer.RegisterEmployerDtoResponse;
import net.thumbtack.school.hiring.server.Employer;
import net.thumbtack.school.hiring.server.Server;
import net.thumbtack.school.hiring.server.Skill;
import net.thumbtack.school.hiring.server.TestDao;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestGetVacanciesActive
{
    Server s = new Server();
    Gson gson = new Gson();
    Employer e;
    TestDao testDao = new TestDao();

    public UUID registerEmployer()
    {
        RegisterEmployerDtoRequest regReq = new RegisterEmployerDtoRequest("Thekla Inc.", "San Francisco", "blow@jon.com",
                "Jonathan", "Blow", "jb", "qwerty123");

        RegisterEmployerDtoResponse result = gson.fromJson(s.registerEmployer(gson.toJson(regReq)), RegisterEmployerDtoResponse.class);

        e = new Employer(regReq.getCompanyName(), regReq.getAddress(), regReq.getEmail(), regReq.getFirstName(), regReq.getLastName(),
                regReq.getLogin(), regReq.getPassword());

        UUID token = result.getToken();

        AddVacancyDtoRequest addVacReq;
        List<Skill> requiredSkills = new ArrayList<>();

        addVacReq = new AddVacancyDtoRequest(token, "Java programmer", 10000, requiredSkills);
        s.addVacancy(gson.toJson(addVacReq));
        addVacReq = new AddVacancyDtoRequest(token, "C++ programmer", 10000, requiredSkills);
        s.addVacancy(gson.toJson(addVacReq));
        addVacReq = new AddVacancyDtoRequest(token, "JavaScript programmer", 10000, requiredSkills);
        s.addVacancy(gson.toJson(addVacReq));
        addVacReq = new AddVacancyDtoRequest(token, "FORTAN programmer", 10000, requiredSkills);
        s.addVacancy(gson.toJson(addVacReq));

        return token;
    }

    @Test
    public void testGetVacanciesActive()
    {
        UUID token = registerEmployer();
        GetVacanciesDtoRequest getActiveVacRequest = new GetVacanciesDtoRequest(token);
        GetVacanciesDtoResponse getActiveVacResponse = gson.fromJson(s.getEmployerVacanciesActive(gson.toJson(getActiveVacRequest)), GetVacanciesDtoResponse.class);

        assertEquals(4, getActiveVacResponse.getVacancies().size());

        SetVacancyStatusDtoRequest setInactiveReq = new SetVacancyStatusDtoRequest(token, "FORTAN programmer", false);
        s.setVacancyStatus(gson.toJson(setInactiveReq));

        getActiveVacRequest = new GetVacanciesDtoRequest(token);
        getActiveVacResponse = gson.fromJson(s.getEmployerVacanciesActive(gson.toJson(getActiveVacRequest)), GetVacanciesDtoResponse.class);

        assertEquals(3, getActiveVacResponse.getVacancies().size());

        testDao.resetDatabase();
    }
}
