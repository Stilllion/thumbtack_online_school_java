package net.thumbtack.school.hiring.request.employee;

import net.thumbtack.school.hiring.exception.ServerErrorCode;
import net.thumbtack.school.hiring.exception.ServerException;

import java.util.UUID;

public class ChangeSkillLevelDtoRequest
{
    UUID token;
    String skillName;
    int skillLevel;

    public ChangeSkillLevelDtoRequest(UUID token, String skillName)
    {
        this.token = token;
        this.skillName = skillName;
    }

    public void validate() throws ServerException
    {
        if(token == null){
            throw new ServerException(ServerErrorCode.WRONG_TOKEN);
        }

        if(skillName == null || skillName.equals("")){
            throw new ServerException(ServerErrorCode.WRONG_SKILL_NAME);
        }
    }

    public UUID getToken() {
        return token;
    }

    public void setToken(UUID token) {
        this.token = token;
    }

    public String getSkillName() {
        return skillName;
    }

    public void setSkillName(String skillName) {
        this.skillName = skillName;
    }

    public int getSkillLevel() {
        return skillLevel;
    }

    public void setSkillLevel(int skillLevel) {
        this.skillLevel = skillLevel;
    }
}
