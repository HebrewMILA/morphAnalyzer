//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v1.0.5-b16-fcs 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2009.06.14 at 03:41:34 PM IDT 
//

package mila.generated;

/**
 * ���� �� ���� ����� �����.
 * 
 * Java content class for ConjunctionType complex type.
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this java content object. (defined at
 * file:/C:/Sun/jwsdp-1.6/jaxb/lib/hebrew_MWcorpus.xsd line 734)
 * <p>
 * 
 * <pre>
 * &lt;complexType name="ConjunctionType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;attribute name="expansion" type="{http://www.w3.org/2001/XMLSchema}token" />
 *       &lt;attribute name="register" type="{}RegisterType" default="formal" />
 *       &lt;attribute name="spelling" type="{}SpellingType" default="standard" />
 *       &lt;attribute name="type" type="{}ConjunctionTypeType" default="coordinating" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 */
public interface ConjunctionType {

	/**
	 * Gets the value of the expansion property.
	 * 
	 * @return possible object is {@link java.lang.String}
	 */
	java.lang.String getExpansion();

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
	 * Gets the value of the type property.
	 * 
	 * @return possible object is {@link java.lang.String}
	 */
	java.lang.String getType();

	/**
	 * Sets the value of the expansion property.
	 * 
	 * @param value
	 *            allowed object is {@link java.lang.String}
	 */
	void setExpansion(java.lang.String value);

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
	 * Sets the value of the type property.
	 * 
	 * @param value
	 *            allowed object is {@link java.lang.String}
	 */
	void setType(java.lang.String value);

}