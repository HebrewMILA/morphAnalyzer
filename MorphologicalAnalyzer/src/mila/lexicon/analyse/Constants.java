/*
 * Created on 05/02/2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package mila.lexicon.analyse;

/**
 * @author daliabo
 * 
 *         TODO To change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public interface Constants {

	public enum ENUM_BINYAN {
		PAAL, NIFAL, PIEL, PUAL, HIFIL, HUFAL, HITPAEL, UNSPECIFIED
	}

	public enum ENUM_GENDER {
		MASCULINE, FEMININE, MASCULINE_AND_FEMININE, UNSPECIFIED
	}

	public enum ENUM_HATTRIBUTE {
		BASE_DEFINITNESS_TRUE_TRUE, BASE_DEFINITNESS_TRUE_FALSE, BASE_DEFINITNESS_FALSE, BASE_DEFINITNESS_REQUIRED_FALSE, BASE_DEFINITNESS_REQUIRED_TRUE, SUBCOORDINATING, PREFIX_STANDALONE_H, UNSPECIFIED
	}

	public enum ENUM_HMMPOS {
		QUANTIFIER, INTERJECTION, INTERROGATIVE, CONJUNCTION, ADVERB, PREPOSITION, NOUN, ADJECTIVE, PRONOUN, PROPERNAME, VERB, NEGATION, PARTICIPLE, PUNCTUATION, URL, FOREIGN, NUMERAL, EXISTENTIAL, IMPERSONAL, MODAL, WPREFIX, PREFIXES, PASSIVEPARTICIPLE, INDEPENDENTINFINITIVE, COPULA, TITLE, MULTIWORD, UNKNOWN, NUMBER_EXPRESSION, ZEVEL, NOUNPOSSESSIVE, NOUNDEF, NOUNCONST, PARTICIPLEDEF, PARTICIPLECONST, ADJECTIVECONST, ADJECTIVEDEF, PROPERNAMEDEF, VERBINF, PREFIX, UNSPECIFIED
	}

	public enum ENUM_NUMBER {
		SINGULAR, SINGULAR_AND_PLURAL, PLURAL, DUAL, DUAL_AND_PLURAL, UNSPECIFIED
	}

	public enum ENUM_NUMBER_EXPRESSION {
		TIME, DATE, TIMEGAMESCORE, GEMESCORE, NUMBERING

	}

	public enum ENUM_NUMERAL_TYPE {
		ORDINAL, CARDINAL, FRACTIONAL, LITERL_NUMBER, GEMATRIA, UNSPECIFIED
	}

	public enum ENUM_OUTPUT_PATTERN {
		QUANTIFIER, INTERJECTION, INTERROGATIVE, CONJUNCTION, ADVERB, PREPOSITION, NOUN, ADJECTIVE, PRONOUN, PROPERNAME, VERB, NEGATION, PARTICIPLE, PUNCTUATION, URL, FOREIGN, LITERAL_NUMBERS, GEMATRIA, NUMERAL, EXISTENTIAL, IMPERSONAL, MODAL, WPREFIX, PREFIXES, PASSIVEPARTICIPLE, INDEPENDENTINFINITIVE, COPULA, TIME, DATE, GAMESCORE, TIMEGAMESCORE, NUMBERING, TITLE, MULTIWORD, UNKNOWN, ZEVEL, HEBREWLETTER, HEBREWDOTLETTER, UNSPECIFIED
	}

	public enum ENUM_PARTICIPLE_TYPE {
		NOUN, ADJECTIVE, VERB, UNSPECIFIED
	}

	public enum ENUM_POS {
		QUANTIFIER, INTERJECTION, INTERROGATIVE, CONJUNCTION, ADVERB, PREPOSITION, NOUN, ADJECTIVE, PRONOUN, PROPERNAME, VERB, NEGATION, PARTICIPLE, NUMERAL, EXISTENTIAL, IMPERSONAL, MODAL, WPREFIX, PREFIXES, PASSIVEPARTICIPLE, INDEPENDENTINFINITIVE, COPULA, TITLE, MULTIWORD, PUNCTUATION, URL, FOREIGN, UNKNOWN, NUMBER_EXPRESSION, ZEVEL, UNSPECIFIED
	}

	public enum ENUM_PREFIX_FUNCTION {
		DEFINITEARTICLE
	}

	public enum ENUM_PROPERNAME_TYPE {
		LOCATION, PERSON, ORGANIZATION, DATETIME, OTHER, PRODUCT, COUNTRY, SYMBOL, LANGUAGE, TOWN, ART, UNSPECIFIED
	}

	public enum ENUM_REGISTER {
		FORMAL, ARCHAIC, INFORMAL, UNSPECIFIED
	}

	public enum ENUM_SPELLING {
		STANDARD, IRREGULAR, UNSPECIFIED
	}

	public enum ENUM_STATUS {
		CONSTRUCT_TRUE, CONSTRUCT_FALSE, UNSPECIFIED
	}

	public enum ENUM_SUFFIX_FUNCTION {
		SUFFIX_FUNCTION_POSSESSIVE, SUFFIX_FUNCTION_ACCUSATIVE_OR_NOMINATIVE, SUFFIX_FUNCTION_PRONOMIAL, SUFFIX_FUNCTION_UNSPECIFIED
	}

	public enum ENUM_TENSE {
		IMPERATIVE, PAST, BEINONI, FUTURE, INFINITIVE, BAREINFINITIVE, PRESENT, UNSPECIFIED
	}

	public static final int BASE_QUANTIFIER_TYPE_NON_NUMERAL = 2;

	public static final int BASE_PRONOUN_TYPE_RELATIVIZER = 5;

	public static final int BASE_PRONOUN_TYPE_DEMONSTRATIVE = 3;

	public static final int BASE_PRONOUN_TYPE_PERSONAL = 2;

	public static final int BASE_PRONOUN_TYPE_INTERROGATIVE = 1;

	public static final int OUTPUT_TXT = 1;

	public static final int OUTPUT_XML = 2;

	public static final int SCRIPT_COLLOQUIAL = 2;

	public static final int SCRIPT_FORMAL = 1;

	final static int PREFIX_ARRAY_SIZE = 112;

	final static String TRUE = "true";
	final static String FALSE = "false";
	final static String PRONOMIAL = "pronomial";
	final static String ACCUSATIVE_OR_NOMINATIVE = "accusative or nominative";
	final static String POSSESSIVE = "possessive";
	final static String UNSPECIFED = "unspecified";
	final static String DEFINITE_ARTICLE = "definiteArticle";
	final static String NOUN = "noun";

	final static int PARTICIPLE_TYPE_ADJECTIVE = 2;
	final static int PARTICIPLE_TYPE_NOUN = 1;
	final static int PARTICIPLE_TYPE_VERB = 3;

	static final int QUANTIFIER_TYPE_AMOUNT = 1;
	static final int QUANTIFIER_TYPE_PARTITIVE = 2;
	static final int QUANTIFIER_TYPE_DETERMINER = 3;

	static final int INTERROGATIVE_TYPE_PRONOUN = 1;

	final static String JAXB_PACKAGE = "mila.generated";

	final static String ALEFBAIT = "[�����������������������]";

	// //////////////////////////////////////////////////////////////////////////////

	static final String[] prefixArray = { "b", "bk", "e", "eb", "ebk", "eh", "ek", "eke", "ekeb", "ekeh", "ekek", "ekel",
			"ekem", "el", "elk", "elke", "elkeb", "elkeh", "elkek", "elkel", "elkem", "em", "eme", "emeb", "emeh", "emek",
			"emel", "emem", "emk", "k", "ke", "keb", "keh", "kek", "kel", "kem", "l", "lk", "lke", "lkeb", "lkeh", "lkek",
			"lkel", "lkem", "m", "me", "meb", "meh", "mek", "mel", "mem", "mh", "mk", "mlk", "w", "wh", "wb", "wbk", "we",
			"web", "webk", "weh", "wek", "wek", "weke", "wekeb", "wekeh", "wekek", "wekel", "wekem", "wel", "welk",
			"welke", "welkeb", "welkeh", "welkek", "welkel", "welkem", "wem", "weme", "wemeb", "wemeh", "wemek", "wemel",
			"wemem", "wemk", "wk", "wke", "wkeb", "wkeh", "wkek", "wkel", "wkem", "wl", "wlk", "wlke", "wlkeb", "wlkeh",
			"wlkek", "wlkel", "wlkem", "wm", "wme", "wmeb", "wmeh", "wmek", "wmel", "wmelk", "wmem", "wmh", "wmk", "h" };

}
