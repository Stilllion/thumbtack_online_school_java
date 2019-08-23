package net.thumbtack.school.figures.v2;

import java.util.Objects;

public class Point3D extends Point2D {

    private int z;

    public Point3D(int x, int y, int z) {
        super(x, y);
        this.z = z;
    }

    public Point3D(int z) {
        super();
        this.z = z;
    }

    public Point3D() {
        super();
        z = 0;
    }

    public int getZ() {
        return z;
    }

    public void setZ(int z) {
        this.z = z;
    }

    public void move(int dx, int dy, int dz) {
        super.moveRel(dx, dy);
        z += dz;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + z;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;
        Point3D other = (Point3D) obj;
        return z == other.z;
    }

}


class Rectangle3D extends Rectangle
{
	private int height;
	// Создает Rectangle3D по координатам углов основания (левого верхнего и правого нижнего) и высотой.
	public Rectangle3D(Point2D leftTop, Point2D rightBottom, int height, int color)
	{
		super(leftTop, rightBottom, color);
		this.height = height;
	}

	// Создает Rectangle3D по координатам углов основания (левого верхнего и правого нижнего) и высотой.
	public Rectangle3D(int xLeft, int yTop, int xRight, int yBottom, int height, int color)
	{
		super(xLeft, yTop, xRight, yBottom, color);
		this.height = height;
	}


	// Создает Rectangle3D, левый нижний угол которого находится в начале координат, а  длина, ширина и высота задаются.
	public Rectangle3D(int length, int width, int height, int color)
	{
		super(length, width, color);
		this.height = height;
	}

	// Создает Rectangle3D с размерами (1, 1, 1), левый нижний угол которого находится в начале координат.
	public Rectangle3D(int color)
	{
		super(color);
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


// Цилиндр, стоящий на плоскости OXY.
class Cylinder extends Circle
{
	private int height;
	// Создает Cylinder по координатам центра, значению радиуса и высоте.
	public Cylinder(Point2D center, int radius, int height, int color)
	{
		super(center, radius, color);
		this.height = height;
	}

	// Создает Cylinder по координатам центра, значению радиуса и высоте.
	public Cylinder(int xCenter, int yCenter, int radius, int height, int color)
	{
		super(xCenter, yCenter, radius, color);
		this.height = height;
	}
	 
	// Создает Cylinder  с центром в точке (0, 0) с указанными радиусом и высотой.
	public Cylinder(int radius, int height, int color)
	{
		super(radius, color);
		this.height = height;
	}

	// Создает Cylinder  с центром в точке (0, 0) с радиусом 1 и высотой 1.
	public Cylinder(int color)
	{
		super(color);
		this.height = 1;
	}

	// Возвращает координаты центра Cylinder.
	public Point2D getCenter()
	{
		return super.getCenter();
	}
	  
	// Возвращает радиус Cylinder.
	public int getRadius()
	{
		return super.getRadius();
	}

	// Устанавливает координаты центра Cylinder.
	public void setCenter(Point2D center)
	{
		super.setCenter(center);
	}

	// Устанавливает радиус Cylinder.
	public void setRadius(int radius)
	{
		super.setRadius(radius);
	}

	// Возвращает высоту Cylinder.
	public int getHeight()
	{
		return height;
	}

	// Устанавливает высоту Cylinder.
	public void setHeight(int height)
	{
		this.height = height;
	}

	// Передвигает Cylinder на (dx, dy).
	public void moveRel(int dx, int dy)
	{
		super.moveRel(dx, dy);
	}

	// Увеличивает радиус Cylinder в n раз, не изменяя центра и высоты.
	public void enlarge(int n)
	{
		super.enlarge(n);
	}

	// Возвращает площадь круга основания цилиндра.
	public double getArea()
	{
		return super.getArea();
	}

	// Возвращает периметр окружности основания цилиндра.
	public double getPerimeter()
	{
		return super.getPerimeter();
	}

	// Возвращает объем цилиндра.
	public double getVolume()
	{
		return super.getArea() * height;
	}
	 
	// Определяет, лежит ли точка (x, y, z) внутри Cylinder. Если точка лежит на поверхности, считается, что она лежит внутри.
	public boolean isInside(int x, int y, int z)
	{
		return super.isInside(x, y) && z <= height;
	}

	// Определяет, лежит ли точка point внутри Cylinder. Если точка лежит на поверхности, считается, что она лежит внутри.
	public boolean isInside(Point3D point)
	{
		return isInside(point.getX(), point.getY(), point.getZ());
	}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Cylinder cylinder = (Cylinder) o;
        return height == cylinder.height;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), height);
    }
}



