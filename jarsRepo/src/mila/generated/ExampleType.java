//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v1.0.5-b16-fcs 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2008.12.01 at 02:23:12 PM IST 
//

package mila.generated;

/**
 * Hebrew examples regarding the Sense of the Item. Java content class for
 * ExampleType complex type.
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this java content object. (defined at
 * file:/C:/Sun/jwsdp-1.6/jaxb/lib/hebrew_MWlexicon_12_11_2008.xsd line 205)
 * <p>
 * 
 * <pre>
 * &lt;complexType name="ExampleType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;attribute name="id" use="required" type="{http://www.w3.org/2001/XMLSchema}token" />
 *       &lt;attribute name="phrase" use="required" type="{http://www.w3.org/2001/XMLSchema}token" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 */
public interface ExampleType {

	/**
	 * Gets the value of the id property.
	 * 
	 * @return possible object is {@link java.lang.String}
	 */
	java.lang.String getId();

	/**
	 * Gets the value of the phrase property.
	 * 
	 * @return possible object is {@link java.lang.String}
	 */
	java.lang.String getPhrase();

	/**
	 * Sets the value of the id property.
	 * 
	 * @param value
	 *            allowed object is {@link java.lang.String}
	 */
	void setId(java.lang.String value);

	/**
	 * Sets the value of the phrase property.
	 * 
	 * @param value
	 *            allowed object is {@link java.lang.String}
	 */
	void setPhrase(java.lang.String value);

}
