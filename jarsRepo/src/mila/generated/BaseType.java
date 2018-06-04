//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v1.0.5-b16-fcs 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2009.06.14 at 03:41:34 PM IDT 
//

package mila.generated;

/**
 * Java content class for BaseType complex type.
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this java content object. (defined at
 * file:/C:/Sun/jwsdp-1.6/jaxb/lib/hebrew_MWcorpus.xsd line 333)
 * <p>
 * 
 * <pre>
 * &lt;complexType name="BaseType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;choice>
 *         &lt;element name="adjective" type="{}GenderNumberStatusDefinitenessType"/>
 *         &lt;element name="adverb" type="{}GenderNumberPersonType"/>
 *         &lt;element name="zevel" type="{}ZVLType"/>
 *         &lt;element name="conjunction" type="{}ConjunctionType"/>
 *         &lt;element name="interjection" type="{}InterjectionType"/>
 *         &lt;element name="interrogative" type="{}InterrogativeType"/>
 *         &lt;element name="negation" type="{}NegationType"/>
 *         &lt;element name="foreign" type="{}ForeignType"/>
 *         &lt;element name="url" type="{}URLType"/>
 *         &lt;element name="noun" type="{}GenderNumberStatusDefinitenessType"/>
 *         &lt;element name="preposition" type="{}GenderNumberPersonType"/>
 *         &lt;element name="pronoun" type="{}PronounType"/>
 *         &lt;element name="properName" type="{}ProperNameType"/>
 *         &lt;element name="punctuation" type="{}PunctuationType"/>
 *         &lt;element name="numberExpression" type="{}NumberExpressionType"/>
 *         &lt;element name="quantifier" type="{}QuantifierType"/>
 *         &lt;element name="verb" type="{}VerbType"/>
 *         &lt;element name="participle" type="{}ParticipleType"/>
 *         &lt;element name="numeral" type="{}NumeralType"/>
 *         &lt;element name="existential" type="{}ExistentialType"/>
 *         &lt;element name="impersonal" type="{}ImpersonalType"/>
 *         &lt;element name="wPrefix" type="{}WprefixType"/>
 *         &lt;element name="modal" type="{}ModalType"/>
 *         &lt;element name="copula" type="{}CopulaType"/>
 *         &lt;element name="title" type="{}TitleType"/>
 *         &lt;element name="MWE" type="{}MWEType"/>
 *         &lt;element name="unknown" type="{}UnknownType"/>
 *       &lt;/choice>
 *       &lt;attribute name="dottedLexiconItem" type="{http://www.w3.org/2001/XMLSchema}token" />
 *       &lt;attribute name="lexiconItem" type="{http://www.w3.org/2001/XMLSchema}token" />
 *       &lt;attribute name="lexiconPointer" type="{http://www.w3.org/2001/XMLSchema}token" />
 *       &lt;attribute name="transliteratedLexiconItem" type="{http://www.w3.org/2001/XMLSchema}token" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 */
public interface BaseType {

	/**
	 * Gets the value of the adjective property.
	 * 
	 * @return possible object is
	 *         {@link mila.generated.GenderNumberStatusDefinitenessType}
	 */
	mila.generated.GenderNumberStatusDefinitenessType getAdjective();

	/**
	 * Gets the value of the adverb property.
	 * 
	 * @return possible object is {@link mila.generated.GenderNumberPersonType}
	 */
	mila.generated.GenderNumberPersonType getAdverb();

	/**
	 * Gets the value of the conjunction property.
	 * 
	 * @return possible object is {@link mila.generated.ConjunctionType}
	 */
	mila.generated.ConjunctionType getConjunction();

	/**
	 * Gets the value of the copula property.
	 * 
	 * @return possible object is {@link mila.generated.CopulaType}
	 */
	mila.generated.CopulaType getCopula();

