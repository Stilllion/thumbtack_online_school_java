package net.thumbtack.school.hiring.server.employer;

import com.google.gson.Gson;
import net.thumbtack.school.hiring.request.employee.AddSkillDtoRequest;
import net.thumbtack.school.hiring.request.employee.RegisterEmployeeDtoRequest;
import net.thumbtack.school.hiring.request.employer.AddVacancyDtoRequest;
import net.thumbtack.school.hiring.request.employer.GetEmployeesDtoRequest;
import net.thumbtack.school.hiring.request.employer.RegisterEmployerDtoRequest;
import net.thumbtack.school.hiring.response.employee.RegisterEmployeeDtoResponse;
import net.thumbtack.school.hiring.response.employer.AddVacancyDtoResponse;
import net.thumbtack.school.hiring.response.employer.GetEmployeesDtoResponse;
import net.thumbtack.school.hiring.response.employer.RegisterEmployerDtoResponse;
import net.thumbtack.school.hiring.server.Employee;
import net.thumbtack.school.hiring.server.Server;
import net.thumbtack.school.hiring.server.Skill;
import net.thumbtack.school.hiring.server.TestDao;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestGetEmployeesMandatory
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

    private List<Employee> registerFiveEmployees()
    {
        RegisterEmployeeDtoRequest regReq;
        RegisterEmployeeDtoResponse regResp;

        Skill skill;
        AddSkillDtoRequest addSkillReq;

        List<Employee> registeredEmployees = new ArrayList<>();

        UUID employeeToken;

        // 1st employee
        regReq = new RegisterEmployeeDtoRequest(
                "test@mail", "A", "L", "SeasonBelok", "123456");
        regResp = gson.fromJson(s.registerEmployee(gson.toJson(regReq)), RegisterEmployeeDtoResponse.class);

        employeeToken = regResp.getToken();

        // Add skills
        skill = new Skill("Java", 5);
        addSkillReq = new AddSkillDtoRequest(employeeToken, skill);
        s.addSkill(gson.toJson(addSkillReq));

        skill = new Skill("English", 5);
        addSkillReq = new AddSkillDtoRequest(employeeToken, skill);
        s.addSkill(gson.toJson(addSkillReq));

        skill = new Skill("HTML&CSS", 5);
        addSkillReq = new AddSkillDtoRequest(employeeToken, skill);
        s.addSkill(gson.toJson(addSkillReq));

        registeredEmployees.add(regResp.getRegisteredEmployee());

        // Second employee
        regReq = new RegisterEmployeeDtoRequest(
                "random@mail.ru", "A", "B", "S", "123456");
        regResp = gson.fromJson(s.registerEmployee(gson.toJson(regReq)), RegisterEmployeeDtoResponse.class);

        employeeToken = regResp.getToken();
        // Add skills
        skill = new Skill("Java", 1);
        addSkillReq = new AddSkillDtoRequest(employeeToken, skill);
        s.addSkill(gson.toJson(addSkillReq));

        registeredEmployees.add(regResp.getRegisteredEmployee());

        // 3d employee
        regReq = new RegisterEmployeeDtoRequest(
                "a@gmail.com", "B", "C", "D", "123456");
        regResp = gson.fromJson(s.registerEmployee(gson.toJson(regReq)), RegisterEmployeeDtoResponse.class);

        employeeToken = regResp.getToken();
        // Add skills
        skill = new Skill("Java", 5);
        addSkillReq = new AddSkillDtoRequest(employeeToken, skill);
        s.addSkill(gson.toJson(addSkillReq));

        skill = new Skill("English", 4);
        addSkillReq = new AddSkillDtoRequest(employeeToken, skill);
        s.addSkill(gson.toJson(addSkillReq));

        skill = new Skill("HTML&CSS", 2);
        addSkillReq = new AddSkillDtoRequest(employeeToken, skill);
        s.addSkill(gson.toJson(addSkillReq));

        registeredEmployees.add(regResp.getRegisteredEmployee());

        // 4th employee
        regReq = new RegisterEmployeeDtoRequest("W", "A", "K", "NN", "123456");
        regResp = gson.fromJson(s.registerEmployee(gson.toJson(regReq)), RegisterEmployeeDtoResponse.class);

        employeeToken = regResp.getToken();
        // Add skills
        skill = new Skill("Java", 5);
        addSkillReq = new AddSkillDtoRequest(employeeToken, skill);
        s.addSkill(gson.toJson(addSkillReq));

        skill = new Skill("English", 1);
        addSkillReq = new AddSkillDtoRequest(employeeToken, skill);
        s.addSkill(gson.toJson(addSkillReq));

        registeredEmployees.add(regResp.getRegisteredEmployee());

        // 5th employee
        regReq = new RegisterEmployeeDtoRequest(
                "e@mail", "FN", "LN", "L", "123456");
        regResp = gson.fromJson(s.registerEmployee(gson.toJson(regReq)), RegisterEmployeeDtoResponse.class);

        employeeToken = regResp.getToken();
        // Add skills
        skill = new Skill("Java", 1);
        addSkillReq = new AddSkillDtoRequest(employeeToken, skill);
        s.addSkill(gson.toJson(addSkillReq));

        skill = new Skill("English", 1);
        addSkillReq = new AddSkillDtoRequest(employeeToken, skill);
        s.addSkill(gson.toJson(addSkillReq));

        skill = new Skill("HTML&CSS", 1);
        addSkillReq = new AddSkillDtoRequest(employeeToken, skill);
        s.addSkill(gson.toJson(addSkillReq));

        registeredEmployees.add(regResp.getRegisteredEmployee());

        return registeredEmployees;
    }

    public UUID addVacancy()
    {
        UUID token = registerEmployer();

        Skill skill = new Skill("Java", 5, true);
        Skill skill2 = new Skill("English", 4, true);
        Skill skill3 = new Skill("HTML&CSS", 2, false);
        Skill skill4 = new Skill("C++", 3, false);

        List<Skill> requiredSkills = new ArrayList<>();
        requiredSkills.add(skill);
        requiredSkills.add(skill2);
        requiredSkills.add(skill3);
        requiredSkills.add(skill4);

        AddVacancyDtoRequest addVacReq = new AddVacancyDtoRequest(token, "Java programmer", 100000, requiredSkills);
        AddVacancyDtoResponse addVacResp = gson.fromJson(s.addVacancy(gson.toJson(addVacReq)), AddVacancyDtoResponse.class);

        return token;
    }

    @Test
    public void testGetEmployeesMandatorySkills()
    {
        List<Employee> registeredEmployee;
        List<Employee> applicableEmployees = new ArrayList<>();

        UUID employerToken = addVacancy();

        // Get list of registered employees, Construct new one containing only applicable employees
        registeredEmployee = registerFiveEmployees();
        applicableEmployees.add(registeredEmployee.get(0));
        applicableEmployees.add(registeredEmployee.get(2));

        GetEmployeesDtoResponse response = gson.fromJson(s.getEmployeesMandatory(gson.toJson(
                new GetEmployeesDtoRequest(
                        employerToken, "Java programmer"))), GetEmployeesDtoResponse.class );

        assertEquals(applicableEmployees, response.getEmployeeList());

        testDao.resetDatabase();
    }
}
