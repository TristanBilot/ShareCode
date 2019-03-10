package services;

import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import users.UsersDatabase;

public class CheckXmlFileService implements IService {
	private String cheminFichier;
	
	public CheckXmlFileService(String string) {
		this.cheminFichier = string;
	}
	
	public String getResultOfService() throws ParserConfigurationException {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setValidating(false);
		factory.setNamespaceAware(true);

		DocumentBuilder builder = factory.newDocumentBuilder();

		builder.setErrorHandler(new SimpleErrorHandler());    
		// the "parse" method also validates XML, will throw an exception if misformatted
		try {
			// ne marche pas, a REFAIRE
			Document document = builder.parse(new InputSource(UsersDatabase.DEFAULT_FTP_URL + "analyse/" + cheminFichier + ".xml"));
			return "This document is an XML file.";
		} catch (SAXException | IOException e) {	
			e.printStackTrace();
			return "This document is not an XML file.";
		}
	}
	
	public class SimpleErrorHandler implements ErrorHandler {
	    public void warning(SAXParseException e) throws SAXException {
	        System.out.println(e.getMessage());
	    }

	    public void error(SAXParseException e) throws SAXException {
	        System.out.println(e.getMessage());
	    }

	    public void fatalError(SAXParseException e) throws SAXException {
	        System.out.println(e.getMessage());
	    }
	}

	@Override
	public void run() {
		try {
			getResultOfService();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}		
	}
}
