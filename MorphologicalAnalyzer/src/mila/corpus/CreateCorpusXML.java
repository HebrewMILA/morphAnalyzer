package mila.corpus;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.PropertyException;

import mila.generated.*;
import mila.lexicon.analyse.Str2Num;
import mila.lexicon.utils.PrefixRec;
import mila.lexicon.utils.StringUtils;
import mila.lexicon.utils.Translate;
import mila.tools.api.MilaException;

import static mila.lexicon.analyse.Constants.*;

/**
 *
 * CreateCorpusXML.java Purpose: creates the XML output according to
 * hebrew_MWcorpus.xsd using jaxb generated code input - analysis values as got
 * from the database or the data files output- XML file or stream according to
 * hebrew_MWcorpus.xsd
 *
 * @author Dalia Bojan
 * @version %G%
 */

public class CreateCorpusXML {

	/** Types defined in the automatically generated code of jaxb */
	public final static JAXBContext jc = acquireJAXBContext();

	private ArticleType article;

	protected ObjectFactory objFactory;

	protected Corpus corpus;

	private ParagraphType paragraph;

	private SentenceType sentence;

	protected TokenType token;

	protected AnalysisType analysis;

	private PrefixType pref;

	/** The current token number (id) in the current sentence */
	private int tokenCounter = 0;

	/** The current sentence number (id) in the current paragraph */
	private int sentenceCounter = 0;

	/** The current paragraph number (id) in the current article */
	private int paragraphCounter = 0;

	/** The current analysis number (id) in the analyses of the current token */
	protected int analysisCounter = 0;

	/**
	 * The path of the XML output file (there are two output options (by file or
	 * by stream)
	 */
	protected String outputFile = "";

	/**
	 * used for XML analysis output (there are two output options (by file or by
	 * stream)
	 */
	private OutputStreamWriter pOut = null;

	/**
	 * Empty Constructor - when the output is stream and not file
	 */
	public CreateCorpusXML() {
	}

	/**
	 * Constructor
	 *
	 * @param outputFile
	 *            - XML created output file according to hebrew_MWcorpus.xsd
	 */
	public CreateCorpusXML(String outputFile) {
		this.outputFile = outputFile;
	}

	/**
	 * This method creates and populate adjective analysis
	 *
	 * @param description
	 *            - relevant in case of prefix existence
	 * @param foreign
	 * @param expansion
	 *            - relevant in case of acronym
	 * @param gender
	 * @param number
	 * @param status
	 *            - absolute/constrcut/unspecified
	 * @param transliteratedLexiocnItem
	 * @param lexiconItem
	 * @param lexiconPointer
	 * @param definiteness
	 * @param hebWord
	 * @param register
	 * @param spelling
	 * @param dottedLexiconItem
	 * @throws Exception
	 */
	public void createAdjectiveAnalysis(final String description, final int foreign, final String expansion,
			final String gender, final String number, final String status, final String transliteratedLexiocnItem,
			final String lexiconItem, final String lexiconPointer, final String definiteness, final String hebWord,
			final String register, final String spelling, final String dottedLexiconItem) throws Exception {
		analysisCounter++;

		analysis = objFactory.createAnalysisType();

		analysis.setId(String.valueOf(analysisCounter));
		BaseType base = null;

		base = objFactory.createBaseType();

		if (description.length() > 0) {
			setPrefix(description);
		}
		setBase(base, transliteratedLexiocnItem, lexiconItem, lexiconPointer, dottedLexiconItem);
		GenderNumberStatusDefinitenessType adjective = null;
		try {
			adjective = objFactory.createGenderNumberStatusDefinitenessType();
		} catch (final JAXBException e2) {
			System.err.println(
					"CreateCorpusXML:createAdjectiveAnalysis Exception while create createGenderNumberStatusDefinitenessType for hebWord="
							+ hebWord);
			e2.printStackTrace();
		}
		adjective.setRegister(register);
		adjective.setSpelling(spelling);
		final ENUM_GENDER genderi = Str2Num.str2NumGender(gender, lexiconItem, hebWord);
		if (gender.length() > 0 && genderi != ENUM_GENDER.UNSPECIFIED) {
			adjective.setGender(gender);
		}
		final ENUM_NUMBER numberi = Str2Num.str2NumNumber(number, lexiconItem, hebWord);
		if (number.length() > 0 && numberi != ENUM_NUMBER.UNSPECIFIED) {
			adjective.setNumber(number);
		}
		if (expansion.length() > 0) {
			adjective.setExpansion(expansion);
		}
		adjective.setStatus(status);
		if (foreign == 1) {
			adjective.setForeign(TRUE);
		}
		// the definiteness attribute is not relevant for the construct form
		final ENUM_STATUS statusi = Str2Num.str2NumConstruct(status, lexiconItem, hebWord);
		if (statusi != ENUM_STATUS.CONSTRUCT_TRUE) {
			adjective.setDefiniteness(definiteness);
		}
		adjective.setFunction("adjective");
		base.setAdjective(adjective);
		analysis.setBase(base);
		token.getAnalysis().add(analysis);
	}

	/**
	 * This method creates and populate adverb analysis
	 *
	 * @param description
	 *            - relevant in case of prefix
	 * @param suffixFunctioni
	 * @param transliteratedLexiocnItem
	 * @param lexiconItem
	 * @param lexiconPointer
	 * @param hebWord
	 * @param register
	 * @param spelling
	 * @param dottedLexiconItem
	 * @param expansion
	 *            - relevant in case of acronym
	 * @param PGN
	 * @throws JAXBException
	 */
	public void createAdverbAnalysis(final String description, ENUM_SUFFIX_FUNCTION suffixFunctioni,
			final String transliteratedLexiocnItem, final String lexiconItem, final String lexiconPointer,
			final String hebWord, final String register, final String spelling, final String dottedLexiconItem,
			final String expansion, String PGN) throws JAXBException {
		analysisCounter++;

		analysis = objFactory.createAnalysisType();

		analysis.setId(String.valueOf(analysisCounter));
		BaseType base = null;

		base = objFactory.createBaseType();

		if (description.length() > 0) {
			setPrefix(description);
		}
		setBase(base, transliteratedLexiocnItem, lexiconItem, lexiconPointer, dottedLexiconItem);
		GenderNumberPersonType adverb = null;

		adverb = objFactory.createGenderNumberPersonType();

		if (expansion.length() > 0) {
			adverb.setExpansion(expansion);
		}
		adverb.setRegister(register);
		adverb.setSpelling(spelling);

		if (suffixFunctioni == ENUM_SUFFIX_FUNCTION.SUFFIX_FUNCTION_PRONOMIAL) {
			SuffixType suffix = null;
			suffix = setSuffix(PGN, PRONOMIAL);
			analysis.setSuffix(suffix);
		}
		base.setAdverb(adverb);
		analysis.setBase(base);
		token.getAnalysis().add(analysis);
	}

	/**
	 * This method creates the article entity of the output XML - each file/
	 * stream is always relevant for a single article. each article is composed
	 * of several paragraphs <b> and each paragraph is composed of several
	 * sentences <br>
	 * and each sentence is composed of several tokens and each token has
	 * several avalysis
	 */
	public void createArticle() {
		// System.out.println("(F) createArticle() ");
		try {
			article = objFactory.createArticleType();
		} catch (final JAXBException e) {
			System.err.println("CreateCorpusXML:createArticle Exception while creating article");
			e.printStackTrace();
		}
		article.setId("1");
		corpus.getArticle().add(article);
	}

	/**
	 * This method creates and populates conjunction analysis
	 *
	 * @param description
	 *            - relevant in case of prefix analysis
	 * @param expansion
	 *            - relevant in case of acronym
	 * @param type
	 * @param transliteratedLexiocnItem
	 * @param lexiconItem
	 * @param lexiconPointer
	 * @param hebWord
	 * @param register
	 * @param spelling
	 * @param dottedLexiconItem
	 * @throws JAXBException
	 */
	public void createConjunctionAnalysis(final String description, final String expansion, final String type,
			final String transliteratedLexiocnItem, final String lexiconItem, final String lexiconPointer,
			final String hebWord, final String register, final String spelling, final String dottedLexiconItem)
					throws JAXBException {
		analysisCounter++;

		analysis = objFactory.createAnalysisType();

		analysis.setId(String.valueOf(analysisCounter));
		BaseType base = null;

		base = objFactory.createBaseType();

		if (description.length() > 0) {
			setPrefix(description);
		}
		setBase(base, transliteratedLexiocnItem, lexiconItem, lexiconPointer, dottedLexiconItem);
		ConjunctionType conjunction = null;

		conjunction = objFactory.createConjunctionType();

		if (expansion.length() > 0) {
			conjunction.setExpansion(expansion);
		}
		conjunction.setRegister(register);
		conjunction.setSpelling(spelling);
		conjunction.setType(type);
		base.setConjunction(conjunction);

		analysis.setBase(base);
		token.getAnalysis().add(analysis);
	}

