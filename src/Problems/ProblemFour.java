package Problems;

import ProjectEulerRunner.Problem;

public class ProblemFour implements Problem
{
	// Problem 4: 
	// A palindromic number reads the same both ways. The largest palindrome made 
	// from the product of two 2-digit numbers is 9009 = 91 × 99.
	//
	// Find the largest palindrome made from the product of two 3-digit numbers.
	public String run() 
	{
		int highest = 0;
		
		for(int i = 999; i >= 100; i--)
		{
			for(int n = 999; n >= 100; n--)
			{
				String number = Integer.toString(n * i);
				String palin = new StringBuilder(number).reverse().toString();
				
				if(number.equalsIgnoreCase(palin))
				{
					if(Integer.parseInt(number) > highest)
						highest = Integer.parseInt(number);
				}
			}
		}
		
		return "Largest palindrome made from the product of two 3-digit numbers: " + Integer.toString(highest);
	}
	
	public boolean isCorrect()
	{
		return true;
	}
	
	public int getID()
	{
		return 4;
	}
}
