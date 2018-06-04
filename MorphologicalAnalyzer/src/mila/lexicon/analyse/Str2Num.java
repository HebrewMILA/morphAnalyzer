/*
 * Created on 09/02/2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package mila.lexicon.analyse;

import static mila.lexicon.analyse.Constants.*;

/**
 * 
 * Str2Num.java Purpose: utility class for translating Strings to numbers - used
 * for attributes that we make equality tests for them - and we want to improve
 * performance
 * 
 * @author Dalia Bojan
 * @version %G%
 */

public class Str2Num {

	/**
	 * This method is used to translate conjunction type strings to codes
	 * 
	 * @param baseConjunctionType
	 * @return
	 * @throws Exception
	 */
	public static String encodeBaseConjunctionType(String baseConjunctionType) throws Exception {
		String strBaseConjunctionType = "";
		if (baseConjunctionType.equals("coordinating")) {
			strBaseConjunctionType = "c";
		} else if (baseConjunctionType.equals("subordinating"))
			strBaseConjunctionType = "s";
		else if (baseConjunctionType.equals("relativizing"))
			strBaseConjunctionType = "r";
		else {
			throw new Exception(
					"Str2Num:encodeBaseConjunctionType illegal value for baseConjunctionType =" + baseConjunctionType);

		}
		return strBaseConjunctionType;
	}

	/**
	 * This method is used to translate pronoun type strings to codes
	 * 
	 * @param basePronounType
	 * @return
	 */
	public static String encodeBasePronounType(String basePronounType) {
		String strBasePronounType = "";
		if (basePronounType.equals("interrogative"))
			strBasePronounType = "1";
		else if (basePronounType.equals("personal"))
			strBasePronounType = "2";
		else if (basePronounType.equals("demonstrative"))
			strBasePronounType = "3";
		else if (basePronounType.equals("impersonal"))
			strBasePronounType = "4";
		else if (basePronounType.equals("relativizer"))
			strBasePronounType = "5";
		else if (basePronounType.equals("reflexive"))
			strBasePronounType = "6";
		else {
			strBasePronounType = "-1";
			// System.out.println("illegal basePronounType: " +
			// basePronounType);
			// System.exit(1);
		}
		return strBasePronounType;
	}

	/**
	 * This method is used for translating Person/Gender/number values to codes
	 * 
	 * @param PGN
	 * @return
	 * @throws Exception
	 */
	public static String encodePGN(String PGN) throws Exception {
		String strPGN = "";
		if (PGN.equals("1p/MF/Sg"))
			strPGN = "1";
		else if (PGN.equals("2p/F/Sg"))
			strPGN = "2";
		else if (PGN.equals("2p/M/Sg"))
			strPGN = "3";
		else if (PGN.equals("3p/M/Sg"))
			strPGN = "4";
		else if (PGN.equals("3p/F/Sg"))
			strPGN = "5";
		else if (PGN.equals("1p/MF/Pl"))
			strPGN = "6";
		else if (PGN.equals("2p/M/Pl"))
			strPGN = "7";
		else if (PGN.equals("2p/F/Pl"))
			strPGN = "8";
		else if (PGN.equals("3p/M/Pl"))
			strPGN = "9";
		else if (PGN.equals("3p/F/Pl"))
			strPGN = "10";
		else if (PGN.equals("123p/M/Sg"))
			strPGN = "11";
		else if (PGN.equals("123p/F/Sg"))
			strPGN = "12";
		else if (PGN.equals("123p/F/Pl"))
			strPGN = "13";
		else if (PGN.equals("123p/M/Pl"))
			strPGN = "14";
		else if (PGN.equals("unspecified"))
			strPGN = "15";
		else if (PGN.equals("NONE"))
			strPGN = "16";
		else {
			throw new Exception("Str2Num:encodePGN illegal value for binyan =" + PGN);

		}
		return strPGN;
	}

	/**
	 * This method is used to translate baseNameEntityType (types of properName) to
	 * codes
	 * 
	 * @param baseNamedEntityType
	 * @param lexiconItem
	 * @param word
	 * @return
	 * @throws Exception
	 */
	public static ENUM_PROPERNAME_TYPE str2NumBaseNamedEntityType(final String baseNamedEntityType,
			final String lexiconItem, final String word) throws Exception {
		ENUM_PROPERNAME_TYPE baseNamedEntityTypei = null;
		if (baseNamedEntityType != null && baseNamedEntityType.length() > 0) {
			char baseNamedEntityTypeStartChar = baseNamedEntityType.charAt(0);
			switch (baseNamedEntityTypeStartChar) {
			case 'l':
				if (baseNamedEntityType.charAt(1) == 'o')
					baseNamedEntityTypei = ENUM_PROPERNAME_TYPE.LOCATION;
				else if (baseNamedEntityType.charAt(1) == 'a')
					baseNamedEntityTypei = ENUM_PROPERNAME_TYPE.LANGUAGE;
				break;
			case 'p':
				if (baseNamedEntityType.charAt(1) == 'e')
					baseNamedEntityTypei = ENUM_PROPERNAME_TYPE.PERSON;
				else if (baseNamedEntityType.charAt(1) == 'r')
					baseNamedEntityTypei = ENUM_PROPERNAME_TYPE.PRODUCT;
				break;
			case 'o':
				if (baseNamedEntityType.charAt(1) == 'r')
					baseNamedEntityTypei = ENUM_PROPERNAME_TYPE.ORGANIZATION;
				else if (baseNamedEntityType.charAt(1) == 't')
					baseNamedEntityTypei = ENUM_PROPERNAME_TYPE.OTHER;
				break;
			case 'd':
				baseNamedEntityTypei = ENUM_PROPERNAME_TYPE.DATETIME;
				break;
			case 'c':
				baseNamedEntityTypei = ENUM_PROPERNAME_TYPE.COUNTRY;
				break;
			case 's':
				baseNamedEntityTypei = ENUM_PROPERNAME_TYPE.SYMBOL;
				break;
			case 't':
				baseNamedEntityTypei = ENUM_PROPERNAME_TYPE.TOWN;
				break;
			case 'a':
				baseNamedEntityTypei = ENUM_PROPERNAME_TYPE.ART;
				break;
			case 'u':
				baseNamedEntityTypei = ENUM_PROPERNAME_TYPE.UNSPECIFIED;
				break;
			default:
				throw new Exception("Str2Num:str2NumBaseNamedEntityType illegal value " + "for baseNamedEntityType ="
						+ baseNamedEntityType + " for lexiconItem: " + lexiconItem + " for input token: " + word);
			}
		}
		return baseNamedEntityTypei;
	}

