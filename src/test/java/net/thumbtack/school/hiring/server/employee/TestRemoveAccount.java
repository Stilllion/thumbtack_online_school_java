package net.thumbtack.school.hiring.server.employee;

import com.google.gson.Gson;
import net.thumbtack.school.hiring.exception.ServerErrorCode;
import net.thumbtack.school.hiring.request.employee.AddSkillDtoRequest;
import net.thumbtack.school.hiring.request.employee.RegisterEmployeeDtoRequest;
import net.thumbtack.school.hiring.request.user.RemoveAccountDtoRequest;
import net.thumbtack.school.hiring.response.employee.AddSkillDtoResponse;
import net.thumbtack.school.hiring.response.employee.RegisterEmployeeDtoResponse;
import net.thumbtack.school.hiring.response.user.RemoveAccountDtoResponse;
import net.thumbtack.school.hiring.server.Server;
import net.thumbtack.school.hiring.server.Skill;
import net.thumbtack.school.hiring.server.TestDao;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class TestRemoveAccount
{
    Server s = new Server();
    Gson gson = new Gson();
    UUID employeeToken;
    TestDao testDao = new TestDao();

    public void registerEmployee()
    {
        RegisterEmployeeDtoRequest regReq;
        RegisterEmployeeDtoResponse regResp;

        regReq = new RegisterEmployeeDtoRequest(
                "test@mail", "A", "L", "SeasonBelok", "123456");
        regResp = gson.fromJson(s.registerEmployee(gson.toJson(regReq)), RegisterEmployeeDtoResponse.class);

        addSkill();
    }

    public void addSkill()
    {
        AddSkillDtoResponse response;
        AddSkillDtoRequest request;
        Skill skill;

        // Valid input
        skill = new Skill("Java", 5);

        request = new AddSkillDtoRequest(employeeToken, skill);
        response = gson.fromJson(s.addSkill(gson.toJson(request)), AddSkillDtoResponse.class);
    }

    @Test
    public void testRemoveAccount()
    {
        RemoveAccountDtoRequest request = new RemoveAccountDtoRequest(employeeToken);
        RemoveAccountDtoResponse response = gson.fromJson(s.removeEmployee(gson.toJson(request)), RemoveAccountDtoResponse.class);

        assertTrue(testDao.getRegisteredEmployees().isEmpty());
        assertTrue(testDao.getSkillsAll().isEmpty());

        testDao.resetDatabase();
    }
}
