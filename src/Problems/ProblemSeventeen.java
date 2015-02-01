package Problems;

import ProjectEulerRunner.Problem;
import ProjectEulerRunner.EnglishNumberToWords;

public class ProblemSeventeen implements Problem 
{
	public String run()
	{
		String str = "";
		
		for(int i = 0; i <= 1000; i++)
		{
			String num = EnglishNumberToWords.convert(i);
			str = str + num;
		}

		return "From 1 to 1000 (one thousand) inclusive were written out in words, how many letters would be used: " + Integer.toString(str.length());
	}
	
	public int getID()
	{
		return 17;
	}
	
	public boolean isCorrect()
	{
		return true;
	}
}
