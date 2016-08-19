package org.emmerson.xsltunit.core.jaxb.config;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;attribute name="description" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="key" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="xml-sources" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="xsd" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="xslt" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "")
@XmlRootElement(name = "test")
public class Test {

    @XmlAttribute(name = "description", required = true)
    protected String description;
    @XmlAttribute(name = "key", required = true)
    protected String key;
    @XmlAttribute(name = "xml-sources", required = true)
    protected String xmlSources;
    @XmlAttribute(name = "xsd", required = true)
    protected String xsd;
    @XmlAttribute(name = "xslt", required = true)
    protected String xslt;

    /**
     * Gets the value of the description property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the value of the description property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescription(String value) {
        this.description = value;
    }

    /**
     * Gets the value of the key property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKey() {
        return key;
    }

    /**
     * Sets the value of the key property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKey(String value) {
        this.key = value;
    }

    /**
     * Gets the value of the xmlSources property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getXmlSources() {
        return xmlSources;
    }

    /**
     * Sets the value of the xmlSources property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setXmlSources(String value) {
        this.xmlSources = value;
    }

    /**
     * Gets the value of the xsd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getXsd() {
        return xsd;
    }

    /**
     * Sets the value of the xsd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setXsd(String value) {
        this.xsd = value;
    }

    /**
     * Gets the value of the xslt property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getXslt() {
        return xslt;
    }

    /**
     * Sets the value of the xslt property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setXslt(String value) {
        this.xslt = value;
    }

}