	/**
	 * This method creates and populates copula analysis
	 *
	 * @param description
	 *            - relevant in case of prefix existence
	 * @param suffixFunctioni
	 * @param gender
	 * @param number
	 * @param person
	 * @param polarity
	 * @param tense
	 * @param transliteratedLexiocnItem
	 * @param lexiconItem
	 * @param register
	 * @param spelling
	 * @param lexiconPointer
	 * @param hebWord
	 * @param dottedLexiconItem
	 * @param PGN
	 *            - person/gender/number of suffix
	 * @throws Exception
	 */
	public void createCopulaAnalysis(final String description, ENUM_SUFFIX_FUNCTION suffixFunctioni,
			final String gender, final String number, final String person, final String polarity, final String tense,
			final String transliteratedLexiocnItem, final String lexiconItem, final String register,
			final String spelling, final String lexiconPointer, final String hebWord, final String dottedLexiconItem,
			final String PGN) throws Exception {
		analysisCounter++;

		analysis = objFactory.createAnalysisType();

		analysis.setId(String.valueOf(analysisCounter));
		BaseType base = null;

		base = objFactory.createBaseType();

		if (description.length() > 0) {
			setPrefix(description);
		}
		setBase(base, transliteratedLexiocnItem, lexiconItem, lexiconPointer, dottedLexiconItem);
		CopulaType copula = null;

		copula = objFactory.createCopulaType();

		final ENUM_TENSE tensei = Str2Num.str2NumTense(tense, lexiconItem, hebWord);
		if (tense.length() > 0 && tensei != ENUM_TENSE.INFINITIVE) {
			copula.setGender(gender);
			copula.setNumber(number);
			copula.setPerson(person);
		}
		copula.setPolarity(polarity);
		// unspecified
		if (tense.charAt(0) != 'u') {
			copula.setTense(tense);
		}
		copula.setRegister(register);
		copula.setSpelling(spelling);
		base.setCopula(copula);

		if (suffixFunctioni == ENUM_SUFFIX_FUNCTION.SUFFIX_FUNCTION_ACCUSATIVE_OR_NOMINATIVE) {
			SuffixType suffix = null;
			suffix = setSuffix(PGN, ACCUSATIVE_OR_NOMINATIVE);
			analysis.setSuffix(suffix);

		}

		analysis.setBase(base);
		token.getAnalysis().add(analysis);
	}

	/**
	 * This method creates the existential analysis
	 *
	 * @param description
	 *            - relevant in case of prefix
	 * @param suffixFunctioni
	 * @param gender
	 * @param number
	 * @param transliterated
	 * @param lexiconItem
	 * @param lexiconPointer
	 * @param root
	 * @param polarity
	 * @param tense
	 * @param definiteness
	 * @param basePerson
	 * @param hebWord
	 * @param register
	 * @param spelling
	 * @param dottedLexiconItem
	 * @param PGN
	 *            - person/gender/number of suffix
	 * @throws Exception
	 */
	public void createExistentialAnalysis(final String description, ENUM_SUFFIX_FUNCTION suffixFunctioni,
			final String gender, final String number, final String transliterated, final String lexiconItem,
			final String lexiconPointer, final String root, final String polarity, final String tense,
			final String definiteness, final String basePerson, final String hebWord, final String register,
			final String spelling, final String dottedLexiconItem, final String PGN) throws Exception {
		analysisCounter++;

		analysis = objFactory.createAnalysisType();

		analysis.setId(String.valueOf(analysisCounter));
		BaseType base = null;

		base = objFactory.createBaseType();

		if (description.length() > 0) {
			setPrefix(description);
		}
		setBase(base, transliterated, lexiconItem, lexiconPointer, dottedLexiconItem);
		ExistentialType existential = null;

		existential = objFactory.createExistentialType();

		existential.setRegister(register);
		existential.setSpelling(spelling);
		existential.setInterrogative(definiteness);

		if (root.length() > 0 && root.charAt(0) != '-') {
			existential.setRoot(root);
		}
		final ENUM_GENDER genderi = Str2Num.str2NumGender(gender, lexiconItem, hebWord);
		if (gender.length() > 0 && genderi != ENUM_GENDER.UNSPECIFIED) {
			existential.setGender(gender);
		}
		final ENUM_NUMBER numberi = Str2Num.str2NumNumber(number, lexiconItem, hebWord);
		if (number.length() > 0 && numberi != ENUM_NUMBER.UNSPECIFIED) {
			existential.setNumber(number);
		}
		final ENUM_TENSE tensei = Str2Num.str2NumTense(tense, lexiconItem, hebWord);
		if (tense.length() > 0 && tensei != ENUM_TENSE.UNSPECIFIED) {
			existential.setTense(tense);
		}

		existential.setPolarity(polarity);

		if (suffixFunctioni == ENUM_SUFFIX_FUNCTION.SUFFIX_FUNCTION_PRONOMIAL) {
			SuffixType suffix = null;
			suffix = setSuffix(PGN, PRONOMIAL);
			analysis.setSuffix(suffix);
		}

		base.setExistential(existential);
		analysis.setBase(base);
		token.getAnalysis().add(analysis);
	}

	/**
	 * This method creates and populate foreign analysis ( a token consists of
	 * Latin lettres)
	 *
	 * @throws JAXBException
	 */
	public void createForeignAnalysis() throws JAXBException {
		analysisCounter++;

		analysis = objFactory.createAnalysisType();

		analysis.setId(String.valueOf(analysisCounter));
		BaseType base = null;

		base = objFactory.createBaseType();

		ForeignType foreign = null;

		foreign = objFactory.createForeignType();

		base.setForeign(foreign);
		analysis.setBase(base);
		token.getAnalysis().add(analysis);
	}

	/**
	 * This method creates and populate interjection analysis
	 *
	 * @param description
	 *            - relevant in case of prefix existence
	 * @param suffixFunctioni
	 * @param transliteratedLexiocnItem
	 * @param lexiconItem
	 * @param lexiconPointer
	 * @param hebWord
	 * @param register
	 * @param spelling
	 * @param dottedLexiconItem
	 * @param PGN
	 *            - person/gender/number in suffix
	 * @throws JAXBException
	 */
	public void createInterjectionAnalysis(final String description, ENUM_SUFFIX_FUNCTION suffixFunctioni,
			final String transliteratedLexiocnItem, final String lexiconItem, final String lexiconPointer,
			final String hebWord, final String register, final String spelling, final String dottedLexiconItem,
			final String PGN) throws JAXBException {
		analysisCounter++;

		analysis = objFactory.createAnalysisType();

		analysis.setId(String.valueOf(analysisCounter));

		BaseType base = null;

		base = objFactory.createBaseType();

		if (description.length() > 0) {
			setPrefix(description);
		}
		setBase(base, transliteratedLexiocnItem, lexiconItem, lexiconPointer, dottedLexiconItem);
		InterjectionType interjection = null;

		interjection = objFactory.createInterjectionType();

		if (suffixFunctioni == ENUM_SUFFIX_FUNCTION.SUFFIX_FUNCTION_PRONOMIAL) {
			SuffixType suffix = null;
			suffix = setSuffix(PGN, PRONOMIAL);
			analysis.setSuffix(suffix);
		}
		base.setInterjection(interjection);
		interjection.setRegister(register);
		interjection.setSpelling(spelling);
		analysis.setBase(base);
		token.getAnalysis().add(analysis);
	}

