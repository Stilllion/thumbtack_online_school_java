package net.thumbtack.school.figures.v3;

import net.thumbtack.school.colors.Color;
import net.thumbtack.school.colors.ColorException;
import net.thumbtack.school.area.HasArea;

import java.util.Objects;

public class Circle extends Figure
{
	private Point2D center;
	private int radius;

	// Создает Circle по координатам центра и значению радиуса.
	public Circle(Point2D center, int radius, Color color) throws ColorException
	{
		super(color);

		this.center = new Point2D(center.getX(),  center.getY());
		this.radius = radius;
	}

	public Circle(Point2D center, int radius, String stringColor) throws ColorException
	{
		this(center, radius, Color.colorFromString(stringColor));
	}

	// Создает Circle по координатам центра и значению радиуса.
	public Circle(int xCenter, int yCenter, int radius, Color color) throws ColorException
	{
		super(color);

		center = new Point2D(xCenter, yCenter);
		this.radius = radius;
	}

	public Circle(int xCenter, int yCenter, int radius, String stringColor) throws ColorException
	{
		this(xCenter, yCenter, radius, Color.colorFromString(stringColor));
	}

	// Создает Circle с центром в точке (0,0) указанного радиуса.
	public Circle(int radius, Color color) throws ColorException
	{
		super(color);

		center = new Point2D();
		this.radius = radius;
	}

	public Circle(int radius, String stringColor) throws ColorException
	{
		this(radius, Color.colorFromString(stringColor));
	}

	// Создает Circle с центром в точке (0,0) с радиусом 1.
	public Circle(Color color) throws ColorException
	{
		super(color);

		center = new Point2D();
		this.radius = 1;
	}

	public Circle(String stringColor) throws ColorException
	{
		this(Color.colorFromString(stringColor));
	}

	// Возвращает центр Circle.
	public Point2D getCenter()
	{
		return center;
	}

	// Возвращает радиус Circle.
	public int getRadius()
	{
		return radius;
	}

	// Устанавливает центр Circle.
	public void setCenter(Point2D center)
	{
		this.center = center;
	}

	// Устанавливает радиус Circle.
	public void setRadius(int radius)
	{
		this.radius = radius;
	}

	// Передвигает Circle на (dx, dy).
	public void moveRel(int dx, int dy)
	{
		center.moveRel(dx, dy);
	}

	// Увеличивает радиус Circle в n раз, не изменяя центра.
	public void enlarge(int n)
	{
		radius *= n;
	}

	// Возвращает площадь круга.
	public double getArea()
	{
		return Math.PI * radius * radius;
	}

	// Возвращает периметр окружности (длину окружности).
	public double getPerimeter()
	{
		return 2 * Math.PI * radius;
	}

	// Определяет, лежит ли точка (x, y) внутри Circle. Если точка лежит на окружности, считается, что она лежит внутри.
	public boolean isInside(int x, int y)
	{
		return ( Math.pow( Math.abs(center.getX() - x ), 2) + Math.pow( Math.abs(center.getY() - y), 2) ) <= radius * radius;
	}

	// Определяет, лежит ли точка point внутри Circle. Если точка лежит на окружности, считается, что она лежит внутри.
	public boolean isInside(Point2D point)
	{
		return isInside(point.getX(), point.getY());
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		if (!super.equals(o)) return false;
		Circle circle = (Circle) o;
		return radius == circle.radius &&
				Objects.equals(center, circle.center);
	}

	@Override
	public int hashCode() {
		return Objects.hash(super.hashCode(), center, radius);
	}
}
