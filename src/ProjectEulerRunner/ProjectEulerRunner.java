package ProjectEulerRunner;

import static java.lang.System.*;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ProjectEulerRunner 
{
	private static final int NUM_OF_PROBLEMS = 20;
	private static final Scanner scan = new Scanner(in);
	private static final boolean LOOKING_FOR_MISSING = true;
	
	public static void main(String[] args) throws InstantiationException, IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException, IOException 
	{
		for(;;)
		{
			out.println("Welcome to the Project Euler problem solver!");
			out.println("==========================================================");
			out.println("Total Number of Problems: " + NUM_OF_PROBLEMS);
			out.println("==========================================================");
			out.println();
			out.println("What would you like to do?");
			out.println();
			out.println("(1) Run all complete Project Euler problems.");
			out.println("(2) Run a specific problem number. Between 1 and " + NUM_OF_PROBLEMS);
			out.println("(3) Get all problem descriptions.");
			out.println("(4) Get a specific problem description.");
			out.println("(5) View all incomplete problems");
			out.println("(6) View all unstarted problems");
			out.println("(7) Basic problem statistics. (Percentage Correct, etc.)");
			out.println("(8) Advanced problem statistics. (Average Run Times, Collated Data, etc.");
			out.println("(0) Exit");
			out.println();
			out.print("Your choice: ");
			
			int input = scan.nextInt();
			
			
			switch(input)
			{
				case 0:
					exit(0);
					break;
				case 1:
					List<Integer> ranProblems;
					ranProblems = runAllProblems(!LOOKING_FOR_MISSING);
					
					out.println("Problems ran: " + ranProblems.toString());
					wt();
					
					break;
				case 2:
					out.print("Problem to run: ");
					int run = scan.nextInt();
					
					runSpecificProblem(run);
					wt();
				case 5:
					List<Integer> missingProblems;
					missingProblems = runAllProblems(LOOKING_FOR_MISSING);
					
					out.println("Incomplete problems: " + missingProblems.toString());
					wt();
					
					break;
				case 6:
					List<Integer> notStarted;
					notStarted = unstartedProblems();
					
					out.println("Problems not started yet: " + notStarted.toString());
					wt();
					
					break;
			}
		}
		
	}
	
	private static List<Integer> runAllProblems(boolean missing) throws InstantiationException, IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException, IOException 
	{
		List<Integer> theList = new ArrayList<>(0);
		
		for(int i = 1; i <= NUM_OF_PROBLEMS; i++)
		{
			Class<?> cls = null; 
			
			String num = EnglishNumberToWords.convert(i);
			num = capitalize(num);
			
			try {
				cls = Class.forName("Problems.Problem" + num);
			}
			catch(ClassNotFoundException e) {
				//out.println("Problem " + EnglishNumberToWords.convert(i) + " not started yet!");
				//out.println();
			}
			
			if(cls != null)
			{
				Object obj = cls.newInstance();
				Method method = cls.getDeclaredMethod("isCorrect");
				
				Object ret = method.invoke(obj);

				if((boolean)ret != false && missing == false)
				{
					String str = cls.toString().substring(6);
					out.println(str + ", Complete!");
					
					method = cls.getDeclaredMethod("run");
					Object run = method.invoke(obj);
					out.println(run);
					
					out.println();
					
					theList.add(i);
				}
				
				if((boolean)ret == false && missing == true)
				{
					String str = cls.toString().substring(6);
					out.println(str + ", Incomplete!");
					out.println();
					
					theList.add(i);
				}
				
			}			
		}
		
		return theList;
	}
	
	private static List<Integer> unstartedProblems()
	{
		List<Integer> theList = new ArrayList<>(0);
		
		for(int i = 1; i <= NUM_OF_PROBLEMS; i++)
		{
			Class<?> cls = null; 
			
			String num = EnglishNumberToWords.convert(i);
			num = capitalize(num);
			
			try {
				cls = Class.forName("Problems.Problem" + num);
			}
			catch(ClassNotFoundException e) {
				out.println("Problem " + EnglishNumberToWords.convert(i) + " not started yet!");
				out.println();
				
				theList.add(i);
			}
		}
		
		return theList;
	}
	
	private static void runSpecificProblem(int n) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException
	{
		Class<?> cls = null; 
		
		String num = EnglishNumberToWords.convert(n);
		num = capitalize(num);
		
		try {
			cls = Class.forName("Problems.Problem" + num);
		}
		catch(ClassNotFoundException e) {
			out.println("Problem " + EnglishNumberToWords.convert(n) + " not started yet!");
			out.println();
		}
		
		if(cls != null)
		{
			Object obj = cls.newInstance();
			Method method = cls.getDeclaredMethod("isCorrect");
			
			Object ret = method.invoke(obj);

			if((boolean)ret != false)
			{
				String str = cls.toString().substring(6);
				out.println(str + ", Complete!");
				
				method = cls.getDeclaredMethod("run");
				Object run = method.invoke(obj);
				out.println(run);	
				out.println();
			}
			
			if((boolean)ret == false)
			{
				String str = cls.toString().substring(6);
				out.println(str + ", Incomplete!");
				out.println();
			}
			
		}			
	}
	
	private static String capitalize(String line)
	{
	  return Character.toUpperCase(line.charAt(0)) + line.substring(1);
	}
	
	private static void wt() throws IOException
	{
		out.println("Press any key to return to the menu!");
		in.read();
	}

}
