//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v1.0.5-b16-fcs 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2009.06.14 at 03:41:34 PM IDT 
//

package mila.generated;

/**
 * Java content class for NumeralType complex type.
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this java content object. (defined at
 * file:/C:/Sun/jwsdp-1.6/jaxb/lib/hebrew_MWcorpus.xsd line 955)
 * <p>
 * 
 * <pre>
 * &lt;complexType name="NumeralType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;attribute name="definiteness" type="{}DefinitenessType" default="false" />
 *       &lt;attribute name="gender" type="{}GenderType" default="masculine" />
 *       &lt;attribute name="number" type="{}NumberType" default="singular" />
 *       &lt;attribute name="register" type="{}RegisterType" default="formal" />
 *       &lt;attribute name="spelling" type="{}SpellingType" default="standard" />
 *       &lt;attribute name="status" type="{}StatusType" default="absolute and construct" />
 *       &lt;attribute name="type" type="{}NumeralTypeType" default="unspecified" />
 *       &lt;attribute name="value" type="{http://www.w3.org/2001/XMLSchema}token" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 */
public interface NumeralType {

	/**
	 * Gets the value of the definiteness property.
	 * 
	 * @return possible object is {@link java.lang.String}
	 */
	java.lang.String getDefiniteness();

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
	 * Gets the value of the register property.
	 * 
	 * @return possible object is {@link java.lang.String}
	 */
	java.lang.String getRegister();

	/**
	 * Gets the value of the spelling property.
	 * 
	 * @return possible object is {@link java.lang.String}
	 */
	java.lang.String getSpelling();

	/**
	 * Gets the value of the status property.
	 * 
	 * @return possible object is {@link java.lang.String}
	 */
	java.lang.String getStatus();

	/**
	 * Gets the value of the type property.
	 * 
	 * @return possible object is {@link java.lang.String}
	 */
	java.lang.String getType();

	/**
	 * Gets the value of the value property.
	 * 
	 * @return possible object is {@link java.lang.String}
	 */
	java.lang.String getValue();

	/**
	 * Sets the value of the definiteness property.
	 * 
	 * @param value
	 *            allowed object is {@link java.lang.String}
	 */
	void setDefiniteness(java.lang.String value);

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
	 * Sets the value of the register property.
	 * 
	 * @param value
	 *            allowed object is {@link java.lang.String}
	 */
	void setRegister(java.lang.String value);

	/**
	 * Sets the value of the spelling property.
	 * 
	 * @param value
	 *            allowed object is {@link java.lang.String}
	 */
	void setSpelling(java.lang.String value);

	/**
	 * Sets the value of the status property.
	 * 
	 * @param value
	 *            allowed object is {@link java.lang.String}
	 */
	void setStatus(java.lang.String value);

	/**
	 * Sets the value of the type property.
	 * 
	 * @param value
	 *            allowed object is {@link java.lang.String}
	 */
	void setType(java.lang.String value);

	/**
	 * Sets the value of the value property.
	 * 
	 * @param value
	 *            allowed object is {@link java.lang.String}
	 */
	void setValue(java.lang.String value);

}
