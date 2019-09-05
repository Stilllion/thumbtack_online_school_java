package net.thumbtack.school.figures.v1;

import java.util.Objects;

// Цилиндр, стоящий на плоскости OXY.
class Cylinder extends Circle
{
	private int height;
	
	// Создает Cylinder по координатам центра, значению радиуса и высоте.
	public Cylinder(Point2D center, int radius, int height)
	{
		super(center, radius);
		this.height = height;
	}

	// Создает Cylinder по координатам центра, значению радиуса и высоте.
	public Cylinder(int xCenter, int yCenter, int radius, int height)
	{
		super(xCenter, yCenter, radius);
		this.height = height;
	}

	// Создает Cylinder  с центром в точке (0, 0) с указанными радиусом и высотой.
	public Cylinder(int radius, int height)
	{
		super(radius);
		this.height = height;
	}

	// Создает Cylinder  с центром в точке (0, 0) с радиусом 1 и высотой 1.
	public Cylinder()
	{
		super();
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
