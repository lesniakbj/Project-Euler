package Problems;

import ProjectEulerRunner.Problem;

public class ProblemTen extends Problem 
{
	private static final int NUMBER = 2000000;
	public String run() 
	{
		double sum = 0d;
		
		int n = 0;
		while(n < NUMBER)
		{
			if(isPrime(n))
				sum += n;
			
			n++;
		}
		
		return "Sum of all the primes below two million: " + Double.toString(sum);
	}
	
	private static boolean isPrime(long n)
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
	
	public boolean isCorrect()
	{
		return true;
	}

}
