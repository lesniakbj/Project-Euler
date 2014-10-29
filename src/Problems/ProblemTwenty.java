package Problems;

import java.math.BigInteger;

import ProjectEulerRunner.Problem;

public class ProblemTwenty implements Problem 
{

	public String run() 
	{
		String num = factorial(100).toString();
		
		int sum = 0;
		for(int i = 0; i < num.length(); i++)
			sum += num.charAt(i) - '0';
		
		return "Find the sum of the digits in the number 100!: " + Integer.toString(sum);
	}
	
	public static BigInteger factorial(int n) 
	{
		if (n < 0)
			throw new IllegalArgumentException("Factorial of negative number");
		
		BigInteger prod = BigInteger.ONE;
		
		for (int i = 2; i <= n; i++)
			prod = prod.multiply(BigInteger.valueOf(i));
		
		return prod;
	}

	public boolean isCorrect() 
	{
		return true;
	}

	public int getID() 
	{
		return 20;
	}

}
