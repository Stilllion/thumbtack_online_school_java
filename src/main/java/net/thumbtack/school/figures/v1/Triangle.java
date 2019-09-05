package net.thumbtack.school.figures.v1;

import java.util.Objects;

class Triangle
{
	private Point2D point1;
	private Point2D point2;
	private Point2D point3;

	// Создает Triangle по координатам трех точек.
	public Triangle(Point2D point1, Point2D point2, Point2D point3)
	{
		this.point1 = new Point2D(point1.getX(), point1.getY());
		this.point2 = new Point2D(point2.getX(), point2.getY());
		this.point3 = new Point2D(point3.getX(), point3.getY());
	}

	// Возвращает точку 1 треугольника.
	public Point2D getPoint1()
	{
		return point1;
	}

	// Возвращает точку 2 треугольника.
	public Point2D getPoint2()
	{
		return point2;
	}

	// Возвращает точку 3 треугольника.
	public Point2D getPoint3()
	{
		return point3;
	}

	// Устанавливает точку 1 треугольника.
	public void setPoint1(Point2D point)
	{
		point1 = point;
	}

	// Устанавливает точку 2 треугольника.
	public void setPoint2(Point2D point)
	{
		point2 = point;
	}

	// Устанавливает точку 3 треугольника.
	public void setPoint3(Point2D point)
	{
		point3 = point;
	}

	// Возвращает длину стороны 1-2.
	public double getSide12()
	{
		return Math.sqrt( Math.pow( point2.getX() - point1.getX(), 2 ) + Math.pow( point2.getY() - point1.getY(), 2 ));
	}

	// Возвращает длину стороны 1-3.
	public double getSide13()
	{
		return Math.sqrt( Math.pow( point3.getX() - point1.getX(), 2 ) + Math.pow( point3.getY() - point1.getY(), 2 ));
	}

	// Возвращает длину стороны 2-3.
	public double getSide23()
	{
		return Math.sqrt( Math.pow( point2.getX() - point3.getX(), 2 ) + Math.pow( point2.getY() - point3.getY(), 2 ));
	}

	// Передвигает Triangle на (dx, dy).
	public void moveRel(int dx, int dy)
	{
		point1.moveRel(dx, dy);
		point2.moveRel(dx, dy);
		point3.moveRel(dx, dy);
	}

	// Возвращает площадь треугольника.
	public double getArea()
	{
		// half of Perimeter
		double s = getPerimeter() / 2;

		return Math.sqrt( s * (s - getSide12() ) * (s - getSide13() ) * (s - getSide23() ));
	}

	// Возвращает периметр треугольника.
	public double getPerimeter()
	{
		return getSide12() + getSide23() + getSide13();
	}

	// Определяет, лежит ли точка (x, y) внутри Triangle. Если точка лежит на стороне треугольника, считается, что она лежит внутри.
	public boolean isInside(int x, int y)
	{
		Triangle t1 = new Triangle(this.point1, this.point2, new Point2D(x, y));
		Triangle t2 = new Triangle(new Point2D(x, y), this.point2, this.point3);
		Triangle t3 = new Triangle(this.point1, new Point2D(x, y), this.point3);

		return (int)this.getArea() == (int)(t1.getArea() + t2.getArea() + t3.getArea());
	}

	// Определяет, лежит ли точка point внутри Triangle. Если точка лежит на стороне треугольника, считается, что она лежит внутри.
	public boolean isInside(Point2D point)
	{
		return isInside(point.getX(), point.getY());
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Triangle triangle = (Triangle) o;
		return Objects.equals(point1, triangle.point1) &&
				Objects.equals(point2, triangle.point2) &&
				Objects.equals(point3, triangle.point3);
	}

	@Override
	public int hashCode() {
		return Objects.hash(point1, point2, point3);
	}
}
