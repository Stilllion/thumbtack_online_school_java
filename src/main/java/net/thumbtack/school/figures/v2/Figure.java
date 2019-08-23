package net.thumbtack.school.figures.v2;

import java.util.Objects;

abstract class Figure implements Colored
{
	public int color;

    Figure(int color)
    {
        this.color = color;
    }

    public abstract void moveRel(int dx, int dy);
    public abstract double getArea();
    public abstract double getPerimeter();
    public abstract boolean isInside(int x, int y);
	
    public boolean isInside(Point2D point)
    {
        return isInside(point.getX(), point.getY());
    }

    public void setColor(int color)
    {
        this.color = color;
    }

    public int getColor()
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
