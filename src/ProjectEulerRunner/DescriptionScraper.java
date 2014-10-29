package ProjectEulerRunner;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.util.Scanner;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.jsoup.*;


public class DescriptionScraper 
{
	private static int NUMBER_OF_PROBLEMS = 0;
	
	private DescriptionScraper(){}
	
	private static String connectToUrl(String in) throws IOException
	{
		URL theURL = new URL(in);
		BufferedReader theReader = new BufferedReader(new InputStreamReader(theURL.openStream()));
		
		String theData = "";
		String allData = "";
		while((theData = theReader.readLine()) != null)
			allData += theData + "\n";
		
		theReader.close();
		return allData;
	}
	
	private static String parseDescription(String theData) throws IOException
	{
		Scanner theReader = new Scanner(theData);
		String inputLine;
		String output = "";
		boolean startFound = false, endFound = false;
		while((inputLine = theReader.nextLine()) != null)
		{
			if(inputLine.equalsIgnoreCase("<div class=\"problem_content\" role=\"problem\">"))
				startFound = true;
			
			if(inputLine.equalsIgnoreCase("</div><br />") && startFound)
				endFound = true;
			
			if(startFound)
				output += inputLine + "\n";
			
			if(endFound)
				break;
		}
		
		theReader.close();	
		return output;
	}
	
	public static SortedMap<Integer,String> scrapeAllDescriptions() throws IOException, InterruptedException, ExecutionException
	{
		SortedMap<Integer, String> theList = new TreeMap<>();
		int numThreads = 24;
		
		ExecutorService exec = Executors.newFixedThreadPool(numThreads);
		CompletionService<Scrape> service = new ExecutorCompletionService<>(exec);
		
		for(int i = 1; i <= NUMBER_OF_PROBLEMS; i++)
		{
			URLScrapeTask task = new URLScrapeTask("https://projecteuler.net/problem=" + i, i);
			service.submit(task);
		}
		
		for(int n = 1; n <= NUMBER_OF_PROBLEMS; n++)
		{
			Future<Scrape> future = service.take();
			int num = future.get().number;
			String rep = future.get().response;
			
			System.out.println("Problem " + future.get().number + " scraped!");
			
			theList.put(num, rep);
		}

		exec.shutdown();

		return theList;
	}
	
	private static int LINE_WORD_LENGTH = 0;
	private static String cleanSingleResponse(String response)
	{
		String ret = "";
		ret = Jsoup.parse(response).text();
		
		String[] theParts = ret.split(" ");
		
		String des = "";
		int currentTotal = 0;
		for(int i = 0; i < theParts.length; i++)
		{
			int wordLength = theParts[i].length();
			
			currentTotal += wordLength + 1;
			
			if(currentTotal <= LINE_WORD_LENGTH)
				des += theParts[i] + " ";
			else
			{
				currentTotal = 0;
				des += "\n" + theParts[i] + " ";
			}
		}
		
		return des;
	}
	
	public static SortedMap<Integer, String> cleanAllDescriptions(SortedMap<Integer, String> list)
	{
		String cleaned = "";
		SortedMap<Integer, String> theList = list;
		for(int i = 0; i < theList.size(); i++)
		{
			if(theList.get(i) != null)
			{
				cleaned = cleanSingleResponse(theList.get(i));
				
				theList.replace(i, cleaned);
			}
		}
		
		return theList;
	}
	
	public static boolean buildDescriptionXML(SortedMap<Integer, String> descriptions) throws FileNotFoundException 
	{
		//TO-DO:
		// If descriptions.xml exists, delete it and recreate the file
		// Entries should follow the following format:
		// <ProblemDescriptions>
		// 		<Problem>
		//			<ProblemID>i</ProblemID>
		//			<Description>descriptions.get(i)</Description>
		//		</Problem>
		// </ProblemDescription>
		
		File xmlFile = new File("src/ProjectEulerRunner/descriptions.xml");
		PrintWriter theWriter = new PrintWriter(xmlFile);
		
		if(xmlFile.exists())
			xmlFile.delete();
		
		String header = "<ProblemDescriptions>\r\n";
		String footer = "</ProblemDescriptions>";
		
		theWriter.write(header);
		for(int i = 1; i < descriptions.size(); i++)
		{
			theWriter.write("\t<Problem>\r\n");
			theWriter.write("\t\t<ProblemID>" + i + "</ProblemID>\r\n");
			theWriter.write("\t\t<Description>" + descriptions.get(i) + "</Description>\r\n");
			theWriter.write("\t</Problem>\r\n");
			theWriter.flush();
		}
		
		theWriter.write(footer);
		theWriter.close();
		
		return true;
	}

	public static void setNumberOfProblems(int n)
	{
		NUMBER_OF_PROBLEMS = n;
	}
	
	public static void setLineLength(int n)
	{
		LINE_WORD_LENGTH = n;
	}
	
	private static final class URLScrapeTask implements Callable<Scrape>
	{
		private String url;
		private int num;
		
		public URLScrapeTask(String theURL, int n)
		{
			url = theURL;
			num = n;
		}
		
		public Scrape call() throws Exception
		{
			return scrapeTheData(url, num);
		}
	}
	
	private static final class Scrape
	{
		String response;
		int number;
	}
	
	private static Scrape scrapeTheData(String aURL, int n) throws IOException
	{
		Scrape scrape = new Scrape();
		scrape.response = parseDescription(connectToUrl(aURL));
		scrape.number = n;
	
		return scrape;
	}	
}