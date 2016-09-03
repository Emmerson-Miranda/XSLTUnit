package org.emmerson.xsltunit.maven.json;

import java.io.PrintStream;

import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.codehaus.jettison.mapped.Configuration;
import org.codehaus.jettison.mapped.MappedNamespaceConvention;
import org.codehaus.jettison.mapped.MappedXMLStreamReader;
import org.codehaus.plexus.util.StringUtils;
import org.emmerson.xsltunit.core.XsltUnitException;

public class ConvertToXml {

	public static void main(String[] args) throws Throwable {
		PrintStream ps = System.out;
		String payload = "{\"customer\":{\"id\":123,\"first-name\":\"Jane\",\"last-name\":\"Doe\",\"address\":{\"street\":\"123 A Street\"},\"phone-number\":[{\"@type\":\"work\",\"$\":\"555-1111\"},{\"@type\":\"cell\",\"$\":\"555-2222\"}]}}";
		convertToXML(payload, ps, "jsonObject");
	}

	/**
	 * Convert a JSON string in an XML document
	 * 
	 * @param jsonPayload
	 *            json string
	 * @param ps
	 * @param wrap
	 *            name of the main object to wrap the xml content
	 * @throws JSONException
	 * @throws XMLStreamException
	 */
	public static void convertToXML(String jsonPayload, PrintStream ps,
			String wrap) throws XsltUnitException {
		try {
			JSONObject obj = new JSONObject(jsonPayload);
			convertToXML(obj, ps, wrap);
		} catch (JSONException e) {
			throw new XsltUnitException(e);
		}

	}

	/**
	 * Convert a JSON object into an XML
	 * 
	 * @param jsonPayload
	 * @param ps
	 * @param wrap
	 *            Name of the main object to wrap the xml content
	 * @throws JSONException
	 * @throws XMLStreamException
	 */
	public static void convertToXML(JSONObject jsonPayload, PrintStream ps,
			String wrap) throws XsltUnitException {
		try {
			Configuration config = new Configuration();
			MappedNamespaceConvention con = new MappedNamespaceConvention(
					config);
			XMLStreamReader xmlStreamReader = new MappedXMLStreamReader(jsonPayload, con);

			ps.println("<?xml version='1.0' encoding='UTF-8' standalone='yes' ?>");
			if (!StringUtils.isEmpty(wrap))
				ps.println("<" + wrap + ">");
			while (xmlStreamReader.hasNext()) {
				printEvent(xmlStreamReader, ps);
				xmlStreamReader.next();
			}
			if (!StringUtils.isEmpty(wrap))
				ps.println("</" + wrap + ">");
		} catch (XMLStreamException | JSONException e) {
				throw new XsltUnitException(e);
		}
	}

	private static void printEvent(XMLStreamReader xmlr, PrintStream ps) {

		switch (xmlr.getEventType()) {

		case XMLStreamConstants.START_ELEMENT:
			ps.print("<");
			ps.print(xmlr.getName());
			for (int i = 0; i < xmlr.getAttributeCount(); i++) {
				ps.print(" ");
				ps.print(xmlr.getAttributeLocalName(i));
				ps.print("='" + xmlr.getAttributeValue(i) + "'");
			}
			ps.print(">");
			break;
		case XMLStreamConstants.END_ELEMENT:
			ps.print("</");
			ps.print(xmlr.getName());
			ps.println(">");
			break;
		case XMLStreamConstants.SPACE:
		case XMLStreamConstants.CHARACTERS:
			int start = xmlr.getTextStart();
			int length = xmlr.getTextLength();
			ps.print(new String(xmlr.getTextCharacters(), start, length));
			break;

		case XMLStreamConstants.PROCESSING_INSTRUCTION:
			ps.print("<?");
			ps.print(xmlr.getText());
			ps.print("?>");
			break;

		case XMLStreamConstants.CDATA:
			ps.print("<![CDATA[");
			start = xmlr.getTextStart();
			length = xmlr.getTextLength();
			ps.print(new String(xmlr.getTextCharacters(), start, length));
			ps.print("]]>");
			break;

		case XMLStreamConstants.COMMENT:
			ps.print("<!--");
			ps.print(xmlr.getText());
			ps.print("-->");
			break;

		case XMLStreamConstants.ENTITY_REFERENCE:
			ps.print(xmlr.getLocalName() + "=");
			if (xmlr.hasText())
				ps.print("[" + xmlr.getText() + "]");
			break;
		}

	}

}
