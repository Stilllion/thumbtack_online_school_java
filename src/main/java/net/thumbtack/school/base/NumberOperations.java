package net.thumbtack.school.base;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Arrays;

public class NumberOperations {
    // Ищет в массиве array первый элемент, значение которого равно value
    public static Integer find(int[] array, int value)
    {
		for(int i = 0; i < array.length; ++i){
			if(array[i] == value){
				return i;
			}
		}
		
		return null;
    }

    // Ищет в массиве array первый элемент, значение которого по модулю не отличается от value более чем на eps.
    public static Integer find(double[] array, double value, double eps)
    {
        for(int i = 0; i < array.length; ++i){
            if(Math.abs(value - array[i]) < eps){
                return i;
            }
        }
		
        return null;
    }

    // Вычисляет плотность вещества по формуле weight / volume.
	// Если получившееся значение не превышает max и не меньше min, возвращает полученное значение, в противном случае возвращает null.
    public static Double calculateDensity(double weight, double volume, double min, double max)
    {
        double density = weight / volume;
		
		if(density < max && density > min) { return density; }
		else { return null; }
    }

    // Ищет в массиве array первый элемент, значение которого равно value.
    public static Integer find(BigInteger[] array, BigInteger value)
    {
		for(int i = 0; i < array.length; ++i){
			if(array[i].equals(value)){
				return i;
			}
		}
     
		return null;
    }

    // Вычисляет плотность вещества по формуле weight / volume.
	// Если получившееся значение не превышает max и не меньше min, возвращает полученное значение, в противном случае возвращает null.
    public static BigDecimal calculateDensity(BigDecimal weight, BigDecimal volume, BigDecimal min, BigDecimal max)
    {
        BigDecimal density = weight.divide(volume);
		
		if(density.compareTo(max) == -1 && density.compareTo(min) == 1) { return density; }
		else { return null; }
    }
}
