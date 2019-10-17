package net.thumbtack.school.hiring.server.employer;

import com.google.gson.Gson;
import net.thumbtack.school.hiring.exception.ServerException;
import net.thumbtack.school.hiring.request.employee.AddSkillDtoRequest;
import net.thumbtack.school.hiring.request.employee.GetVacanciesAnyLevelDtoRequest;
import net.thumbtack.school.hiring.request.employee.GetVacanciesMandatorySkillsDtoRequest;
import net.thumbtack.school.hiring.request.employee.GetVacanciesRequiredLevelDtoRequest;
import net.thumbtack.school.hiring.request.employer.*;
import net.thumbtack.school.hiring.response.employee.RegisterEmployeeDtoResponse;
import net.thumbtack.school.hiring.response.employer.RegisterEmployerDtoResponse;
import net.thumbtack.school.hiring.server.Server;
import net.thumbtack.school.hiring.server.TestDao;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.UUID;

public class TestRegisterEmployer
{
    @Test
    public void testRegisterEmployer() throws ServerException
    {
        Server s = new Server();
        Gson gson = new Gson();
        TestDao testDao = new TestDao();

        RegisterEmployerDtoRequest regReq = new RegisterEmployerDtoRequest("Thekla Inc.", "San Francisco", "blow@jon.com",
                "Jonathan", "Blow", "jb", "qwerty123");

        RegisterEmployeeDtoResponse response = gson.fromJson(s.registerEmployer(gson.toJson(regReq)), RegisterEmployeeDtoResponse.class);
        UUID token = response.getToken();
		
		assertEquals(1, testDao.getRegisteredEmployers().size());

        testDao.resetDatabase();
    }
}
