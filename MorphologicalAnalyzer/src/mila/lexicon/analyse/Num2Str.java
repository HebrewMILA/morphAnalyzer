package mila.lexicon.analyse;

import static mila.lexicon.analyse.Constants.*;

/**
 * 
 * Num2Str.java Used when using data files to translate the digits values to
 * values
 * 
 * @author Dalia Bojan
 * @version %G%
 */

public class Num2Str {
	/**
	 * This method translates data file codes for polarity to strings
	 * 
	 * @param polarity
	 * @return
	 * @throws Exception
	 */
	public static String char2StrPolarity(String polarity) throws Exception {
		String decodedPolarity = "";
		switch (polarity.charAt(0)) {
		case 'p':
			decodedPolarity = "positive";
			break;
		case 'n':
			decodedPolarity = "negative";
			break;
		case 'u':
			decodedPolarity = "unspecified";
			break;
		default:
			throw new Exception("Num2Str:char2StrPolarity illegal value " + "for polarity=" + polarity);

		}
		return decodedPolarity;
	}

	/**
	 * This method translates data file codes for part of speech to strings
	 * 
	 * @param posi
	 * @param transliterated
	 * @return
	 * @throws Exception
	 */
	public static String decodePos(ENUM_POS posi, String transliterated) throws Exception {
		String pos = "";
		switch (posi) {
		case QUANTIFIER:
			pos = "quantifier";
			break;
		case INTERJECTION:
			pos = "interjection";
			break;
		case INTERROGATIVE:
			pos = "interrogative";
			break;
		case CONJUNCTION:
			pos = "conjunction";
			break;
		case ADVERB:
			pos = "adverb";
			break;
		case PREPOSITION:
			pos = "preposition";
			break;
		case NOUN:
			pos = "noun";
			break;
		case ADJECTIVE:
			pos = "adjective";
			break;
		case PRONOUN:
			pos = "pronoun";
			break;
		case PROPERNAME:
			pos = "properName";
			break;
		case VERB:
			pos = "verb";
			break;
		case NEGATION:
			pos = "negation";
			break;
		case PARTICIPLE:
			pos = "participle";
			break;
		case NUMERAL:
			pos = "numeral";
			break;
		case EXISTENTIAL:
			pos = "existential";
			break;
		case IMPERSONAL:
			pos = "impersonal";
			break;
		case MODAL:
			pos = "modal";
			break;
		case WPREFIX:
			pos = "wPrefix";
			break;
		case PASSIVEPARTICIPLE:
			pos = "passiveParticiple";
			break;
		case INDEPENDENTINFINITIVE:
			pos = "indInf";
			break;
		case COPULA:
			pos = "copula";
			break;
		case TITLE:
			pos = "title";
			break;
		case MULTIWORD:
			pos = "multiWord";
			break;
		default:
			throw new Exception(
					"Num2Str:decodePos illegal  value " + "for pos=" + pos + " for transliterated: " + transliterated);
		}
		return pos;
	}

	/**
	 * This method translates data file codes for binyan to strings
	 * 
	 * @param binyani
	 *           - the encoded value of the Binyan in the inflections.data
	 * @param transliterated
	 *           - the transliterated form of the inflection
	 * @return - the decoded Binyan value
	 * @throws Exception
	 */
	public static String strNum2StrBinyan(final String numBinyan, final String lexiconItem, final String word)
			throws Exception {
		String binyan = "";
		switch (numBinyan.charAt(0)) {
		case '1':
			binyan = "Pa'al";
			break;
		case '2':
			binyan = "Nif'al";
			break;
		case '3':
			binyan = "Pi'el";
			break;
		case '4':
			binyan = "Pu'al";
			break;
		case '5':
			binyan = "Hif'il";
			break;
		case '6':
			binyan = "Huf'al";
			break;
		case '7':
			binyan = "Hitpa'el";
			break;
		case '-':
			binyan = "unspecified";
			break;

		default:
			throw new Exception("Num2Str:strNum2StrBinyan illegal value " + "for binyan=" + binyan + " for lexiconItem: "
					+ lexiconItem + " for input token: " + word);
		}
		return binyan;
	}

