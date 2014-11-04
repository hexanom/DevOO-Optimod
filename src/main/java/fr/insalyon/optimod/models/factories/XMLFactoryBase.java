package fr.insalyon.optimod.models.factories;

import java.io.File;
import java.io.IOException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

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
     * Validate the given xml file according to the given xsd file
     * @param xml
     * @param xsd
     * @throws IOException
     * @throws SAXException
     */
    private boolean validateXMLwithXSD(File xml, File xsd) {

        Source xmlFile = new StreamSource(xml);
        SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        try {
            Schema schema = schemaFactory.newSchema(xsd);
            Validator validator = schema.newValidator();
            validator.validate(xmlFile);
        } catch (SAXException se) {
            System.out.println("validateXMLwithXSD : create schema");
            return false;
        } catch (IOException ioe) {
            System.out.println("validateXMLwithXSD : Input/Output error ");
            return false;
        }

        return true;

    }
    /**
     * Loads the root node from an XML File
     * @return A dom node
     */
    protected Element loadXMLFile(String xsdFile) {

        Element racine = null;
        if(mPath != null && !mPath.isEmpty())
        {
            boolean isValid = validateXMLwithXSD(new File(mPath), new File(xsdFile));

            if(isValid)
            {
                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                try {
                    DocumentBuilder builder = factory.newDocumentBuilder();
                    Document document= builder.parse(new File(mPath));

                    racine = document.getDocumentElement();

                }catch (ParserConfigurationException pce) {
                    System.out.println("DOM parser configuration error");
                    return null;
                } catch (SAXException se) {
                    System.out.println("Parsing document error");
                    return null;
                } catch (IOException ioe) {
                    System.out.println("Input/Output error");
                    return null;
                }
            }
        }
        return racine;
    }
}
