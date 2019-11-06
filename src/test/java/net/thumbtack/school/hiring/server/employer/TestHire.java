package net.thumbtack.school.hiring.server.employer;

import com.google.gson.Gson;
import net.thumbtack.school.hiring.request.employee.GetVacanciesDtoRequest;
import net.thumbtack.school.hiring.request.employee.RegisterEmployeeDtoRequest;
import net.thumbtack.school.hiring.request.employer.AddVacancyDtoRequest;
import net.thumbtack.school.hiring.request.employer.HireDtoRequest;
import net.thumbtack.school.hiring.request.employer.RegisterEmployerDtoRequest;
import net.thumbtack.school.hiring.response.GetVacanciesDtoResponse;
import net.thumbtack.school.hiring.response.employee.RegisterEmployeeDtoResponse;
import net.thumbtack.school.hiring.response.employer.AddVacancyDtoResponse;
import net.thumbtack.school.hiring.response.employer.HireDtoResponse;
import net.thumbtack.school.hiring.response.employer.RegisterEmployerDtoResponse;
import net.thumbtack.school.hiring.server.Server;
import net.thumbtack.school.hiring.server.Skill;
import net.thumbtack.school.hiring.server.TestDao;
import net.thumbtack.school.hiring.server.Vacancy;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class TestHire
{
    Server s = new Server();
    Gson gson = new Gson();
    TestDao testDao = new TestDao();
    UUID employerToken;

    public List<Vacancy> registerEmployer()
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

        return availableVacancies;
    }

    public void registerEmployee()
    {
        RegisterEmployeeDtoRequest regReq;
        RegisterEmployeeDtoResponse regResp;

        regReq = new RegisterEmployeeDtoRequest("employee@mail", "Alexey", "Larshin", "SeasonBelok", "123456");
        regResp = gson.fromJson(s.registerEmployee(gson.toJson(regReq)), RegisterEmployeeDtoResponse.class);
    }

    @Test
    public void testHire()
    {
        registerEmployer();
        registerEmployee();

        HireDtoRequest hireRequest = new HireDtoRequest(employerToken, "employee@mail", "Java programmer");
        HireDtoResponse hireResponse = gson.fromJson(s.hire(gson.toJson(hireRequest)), HireDtoResponse.class);

        GetVacanciesDtoRequest requestAllVacancies = new GetVacanciesDtoRequest(employerToken);
        GetVacanciesDtoResponse responseAllVacancies = gson.fromJson(s.getEmployerVacanciesAll(gson.toJson(requestAllVacancies)),
                GetVacanciesDtoResponse.class);


        assertEquals(false, responseAllVacancies.getVacancies().get(0).isActive());
        assertEquals(false, testDao.getRegisteredEmployees().get(0).isActive());

        testDao.resetDatabase();
    }
}