	/**
	 * This method creates and populate interrogative analysis
	 *
	 * @param description
	 *            - relevant in case of prefix existence
	 * @param transliteratedLexiocnItem
	 * @param lexiconItem
	 * @param lexiconPointer
	 * @param hebWord
	 * @param register
	 * @param spelling
	 * @param dottedLexiconItem
	 * @param PGN
	 *            - person/gender/number in suffix
	 * @param suffixFunctioni
	 * @param interrogativeType
	 * @throws JAXBException
	 */
	public void createInterrogativeAnalysis(final String description, final String transliteratedLexiocnItem,
			final String lexiconItem, final String lexiconPointer, final String hebWord, final String register,
			final String spelling, final String dottedLexiconItem, final String PGN,
			final ENUM_SUFFIX_FUNCTION suffixFunctioni, final String interrogativeType) throws JAXBException {
		analysisCounter++;

		analysis = objFactory.createAnalysisType();

		analysis.setId(String.valueOf(analysisCounter));
		BaseType base = null;

		base = objFactory.createBaseType();

		if (description.length() > 0) {
			setPrefix(description);
		}
		setBase(base, transliteratedLexiocnItem, lexiconItem, lexiconPointer, dottedLexiconItem);
		InterrogativeType interrogative = null;

		interrogative = objFactory.createInterrogativeType();

		interrogative.setRegister(register);
		interrogative.setSpelling(spelling);
		interrogative.setType(interrogativeType);
		base.setInterrogative(interrogative);

		if (suffixFunctioni == ENUM_SUFFIX_FUNCTION.SUFFIX_FUNCTION_PRONOMIAL) {
			SuffixType suffix = null;
			suffix = setSuffix(PGN, PRONOMIAL);
			analysis.setSuffix(suffix);
		}

		analysis.setBase(base);
		token.getAnalysis().add(analysis);
	}

	/**
	 * This method creates and populates modal analysis
	 *
	 * @param description
	 *            - relevant in case of prefix existence
	 * @param gender
	 * @param number
	 * @param basePerson
	 * @param tense
	 * @param transliteratedLexiocnItem
	 * @param lexiconItem
	 * @param lexiconPointer
	 * @param hebWord
	 * @param register
	 * @param spelling
	 * @param dottedLexiconItem
	 * @param hAttributei
	 * @throws Exception
	 */
	public void createModalAnalysis(final String description, final String gender, final String number,
			final String basePerson, final String tense, final String transliteratedLexiocnItem,
			final String lexiconItem, final String lexiconPointer, final String hebWord, final String register,
			final String spelling, final String dottedLexiconItem, final ENUM_HATTRIBUTE hAttributei) throws Exception {
		analysisCounter++;

		analysis = objFactory.createAnalysisType();

		analysis.setId(String.valueOf(analysisCounter));
		BaseType base = null;

		base = objFactory.createBaseType();

		if (description.length() > 0) {
			setPrefix(description);
		}
		setBase(base, transliteratedLexiocnItem, lexiconItem, lexiconPointer, dottedLexiconItem);
		ModalType modal = null;
		try {
			modal = objFactory.createModalType();
		} catch (final JAXBException e2) {
			System.err.println(
					"CreateCorpusXML:createModalAnalysis Exception while creating ModalType for hebWord=" + hebWord);
			e2.printStackTrace();
		}
		modal.setRegister(register);
		modal.setSpelling(spelling);
		final ENUM_GENDER genderi = Str2Num.str2NumGender(gender, lexiconItem, hebWord);
		if (gender.length() > 0 && genderi != ENUM_GENDER.UNSPECIFIED) {
			modal.setGender(gender);
		}

		final ENUM_NUMBER numberi = Str2Num.str2NumNumber(number, lexiconItem, hebWord);
		if (number.length() > 0 && numberi != ENUM_NUMBER.UNSPECIFIED) {
			modal.setNumber(number);
		}

		// u-> unspecified
		if (basePerson.charAt(0) != 'u') {
			modal.setPerson(basePerson);
		}

		final ENUM_TENSE tensei = Str2Num.str2NumTense(tense, lexiconItem, hebWord);
		if (tensei != ENUM_TENSE.UNSPECIFIED) {
			modal.setTense(tense);
		}

		if (hAttributei == ENUM_HATTRIBUTE.SUBCOORDINATING) {
			modal.setSubcoordinating("true");
		}

		base.setModal(modal);
		analysis.setBase(base);
		token.getAnalysis().add(analysis);
	}

	/**
	 * This method creates and populate negation analysis
	 *
	 * @param description
	 *            - relevant in case of prefix existence
	 * @param transliteratedLexiocnItem
	 * @param lexiconItem
	 * @param lexiconPointer
	 * @param hebWord
	 * @param register
	 * @param spelling
	 * @param dottedLexiconItem
	 * @param definiteness
	 * @throws JAXBException
	 */
	public void createNegationAnalysis(final String description, final String transliteratedLexiocnItem,
			final String lexiconItem, final String lexiconPointer, final String hebWord, final String register,
			final String spelling, final String dottedLexiconItem, final String definiteness) throws JAXBException {
		analysisCounter++;

		analysis = objFactory.createAnalysisType();

		analysis.setId(String.valueOf(analysisCounter));
		BaseType base = null;

		base = objFactory.createBaseType();

		if (description.length() > 0) {
			setPrefix(description);
		}
		setBase(base, transliteratedLexiocnItem, lexiconItem, lexiconPointer, dottedLexiconItem);
		NegationType negation = null;

		negation = objFactory.createNegationType();

		negation.setRegister(register);
		negation.setSpelling(spelling);
		negation.setDefiniteness(definiteness);
		base.setNegation(negation);
		analysis.setBase(base);
		token.getAnalysis().add(analysis);
	}

	/**
	 * This method creates and popualte noun analysis
	 *
	 * @param description
	 *            - relevant in case of prefix existence
	 * @param suffixFunctioni
	 * @param constructi
	 * @param foreign
	 *            - true/false in case it's a foreign word like
	 * @param expansion
	 *            - relevant in case of acronym
	 * @param gender
	 * @param number
	 * @param status
	 * @param transliteratedLexiocnItem
	 * @param lexiconItem
	 * @param lexiconPointer
	 * @param definiteness
	 * @param hebWord
	 * @param register
	 * @param spelling
	 * @param dottedLexiconItem
	 * @param PGN
	 *            - person/gender/number in suffix
	 * @throws JAXBException
	 */
	public void createNounAnalysis(final String description, ENUM_SUFFIX_FUNCTION suffixFunctioni,
			final ENUM_STATUS constructi, final int foreign, final String expansion, final String gender,
			final String number, final String status, final String transliteratedLexiocnItem, final String lexiconItem,
			final String lexiconPointer, final String definiteness, final String hebWord, final String register,
			final String spelling, final String dottedLexiconItem, final String PGN) throws JAXBException {
		analysisCounter++;

		analysis = objFactory.createAnalysisType();

		analysis.setId(String.valueOf(analysisCounter));
		BaseType base = null;

		base = objFactory.createBaseType();

		if (description.length() > 0) {
			setPrefix(description);
		}
		setBase(base, transliteratedLexiocnItem, lexiconItem, lexiconPointer, dottedLexiconItem);
		GenderNumberStatusDefinitenessType noun = null;

		noun = objFactory.createGenderNumberStatusDefinitenessType();

		noun.setRegister(register);
		noun.setSpelling(spelling);
		noun.setGender(gender);
		noun.setNumber(number);
		noun.setStatus(status);
		if (expansion.length() > 0) {
			noun.setExpansion(expansion);
		}
		// the definiteness attribute is not relevant for the construct form
		if (constructi != ENUM_STATUS.CONSTRUCT_TRUE) {
			noun.setDefiniteness(definiteness);
		}
		try {
			if (foreign == 1) {
				noun.setForeign(TRUE);
			}
		} catch (final Exception e) {
			e.printStackTrace();
		}
		base.setNoun(noun);
		analysis.setBase(base);

		if (suffixFunctioni == ENUM_SUFFIX_FUNCTION.SUFFIX_FUNCTION_POSSESSIVE) {
			SuffixType suffix = null;
			// definitenss and status are not relevant for the possessive noun
			noun.setStatus(null);
			noun.setDefiniteness(null);
			suffix = setSuffix(PGN, POSSESSIVE);
			analysis.setSuffix(suffix);

		}
		token.getAnalysis().add(analysis);
	}

