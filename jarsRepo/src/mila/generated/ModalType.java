//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v1.0.5-b16-fcs 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2009.06.14 at 03:41:34 PM IDT 
//

package mila.generated;

/**
 * Java content class for ModalType complex type.
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this java content object. (defined at
 * file:/C:/Sun/jwsdp-1.6/jaxb/lib/hebrew_MWcorpus.xsd line 1062)
 * <p>
 * 
 * <pre>
 * &lt;complexType name="ModalType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;attribute name="gender" type="{}GenderType" default="unspecified" />
 *       &lt;attribute name="number" type="{}NumberType" default="unspecified" />
 *       &lt;attribute name="person" type="{}PersonType" default="unspecified" />
 *       &lt;attribute name="register" type="{}RegisterType" default="formal" />
 *       &lt;attribute name="spelling" type="{}SpellingType" default="standard" />
 *       &lt;attribute name="subcoordinating" type="{http://www.w3.org/2001/XMLSchema}token" default="false" />
 *       &lt;attribute name="tense" type="{}TenseType" default="unspecified" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 */
public interface ModalType {

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
	 * Gets the value of the subcoordinating property.
	 * 
	 * @return possible object is {@link java.lang.String}
	 */
	java.lang.String getSubcoordinating();

	/**
	 * Gets the value of the tense property.
	 * 
	 * @return possible object is {@link java.lang.String}
	 */
	java.lang.String getTense();

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
	 * Sets the value of the subcoordinating property.
	 * 
	 * @param value
	 *            allowed object is {@link java.lang.String}
	 */
	void setSubcoordinating(java.lang.String value);

	/**
	 * Sets the value of the tense property.
	 * 
	 * @param value
	 *            allowed object is {@link java.lang.String}
	 */
	void setTense(java.lang.String value);

}
