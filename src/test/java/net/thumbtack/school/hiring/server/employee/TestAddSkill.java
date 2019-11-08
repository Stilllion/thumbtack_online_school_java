package net.thumbtack.school.hiring.server.employee;

import com.google.gson.Gson;
import net.thumbtack.school.hiring.exception.ServerErrorCode;
import net.thumbtack.school.hiring.request.employee.AddSkillDtoRequest;
import net.thumbtack.school.hiring.request.employee.RegisterEmployeeDtoRequest;
import net.thumbtack.school.hiring.request.user.RemoveAccountDtoRequest;
import net.thumbtack.school.hiring.response.employee.AddSkillDtoResponse;
import net.thumbtack.school.hiring.response.employee.RegisterEmployeeDtoResponse;
import net.thumbtack.school.hiring.server.Server;
import net.thumbtack.school.hiring.server.Skill;
import net.thumbtack.school.hiring.server.TestDao;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TestAddSkill
{
    Server s = new Server();
    Gson gson = new Gson();
    TestDao testDao = new TestDao();

    UUID registerEmployee()
    {
        RegisterEmployeeDtoRequest regReq;
        RegisterEmployeeDtoResponse regResp;

        regReq = new RegisterEmployeeDtoRequest(
                "test@mail", "A", "L", "SeasonBelok", "123456");
        regResp = gson.fromJson(s.registerEmployee(gson.toJson(regReq)), RegisterEmployeeDtoResponse.class);

        return regResp.getToken();
    }

    @Test
    public void testAddSkill()
    {
        UUID token = registerEmployee();
        AddSkillDtoResponse response;
        AddSkillDtoRequest request;
        Skill skill;

        // Valid input
        skill = new Skill("Java", 5);

        request = new AddSkillDtoRequest(token, skill);
        response = gson.fromJson(s.addSkill(gson.toJson(request)), AddSkillDtoResponse.class);

        assertEquals(skill, response.getEmployeeSkills().get(0));
        assertEquals(null, response.getError());

        // Invalid input
        skill = new Skill(null, 1);

        request = new AddSkillDtoRequest(token, skill);
        response = gson.fromJson(s.addSkill(gson.toJson(request)), AddSkillDtoResponse.class);

        assertEquals(null, response.getEmployeeSkills());
        assertEquals(ServerErrorCode.WRONG_SKILL_NAME.getErrorString(), response.getError());

        skill = new Skill("Java", 0);

        request = new AddSkillDtoRequest(token, skill);
        response = gson.fromJson(s.addSkill(gson.toJson(request)), AddSkillDtoResponse.class);

        assertEquals(null, response.getEmployeeSkills());
        assertEquals(ServerErrorCode.WRONG_SKILL_LEVEL.getErrorString(), response.getError());

        testDao.resetDatabase();
    }
}