	/**
	 * This method creates a number expression - currently we defined the
	 * following number expressions: date game score (20:40) time numbering (1.)
	 *
	 * @param type
	 * @throws Exception
	 */
	public void createNumberExpressionAnalysis(String type) throws Exception {
		// existing types: time,timeGameScore,date,gameScore
		analysisCounter++;
		analysis = objFactory.createAnalysisType();
		analysis.setId(String.valueOf(analysisCounter));
		BaseType base = null;
		base = objFactory.createBaseType();
		NumberExpressionType numberExpression = null;
		numberExpression = objFactory.createNumberExpressionType();
		final ENUM_NUMBER_EXPRESSION typei = Str2Num.str2NumNumberExpression(type);
		if (typei != ENUM_NUMBER_EXPRESSION.TIMEGAMESCORE) {
			numberExpression.setType(type);
			base.setNumberExpression(numberExpression);
			analysis.setBase(base);
			token.getAnalysis().add(analysis);
		}

		else {
			type = "time";
			numberExpression.setType(type);
			base.setNumberExpression(numberExpression);
			analysis.setBase(base);
			token.getAnalysis().add(analysis);

			type = "gameScore";
			analysisCounter++;
			analysis = objFactory.createAnalysisType();
			analysis.setId(String.valueOf(analysisCounter));
			base = null;
			base = objFactory.createBaseType();
			numberExpression = null;
			numberExpression = objFactory.createNumberExpressionType();
			numberExpression.setType(type);
			base.setNumberExpression(numberExpression);
			analysis.setBase(base);
			token.getAnalysis().add(analysis);
		}

	}

	/**
	 * This method creates and populate the numeral analysis
	 *
	 * @param description
	 *            - relevant in case of prefix existence
	 * @param suffixFunctioni
	 * @param hAttributei
	 * @param constructi
	 * @param baseGender
	 * @param baseNumber
	 * @param construct
	 *            - absolute/construct/unspecified
	 * @param value
	 *            - the value of the literal number of example yer -> 10
	 * @param transliterated
	 * @param lexiconItem
	 * @param lexiconPointer
	 * @param definiteness
	 * @param baseNumeralType
	 * @param hebWord
	 * @param register
	 * @param spelling
	 * @param dottedLexiconItem
	 * @param PGN
	 *            - person/gender/number of suffix
	 * @throws Exception
	 */
	public void createNumeralAnalysis(final String description, ENUM_SUFFIX_FUNCTION suffixFunctioni,
			ENUM_HATTRIBUTE hAttributei, ENUM_STATUS constructi, final String baseGender, final String baseNumber,
			final String construct, final String value, final String transliterated, final String lexiconItem,
			final String lexiconPointer, final String definiteness, final String baseNumeralType, final String hebWord,
			final String register, final String spelling, final String dottedLexiconItem, final String PGN)
					throws Exception {
		int prefixesCounter = -1;
		analysisCounter++;

		analysis = objFactory.createAnalysisType();

		analysis.setId(String.valueOf(analysisCounter));
		BaseType base = null;

		base = objFactory.createBaseType();

		NumeralType numeral = null;

		numeral = objFactory.createNumeralType();

		if (description.length() > 0) {
			// definiteArticle
			if (description.charAt(0) != 'd') {
				prefixesCounter = setPrefix(description);
			} else {
				// for definiteArticle there is only a single prefix=h
				prefixesCounter = 0;
			}

		}
		final ENUM_NUMERAL_TYPE baseNumeralTypei = Str2Num.str2NumNumaralType(baseNumeralType, lexiconItem, hebWord);
		if (baseNumeralTypei != ENUM_NUMERAL_TYPE.LITERL_NUMBER && baseNumeralTypei != ENUM_NUMERAL_TYPE.GEMATRIA) {
			setBase(base, transliterated, lexiconItem, lexiconPointer, dottedLexiconItem);
			if (constructi != ENUM_STATUS.CONSTRUCT_TRUE) {
				numeral.setDefiniteness(definiteness);
			}
			numeral.setGender(baseGender);
			numeral.setNumber(baseNumber);
			numeral.setType(baseNumeralType);
			numeral.setStatus(construct);
			numeral.setRegister(register);
			numeral.setSpelling(spelling);
			if (value.length() > 0) {
				numeral.setValue(value);
			}
			base.setNumeral(numeral);
			analysis.setBase(base);

			if (suffixFunctioni == ENUM_SUFFIX_FUNCTION.SUFFIX_FUNCTION_POSSESSIVE) {
				SuffixType suffix = null;
				suffix = setSuffix(PGN, POSSESSIVE);
				analysis.setSuffix(suffix);
			}
			// ///////////////////////////////////////////
			// gimatria and literal numbers handling
			// ////////////////////////////////////////
		} else {
			if (hAttributei == ENUM_HATTRIBUTE.BASE_DEFINITNESS_TRUE_TRUE) {

				pref = objFactory.createPrefixType();

				// if prefix ends with h
				prefixesCounter++;

				pref.setId(String.valueOf(prefixesCounter));
				pref.setFunction(DEFINITE_ARTICLE);
				pref.setSurface("ה");
				analysis.getPrefix().add(pref);
			}

			numeral.setType(baseNumeralType);
			numeral.setValue(value);
			base.setNumeral(numeral);
			analysis.setBase(base);
		}
		token.getAnalysis().add(analysis);
	}

	/**
	 * This method creates the paragraph entity of the output XML
	 */
	public void createParagraph() {
		paragraphCounter++;
		try {
			paragraph = objFactory.createParagraphType();
		} catch (final JAXBException e) {
			System.err.println("CreateCorpusXML:createParagraph Exception while creating paragraph");
			e.printStackTrace();
		}
		paragraph.setId(String.valueOf(paragraphCounter));
	}

	/**
	 * This method creates and populates participle analysis
	 *
	 * @param description
	 *            - relevant in case of prefix existence
	 * @param suffixFunctioni
	 * @param constructi
	 * @param hAttributei
	 * @param gender
	 * @param number
	 * @param status
	 *            - absolute/construct/unspecified
	 * @param person
	 * @param transliteratedLexiocnItem
	 * @param lexiconItem
	 * @param lexiconPointer
	 * @param root
	 * @param binyan
	 * @param definiteness
	 * @param hebWord
	 * @param register
	 * @param spelling
	 * @param dottedLexiconItem
	 * @param PGN
	 *            -person/gender/number
	 * @param participleType
	 *            - adjective/noun/verb
	 * @param expansion
	 *            - relevant in case of acronym
	 * @throws JAXBException
	 */
	public void createParticipleAnalysis(final String description, ENUM_SUFFIX_FUNCTION suffixFunctioni,
			final ENUM_STATUS constructi, final ENUM_HATTRIBUTE hAttributei, final String gender, final String number,
			final String status, final String person, final String transliteratedLexiocnItem, final String lexiconItem,
			final String lexiconPointer, final String root, final String binyan, final String definiteness,
			final String hebWord, final String register, final String spelling, final String dottedLexiconItem,
			final String PGN, String participleType, String expansion) throws JAXBException {
		analysisCounter++;

		analysis = objFactory.createAnalysisType();

		analysis.setId(String.valueOf(analysisCounter));
		BaseType base = null;

		base = objFactory.createBaseType();

		if (description.length() > 0) {
			setPrefix(description);
		}
		setBase(base, transliteratedLexiocnItem, lexiconItem, lexiconPointer, dottedLexiconItem);
		ParticipleType participle = null;

		participle = objFactory.createParticipleType();

		participle.setRegister(register);
		participle.setSpelling(spelling);
		participle.setGender(gender);
		participle.setNumber(number);
		participle.setStatus(status);
		participle.setRoot(root);
		participle.setBinyan(binyan);
		participle.setPerson(person);
		// definiteness is not relevant for construct form
		// check if status=absolute ->!status.equals("construct")
		if (constructi != ENUM_STATUS.CONSTRUCT_TRUE) {
			participle.setDefiniteness(definiteness);
			// אם יש תחילית
			// קביעת ערך הסוג
		}

		if (hAttributei == ENUM_HATTRIBUTE.SUBCOORDINATING) {
			participle.setSubcoordinating("true");
			participle.setDefiniteness(null);
		}

		participle.setType(participleType);
		base.setParticiple(participle);
		analysis.setBase(base);

		if (suffixFunctioni == ENUM_SUFFIX_FUNCTION.SUFFIX_FUNCTION_POSSESSIVE) {
			SuffixType suffix = null;
			// definitenss and status are not relevant for the possessive noun
			participle.setDefiniteness(null);
			participle.setStatus(null);
			suffix = setSuffix(PGN, POSSESSIVE);
			analysis.setSuffix(suffix);
		}

		if (expansion.length() > 0) {
			participle.setExpansion(expansion);
			participle.setRoot(null);
			participle.setBinyan(null);
			participle.setPerson(null);
			participle.setGender(null);
			participle.setNumber(null);
			participle.setStatus(null);
			participle.setType("verb");
		}
		token.getAnalysis().add(analysis);

	}

