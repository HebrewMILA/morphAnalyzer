//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v1.0.5-b16-fcs 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2008.12.01 at 02:23:12 PM IST 
//

package mila.generated;

/**
 * ���� �� ���� ������ ���� ����. �����: ====== inflectionBase �� ����� ��
 * inflect="false". ������ ��� ���� ����� ����� ������ ����, ����� ����� �������
 * add/replace/remove.
 * 
 * Java content class for InterrogativeLexiconType complex type.
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this java content object. (defined at
 * file:/C:/Sun/jwsdp-1.6/jaxb/lib/hebrew_MWlexicon_12_11_2008.xsd line 1544)
 * <p>
 * 
 * <pre>
 * &lt;complexType name="InterrogativeLexiconType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;choice maxOccurs="unbounded" minOccurs="0">
 *           &lt;element name="add" type="{}InterrogativeExceptionType" maxOccurs="unbounded" minOccurs="0"/>
 *           &lt;element name="replace" type="{}InterrogativeExceptionType" maxOccurs="unbounded" minOccurs="0"/>
 *           &lt;element name="remove" type="{}InterrogativeExceptionType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;/choice>
 *       &lt;/sequence>
 *       &lt;attribute name="inflect" type="{http://www.w3.org/2001/XMLSchema}boolean" default="false" />
 *       &lt;attribute name="inflectionBase" type="{http://www.w3.org/2001/XMLSchema}token" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 */
public interface InterrogativeLexiconType {

	/**
	 * ���� �� ���� ������ ����� ������. ���� -- ���� ����, ����� ������ ����
	 * ������ ����. ������ ���� *��* ����� ����� ������ ������ ����� �����!
	 * 
	 * Java content class for add element declaration.
	 * <p>
	 * The following schema fragment specifies the expected content contained
	 * within this java content object. (defined at
	 * file:/C:/Sun/jwsdp-1.6/jaxb/lib/hebrew_MWlexicon_12_11_2008.xsd line
	 * 1558)
	 * <p>
	 * 
	 * <pre>
	 * &lt;element name="add" type="{}InterrogativeExceptionType"/>
	 * </pre>
	 * 
	 */
	public interface Add extends javax.xml.bind.Element,
			mila.generated.InterrogativeExceptionType {

	}

	/**
	 * ���� �� ���� ����� ����� ������ ����� �������, ���� ���� ����� �����
	 * ����� ����� ��������, ��� ����� (���� ������ �� ������ ����, �� ��� �����
	 * ���). ������ ���� *��* ������ ����� ������ ������ ����� �����! �����
	 * ����, �� ������ ����� ��� �- add ��- replace ��� ������ �- remove, ���
	 * ���� �� ������ ���� ����� ������ ���� ����� ��������.
	 * 
	 * Java content class for remove element declaration.
	 * <p>
	 * The following schema fragment specifies the expected content contained
	 * within this java content object. (defined at
	 * file:/C:/Sun/jwsdp-1.6/jaxb/lib/hebrew_MWlexicon_12_11_2008.xsd line
	 * 1575)
	 * <p>
	 * 
	 * <pre>
	 * &lt;element name="remove" type="{}InterrogativeExceptionType"/>
	 * </pre>
	 * 
	 */
	public interface Remove extends javax.xml.bind.Element,
			mila.generated.InterrogativeExceptionType {

	}

	/**
	 * ���� �� ���� ������ ����� ������ ������. ������ ���� *��* ����� �����
	 * ������ ������ ����� �����!
	 * 
	 * Java content class for replace element declaration.
	 * <p>
	 * The following schema fragment specifies the expected content contained
	 * within this java content object. (defined at
	 * file:/C:/Sun/jwsdp-1.6/jaxb/lib/hebrew_MWlexicon_12_11_2008.xsd line
	 * 1567)
	 * <p>
	 * 
	 * <pre>
	 * &lt;element name="replace" type="{}InterrogativeExceptionType"/>
	 * </pre>
	 * 
	 */
	public interface Replace extends javax.xml.bind.Element,
			mila.generated.InterrogativeExceptionType {

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
	 * {@link mila.generated.InterrogativeLexiconType.Replace}
	 * {@link mila.generated.InterrogativeLexiconType.Remove}
	 * {@link mila.generated.InterrogativeLexiconType.Add}
	 * 
	 */
	java.util.List getAddOrReplaceOrRemove();

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
