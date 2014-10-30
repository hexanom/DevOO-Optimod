package fr.insalyon.optimod.models.factories;

import java.io.File;
import java.io.IOException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

/**
 * XML Loader
 */
public abstract class XMLFactoryBase {
    private String mPath;

    /**
     * Path constructor
     * @param path The path to the file
     */
    public XMLFactoryBase(String path) {
        mPath = path;
    }

    /**
     * Loads the root node from an XML File
     * @return A dom node
     */
    protected Element loadXMLFile() {
    	
    	Element racine = null;
    	if(mPath != null && !mPath.isEmpty())
    	{
        	final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        	try {
	        	    DocumentBuilder builder = factory.newDocumentBuilder();
	        	    Document document= builder.parse(new File(mPath));
	        	    
	        	     racine = document.getDocumentElement();
        	    
        	}catch (ParserConfigurationException pce) {
                System.out.println("DOM parser configuration error");
            } catch (SAXException se) {
                System.out.println("Parsing document error");
            } catch (IOException ioe) {
                System.out.println("Input/Output error");
            }
    	}
        return racine; 
    }
}
