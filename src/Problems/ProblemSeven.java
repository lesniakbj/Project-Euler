package Problems;

import ProjectEulerRunner.Problem;

public class ProblemSeven extends Problem 
{

	public String run() 
	{
		int totalPrimes = 0;
		long count = 0;
		while(totalPrimes < 10001)
		{
			if(isPrime(count))
			{
				totalPrimes++;
				count++;
			}
			else
				count++;
		}
		
		return "The 10 001st prime number: " + Long.toString(--count);
	}
	
	public boolean isCorrect()
	{
		return true;
	}
	
	private boolean isPrime(long n)
	{
		if(n < 2) 
			return false;
		
		if(n == 2 || n == 3) 
			return true;
		    
		if(n%2 == 0 || n%3 == 0) 
			return false;
		    
		long sqrtN = (long)Math.sqrt(n)+1;
		
	    for(long i = 6L; i <= sqrtN; i += 6) 
	    {
	        if(n%(i-1) == 0 || n%(i+1) == 0) 
	        	return false;
	    }
	    
	    return true;
	}
}
