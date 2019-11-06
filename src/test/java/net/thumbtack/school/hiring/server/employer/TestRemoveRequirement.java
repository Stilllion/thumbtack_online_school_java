package net.thumbtack.school.hiring.server.employer;

import com.google.gson.Gson;
import net.thumbtack.school.hiring.request.employer.AddVacancyDtoRequest;
import net.thumbtack.school.hiring.request.employer.RegisterEmployerDtoRequest;
import net.thumbtack.school.hiring.request.employer.RemoveRequirementDtoRequest;
import net.thumbtack.school.hiring.response.employer.RegisterEmployerDtoResponse;
import net.thumbtack.school.hiring.response.employer.UpdateVacancyDtoResponse;
import net.thumbtack.school.hiring.server.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestRemoveRequirement
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
        Skill skill2 = new Skill("English", 3, false);

        List<Skill> requiredSkills = new ArrayList<>();
        requiredSkills.add(skill);
        requiredSkills.add(skill2);

        AddVacancyDtoRequest addVacReq = new AddVacancyDtoRequest(token, "Java programmer", 10000, requiredSkills);
        s.addVacancy(gson.toJson(addVacReq));

        RemoveRequirementDtoRequest removeReqRequest = new RemoveRequirementDtoRequest(token, "Java programmer", "English");
        UpdateVacancyDtoResponse removeReqResponse = gson.fromJson(s.removeRequirement(gson.toJson(removeReqRequest)), UpdateVacancyDtoResponse.class);

        assertEquals(removeReqResponse.getUpdatedVacancy().getRequirements().size(), 1);

        testDao.resetDatabase();
    }
}
