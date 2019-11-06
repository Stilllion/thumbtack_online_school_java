package net.thumbtack.school.hiring.server;

import com.google.gson.Gson;
import net.thumbtack.school.hiring.exception.ServerErrorCode;
import net.thumbtack.school.hiring.request.employee.AddSkillDtoRequest;
import net.thumbtack.school.hiring.request.employee.RegisterEmployeeDtoRequest;
import net.thumbtack.school.hiring.request.employer.AddVacancyDtoRequest;
import net.thumbtack.school.hiring.request.employer.RegisterEmployerDtoRequest;
import net.thumbtack.school.hiring.response.employee.AddSkillDtoResponse;
import net.thumbtack.school.hiring.response.employee.RegisterEmployeeDtoResponse;
import net.thumbtack.school.hiring.response.employer.AddVacancyDtoResponse;
import net.thumbtack.school.hiring.response.employer.RegisterEmployerDtoResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class TestSaveFile
{
    @TempDir
    public Path tempDir;

	Server s = new Server();
	Gson gson = new Gson();
	TestDao testDao = new TestDao();

	public void registerEmployer()
	{
		RegisterEmployerDtoRequest regReq = new RegisterEmployerDtoRequest("Thekla Inc.", "San Francisco", "blow@jon.com",
				"Jonathan", "Blow", "jb", "qwerty123");

		RegisterEmployerDtoResponse result = gson.fromJson(s.registerEmployer(gson.toJson(regReq)), RegisterEmployerDtoResponse.class);


		addVacancy(result.getToken());
	}

	public void addVacancy(UUID token)
	{
		Skill skill = new Skill("Java", 5, true);
		Skill skill2 = new Skill("English", 3, false);

		List<Skill> requiredSkills = new ArrayList<>();
		requiredSkills.add(skill);
		requiredSkills.add(skill2);

		AddVacancyDtoRequest addVacReq = new AddVacancyDtoRequest(token, "Java programmer", 10000, requiredSkills);
		AddVacancyDtoResponse addVacResp = gson.fromJson(s.addVacancy(gson.toJson(addVacReq)), AddVacancyDtoResponse.class);
	}


	public void registerEmployee()
	{
		RegisterEmployeeDtoRequest regReq;
		RegisterEmployeeDtoResponse regResp;

		regReq = new RegisterEmployeeDtoRequest(
				"test@mail", "A", "L", "SeasonBelok", "123456");
		regResp = gson.fromJson(s.registerEmployee(gson.toJson(regReq)), RegisterEmployeeDtoResponse.class);

		addSkill(regResp.getToken());
	}

	public void addSkill(UUID token) {
		AddSkillDtoResponse response;
		AddSkillDtoRequest request;
		Skill skill;

		// Valid input
		skill = new Skill("Java", 5);

		request = new AddSkillDtoRequest(token, skill);
		s.addSkill(gson.toJson(request));
	}

	@Test
    public void tessSaveLoadFromFile()
	{
	    String[] args = new String[2];
		args[0] = "-s";
		String filePath = args[1] = tempDir.toString() + "server.data";

	    Server.main(args);

	    File file = new File(filePath);

		// Register Employer and add a Vacancy
		registerEmployer();
		// Register Employee and add a Skill
		registerEmployee();

		// Capture database state
		List<Employer> registeredEmployers = testDao.getRegisteredEmployers();
		List<Employee> registeredEmployees = testDao.getRegisteredEmployees();
		// Capture database state
		Map<Employer, List<Vacancy>> vacancies = testDao.getVacanciesAll();
		Map<Employee, List<Skill>> skills = testDao.getSkillsAll();

		s.stopServer(filePath);

		assertTrue(file.exists());

		// Simulate turning off the server
		testDao.resetDatabase();

		// "Restart" the server
		args = new String[2];
		args[0] = "-l";
		args[1] = tempDir.toString() + "server.data";

		Server.main(args);

		assertEquals(testDao.getRegisteredEmployers(), registeredEmployers);
		assertEquals(testDao.getRegisteredEmployees(), registeredEmployees);

		assertEquals(testDao.getVacanciesAll(), vacancies);
		assertEquals(testDao.getSkillsAll(), skills);

		testDao.resetDatabase();
	}
}