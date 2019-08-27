package net.thumbtack.school.figures.v3;

import net.thumbtack.school.colors.Color;
import net.thumbtack.school.colors.ColorException;

public class CircleFactory
{
	static int circleCount;

	// Создает Circle по координатам центра и значению радиуса.
	public static Circle createCircle(Point2D center, int radius, Color color) throws ColorException
	{
		circleCount += 1;
		return new Circle(center, radius, color);
	}

	public static Circle createCircle(Point2D center, int radius, String stringColor) throws ColorException
	{
		return createCircle(center, radius, Color.colorFromString(stringColor));
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
