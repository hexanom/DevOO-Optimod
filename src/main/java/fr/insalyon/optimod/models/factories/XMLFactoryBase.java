package fr.insalyon.optimod.models.factories;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;

import fr.insalyon.optimod.models.DeserializationException;
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
    protected Element loadXMLFile(String xsdFile) throws ParserConfigurationException, IOException, SAXException, DeserializationException, URISyntaxException {

        Element racine = null;
        if(mPath != null && !mPath.isEmpty()) {
            URL xsdURL = getClass().getClassLoader().getResource(xsdFile);
            boolean isValid = validateXMLwithXSD(new File(mPath), new File(xsdURL.toURI()));

            if(isValid) {
                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                DocumentBuilder builder = factory.newDocumentBuilder();
                Document document= builder.parse(new File(mPath));

                racine = document.getDocumentElement();
            } else {
                throw new DeserializationException("XML File don't validate XSD");
            }
        }
        return racine;
    }
}
