//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v1.0.5-b16-fcs 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2008.12.01 at 02:23:12 PM IST 
//

package mila.generated;

/**
 * A word sense as determined by the Rav Millim dictionary. Different senses of
 * the same item have identical morphological attributes, including the same
 * part of speech. They differ in their semantics, which is reflected in
 * possibly different translation equivalents, definitions and usage examples.
 * Java content class for SenseType complex type.
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this java content object. (defined at
 * file:/C:/Sun/jwsdp-1.6/jaxb/lib/hebrew_MWlexicon_12_11_2008.xsd line 173)
 * <p>
 * 
 * <pre>
 * &lt;complexType name="SenseType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="english" type="{}EnglishType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="synset" type="{}SynsetType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="example" type="{}ExampleType" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="definition" type="{http://www.w3.org/2001/XMLSchema}token" />
 *       &lt;attribute name="id" use="required" type="{http://www.w3.org/2001/XMLSchema}token" />
 *       &lt;attribute name="weight" type="{http://www.w3.org/2001/XMLSchema}token" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 */
public interface SenseType {

	/**
	 * Gets the value of the definition property.
	 * 
	 * @return possible object is {@link java.lang.String}
	 */
	java.lang.String getDefinition();

	/**
	 * Gets the value of the English property.
	 * 
	 * <p>
	 * This accessor method returns a reference to the live list, not a
	 * snapshot. Therefore any modification you make to the returned list will
	 * be present inside the JAXB object. This is why there is not a
	 * <CODE>set</CODE> method for the English property.
	 * 
	 * <p>
	 * For example, to add a new item, do as follows:
	 * 
	 * <pre>
	 * getEnglish().add(newItem);
	 * </pre>
	 * 
	 * 
	 * <p>
	 * Objects of the following type(s) are allowed in the list
	 * {@link mila.generated.EnglishType}
	 * 
	 */
	java.util.List getEnglish();

	/**
	 * Gets the value of the Example property.
	 * 
	 * <p>
	 * This accessor method returns a reference to the live list, not a
	 * snapshot. Therefore any modification you make to the returned list will
	 * be present inside the JAXB object. This is why there is not a
	 * <CODE>set</CODE> method for the Example property.
	 * 
	 * <p>
	 * For example, to add a new item, do as follows:
	 * 
	 * <pre>
	 * getExample().add(newItem);
	 * </pre>
	 * 
	 * 
	 * <p>
	 * Objects of the following type(s) are allowed in the list
	 * {@link mila.generated.ExampleType}
	 * 
	 */
	java.util.List getExample();

	/**
	 * Gets the value of the id property.
	 * 
	 * @return possible object is {@link java.lang.String}
	 */
	java.lang.String getId();

	/**
	 * Gets the value of the Synset property.
	 * 
	 * <p>
	 * This accessor method returns a reference to the live list, not a
	 * snapshot. Therefore any modification you make to the returned list will
	 * be present inside the JAXB object. This is why there is not a
	 * <CODE>set</CODE> method for the Synset property.
	 * 
	 * <p>
	 * For example, to add a new item, do as follows:
	 * 
	 * <pre>
	 * getSynset().add(newItem);
	 * </pre>
	 * 
	 * 
	 * <p>
	 * Objects of the following type(s) are allowed in the list
	 * {@link mila.generated.SynsetType}
	 * 
	 */
	java.util.List getSynset();

	/**
	 * Gets the value of the weight property.
	 * 
	 * @return possible object is {@link java.lang.String}
	 */
	java.lang.String getWeight();

	/**
	 * Sets the value of the definition property.
	 * 
	 * @param value
	 *            allowed object is {@link java.lang.String}
	 */
	void setDefinition(java.lang.String value);

	/**
	 * Sets the value of the id property.
	 * 
	 * @param value
	 *            allowed object is {@link java.lang.String}
	 */
	void setId(java.lang.String value);

	/**
	 * Sets the value of the weight property.
	 * 
	 * @param value
	 *            allowed object is {@link java.lang.String}
	 */
	void setWeight(java.lang.String value);

}
