package Problems;

import ProjectEulerRunner.Problem;

public class ProblemTwelve extends Problem 
{

	public String run() 
	{

		int triNum = 0;
		int n = 1;
		
		while(getNumDivisors(triNum) < 500)
		{
			triNum += n;
			n++;
		}
		
		return "Value of the first triangle number to have over five hundred divisors: " + Integer.toString(triNum);
	}
	
	private static int getNumDivisors(int num)
	{
		int nod = 0;
	    int sqrt = (int) Math.sqrt(num);
	 
	    for(int i = 1; i<= sqrt; i++)
	    {
	        if(num % i == 0)
	        {
	            nod += 2;
	        }
	    }
	    
	    if (sqrt * sqrt == num) 
	    {
	        nod--;
	    }
	 
	    return nod;
	}
	
	public boolean isCorrect()
	{
		return true;
	}
}
