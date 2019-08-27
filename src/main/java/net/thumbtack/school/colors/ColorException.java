package net.thumbtack.school.colors;

public class ColorException extends Exception
{
	ColorErrorCode errorCode;
	
	public ColorException(ColorErrorCode errorCode)
	{
		this.errorCode = errorCode;
	}
	
	public ColorErrorCode getErrorCode()
	{
		return errorCode;
	}
}
