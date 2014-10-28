package Problems;

import java.io.File;
import java.math.BigInteger;
import java.util.Scanner;

public class ProblemThirteen extends ProjectEulerRunner.Problem 
{
	public String run()
	{
		String[] data = null;	
		try 
		{
			data = getData();
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		
		/*
		for(int i = 0; i < data.length; i++)
			System.out.println(i + " " + data[i]);
		*/
		
		BigInteger sum = BigInteger.ZERO;
		for(int i = 0; i < data.length; i++)
			sum = sum.add( new BigInteger(data[i]));
		
		return "First ten digits of the sum of the following one-hundred 50-digit numbers: " + sum.toString().substring(0, 10);
	}
	
	@SuppressWarnings("resource")
	private static String[] getData() throws Exception
	{

		String input = null;
		Scanner scan = new Scanner(new File("src/problems/dataFile.dat"));
		String[] records = new String[100];
		
		int n = 0;
		while(scan.hasNextLine())
		{
			input = scan.nextLine();
			
			records[n] = input;
			n++;
		}
		
		return records;
	}
	
	public boolean isCorrect()
	{
		return true;
	}
}
