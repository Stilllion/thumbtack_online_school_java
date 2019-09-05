package net.thumbtack.school.figures.v1;

import java.util.Objects;

class Rectangle3D extends Rectangle
{
	private int height;
	// Создает Rectangle3D по координатам углов основания (левого верхнего и правого нижнего) и высотой.
	public Rectangle3D(Point2D leftTop, Point2D rightBottom, int height)
	{
		super(leftTop, rightBottom);
		this.height = height;
	}

	// Создает Rectangle3D по координатам углов основания (левого верхнего и правого нижнего) и высотой.
	public Rectangle3D(int xLeft, int yTop, int xRight, int yBottom, int height)
	{
		super(xLeft, yTop, xRight, yBottom);
		this.height = height;
	}


	// Создает Rectangle3D, левый нижний угол которого находится в начале координат, а  длина, ширина и высота задаются.
	public Rectangle3D(int length, int width, int height)
	{
		super(length, width);
		this.height = height;
	}

	// Создает Rectangle3D с размерами (1, 1, 1), левый нижний угол которого находится в начале координат.
	public Rectangle3D()
	{
		super();
		height = 1;
	}

	// Возвращает левую верхнюю точку Rectangle основания.
	public Point2D getTopLeft()
	{
		return super.getTopLeft();
	}

	// Возвращает правую нижнюю точку Rectangle основания.
	public Point2D getBottomRight()
	{
		return super.getBottomRight();
	}

	// Возвращает высоту параллелепипеда.
	public int getHeight()
	{
		return height;
	}

	// Устанавливает левую верхнюю точку Rectangle основания.
	public void setTopLeft(Point2D topLeft)
	{
		super.setTopLeft(topLeft);
	}

	// Устанавливает правую нижнюю точку Rectangle основания.
	public void setBottomRight(Point2D bottomRight)
	{
		super.setBottomRight(bottomRight);
	}

	// Устанавливает высоту параллелепипеда.
	public void setHeight(int height)
	{
		this.height = height;
	}

	// Возвращает длину  прямоугольника основания.
	public int getLength()
	{
		return super.getLength();
	}

	// Возвращает ширину прямоугольника основания.
	public int getWidth()
	{
		return super.getWidth();
	}

	// Передвигает Rectangle3D на (dx, dy). Высота не изменяется.
	public void moveRel(int dx, int dy)
	{
		super.moveRel(dx, dy);
	}

	// Увеличивает стороны Rectangle основания в (nx, ny) раз при сохранении координат левой верхней вершины; высота не изменяется.
	public void enlarge(int nx, int ny)
	{
		super.enlarge(nx, ny);
	}

	// Возвращает площадь прямоугольника  основания.
	public double getArea()
	{
		return super.getArea();
	}

	// Возвращает периметр прямоугольника основания.
	public double getPerimeter()
	{
		return super.getPerimeter();
	}

	// Возвращает объем параллелепипеда.
	public double getVolume()
	{
		return super.getArea() * height;
	}

	// Определяет, лежит ли точка (x, y) внутри Rectangle основания. Если точка лежит на стороне, считается, что она лежит внутри.
	public boolean isInside(int x, int y)
	{
		return super.isInside(x, y);
	}

	// Определяет, лежит ли точка point внутри Rectangle основания. Если точка лежит на стороне, считается, что она лежит внутри.
	public boolean isInside(Point2D point)
	{
		return super.isInside(point);
	}

	// Определяет, лежит ли точка (x, y, z) внутри Rectangle3D. Если точка лежит на стороне, считается, что она лежит внутри.
	public boolean isInside(int x, int y, int z)
	{
		return super.isInside(x, y) && z <= height;
	}

	// Определяет, лежит ли точка point внутри Rectangle3D. Если точка лежит на стороне, считается, что она лежит внутри.
	public boolean isInside(Point3D point)
	{
		return isInside(point.getX(), point.getY(), point.getZ());
	}

	// Определяет, пересекается  ли Rectangle3D с другим Rectangle3D. Считается, что параллелепипеды пересекаются, если у них есть хоть одна общая точка.
	public boolean isIntersects(Rectangle3D rectangle)
	{
		return super.isInside(rectangle.getTopLeft()) || super.isInside(rectangle.getBottomRight()) || rectangle.isInside(getTopLeft()) || rectangle.isInside(getBottomRight());
	}

	// Определяет, лежит ли rectangle3D целиком внутри текущего Rectangle3D.
	public boolean isInside(Rectangle3D rectangle)
	{
		return isInside(rectangle.getTopLeft()) && isInside(rectangle.getBottomRight()) && rectangle.getHeight() <= height;
	}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Rectangle3D that = (Rectangle3D) o;
        return height == that.height;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), height);
    }
}
