package net.thumbtack.school.cars;

import net.thumbtack.school.colors.*;

public class Car implements Colored
{
	private String model;
	private int weight;
	private int maxSpeed;
	private Color color;
	
    // Создает автомобиль указанной модели, веса (в килограммах), максимальной скорости и цвета.
    public Car (String model, int weight, int maxSpeed, Color color) throws ColorException
	{
		if(color == null){ throw new ColorException(ColorErrorCode.NULL_COLOR);}

		this.model = model;
		this.weight = weight;
		this.maxSpeed = maxSpeed;
		this.color = color;
	}

    public Car (String model, int weight, int maxSpeed, String color) throws ColorException
	{
    	this(model, weight, maxSpeed, Color.colorFromString(color));

    }

    public String getModel()
	{
		return model;
	}
    
    public void setModel(String model)
	{
		this.model = model;
	}

    public int getWeight()
	{
		return weight;
	}
    
    public void setWeight(int weight)
	{
		this.weight = weight;
	}

    public int getMaxSpeed()
	{
		return maxSpeed;
	}
    
    public void setMaxSpeed(int maxSpeed)
	{
		this.maxSpeed = maxSpeed;
	}

	public Color getColor()
	{
		return color;
	}

	public void setColor(Color color) throws ColorException
	{
		if(color == null){ throw new ColorException(ColorErrorCode.NULL_COLOR);}
    	this.color = color;
	}
	public void setColor(String colorString) throws ColorException
	{
		this.color = Color.colorFromString(colorString);
	}
}