	/**
	 * This methods is used for translating the binyan strings to codes
	 * 
	 * @param binyan
	 * @param lexiconItem
	 * @param word
	 * @return
	 * @throws Exception
	 */
	public static ENUM_BINYAN str2NumBinyan(final String binyan, final String lexiconItem, final String word)
			throws Exception {
		ENUM_BINYAN binyani = null;
		if (binyan != null && binyan.length() > 0) {
			char binyanStartChar = binyan.charAt(0);
			switch (binyanStartChar) {
			case 'H':
				if (binyan.charAt(1) == 'u')
					binyani = ENUM_BINYAN.HUFAL;
				else if (binyan.charAt(2) == 'f')
					binyani = ENUM_BINYAN.HIFIL;
				else if (binyan.charAt(2) == 't')
					binyani = ENUM_BINYAN.HITPAEL;
				else {
					throw new Exception("Str2Num:str2NumBinyan illegal value for binyan =" + binyan + " for lexiconItem: "
							+ lexiconItem + " for input token: " + word);
				}
				break;
			case 'N':
				binyani = ENUM_BINYAN.NIFAL;
				break;
			case 'P':
				if (binyan.charAt(1) == 'a')
					binyani = ENUM_BINYAN.PAAL;
				else if (binyan.charAt(1) == 'i')
					binyani = ENUM_BINYAN.PIEL;
				else if (binyan.charAt(1) == 'u')
					binyani = ENUM_BINYAN.PUAL;
				else {
					throw new Exception("Str2Num:str2NumBinyan illegal value for binyan =" + binyan + " for lexiconItem: "
							+ lexiconItem + " for input token: " + word);
				}
				break;
			case '-':
				binyani = ENUM_BINYAN.UNSPECIFIED;
				break;
			default:
				throw new Exception("Str2Num:str2NumBinyan illegal value for binyan =" + binyan + " for lexiconItem: "
						+ lexiconItem + " for input token: " + word);
			}
		}
		return binyani;
	}

	/**
	 * This method is used for translating construct string values to codes
	 * 
	 * @param construct
	 * @param lexiconItem
	 * @param word
	 * @return
	 * @throws Exception
	 */
	public static ENUM_STATUS str2NumConstruct(final String construct, final String lexiconItem, final String word)
			throws Exception {
		ENUM_STATUS constructi = null;
		if (construct == null || construct.length() == 0)
			constructi = ENUM_STATUS.UNSPECIFIED;
		else {
			char constructChar = construct.charAt(0);
			switch (constructChar) {
			case 'u':
			case 'n':
				constructi = ENUM_STATUS.UNSPECIFIED;
				break;
			case 'c':
			case 't':
				constructi = ENUM_STATUS.CONSTRUCT_TRUE;
				break;
			case 'a':
			case 'f':
				constructi = ENUM_STATUS.CONSTRUCT_FALSE;
				break;
			default:
				throw new Exception("Str2Num:str2NumConstruct illegal value for construct =" + construct
						+ " for lexiconItem: " + lexiconItem + " for input token: " + word);

			}
		}
		return constructi;
	}

	/**
	 * This method is used to translate gender strings to codes
	 * 
	 * @param gender
	 * @param lexiconItem
	 * @param word
	 * @return
	 * @throws Exception
	 */
	public static ENUM_GENDER str2NumGender(final String gender, final String lexiconItem, final String word)
			throws Exception {
		ENUM_GENDER genderi = null;
		if (gender != null && gender.length() > 0) {
			char genderStartChar = gender.charAt(0);
			// masculine
			switch (genderStartChar) {
			case 'm':
				if (gender.length() == 8)
					genderi = ENUM_GENDER.MASCULINE;
				else
					genderi = ENUM_GENDER.MASCULINE_AND_FEMININE;
				break;
			case 'f':
				genderi = ENUM_GENDER.FEMININE;
				break;
			case 'u':
				genderi = ENUM_GENDER.UNSPECIFIED;
				break;
			default:
				throw new Exception("Str2Num:str2NumGender illegal value for gender =" + gender + " for lexiconItem: "
						+ lexiconItem + " for input token: " + word);
			}
		}
		return genderi;
	}