	/**
	 * This method translates data file codes for gender to strings
	 * 
	 * @param genderi
	 *           - the encoded value of the gender in the inflections.data
	 * @param transliterated
	 *           - the transliterated form of the inflection
	 * @return - the decoded Gender value
	 * @throws Exception
	 */
	public static String strNum2StrGender(final String numGender, final String lexiconItem, final String word)
			throws Exception {
		String gender = "";
		switch (numGender.charAt(0)) {
		case '1':
			gender = "masculine";
			break;
		case '2':
			gender = "feminine";
			break;
		case '3':
			gender = "masculine and feminine";

			break;
		case '4':
		case '-':
			gender = "unspecified";
			break;

		default:
			throw new Exception("Num2Str:strNum2StrGender illegal value " + "for numGender=" + numGender
					+ " for lexiconItem: " + lexiconItem + " for input token: " + word);
		}
		return gender;
	}

	/**
	 * This method translates data file codes for hAttribute to strings
	 * 
	 * @param baseDefinitenessi
	 * @param lexiconItem
	 * @param word
	 * @return
	 * @throws Exception
	 */
	public static String strNum2StrhAttribute(final String baseDefinitenessi, final String lexiconItem,
			final String word) throws Exception {
		String baseDefiniteness = "";
		switch (baseDefinitenessi.charAt(0)) {
		case '1':
			baseDefiniteness = "tt";
			break;
		case '2':
			baseDefiniteness = "tf";
			break;
		case '3':
			baseDefiniteness = "f";
			break;
		case '4':
			baseDefiniteness = "rf";
			break;
		case '5':
			baseDefiniteness = "rt";
			break;
		case '6':
			baseDefiniteness = "s";
			break;
		case '-':
			baseDefiniteness = "unspecified";
			break;

		default:
			throw new Exception("Num2Str:strNum2StrhAttribute illegal value " + "for baseDefinitenessi="
					+ baseDefinitenessi + " for lexiconItem: " + lexiconItem + " for input token: " + word);
		}

		return baseDefiniteness;
	}

	/**
	 * This method translates data file codes for number to strings
	 * 
	 * @param numberi
	 *           - the encoded value of the number in the inflections.data
	 * @param transliterated
	 *           - the transliterated form of the inflection
	 * @return - the decoded Number value
	 * @throws Exception
	 */
	public static String strNum2StrNumber(final String numNumber, final String lexiconItem, final String word)
			throws Exception {
		String number = "";
		switch (numNumber.charAt(0)) {
		case '1':
			number = "singular";
			break;
		case '2':
			number = "singular and plural";
			break;
		case '3':
			number = "plural";
			break;
		case '4':
			number = "dual";
			break;
		case '5':
			number = "dual and plural";
			break;
		case '6':
		case '-':
			number = "unspecified";
			break;
		default:
			throw new Exception("Num2Str:strNum2StrNumber illegal value " + "for numNumber=" + numNumber
					+ " for lexiconItem: " + lexiconItem + " for input token: " + word);
		}
		return number;
	}