	/**
	 * This method creates and populate passive participle analysis meaning a
	 * participle analysis with mood='passive'
	 *
	 * @param description
	 *            - relevant in case of prefix existence
	 * @param constructi
	 * @param hAttributei
	 * @param gender
	 * @param number
	 * @param status
	 *            - absolute/construct/unspecified
	 * @param person
	 * @param transliteratedLexiocnItem
	 * @param lexiconItem
	 * @param lexiconPointer
	 * @param root
	 * @param binyan
	 * @param definiteness
	 * @param hebWord
	 * @param register
	 * @param spelling
	 * @param dottedLexiconItem
	 * @param participleType
	 *            - adjective/noun/verb
	 */
	public void createPassiveParticipleAnalysis(final String description, final ENUM_STATUS constructi,
			final ENUM_HATTRIBUTE hAttributei, final String gender, final String number, final String status,
			final String person, final String transliteratedLexiocnItem, final String lexiconItem,
			final String lexiconPointer, final String root, final String binyan, final String definiteness,
			final String hebWord, final String register, final String spelling, final String dottedLexiconItem,
			String participleType) {
		try {
			analysisCounter++;
			analysis = objFactory.createAnalysisType();
			analysis.setId(String.valueOf(analysisCounter));
			BaseType base = null;
			base = objFactory.createBaseType();
			// !description.equals("")
			if (description.length() > 0) {
				setPrefix(description);
			}
			setBase(base, transliteratedLexiocnItem, lexiconItem, lexiconPointer, dottedLexiconItem);
			ParticipleType participle = null;
			participle = objFactory.createParticipleType();
			participle.setRegister(register);
			participle.setSpelling(spelling);
			participle.setGender(gender);
			participle.setNumber(number);
			participle.setStatus(status);
			participle.setRoot(root);
			participle.setBinyan(binyan);
			participle.setPerson(person);
			if (constructi != ENUM_STATUS.CONSTRUCT_TRUE) {
				participle.setDefiniteness(definiteness);
			}

			if (hAttributei == ENUM_HATTRIBUTE.SUBCOORDINATING) {
				participle.setSubcoordinating("true");
				participle.setDefiniteness(null);
			}

			participle.setType(participleType);
			participle.setMood("passive");
			base.setParticiple(participle);
			analysis.setBase(base);
			token.getAnalysis().add(analysis);
		} catch (JAXBException e) {
			e.printStackTrace();
		}
	}

	/**
	 * This method creates and populate prefix analysis when there is no base
	 * For example: h-10 (h will be analysed as a stand alone prefix without
	 * base and any part of speech),<br>
	 * another example wb"ildim (wb will be analyszed as a stand alone prefix
	 * without base and any part of speech)
	 *
	 * @param description
	 *            - relevant in caes of prefix existence
	 * @param hAttributei
	 */
	public void createPrefixesAnalysis(final String description, ENUM_HATTRIBUTE hAttributei) {
		try {
			analysisCounter++;
			analysis = objFactory.createAnalysisType();
			analysis.setId(String.valueOf(analysisCounter));

			if (description.equals("definiteArticle")) {
				pref = objFactory.createPrefixType();
				pref.setId("1");
				pref.setFunction("definiteArticle");
				pref.setSurface("ה");
				analysis.getPrefix().add(pref);
			} else {
				List<PrefixRec> list = Translate.analyzeMixedHebEng(description);
				final int size = list.size();
				PrefixRec prefixRec = new PrefixRec();
				for (int i = 0; i < size; i++) {
					prefixRec = list.get(i);
					pref = objFactory.createPrefixType();
					pref.setId(String.valueOf(i + 1));
					pref.setFunction(prefixRec.getFunction());
					String prefixSurface = prefixRec.getSurface();
					// for the case of definite article
					if (prefixSurface.length() == 0) {
						prefixSurface = "unspecified";
					}
					pref.setSurface(prefixSurface);
					analysis.getPrefix().add(pref);
				}
				// h handling- the h is always at the end of the prefix
				if (hAttributei == ENUM_HATTRIBUTE.PREFIX_STANDALONE_H) {
					pref = objFactory.createPrefixType();
					pref.setId(String.valueOf(size + 1));
					pref.setFunction("definiteArticle");
					pref.setSurface("ה");
					analysis.getPrefix().add(pref);
				}
			}
			token.getAnalysis().add(analysis);
		} catch (JAXBException e) {
			e.printStackTrace();
		}
	}

	/**
	 * This method creates and populate preposition analysis
	 *
	 * @param description
	 *            - relevant in case of prefix analysis
	 * @param expansion
	 *            - relevant in case of acronym
	 * @param suffixFunctioni
	 * @param transliteratedLexiocnItem
	 * @param lexiconItem
	 * @param lexiconPointer
	 * @param hebWord
	 * @param register
	 * @param spelling
	 * @param dottedLexiconItem
	 * @param PGN
	 * @throws JAXBException
	 */
	public void createPrepositionAnalysis(final String description, final String expansion,
			ENUM_SUFFIX_FUNCTION suffixFunctioni, final String transliteratedLexiocnItem, final String lexiconItem,
			final String lexiconPointer, final String hebWord, final String register, final String spelling,
			final String dottedLexiconItem, final String PGN) throws JAXBException {
		analysisCounter++;
		analysis = objFactory.createAnalysisType();
		analysis.setId(String.valueOf(analysisCounter));
		BaseType base = objFactory.createBaseType();

		if (description.length() > 0) {
			setPrefix(description);
		}
		setBase(base, transliteratedLexiocnItem, lexiconItem, lexiconPointer, dottedLexiconItem);
		GenderNumberPersonType preposition = null;

		preposition = objFactory.createGenderNumberPersonType();

		preposition.setRegister(register);
		preposition.setSpelling(spelling);
		if (expansion.length() > 0) {
			preposition.setExpansion(expansion);
		}
		if (suffixFunctioni == ENUM_SUFFIX_FUNCTION.SUFFIX_FUNCTION_PRONOMIAL) {
			SuffixType suffix = null;
			suffix = setSuffix(PGN, PRONOMIAL);
			analysis.setSuffix(suffix);
		}
		base.setPreposition(preposition);
		analysis.setBase(base);
		token.getAnalysis().add(analysis);
	}

