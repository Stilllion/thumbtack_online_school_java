package net.thumbtack.school.colors;

public enum Color
{
	RED, GREEN, BLUE;

	public static Color colorFromString(String colorString) throws ColorException
	{
		if(colorString == null){ throw new ColorException(ColorErrorCode.NULL_COLOR);}
		// Throws IllegalArgumentException
		try {
			return Color.valueOf(colorString);
		} catch (IllegalArgumentException e){
			throw new ColorException(ColorErrorCode.WRONG_COLOR_STRING);
		}

	}
}



