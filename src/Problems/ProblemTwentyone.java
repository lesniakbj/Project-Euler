package Problems;

import ProjectEulerRunner.Problem;

public class ProblemTwentyone implements Problem 
{
	public String run() 
	{
		int total = 0;
		for(int i = 1; i <= 10000; i++)
		{
			int sum = sumOfDivisors(i);
			
			if(sumOfDivisors(sum) == i && i != sum)
			{
				total += i;
			}
		}
		
		return "Evaluate the sum of all the amicable numbers under 10000: " + Integer.toString(total);
	}
	
	private int sumOfDivisors(int num)
	{
		int sum = 0;
		for(int i = num - 1; i >= 1; i--)
		{
			if(num % i == 0)
				sum += i;
		}
		
		return sum;
	}

	@Override
	public boolean isCorrect() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return 21;
	}

}
