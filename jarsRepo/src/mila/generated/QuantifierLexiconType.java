//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v1.0.5-b16-fcs 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2008.12.01 at 02:23:12 PM IST 
//

package mila.generated;

/**
 * ���� �� ����� ����� ������� ����� ����. ������� �� �"� Glinert "The grammar
 * of Modern Hebrew". ����� ������� ����� ���� ��� ����� ���� ����. ����, �����
 * ����� ���� �� ���� �����. ���� ���� ������� �: * ������ ������ * ������ �����
 * * ������ ������ (�����) ��� ���� ������ �������� ������ ���������. ����� ����
 * �� ��� ���� ���� ���� ����� ������, ���� ���� ���� ����� ����. ��� ��������
 * ����� ��� ���� ��� �� ���/��� ����� ��� ���� ������� ����� ��� �� ��� ��
 * ������� ������ ��� ������. �� ���� �� �� �� ���� ��� ������� ��� �����
 * acronym ���� true.
 * 
 * Java content class for QuantifierLexiconType complex type.
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this java content object. (defined at
 * file:/C:/Sun/jwsdp-1.6/jaxb/lib/hebrew_MWlexicon_12_11_2008.xsd line 1702)
 * <p>
 * 
 * <pre>
 * &lt;complexType name="QuantifierLexiconType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;choice maxOccurs="unbounded" minOccurs="0">
 *           &lt;element name="add" type="{}QuantifierExceptionType" maxOccurs="unbounded" minOccurs="0"/>
 *           &lt;element name="replace" type="{}QuantifierExceptionType" maxOccurs="unbounded" minOccurs="0"/>
 *           &lt;element name="remove" type="{}QuantifierExceptionType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;/choice>
 *       &lt;/sequence>
 *       &lt;attribute name="definiteness" type="{}DefinitnessType" default="prohibited" />
 *       &lt;attribute name="gender" type="{}GenderType" default="unspecified" />
 *       &lt;attribute name="inflect" type="{http://www.w3.org/2001/XMLSchema}boolean" default="false" />
 *       &lt;attribute name="inflectionBase" type="{http://www.w3.org/2001/XMLSchema}token" default="" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 */
public interface QuantifierLexiconType {

	/**
	 * Java content class for add element declaration.
	 * <p>
	 * The following schema fragment specifies the expected content contained
	 * within this java content object. (defined at
	 * file:/C:/Sun/jwsdp-1.6/jaxb/lib/hebrew_MWlexicon_12_11_2008.xsd line
	 * 1722)
	 * <p>
	 * 
	 * <pre>
	 * &lt;element name="add" type="{}QuantifierExceptionType"/>
	 * </pre>
	 * 
	 */
	public interface Add extends javax.xml.bind.Element,
			mila.generated.QuantifierExceptionType {

	}

	/**
	 * Java content class for remove element declaration.
	 * <p>
	 * The following schema fragment specifies the expected content contained
	 * within this java content object. (defined at
	 * file:/C:/Sun/jwsdp-1.6/jaxb/lib/hebrew_MWlexicon_12_11_2008.xsd line
	 * 1724)
	 * <p>
	 * 
	 * <pre>
	 * &lt;element name="remove" type="{}QuantifierExceptionType"/>
	 * </pre>
	 * 
	 */
	public interface Remove extends javax.xml.bind.Element,
			mila.generated.QuantifierExceptionType {

	}

	/**
	 * Java content class for replace element declaration.
	 * <p>
	 * The following schema fragment specifies the expected content contained
	 * within this java content object. (defined at
	 * file:/C:/Sun/jwsdp-1.6/jaxb/lib/hebrew_MWlexicon_12_11_2008.xsd line
	 * 1723)
	 * <p>
	 * 
	 * <pre>
	 * &lt;element name="replace" type="{}QuantifierExceptionType"/>
	 * </pre>
	 * 
	 */
	public interface Replace extends javax.xml.bind.Element,
			mila.generated.QuantifierExceptionType {

	}

	/**
	 * Gets the value of the AddOrReplaceOrRemove property.
	 * 
	 * <p>
	 * This accessor method returns a reference to the live list, not a
	 * snapshot. Therefore any modification you make to the returned list will
	 * be present inside the JAXB object. This is why there is not a
	 * <CODE>set</CODE> method for the AddOrReplaceOrRemove property.
	 * 
	 * <p>
	 * For example, to add a new item, do as follows:
	 * 
	 * <pre>
	 * getAddOrReplaceOrRemove().add(newItem);
	 * </pre>
	 * 
	 * 
	 * <p>
	 * Objects of the following type(s) are allowed in the list
	 * {@link mila.generated.QuantifierLexiconType.Remove}
	 * {@link mila.generated.QuantifierLexiconType.Add}
	 * {@link mila.generated.QuantifierLexiconType.Replace}
	 * 
	 */
	java.util.List getAddOrReplaceOrRemove();

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
	 * Gets the value of the inflectionBase property.
	 * 
	 * @return possible object is {@link java.lang.String}
	 */
	java.lang.String getInflectionBase();

	/**
	 * Gets the value of the inflect property.
	 * 
	 */
	boolean isInflect();

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
	 * Sets the value of the inflect property.
	 * 
	 */
	void setInflect(boolean value);

	/**
	 * Sets the value of the inflectionBase property.
	 * 
	 * @param value
	 *            allowed object is {@link java.lang.String}
	 */
	void setInflectionBase(java.lang.String value);

}
