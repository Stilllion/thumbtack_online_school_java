package net.thumbtack.school.boxes;
import net.thumbtack.school.figures.v3.Figure;

public class NamedBox<T extends Figure> extends Box<T>
{
    private String name;

    public NamedBox(T obj, String name)
    {
        super(obj);
        this.name = name;
    }

    String getName()
    {
        return name;
    }

    void setName(String name)
    {
        this.name = name;
    }
}
