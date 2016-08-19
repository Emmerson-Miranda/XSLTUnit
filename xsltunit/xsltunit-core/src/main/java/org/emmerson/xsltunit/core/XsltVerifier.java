package org.emmerson.xsltunit.core;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

/**
 * Validate XSLT outputs against a XSD file
 * 
 * @author Emmerson
 *
 */
public class XsltVerifier {

	private String temporalFolder;
	
	private String suiteKey;
	
	private String testKey;
	
	/**
	 * Constructor. Initialize temporal folder to store transformation outputs.
	 * @param suite suite name
	 * @param test test name
	 */
	public XsltVerifier(String suite, String test){
		File temporal = new File("target/xslt-outputs/");
		temporal.mkdirs();
		temporalFolder = temporal.getAbsolutePath();
		this.suiteKey = suite;
		this.testKey = test;
	}
	
	/**
	 * Validate the output of a XSLT given a XML against a XSD.
	 * @param xsltLocation
	 * @param xmlLocation
	 * @param xsdLocation
	 * @throws Exception
	 */
	public void verifyXSLT(String xsltLocation, String xmlLocation, String xsdLocation) throws XsltUnitException{
		verifyXSLT(
				new File(xsltLocation),
				new File(xmlLocation),
				new File(xsdLocation)
		);
	}
	
	/**
	 * Validate the output of a XSLT given a XML against a XSD.
	 * @param xslt
	 * @param xml
	 * @param xsd
	 * @throws Exception
	 */
	public void verifyXSLT(File xslt, File xml, File ...xsd) throws XsltUnitException{
		try{
			if(!xslt.exists()){
				throw new FileNotFoundException(xslt.getAbsolutePath());
			}
			if(!xml.exists()){
				throw new FileNotFoundException(xml.getAbsolutePath());
			}
	
			verifyXSLT(
					getXSLT(xslt),
					getXMLsource(xml),
					getXSLTvalidator(xsd),
					suiteKey + "_" + testKey + "_" + xml.getName()
			);
		} catch (FileNotFoundException e) {
			throw new XsltUnitException(e);
		}

	}
	
	/**
	 * Validate the output of a XSLT given a XML against a XSD.
	 * @param xslt
	 * @param xml
	 * @param xsd
	 * @param outputFileName
	 * @throws Exception
	 */
	public void verifyXSLT(Transformer xslt, DOMSource xml, Validator xsd, String outputFileName) throws XsltUnitException{
		try {
			DOMResult result = new DOMResult();
			xslt.transform(xml, result);
	
			Document doc = (Document) result.getNode();
			DOMSource ds = new DOMSource(doc);
			writeOutput(ds, outputFileName);
	    
			xsd.validate(ds);
		} catch (SAXException | IOException | TransformerException e) {
			throw new XsltUnitException(e);
		} 
	}
	
	/**
	 * Write XSLT output in filesystem.
	 * @param ds
	 * @param outputFileLocation
	 * @throws Exception
	 */
	private void writeOutput(DOMSource ds, String outputFileLocation) throws XsltUnitException{
		try {
			// Use a Transformer for output
	        TransformerFactory tFactory = TransformerFactory.newInstance();
	        Transformer transformer = tFactory.newTransformer();
			
	        StreamResult result = new StreamResult(new FileOutputStream(temporalFolder + File.separator + outputFileLocation));
	        transformer.transform(ds, result);
		} catch (TransformerException | FileNotFoundException e) {
			throw new XsltUnitException(e);
		}
	}
	
	private Transformer getXSLT(File stylesheet) throws XsltUnitException{
		try {
			StreamSource stylesource = new StreamSource(stylesheet);
			TransformerFactory tFactory = TransformerFactory.newInstance();
		
			return tFactory.newTransformer(stylesource);
		} catch (TransformerConfigurationException e) {
			throw new XsltUnitException(e);
		}
	}
	
	private DOMSource getXMLsource(File datafile) throws XsltUnitException{
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			factory.setNamespaceAware(true);
			DocumentBuilder builder = factory.newDocumentBuilder();
			
			return new DOMSource(builder.parse(datafile));
		} catch (ParserConfigurationException | SAXException | IOException e) {
			throw new XsltUnitException(e);
		}
	}
	
	private Validator getXSLTvalidator(File ...xsdFile) throws XsltUnitException{
		try {
			// create a SchemaFactory capable of understanding WXS schemas
		    SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);

		    // load a WXS schema, represented by a Schema instance
		    ArrayList<StreamSource> schemaFile = new ArrayList<StreamSource>();
		    for(File f : xsdFile){
		    	if(!f.exists()){
					throw new FileNotFoundException(f.getAbsolutePath());
				}
		    	schemaFile.add(new StreamSource(f));
		    }
		    Schema schema = factory.newSchema(schemaFile.toArray(new StreamSource[]{}));
	
		    // create a Validator instance, which can be used to validate an instance document
		    return schema.newValidator();
		} catch (SAXException | FileNotFoundException e) {
			throw new XsltUnitException(e);
		}
	}

}
