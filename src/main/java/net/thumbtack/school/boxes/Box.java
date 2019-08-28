package net.thumbtack.school.boxes;

import net.thumbtack.school.area.HasArea;
import net.thumbtack.school.figures.v3.Figure;

public class Box<T extends Figure> implements HasArea
{
    private T obj;

    public Box(T obj)
    {
        this.obj = obj;
    }

    public T getContent()
    {
        return obj;
    }

    public void setContent(T obj)
    {
        this.obj = obj;
    }

    @Override
    public double getArea() {
        return obj.getArea();
    }

    public boolean isAreaEqual(Box box)
    {
        return this.getArea() == box.getArea();
    }

    public static boolean isAreaEqual(Box box1, Box box2)
    {
        return box1.getArea() == box2.getArea();
    }
}
