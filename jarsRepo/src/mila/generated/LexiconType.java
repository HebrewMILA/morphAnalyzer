//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v1.0.5-b16-fcs 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2008.12.01 at 02:23:12 PM IST 
//

package mila.generated;

/**
 * Java content class for LexiconType complex type.
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this java content object. (defined at
 * file:/C:/Sun/jwsdp-1.6/jaxb/lib/hebrew_MWlexicon_12_11_2008.xsd line 45)
 * <p>
 * 
 * <pre>
 * &lt;complexType name="LexiconType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="metadata" type="{}MetadataType" minOccurs="0"/>
 *         &lt;element name="item" type="{}ItemType" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 */
public interface LexiconType {

	/**
	 * Gets the value of the Item property.
	 * 
	 * <p>
	 * This accessor method returns a reference to the live list, not a
	 * snapshot. Therefore any modification you make to the returned list will
	 * be present inside the JAXB object. This is why there is not a
	 * <CODE>set</CODE> method for the Item property.
	 * 
	 * <p>
	 * For example, to add a new item, do as follows:
	 * 
	 * <pre>
	 * getItem().add(newItem);
	 * </pre>
	 * 
	 * 
	 * <p>
	 * Objects of the following type(s) are allowed in the list
	 * {@link mila.generated.ItemType}
	 * 
	 */
	java.util.List getItem();

	/**
	 * Gets the value of the metadata property.
	 * 
	 * @return possible object is {@link mila.generated.MetadataType}
	 */
	mila.generated.MetadataType getMetadata();

	/**
	 * Sets the value of the metadata property.
	 * 
	 * @param value
	 *            allowed object is {@link mila.generated.MetadataType}
	 */
	void setMetadata(mila.generated.MetadataType value);

}