	/**
	 * This method is used to translate hAttribute string values to codes
	 * 
	 * @param hAttribute
	 * @param lexiconItem
	 * @param word
	 * @return
	 * @throws Exception
	 */
	public static ENUM_HATTRIBUTE str2NumHAttribute(final String hAttribute, final String lexiconItem, final String word)
			throws Exception {
		ENUM_HATTRIBUTE hAttributei = null;
		if (hAttribute != null && hAttribute.length() > 0) {
			char hAttributeStartChar = hAttribute.charAt(0);
			switch (hAttributeStartChar) {
			case 'f':
				hAttributei = ENUM_HATTRIBUTE.BASE_DEFINITNESS_FALSE;
				break;
			case 'r':
				if (hAttribute.charAt(1) == 'f')
					hAttributei = ENUM_HATTRIBUTE.BASE_DEFINITNESS_REQUIRED_FALSE;
				else if (hAttribute.charAt(1) == 't')
					hAttributei = ENUM_HATTRIBUTE.BASE_DEFINITNESS_REQUIRED_TRUE;
				break;
			case 's':
				hAttributei = ENUM_HATTRIBUTE.SUBCOORDINATING;
				break;
			case 't':
				if (hAttribute.charAt(1) == 't')
					hAttributei = ENUM_HATTRIBUTE.BASE_DEFINITNESS_TRUE_TRUE;
				else if (hAttribute.charAt(1) == 'f')
					hAttributei = ENUM_HATTRIBUTE.BASE_DEFINITNESS_TRUE_FALSE;
				break;
			case 'u':
				hAttributei = ENUM_HATTRIBUTE.UNSPECIFIED;
				break;
			default:
				throw new Exception("Str2Num:str2NumHAttribute illegal value for hAttribute =" + hAttribute
						+ " for lexiconItem: " + lexiconItem + " for input token: " + word);
			}
		}
		return hAttributei;
	}

