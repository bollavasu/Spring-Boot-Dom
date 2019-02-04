package com.journaldev.spring;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

@RestController
public class PersonController {
	
	@RequestMapping("/")
	public String welcome() {
		readXml();
		readXmlNodeType();
		readXmlDynamic();
		createNewXml();
		return "Welcome to Spring Boot REST...";
	}
	
	public void readXml() {
		try {
			ClassLoader classLoader = this.getClass().getClassLoader();
			
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			 
			Document document = builder.parse(new File(classLoader.getResource("employees.xml").getFile()));			
			document.getDocumentElement().normalize();
			
			Element elementRoot = document.getDocumentElement();
			System.out.println("Node Name : " + elementRoot.getNodeName());
			System.out.println("Node Value : " + elementRoot.getNodeValue());
			System.out.println("Node Type : " + elementRoot.getNodeType());
			
			//Element element2 = document.getElementById("222");
			
			NodeList nodeList = document.getElementsByTagName("employee");
			
			for (int temp = 0; temp < nodeList.getLength(); temp++)
			{
			 Node node = nodeList.item(temp);
			 System.out.println("");    
			 System.out.println("Node Type : " + node.getNodeType());    
			 if (node.getNodeType() == Node.ELEMENT_NODE)
			 {
			    Element eElement = (Element) node;
			    System.out.println("Employee ID : "    + eElement.getAttribute("id"));
			    System.out.println("Employee Salary : "    + eElement.getAttribute("sal"));
			    System.out.println("First Name : "  + eElement.getElementsByTagName("firstName").item(0).getTextContent());
			    System.out.println("Last Name : "   + eElement.getElementsByTagName("lastName").item(0).getTextContent());
			    System.out.println("City : "   + eElement.getElementsByTagName("city").item(0).getTextContent());
			    System.out.println("Location : "    + eElement.getElementsByTagName("location").item(0).getTextContent());
			 }
			}
			
		} catch(Exception e) {
		  e.printStackTrace();
		}
	}
	
	public void readXmlNodeType() {
		try {
			ClassLoader classLoader = this.getClass().getClassLoader();
			
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			 
			Document document = builder.parse(new File(classLoader.getResource("employees.xml").getFile()));			
			document.getDocumentElement().normalize();
			
			System.out.println("-------------------------------------");
			Element elementRoot = document.getDocumentElement();
			System.out.println("Node Name : " + elementRoot.getNodeName());
			System.out.println("Node Value : " + elementRoot.getNodeValue());
			System.out.println("Node Type : " + elementRoot.getNodeType());
						
			NodeList nodeList = document.getElementsByTagName("employee");
			System.out.println("nodeList length : " + nodeList.getLength());    
			
			for (int temp = 0; temp < 1; temp++)
			{
			 Node node = nodeList.item(temp);
			 System.out.println("");    
			 System.out.println("Node Type : " + node.getNodeType());    
			 if (node.getNodeType() == Node.ELEMENT_NODE)
			 {
			    Element eElement = (Element) node;
			    
			    NamedNodeMap namedNodeMap = eElement.getAttributes();
			    for(int i=0; i<namedNodeMap.getLength(); i++) {
			    	Node anode = namedNodeMap.item(i);
			    	System.out.println("Node Type : " + anode.getNodeType());                               //Node Type = Attribute
			    	System.out.println("$$$$$$$$$Node Name : " + anode.getNodeName());
			    	System.out.println("$$$$$$$$$$$$$$$$$$Node Value : " + anode.getNodeValue());
			    }
			    
			    System.out.println("");
			    Element cElement = (Element) eElement.getElementsByTagName("firstName").item(0);
			    System.out.println("Node Type : " + cElement.getNodeType());                                 //Node Type = Element
			    System.out.println("***********Node Name : " + cElement.getNodeName());
		    	System.out.println("**********************Node Value : " + cElement.getNodeValue());
		    	System.out.println("**********************Node Content : " + cElement.getTextContent());
		    	System.out.println("Content Node Type : " + cElement.getChildNodes().item(0).getNodeType()); //Node Type = CDATA
		    	
		    	System.out.println("");
			    Element ccElement = (Element) eElement.getElementsByTagName("lastName").item(0);
			    System.out.println("Node Type : " + ccElement.getNodeType());
			    System.out.println("***********Node Name : " + ccElement.getNodeName());
		    	System.out.println("**********************Node Value : " + ccElement.getNodeValue());
		    	System.out.println("**********************Node Content : " + ccElement.getTextContent());
		    	System.out.println("Content Node Type : " + ccElement.getChildNodes().item(0).getNodeType()); //Node Type = Text
			 }
			}
			
		} catch(Exception e) {
		  e.printStackTrace();
		}
	}
	
