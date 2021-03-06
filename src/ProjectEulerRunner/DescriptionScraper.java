package ProjectEulerRunner;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
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

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.jsoup.*;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;


public class DescriptionScraper 
{
	private static int NUMBER_OF_PROBLEMS = 0;
	private static int LINE_WORD_LENGTH = 0;
	private static final int MAX_NUMBER_OF_CONNECTIONS = 48;
	
	
	private DescriptionScraper(){}
	
	public static SortedMap<Integer,String> scrapeAllDescriptions() throws IOException, InterruptedException, ExecutionException
	{
		SortedMap<Integer, String> theList = new TreeMap<>();
		
		ExecutorService exec = Executors.newFixedThreadPool(MAX_NUMBER_OF_CONNECTIONS);
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
	
	public static String getProblemDescription(int n) throws ParserConfigurationException
	{
		String filler = "=======================================================";
		File descriptionFile = new File("src/ProjectEulerRunner/descriptions.xml");
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuild = dbFactory.newDocumentBuilder();
		
		Document theDoc = null;
		try {
			theDoc = docBuild.parse(descriptionFile);
		} catch (SAXException | IOException e) {
			System.out.println(e.getMessage());
			//e.printStackTrace();
		}
		
		theDoc.getDocumentElement().normalize();
		
		NodeList allNodes = theDoc.getElementsByTagName("Problem");

		for(int i = 0; i < allNodes.getLength(); i++)
		{
			Node theNode = allNodes.item(i);
			
			if(theNode.getNodeType() == Node.ELEMENT_NODE)
			{
				Element problemElement = (Element) theNode;
				NodeList problemIDList = problemElement.getElementsByTagName("ProblemID");		
				
				Element problemIDElm = (Element) problemIDList.item(0);				
				NodeList problemID = problemIDElm.getChildNodes();
				
				int id = Integer.parseInt(((Node) problemID.item(0)).getNodeValue());
				
				if(id == n)
				{
					NodeList descriptionList = problemElement.getElementsByTagName("Description");		
					
					Element descriptionElm = (Element)descriptionList.item(0);				
					NodeList description = descriptionElm.getChildNodes();

					Node theDescription = ((Node) description.item(0));
					
					if(theDescription != null)
					{
						String input = theDescription.getNodeValue();
						
						return filler + "\n" + "Problem " + id + " description: \n" + input + "\n" + filler;
					}
					
				}
				
			}
		}
		
		return "No Description Found!";
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
	
	public static boolean buildDescriptionXML(SortedMap<Integer, String> descriptions) throws FileNotFoundException, UnsupportedEncodingException 
	{		
		File xmlFile = new File("src/ProjectEulerRunner/descriptions.xml");
		PrintWriter theWriter = new PrintWriter(xmlFile, "UTF-8");
		
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
	
	
	private static String cleanSingleResponse(String response)
	{
		String ret = "";
		ret = Jsoup.parse(response).text();
		
		String[] theParts = ret.split(" ");
		
		String des = "";
		int currentTotal = 0;
		for(int i = 0; i < theParts.length; i++)
		{
			if(theParts[i].contains("<"))
				theParts[i] = theParts[i].replace("<", "less than");
			
			if(theParts[i].contains(">"))
				theParts[i] = theParts[i].replace("<", "greater than");
			
			if(theParts[i].contains("&"))
				theParts[i] = theParts[i].replace("&", "&amp;");

				
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