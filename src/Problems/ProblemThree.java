package Problems;

import ProjectEulerRunner.Problem;

public class ProblemThree extends Problem 
{
	
	private static final long NUMBER = 600851475143l;

	public String run() 
	{
		long factor = 0;
		for(long i = 2; i < NUMBER; i++)
		{
			if(isPrime(i))
				if(i > factor)
					factor = i;
		}
		
		return Long.toString(factor);
	}
	
	private boolean isPrime(long num)
	{
		if ((num & 1) == 0) 
			return false; // checks divisibility by 2
		
		for (long i=3; i*i<=num; i+=2)
		{
			if (num % i == 0) 
			      return false;
			  
	    }
		
		return true;
	}
	
	public boolean isCorrect()
	{
		return false;
	}

}
