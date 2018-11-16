package com.filter.textcorrector.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TextProcessor {
	
	public static int WordCount(String input)
	{
		if (input.isEmpty())
		{
			return 0;
		}
		
		Pattern pattern = Pattern.compile("[^ \n\r]{2,}");
		Matcher matcher = pattern.matcher(input);
		
		int count = 0;
		while (matcher.find())
		{
			count++;
		}
		return count;
	}

}
