package net.thumbtack.school.figures.v1;

import java.util.Objects;

class Rectangle
{
	private Point2D topLeft;
	private Point2D bottomRight;

	// Создает Rectangle по координатам углов - левого верхнего и правого нижнего.
	public Rectangle(Point2D topLeft, Point2D bottomRight)
	{
		this.topLeft = new Point2D(topLeft.getX(), topLeft.getY());
		this.bottomRight = new Point2D(bottomRight.getX(), bottomRight.getY());

		// length = bottomRight.getX() - topLeft.getX();
		// width  = bottomRight.getY() - topLeft.getY();
	}

	// Создает Rectangle по координатам углов - левого верхнего и правого нижнего.
	public Rectangle(int xLeft, int yTop, int xRight, int yBottom)
	{
		this.topLeft = new Point2D(xLeft, yTop);
		this.bottomRight = new Point2D(xRight, yBottom);
	}

	// Создает Rectangle, левый нижний угол которого находится в начале координат, а  длина (по оси X) и ширина (по оси Y) задаются.
	public Rectangle(int length, int width)
	{
		this.topLeft = new Point2D(0, -width);
		this.bottomRight = new Point2D(length, 0);
	}

	// Создает Rectangle с размерами (1,1), левый нижний угол которого находится в начале координат.
	public Rectangle()
	{
		this.topLeft = new Point2D(0, -1);
		this.bottomRight = new Point2D(1, 0);
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
		return bottomRight.getX() - topLeft.getX();
	}

	// Возвращает ширину прямоугольника.
	public int getWidth()
	{
		return bottomRight.getY() - topLeft.getY();
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
		bottomRight.setX( topLeft.getX() + getLength() * nx );
		bottomRight.setY( topLeft.getY() +  getWidth() * ny );
	}

	// Возвращает площадь прямоугольника.
	public double getArea()
	{
		return getLength() * getWidth();
	}

	// Возвращает периметр прямоугольника.
	public double getPerimeter()
	{
		return (2 * getLength()) + (2 * getWidth());
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
		Rectangle rectangle = (Rectangle) o;
		return Objects.equals(topLeft, rectangle.topLeft) &&
				Objects.equals(bottomRight, rectangle.bottomRight);
	}

	@Override
	public int hashCode() {
		return Objects.hash(topLeft, bottomRight);
	}
}
