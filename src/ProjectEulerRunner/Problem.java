package ProjectEulerRunner;

import java.io.IOException;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;

public interface Problem 
{
	public String run();
	
	public default String getProblemDescription() throws ParserConfigurationException, SAXException, IOException
	{	
		String theDescription = DescriptionScraper.getProblemDescription(getID());
		
		theDescription = theDescription.replace("less than", "<");
		theDescription = theDescription.replace("greater than", ">");
		theDescription = theDescription.replace("&amp;", "&");
		
		return theDescription;
	}
	
	public boolean isCorrect();
	public int getID();
}