	/**
	 * @param hmmPos
	 * @param lexiconItem
	 * @param word
	 * @return
	 * @throws Exception
	 */
	public static ENUM_HMMPOS str2numHMMPOS(final String hmmPos, final String lexiconItem, final String word) {
		ENUM_HMMPOS posi = ENUM_HMMPOS.UNKNOWN; // edited by yossi on the
		// 13.02.2011
		// ENUM_HMMPOS posi = null;
		int hmmPosLen = hmmPos.length();
		if (hmmPos != null && hmmPosLen > 0) {
			char posStartChar = hmmPos.charAt(0);
			switch (posStartChar) {
			case 'a':
				if (hmmPosLen >= 3 && hmmPos.charAt(2) == 'v')
					posi = ENUM_HMMPOS.ADVERB;
				else if (hmmPosLen == 15 && hmmPos.charAt(14) == 't')
					posi = ENUM_HMMPOS.ADJECTIVECONST;
				else if (hmmPosLen == 13 && hmmPos.charAt(12) == 'f')
					posi = ENUM_HMMPOS.ADJECTIVEDEF;
				else if (hmmPosLen == 9 && hmmPos.charAt(8) == 'e')
					posi = ENUM_HMMPOS.ADJECTIVE;
				// else {
				// throw new Exception(
				// "Str2Num:str2NumStrInterrogativeType illegal value "
				// + "for hmmPos=" + hmmPos
				// + " for lexiconItem: " + lexiconItem
				// + " for input token: " + word);
				//
				// }
				break;
			case 'c':
				if (hmmPosLen >= 3 && hmmPos.charAt(2) == 'n')
					posi = ENUM_HMMPOS.CONJUNCTION;
				else if (hmmPosLen >= 3 && hmmPos.charAt(2) == 'p')
					posi = ENUM_HMMPOS.COPULA;
				// else {
				// throw new Exception(
				// "Str2Num:str2NumStrInterrogativeType illegal value "
				// + "for hmmPos=" + hmmPos
				// + " for lexiconItem: " + lexiconItem
				// + " for input token: " + word);
				//
				// }
				break;
			case 'e':
				posi = ENUM_HMMPOS.EXISTENTIAL;
				break;
			case 'f':
				posi = ENUM_HMMPOS.FOREIGN;
				break;
			case 'i':
				if (hmmPosLen >= 6 && hmmPos.charAt(5) == 'j')
					posi = ENUM_HMMPOS.INTERJECTION;
				else if (hmmPosLen >= 6 && hmmPos.charAt(5) == 'r')
					posi = ENUM_HMMPOS.INTERROGATIVE;
				// Not in use
				// else
				// posi = ENUM_HMMPOS.IMPERSONAL;
				else if (hmmPosLen >= 3 && hmmPos.charAt(2) == 'd')
					posi = ENUM_HMMPOS.INDEPENDENTINFINITIVE;
				// else {
				// throw new Exception(
				// "Str2Num:str2NumStrInterrogativeType illegal value "
				// + "for hmmPos=" + hmmPos
				// + " for lexiconItem: " + lexiconItem
				// + " for input token: " + word);
				//
				// }
			case 'm':
				if (hmmPosLen >= 2 && hmmPos.charAt(1) == 'o')
					posi = ENUM_HMMPOS.MODAL;
				else if (hmmPosLen >= 2 && hmmPos.charAt(1) == 'w')
					posi = ENUM_HMMPOS.MULTIWORD;
				break;
			case 'n':
				hmmPosLen = hmmPos.length();
				if (hmmPosLen == 4 && hmmPos.charAt(1) == 'o')
					posi = ENUM_HMMPOS.NOUN;
				else if (hmmPosLen == 10 && hmmPos.charAt(9) == 't')
					posi = ENUM_HMMPOS.NOUNCONST;
				else if (hmmPosLen == 8 && hmmPos.charAt(7) == 'f')
					posi = ENUM_HMMPOS.NOUNDEF;
				else if (hmmPosLen == 15 && hmmPos.charAt(14) == 'e')
					posi = ENUM_HMMPOS.NOUNPOSSESSIVE;
				else if (hmmPos.charAt(1) == 'e')
					posi = ENUM_HMMPOS.NEGATION;
				else if (hmmPos.charAt(3) == 'e')
					posi = ENUM_HMMPOS.NUMERAL;
				else if (hmmPos.charAt(3) == 'b')
					posi = ENUM_HMMPOS.NUMBER_EXPRESSION;
				// else {
				// throw new Exception(
				// "Str2Num:str2NumStrInterrogativeType illegal value "
				// + "for hmmPos=" + hmmPos
				// + " for lexiconItem: " + lexiconItem
				// + " for input token: " + word);
				//
				// }
				break;
			case 'p':
				hmmPosLen = hmmPos.length();
				if (hmmPosLen >= 4 && hmmPos.charAt(2) == 'e' && hmmPos.charAt(3) == 'p')
					posi = ENUM_HMMPOS.PREPOSITION;
				else if (hmmPosLen == 7 && hmmPos.charAt(2) == 'o')
					posi = ENUM_HMMPOS.PRONOUN;
				else if (hmmPosLen == 6)
					posi = ENUM_HMMPOS.PREFIX;
				else if (hmmPosLen == 10 && hmmPos.charAt(2) == 'o')
					posi = ENUM_HMMPOS.PROPERNAME;
				else if (hmmPosLen == 14 && hmmPos.charAt(2) == 'o')
					posi = ENUM_HMMPOS.PROPERNAMEDEF;
				else if (hmmPosLen == 16 && hmmPos.charAt(2) == 'r')
					posi = ENUM_HMMPOS.PARTICIPLECONST;
				else if (hmmPosLen == 14 && hmmPos.charAt(2) == 'r')
					posi = ENUM_HMMPOS.PARTICIPLEDEF;
				else if (hmmPosLen == 10 && hmmPos.charAt(2) == 'r')
					posi = ENUM_HMMPOS.PARTICIPLE;
				else if (hmmPosLen >= 3 && hmmPos.charAt(2) == 's')
					posi = ENUM_HMMPOS.PASSIVEPARTICIPLE;
				// else {
				// throw new Exception(
				// "Str2Num:str2NumStrInterrogativeType illegal value "
				// + "for hmmPos=" + hmmPos
				// + " for lexiconItem: " + lexiconItem
				// + " for input token: " + word);
				//
				// }
				break;
			case 'q':
				posi = ENUM_HMMPOS.QUANTIFIER;
				break;
			case 't':
				posi = ENUM_HMMPOS.TITLE;
				break;
			case 'u':
				if (hmmPosLen >= 2 && hmmPos.charAt(1) == 'n')
					posi = ENUM_HMMPOS.UNKNOWN;
				else if (hmmPosLen >= 2 && hmmPos.charAt(1) == 'r')
					posi = ENUM_HMMPOS.URL;
				break;

			case 'v':
				if (hmmPos.length() == 8)
					posi = ENUM_HMMPOS.VERBINF;
				else
					posi = ENUM_HMMPOS.VERB;
				break;
			case 'w':
				posi = ENUM_HMMPOS.WPREFIX;
				break;
			case 'z':
				posi = ENUM_HMMPOS.ZEVEL;
				break;
			default:
				// throw new Exception(
				// "Str2Num:str2NumStrInterrogativeType illegal value "
				// + "for hmmPos=" + hmmPos + " for lexiconItem: "
				// + lexiconItem + " for input token: " + word);
			}
		}

		return posi;
	}

	/**
	 * This method is used to translate numeral type string to codes
	 * 
	 * @param type
	 * @param lexiconItem
	 * @param word
	 * @return
	 * @throws Exception
	 */
	public static ENUM_NUMERAL_TYPE str2NumNumaralType(final String type, final String lexiconItem, final String word)
			throws Exception {
		ENUM_NUMERAL_TYPE typei = null;
		if (type != null && type.length() > 0) {
			char typeStartChar = type.charAt(0);
			switch (typeStartChar) {
			case 'l':
				typei = ENUM_NUMERAL_TYPE.LITERL_NUMBER;
				break;
			case 'g':
				typei = ENUM_NUMERAL_TYPE.GEMATRIA;
				break;
			case 'u':
				typei = ENUM_NUMERAL_TYPE.UNSPECIFIED;
				break;
			default:
				char typeMiddleChar = type.charAt(8);
				switch (typeMiddleChar) {
				case 'o':
					typei = ENUM_NUMERAL_TYPE.ORDINAL;
					break;
				case 'c':
					typei = ENUM_NUMERAL_TYPE.CARDINAL;
					break;
				case 'f':
					typei = ENUM_NUMERAL_TYPE.FRACTIONAL;
					break;
				default:
					throw new Exception("Str2Num:str2NumNumaralType illegal value " + "for type=" + type
							+ " for lexiconItem: " + lexiconItem + " for input token: " + word);
				}
			}

		}
		return typei;
	}

