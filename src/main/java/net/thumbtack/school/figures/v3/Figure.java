package net.thumbtack.school.figures.v3;

import net.thumbtack.school.colors.*;
import net.thumbtack.school.area.HasArea;

import java.util.Objects;

abstract public class Figure implements Colored, HasArea
{
	public Color color;

    Figure(Color color) throws ColorException
    {
        if(color == null){ throw new ColorException(ColorErrorCode.NULL_COLOR);}
        this.color = color;
    }

    public abstract void moveRel(int dx, int dy);
    public abstract double getArea();
    public abstract double getPerimeter();
    public abstract boolean isInside(int x, int y) throws ColorException;
	
    public boolean isInside(Point2D point) throws ColorException
    {
        return isInside(point.getX(), point.getY());
    }

    public void setColor(Color color) throws ColorException
    {
        if(color == null){ throw new ColorException(ColorErrorCode.NULL_COLOR);}
        this.color = color;
    }

    public void setColor(String colorString) throws ColorException
    {
        this.color = Color.colorFromString(colorString);
    }

    public Color getColor()
    {
        return color;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Figure figure = (Figure) o;
        return color == figure.color;
    }

    @Override
    public int hashCode() {
        return Objects.hash(color);
    }
}
