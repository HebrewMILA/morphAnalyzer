//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v1.0.5-b16-fcs 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2009.06.14 at 03:41:34 PM IDT 
//

package mila.generated;

import javax.xml.bind.JAXBException;
import javax.xml.bind.PropertyException;

/**
 * This object contains factory methods for each Java content interface and Java
 * element interface generated in the generated package.
 * <p>
 * An ObjectFactory allows you to programatically construct new instances of the
 * Java representation for XML content. The Java representation of XML content
 * can consist of schema derived interfaces and classes representing the binding
 * of schema type definitions, element declarations and model groups. Factory
 * methods for each of these are provided in this class.
 * 
 */
public class ObjectFactory extends
		mila.generated.impl.runtime.DefaultJAXBContextImpl {

	private static java.util.HashMap defaultImplementations = new java.util.HashMap(
			55, 0.75F);
	private static java.util.HashMap rootTagMap = new java.util.HashMap();
	public final static mila.generated.impl.runtime.GrammarInfo grammarInfo = new mila.generated.impl.runtime.GrammarInfoImpl(
			rootTagMap, defaultImplementations, (mila.generated.ObjectFactory.class));
	public final static java.lang.Class version = (mila.generated.impl.JAXBVersion.class);

	static {
		defaultImplementations.put(
				(mila.generated.TransliterationType.StringType.class),
				"mila.generated.impl.TransliterationTypeImpl.StringTypeImpl");
		defaultImplementations.put((mila.generated.InterjectionType.class),
				"mila.generated.impl.InterjectionTypeImpl");
		defaultImplementations.put((mila.generated.WprefixType.class),
				"mila.generated.impl.WprefixTypeImpl");
		defaultImplementations.put((mila.generated.ExistentialType.class),
				"mila.generated.impl.ExistentialTypeImpl");
		defaultImplementations.put((mila.generated.ArticleType.class),
				"mila.generated.impl.ArticleTypeImpl");
		defaultImplementations.put((mila.generated.NegationType.class),
				"mila.generated.impl.NegationTypeImpl");
		defaultImplementations.put((mila.generated.SuffixType.class),
				"mila.generated.impl.SuffixTypeImpl");
		defaultImplementations.put((mila.generated.SourceType.class),
				"mila.generated.impl.SourceTypeImpl");
		defaultImplementations.put((mila.generated.NumeralType.class),
				"mila.generated.impl.NumeralTypeImpl");
		defaultImplementations.put((mila.generated.AnalysisType.class),
				"mila/generated.impl.AnalysisTypeImpl");
		defaultImplementations.put((mila.generated.PronounType.class),
				"mila.generated.impl.PronounTypeImpl");
		defaultImplementations.put((mila.generated.MetadataType.class),
				"mila.generated.impl.MetadataTypeImpl");
		defaultImplementations.put((mila.generated.GenderNumberPersonType.class),
				"mila.generated.impl.GenderNumberPersonTypeImpl");
		defaultImplementations.put((mila.generated.ZVLType.class),
				"mila.generated.impl.ZVLTypeImpl");
		defaultImplementations.put((mila.generated.VerbType.class),
				"mila.generated.impl.VerbTypeImpl");
		defaultImplementations.put(
				(mila.generated.GenderNumberStatusDefinitenessType.class),
				"mila.generated.impl.GenderNumberStatusDefinitenessTypeImpl");
		defaultImplementations.put((mila.generated.UnknownType.class),
				"mila.generated.impl.UnknownTypeImpl");
		defaultImplementations.put((mila.generated.ConjunctionType.class),
				"mila.generated.impl.ConjunctionTypeImpl");
		defaultImplementations.put((mila.generated.MWEType.class),
				"mila.generated.impl.MWETypeImpl");
		defaultImplementations.put((mila.generated.CopulaType.class),
				"mila.generated.impl.CopulaTypeImpl");
		defaultImplementations.put((mila.generated.ImpersonalType.class),
				"mila.generated.impl.ImpersonalTypeImpl");
		defaultImplementations.put((mila.generated.ModalType.class),
				"mila.generated.impl.ModalTypeImpl");
		defaultImplementations.put((mila.generated.GenderNumberStatusType.class),
				"mila.generated.impl.GenderNumberStatusTypeImpl");
		defaultImplementations.put((mila.generated.URLType.class),
				"mila.generated.impl.URLTypeImpl");
		defaultImplementations.put((mila.generated.CorpusType.class),
				"mila.generated.impl.CorpusTypeImpl");
		defaultImplementations.put((mila.generated.TitleType.class),
				"mila.generated.impl.TitleTypeImpl");
		defaultImplementations.put((mila.generated.Corpus.class),
				"mila.generated.impl.CorpusImpl");
		defaultImplementations.put((mila.generated.ProperNameType.class),
				"mila.generated.impl.ProperNameTypeImpl");
		defaultImplementations.put((mila.generated.PrefixType.class),
				"mila.generated.impl.PrefixTypeImpl");
		defaultImplementations.put((mila.generated.TransliterationType.class),
				"mila.generated.impl.TransliterationTypeImpl");
		defaultImplementations.put((mila.generated.SentenceType.class),
				"mila.generated.impl.SentenceTypeImpl");
		defaultImplementations.put((mila.generated.ParagraphType.class),
				"mila.generated.impl.ParagraphTypeImpl");
		defaultImplementations.put((mila.generated.TokenType.class),
				"mila.generated.impl.TokenTypeImpl");
		defaultImplementations.put((mila.generated.QuantifierType.class),
				"mila.generated.impl.QuantifierTypeImpl");
		defaultImplementations.put((mila.generated.InterrogativeType.class),
				"mila.generated.impl.InterrogativeTypeImpl");
		defaultImplementations.put((mila.generated.PunctuationType.class),
				"mila.generated.impl.PunctuationTypeImpl");
		defaultImplementations.put((mila.generated.NumberExpressionType.class),
				"mila.generated.impl.NumberExpressionTypeImpl");
		defaultImplementations.put((mila.generated.ParticipleType.class),
				"mila.generated.impl.ParticipleTypeImpl");
		defaultImplementations.put((mila.generated.ForeignType.class),
				"mila.generated.impl.ForeignTypeImpl");
		defaultImplementations.put((mila.generated.BaseType.class),
				"mila.generated.impl.BaseTypeImpl");
		rootTagMap.put(new javax.xml.namespace.QName("", "corpus"),
				(mila.generated.Corpus.class));
	}

	/**
	 * Create a new ObjectFactory that can be used to create new instances of
	 * schema derived classes for package: generated
	 * 
	 */
	public ObjectFactory() {
		super(grammarInfo);
	}

	/**
	 * Create an instance of AnalysisType
	 * 
	 * @throws JAXBException
	 *             if an error occurs
	 */
	public mila.generated.AnalysisType createAnalysisType()
			throws javax.xml.bind.JAXBException {
		return new mila.generated.impl.AnalysisTypeImpl();
	}

	/**
	 * Create an instance of ArticleType
	 * 
	 * @throws JAXBException
	 *             if an error occurs
	 */
	public mila.generated.ArticleType createArticleType()
			throws javax.xml.bind.JAXBException {
		return new mila.generated.impl.ArticleTypeImpl();
	}

	/**
	 * Create an instance of BaseType
	 * 
	 * @throws JAXBException
	 *             if an error occurs
	 */
	public mila.generated.BaseType createBaseType()
			throws javax.xml.bind.JAXBException {
		return new mila.generated.impl.BaseTypeImpl();
	}

	/**
	 * Create an instance of ConjunctionType
	 * 
	 * @throws JAXBException
	 *             if an error occurs
	 */
	public mila.generated.ConjunctionType createConjunctionType()
			throws javax.xml.bind.JAXBException {
		return new mila.generated.impl.ConjunctionTypeImpl();
	}

	/**
	 * Create an instance of CopulaType
	 * 
	 * @throws JAXBException
	 *             if an error occurs
	 */
	public mila.generated.CopulaType createCopulaType()
			throws javax.xml.bind.JAXBException {
		return new mila.generated.impl.CopulaTypeImpl();
	}

	/**
	 * Create an instance of Corpus
	 * 
	 * @throws JAXBException
	 *             if an error occurs
	 */
	public mila.generated.Corpus createCorpus() throws javax.xml.bind.JAXBException {
		return new mila.generated.impl.CorpusImpl();
	}

	/**
	 * Create an instance of CorpusType
	 * 
	 * @throws JAXBException
	 *             if an error occurs
	 */
	public mila.generated.CorpusType createCorpusType()
			throws javax.xml.bind.JAXBException {
		return new mila.generated.impl.CorpusTypeImpl();
	}

	/**
	 * Create an instance of ExistentialType
	 * 
	 * @throws JAXBException
	 *             if an error occurs
	 */
	public mila.generated.ExistentialType createExistentialType()
			throws javax.xml.bind.JAXBException {
		return new mila.generated.impl.ExistentialTypeImpl();
	}

	/**
	 * Create an instance of ForeignType
	 * 
	 * @throws JAXBException
	 *             if an error occurs
	 */
	public mila.generated.ForeignType createForeignType()
			throws javax.xml.bind.JAXBException {
		return new mila.generated.impl.ForeignTypeImpl();
	}

	/**
	 * Create an instance of GenderNumberPersonType
	 * 
	 * @throws JAXBException
	 *             if an error occurs
	 */
	public mila.generated.GenderNumberPersonType createGenderNumberPersonType()
			throws javax.xml.bind.JAXBException {
		return new mila.generated.impl.GenderNumberPersonTypeImpl();
	}

	/**
	 * Create an instance of GenderNumberStatusDefinitenessType
	 * 
	 * @throws JAXBException
	 *             if an error occurs
	 */
	public mila.generated.GenderNumberStatusDefinitenessType createGenderNumberStatusDefinitenessType()
			throws javax.xml.bind.JAXBException {
		return new mila.generated.impl.GenderNumberStatusDefinitenessTypeImpl();
	}

	/**
	 * Create an instance of GenderNumberStatusType
	 * 
	 * @throws JAXBException
	 *             if an error occurs
	 */
	public mila.generated.GenderNumberStatusType createGenderNumberStatusType()
			throws javax.xml.bind.JAXBException {
		return new mila.generated.impl.GenderNumberStatusTypeImpl();
	}

	/**
	 * Create an instance of ImpersonalType
	 * 
	 * @throws JAXBException
	 *             if an error occurs
	 */
	public mila.generated.ImpersonalType createImpersonalType()
			throws javax.xml.bind.JAXBException {
		return new mila.generated.impl.ImpersonalTypeImpl();
	}

	/**
	 * Create an instance of InterjectionType
	 * 
	 * @throws JAXBException
	 *             if an error occurs
	 */
	public mila.generated.InterjectionType createInterjectionType()
			throws javax.xml.bind.JAXBException {
		return new mila.generated.impl.InterjectionTypeImpl();
	}

	/**
	 * Create an instance of InterrogativeType
	 * 
	 * @throws JAXBException
	 *             if an error occurs
	 */
	public mila.generated.InterrogativeType createInterrogativeType()
			throws javax.xml.bind.JAXBException {
		return new mila.generated.impl.InterrogativeTypeImpl();
	}

	/**
	 * Create an instance of MetadataType
	 * 
	 * @throws JAXBException
	 *             if an error occurs
	 */
	public mila.generated.MetadataType createMetadataType()
			throws javax.xml.bind.JAXBException {
		return new mila.generated.impl.MetadataTypeImpl();
	}

	/**
	 * Create an instance of ModalType
	 * 
	 * @throws JAXBException
	 *             if an error occurs
	 */
	public mila.generated.ModalType createModalType()
			throws javax.xml.bind.JAXBException {
		return new mila.generated.impl.ModalTypeImpl();
	}

	/**
	 * Create an instance of MWEType
	 * 
	 * @throws JAXBException
	 *             if an error occurs
	 */
	public mila.generated.MWEType createMWEType()
			throws javax.xml.bind.JAXBException {
		return new mila.generated.impl.MWETypeImpl();
	}

	/**
	 * Create an instance of NegationType
	 * 
	 * @throws JAXBException
	 *             if an error occurs
	 */
	public mila.generated.NegationType createNegationType()
			throws javax.xml.bind.JAXBException {
		return new mila.generated.impl.NegationTypeImpl();
	}

	/**
	 * Create an instance of NumberExpressionType
	 * 
	 * @throws JAXBException
	 *             if an error occurs
	 */
	public mila.generated.NumberExpressionType createNumberExpressionType()
			throws javax.xml.bind.JAXBException {
		return new mila.generated.impl.NumberExpressionTypeImpl();
	}

	/**
	 * Create an instance of NumeralType
	 * 
	 * @throws JAXBException
	 *             if an error occurs
	 */
	public mila.generated.NumeralType createNumeralType()
			throws javax.xml.bind.JAXBException {
		return new mila.generated.impl.NumeralTypeImpl();
	}

	/**
	 * Create an instance of ParagraphType
	 * 
	 * @throws JAXBException
	 *             if an error occurs
	 */
	public mila.generated.ParagraphType createParagraphType()
			throws javax.xml.bind.JAXBException {
		return new mila.generated.impl.ParagraphTypeImpl();
	}

	/**
	 * Create an instance of ParticipleType
	 * 
	 * @throws JAXBException
	 *             if an error occurs
	 */
	public mila.generated.ParticipleType createParticipleType()
			throws javax.xml.bind.JAXBException {
		return new mila.generated.impl.ParticipleTypeImpl();
	}

	/**
	 * Create an instance of PrefixType
	 * 
	 * @throws JAXBException
	 *             if an error occurs
	 */
	public mila.generated.PrefixType createPrefixType()
			throws javax.xml.bind.JAXBException {
		return new mila.generated.impl.PrefixTypeImpl();
	}

	/**
	 * Create an instance of PronounType
	 * 
	 * @throws JAXBException
	 *             if an error occurs
	 */
	public mila.generated.PronounType createPronounType()
			throws javax.xml.bind.JAXBException {
		return new mila.generated.impl.PronounTypeImpl();
	}

	/**
	 * Create an instance of ProperNameType
	 * 
	 * @throws JAXBException
	 *             if an error occurs
	 */
	public mila.generated.ProperNameType createProperNameType()
			throws javax.xml.bind.JAXBException {
		return new mila.generated.impl.ProperNameTypeImpl();
	}

	/**
	 * Create an instance of PunctuationType
	 * 
	 * @throws JAXBException
	 *             if an error occurs
	 */
	public mila.generated.PunctuationType createPunctuationType()
			throws javax.xml.bind.JAXBException {
		return new mila.generated.impl.PunctuationTypeImpl();
	}

	/**
	 * Create an instance of QuantifierType
	 * 
	 * @throws JAXBException
	 *             if an error occurs
	 */
	public mila.generated.QuantifierType createQuantifierType()
			throws javax.xml.bind.JAXBException {
		return new mila.generated.impl.QuantifierTypeImpl();
	}

	/**
	 * Create an instance of SentenceType
	 * 
	 * @throws JAXBException
	 *             if an error occurs
	 */
	public mila.generated.SentenceType createSentenceType()
			throws javax.xml.bind.JAXBException {
		return new mila.generated.impl.SentenceTypeImpl();
	}

	/**
	 * Create an instance of SourceType
	 * 
	 * @throws JAXBException
	 *             if an error occurs
	 */
	public mila.generated.SourceType createSourceType()
			throws javax.xml.bind.JAXBException {
		return new mila.generated.impl.SourceTypeImpl();
	}

	/**
	 * Create an instance of SuffixType
	 * 
	 * @throws JAXBException
	 *             if an error occurs
	 */
	public mila.generated.SuffixType createSuffixType()
			throws javax.xml.bind.JAXBException {
		return new mila.generated.impl.SuffixTypeImpl();
	}

	/**
	 * Create an instance of TitleType
	 * 
	 * @throws JAXBException
	 *             if an error occurs
	 */
	public mila.generated.TitleType createTitleType()
			throws javax.xml.bind.JAXBException {
		return new mila.generated.impl.TitleTypeImpl();
	}

	/**
	 * Create an instance of TokenType
	 * 
	 * @throws JAXBException
	 *             if an error occurs
	 */
	public mila.generated.TokenType createTokenType()
			throws javax.xml.bind.JAXBException {
		return new mila.generated.impl.TokenTypeImpl();
	}

	/**
	 * Create an instance of TransliterationType
	 * 
	 * @throws JAXBException
	 *             if an error occurs
	 */
	public mila.generated.TransliterationType createTransliterationType()
			throws javax.xml.bind.JAXBException {
		return new mila.generated.impl.TransliterationTypeImpl();
	}

	/**
	 * Create an instance of TransliterationTypeStringType
	 * 
	 * @throws JAXBException
	 *             if an error occurs
	 */
	public mila.generated.TransliterationType.StringType createTransliterationTypeStringType()
			throws javax.xml.bind.JAXBException {
		return new mila.generated.impl.TransliterationTypeImpl.StringTypeImpl();
	}

	/**
	 * Create an instance of UnknownType
	 * 
	 * @throws JAXBException
	 *             if an error occurs
	 */
	public mila.generated.UnknownType createUnknownType()
			throws javax.xml.bind.JAXBException {
		return new mila.generated.impl.UnknownTypeImpl();
	}

	/**
	 * Create an instance of URLType
	 * 
	 * @throws JAXBException
	 *             if an error occurs
	 */
	public mila.generated.URLType createURLType()
			throws javax.xml.bind.JAXBException {
		return new mila.generated.impl.URLTypeImpl();
	}

	/**
	 * Create an instance of VerbType
	 * 
	 * @throws JAXBException
	 *             if an error occurs
	 */
	public mila.generated.VerbType createVerbType()
			throws javax.xml.bind.JAXBException {
		return new mila.generated.impl.VerbTypeImpl();
	}

	/**
	 * Create an instance of WprefixType
	 * 
	 * @throws JAXBException
	 *             if an error occurs
	 */
	public mila.generated.WprefixType createWprefixType()
			throws javax.xml.bind.JAXBException {
		return new mila.generated.impl.WprefixTypeImpl();
	}

	/**
	 * Create an instance of ZVLType
	 * 
	 * @throws JAXBException
	 *             if an error occurs
	 */
	public mila.generated.ZVLType createZVLType()
			throws javax.xml.bind.JAXBException {
		return new mila.generated.impl.ZVLTypeImpl();
	}

	/**
	 * Get the specified property. This method can only be used to get provider
	 * specific properties. Attempting to get an undefined property will result
	 * in a PropertyException being thrown.
	 * 
	 * @param name
	 *            the name of the property to retrieve
	 * @return the value of the requested property
	 * @throws PropertyException
	 *             when there is an error retrieving the given property or value
	 */
	@Override
	public java.lang.Object getProperty(java.lang.String name)
			throws javax.xml.bind.PropertyException {
		return super.getProperty(name);
	}

	/**
	 * Create an instance of the specified Java content interface.
	 * 
	 * @param javaContentInterface
	 *            the Class object of the javacontent interface to instantiate
	 * @return a new instance
	 * @throws JAXBException
	 *             if an error occurs
	 */
	@Override
	public java.lang.Object newInstance(java.lang.Class javaContentInterface)
			throws javax.xml.bind.JAXBException {
		return super.newInstance(javaContentInterface);
	}

	/**
	 * Set the specified property. This method can only be used to set provider
	 * specific properties. Attempting to set an undefined property will result
	 * in a PropertyException being thrown.
	 * 
	 * @param value
	 *            the value of the property to be set
	 * @param name
	 *            the name of the property to retrieve
	 * @throws PropertyException
	 *             when there is an error processing the given property or value
	 */
	@Override
	public void setProperty(java.lang.String name, java.lang.Object value)
			throws javax.xml.bind.PropertyException {
		super.setProperty(name, value);
	}

}