	public void readXmlDynamic() {
		try {
			  ClassLoader classLoader = this.getClass().getClassLoader();
			//Get Document Builder
		      DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		      DocumentBuilder builder = factory.newDocumentBuilder();
		       
		      //Build Document
		      Document document = builder.parse(new File(classLoader.getResource("employees.xml").getFile()));
		       
		      //Normalize the XML Structure; It's just too important !!
		      document.getDocumentElement().normalize();
		       
		      //Here comes the root node
		      Element root = document.getDocumentElement();
		      System.out.println(root.getNodeName());
		       
		      //Get all employees
		      NodeList nList = document.getElementsByTagName("employee");
		      System.out.println("============================");
		       
		      visitChildNodes(nList);	
		      
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void visitChildNodes(NodeList nList)
	   {
	      for (int temp = 0; temp < nList.getLength(); temp++)
	      {
	         Node node = nList.item(temp);
	         if (node.getNodeType() == Node.ELEMENT_NODE)
	         {
	            System.out.println("Node Name = " + node.getNodeName() + "; Value = " + node.getTextContent());
	            //Check all attributes
	            if (node.hasAttributes()) {
	               // get attributes names and values
	               NamedNodeMap nodeMap = node.getAttributes();
	               for (int i = 0; i < nodeMap.getLength(); i++)
	               {
	                   Node tempNode = nodeMap.item(i);
	                   System.out.println("Attr name : " + tempNode.getNodeName()+ "; Value = " + tempNode.getNodeValue());
	               }
	               if (node.hasChildNodes()) {
	                  //We got more childs; Let's visit them as well
	                  visitChildNodes(node.getChildNodes());
	               }
	           }
	         }
	      }
	   }
	
	public void createNewXml() {
	   try {
		   DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

			// root elements
			Document doc = docBuilder.newDocument();
			Element rootElement = doc.createElement("company");
			doc.appendChild(rootElement);

			// staff elements
			Element staff = doc.createElement("Staff");
			rootElement.appendChild(staff);

			// set attribute to staff element
			Attr attr = doc.createAttribute("id");
			attr.setValue("1");
			staff.setAttributeNode(attr);

			// shorten way
			// staff.setAttribute("id", "1");

			// firstname elements
			Element firstname = doc.createElement("firstname");
			firstname.appendChild(doc.createTextNode("yong"));
			staff.appendChild(firstname);

			// lastname elements
			Element lastname = doc.createElement("lastname");
			lastname.appendChild(doc.createTextNode("mook kim"));
			staff.appendChild(lastname);

			// nickname elements
			Element nickname = doc.createElement("nickname");
			nickname.appendChild(doc.createTextNode("mkyong"));
			staff.appendChild(nickname);

			// salary elements
			Element salary = doc.createElement("salary");
			salary.appendChild(doc.createTextNode("100000"));
			staff.appendChild(salary);

			// write the content into xml file
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File("C:/Output/company.xml"));

			// Output to console for testing
			// StreamResult result = new StreamResult(System.out);

			transformer.transform(source, result);

			System.out.println("File saved!");   
	   } catch(Exception e) {
			e.printStackTrace();
		}
	}
}
