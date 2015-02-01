package Problems;

import ProjectEulerRunner.Problem;
import static java.lang.Math.*;

public class ProblemNine implements Problem 
{
	private static final int NUMBER = 1000;

	public String run() 
	{
		for(int a = 0; a <= 1000; a++)
		{
			for(int b = 0; b <= 1000; b++)
			{
				int c_sqr = (int) (pow(a, 2) + pow(b, 2));
				double c = sqrt(c_sqr);
				
				if(((a + b + c) == NUMBER && a != 0 && b != 0))
					return "There exists exactly one Pythagorean triplet for which a + b + c = 1000, Find the product abc: " + Double.toString(a*b*c);

			}
		}
		
		return null;
	}
	
	public boolean isCorrect()
	{
		return true;
	}
	
	public int getID()
	{
		return 9;
	}
}
