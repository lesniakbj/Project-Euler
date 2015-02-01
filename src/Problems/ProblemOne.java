package Problems;

import ProjectEulerRunner.Problem;

public class ProblemOne implements Problem 
{
	// Problem 1:
	// Find the sum of all the multiples of 3 or 5 below 1000.
	
	public String run() 
	{
		int count = 0;
		
		for(int i = 1; i < 1000; i++)
			if((i % 3 == 0) || (i % 5 == 0))
				count += i;
				
		return "Multiples of 3 or 5 for all numbers less than n (n = 1000): " + Integer.toString(count);
	}
	
	public boolean isCorrect()
	{
		return true;
	}
	
	public int getID()
	{
		return 1;
	}
}