	/**
	 * This method translates data file codes for person/gender/number (used for
	 * suffix) to strings
	 * 
	 * @param PGNi
	 *           - the encoded value of the PERSON/GENDER/NUMBER in the
	 *           inflections.data
	 * @param transliterated
	 *           - the transliterated form of the inflection
	 * @return - the decoded PGN value
	 * @throws Exception
	 */
	public static String strNum2StrPGN(final String numPGN, final String lexiconItem, final String word)
			throws Exception {
		String PGN = "";
		switch (numPGN.charAt(0)) {
		case '1':
			if (numPGN.length() == 1)
				PGN = "1p/MF/Sg";
			else
				switch (numPGN.charAt(1)) {
				case '0':
					PGN = "3p/M/Pl";
					break;
				case '1':
					PGN = "3p/F/Pl";
					break;
				case '2':
					PGN = "3p/MF/Pl";
					break;
				case '3':
					PGN = "123p/M/Sg";
					break;
				case '4':
					PGN = "123p/F/Sg";
					break;
				case '5':
					PGN = "123p/F/Pl";
					break;
				case '6':
					PGN = "123p/M/Pl";
					break;
				case '7':
					PGN = "1p/M/Pl";
					break;
				case '8':
					PGN = "1p/F/Pl";
					break;
				case '9':
					PGN = "unspecified";
					break;

				}

			break;
		case '2':
			if (numPGN.length() == 1)
				PGN = "2p/F/Sg";
			else
				PGN = "NONE";
			break;
		case '3':
			PGN = "2p/M/Sg";
			break;
		case '4':
			PGN = "3p/M/Sg";
			break;
		case '5':
			PGN = "3p/F/Sg";
			break;
		case '6':
			PGN = "1p/MF/Pl";
			break;
		case '7':
			PGN = "2p/M/Pl";
			break;
		case '8':
			PGN = "2p/F/Pl";
			break;
		case '9':
			PGN = "2p/MF/Pl";
			break;
		case '-':
			PGN = "unspecified";
			break;
		default:
			throw new Exception("Num2Str:strNum2StrPGN illegal value " + "for PGN =" + PGN + " for lexiconItem: "
					+ lexiconItem + " for input token: " + word);
		}

		return PGN;
	}

	/**
	 * This method translates data file codes for part of speech to strings
	 * 
	 * @param numPOS
	 * @param lexiconItem
	 * @param word
	 * @return
	 * @throws Exception
	 */
	public static String strNum2StrPos(final String numPOS, final String lexiconItem, final String word)
			throws Exception {
		String pos = "";
		switch (numPOS.charAt(0)) {
		case '1':
			if (numPOS.length() == 1)
				pos = "quantifier"; // 1
			else if (numPOS.charAt(1) == '0')
				pos = "properName";// 10
			else if (numPOS.charAt(1) == '1')
				pos = "verb";// 11
			else if (numPOS.charAt(1) == '2')
				pos = "negation";// 12
			else if (numPOS.charAt(1) == '3')
				pos = "participle";// 13
			else if (numPOS.charAt(1) == '4')
				pos = "numeral";// 14
			else if (numPOS.charAt(1) == '5')
				pos = "existential";// 15
			else if (numPOS.charAt(1) == '7')
				pos = "modal";// 17
			else if (numPOS.charAt(1) == '8')
				pos = "wPrefix";// 18
			break;
		case '2':
			if (numPOS.length() == 1)
				pos = "interjection";// 2
			else {
				switch (numPOS.charAt(1)) {
				case '0':
					pos = "passiveParticiple";// 20
					break;
				case '1':
					pos = "indInf";// 21
					break;
				case '2':
					pos = "copula";// 22
					break;
				case '3':
					pos = "title";// 23
					break;
				case '4':
					pos = "multiWord";// 24
					break;
				default:
					throw new Exception("Num2Str:strNum2StrPos illegal value " + "for pos=" + pos + " for lexiconItem: "
							+ lexiconItem + " for input token: " + word + "the numPOS is :" + numPOS);
				}
			}
			break;
		case '3':
			pos = "interrogative";
			break;
		case '4':
			pos = "conjunction";
			break;
		case '5':
			pos = "adverb";
			break;
		case '6':
			pos = "preposition";
			break;
		case '7':
			pos = "noun";
			break;
		case '8':
			pos = "adjective";
			break;
		case '9':
			pos = "pronoun";
			break;
		default:
			throw new Exception("Num2Str:strNum2StrPos illegal value  " + "for pos=" + pos + " for lexiconItem: "
					+ lexiconItem + " for input token: " + word);
		}
		return pos;
	}

