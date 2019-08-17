package net.thumbtack.school.figures.v1;

import java.util.Objects;

public class Point2D {

    private int x, y;

    public Point2D(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Point2D() {
        this(0, 0);
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void moveTo(int newX, int newY) {
        x = newX;
        y = newY;
    }

    public void moveRel(int dx, int dy) {
        x += dx;
        y += dy;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + x;
        result = prime * result + y;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Point2D other = (Point2D) obj;
        if (x != other.x)
            return false;
        return y == other.y;
    }
}

/* Rect */
class Rectangle{
	// Fields
	private Point2D topLeft;
	private Point2D bottomRight;
	private int length;
	private int width;
	
	// Создает Rectangle по координатам углов - левого верхнего и правого нижнего.
	public Rectangle(Point2D topLeft, Point2D bottomRight)
	{
		this.topLeft = new Point2D(topLeft.getX(), topLeft.getY());
		this.bottomRight = new Point2D(bottomRight.getX(), bottomRight.getY());
		
		length = bottomRight.getX() - topLeft.getX();
		width  = bottomRight.getY() - topLeft.getY();
	}

	// Создает Rectangle по координатам углов - левого верхнего и правого нижнего.
	public Rectangle(int xLeft, int yTop, int xRight, int yBottom)
	{
		this.topLeft = new Point2D(xLeft, yTop);
		this.bottomRight = new Point2D(xRight, yBottom);
				
		length = bottomRight.getX() - topLeft.getX();
		width  = bottomRight.getY() - topLeft.getY();

	}

	// Создает Rectangle, левый нижний угол которого находится в начале координат, а  длина (по оси X) и ширина (по оси Y) задаются.
	public Rectangle(int length, int width)
	{
		this.length = length;
		this.width = width;
		
		this.topLeft = new Point2D(0, -width);
		this.bottomRight = new Point2D(length, 0);
	}

	// Создает Rectangle с размерами (1,1), левый нижний угол которого находится в начале координат.
	public Rectangle()
	{
		this.length = 1;
		this.width = 1;
		
		this.topLeft = new Point2D(0, -width);
		this.bottomRight = new Point2D(length, 0);
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
		Rectangle rectangle = (Rectangle) o;
		return length == rectangle.length &&
				width == rectangle.width &&
				Objects.equals(topLeft, rectangle.topLeft) &&
				Objects.equals(bottomRight, rectangle.bottomRight);
	}

	@Override
	public int hashCode() {
		return Objects.hash(topLeft, bottomRight, length, width);
	}
}


class Circle
{
	// Fields
	private Point2D center;
	private int radius;
	
	// Создает Circle по координатам центра и значению радиуса.
	public Circle(Point2D center, int radius)
	{
		this.center = new Point2D(center.getX(),  center.getY());
		this.radius = radius;
	}

	// Создает Circle по координатам центра и значению радиуса.
	public Circle(int xCenter, int yCenter, int radius)
	{
		center = new Point2D(xCenter, yCenter);
		this.radius = radius;
	}
	 
	// Создает Circle с центром в точке (0,0) указанного радиуса.
	public Circle(int radius)
	{
		center = new Point2D();
		this.radius = radius;
	}

	// Создает Circle с центром в точке (0,0) с радиусом 1.
	public Circle()
	{
		center = new Point2D();
		this.radius = 1;
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
		Circle circle = (Circle) o;
		return radius == circle.radius &&
				Objects.equals(center, circle.center);
	}

	@Override
	public int hashCode() {
		return Objects.hash(center, radius);
	}
}


class CircleFactory
{
	static int circleCount;
	// Создает Circle по координатам центра и значению радиуса. 
	public static Circle createCircle(Point2D center, int radius)
	{
		circleCount += 1;
		return new Circle(center, radius);
	}
	
	// Возвращает количество Circle, созданных с помощью метода createCircle.
	public static int getCircleCount()
	{
		return circleCount;
	}
	
	// Устанавливает количество Circle, созданных с помощью метода createCircle, равным 0 (иными словами, реинициализирует фабрику).
	public static void reset()
	{
		circleCount = 0;
	}

}


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

