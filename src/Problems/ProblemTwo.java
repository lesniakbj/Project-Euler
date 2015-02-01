package Problems;

import ProjectEulerRunner.Problem;

public class ProblemTwo implements Problem 
{
	// Problem 2: 
	// Each new term in the Fibonacci sequence is generated by adding the 
	// previous two terms. By starting with 1 and 2, the first 10 terms will be:
	//					1, 2, 3, 5, 8, 13, 21, 34, 55, 89, ...
	// By considering the terms in the Fibonacci sequence whose values do 
	// not exceed four million, find the sum of the even-valued terms.
	
	private static int count = 0;
	private static final int NUMBER = 4000000;

	public String run()
	{
		return "Sum of the even-valued fibonacci terms less than 4 Million: " + Integer.toString(fib(NUMBER));
	}
	
	// Naive Solution
	private static int fib(int term)
	{
		int num1 = 1, num2 = 1;
		int result = 0;
		
		while(result < term)
		{
			if(result % 2 == 0)
				count += result;
			
			result = num1 + num2;
			num2 = num1;
			num1 = result;
		}
		
		return count;
	}

	public boolean isCorrect()
	{
		return true;
	}
	
	public int getID()
	{
		return 2;
	}
}