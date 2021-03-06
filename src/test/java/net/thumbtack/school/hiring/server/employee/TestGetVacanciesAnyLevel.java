package net.thumbtack.school.hiring.server.employee;

import com.google.gson.Gson;
import net.thumbtack.school.hiring.request.employee.AddSkillDtoRequest;
import net.thumbtack.school.hiring.request.employee.GetVacanciesDtoRequest;
import net.thumbtack.school.hiring.request.employee.RegisterEmployeeDtoRequest;
import net.thumbtack.school.hiring.request.employer.AddVacancyDtoRequest;
import net.thumbtack.school.hiring.request.employer.RegisterEmployerDtoRequest;
import net.thumbtack.school.hiring.response.GetVacanciesDtoResponse;
import net.thumbtack.school.hiring.response.employee.RegisterEmployeeDtoResponse;
import net.thumbtack.school.hiring.response.employer.AddVacancyDtoResponse;
import net.thumbtack.school.hiring.response.employer.RegisterEmployerDtoResponse;
import net.thumbtack.school.hiring.server.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TestGetVacanciesAnyLevel
{
    Server s = new Server();
    Gson gson = new Gson();
    TestDao testDao = new TestDao();

    public List<Vacancy> addVacancies()
    {
        RegisterEmployerDtoRequest regReq;
        RegisterEmployerDtoResponse regResp;

        AddVacancyDtoRequest addVacReq;
        AddVacancyDtoResponse addVacResp;

        UUID token;

        List<Vacancy> availableVacancies = new ArrayList<>();

        List<Skill> requirementsJaiProgrammer = new ArrayList<>();
        List<Skill> requirementsJavaMidProgrammer = new ArrayList<>();
        List<Skill> requirementsJavaJuniorProgrammer = new ArrayList<>();
        List<Skill> requirementsFullStackDev = new ArrayList<>();

        // Register Employer
        regReq = new RegisterEmployerDtoRequest("Thekla Inc.", "San Francisco", "blow@jon.com",
                "Jonathan", "Blow", "jb", "qwerty123");

        regResp = gson.fromJson(s.registerEmployer(gson.toJson(regReq)), RegisterEmployerDtoResponse.class);
        token = regResp.getToken();

        // Set up vacancies requirements
        requirementsJavaMidProgrammer.add(new Skill("Java", 3, true));
        requirementsJavaMidProgrammer.add(new Skill("English", 4, true));
        requirementsJavaMidProgrammer.add(new Skill("HTML&CSS", 3, false));

        requirementsJaiProgrammer.add(new Skill("Jai", 5, true));
        requirementsJaiProgrammer.add(new Skill("English", 5, true));

        requirementsFullStackDev.add(new Skill("Java", 5, true));
        requirementsFullStackDev.add(new Skill("English", 5, true));
        requirementsFullStackDev.add(new Skill("HTML&CSS", 5, false));

        requirementsJavaJuniorProgrammer.add(new Skill("Java", 1, true));
        requirementsJavaJuniorProgrammer.add(new Skill("English", 1, true));

        // Add vacancies
        addVacReq = new AddVacancyDtoRequest(token, "Java middle", 32768, requirementsJavaMidProgrammer);
        s.addVacancy(gson.toJson(addVacReq));

        addVacReq = new AddVacancyDtoRequest(token, "Jai programmer", 32768, requirementsJaiProgrammer);
        s.addVacancy(gson.toJson(addVacReq));

        addVacReq = new AddVacancyDtoRequest(token, "FullStack Dev", 32768, requirementsFullStackDev);
        s.addVacancy(gson.toJson(addVacReq));

        addVacReq = new AddVacancyDtoRequest(token, "Java junior", 1023, requirementsJavaJuniorProgrammer);
        addVacResp = gson.fromJson(s.addVacancy(gson.toJson(addVacReq)), AddVacancyDtoResponse.class);

        availableVacancies.addAll(addVacResp.getAddedVacancies());

        return availableVacancies;
    }

    private UUID registerEmployee()
    {
        RegisterEmployeeDtoRequest regReq;
        RegisterEmployeeDtoResponse regResp;

        AddSkillDtoRequest addSkillReq;
        Skill skill;

        UUID token;

        regReq = new RegisterEmployeeDtoRequest("test@mail.com", "A", "L", "SeasonBelok", "123456");
        regResp = gson.fromJson(s.registerEmployee(gson.toJson(regReq)), RegisterEmployeeDtoResponse.class);

        token = regResp.getToken();

        skill = new Skill("Java", 1);
        addSkillReq = new AddSkillDtoRequest(token, skill);
        s.addSkill(gson.toJson(addSkillReq));

        skill = new Skill("English", 1);
        addSkillReq = new AddSkillDtoRequest(token, skill);
        s.addSkill(gson.toJson(addSkillReq));

        skill = new Skill("HTML&CSS", 1);
        addSkillReq = new AddSkillDtoRequest(token, skill);
        s.addSkill(gson.toJson(addSkillReq));

        return token;
    }

    @Test
    public void testGetVacanciesAnyLevel()
    {
        UUID employeeToken = registerEmployee();

        List<Vacancy> availableVacancies;
        List<Vacancy> applicableVacancies = new ArrayList<>();

        availableVacancies = addVacancies();

        applicableVacancies.add(availableVacancies.get(0));
        applicableVacancies.add(availableVacancies.get(2));
        applicableVacancies.add(availableVacancies.get(3));

        GetVacanciesDtoRequest request = new GetVacanciesDtoRequest(employeeToken);
        GetVacanciesDtoResponse response;

        response = gson.fromJson(s.getVacanciesAnyLevel(gson.toJson(request)), GetVacanciesDtoResponse.class);

        assertEquals(3, response.getVacancies().size());

        assertEquals(applicableVacancies.get(0), response.getVacancies().get(0));
        assertEquals(applicableVacancies.get(1), response.getVacancies().get(1));
        assertEquals(applicableVacancies.get(2), response.getVacancies().get(2));

        assertEquals("Java middle", response.getVacancies().get(0).getName());
        assertEquals("FullStack Dev", response.getVacancies().get(1).getName());
        assertEquals("Java junior", response.getVacancies().get(2).getName());

        testDao.resetDatabase();
    }
}