	/**
	 * This method creates and populate pronoun analysis
	 *
	 * @param description
	 *            - relevant in case of prefix existence
	 * @param suffixFunctioni
	 * @param hAttributei
	 * @param gender
	 * @param number
	 * @param person
	 * @param type
	 * @param baseDefinitness
	 * @param transliteratedLexiocnItem
	 * @param lexiconItem
	 * @param lexiconPointer
	 * @param hebWord
	 * @param register
	 * @param spelling
	 * @param dottedLexiconItem
	 * @param PGN
	 * @throws Exception
	 */
	public void createPronounAnalysis(final String description, ENUM_SUFFIX_FUNCTION suffixFunctioni,
			ENUM_HATTRIBUTE hAttributei, final String gender, final String number, final String person,
			final String type, final String baseDefinitness, final String transliteratedLexiocnItem,
			final String lexiconItem, final String lexiconPointer, final String hebWord, final String register,
			final String spelling, final String dottedLexiconItem, final String PGN) throws Exception {
		analysisCounter++;

		analysis = objFactory.createAnalysisType();

		analysis.setId(String.valueOf(analysisCounter));
		BaseType base = objFactory.createBaseType();

		if (description.length() > 0) {
			setPrefix(description);
		}
		setBase(base, transliteratedLexiocnItem, lexiconItem, lexiconPointer, dottedLexiconItem);
		PronounType pronoun = null;
		pronoun = objFactory.createPronounType();
		pronoun.setRegister(register);
		pronoun.setSpelling(spelling);
		final ENUM_GENDER genderi = Str2Num.str2NumGender(gender, lexiconItem, hebWord);
		if (gender.length() > 0 && genderi != ENUM_GENDER.UNSPECIFIED) {
			pronoun.setGender(gender);
		}
		final ENUM_NUMBER numberi = Str2Num.str2NumNumber(number, lexiconItem, hebWord);
		if (number.length() > 0 && numberi != ENUM_NUMBER.UNSPECIFIED) {
			pronoun.setNumber(number);
		}
		if (person != null && person.length() > 0 && person.charAt(0) != 'u') {
			pronoun.setPerson(person);
		}
		if (hAttributei != ENUM_HATTRIBUTE.UNSPECIFIED) {
			pronoun.setDefiniteness(baseDefinitness);
		}
		pronoun.setType(type);

		if (suffixFunctioni == ENUM_SUFFIX_FUNCTION.SUFFIX_FUNCTION_PRONOMIAL) {
			SuffixType suffix = null;
			// עבור הטייה יידוע וגוף לא רלבנטי
			pronoun.setDefiniteness(null);
			pronoun.setPerson(null);
			pronoun.setGender(null);
			pronoun.setNumber(null);
			suffix = setSuffix(PGN, PRONOMIAL);
			analysis.setSuffix(suffix);
		}

		base.setPronoun(pronoun);
		analysis.setBase(base);
		token.getAnalysis().add(analysis);
	}

	/**
	 * This method creates and populate propername analysis
	 *
	 * @param description
	 *            - relevant in case of prefix existence
	 * @param gender
	 * @param number
	 * @param type
	 * @param expansion
	 *            - relevant in case of acronym
	 * @param transliteratedLexiocnItem
	 * @param lexiconItem
	 * @param lexiconPointer
	 * @param definiteness
	 * @param hebWord
	 * @param register
	 * @param spelling
	 * @param dottedLexiconItem
	 * @throws Exception
	 */
	public void createProperNameAnalysis(final String description, final String gender, final String number,
			final String type, final String expansion, final String transliteratedLexiocnItem, final String lexiconItem,
			final String lexiconPointer, final String definiteness, final String hebWord, final String register,
			final String spelling, final String dottedLexiconItem) throws Exception {
		analysisCounter++;

		analysis = objFactory.createAnalysisType();

		analysis.setId(String.valueOf(analysisCounter));
		BaseType base = null;

		base = objFactory.createBaseType();

		if (description.length() > 0) {
			setPrefix(description);
		}

		setBase(base, transliteratedLexiocnItem, lexiconItem, lexiconPointer, dottedLexiconItem);
		ProperNameType properName = null;

		properName = objFactory.createProperNameType();

		final ENUM_REGISTER registeri = Str2Num.str2NumRegister(register, lexiconItem, hebWord);
		if (register.length() > 0 && registeri != ENUM_REGISTER.UNSPECIFIED) {
			properName.setRegister(register);
		}
		final ENUM_SPELLING spellingi = Str2Num.str2NumSpelling(spelling, lexiconItem, hebWord);
		if (spelling.length() > 0 && spellingi != ENUM_SPELLING.UNSPECIFIED) {
			properName.setSpelling(spelling);
		}

		final ENUM_GENDER genderi = Str2Num.str2NumGender(gender, lexiconItem, hebWord);
		if (gender.length() > 0 && genderi != ENUM_GENDER.UNSPECIFIED) {
			properName.setGender(gender);
		}
		final ENUM_NUMBER numberi = Str2Num.str2NumNumber(number, lexiconItem, hebWord);
		if (number.length() > 0 && numberi != ENUM_NUMBER.UNSPECIFIED) {
			properName.setNumber(number);
		}
		if (expansion.length() > 0) {
			properName.setExpansion(expansion);
		}
		final ENUM_PROPERNAME_TYPE typei = Str2Num.str2NumBaseNamedEntityType(type, lexiconItem, hebWord);
		if (type.length() > 0 && typei != ENUM_PROPERNAME_TYPE.UNSPECIFIED) {
			properName.setType(type);
		}
		if (!definiteness.equals("unspecified")) {
			properName.setDefiniteness(definiteness);
		}
		base.setProperName(properName);
		analysis.setBase(base);
		token.getAnalysis().add(analysis);
	}

	/**
	 * This method creates a punctuation analysis for punctuation
	 *
	 * @param hebWord
	 * @throws JAXBException
	 */
	public void createPunctuationAnalysis(final String hebWord) throws JAXBException {
		analysisCounter++;
		analysis = objFactory.createAnalysisType();
		analysis.setId(String.valueOf(analysisCounter));
		BaseType base = null;
		base = objFactory.createBaseType();
		PunctuationType punctuation = null;
		punctuation = objFactory.createPunctuationType();
		base.setPunctuation(punctuation);
		analysis.setBase(base);
		token.getAnalysis().add(analysis);
	}

	/**
	 * all quantifiers are construct - so hAttribute is not relevant This method
	 * creates and populate quantifier analysis Their attributes values are
	 * defined according to their type (amount,partitive,determiner)
	 *
	 * @param description
	 *            - relevant in case of prefix existence
	 * @param suffixFunctioni
	 *            - the int value of suffix function
	 * @param transliteratedLexiocnItem
	 *            - transliterated form of the lexicon item
	 * @param lexiconItem
	 *            - hebrew form of the lexicon item
	 * @param lexiconPointer
	 *            - the lexicon id of the lexicon item
	 * @param hebWord
	 *            - the hebrew form of the token base
	 * @param gender
	 * @param status
	 *            - absolute/construct/unspecified
	 * @param definiteness
	 * @param register
	 * @param spelling
	 * @param dottedLexiconItem
	 * @param PGN
	 *            - person/gender/number of suffix
	 * @throws JAXBException
	 */
	public void createQuantifierAnalysis(final String description, ENUM_SUFFIX_FUNCTION suffixFunctioni,
			final String transliteratedLexiocnItem, final String lexiconItem, final String lexiconPointer,
			final String hebWord, final String register, final String spelling, final String dottedLexiconItem,
			final String PGN, final String status, final String hAttribute, final String type) throws JAXBException {
		analysisCounter++;

		analysis = objFactory.createAnalysisType();

		analysis.setId(String.valueOf(analysisCounter));
		BaseType base = null;

		base = objFactory.createBaseType();

		QuantifierType quantifier = null;

		quantifier = objFactory.createQuantifierType();

		if (description.length() > 0) {
			setPrefix(description);
		}
		setBase(base, transliteratedLexiocnItem, lexiconItem, lexiconPointer, dottedLexiconItem);
		final char statusChar = status.charAt(0);
		// unspecified
		if (statusChar != 'u') {
			quantifier.setStatus(status);
		}
		quantifier.setRegister(register);
		quantifier.setSpelling(spelling);
		quantifier.setType(type);
		// construct
		if (statusChar != 'c') {
			quantifier.setDefiniteness(hAttribute);
		}
		if (suffixFunctioni == ENUM_SUFFIX_FUNCTION.SUFFIX_FUNCTION_PRONOMIAL) {
			SuffixType suffix = null;
			suffix = setSuffix(PGN, PRONOMIAL);
			analysis.setSuffix(suffix);
			quantifier.setStatus(null);
		}
		base.setQuantifier(quantifier);
		analysis.setBase(base);
		token.getAnalysis().add(analysis);
	}

	/**
	 * This method creates the sentence entity of the output XML We define
	 * sentence as several tokens ending with . or ? or !
	 */
	public void createSentence() {
		sentenceCounter++;
		try {
			sentence = objFactory.createSentenceType();
		} catch (final JAXBException e) {
			System.err.println("CreateCorpusXML:createSentence Exception while creating Sentence");
			e.printStackTrace();
		}
		sentence.setId(String.valueOf(sentenceCounter));
	}

