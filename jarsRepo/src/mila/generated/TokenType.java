//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v1.0.5-b16-fcs 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2009.06.14 at 03:41:34 PM IDT 
//

package mila.generated;

/**
 * ����� ����� �� ����� �� ��� ���� ������ ������� ����� ���, ����� �����
 * ������� �����-��������� �� ����� �� ��� ����.
 * 
 * Java content class for TokenType complex type.
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this java content object. (defined at
 * file:/C:/Sun/jwsdp-1.6/jaxb/lib/hebrew_MWcorpus.xsd line 156)
 * <p>
 * 
 * <pre>
 * &lt;complexType name="TokenType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="analysis" type="{}AnalysisType" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="id" use="required" type="{http://www.w3.org/2001/XMLSchema}token" />
 *       &lt;attribute name="surface" use="required" type="{http://www.w3.org/2001/XMLSchema}token" />
 *       &lt;attribute name="transliterated" type="{http://www.w3.org/2001/XMLSchema}token" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 */
public interface TokenType {
	/**
	 * Gets the value of the Analysis property.
	 * 
	 * <p>
	 * This accessor method returns a reference to the live list, not a
	 * snapshot. Therefore any modification you make to the returned list will
	 * be present inside the JAXB object. This is why there is not a
	 * <CODE>set</CODE> method for the Analysis property.
	 * 
	 * <p>
	 * For example, to add a new item, do as follows:
	 * 
	 * <pre>
	 * getAnalysis().add(newItem);
	 * </pre>
	 * 
	 * 
	 * <p>
	 * Objects of the following type(s) are allowed in the list
	 * {@link mila.generated.AnalysisType}
	 * 
	 */
	java.util.List getAnalysis();

	/**
	 * Gets the value of the id property.
	 * 
	 * @return possible object is {@link java.lang.String}
	 */
	java.lang.String getId();

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
	 * Sets the value of the id property.
	 * 
	 * @param value
	 *            allowed object is {@link java.lang.String}
	 */
	void setId(java.lang.String value);

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
