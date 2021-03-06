//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v1.0.5-b16-fcs 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2009.06.14 at 03:41:34 PM IDT 
//

package mila.generated;

/**
 * Java content class for SuffixType complex type.
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this java content object. (defined at
 * file:/C:/Sun/jwsdp-1.6/jaxb/lib/hebrew_MWcorpus.xsd line 885)
 * <p>
 * 
 * <pre>
 * &lt;complexType name="SuffixType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;attribute name="function" type="{}SuffixFunctionType" default="unspecified" />
 *       &lt;attribute name="gender" type="{}GenderType" default="unspecified" />
 *       &lt;attribute name="number" type="{}NumberType" default="unspecified" />
 *       &lt;attribute name="person" type="{}PersonType" default="unspecified" />
 *       &lt;attribute name="surface" type="{}SuffixSurfaceType" default="unspecified" />
 *       &lt;attribute name="transliterated" type="{}SuffixTransliteratedSurfaceType" default="unspecified" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 */
public interface SuffixType {

	/**
	 * Gets the value of the function property.
	 * 
	 * @return possible object is {@link java.lang.String}
	 */
	java.lang.String getFunction();

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
	 * Gets the value of the surface property.
	 * 
	 * @return possible object is {@link java.lang.String}
	 */
	java.lang.String getSurface();

	/**
	 * Gets the value of the transliterated property.
	 * 
	 * @return possible object is {@link java.lang.String}
	 */
	java.lang.String getTransliterated();

	/**
	 * Sets the value of the function property.
	 * 
	 * @param value
	 *            allowed object is {@link java.lang.String}
	 */
	void setFunction(java.lang.String value);

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
	 * Sets the value of the surface property.
	 * 
	 * @param value
	 *            allowed object is {@link java.lang.String}
	 */
	void setSurface(java.lang.String value);

	/**
	 * Sets the value of the transliterated property.
	 * 
	 * @param value
	 *            allowed object is {@link java.lang.String}
	 */
	void setTransliterated(java.lang.String value);

}