	/**
	 * This method is used to translate number strings to codes
	 * 
	 * @param number
	 * @param lexiconItem
	 * @param word
	 * @return
	 * @throws Exception
	 */
	public static ENUM_NUMBER str2NumNumber(final String number, final String lexiconItem, final String word)
			throws Exception {
		ENUM_NUMBER numberi = null;
		if (number != null && number.length() > 0) {
			char numberStartChar = number.charAt(0);
			// masculine
			switch (numberStartChar) {
			case 's':
				if (number.length() == 8)
					numberi = ENUM_NUMBER.SINGULAR;
				else
					numberi = ENUM_NUMBER.SINGULAR_AND_PLURAL;
				break;
			case 'p':
				numberi = ENUM_NUMBER.PLURAL;
				break;
			case 'd':
				if (number.length() == 4)
					numberi = ENUM_NUMBER.DUAL;
				else
					numberi = ENUM_NUMBER.DUAL_AND_PLURAL;
				break;
			case 'u':
				numberi = ENUM_NUMBER.UNSPECIFIED;
				break;
			default:
				throw new Exception("Str2Num:str2NumNumber illegal value for number =" + number + " for lexiconItem: "
						+ lexiconItem + " for input token: " + word);
			}
		}
		return numberi;
	}

	/**
	 * This method is used to translate number expressions string types to codes
	 * 
	 * @param type
	 * @return
	 * @throws Exception
	 */
	public static ENUM_NUMBER_EXPRESSION str2NumNumberExpression(final String type) throws Exception {
		ENUM_NUMBER_EXPRESSION typei = null;
		if (type != null && type.length() > 0) {
			char typeStartChar = type.charAt(0);
			// masculine
			switch (typeStartChar) {
			case 'd':
				typei = ENUM_NUMBER_EXPRESSION.DATE;
				break;
			case 't':
				int len = type.length();
				if (len == 4)
					typei = ENUM_NUMBER_EXPRESSION.TIME;
				else
					typei = ENUM_NUMBER_EXPRESSION.TIMEGAMESCORE;
				break;
			case 'g':
				typei = ENUM_NUMBER_EXPRESSION.GEMESCORE;
				break;
			case 'n':
				typei = ENUM_NUMBER_EXPRESSION.NUMBERING;
				break;
			default:
				throw new Exception("Str2Num:str2NumNumberExpression illegal value for type =" + type);

			}
		}
		return typei;
	}

	/**
	 * This methods is used to translate output patters strings to codes
	 * 
	 * @param outputPattern
	 *           - used when building the XML output
	 * @param lexiconItem
	 * @param word
	 * @return
	 * @throws Exception
	 */
	public static ENUM_OUTPUT_PATTERN str2NumOutputPattern(final String outputPattern, final String lexiconItem,
			final String word) throws Exception {
		ENUM_OUTPUT_PATTERN outputPatterni = null;
		if (outputPattern != null && outputPattern.length() > 0) {
			char outputPatternStartChar = outputPattern.charAt(0);
			switch (outputPatternStartChar) {
			case 'a':
				if (outputPattern.charAt(2) == 'v')
					outputPatterni = ENUM_OUTPUT_PATTERN.ADVERB;
				else if (outputPattern.charAt(2) == 'j')
					outputPatterni = ENUM_OUTPUT_PATTERN.ADJECTIVE;
				else {
					throw new Exception("Str2Num:str2NumOutputPattern illegal value for outputPattern =" + outputPattern
							+ " for lexiconItem: " + lexiconItem + " for input token: " + word);

				}
				break;
			case 'c':
				if (outputPattern.charAt(2) == 'n')
					outputPatterni = ENUM_OUTPUT_PATTERN.CONJUNCTION;
				else if (outputPattern.charAt(2) == 'p')
					outputPatterni = ENUM_OUTPUT_PATTERN.COPULA;
				else {
					throw new Exception("Str2Num:str2NumOutputPattern illegal value for outputPattern =" + outputPattern
							+ " for lexiconItem: " + lexiconItem + " for input token: " + word);

				}
				break;
			case 'e':
				outputPatterni = ENUM_OUTPUT_PATTERN.EXISTENTIAL;
				break;
			case 'i':
				if (outputPattern.charAt(5) == 'j')
					outputPatterni = ENUM_OUTPUT_PATTERN.INTERJECTION;
				else if (outputPattern.charAt(5) == 'r')
					outputPatterni = ENUM_OUTPUT_PATTERN.INTERROGATIVE;
				// Not in use
				// else
				// outputPatterni = ENUM_OUTPUT_PATTERN.IMPERSONAL;
				else if (outputPattern.charAt(2) == 'd')
					outputPatterni = ENUM_OUTPUT_PATTERN.INDEPENDENTINFINITIVE;
				else {
					throw new Exception("Str2Num:str2NumOutputPattern illegal value for outputPattern =" + outputPattern
							+ " for lexiconItem: " + lexiconItem + " for input token: " + word);

				}
				break;
			case 'm':
				if (outputPattern.charAt(1) == 'o')
					outputPatterni = ENUM_OUTPUT_PATTERN.MODAL;
				else if (outputPattern.charAt(1) == 'u')
					outputPatterni = ENUM_OUTPUT_PATTERN.MULTIWORD;
				break;
			case 'n':
				if (outputPattern.charAt(1) == 'o')
					outputPatterni = ENUM_OUTPUT_PATTERN.NOUN;
				else if (outputPattern.charAt(1) == 'e')
					outputPatterni = ENUM_OUTPUT_PATTERN.NEGATION;
				else if (outputPattern.charAt(1) == 'u')
					outputPatterni = ENUM_OUTPUT_PATTERN.NUMERAL;

				else {
					throw new Exception("Str2Num:str2NumOutputPattern illegal value for outputPattern =" + outputPattern
							+ " for lexiconItem: " + lexiconItem + " for input token: " + word);

				}
				break;
			case 'p':
				if (outputPattern.charAt(2) == 'e' && outputPattern.charAt(3) == 'p')
					outputPatterni = ENUM_OUTPUT_PATTERN.PREPOSITION;
				else if (outputPattern.charAt(3) == 'n')
					outputPatterni = ENUM_OUTPUT_PATTERN.PRONOUN;
				else if (outputPattern.charAt(3) == 'p')
					outputPatterni = ENUM_OUTPUT_PATTERN.PROPERNAME;
				else if (outputPattern.charAt(3) == 't')
					outputPatterni = ENUM_OUTPUT_PATTERN.PARTICIPLE;
				else if (outputPattern.charAt(3) == 's')
					outputPatterni = ENUM_OUTPUT_PATTERN.PASSIVEPARTICIPLE;
				else {
					throw new Exception("Str2Num:str2NumOutputPattern illegal value for outputPattern =" + outputPattern
							+ " for lexiconItem: " + lexiconItem + " for input token: " + word);
				}
				break;
			case 'q':
				outputPatterni = ENUM_OUTPUT_PATTERN.QUANTIFIER;
				break;
			case 't':
				outputPatterni = ENUM_OUTPUT_PATTERN.TITLE;
				break;
			case 'u':
				outputPatterni = ENUM_OUTPUT_PATTERN.UNKNOWN;
				break;
			case 'v':
				outputPatterni = ENUM_OUTPUT_PATTERN.VERB;
				break;
			case 'w':
				outputPatterni = ENUM_OUTPUT_PATTERN.WPREFIX;
				break;
			default:
				throw new Exception("Str2Num:str2NumOutputPattern illegal value for outputPattern =" + outputPattern
						+ " for lexiconItem: " + lexiconItem + " for input token: " + word);
			}
		}
		return outputPatterni;
	}