	/**
	 * This method translates data file codes for part of register to strings
	 * 
	 * @param scripti
	 *           - the encoded value of the script in the inflections.data file
	 * @param transliterated
	 *           - the transliterated form of the inflection
	 * @return the decoded script value
	 * @throws Exception
	 */
	public static String strNum2StrRegister(final String numRegister, final String lexiconItem, final String word)
			throws Exception {
		String register = "";
		switch (numRegister.charAt(0)) {
		case '1':
			register = "formal";
			break;
		case '2':
			register = "archaic";
			break;
		case '3':
			register = "informal";
			break;
		default:
			throw new Exception("Num2Str:strNum2StrRegister illegal value " + "for numRegister=" + numRegister
					+ " for lexiconItem: " + lexiconItem + " for input token: " + word);
		}
		return register;
	}

	/**
	 * This method translates data file codes for spelling to strings
	 * 
	 * @param scripti
	 *           - the encoded value of the script in the inflections.data file
	 * @param transliterated
	 *           - the transliterated form of the inflection
	 * @return the decoded script value
	 * @throws Exception
	 */
	public static String strNum2StrSpelling(final String numSpelling, final String lexiconItem, final String word)
			throws Exception {
		String spelling = "";
		switch (numSpelling.charAt(0)) {
		case '1':
			spelling = "standard";
			break;
		case '2':
			spelling = "irregular";
			break;
		default:
			throw new Exception("Num2Str:strNum2StrSpelling illegal value " + "for numSpelling=" + numSpelling
					+ " for lexiconItem: " + lexiconItem + " for input token: " + word);
		}
		return spelling;
	}

	/**
	 * This method translates data file codes for status to strings
	 * 
	 * @param numStatus
	 * @param lexiconItem
	 * @param word
	 * @return
	 * @throws Exception
	 */
	public static String strNum2StrStatus(final String numStatus, final String lexiconItem, final String word)
			throws Exception {
		String strStatus = "";
		switch (numStatus.charAt(0)) {
		case '1':
			strStatus = "true";
			break;
		case '2':
			strStatus = "false";
			break;
		case '-':
			strStatus = "unspecified";
			break;

		default:
			throw new Exception("Num2Str:strNum2StrStatus illegal value " + "for numStatus=" + numStatus
					+ " for lexiconItem: " + lexiconItem + " for input token: " + word);

		}

		return strStatus;

	}

	// /**
	// * This method translates data file codes for tense to strings
	// * @param baseConjunctionTypeChar -
	// * the encoded value of the BaseConjunctionType in the
	// * inflections.data
	// * @param transliterated -
	// * the transliterated form of the inflection
	// * @return - the decoded BaseConjunctionType value
	// * @throws Exception
	// */
	// public static String decodeBaseConjunctionType(
	// char baseConjunctionTypeChar, String transliterated) throws Exception {
	// String baseConjunctionType = "";
	// switch (baseConjunctionTypeChar) {
	// case 'c':
	// baseConjunctionType = "coordinating";
	// break;
	// case 's':
	// baseConjunctionType = "subordinating";
	// break;
	// case 'r':
	// baseConjunctionType = "relativizing";
	// break;
	// default:
	// throw new Exception("Num2Str:decodeBaseConjunctionType illegal value " +
	// "for baseConjunctionTypeChar=" + baseConjunctionTypeChar+
	// " for transliterated: " + transliterated );
	// }
	//
	// return baseConjunctionType;
	//
	// }

	// /**
	// * @param baseNumeralTypei
	// * @param transliterated
	// * @return
	// * @throws Exception
	// */
	// public static String decodeBaseNumeralType(int baseNumeralTypei,
	// String transliterated) throws Exception {
	// String baseNumeralType = "";
	// switch (baseNumeralTypei) {
	// case 1:
	// baseNumeralType = "numeral ordinal";
	// break;
	// case 2:
	// baseNumeralType = "numeral cardinal";
	// break;
	// case 3:
	// baseNumeralType = "numeral fractional";
	// break;
	// case 4:
	// baseNumeralType = "literal number";
	// break;
	// case 5:
	// baseNumeralType = "unspecified";
	// break;
	// default:
	// throw new Exception("Num2Str:decodeBaseNumeralType illegal value " +
	// "for baseNumeralTypei=" + baseNumeralTypei+
	// " for transliterated: " + transliterated );
	// }
	//
	// return baseNumeralType;
	//
	// }

