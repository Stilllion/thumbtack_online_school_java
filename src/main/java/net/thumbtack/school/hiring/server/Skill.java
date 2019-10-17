package net.thumbtack.school.hiring.server;

import javax.swing.*;
import java.util.Objects;

public class Skill
{
    // Каждое требование к работнику состоит из 3 элементов
    // название требования - текстовая строка. Например, “язык Java”
    String name;
    //уровень владения этим требованием потенциального работника. Определяется по шкале “1” - “5” (1- начальный уровень, 5 - максимально высокий)
    int level;
    // является ли требование обязательным
    boolean isMandatory;

    public Skill(String name, int level, boolean isMandatory)
    {
        this.name = name;
        this.level = level;
        this.isMandatory = isMandatory;
    }

    public Skill(String name, int level)
    {
        this.name = name;
        this.level = level;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public boolean isMandatory() {
        return isMandatory;
    }

    public void setMandatory(boolean mandatory) {
        isMandatory = mandatory;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Skill skill = (Skill) o;
        return level == skill.level &&
                isMandatory == skill.isMandatory &&
                Objects.equals(name, skill.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, level, isMandatory);
    }
}