	/**
	 * Gets the value of the dottedLexiconItem property.
	 * 
	 * @return possible object is {@link java.lang.String}
	 */
	java.lang.String getDottedLexiconItem();

	/**
	 * Gets the value of the existential property.
	 * 
	 * @return possible object is {@link mila.generated.ExistentialType}
	 */
	mila.generated.ExistentialType getExistential();

	/**
	 * Gets the value of the foreign property.
	 * 
	 * @return possible object is {@link mila.generated.ForeignType}
	 */
	mila.generated.ForeignType getForeign();

	/**
	 * Gets the value of the impersonal property.
	 * 
	 * @return possible object is {@link mila.generated.ImpersonalType}
	 */
	mila.generated.ImpersonalType getImpersonal();

	/**
	 * Gets the value of the interjection property.
	 * 
	 * @return possible object is {@link mila.generated.InterjectionType}
	 */
	mila.generated.InterjectionType getInterjection();

	/**
	 * Gets the value of the interrogative property.
	 * 
	 * @return possible object is {@link mila.generated.InterrogativeType}
	 */
	mila.generated.InterrogativeType getInterrogative();

	/**
	 * Gets the value of the lexiconItem property.
	 * 
	 * @return possible object is {@link java.lang.String}
	 */
	java.lang.String getLexiconItem();

	/**
	 * Gets the value of the lexiconPointer property.
	 * 
	 * @return possible object is {@link java.lang.String}
	 */
	java.lang.String getLexiconPointer();

	/**
	 * Gets the value of the modal property.
	 * 
	 * @return possible object is {@link mila.generated.ModalType}
	 */
	mila.generated.ModalType getModal();

	/**
	 * Gets the value of the mwe property.
	 * 
	 * @return possible object is {@link mila.generated.MWEType}
	 */
	mila.generated.MWEType getMWE();

	/**
	 * Gets the value of the negation property.
	 * 
	 * @return possible object is {@link mila.generated.NegationType}
	 */
	mila.generated.NegationType getNegation();

	/**
	 * Gets the value of the noun property.
	 * 
	 * @return possible object is
	 *         {@link mila.generated.GenderNumberStatusDefinitenessType}
	 */
	mila.generated.GenderNumberStatusDefinitenessType getNoun();

	/**
	 * Gets the value of the numberExpression property.
	 * 
	 * @return possible object is {@link mila.generated.NumberExpressionType}
	 */
	mila.generated.NumberExpressionType getNumberExpression();

	/**
	 * Gets the value of the numeral property.
	 * 
	 * @return possible object is {@link mila.generated.NumeralType}
	 */
	mila.generated.NumeralType getNumeral();

	/**
	 * Gets the value of the participle property.
	 * 
	 * @return possible object is {@link mila.generated.ParticipleType}
	 */
	mila.generated.ParticipleType getParticiple();

	/**
	 * Gets the value of the preposition property.
	 * 
	 * @return possible object is {@link mila.generated.GenderNumberPersonType}
	 */
	mila.generated.GenderNumberPersonType getPreposition();

	/**
	 * Gets the value of the pronoun property.
	 * 
	 * @return possible object is {@link mila.generated.PronounType}
	 */
	mila.generated.PronounType getPronoun();

	/**
	 * Gets the value of the properName property.
	 * 
	 * @return possible object is {@link mila.generated.ProperNameType}
	 */
	mila.generated.ProperNameType getProperName();

	/**
	 * Gets the value of the punctuation property.
	 * 
	 * @return possible object is {@link mila.generated.PunctuationType}
	 */
	mila.generated.PunctuationType getPunctuation();

	/**
	 * Gets the value of the quantifier property.
	 * 
	 * @return possible object is {@link mila.generated.QuantifierType}
	 */
	mila.generated.QuantifierType getQuantifier();

	/**
	 * Gets the value of the title property.
	 * 
	 * @return possible object is {@link mila.generated.TitleType}
	 */
	mila.generated.TitleType getTitle();

