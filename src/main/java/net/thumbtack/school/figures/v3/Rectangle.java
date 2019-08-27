package net.thumbtack.school.figures.v3;

import net.thumbtack.school.colors.Color;
import net.thumbtack.school.colors.ColorException;

import java.util.Objects;

public class Rectangle extends Figure
{
	private Point2D topLeft;
	private Point2D bottomRight;
	private int length;
	private int width;

	// Создает Rectangle по координатам углов - левого верхнего и правого нижнего.
	public Rectangle(Point2D topLeft, Point2D bottomRight, Color color) throws ColorException
	{
		super(color);
		this.topLeft = new Point2D(topLeft.getX(), topLeft.getY());
		this.bottomRight = new Point2D(bottomRight.getX(), bottomRight.getY());

		length = bottomRight.getX() - topLeft.getX();
		width  = bottomRight.getY() - topLeft.getY();
	}

	public Rectangle(Point2D topLeft, Point2D bottomRight, String stringColor) throws ColorException
	{
		this(topLeft, bottomRight, Color.colorFromString(stringColor));
	}

	// Создает Rectangle по координатам углов - левого верхнего и правого нижнего.
	public Rectangle(int xLeft, int yTop, int xRight, int yBottom, Color color) throws ColorException
	{
		super(color);
		this.topLeft = new Point2D(xLeft, yTop);
		this.bottomRight = new Point2D(xRight, yBottom);

		length = bottomRight.getX() - topLeft.getX();
		width  = bottomRight.getY() - topLeft.getY();

	}

	public Rectangle(int xLeft, int yTop, int xRight, int yBottom, String stringColor) throws ColorException
	{
		this(xLeft, yTop, xRight, yBottom, Color.colorFromString(stringColor));
	}

	// Создает Rectangle, левый нижний угол которого находится в начале координат, а  длина (по оси X) и ширина (по оси Y) задаются.
	public Rectangle(int length, int width, Color color) throws ColorException
	{
		super(color);

		this.length = length;
		this.width = width;

		this.topLeft = new Point2D(0, -width);
		this.bottomRight = new Point2D(length, 0);
	}

	public Rectangle(int length, int width, String stringColor) throws ColorException
	{
		this(length, width, Color.colorFromString(stringColor));
	}

	// Создает Rectangle с размерами (1,1), левый нижний угол которого находится в начале координат.
	public Rectangle(Color color) throws ColorException
	{
		super(color);

		this.length = 1;
		this.width = 1;

		this.topLeft = new Point2D(0, -width);
		this.bottomRight = new Point2D(length, 0);
	}

	public Rectangle(String stringColor) throws ColorException
	{
		this(Color.colorFromString(stringColor));
	}

	// Возвращает левую верхнюю точку Rectangle.
	public Point2D getTopLeft()
	{
		return topLeft;
	}

	// Возвращает правую нижнюю точку Rectangle.
	public Point2D getBottomRight()
	{
		return bottomRight;
	}

	// Устанавливает левую верхнюю точку Rectangle.
	public void setTopLeft(Point2D topLeft)
	{
		this.topLeft = topLeft;
	}

	// Устанавливает правую нижнюю точку Rectangle.
	public void setBottomRight(Point2D bottomRight)
	{
		this.bottomRight = bottomRight;
	}

	// Возвращает длину прямоугольника.
	public int getLength()
	{
		return length;
	}

	// Возвращает ширину прямоугольника.
	public int getWidth()
	{
		return width;
	}

	// Передвигает Rectangle на (dx, dy).
	public void moveRel(int dx, int dy)
	{
		topLeft.moveRel(dx, dy);
		bottomRight.moveRel(dx, dy);
	}

	// Увеличивает стороны Rectangle в (nx, ny) раз при сохранении координат левой верхней вершины.
	public void enlarge(int nx, int ny)
	{
		bottomRight.setX( topLeft.getX() + length * nx );
		bottomRight.setY( topLeft.getY() +  width * ny );
	}

	// Возвращает площадь прямоугольника.
	public double getArea()
	{
		return length * width;
	}

	// Возвращает периметр прямоугольника.
	public double getPerimeter()
	{
		return (2 * length) + (2 * width);
	}

	// Определяет, лежит ли точка (x, y) внутри Rectangle. Если точка лежит на стороне, считается, что она лежит внутри.
	public boolean isInside(int x, int y)
	{
		return x >= topLeft.getX() && y >= topLeft.getY() && x <= bottomRight.getX() && y <= bottomRight.getY();
	}

	// Определяет, лежит ли точка point внутри Rectangle. Если точка лежит на стороне, считается, что она лежит внутри.
	public boolean isInside(Point2D point)
	{
		return isInside(point.getX(), point.getY());
	}

	// Определяет, пересекается  ли Rectangle с другим Rectangle. Считается, что прямоугольники пересекаются, если у них есть хоть одна общая точка.
	public boolean isIntersects(Rectangle rectangle)
	{
		return isInside(rectangle.topLeft) || isInside(rectangle.bottomRight) || rectangle.isInside(topLeft) || rectangle.isInside(bottomRight);
	}

	// Определяет, лежит ли rectangle целиком внутри текущего Rectangle.
	public boolean isInside(Rectangle rectangle)
	{
		return isInside(rectangle.topLeft) && isInside(rectangle.bottomRight);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		if (!super.equals(o)) return false;
		Rectangle rectangle = (Rectangle) o;
		return length == rectangle.length &&
				width == rectangle.width &&
				Objects.equals(topLeft, rectangle.topLeft) &&
				Objects.equals(bottomRight, rectangle.bottomRight);
	}

	@Override
	public int hashCode() {
		return Objects.hash(super.hashCode(), topLeft, bottomRight, length, width);
	}
}
