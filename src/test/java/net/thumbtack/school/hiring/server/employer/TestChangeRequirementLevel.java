package net.thumbtack.school.hiring.server.employer;

import com.google.gson.Gson;
import net.thumbtack.school.hiring.request.employer.AddVacancyDtoRequest;
import net.thumbtack.school.hiring.request.employer.ChangeRequirementLevelDtoRequest;
import net.thumbtack.school.hiring.request.employer.RegisterEmployerDtoRequest;
import net.thumbtack.school.hiring.response.employer.AddVacancyDtoResponse;
import net.thumbtack.school.hiring.response.employer.RegisterEmployerDtoResponse;
import net.thumbtack.school.hiring.response.employer.UpdateVacancyDtoResponse;
import net.thumbtack.school.hiring.server.Server;
import net.thumbtack.school.hiring.server.Skill;
import net.thumbtack.school.hiring.server.TestDao;
import net.thumbtack.school.hiring.server.Vacancy;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class TestChangeRequirementLevel
{
	Server s = new Server();
	Gson gson = new Gson();
	TestDao testDao = new TestDao();
	
	public UUID registerEmployer()
	{
		RegisterEmployerDtoRequest regReq = new RegisterEmployerDtoRequest("Thekla Inc.", "San Francisco", "blow@jon.com",
				"Jonathan", "Blow", "jb", "qwerty123");
	
		RegisterEmployerDtoResponse result = gson.fromJson(s.registerEmployer(gson.toJson(regReq)), RegisterEmployerDtoResponse.class);
	
		Skill skill = new Skill("Java", 5, true);
		List<Skill> requiredSkills = new ArrayList<>();
		requiredSkills.add(skill);
	
		AddVacancyDtoRequest addVacReq = new AddVacancyDtoRequest(result.getToken(), "Java programmer", 10000, requiredSkills);
		AddVacancyDtoResponse addVacResp = gson.fromJson(s.addVacancy(gson.toJson(addVacReq)), AddVacancyDtoResponse.class);
	
		List<Vacancy> addedVacancies = addVacResp.getAddedVacancies();
	
		return result.getToken();
	}
	
	@Test
	public void testChangeRequirementName()
	{
		UUID employerToken = registerEmployer();
	
		ChangeRequirementLevelDtoRequest request = new ChangeRequirementLevelDtoRequest(employerToken, "Java programmer","Java", 1);
		UpdateVacancyDtoResponse response = gson.fromJson(s.changeRequirementLevel(gson.toJson(request)), UpdateVacancyDtoResponse.class);

		assertEquals(1, response.getUpdatedVacancy().getRequirements().get(0).getLevel());

		testDao.resetDatabase();
	}
}