	/**
	 * Gets the value of the transliteratedLexiconItem property.
	 * 
	 * @return possible object is {@link java.lang.String}
	 */
	java.lang.String getTransliteratedLexiconItem();

	/**
	 * Gets the value of the unknown property.
	 * 
	 * @return possible object is {@link mila.generated.UnknownType}
	 */
	mila.generated.UnknownType getUnknown();

	/**
	 * Gets the value of the url property.
	 * 
	 * @return possible object is {@link mila.generated.URLType}
	 */
	mila.generated.URLType getUrl();

	/**
	 * Gets the value of the verb property.
	 * 
	 * @return possible object is {@link mila.generated.VerbType}
	 */
	mila.generated.VerbType getVerb();

	/**
	 * Gets the value of the wPrefix property.
	 * 
	 * @return possible object is {@link mila.generated.WprefixType}
	 */
	mila.generated.WprefixType getWPrefix();

	/**
	 * Gets the value of the zevel property.
	 * 
	 * @return possible object is {@link mila.generated.ZVLType}
	 */
	mila.generated.ZVLType getZevel();

	/**
	 * Sets the value of the adjective property.
	 * 
	 * @param value
	 *            allowed object is
	 *            {@link mila.generated.GenderNumberStatusDefinitenessType}
	 */
	void setAdjective(mila.generated.GenderNumberStatusDefinitenessType value);

	/**
	 * Sets the value of the adverb property.
	 * 
	 * @param value
	 *            allowed object is {@link mila.generated.GenderNumberPersonType}
	 */
	void setAdverb(mila.generated.GenderNumberPersonType value);

	/**
	 * Sets the value of the conjunction property.
	 * 
	 * @param value
	 *            allowed object is {@link mila.generated.ConjunctionType}
	 */
	void setConjunction(mila.generated.ConjunctionType value);

	/**
	 * Sets the value of the copula property.
	 * 
	 * @param value
	 *            allowed object is {@link mila.generated.CopulaType}
	 */
	void setCopula(mila.generated.CopulaType value);

	/**
	 * Sets the value of the dottedLexiconItem property.
	 * 
	 * @param value
	 *            allowed object is {@link java.lang.String}
	 */
	void setDottedLexiconItem(java.lang.String value);

	/**
	 * Sets the value of the existential property.
	 * 
	 * @param value
	 *            allowed object is {@link mila.generated.ExistentialType}
	 */
	void setExistential(mila.generated.ExistentialType value);

	/**
	 * Sets the value of the foreign property.
	 * 
	 * @param value
	 *            allowed object is {@link mila.generated.ForeignType}
	 */
	void setForeign(mila.generated.ForeignType value);

	/**
	 * Sets the value of the impersonal property.
	 * 
	 * @param value
	 *            allowed object is {@link mila.generated.ImpersonalType}
	 */
	void setImpersonal(mila.generated.ImpersonalType value);

	/**
	 * Sets the value of the interjection property.
	 * 
	 * @param value
	 *            allowed object is {@link mila.generated.InterjectionType}
	 */
	void setInterjection(mila.generated.InterjectionType value);

	/**
	 * Sets the value of the interrogative property.
	 * 
	 * @param value
	 *            allowed object is {@link mila.generated.InterrogativeType}
	 */
	void setInterrogative(mila.generated.InterrogativeType value);

	/**
	 * Sets the value of the lexiconItem property.
	 * 
	 * @param value
	 *            allowed object is {@link java.lang.String}
	 */
	void setLexiconItem(java.lang.String value);

	/**
	 * Sets the value of the lexiconPointer property.
	 * 
	 * @param value
	 *            allowed object is {@link java.lang.String}
	 */
	void setLexiconPointer(java.lang.String value);

	/**
	 * Sets the value of the modal property.
	 * 
	 * @param value
	 *            allowed object is {@link mila.generated.ModalType}
	 */
	void setModal(mila.generated.ModalType value);

