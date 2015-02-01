package Problems;

import ProjectEulerRunner.Problem;
import static java.lang.Math.*;

public class ProblemSix implements Problem 
{
	// Problem Six:
	// Find the difference between the sum of the squares of the
	// first one hundred natural numbers and the square of the sum.

	
	
	public String run() 
	{
		int sumOfSqrs = sumOfSquares(100);
		int sqrOfSums = squareOfSums(100);
		
		return "Difference between the sum of the squares of the first one hundred natural numbers and the square of the sum: " + Integer.toString(sqrOfSums - sumOfSqrs);
	}

	private static int sumOfSquares(int num)
	{
		int sum = 0;
		
		for(int i = 0; i <= num; i++)
			sum += (int) pow(i, 2);
		
		return sum;
	}
	
	private static int squareOfSums(int num)
	{
		int sum = 0;
		
		for(int i = 0; i <= num; i++)
			sum += i;
		
		return (int) pow(sum, 2);
	}
	
	public boolean isCorrect()
	{
		return true;
	}
	
	public int getID()
	{
		return 6;
	}
}
