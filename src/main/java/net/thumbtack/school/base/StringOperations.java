package net.thumbtack.school.base;

import java.util.Arrays;
import java.math.BigInteger;
import java.math.BigDecimal;
import java.lang.String;
import java.text.DecimalFormat;


// Возвращает суммарную длину строк, заданных массивом strings.
public class StringOperations
{
	public static int getSummaryLength(String[] strings)
	{
		int ret_length = 0;
		
		for(String s : strings){
			ret_length += s.length();
		}
		
		return ret_length;
	}

	// Возвращает двухсимвольную строку, состоящую из начального и конечного символов заданной строки.
	public static String getFirstAndLastLetterString(String string)
	{
		return new String(string.substring(0, 1) + string.substring(string.length() - 1));
	}

	// Возвращает true, если обе строки в позиции index содержат один и тот же символ, иначе false.
	public static boolean isSameCharAtPosition(String string1, String string2, int index)
	{
		return string1.charAt(index) == string2.charAt(index);
	}

	// Возвращает true, если в обеих строках первый встреченный символ character находится в одной и той же позиции. Просмотр строк ведется от начала.
	public static boolean isSameFirstCharPosition(String string1, String string2, char character)
	{
		return string1.indexOf(character) == string2.indexOf(character);
	}

	// Возвращает true, если в обеих строках первый встреченный символ character находится в одной и той же позиции. Просмотр строк ведется от конца.
	public static boolean isSameLastCharPosition(String string1, String string2, char character)
	{
		return string1.indexOf(character, string1.lastIndexOf(string1) ) == string2.indexOf(character, string2.lastIndexOf(string2));
	}

	// Возвращает true, если в обеих строках первая встреченная подстрока str начинается в одной и той же позиции. Просмотр строк ведется от начала.
	public static boolean isSameFirstStringPosition(String string1, String string2, String str)
	{
		return string1.indexOf(str) == string2.indexOf(str);
	}

	// Возвращает true, если в обеих строках первая встреченная подстрока str начинается в одной и той же позиции. Просмотр строк ведется от конца.
	public static boolean isSameLastStringPosition(String string1, String string2, String str)
	{
		return string1.indexOf(str, string1.lastIndexOf(string1)) == string2.indexOf(str, string2.lastIndexOf(string2));
	}

	// Возвращает true, если строки равны.
	public static boolean isEqual(String string1, String string2)
	{
		return string1.equals(string2);
	}

	// Возвращает true, если строки равны без учета регистра (например, строки “abc” и “aBC” в этом смысле равны).
	public static boolean isEqualIgnoreCase(String string1, String string2)
	{
		return string1.equalsIgnoreCase(string2);
	}

	// Возвращает true, если строка string1 меньше строки string2.
	public static boolean isLess(String string1, String string2)
	{
	    if(string1.compareTo(string2) < 0) { return true; }
		return false;
	}

	// Возвращает true, если строка string1 меньше строки string2 без учета регистра (например, строка “abc” меньше строки “ABCd” в этом смысле).
	public static boolean isLessIgnoreCase(String string1, String string2)
	{
	    if(string1.compareToIgnoreCase(string2) < 0){ return true; }
	    return false;
	}

	// Возвращает строку, полученную путем сцепления двух строк.
	public static String concat(String string1, String string2)
	{
		return string1.concat(string2);
	}

	// Возвращает true, если обе строки string1 и string2 начинаются с одной и той же подстроки prefix.
	public static boolean isSamePrefix(String string1, String string2, String prefix)
	{
		return string1.startsWith(prefix) && string2.startsWith(prefix);
	}

	// Возвращает true, если обе строки string1 и string2 заканчиваются одной и той же подстрокой suffix.
	public static boolean isSameSuffix(String string1, String string2, String suffix)
	{
		return string1.endsWith(suffix) && string2.endsWith(suffix);
	}

	// Возвращает самое длинное общее “начало” двух строк. Если у строк нет общего начала, возвращает пустую строку.
	public static String getCommonPrefix(String string1, String string2)
	{
		// end of substring
		int end = 0;
		
		int minLength = Math.min(string1.length(), string2.length());
		
		for(int i = 0; i < minLength; ++i){
			if(string1.charAt(i) != string2.charAt(i)){	break; }
			
			++end;
		}
		
		if(end > 0){ return string1.substring(0, end); }
		else { return ""; }
	}

	// Возвращает перевернутую строку.
	public static String reverse(String string)
	{
		StringBuilder sb = new StringBuilder(string);
		return sb.reverse().toString();
	}