	/**
	 * Sets the value of the mwe property.
	 * 
	 * @param value
	 *            allowed object is {@link mila.generated.MWEType}
	 */
	void setMWE(mila.generated.MWEType value);

	/**
	 * Sets the value of the negation property.
	 * 
	 * @param value
	 *            allowed object is {@link mila.generated.NegationType}
	 */
	void setNegation(mila.generated.NegationType value);

	/**
	 * Sets the value of the noun property.
	 * 
	 * @param value
	 *            allowed object is
	 *            {@link mila.generated.GenderNumberStatusDefinitenessType}
	 */
	void setNoun(mila.generated.GenderNumberStatusDefinitenessType value);

	/**
	 * Sets the value of the numberExpression property.
	 * 
	 * @param value
	 *            allowed object is {@link mila.generated.NumberExpressionType}
	 */
	void setNumberExpression(mila.generated.NumberExpressionType value);

	/**
	 * Sets the value of the numeral property.
	 * 
	 * @param value
	 *            allowed object is {@link mila.generated.NumeralType}
	 */
	void setNumeral(mila.generated.NumeralType value);

	/**
	 * Sets the value of the participle property.
	 * 
	 * @param value
	 *            allowed object is {@link mila.generated.ParticipleType}
	 */
	void setParticiple(mila.generated.ParticipleType value);

	/**
	 * Sets the value of the preposition property.
	 * 
	 * @param value
	 *            allowed object is {@link mila.generated.GenderNumberPersonType}
	 */
	void setPreposition(mila.generated.GenderNumberPersonType value);

	/**
	 * Sets the value of the pronoun property.
	 * 
	 * @param value
	 *            allowed object is {@link mila.generated.PronounType}
	 */
	void setPronoun(mila.generated.PronounType value);

	/**
	 * Sets the value of the properName property.
	 * 
	 * @param value
	 *            allowed object is {@link mila.generated.ProperNameType}
	 */
	void setProperName(mila.generated.ProperNameType value);

	/**
	 * Sets the value of the punctuation property.
	 * 
	 * @param value
	 *            allowed object is {@link mila.generated.PunctuationType}
	 */
	void setPunctuation(mila.generated.PunctuationType value);

	/**
	 * Sets the value of the quantifier property.
	 * 
	 * @param value
	 *            allowed object is {@link mila.generated.QuantifierType}
	 */
	void setQuantifier(mila.generated.QuantifierType value);

	/**
	 * Sets the value of the title property.
	 * 
	 * @param value
	 *            allowed object is {@link mila.generated.TitleType}
	 */
	void setTitle(mila.generated.TitleType value);

	/**
	 * Sets the value of the transliteratedLexiconItem property.
	 * 
	 * @param value
	 *            allowed object is {@link java.lang.String}
	 */
	void setTransliteratedLexiconItem(java.lang.String value);

	/**
	 * Sets the value of the unknown property.
	 * 
	 * @param value
	 *            allowed object is {@link mila.generated.UnknownType}
	 */
	void setUnknown(mila.generated.UnknownType value);

	/**
	 * Sets the value of the url property.
	 * 
	 * @param value
	 *            allowed object is {@link mila.generated.URLType}
	 */
	void setUrl(mila.generated.URLType value);

	/**
	 * Sets the value of the verb property.
	 * 
	 * @param value
	 *            allowed object is {@link mila.generated.VerbType}
	 */
	void setVerb(mila.generated.VerbType value);

	/**
	 * Sets the value of the wPrefix property.
	 * 
	 * @param value
	 *            allowed object is {@link mila.generated.WprefixType}
	 */
	void setWPrefix(mila.generated.WprefixType value);

	/**
	 * Sets the value of the zevel property.
	 * 
	 * @param value
	 *            allowed object is {@link mila.generated.ZVLType}
	 */
	void setZevel(mila.generated.ZVLType value);

}