	/**
	 * This method translates data file codes for suffixFunction to strings
	 * 
	 * @param suffixFunctionCode
	 * @param lexiconItem
	 * @param word
	 * @return
	 * @throws Exception
	 */
	public static String strNum2StrSuffixFunction(String suffixFunctionCode, final String lexiconItem, final String word)
			throws Exception {
		String suffixFunction = "";
		switch (suffixFunctionCode.charAt(0)) {
		case '1':
			suffixFunction = "possessive";
			break;
		case '2':
			suffixFunction = "accusative or nominative";
			break;
		case '3':
			suffixFunction = "pronomial";
			break;
		case '-':
			suffixFunction = "unspecified";
			break;

		default:

			throw new Exception("Num2Str:strNum2StrSuffixFunction illegal value " + "for suffixFunctionCode="
					+ suffixFunctionCode + " for lexiconItem: " + lexiconItem + " for input token: " + word);
		}
		return suffixFunction;
	}

	// /**
	// * This method decodes BasePronounType
	// *
	// * @param basePronounTypei -
	// * the encoded value of the BasePronounType in the
	// * inflections.data
	// * @param transliterated -
	// * the transliterated form of the inflection
	// * @return - the decoded decodeBasePronounType value
	// * @throws Exception
	// */
	// public static String decodeBasePronounType(int basePronounTypei,
	// String transliterated) throws Exception {
	// String basePronounType = "";
	// switch (basePronounTypei) {
	// case 1:
	// basePronounType = "interrogative";
	// break;
	// case 2:
	// basePronounType = "personal";
	// break;
	// case 3:
	// basePronounType = "demonstrative";
	// break;
	// case 4:
	// basePronounType = "impersonal";
	// break;
	// case 5:
	// basePronounType = "relativizer";
	// break;
	// case 6:
	// basePronounType = "reflexive";
	// break;
	// default:
	// throw new Exception("Num2Str:decodeBasePronounType illegal value " +
	// "for basePronounTypei=" + basePronounTypei+
	// " for transliterated: " + transliterated );
	// }
	// return basePronounType;
	// }
	//
	/**
	 * This method translates data file codes for tense to strings
	 * 
	 * @param tensei
	 *           - the encoded value of the tensei in the inflections.data
	 * @param transliterated
	 *           - the transliterated form of the inflection
	 * @return - the decoded tense value
	 * @throws Exception
	 */
	public static String strNum2StrTense(final String numTense, final String lexiconItem, final String word)
			throws Exception {
		String tense = "";
		switch (numTense.charAt(0)) {
		case '1':
			tense = "imperative";
			break;
		case '2':
			tense = "past";
			break;
		case '3':
			tense = "beinoni";
			break;
		case '4':
			tense = "future";
			break;
		case '5':
			tense = "infinitive";
			break;
		case '6':
			tense = "bareInfinitive";
			break;
		case '7':
			tense = "present"; // used for existential
			break;
		case '-':
			tense = "unspecified";
			break;
		default:
			throw new Exception("Num2Str:strNum2StrTense illegal value " + "for numTense=" + numTense
					+ " for lexiconItem: " + lexiconItem + " for input token: " + word);
		}
		return tense;
	}