	/**
	 * This method creates and populate the title analysis
	 *
	 * @param description
	 *            - relevant in case of prefix existence
	 * @param gender
	 * @param number
	 * @param definiteness
	 * @param expansion
	 *            - relevant in case of acronym
	 * @param transliteratedLexiocnItem
	 * @param lexiconItem
	 * @param lexiconPointer
	 * @param hebWord
	 * @param register
	 * @param spelling
	 * @param dottedLexiconItem
	 * @throws Exception
	 */
	public void createTitleAnalysis(final String description, final String gender, final String number,
			final String definiteness, final String expansion, final String transliteratedLexiocnItem,
			final String lexiconItem, final String lexiconPointer, final String hebWord, final String register,
			final String spelling, final String dottedLexiconItem) throws Exception {
		analysisCounter++;
		analysis = objFactory.createAnalysisType();
		analysis.setId(String.valueOf(analysisCounter));
		BaseType base = null;
		base = objFactory.createBaseType();
		TitleType title = null;
		title = objFactory.createTitleType();
		title.setRegister(register);
		title.setSpelling(spelling);
		if (description.length() > 0) {
			setPrefix(description);
		}
		setBase(base, transliteratedLexiocnItem, lexiconItem, lexiconPointer, dottedLexiconItem);
		final ENUM_GENDER genderi = Str2Num.str2NumGender(gender, lexiconItem, hebWord);
		if (gender.length() > 0 && genderi != ENUM_GENDER.UNSPECIFIED) {
			title.setGender(gender);
		}
		final ENUM_NUMBER numberi = Str2Num.str2NumNumber(number, lexiconItem, hebWord);
		if (number.length() > 0 && numberi != ENUM_NUMBER.UNSPECIFIED) {
			title.setNumber(number);
		}
		title.setDefiniteness(definiteness);
		if (expansion.length() > 0) {
			title.setExpansion(expansion);
		}
		base.setTitle(title);
		analysis.setBase(base);
		token.getAnalysis().add(analysis);
	}

	/**
	 * This method creates the token entity of the output XML
	 *
	 * @param surface
	 *            - the token surface in Hebrew form for populating the surface
	 *            attribute
	 */
	public void createToken(String surface) {
		try {
			tokenCounter++;
			token = objFactory.createTokenType();
			token.setSurface(surface);
			token.setId(String.valueOf(tokenCounter));
		} catch (JAXBException e) {
			e.printStackTrace();
		}
	}

	/**
	 * This method creates and populate the unknown analysis - in case no
	 * analysis was found in the inflections table<br>
	 * or in the inflections data file, and it is not a lettr or a number or
	 * date <br>
	 *
	 * @param hebWord
	 * @param transliterated
	 * @throws JAXBException
	 */
	public void createUnknownAnalysis(final String hebWord, final String transliterated) throws JAXBException {
		analysisCounter++;
		analysis = objFactory.createAnalysisType();
		analysis.setId(String.valueOf(analysisCounter));
		BaseType base = objFactory.createBaseType();
		UnknownType unknown = objFactory.createUnknownType();
		setBase(base, transliterated, hebWord, "0", "");
		base.setUnknown(unknown);
		analysis.setBase(base);
		token.getAnalysis().add(analysis);
	}

	/**
	 * This method creates and popualte URL analysis (url link or mail adress)
	 *
	 * @throws JAXBException
	 */
	public void createURLAnalysis() throws JAXBException {
		analysisCounter++;

		analysis = objFactory.createAnalysisType();

		analysis.setId(String.valueOf(analysisCounter));
		BaseType base = null;

		base = objFactory.createBaseType();

		URLType url = null;

		url = objFactory.createURLType();

		base.setUrl(url);
		analysis.setBase(base);
		token.getAnalysis().add(analysis);
	}

	/**
	 * This method creates and populate verb analysis
	 *
	 * @param description
	 *            - relevant in case of prefix analysis
	 * @param suffixFunctioni
	 * @param binyan
	 * @param gender
	 * @param number
	 * @param person
	 * @param root
	 * @param tense
	 * @param transliteratedLexiocnItem
	 * @param lexiconItem
	 * @param lexiconPointer
	 * @param hebWord
	 * @param register
	 * @param spelling
	 * @param dottedLexiconItem
	 * @param PGN
	 *            - person/gender/number in suffix
	 * @param expansion
	 *            - relevant in case of acronym
	 * @throws Exception
	 */
	public void createVerbAnalysis(final String description, ENUM_SUFFIX_FUNCTION suffixFunctioni, final String binyan,
			final String gender, final String number, final String person, final String root, final String tense,
			final String transliteratedLexiocnItem, final String lexiconItem, final String lexiconPointer,
			final String hebWord, final String register, final String spelling, final String dottedLexiconItem,
			final String PGN, final String expansion) throws Exception {
		analysisCounter++;

		analysis = objFactory.createAnalysisType();

		analysis.setId(String.valueOf(analysisCounter));
		BaseType base = null;

		base = objFactory.createBaseType();

		if (description.length() > 0) {
			setPrefix(description);
		}
		setBase(base, transliteratedLexiocnItem, lexiconItem, lexiconPointer, dottedLexiconItem);
		VerbType verb = null;
		try {
			verb = objFactory.createVerbType();
		} catch (final JAXBException e2) {
			System.err.println("CreateCorpusXML:createVerbAnalysis Exception while createVerbType analysis for hebWord="
					+ hebWord);
			e2.printStackTrace();
		}
		verb.setRegister(register);
		verb.setSpelling(spelling);
		verb.setBinyan(binyan);
		final ENUM_TENSE tensei = Str2Num.str2NumTense(tense, lexiconItem, hebWord);
		if (tense.length() > 0 && tensei != ENUM_TENSE.INFINITIVE) {
			verb.setGender(gender);
			verb.setNumber(number);
			verb.setPerson(person);
		}
		verb.setRoot(root);
		verb.setTense(tense);
		if (expansion.length() > 0) {
			verb.setExpansion(expansion);
			verb.setRoot(null);
			verb.setBinyan(null);
			verb.setPerson(null);
			verb.setTense(null);
			verb.setGender(null);
			verb.setNumber(null);

		}

		base.setVerb(verb);

		if (suffixFunctioni == ENUM_SUFFIX_FUNCTION.SUFFIX_FUNCTION_ACCUSATIVE_OR_NOMINATIVE) {
			SuffixType suffix = null;
			suffix = setSuffix(PGN, ACCUSATIVE_OR_NOMINATIVE);
			analysis.setSuffix(suffix);
		}

		analysis.setBase(base);

		token.getAnalysis().add(analysis);
	}

	/**
	 * This method creates and populate Wprefix analysis
	 *
	 * @param gender
	 * @param number
	 * @param description
	 *            - relevant in
	 * @param transliteratedLexiocnItem
	 * @param lexiconItem
	 * @param lexiconPointer
	 * @param hebWord
	 * @param register
	 * @param spelling
	 * @param dottedLexiconItem
	 * @param definiteness
	 * @param polarity
	 * @throws Exception
	 */
	public void createWprefixAnalysis(final String gender, final String number, final String description,
			final String transliteratedLexiocnItem, final String lexiconItem, final String lexiconPointer,
			final String hebWord, final String register, final String spelling, final String dottedLexiconItem,
			final String definiteness, final String polarity) throws Exception {
		analysisCounter++;

		analysis = objFactory.createAnalysisType();

		analysis.setId(String.valueOf(analysisCounter));
		BaseType base = null;

		base = objFactory.createBaseType();

		if (description.length() > 0) {
			setPrefix(description);
		}
		setBase(base, transliteratedLexiocnItem, lexiconItem, lexiconPointer, dottedLexiconItem);
		WprefixType wprefix = null;
		try {
			wprefix = objFactory.createWprefixType();
		} catch (final JAXBException e2) {
			System.err.println(
					"CreateCorpusXML:createNegationAnalysis Exception while createNegationType analysis for hebWord="
							+ hebWord);
			e2.printStackTrace();
		}
		final ENUM_GENDER genderi = Str2Num.str2NumGender(gender, lexiconItem, hebWord);
		if (gender.length() > 0 && genderi != ENUM_GENDER.UNSPECIFIED) {
			wprefix.setGender(gender);
		}
		final ENUM_NUMBER numberi = Str2Num.str2NumNumber(number, lexiconItem, hebWord);
		if (number.length() > 0 && numberi != ENUM_NUMBER.UNSPECIFIED) {
			wprefix.setNumber(number);
		}
		wprefix.setRegister(register);
		wprefix.setSpelling(spelling);
		wprefix.setDefiniteness(definiteness);
		// unspecified
		if (polarity.charAt(0) != 'u') {
			wprefix.setPolarity(polarity);
		}
		base.setWPrefix(wprefix);
		analysis.setBase(base);
		token.getAnalysis().add(analysis);
	}

