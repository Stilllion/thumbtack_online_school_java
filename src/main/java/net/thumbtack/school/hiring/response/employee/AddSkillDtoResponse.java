package net.thumbtack.school.hiring.response.employee;

import net.thumbtack.school.hiring.server.Skill;

import java.util.List;

public class AddSkillDtoResponse
{
    List<Skill> employeeSkills;
    String error;

    public AddSkillDtoResponse()
    {
        employeeSkills = null;
        error = null;
    }

    public List<Skill> getEmployeeSkills()
    {
        return employeeSkills;
    }

    public void setEmployeeSkills(List<Skill> employeeSkills) {
        this.employeeSkills = employeeSkills;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
        employeeSkills = null;
    }
}
