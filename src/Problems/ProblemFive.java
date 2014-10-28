package Problems;

import ProjectEulerRunner.Problem;

public class ProblemFive implements Problem 
{

	public String run() 
	{
		int current = 1;
		boolean found = false;
		
		while(!found)
		{
			if(current % 1 != 0 || current % 2 != 0 || current % 3 != 0 || current % 4 != 0 ||
					current % 5 != 0 || current % 6 != 0 || current % 7 != 0 || current % 8 != 0 ||
					current % 9 != 0 || current % 10 != 0 ||
					current % 11 != 0 || current % 12 != 0 || current % 13 != 0 || current % 14 != 0 ||
					current % 15 != 0 || current % 16 != 0 || current % 17 != 0 || current % 18 != 0 ||
					current % 19 != 0 || current % 20 != 0)
				current++;
			else
				found = true;
		}
		
		return "Smallest positive number that is evenly divisible by all of the numbers from 1 to 20: " + Integer.toString(current);
	}
	
	public boolean isCorrect()
	{
		return true;
	}
	
	public int getID()
	{
		return 5;
	}

}