	/**
	 * This method is used for translating the part of speech values to codes
	 * 
	 * @param pos
	 *           - part of speech
	 * @param lexiconItem
	 * @param word
	 * @return
	 * @throws Exception
	 */
	public static ENUM_POS str2NumPos(final String pos_in, final String lexiconItem, final String word)
			throws Exception {
		ENUM_POS posi = null;
		if (pos_in != null && pos_in.length() > 0) {
			final String pos = pos_in.toLowerCase();
			char posStartChar = pos.charAt(0);
			posStartChar = Character.toLowerCase(posStartChar);

			/*
			 * char pos_charAt_1 = 'o'; char pos_charAt_2 = 'v'; char pos_charAt_3 = 'e';
			 * char pos_charAt_5 = 'j'; if (pos.length() > 1) { pos_charAt_1 =
			 * Character.toLowerCase(pos.charAt(1)); if (pos.length() > 2) { pos_charAt_2 =
			 * Character.toLowerCase(pos.charAt(2)); if (pos.length() > 3) { pos_charAt_3 =
			 * Character.toLowerCase(pos.charAt(3)); if (pos.length() > 5) { pos_charAt_5 =
			 * Character.toLowerCase(pos.charAt(5)); } } } } /*** UPDATE END
			 */
			switch (posStartChar) {
			case 'a':
				if (Character.toLowerCase(pos.charAt(2)) == 'v')
					posi = ENUM_POS.ADVERB;
				else if (Character.toLowerCase(pos.charAt(2)) == 'j')
					posi = ENUM_POS.ADJECTIVE;
				else {
					throw new Exception("Str2Num:str2NumPos illegal value for pos =" + pos + " for lexiconItem: "
							+ lexiconItem + " for input token: " + word);
				}
				break;
			case 'c':
				if (pos.charAt(2) == 'n')
					posi = ENUM_POS.CONJUNCTION;
				else if (pos.charAt(2) == 'p')
					posi = ENUM_POS.COPULA;
				else {
					throw new Exception("Str2Num:str2NumPos illegal value for pos =" + pos + " for lexiconItem: "
							+ lexiconItem + " for input token: " + word);

				}
				break;
			case 'e':
				posi = ENUM_POS.EXISTENTIAL;
				break;
			case 'i':
				if (pos.charAt(5) == 'j')
					posi = ENUM_POS.INTERJECTION;
				else if (pos.charAt(5) == 'r')
					posi = ENUM_POS.INTERROGATIVE;
				// Not in use
				// else
				// posi = ENUM_POS.IMPERSONAL;
				else if (pos.charAt(2) == 'd')
					posi = ENUM_POS.INDEPENDENTINFINITIVE;
				else {
					throw new Exception("Str2Num:str2NumPos illegal value for pos =" + pos + " for lexiconItem: "
							+ lexiconItem + " for input token: " + word);

				}
			case 'm':
				if (Character.toLowerCase(pos.charAt(1)) == 'o')
					posi = ENUM_POS.MODAL;
				else if (pos.charAt(1) == 'u')
					posi = ENUM_POS.MULTIWORD;
				break;
			case 'n':
				if (pos.charAt(1) == 'o')
					posi = ENUM_POS.NOUN;
				else if (pos.charAt(1) == 'e')
					posi = ENUM_POS.NEGATION;
				else if (pos.charAt(3) == 'e')
					posi = ENUM_POS.NUMERAL;
				else if (pos.charAt(3) == 'b')
					posi = ENUM_POS.NUMBER_EXPRESSION;
				else {
					throw new Exception("Str2Num:str2NumPos illegal value for pos =" + pos + " for lexiconItem: "
							+ lexiconItem + " for input token: " + word);

				}
				break;
			case 'p':
				if (pos.charAt(2) == 'e' && pos.charAt(3) == 'p')
					posi = ENUM_POS.PREPOSITION;
				else if (pos.charAt(3) == 'n')
					posi = ENUM_POS.PRONOUN;
				else if (pos.charAt(3) == 'p')
					posi = ENUM_POS.PROPERNAME;
				else if (pos.charAt(3) == 't')
					posi = ENUM_POS.PARTICIPLE;
				else if (pos.charAt(3) == 's')
					posi = ENUM_POS.PASSIVEPARTICIPLE;
				else {
					throw new Exception("Str2Num:str2NumPos illegal value for pos =" + pos + " for lexiconItem: "
							+ lexiconItem + " for input token: " + word);

				}
				break;
			case 'q':
				posi = ENUM_POS.QUANTIFIER;
				break;
			case 't':
				posi = ENUM_POS.TITLE;
				break;
			case 'u':
				posi = ENUM_POS.UNKNOWN;
				break;
			case 'v':
				posi = ENUM_POS.VERB;
				break;
			case 'w':
				posi = ENUM_POS.WPREFIX;
				break;
			case 'z':
				posi = ENUM_POS.ZEVEL;
				break;
			default:
				throw new Exception("Str2Num:str2NumPos illegal value for pos =" + pos + " for lexiconItem: " + lexiconItem
						+ " for input token: " + word);

			}
		}
		return posi;
	}

