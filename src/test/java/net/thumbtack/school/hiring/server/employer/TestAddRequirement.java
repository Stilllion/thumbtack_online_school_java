package net.thumbtack.school.hiring.server.employer;

import com.google.gson.Gson;
import net.thumbtack.school.hiring.request.employer.AddRequirementDtoRequest;
import net.thumbtack.school.hiring.request.employer.AddVacancyDtoRequest;
import net.thumbtack.school.hiring.request.employer.RegisterEmployerDtoRequest;
import net.thumbtack.school.hiring.response.employer.RegisterEmployerDtoResponse;
import net.thumbtack.school.hiring.response.employer.UpdateVacancyDtoResponse;
import net.thumbtack.school.hiring.server.Employer;
import net.thumbtack.school.hiring.server.Server;
import net.thumbtack.school.hiring.server.Skill;
import net.thumbtack.school.hiring.server.TestDao;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestAddRequirement
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
        return result.getToken();
    }

    @Test
    public void testRemoveRequirement()
    {
        UUID token = registerEmployer();

        Skill skill = new Skill("Java", 5, true);

        List<Skill> requiredSkills = new ArrayList<>();
        requiredSkills.add(skill);

        AddVacancyDtoRequest addVacReq = new AddVacancyDtoRequest(token, "Java programmer", 10000, requiredSkills);
        s.addVacancy(gson.toJson(addVacReq));

        Skill newRequirement = new Skill("English", 3, false);

        AddRequirementDtoRequest addReqRequest = new AddRequirementDtoRequest(token, "Java programmer", newRequirement);
        UpdateVacancyDtoResponse addReqResponse = gson.fromJson(s.addRequirement(gson.toJson(addReqRequest)), UpdateVacancyDtoResponse.class);

        assertEquals(addReqResponse.getUpdatedVacancy().getRequirements().size(), 2);
        assertEquals(addReqResponse.getUpdatedVacancy().getRequirements().get(0).getName(), "Java");
        assertEquals(addReqResponse.getUpdatedVacancy().getRequirements().get(1).getName(), "English");
        assertEquals(addReqResponse.getUpdatedVacancy().getRequirements().get(1).getLevel(), 3);

        testDao.resetDatabase();
    }
}
