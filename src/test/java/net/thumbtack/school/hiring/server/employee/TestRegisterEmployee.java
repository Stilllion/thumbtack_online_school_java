package net.thumbtack.school.hiring.server.employee;

import com.google.gson.Gson;
import net.thumbtack.school.hiring.exception.ServerErrorCode;
import net.thumbtack.school.hiring.exception.ServerException;
import net.thumbtack.school.hiring.request.employee.AddSkillDtoRequest;
import net.thumbtack.school.hiring.request.employee.RegisterEmployeeDtoRequest;
import net.thumbtack.school.hiring.response.employee.RegisterEmployeeDtoResponse;
import net.thumbtack.school.hiring.server.Employee;
import net.thumbtack.school.hiring.server.Server;
import net.thumbtack.school.hiring.server.Skill;
import net.thumbtack.school.hiring.server.TestDao;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class TestRegisterEmployee
{
    Server s = new Server();
    Gson gson = new Gson();
    TestDao testDao = new TestDao();

    @Test
    public void testRegisterEmployee()
    {
        // Valid input
        RegisterEmployeeDtoRequest regEmplReq = new RegisterEmployeeDtoRequest("test@mail", "A", "L", "SeasonBelok", "123456");
        RegisterEmployeeDtoResponse response = gson.fromJson(s.registerEmployee(gson.toJson(regEmplReq)), RegisterEmployeeDtoResponse.class);

        assertEquals("test@mail", response.getRegisteredEmployee().getEmail());
        assertEquals("A", response.getRegisteredEmployee().getFirstName());
        assertEquals("L", response.getRegisteredEmployee().getLastName());
        assertEquals("SeasonBelok", response.getRegisteredEmployee().getLogin());
        assertEquals("123456", response.getRegisteredEmployee().getPassword());

        // Invalid input
        regEmplReq = new RegisterEmployeeDtoRequest("", "A", "L", "SeasonBelok", "123456");

        response = gson.fromJson(s.registerEmployee(gson.toJson(regEmplReq)), RegisterEmployeeDtoResponse.class);
        assertEquals(ServerErrorCode.WRONG_EMAIL.getErrorString(), response.getError());

        regEmplReq = new RegisterEmployeeDtoRequest("mail", "A", "L", "SeasonBelok", "123456");

        response = gson.fromJson(s.registerEmployee(gson.toJson(regEmplReq)), RegisterEmployeeDtoResponse.class);
        assertEquals(ServerErrorCode.DUPLICATE_LOGIN.getErrorString(), response.getError());
		
		
		regEmplReq = new RegisterEmployeeDtoRequest("test@mail", "A", "L", "SeasonBelok", "");
		
		response = gson.fromJson(s.registerEmployee(gson.toJson(regEmplReq)), RegisterEmployeeDtoResponse.class);
        assertEquals(ServerErrorCode.WRONG_PASSWORD.getErrorString(), response.getError());

        testDao.resetDatabase();
    }

    private UUID registerEmployeeInvalidInput()
    {
        RegisterEmployeeDtoRequest regReq;
        RegisterEmployeeDtoResponse regResp;

        AddSkillDtoRequest addSkillReq;
        Skill skill;

        UUID token;

        regReq = new RegisterEmployeeDtoRequest("test@mail", "A", "L", "SeasonBelok", "123456");
        regResp = gson.fromJson(s.registerEmployee(gson.toJson(regReq)), RegisterEmployeeDtoResponse.class);

        token = regResp.getToken();

        skill = new Skill("", 1);
        addSkillReq = new AddSkillDtoRequest(token, skill);
        s.addSkill(gson.toJson(addSkillReq));

        s.addSkill(gson.toJson(addSkillReq));

        return token;
    }
}