	/**
	 * This method is used to translate register strings to codes
	 * 
	 * @param register
	 * @param lexiconItem
	 * @param word
	 * @return
	 * @throws Exception
	 */
	public static ENUM_REGISTER str2NumRegister(final String register, final String lexiconItem, final String word)
			throws Exception {
		ENUM_REGISTER registeri = null;
		if (register != null && register.length() > 0) {
			char registerStartChar = register.charAt(0);
			switch (registerStartChar) {
			case 'f':
				registeri = ENUM_REGISTER.FORMAL;
				break;
			case 'a':
				registeri = ENUM_REGISTER.ARCHAIC;
				break;
			case 'i':
				registeri = ENUM_REGISTER.INFORMAL;
				break;
			case 'u':

				registeri = ENUM_REGISTER.UNSPECIFIED;
				break;
			default:
				throw new Exception("Str2Num:str2NumRegister illegal value for register =" + register + " for lexiconItem: "
						+ lexiconItem + " for input token: " + word);
			}
		}
		return registeri;
	}

	/**
	 * This method is used to translate spelling strings to codes
	 * 
	 * @param spelling
	 * @param lexiconItem
	 * @param word
	 * @return
	 * @throws Exception
	 */
	public static ENUM_SPELLING str2NumSpelling(final String spelling, final String lexiconItem, final String word)
			throws Exception {
		ENUM_SPELLING spellingi = null;
		if (spelling != null && spelling.length() > 0) {
			char spellingStartChar = spelling.charAt(0);
			switch (spellingStartChar) {
			case 's':
				spellingi = ENUM_SPELLING.STANDARD;
				break;
			case 'i':
				spellingi = ENUM_SPELLING.IRREGULAR;
				break;
			case 'u':
				spellingi = ENUM_SPELLING.UNSPECIFIED;
				break;
			default:
				throw new Exception("Str2Num:str2NumSpelling illegal value for spelling =" + spelling + " for lexiconItem: "
						+ lexiconItem + " for input token: " + word);
			}
		}
		return spellingi;
	}

	/**
	 * @param type
	 * @param lexiconItem
	 * @param word
	 * @return
	 * @throws Exception
	 */
	public static int str2NumStrInterrogativeType(final String type, final String lexiconItem, final String word)
			throws Exception {
		int interrogativeTypeInt = -1;
		if (type != null && type.length() > 0) {
			char typeStartChar = type.charAt(0);
			switch (typeStartChar) {
			case '1':
				interrogativeTypeInt = 1;
				break;
			case '2':
				interrogativeTypeInt = 2;
				break;
			case '3':
				interrogativeTypeInt = 3;
				break;
			case 'p': // pronoun //proadverb //prodet
				switch (type.charAt(3)) {
				case 'n':
					interrogativeTypeInt = 1;
					break;
				case 'a':

					interrogativeTypeInt = 2;
					break;
				case 'd':

					interrogativeTypeInt = 3;
					break;
				}
				break;

			case 'y': // yesno
			case '4':
				interrogativeTypeInt = 4;
				break;
			case 'u': // //unspecified
			case '5':
				interrogativeTypeInt = 5;
				break;
			default:
				throw new Exception("Str2Num:str2NumStrInterrogativeType illegal value " + "for type=" + type
						+ " for lexiconItem: " + lexiconItem + " for input token: " + word);
			}
		}
		return interrogativeTypeInt;
	}

