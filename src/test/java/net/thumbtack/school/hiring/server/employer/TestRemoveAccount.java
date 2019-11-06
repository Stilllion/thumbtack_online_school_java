package net.thumbtack.school.hiring.server.employer;

import com.google.gson.Gson;
import net.thumbtack.school.hiring.request.employee.AddSkillDtoRequest;
import net.thumbtack.school.hiring.request.employee.RegisterEmployeeDtoRequest;
import net.thumbtack.school.hiring.request.employer.AddVacancyDtoRequest;
import net.thumbtack.school.hiring.request.employer.RegisterEmployerDtoRequest;
import net.thumbtack.school.hiring.request.user.RemoveAccountDtoRequest;
import net.thumbtack.school.hiring.response.employee.AddSkillDtoResponse;
import net.thumbtack.school.hiring.response.employee.RegisterEmployeeDtoResponse;
import net.thumbtack.school.hiring.response.employer.AddVacancyDtoResponse;
import net.thumbtack.school.hiring.response.employer.RegisterEmployerDtoResponse;
import net.thumbtack.school.hiring.response.user.RemoveAccountDtoResponse;
import net.thumbtack.school.hiring.server.Server;
import net.thumbtack.school.hiring.server.Skill;
import net.thumbtack.school.hiring.server.TestDao;
import net.thumbtack.school.hiring.server.Vacancy;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestRemoveAccount
{
    Server s = new Server();
    Gson gson = new Gson();
    UUID employerToken;
    TestDao testDao = new TestDao();

    public void registerEmployer()
    {
        RegisterEmployerDtoRequest regReq = new RegisterEmployerDtoRequest("Thekla Inc.", "San Francisco", "blow@jon.com",
                "Jonathan", "Blow", "jb", "qwerty123");

        RegisterEmployerDtoResponse result = gson.fromJson(s.registerEmployer(gson.toJson(regReq)), RegisterEmployerDtoResponse.class);

        employerToken = result.getToken();

        addVacancy();
    }


    public void addVacancy()
    {
        Skill skill = new Skill("Java", 5, true);
        Skill skill2 = new Skill("English", 3, false);

        List<Skill> requiredSkills = new ArrayList<>();
        requiredSkills.add(skill);
        requiredSkills.add(skill2);

        AddVacancyDtoRequest addVacReq = new AddVacancyDtoRequest(employerToken, "Java programmer", 10000, requiredSkills);
        AddVacancyDtoResponse addVacResp = gson.fromJson(s.addVacancy(gson.toJson(addVacReq)), AddVacancyDtoResponse.class);
    }

    @Test
    public void testRemoveAccount()
    {
        RemoveAccountDtoRequest request = new RemoveAccountDtoRequest(employerToken);
        RemoveAccountDtoResponse response = gson.fromJson(s.removeEmployer(gson.toJson(request)), RemoveAccountDtoResponse.class);


        assertTrue(testDao.getRegisteredEmployers().isEmpty());
        assertTrue(testDao.getVacanciesAll().isEmpty());

        testDao.resetDatabase();
    }
}
