package Problems;

import java.util.ArrayList;
import java.util.List;
import ProjectEulerRunner.Problem;

public class ProblemFourteen implements Problem 
{
	private static final int NUMBER = 1000000;

	private static List<Integer> theSet = new ArrayList<Integer>(0);
	
	public String run() 
	{
		int longest = 0;
		for(int i = 1; i <= NUMBER; i++)
		{
			generateChain(i);
			
			if(theSet.size() > longest)
				longest = i;
			
			theSet.clear();
		}
		
		return "Longest Collatz Chain, under 1 Million: " + Integer.toString(longest);
	}
	
	private static List<Integer> generateChain(int i)
	{
		int count = i;
		
		while(count != 1)
		{
			theSet.add(count);
			if(count % 2 ==0)
				count = count / 2;
			else
				count = (count * 3) + 1;
		}
		
		theSet.add(1);
		
		return theSet;
	}
	
	public boolean isCorrect()
	{
		return false;
	}
	
	public int getID()
	{
		return 14;
	}
	
	public String getProblemDescription()
	{
		return null;
	}
	
	/*
	private static List<Integer> generateChain(int i)
	{
		theSet.add(i);
		
		if(i == 1)
			return theSet;
		
		if(i % 2 == 0)
			return generateChain(i/2);
		else
			return generateChain((3 * i) + 1);
	}
	*/
}
