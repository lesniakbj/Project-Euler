package ProjectEulerRunner;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

public interface Problem 
{
	public String run();
	
	public default String getProblemDescription() throws ParserConfigurationException, SAXException, IOException
	{
		String filler = "=======================================================";
		File descriptionFile = new File("src/ProjectEulerRunner/descriptions.xml");
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuild = dbFactory.newDocumentBuilder();
		
		Document theDoc = docBuild.parse(descriptionFile);
		
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
				
				if(id == getID())
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
		
		return "Long description not created yet!";
		
	}
	
	public boolean isCorrect();
	public int getID();
}