	/**
	 * This method creates the header of the XML document
	 */
	public void createXMLDoc() {
		try {
			objFactory = new ObjectFactory();
			corpus = objFactory.createCorpus();
			corpus.setName("Analysis Results");
			corpus.setMaintainer("Yamit Barshatz");
			corpus.setEmail("mila@cs.technion.ac.il");
			corpus.setComment("versions info: lexicon: 13/03/2013;  morphologicalAnalyzer: 1.8 (13/03/2013); "
					+ "corpus schema 16/06/2009; lexicon schema 16/06/2009");
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * This method is called at the end of the XML output of the tokenizer
	 */
	public void finalizeEOF() {
		// in case of empty input file we create an XML file with a single
		// article, parapraph and sentence
		if (tokenCounter != 0) {
			paragraph.getSentence().add(sentence);
			article.getParagraph().add(paragraph);
		}
	}

	/**
	 * This method is called at the end of processing paragraph entity in the
	 * XML output of the tokenizer
	 */
	public void finalizeParagraph() {
		article.getParagraph().add(paragraph);
		sentenceCounter = 0;
	}

	/**
	 * This method is called at the end of processing sentence entity in the XML
	 * output of the tokenizer
	 */
	public void finalizeSentence() {
		paragraph.getSentence().add(sentence);
		tokenCounter = 0;
		analysisCounter = 0;
	}

	/**
	 * This method is called at the end of processing of each token entity in
	 * the XML output of the tokenizer
	 */
	public void finalizeToken() {
		if (tokenCounter > 0) {
			sentence.getToken().add(token);
			// for each token we count the number of analysis
			analysisCounter = 0;
		}
	}

	/**
	 * This method generates the XML output (file or System.out) using jaxb
	 */
	public void printDoc() {
		if (outputFile.length() == 0) {
			try {
				pOut = new OutputStreamWriter(System.out, "UTF8");
			} catch (final UnsupportedEncodingException e) {
				System.err.println(
						"CreateCorpusXML:printDoc UnsupportedEncodingException while create OutputStreamWriter");
				e.printStackTrace();
			}
		} else {
			FileOutputStream out = null;
			try {
				out = new FileOutputStream(outputFile);
			} catch (final FileNotFoundException e1) {
				System.err.println("CreateCorpusXML:printDoc FileNotFoundException while create OutputStreamWriter");
				e1.printStackTrace();
			}
			try {
				pOut = new OutputStreamWriter(out, "UTF8");
			} catch (final UnsupportedEncodingException e2) {
				System.err.println(
						"CreateCorpusXML:printDoc UnsupportedEncodingException while create OutputStreamWriter");
				e2.printStackTrace();
			}
		}
		Marshaller m = null;
		try {
			m = jc.createMarshaller();
		} catch (final JAXBException e1) {
			System.err.println("CreateCorpusXML:printDoc JAXBException while create Marshaller");
			e1.printStackTrace();
		}
		try {
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.FALSE);
		} catch (final PropertyException e2) {
			System.err.println("CreateCorpusXML:printDoc PropertyException while create Marshaller");
			e2.printStackTrace();
		}
		try {
			m.marshal(corpus, pOut);
		} catch (final JAXBException e3) {
			System.err.println("CreateCorpusXML:printDoc JAXBException while create Marshaller");
			e3.printStackTrace();
		}
		try {
			// if (!validator.validate(corpus)) {
			// System.err.println("Corpus Not valid !!!");
			// }
			pOut.close();
		} catch (final IOException e4) {
			System.err.println("CreateCorpusXML:printDoc IOException while closeing pOut");
			e4.printStackTrace();
		}
	}

	/**
	 * This method generates the XML output (outputStream) using jaxb
	 *
	 * @param outputStream
	 */
	public void printDoc(OutputStream outputStream) {
		try {
			pOut = new OutputStreamWriter(outputStream, "UTF-8");
			Marshaller m = jc.createMarshaller();
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.FALSE);
			m.marshal(corpus, pOut);
		} catch (final JAXBException e3) {
			System.err.println("CreateCorpusXML:printDoc JAXBException while create Marshaller");
			e3.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * This method generates the XML output (PrintWriter) using jaxb
	 *
	 * @param pw
	 */
	public void printDoc(PrintWriter pw) {
		// System.out.println("(F) printDoc() ");

		Marshaller m = null;

		try {
			m = jc.createMarshaller();
		} catch (final JAXBException e1) {
			System.err.println("CreateCorpusXML:printDoc JAXBException while create Marshaller");
			e1.printStackTrace();
		}
		try {
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.FALSE);
		} catch (final PropertyException e2) {
			System.err.println("CreateCorpusXML:printDoc PropertyException while create Marshaller");
			e2.printStackTrace();
		}
		try {
			m.marshal(corpus, pw);
		} catch (final JAXBException e3) {
			System.err.println("CreateCorpusXML:printDoc JAXBException while create Marshaller");
			e3.printStackTrace();
		}
	}

	/**
	 *
	 * This method creates and populates the base entity part of the XML output
	 *
	 * @param base
	 * @param transliteratedLexiocnItem
	 *            - the transliterated form of the lexicon item
	 * @param lexiconItem
	 *            - the hebrew form of the lexicon item
	 * @param lexiconPointer
	 *            - the lexicon id
	 * @param dottedLexiconItem
	 *            - the dotted form of the lexicon form
	 */
	private void setBase(BaseType base, String transliteratedLexiocnItem, String lexiconItem, String lexiconPointer,
			String dottedLexiconItem) {
		base.setTransliteratedLexiconItem(transliteratedLexiocnItem);
		base.setLexiconItem(lexiconItem);
		base.setLexiconPointer(lexiconPointer);
		if (dottedLexiconItem != null && dottedLexiconItem.length() > 0) {
			base.setDottedLexiconItem(dottedLexiconItem);
		} else {
			base.setDottedLexiconItem(null);
		}
	}

	/**
	 * This method creates and populate the prefix entity part of the XML output
	 *
	 * @param description
	 *            - the description of the prefix - comes from prefixes table
	 * @return the number of prefixs for the current prefix for example wlbit
	 *         (and to the home) -> w+l -> 2
	 */
	protected int setPrefix(String description) {
		int prefixesCounter = 0;
		try {
			String prefixSurface = "";
			List<PrefixRec> list = Translate.analyzeMixedHebEng(description);
			final int size = list.size();
			PrefixRec prefixRec = new PrefixRec();
			// each prefix contained in the prefix part of the tokenizer apeares
			// in
			// separate - we break it
			for (prefixesCounter = 0; prefixesCounter < size; prefixesCounter++) {
				prefixRec = list.get(prefixesCounter);
				pref = objFactory.createPrefixType();
				pref.setId(String.valueOf(prefixesCounter + 1));
				pref.setFunction(prefixRec.getFunction());
				prefixSurface = prefixRec.getSurface();
				// for the case of definite article
				// if (prefixSurface.equals(""))
				// prefixSurface = "unspecified";
				pref.setSurface(prefixSurface);
				analysis.getPrefix().add(pref);
			}
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		return prefixesCounter;
	}

	/**
	 * This method creates and populates the suffix part of the XML output. It
	 * is used in case of an analysis<br>
	 * containing a possessive element or accusative or nominative element or a
	 * pronomial element <br>
	 *
	 * @param PGN
	 *            - person/gender/number
	 * @param function
	 *            - the functionality- possessive/nominative or
	 *            accusative/pronomial
	 * @return suffixType entity
	 */
	private SuffixType setSuffix(String PGN, String function) {
		SuffixType suffix = null;
		try {
			suffix = objFactory.createSuffixType();
			suffix.setFunction(function);
			suffix.setPerson(StringUtils.getPersonPGN(PGN));
			suffix.setGender(StringUtils.getGenderPGN(PGN));
			suffix.setNumber(StringUtils.getNumberPGN(PGN));
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		return suffix;
	}

	private static JAXBContext acquireJAXBContext() {
		try {
			return JAXBContext.newInstance(JAXB_PACKAGE);
		} catch (JAXBException e) {
			e.printStackTrace();
			throw new MilaException(e);
		}
	}
}
