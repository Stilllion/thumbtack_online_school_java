package net.thumbtack.school.hiring.server.employer;

import com.google.gson.Gson;
import net.thumbtack.school.hiring.exception.ServerException;
import net.thumbtack.school.hiring.request.employee.RegisterEmployeeDtoRequest;
import net.thumbtack.school.hiring.request.employer.AddVacancyDtoRequest;
import net.thumbtack.school.hiring.request.employer.RegisterEmployerDtoRequest;
import net.thumbtack.school.hiring.response.employer.AddVacancyDtoResponse;
import net.thumbtack.school.hiring.response.employee.RegisterEmployeeDtoResponse;
import net.thumbtack.school.hiring.response.employer.RegisterEmployerDtoResponse;
import net.thumbtack.school.hiring.server.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;

public class TestAddVacancy
{
    Server s = new Server();
    Gson gson = new Gson();
    TestDao testDao = new TestDao();

    public UUID registerEmployer()
    {
        RegisterEmployerDtoRequest regReq = new RegisterEmployerDtoRequest("Thekla Inc.", "San Francisco", "blow@jon.com",
                "Jonathan", "Blow", "jb", "qwerty123");

        RegisterEmployerDtoResponse result = gson.fromJson(s.registerEmployer(gson.toJson(regReq)), RegisterEmployerDtoResponse.class);
        return result.getToken();
    }

    @Test
    public void testAddVacancy()
    {
        UUID token = registerEmployer();

        Skill skill = new Skill("Java", 5, true);
        Skill skill2 = new Skill("English", 3, false);

        List<Skill> requiredSkills = new ArrayList<>();
        requiredSkills.add(skill);
        requiredSkills.add(skill2);

        AddVacancyDtoRequest addVacReq = new AddVacancyDtoRequest(token, "Java programmer", 10000, requiredSkills);
        AddVacancyDtoResponse addVacResp = gson.fromJson(s.addVacancy(gson.toJson(addVacReq)), AddVacancyDtoResponse.class);
		
        List<Vacancy> addedVacancies = addVacResp.getAddedVacancies();

        assertEquals(1, addedVacancies.size());
        assertEquals("Java programmer", addedVacancies.get(0).getName());
        assertEquals(10000, addedVacancies.get(0).getSalary());
        assertEquals(requiredSkills, addedVacancies.get(0).getRequirements());

        testDao.resetDatabase();
    }
}
