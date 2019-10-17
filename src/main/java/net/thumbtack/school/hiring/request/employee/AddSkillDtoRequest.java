package net.thumbtack.school.hiring.request.employee;

import net.thumbtack.school.hiring.exception.ServerErrorCode;
import net.thumbtack.school.hiring.exception.ServerException;
import net.thumbtack.school.hiring.server.Skill;

import java.util.UUID;

public class AddSkillDtoRequest
{
    private String name;
    //уровень владения этим требованием потенциального работника. Определяется по шкале “1” - “5” (1- начальный уровень, 5 - максимально высокий)
    private int level;
    // является ли требование обязательным
    private boolean isMandatory;

    UUID token;

    public AddSkillDtoRequest(UUID token, String name, int level, boolean isMandatory)
    {
        this.token = token;

        this.name = name;
        this.level = level;
        this.isMandatory = isMandatory;
    }

    public AddSkillDtoRequest(UUID token, String name, int level)
    {
        this.token = token;

        this.name = name;
        this.level = level;
    }

    public AddSkillDtoRequest(UUID token, Skill s)
    {
        this.token = token;

        this.name = s.getName();
        this.level = s.getLevel();
    }

    public boolean validate() throws ServerException
    {
        if(name == null || name.equals("")){
            throw new ServerException(ServerErrorCode.WRONG_SKILL_NAME);
        }

        if(level < 1 || level > 5){
            throw new ServerException(ServerErrorCode.WRONG_SKILL_LEVEL);
        }

        return true;
    }

    public String getName() {
        return name;
    }

    public int getLevel() {
        return level;
    }

    public boolean isMandatory() {
        return isMandatory;
    }

    public UUID getToken() {
        return token;
    }
}