	/**
	 * This method translates data file codes for type to strings It serves all the
	 * part of speech - so it has a parameter of part of speech in addition to a
	 * parameter of type
	 * 
	 * @param baseNamedEntityTypei
	 *           - the encoded value of the number in the inflections.data
	 * @param transliterated
	 *           - the transliterated form of the inflection
	 * @return -the decoded BaseNamedEntityType value
	 * @throws Exception
	 */
	public static String strNum2StrType(final String numType, String numPos, final String lexiconItem, final String word)
			throws Exception {
		String type = "";
		switch (numPos.charAt(0)) {
		case '1': // quantifier
			if (numPos.length() == 1) {
				switch (numType.charAt(0)) {
				case '1':
					type = "amount";
					break;
				case '2':
					type = "partitive";
					break;
				case '3':
					type = "determiner";
					break;
				case '-':
					type = "unspecified";
					break;
				default:
					throw new Exception(
							"Num2Str:strNum2StrType illegal value " + "for numType=" + numType + " for numPos: " + numPos);
				}
			} else {
				switch (numPos.charAt(1)) {
				case '0': // propername
					switch (numType.charAt(0)) {
					case '1':
						if (numType.length() == 1)
							type = "location";
						else if (numType.charAt(1) == '0') // 10
							type = "town";
						else if (numType.charAt(1) == '1') // 11
							type = "art";
						break;
					case '2':
						type = "person";
						break;
					case '3':
						type = "organization";
						break;
					case '4':
						type = "dateTime";
						break;
					case '5':
						type = "other";
						break;
					case '6':
						type = "product";
						break;
					case '7':
						type = "country";
						break;
					case '8':
						type = "symbol";
						break;
					case '9':
						type = "language";
						break;

					case '-':
						type = "unspecified";
						break;
					default:
						throw new Exception(
								"Num2Str:strNum2StrType illegal value " + "for numType=" + numType + " for numPos: " + numPos);
					}
					break;
				case '3': // participle
					switch (numType.charAt(0)) {
					case '1':
						type = "noun";
						break;
					case '2':
						type = "adjective";
						break;
					case '3':
						type = "verb";
						break;
					case '-':
						type = "unspecified";
						break;
					default:
						throw new Exception(
								"Num2Str:strNum2StrType illegal value " + "for numType=" + numType + " for numPos: " + numPos);
					}
					break;
				case '4':// numeral
					switch (numType.charAt(0)) {
					case '1':
						type = "numeral ordinal";
						break;
					case '2':
						type = "numeral cardinal";
						break;
					case '3':
						type = "numeral fractional";
						break;
					case '-':
						type = "unspecified";
						break;
					default:
						throw new Exception(
								"Num2Str:strNum2StrType illegal value " + "for numType=" + numType + " for numPos: " + numPos);

					}
				}

			}
			break;
		case '2': // passiveParticiple
			if (numPos.charAt(1) == '0')
				switch (numType.charAt(0)) {
				case '1':
					type = "noun";
					break;
				case '2':
					type = "adjective";
					break;
				case '3':
					type = "verb";
					break;
				case '-':
					type = "unspecified";
					break;
				default:
					throw new Exception(
							"Num2Str:strNum2StrType illegal value " + "for numType=" + numType + " for numPos: " + numPos);

				}
			break;
		case '3': // interrogative
			switch (numType.charAt(0)) {
			case '1':
				type = "pronoun";
				break;
			case '2':
				type = "proadverb";
				break;
			case '3':
				type = "prodet";
				break;
			case '4':
				type = "yesno";
				break;
			case '-':
				type = "unspecified";
				break;
			default:
				throw new Exception(
						"Num2Str:strNum2StrType illegal value " + "for numType=" + numType + " for numPos: " + numPos);

			}
			break;
		case '4': // conjunction
			switch (numType.charAt(0)) {
			case '1':
				type = "coordinating";
				break;
			case '2':
				type = "subordinating";
				break;
			case '3':
				type = "relativizing";
				break;
			case '-':
				type = "unspecified";
				break;
			default:
				throw new Exception(
						"Num2Str:strNum2StrType illegal value " + "for numType=" + numType + " for numPos: " + numPos);
			}
			break;
		case '9': // pronoun
			switch (numType.charAt(0)) {
			case '1':
				type = "interrogative";
				break;
			case '2':
				type = "personal";
				break;
			case '3':
				type = "demonstrative";
				break;
			case '4':
				type = "impersonal";
				break;
			case '5':
				type = "relativizer";
				break;
			case '6':
				type = "reflexive";
				break;
			case '-':
				type = "unspecified";
				break;
			default:
				throw new Exception(
						"Num2Str:strNum2StrType illegal value " + "for numType=" + numType + " for numPos: " + numPos);
			}
			break;
		default:
			throw new Exception(
					"Num2Str:strNum2StrType illegal value " + "for numType=" + numType + " for numPos: " + numPos);
		}
		return type;

	}
}
