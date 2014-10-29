package ProjectEulerRunner;

import java.io.IOException;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;

public interface Problem 
{
	public String run();
	
	public default String getProblemDescription() throws ParserConfigurationException, SAXException, IOException
	{	
		return DescriptionScraper.getProblemDescription(getID());
	}
	
	public boolean isCorrect();
	public int getID();
}
