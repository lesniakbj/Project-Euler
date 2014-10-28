package ProjectEulerRunner;

import static java.lang.System.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

public class ProjectEulerRunner 
{
	private static final int NUM_OF_PROBLEMS = 486;
	private static final Scanner scan = new Scanner(in);
	
	private static SortedMap<Integer, Problem> eulerProblems = new TreeMap<>();
	private static String retString = "";
	
	private enum RunMode 
	{
		RUN_ALL,
		SEARCH_INCORRECT,
		SEARCH_UNSTARTED
	}
	
	private enum ProblemStatus
	{
		COMPLETE,
		INCORRECT,
		UNSTARTED
	}
	
	public static void main(String[] args) throws InstantiationException, IllegalAccessException, IOException, ParserConfigurationException, SAXException
	{
		populateProblemSet();
		
		test(1);
		
		out.println("Welcome to the Project Euler problem solver!");
		out.println();
		out.println("Loading problems...");
		int numIncorrect = runAllProblems(RunMode.SEARCH_INCORRECT).size();
		int numUnstarted = runAllProblems(RunMode.SEARCH_UNSTARTED).size();
		
		for(;;)
		{
			out.println("==========================================================");
			out.println("Existing Problems: " + NUM_OF_PROBLEMS + "	Loaded Problems: " + eulerProblems.size());
			out.println("Incorrect Problems: " + numIncorrect + "	Unstarted Problems: " +  numUnstarted);
			out.println("==========================================================");
			out.println();
			out.println("What would you like to do?");
			out.println();
			out.println("(1) Run all complete Project Euler problems.");
			out.println("(2) Run a specific problem number. Between 1 and " + NUM_OF_PROBLEMS);
			out.println("(3) Get all problem descriptions.");
			out.println("(4) Get a specific problem description.");
			out.println("(5) View all incorrect problems");
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
					ranProblems = runAllProblems(RunMode.RUN_ALL);
					
					out.println("Problems ran: " + ranProblems.toString());
					wt();
					
					break;
				case 2:
					out.print("Problem to run: ");
					int run = scan.nextInt();
					
					runSpecificProblem(run);
					out.println(retString);
					wt();
					break;
				case 5:
					List<Integer> missingProblems;
					missingProblems = runAllProblems(RunMode.SEARCH_INCORRECT);
					
					out.println("Incorrect problems: " + missingProblems.toString());
					wt();
					
					break;
				case 6:
					List<Integer> notStarted;
					notStarted = runAllProblems(RunMode.SEARCH_UNSTARTED);
					
					out.println("Problems not started yet: " + notStarted.toString());
					wt();
					
					break;
			}
		}
		
	}
	
	private static void populateProblemSet() throws InstantiationException, IllegalAccessException
	{
		for(int i = 1; i <= NUM_OF_PROBLEMS; i++)
		{
			try {			
				String num = EnglishNumberToWords.convert(i);
				num = capitalize(num);
				
				num = num.replace(" ", "");
				
				Class<?> cls = Class.forName("Problems.Problem" + num); 
				
				if(Problem.class.isAssignableFrom(cls))
				{
					Problem inst = Problem.class.cast(cls.newInstance());
					eulerProblems.put(inst.getID(), inst);
				}
			}
			catch(ClassNotFoundException e) {
				//out.println("Problem " + EnglishNumberToWords.convert(i) + " not started yet!");
				//out.println();
			}
		}
	}
	
	private static List<Integer> runAllProblems(RunMode mode)
	{
		List<Integer> theList = new ArrayList<>(0);
		
		for(int i = 1; i <= NUM_OF_PROBLEMS; i++)
		{
			ProblemStatus status = runSpecificProblem(i);
			
			if(status == ProblemStatus.COMPLETE && mode == RunMode.RUN_ALL)
			{
				out.println(retString);
				theList.add(i);
			}
			
			
			if(status == ProblemStatus.UNSTARTED && mode == RunMode.SEARCH_UNSTARTED)
				theList.add(i);
			
			
			if(status == ProblemStatus.INCORRECT && mode == RunMode.SEARCH_INCORRECT)
				theList.add(i);
			
		}
		
		return theList;
	}

	
	private static ProblemStatus runSpecificProblem(int n)
	{
		Problem runner = eulerProblems.get(n);
		
		if(runner != null && runner.isCorrect())
		{
			retString = runner.getID() + ": " + runner.run();
			return ProblemStatus.COMPLETE;
		}
		else
		{
			if(runner == null)
				return ProblemStatus.UNSTARTED;
			else
				return ProblemStatus.INCORRECT;
			
		}
	}
	
	private static void test(int n) throws ParserConfigurationException, SAXException, IOException
	{
		Problem runner = eulerProblems.get(n);
		
		out.println(runner.getProblemDescription());
		out.println(runner.run());
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
