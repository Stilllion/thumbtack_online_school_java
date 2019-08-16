package net.thumbtack.school.introduction;

public class FirstSteps {
	
	// Возвращает сумму чисел x и y.
	public int sum (int x, int y)
	{
		return x + y;
	}
	
	// Возвращает произведение чисел x и y.
	public int mul (int x, int y)
	{
		return x * y;
	}
	
	// Возвращает частное от деления чисел x и y.
	public int div (int x, int y)
	{
		return x / y;
	}
	
	// Возвращает остаток от деления чисел x и y.
	public int mod (int x, int y)
	{
		return x % y;
	}
	
	// Возвращает true, если  x равен y, иначе false.
	public boolean isEqual (int x, int y)
	{
		return x == y;
	}
	
	// Возвращает true, если  x больше y, иначе false.
	public boolean isGreater (int x, int y)
	{
		return x > y;
	}
	
	// Прямоугольник с горизонтальными и вертикальными сторонами, задан двумя точками - левой верхней (xLeft, yTop) и правой нижней (xRight, yBottom).
	// Метод должен возвращать true, если точка лежит внутри прямоугольника , иначе false.

	public boolean isInsideRect(int xLeft, int yTop, int xRight, int yBottom, int x, int y)
	{
		return x >= xLeft && y >= yTop && x <= xRight && y <= yBottom; 
	}
	
	// Возвращает сумму чисел, заданных одномерным массивом array.
	public int sum(int[] array)
	{	
		int ret_sum = 0;
		
		for(int i : array){
			ret_sum += i;
		}
		
		return ret_sum;
	}
	
	// Возвращает произведение чисел, заданных одномерным массивом array. 
	public int mul(int[] array)
	{
		if(array.length == 0){ return 0; }

		int ret_sum = 1;

		for(int i : array){
			ret_sum *= i;
		}

		return ret_sum;
	}
	
	// Возвращает минимальное из чисел, заданных одномерным массивом array.
	public int min(int[] array)
	{
		int min_value = Integer.MAX_VALUE;
		
		for(int i : array){
			if(i < min_value){
				min_value = i;
			}
		}
		
		return min_value;
	}
	
	// Возвращает максимальное из чисел, заданных одномерным массивом array. 
	public int max(int[] array)
	{
		int max_value = Integer.MIN_VALUE;
		
		for(int i : array){
			if(i > max_value){
				max_value = i;
			}
		}
		
		return max_value;
	}
	
	// Возвращает среднее значение для чисел, заданных одномерным массивом array. 
	public double average(int[] array)
	{
		if(array.length == 0) { return 0; }
		return (double) sum(array) / array.length;
	}
	
	// Возвращает true, если одномерный массив array строго упорядочен по убыванию, иначе false.
	public boolean isSortedDescendant(int[] array)
	{
		if(array.length == 1){ return true; }
		
		for(int i = 1; i < array.length; ++i){
			if(array[i] >= array[i-1]){
				return false;
			}
		}

		return true;
	}
	
	// Возводит все элементы одномерного массива array в куб.
	public void cube(int[]array)
	{
		for(int i = 0; i < array.length; ++i){
			array[i] = array[i] * array[i] * array[i]; 
		}	
	}
	
	// Возвращает true, если в одномерном массиве array имеется элемент, равный value, иначе false.
	public boolean find(int[]array, int value)
	{
		for(int i : array){
			if(i == value){ return true; }
		}
		
		return false;
	}
	
	// Переворачивает одномерный массив array, то есть меняет местами 0-й и последний, 1-й и предпоследний и т.д. элементы.
	public void reverse(int[]array)
	{
		int temp = 0;
		
		for(int s = 0, e = array.length - 1; s < array.length/ 2; ++s, --e){
			temp = array[s];
			
			array[s] = array[e];
			
			array[e] = temp;
		}
	}
	
	// Возвращает true, если одномерный массив является палиндромом, иначе false. Пустой массив считается палиндромом.
	public boolean isPalindrome(int[]array)
	{
		for(int s = 0, e = array.length - 1; s < array.length/ 2; ++s, --e){
			if(array[s] == array[e]) { continue; }
			else { return false; }
		}
		
		return true;
	}
	
	// Возвращает сумму чисел, заданных двумерным массивом matrix.
	public int sum(int[][] matrix)
	{
		int ret_sum = 0;
		
		for(int row = 0; row < matrix.length; ++row){
			for(int col = 0; col < matrix[row].length; ++col){
				ret_sum += matrix[row][col];
			}
		}
		
		return ret_sum;
	}
	
	// Возвращает максимальное из чисел, заданных двумерным массивом matrix.
	public int max(int[][] matrix)
	{
		
		int max_value = Integer.MIN_VALUE;
		
		for(int row = 0; row < matrix.length; ++row){
			for(int col = 0; col < matrix[row].length; ++col){
				if(matrix[row][col] > max_value){
					max_value = matrix[row][col];
				}
			}
		}
		
		return max_value;
	}
	
	// Возвращает максимальное из чисел, находящихся на главной диагонали квадратного двумерного массива matrix.
	public int diagonalMax(int[][] matrix)
	{
		int max_value = Integer.MIN_VALUE;
		
		for(int row = 0, col = 0; row < matrix.length; ++row, col = row){
			if(matrix[row][col] > max_value){
				max_value = matrix[row][col];
			}
		}
		
		return max_value;
	}
	// Возвращает true, если все строки двумерного массива matrix строго упорядочены по убыванию, иначе false.
	// Пустая строка считается упорядоченной.
	public boolean isSortedDescendant(int[][] matrix)
	{
		for(int[] row : matrix){
			if(!isSortedDescendant(row)) { return false; }
		}
		
		return true;
	}

}
