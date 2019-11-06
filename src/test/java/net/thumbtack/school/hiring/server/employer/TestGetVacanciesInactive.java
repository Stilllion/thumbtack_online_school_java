package net.thumbtack.school.hiring.server.employer;

import com.google.gson.Gson;
import net.thumbtack.school.hiring.request.employer.*;
import net.thumbtack.school.hiring.response.GetVacanciesDtoResponse;
import net.thumbtack.school.hiring.response.employer.RegisterEmployerDtoResponse;
import net.thumbtack.school.hiring.server.Server;
import net.thumbtack.school.hiring.server.Skill;
import net.thumbtack.school.hiring.server.TestDao;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestGetVacanciesInactive
{
    Server s = new Server();
    Gson gson = new Gson();
    TestDao testDao = new TestDao();

    public UUID registerEmployer()
    {
        RegisterEmployerDtoRequest regReq = new RegisterEmployerDtoRequest("Thekla Inc.", "San Francisco", "blow@jon.com",
                "Jonathan", "Blow", "jb", "qwerty123");

        RegisterEmployerDtoResponse result = gson.fromJson(s.registerEmployer(gson.toJson(regReq)), RegisterEmployerDtoResponse.class);

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
        GetVacanciesDtoRequest getInactiveVacRequest = new GetVacanciesDtoRequest(token);
        GetVacanciesDtoResponse getInactiveVacResponse = gson.fromJson(s.getEmployerVacanciesInactive(gson.toJson(getInactiveVacRequest)), GetVacanciesDtoResponse.class);

        assertEquals(0, getInactiveVacResponse.getVacancies().size());

        SetVacancyStatusDtoRequest setInactiveReq = new SetVacancyStatusDtoRequest(token, "FORTAN programmer", false);
        s.setVacancyStatus(gson.toJson(setInactiveReq));

        setInactiveReq = new SetVacancyStatusDtoRequest(token, "Java programmer", false);
        s.setVacancyStatus(gson.toJson(setInactiveReq));

        getInactiveVacRequest = new GetVacanciesDtoRequest(token);
        getInactiveVacResponse = gson.fromJson(s.getEmployerVacanciesInactive(gson.toJson(getInactiveVacRequest)), GetVacanciesDtoResponse.class);

        assertEquals(2, getInactiveVacResponse.getVacancies().size());

        testDao.resetDatabase();
    }
}