	/**
	 * This method is used to translate participle type string to codes
	 * 
	 * @param type
	 * @param lexiconItem
	 * @param word
	 * @return
	 * @throws Exception
	 */
	public static int str2NumStrParticipleType(final String type, final String lexiconItem, final String word)
			throws Exception {
		int participleTypeInt = -1;
		if (type != null && type.length() > 0) {
			char typeStartChar = type.charAt(0);
			switch (typeStartChar) {
			case '1':
			case 'n':
				participleTypeInt = 1;
				break;
			case '2':
			case 'a':
				participleTypeInt = 2;
				break;
			case '3':
			case 'v':
				participleTypeInt = 3;
				break;
			case '4':
			case 'u':
				participleTypeInt = 4;
				break;
			default:
				throw new Exception("Str2Num:str2NumStrParticipleType illegal value " + "for type=" + type
						+ " for lexiconItem: " + lexiconItem + " for input token: " + word);
			}
		}
		return participleTypeInt;
	}

	/**
	 * @param type
	 * @param lexiconItem
	 * @param word
	 * @return
	 * @throws Exception
	 */
	public static int str2NumStrQuantifierType(final String type, final String lexiconItem, final String word)
			throws Exception {
		int quantifierTypeInt = -1;
		if (type != null && type.length() > 0) {
			char typeStartChar = type.charAt(0);
			switch (typeStartChar) {
			case '1':
			case 'a':
				quantifierTypeInt = 1;
				break;
			case '2':
			case 'p':
				quantifierTypeInt = 2;
				break;
			case '3':
			case 'd':
				quantifierTypeInt = 3;
				break;
			case '4':
			case 'u':
				quantifierTypeInt = 4;
				break;
			default:
				throw new Exception("Str2Num:str2NumStrQuantifierType illegal value " + "for type=" + type
						+ " for lexiconItem: " + lexiconItem + " for input token: " + word);
			}
		}
		return quantifierTypeInt;
	}

	/**
	 * This method is used to translate suffixFunction strings to codes
	 * 
	 * @param suffixFunction
	 * @param lexiconItem
	 * @param word
	 * @return
	 * @throws Exception
	 */
	public static ENUM_SUFFIX_FUNCTION str2NumSuffixFunction(final String suffixFunction, final String lexiconItem,
			final String word) throws Exception {
		ENUM_SUFFIX_FUNCTION suffixFunctioni = null;
		if (suffixFunction != null && suffixFunction.length() > 0) {
			char suffixFunctionStartChar = suffixFunction.charAt(0);
			switch (suffixFunctionStartChar) {
			case 'a':
				suffixFunctioni = ENUM_SUFFIX_FUNCTION.SUFFIX_FUNCTION_ACCUSATIVE_OR_NOMINATIVE;
				break;
			case 'p':
				if (suffixFunction.charAt(1) == 'o')
					suffixFunctioni = ENUM_SUFFIX_FUNCTION.SUFFIX_FUNCTION_POSSESSIVE;
				else if (suffixFunction.charAt(1) == 'r')
					suffixFunctioni = ENUM_SUFFIX_FUNCTION.SUFFIX_FUNCTION_PRONOMIAL;
				break;
			case 'u':
				suffixFunctioni = ENUM_SUFFIX_FUNCTION.SUFFIX_FUNCTION_UNSPECIFIED;
				break;
			default:
				throw new Exception("Str2Num:str2NumSuffixFunction illegal value " + "for suffixFunction =" + suffixFunction
						+ " for lexiconItem: " + lexiconItem + " for input token: " + word);
			}
		}
		return suffixFunctioni;
	}

	// public static ENUM_PREFIX_FUNCTION str2NumPrefixFunction(
	// final String function, final String lexiconItem,
	// final String word) {
	// ENUM_PREFIX_FUNCTION functioni = null;
	// char functionStartChar = function.charAt(0);
	// switch (functionStartChar) {
	// case 'd':
	// functioni = ENUM_PREFIX_FUNCTION.DEFINITEARTICLE;
	// break;
	// default:
	// final String message = "illegal prefix function: " + function
	// + " for lexiconItem: " + lexiconItem + " for input token: "
	// + word;
	// System.out.println(message);
	// System.exit(1);
	// }
	// return functioni;
	// }

	/**
	 * This method is used to translate tense strings to dodes
	 * 
	 * @param tense
	 * @param lexiconItem
	 * @param word
	 * @return
	 * @throws Exception
	 */
	public static ENUM_TENSE str2NumTense(final String tense, final String lexiconItem, final String word)
			throws Exception {
		ENUM_TENSE tensei = null;
		if (tense != null && tense.length() > 0) {
			char tenseStartChar = tense.charAt(0);
			if (tense.length() == 0)
				tensei = ENUM_TENSE.UNSPECIFIED;
			else {
				switch (tenseStartChar) {
				case 'b':
					tensei = ENUM_TENSE.BEINONI;
					// not in use
					// tensei = ENUM_TENSE.BAREINFINITIVE;
					break;
				case 'f':
					tensei = ENUM_TENSE.FUTURE;
					break;
				case 'i':
					if (tense.charAt(1) == 'm')
						tensei = ENUM_TENSE.IMPERATIVE;
					else if (tense.charAt(1) == 'n')
						tensei = ENUM_TENSE.INFINITIVE;
					break;
				case 'p':
					if (tense.charAt(1) == 'a')
						tensei = ENUM_TENSE.PAST;
					else if (tense.charAt(1) == 'r')
						tensei = ENUM_TENSE.PRESENT;
					break;
				case 'u':
					tensei = ENUM_TENSE.UNSPECIFIED;
					break;
				default:
					throw new Exception("Str2Num:str2NumTense illegal value for tense =" + tense + " for lexiconItem: "
							+ lexiconItem + " for input token: " + word);
				}
			}
		}
		return tensei;
	}
}
