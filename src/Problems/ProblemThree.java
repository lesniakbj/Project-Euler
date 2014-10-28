package Problems;

import ProjectEulerRunner.Problem;

public class ProblemThree implements Problem 
{
	
	private static long NUMBER = 600851475143L;

	public String run() 
	{
		while (true) 
		{
			long small = smallestFactor(NUMBER);
			
			if (small < NUMBER)
				NUMBER /= small;
			else
				return "The largest prime factor of the number 600851475143: " + Long.toString(NUMBER);
		}
	}
	
	private static long smallestFactor(long n) 
	{
		for (long i = 2, end = (long) Math.sqrt(n); i <= end; i++) 
		{
			if (n % i == 0)
				return i;
		}
		
		return n;
	}
	
	public boolean isCorrect()
	{
		return true;
	}
	
	public int getID()
	{
		return 3;
	}

	public String getProblemDescription()
	{
		return null;
	}
	
}