	// Возвращает true, если строка является палиндромом, то есть читается слева направо так же, как и справа налево.
	public static boolean isPalindrome(String string)
	{
		return string.equals(reverse(string));
	}

	// Возвращает true, если строка является палиндромом, то есть читается слева направо так же, как и справа налево, без учета регистра.
	public static boolean isPalindromeIgnoreCase(String string)
	{
		return string.equalsIgnoreCase(reverse(string));
	}

	// Возвращает самый длинный палиндром (без учета регистра) из массива заданных строк. Если в массиве нет палиндромов, возвращает пустую строку.
	public static String getLongestPalindromeIgnoreCase(String[] strings)
	{
		String longestPalindrom = "";
		
		for(String s : strings){
			
			if(isPalindromeIgnoreCase(s) && s.length() > longestPalindrom.length()){
				longestPalindrom = s;
			}
		}
		
		return longestPalindrom;
	}

	// Возвращает true, если обе строки содержат один и тот же фрагмент длиной length, начиная с позиции index.
	public static boolean hasSameSubstring(String string1, String string2, int index, int length)
	{
		return string1.regionMatches(index, string2, index, length);
	}

	// Возвращает true, если после замены в string1 всех вхождений replaceInStr1 на replaceByInStr1 и замены в string2 всех вхождений replaceInStr2 на replaceByInStr2 полученные строки равны.
	public static boolean isEqualAfterReplaceCharacters(String string1, char replaceInStr1, char replaceByInStr1, String string2, char replaceInStr2, char replaceByInStr2)
	{
		return string1.replace(replaceInStr1, replaceByInStr1).equals(string2.replace(replaceInStr2, replaceByInStr2));
	}

	// Возвращает true, если после замены в string1 всех вхождений строки replceInStr1 на replaceByInStr1 и замены в string2 всех вхождений replceInStr2 на replaceByInStr2 полученные строки равны.
	public static boolean isEqualAfterReplaceStrings(String string1, String replaceInStr1, String replaceByInStr1, String string2, String replaceInStr2, String replaceByInStr2)
	{
		return string1.replaceAll(replaceInStr1, replaceByInStr1).equals(string2.replaceAll(replaceInStr2, replaceByInStr2));
	}

	// Возвращает true, если строка после выбрасывания из нее всех пробелов является палиндромом, без учета регистра.
	public static boolean isPalindromeAfterRemovingSpacesIgnoreCase(String string)
	{
		return isPalindromeIgnoreCase(string.replaceAll(" ", ""));
	}

	// Возвращает true, если две строки равны, если не принимать во внимание все пробелы в начале и конце каждой строки.
	public static boolean isEqualAfterTrimming(String string1, String string2)
	{
		return string1.trim().equals(string2.trim());
	}

	// Для заданного массива целых чисел создает текстовую строку формата CSV
	public static String makeCsvStringFromInts(int[] array)
	{
		String[] str_arr = new String[array.length];
			
		for(int i = 0; i < array.length; ++i){
			str_arr[i] = Integer.toString(array[i]);
		}
		
		return String.join(",", str_arr);
	}

	// Для заданного массива вещественных чисел создает текстовую строку,
	// в которой числа разделены знаком “запятая”, причем каждое число записывается с двумя знаками после точки.
	public static String makeCsvStringFromDoubles(double[] array)
	{
		DecimalFormat formatter = new DecimalFormat(".00");
		String[] str_arr = new String[array.length];
			
		for(int i = 0; i < array.length; ++i){
			str_arr[i] = formatter.format(array[i]);
		}
		
		return String.join(",", str_arr);		
	}

	public static StringBuilder makeCsvStringBuilderFromInts(int[] array)
	{
		return new StringBuilder(makeCsvStringFromInts(array));
	}

	public static StringBuilder makeCsvStringBuilderFromDoubles(double[] array)
	{
		return new StringBuilder(makeCsvStringFromDoubles(array));
	}

	// Удаляет из строки символы, номера которых заданы в массиве positions.
	public static StringBuilder removeCharacters(String string, int[] positions)
	{
		StringBuilder sb = new StringBuilder(string);
		
		for(int i = positions.length - 1; i >= 0; --i){
			sb.deleteCharAt(positions[i]);
		}
		
		return sb;
	}

	// Вставляет в строку символы.
	public static StringBuilder insertCharacters(String string, int[] positions, char[] characters)
	{
		StringBuilder sb = new StringBuilder(string);
		
		for(int i = positions.length - 1; i >= 0; --i){
			sb.insert(positions[i], characters[i]);
		}
		
		return sb;
	}
}
