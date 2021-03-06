//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v1.0.5-b16-fcs 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2008.12.01 at 02:23:12 PM IST 
//

package mila.generated;

/**
 * Java content class for ModalExceptionType complex type.
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this java content object. (defined at
 * file:/C:/Sun/jwsdp-1.6/jaxb/lib/hebrew_MWlexicon_12_11_2008.xsd line 1147)
 * <p>
 * 
 * <pre>
 * &lt;complexType name="ModalExceptionType">
 *   &lt;simpleContent>
 *     &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>token">
 *       &lt;attribute name="dotted" type="{http://www.w3.org/2001/XMLSchema}token" default="" />
 *       &lt;attribute name="gender" type="{}GenderType" default="unspecified" />
 *       &lt;attribute name="number" type="{}NumberType" default="unspecified" />
 *       &lt;attribute name="person" type="{}PersonType" default="unspecified" />
 *       &lt;attribute name="script" type="{}ScriptType" default="formal" />
 *       &lt;attribute name="tense" type="{}TenseType" default="unspecified" />
 *       &lt;attribute name="transliterated" use="required" type="{http://www.w3.org/2001/XMLSchema}token" />
 *       &lt;attribute name="undotted" use="required" type="{http://www.w3.org/2001/XMLSchema}token" />
 *     &lt;/extension>
 *   &lt;/simpleContent>
 * &lt;/complexType>
 * </pre>
 * 
 */
public interface ModalExceptionType {

	/**
	 * Gets the value of the dotted property.
	 * 
	 * @return possible object is {@link java.lang.String}
	 */
	java.lang.String getDotted();

	/**
	 * Gets the value of the gender property.
	 * 
	 * @return possible object is {@link java.lang.String}
	 */
	java.lang.String getGender();

	/**
	 * Gets the value of the number property.
	 * 
	 * @return possible object is {@link java.lang.String}
	 */
	java.lang.String getNumber();

	/**
	 * Gets the value of the person property.
	 * 
	 * @return possible object is {@link java.lang.String}
	 */
	java.lang.String getPerson();

	/**
	 * Gets the value of the script property.
	 * 
	 * @return possible object is {@link java.lang.String}
	 */
	java.lang.String getScript();

	/**
	 * Gets the value of the tense property.
	 * 
	 * @return possible object is {@link java.lang.String}
	 */
	java.lang.String getTense();

	/**
	 * Gets the value of the transliterated property.
	 * 
	 * @return possible object is {@link java.lang.String}
	 */
	java.lang.String getTransliterated();

	/**
	 * Gets the value of the undotted property.
	 * 
	 * @return possible object is {@link java.lang.String}
	 */
	java.lang.String getUndotted();

	/**
	 * Gets the value of the value property.
	 * 
	 * @return possible object is {@link java.lang.String}
	 */
	java.lang.String getValue();

	/**
	 * Sets the value of the dotted property.
	 * 
	 * @param value
	 *            allowed object is {@link java.lang.String}
	 */
	void setDotted(java.lang.String value);

	/**
	 * Sets the value of the gender property.
	 * 
	 * @param value
	 *            allowed object is {@link java.lang.String}
	 */
	void setGender(java.lang.String value);

	/**
	 * Sets the value of the number property.
	 * 
	 * @param value
	 *            allowed object is {@link java.lang.String}
	 */
	void setNumber(java.lang.String value);

	/**
	 * Sets the value of the person property.
	 * 
	 * @param value
	 *            allowed object is {@link java.lang.String}
	 */
	void setPerson(java.lang.String value);

	/**
	 * Sets the value of the script property.
	 * 
	 * @param value
	 *            allowed object is {@link java.lang.String}
	 */
	void setScript(java.lang.String value);

	/**
	 * Sets the value of the tense property.
	 * 
	 * @param value
	 *            allowed object is {@link java.lang.String}
	 */
	void setTense(java.lang.String value);

	/**
	 * Sets the value of the transliterated property.
	 * 
	 * @param value
	 *            allowed object is {@link java.lang.String}
	 */
	void setTransliterated(java.lang.String value);

	/**
	 * Sets the value of the undotted property.
	 * 
	 * @param value
	 *            allowed object is {@link java.lang.String}
	 */
	void setUndotted(java.lang.String value);

	/**
	 * Sets the value of the value property.
	 * 
	 * @param value
	 *            allowed object is {@link java.lang.String}
	 */
	void setValue(java.lang.String value);

}
