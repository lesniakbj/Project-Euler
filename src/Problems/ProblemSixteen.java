package Problems;

import java.math.BigInteger;

import ProjectEulerRunner.Problem;

public class ProblemSixteen implements Problem 
{
	public String run() 
	{
		BigInteger num = pow(new BigInteger("2"), new BigInteger("1000"));
		
		String theNum = num.toString();
		
		int sum = 0;
		for(int i = 0; i < theNum.length(); i++)
		{
			sum += Integer.parseInt(Character.toString(theNum.charAt(i)));
		}
		
		return "Sum of the digits of the number 2^1000: " + Integer.toString(sum);
	}
	
	private BigInteger pow(BigInteger base, BigInteger exponent) 
	{
		BigInteger result = BigInteger.ONE;
		
		while (exponent.signum() > 0) 
		{
		    if (exponent.testBit(0)) 
		    	result = result.multiply(base);
		    
		    base = base.multiply(base);
		    exponent = exponent.shiftRight(1);
		}
		
		return result;
	}
	
	public boolean isCorrect()
	{
		return true;
	}
	
	public int getID()
	{
		return 16;
	}

}
