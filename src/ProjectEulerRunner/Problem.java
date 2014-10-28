package ProjectEulerRunner;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

public interface Problem 
{
	public String run();
	
	public default String getProblemDescription() throws ParserConfigurationException, SAXException, IOException
	{
		File descriptionFile = new File("src/ProjectEulerRunner/descriptions.xml");
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuild = dbFactory.newDocumentBuilder();
		
		Document theDoc = docBuild.parse(descriptionFile);
		
		theDoc.getDocumentElement().normalize();
		
		NodeList allNodes = theDoc.getElementsByTagName("Problem");

		Node theNode = allNodes.item(getID());
		
		return 
		
		return null;
		
	}
	
	public boolean isCorrect();
	public int getID();
}
