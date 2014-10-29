package ProjectEulerRunner;

import static java.lang.System.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.concurrent.ExecutionException;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

public class ProjectEulerRunner 
{
	private static final int NUM_OF_PROBLEMS = 486;
	private static int NUM_INCORRECT;
	private static int NUM_UNSTARTED;
	
	private static final boolean CHECK_FOR_DESCRIPTIONS = true;
	private static final boolean PARSE_ALL_DESCRIPTIONS_TO_FILE = true;
	
	private static final boolean TESTS_ENABLED = false;
	
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
	
	public static void main(String[] args) throws InstantiationException, IllegalAccessException, IOException, ParserConfigurationException, SAXException, InterruptedException, ExecutionException
	{
		populateProblemSet();
		initialize();
		for(;;)
		{		
			displayMenu();
			
			int input = scan.nextInt();
			run(input);
		}	
	}
	
	private static void initialize() throws IOException, InterruptedException, ExecutionException, ParserConfigurationException, SAXException
	{
		if(CHECK_FOR_DESCRIPTIONS)
		{
			SortedMap<Integer, String> descriptions = checkForProblemDescriptions();
			
			if(PARSE_ALL_DESCRIPTIONS_TO_FILE)
				parseAllDescriptionsToFile(descriptions);
				
		}
		else
			out.println("Skipping Description Scrape!");
		
		if(TESTS_ENABLED)
			test(7);
		else
			out.println("Skipping System Tests!");
		
		out.println("Welcome to the Project Euler problem solver!");
		out.println();
		out.println("Loading problems...");
		NUM_INCORRECT = runAllProblems(RunMode.SEARCH_INCORRECT).size();
		NUM_UNSTARTED = runAllProblems(RunMode.SEARCH_UNSTARTED).size();
	}
	
	private static void displayMenu()
	{
		out.println("==========================================================");
		out.println("Existing Problems: " + NUM_OF_PROBLEMS + "	Loaded Problems: " + eulerProblems.size());
		out.println("Incorrect Problems: " + NUM_INCORRECT + "	Unstarted Problems: " +  NUM_UNSTARTED);
		out.println("==========================================================");
		out.println();
		out.println("What would you like to do?");
		out.println();
		out.println("(1) Run all complete Project Euler problems.");
		out.println("(2) Run a specific problem number. Between 1 and " + NUM_OF_PROBLEMS);
		out.println("(3) Get a specific problem description.");
		out.println("(4) View all incorrect problems");
		out.println("(5) View all unstarted problems");
		out.println("(6) Basic problem statistics. (Percentage Correct, etc.)");
		out.println("(7) Advanced problem statistics. (Average Run Times, Collated Data, etc.");
		out.println("(0) Exit");
		out.println();
		out.print("Your choice: ");
	}
	
	private static void run(int input) throws ParserConfigurationException, SAXException, InterruptedException, IOException
	{
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
				scan.nextLine();
				
				runSpecificProblem(run);
				out.println(retString);
				wt();
				break;				
			case 3:
				out.print("Problem description to display: ");
				int descrip = scan.nextInt();
				scan.nextLine();
				
				out.println(getProblemDescription(descrip));
				wt();
				break;
			case 4:
				List<Integer> missingProblems;
				missingProblems = runAllProblems(RunMode.SEARCH_INCORRECT);
				
				out.println("Incorrect problems: " + missingProblems.toString());
				wt();
				break;
			case 5:
				List<Integer> notStarted;
				notStarted = runAllProblems(RunMode.SEARCH_UNSTARTED);
				
				out.println("Problems not started yet: " + notStarted.toString());
				wt();
				break;
		}
	}

	private static SortedMap<Integer, String> checkForProblemDescriptions() throws IOException, InterruptedException, ExecutionException
	{
		System.out.println("Retrieving problem descriptions...");
		DescriptionScraper.setNumberOfProblems(NUM_OF_PROBLEMS);
		return DescriptionScraper.scrapeAllDescriptions();
	}
	
	private static void parseAllDescriptionsToFile(SortedMap<Integer, String> descriptions) throws FileNotFoundException, UnsupportedEncodingException
	{
		//Parse to descriptions.xml
		DescriptionScraper.setLineLength(75);
		descriptions = DescriptionScraper.cleanAllDescriptions(descriptions);
		
		if(DescriptionScraper.buildDescriptionXML(descriptions))
			out.println("All descriptions loaded!");
		else
			out.println("Something failed!");

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
	
	private static String getProblemDescription(int n) throws ParserConfigurationException, SAXException, IOException
	{
		String description = DescriptionScraper.getProblemDescription(n);
		if(description != null)
			return description;
		
		return "No description set yet!";
	}
	
	private static void test(int n) throws ParserConfigurationException, SAXException, IOException
	{
		Problem runner = eulerProblems.get(n);
		
		out.println(runner.getProblemDescription());
		out.println(runner.run());
		
		exit(1);
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
