package mila.lexicon.analyse;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;

import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import mila.corpus.CreateCorpusXML;
import mila.dataStructures.DBInflectionsRecord;
import mila.dataStructures.InflectedRecordNum;
import mila.lexicon.dbUtils.PrefixRecord;
import mila.lexicon.utils.StringUtils;
import mila.lexicon.utils.Translate;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;
import static mila.lexicon.analyse.Constants.*;

/**
 * 
 * TokenizationParse.java Purpose: parse input XML file which contains
 * segmentation/tokenization details of the input text Parsing is done using SAX
 * 
 * @author Dalia Bojan
 * @version %G%
 */

public class TokenizationParser extends DefaultHandler {
	String outputFile = "";

	/**
	 * A flag to signify if the current inflected form has prefix per entry
	 * (relevant only for pronouns)
	 */
	public boolean prefixPerEntryExistFlag = false;

	/**
	 * CreateCorpusXML class object which is used for creating the XML output
	 */
	private CreateCorpusXML createXML = null;
	// protected int tokensCount = 0;

	// related to the SAX parser used for parsing the XML input of tokenized
	// text
	public SAXParser _parser;

	// empty constructor
	public TokenizationParser() {

	}

	// SAX parser code for parsing the input XML tokenized text
	public TokenizationParser(CreateCorpusXML createXML) {
		try {
			this.createXML = createXML;
			SAXParserFactory factory = SAXParserFactory.newInstance();
			factory.setNamespaceAware(false);
			factory.setValidating(false);
			_parser = factory.newSAXParser();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
	}

	/**
	 * This methos is used only for handling a token containing ' or '' it is very
	 * similar to the method used for tokens that don't contain ' or '' -
	 * analyzeBaseAndPrefix the only difference is that we call
	 * analyzePrefixGimatriaAndInvertedCommas and not analyzeBase - in this case we
	 * search in both inflections and Gimatria tables and not only in inflectiosn
	 * table
	 * 
	 * @param transliterated
	 * @throws Exception
	 */

	protected boolean analyzeAcronymsBaseAndPrefix(String transliterated) throws Exception {
		boolean rt = false;
		int transliteratedLen = transliterated.length();
		// the longest prefix known is 6 chars long
		for (int i = 1; i < 6 && i < transliteratedLen && transliterated.charAt(i) != '"'
				&& transliterated.charAt(i) != '\''; i++) {
			String prefix = transliterated.substring(0, i);
			// לא תיתכן תחילית שאינה משהוכלב
			if (StringUtils.moshevkaleb(prefix)) {
				String base = transliterated.substring(i);
				rt = analyzePrefixGimatriaAndInvertedCommas(base, prefix);
			} else
				break;
		}
		return rt;
	}

	/**
	 * We check the possibility that the token is composed of prefix and base This
	 * method is searching in the inflections table/data file and in the prefixes
	 * table/data file
	 * 
	 * @param base
	 * @param prefix
	 * @param transliterated
	 * @param hebWord
	 * @return
	 */
	public boolean analyzeBase(final String base, String prefix, final String transliterated, final String hebWord) {
		// A flag signify whether we have found an anlysis based on prefix +
		// base
		boolean foundAnalysis = false;
		// A data structure (record) which holds a single entry from the
		// prefixes table/data file
		PrefixRecord pr = null;
		// The number of analysis based on the base section of the token found
		// in the inflections file/data file
		int baseListSize = 0;
		// A data structure which holds DBInflectionsRecor objects which
		// contains the relevant analysis of the token
		ArrayList<DBInflectionsRecord> inflectionsList = null;
		// A data structure (record) which holds a single entry from the
		// inflections table/data file
		DBInflectionsRecord inflectionsRecDB = null;
		// A data structure (record) which holds codes of a single entry from
		// the inflections table/data file
		InflectedRecordNum inflectionsRecNum = null;
		try {
			// getting all the relevant entries from the inflections data
			// file/table according to the key=base
			inflectionsList = Data.getInflections(base);
		} catch (Exception e) {
			System.out
					.println("XMLMorphAnalyzer:analyzeBase - Exception while getting inflections list for base = " + base);
			e.printStackTrace();
		}
		baseListSize = inflectionsList.size();
		if (baseListSize > 0) {
			// run on each analysis brought from the inflections table/data file
			// and check whether adding a prefix is relevant
			// according to it's part of speech and rules we have for
			// concatenating prefix to base by it's part of speech
			for (int i = 0; i < baseListSize; i++) {
				try {
					inflectionsRecDB = inflectionsList.get(i);
					inflectionsRecNum = Data.Str2NumBeforeValidation(inflectionsRecDB, hebWord);
				} catch (Exception e1) {
					System.out
							.println("XMLMorphAnalyzer:analyzeBase - Exception while analyzeInflectionList for word=" + base);
					e1.printStackTrace();
				}

				// relevant for pronouns, for example handles the case of el and
				// wlmi
				// wlmi will not appear in the inflections file - w and l of
				// wlmi are related to the prefix
				char prefixPerEntry = inflectionsRecDB.getPrefixPerEntry();

				String combinedPrefix = prefix;
				// if the base analysis is a pronoun and there is a prefix per
				// entry in addition to a prefix
				// for example wbalw for which prefixPerEntry = b prefix=w and
				// base =alw

				if (prefixPerEntry != 'u') {
					if (!prefix.equals(prefixPerEntry)) {
						combinedPrefix = prefix + prefixPerEntry;
						prefix = combinedPrefix;
					}
				}

				int prefixListSize = 0;

				// get the relevant entries from the prefixes table/ data file
				try {
					prefixListSize = Data.getPrefixes(prefix);
				} catch (Exception e2) {
					System.out.println("XMLMorphAnalyzer:analyzeBase - Exception while getting prefixes list for word="
							+ base + "and prefix=" + prefix);
					e2.printStackTrace();
				}
				// System.out.println("prefixListSize =" + prefixListSize);

				for (int j = 0; j < prefixListSize; j++) {
					pr = new PrefixRecord();
					pr = Data.analyzePrefixList(j);

					// check that concatenating the current prefix with the
					// current base is valid according to our rules
					if (validateByRules(base, prefix, inflectionsRecNum, pr)) {
						try {
							foundAnalysis = true;
							// generate the XML analysis
							TextOutput.buildXMLPrefixOutput(pr, inflectionsRecDB, inflectionsRecNum, createXML,
									Translate.Eng2Heb(base));
						} catch (Exception e3) {
							System.out.println("XMLMorphAnalyzer:analyzeBase - Exception while buildXMLPrefixOutput for word="
									+ base + " and prefix=" + prefix);
							e3.printStackTrace();
						}
					}
				}
			}
		}
		return foundAnalysis;
	}

	/**
	 * Check whether the token is comprised of prefix + subToken Special treatment
	 * is given to prefix (bklm) per entry - currently implemented for pronouns only
	 * 
	 * @param hebWord
	 * @param transliterated
	 * @return
	 */
	protected boolean analyzeBaseAndPrefix(final String hebWord, final String transliterated) {
		boolean foundAnalysisPerPrefixEntry = false;
		boolean foundAnalysisNoPerPrefixEntry = false;
		// We already know the value of prefixPerEntry from analyzeBaseNoPrefix
		if (prefixPerEntryExistFlag)
			// prefixPerEntry can be populated with a value different from
			// unspecified in the following circumstances:
			// - when analyzing it as a token without prefix in
			// analyzeBaseNoPrefix
			// - when analyzing it in
			// analyzeBaseAndPrefixNoPrefixPreEntry->analyzeBase and breaking
			// it's prefix - finding that the base is consists of
			// - prefixPerEntry
			// the logic in case there is a prefixPerEntry is different -
			// because the key for search in the inflections table will be the
			// whole transliterated
			// for example - balw - we will look for balw and not for alw in the
			// inflections table - otherwise the analysis of pronoun will not be
			// found
			// afterwards we will try to check whether there are several
			// combinations for the prefix
			foundAnalysisPerPrefixEntry = analyzeBaseAndPrefixAndPrefixPerEntry(hebWord, transliterated);

		try {
			foundAnalysisNoPerPrefixEntry = analyzeBaseAndPrefixNoPrefixPreEntry(hebWord, transliterated);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return foundAnalysisPerPrefixEntry || foundAnalysisNoPerPrefixEntry;
	}

	/**
	 * Check whether the token is comprised of prefix + subToken - when it is
	 * already known that at least one of the analyses contains prefixPreEntry (from
	 * analysing the whole token) Special treatment is given to prefix (bklm) per
	 * entry - currently implemented for pronouns only
	 * 
	 * @param hebWord
	 * @param transliterated
	 * @return
	 */
	protected boolean analyzeBaseAndPrefixAndPrefixPerEntry(final String hebWord, final String transliterated) {
		// System.out.println(hebWord);
		boolean foundAnalysis = false;
		// unspecified- we initialize it to unspecified, although we already
		// know that for one of the analysis prefixPerEntry!=unspecified
		// we need to find out for which of the analysis -
		// prefixPerEntry!='unspecified'
		char prefixPerEntry = 'u';

		int listSize = 0;
		ArrayList<?> inflectionsList = null;
		DBInflectionsRecord inflectionsRecDB = null;
		InflectedRecordNum inflectionsRecNum = null;
		try {
			inflectionsList = Data.getInflections(transliterated);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		listSize = inflectionsList.size();
		if (listSize > 0)
			for (int i = 0; i < listSize; i++) {
				prefixPerEntry = 'u';
				try {
					inflectionsRecDB = (DBInflectionsRecord) inflectionsList.get(i);
					inflectionsRecNum = Data.Str2NumBeforeValidation(inflectionsRecDB, hebWord);

				} catch (Exception e1) {
					System.out.println(
							"TokenizationParser: Exception occured while analyzeBaseNoPrefix for word=" + transliterated);
					e1.printStackTrace();
				}
				// there are special entries for which we define prefix per
				// entry - we can't define a rule per pos etc for them
				prefixPerEntry = inflectionsRecNum.getPrefixPerEntry();
				String base = "";
				PrefixRecord pr = null;
				if (prefixPerEntry != 'u') {
					for (int k = 1; k < 6 && k < transliterated.length(); k++) {
						String prefix = transliterated.substring(0, k);

						// לא תיתכן תחילית שאינה משהוכלב
						if (StringUtils.moshevkaleb(prefix)) {
							// we need to redefine the prefix - to take the
							// prefixPerEntry from the base and to move it to
							// the prefix
							if (prefix.length() == 1 && prefixPerEntry == prefix.charAt(0))
								base = transliterated;
							else if (prefix.charAt(k - 1) == prefixPerEntry) {
								base = transliterated.substring(k - 1);
								prefix = transliterated.substring(0, k - 1);
							} else
								break;

							int prefixListSize = 0;

							try {
								prefixListSize = Data.getPrefixes(prefix);
							} catch (Exception e2) {
								System.out
										.println("XMLMorphAnalyzer:analyzeBase - Exception while getting prefixes list for word="
												+ base + "and prefix=" + prefix);
								e2.printStackTrace();
							}
							// System.out.println("prefixListSize =" +
							// prefixListSize);

							for (int j = 0; j < prefixListSize; j++) {
								pr = new PrefixRecord();
								pr = Data.analyzePrefixList(j);
								if (validateByRules(base, prefix, inflectionsRecNum, pr)) {
									try {
										foundAnalysis = true;

										TextOutput.buildXMLPrefixOutput(pr, inflectionsRecDB, inflectionsRecNum, createXML,
												Translate.Eng2Heb(base));
									} catch (Exception e3) {
										System.out.println(
												"XMLMorphAnalyzer:analyzeBase - Exception while buildXMLPrefixOutput for word="
														+ base + "and prefix=" + prefix);
										e3.printStackTrace();
									}
								}
							}
						}
					}
				}

			}
		return foundAnalysis;
	}

	/**
	 * This method handles separating the token to prefix + base combinations for
	 * looking in the inflections and prefixes table
	 * 
	 * @param hebWord
	 * @param transliterated
	 * @return
	 * @throws Exception
	 */
	protected boolean analyzeBaseAndPrefixNoPrefixPreEntry(final String hebWord, final String transliterated)
			throws Exception {
		// System.out.println(hebWord);
		boolean foundAnalysis = false;
		boolean currentFoundAnalysis = false;
		// the longest prefix know is 6 chars long
		for (int j = 1; j < 6 && j < transliterated.length(); j++) {
			String prefix = transliterated.substring(0, j);

			// לא תיתכן תחילית שאינה משהוכלב
			if (StringUtils.moshevkaleb(prefix)) {
				String base = transliterated.substring(j);
				currentFoundAnalysis = analyzeBase(base, prefix, transliterated, hebWord);
				if (currentFoundAnalysis)
					foundAnalysis = true;
			} else
				break;
		}

		return foundAnalysis;
	}

	/**
	 * look for the whole token in the inflections table without trying to break it
	 * to prefix + base
	 * 
	 * @param hebWord
	 * @param transliterated
	 * @return
	 * @throws Exception
	 */
	protected boolean analyzeBaseNoPrefix(final String hebWord, final String transliterated) throws Exception {
		boolean foundAnalysis = false;
		char prefixPerEntry = 'u'; // unspecified
		prefixPerEntryExistFlag = false;
		InflectedRecordNum inflectionsRecNum = null;
		DBInflectionsRecord inflectionsRecDB = null;
		ArrayList<?> inflectionsList = null;

		// handle cases where in tokenization stage we can't guess for sure that
		// the token is whole prefix
		// for example for the b in b 20
		if (StringUtils.moshevkaleb(hebWord)) {
			handlePrefix(hebWord);
			// We wouldn't like to get unknown analysis because we found an
			// analysis of stand alone prefix
			foundAnalysis = true;
		}
		int listSize = 0;
		try {
			inflectionsList = Data.getInflections(transliterated);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		listSize = inflectionsList.size();
		for (int i = 0; i < listSize; i++) {
			try {

				inflectionsRecDB = (DBInflectionsRecord) inflectionsList.get(i);
				inflectionsRecNum = Data.Str2NumBeforeValidation(inflectionsRecDB, hebWord);
			} catch (Exception e1) {
				System.out.println(
						"TokenizationParser: Exception occured while analyzeBaseNoPrefix for word=" + transliterated);
				e1.printStackTrace();
			}

			if (!checkEndOfToken(transliterated, hebWord, inflectionsRecDB.getSurface()))
				continue;
			// there are special entries for which we define prefix per
			// entry - we can't define a rule per pos etc for them
			// currently it is implemented only for pronoun

			prefixPerEntry = inflectionsRecDB.getPrefixPerEntry();

			// handle the simple case for which prefixPerEntry='unspecified'
			if (prefixPerEntry == 'u') {
				if (validateByRulesWithoutPrefixes(transliterated, inflectionsRecNum)) {
					foundAnalysis = true;
					try {
						TextOutput.buildXMLOutput(inflectionsRecDB, inflectionsRecNum, createXML, hebWord);
					} catch (UnsupportedEncodingException e2) {
						System.out.println(
								"XMLAnalyzer:handlePrefix UnsupportedEncodingException occured while buildXMLOutput for word="
										+ transliterated);
						e2.printStackTrace();
					} catch (JAXBException e2) {
						System.out.println("XMLAnalyzer:handlePrefix JAXBException occured while buildXMLOutput for word="
								+ transliterated);
						e2.printStackTrace();
					}
				}
				// if it is a pronoun with prefixPerEntry!='unspecified' - we
				// mark it - in order not to check again and it will be handled
				// in the method which handles token which is consist of prefix
				// + base (analyseBase)
				// we run on all the possible analysis and if we already found
				// one for which prefixPerEntry!='unspecified'
				// we want to keep this information for method analyseBase
			} else if (!prefixPerEntryExistFlag)
				prefixPerEntryExistFlag = true;
		}

		return (foundAnalysis);
	}

	/**
	 * Check if the token is a foreign word
	 * 
	 * @param hebWord
	 * @return
	 * @throws Exception
	 */
	protected boolean analyzeForeign(String hebWord) throws Exception {
		boolean isForeign = false;

		isForeign = StringUtils.analyzeForeign(hebWord);
		if (isForeign) {
			try {
				ENUM_OUTPUT_PATTERN outputPattern = ENUM_OUTPUT_PATTERN.FOREIGN;
				TextOutput.buildSimpleXMLAnalysis(outputPattern, createXML, hebWord, -1);
			} catch (JAXBException e1) {
				System.out.println("XMLAnalyzer:analyzeForeign JAXBException occured  for hebWord=" + hebWord);
				e1.printStackTrace();
			}
		}
		return isForeign;
	}

	/**
	 * Check if the token is a single lettre with dot (used for proper names)
	 * 
	 * @param hebWord
	 * @return
	 * @throws Exception
	 */
	protected boolean analyzeHebrewDotSingleLetter(String hebWord) throws Exception {
		boolean isHebrewSingleLetter = false;
		ENUM_OUTPUT_PATTERN outputPattern = null;
		int hebWordLen = hebWord.length();
		if (hebWordLen == 2 && hebWord.charAt(1) == '.') {

			try {
				isHebrewSingleLetter = StringUtils.analyzeHebrewSingleLetter(hebWord.substring(0, 1));
			} catch (Exception e) {
				System.out.println("XMLAnalyzer:analyzeNumbers Exception occured  for hebWord=" + hebWord);
				e.printStackTrace();
			}
			if (isHebrewSingleLetter) {
				try {
					outputPattern = ENUM_OUTPUT_PATTERN.HEBREWLETTER;
					TextOutput.buildSimpleXMLAnalysis(outputPattern, createXML, hebWord, -1);
				} catch (JAXBException e1) {
					System.out.println("XMLAnalyzer:analyzeNumbers JAXBException occured  for hebWord=" + hebWord);
					e1.printStackTrace();
				}
			}
		}
		return isHebrewSingleLetter;
	}

	/**
	 * Check if the token is a single letter
	 * 
	 * @param hebWord
	 * @return
	 * @throws Exception
	 */
	protected boolean analyzeHebrewSingleLetter(String hebWord) throws Exception {
		boolean isHebrewSingleLetter = false;
		ENUM_OUTPUT_PATTERN outputPattern = null;
		try {
			isHebrewSingleLetter = StringUtils.analyzeHebrewSingleLetter(hebWord);
		} catch (Exception e) {
			System.out.println("XMLAnalyzer:analyzeNumbers Exception occured  for hebWord=" + hebWord);
			e.printStackTrace();
		}
		if (isHebrewSingleLetter) {
			try {
				outputPattern = ENUM_OUTPUT_PATTERN.HEBREWLETTER;
				TextOutput.buildSimpleXMLAnalysis(outputPattern, createXML, hebWord, -1);
			} catch (JAXBException e1) {
				System.out.println("XMLAnalyzer:analyzeNumbers JAXBException occured  for hebWord=" + hebWord);
				e1.printStackTrace();
			}
		}
		return isHebrewSingleLetter;
	}

	/**
	 * Check if the token is a number expression
	 * 
	 * @param hebWord
	 * @return
	 * @throws Exception
	 */
	protected boolean analyzeNumberExpression(String hebWord) throws Exception {
		boolean isNumberExpression = false;
		ENUM_OUTPUT_PATTERN outputPattern = null;

		try {
			outputPattern = StringUtils.analyzeNumberExpression(hebWord);
			if (outputPattern != ENUM_OUTPUT_PATTERN.UNSPECIFIED)
				isNumberExpression = true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (isNumberExpression) {
			try {
				TextOutput.buildSimpleXMLAnalysis(outputPattern, createXML, hebWord, -1);
			} catch (JAXBException e1) {
				System.out.println("XMLAnalyzer:analyzePunctuations JAXBException occured  for hebWord=" + hebWord);
				e1.printStackTrace();
			}
		}
		return isNumberExpression;
	}

	/**
	 * Check if the token is a number
	 * 
	 * @param hebWord
	 * @return
	 * @throws Exception
	 */
	protected boolean analyzeNumbers(String hebWord) throws Exception {
		boolean isNumber = false;
		ENUM_OUTPUT_PATTERN outputPattern = null;
		try {
			isNumber = StringUtils.analyzeNumbers(hebWord);
		} catch (Exception e) {
			System.out.println("XMLAnalyzer:analyzeNumbers Exception occured  for hebWord=" + hebWord);
			e.printStackTrace();
		}
		if (isNumber) {
			try {
				outputPattern = ENUM_OUTPUT_PATTERN.LITERAL_NUMBERS;
				TextOutput.buildSimpleXMLAnalysis(outputPattern, createXML, hebWord, -1);
			} catch (JAXBException e1) {
				System.out.println("XMLAnalyzer:analyzeNumbers JAXBException occured  for hebWord=" + hebWord);
				e1.printStackTrace();
			}
		}
		return isNumber;
	}

	/**
	 * This method is handling tokens containing ' or '' it get combination of
	 * prefix and base and looks for the base in the Gimatria table - it is similar
	 * to analyzeBase Analyze base look for the base in the inflections table
	 * 
	 * @param base
	 * @param prefix
	 * @return
	 */
	protected boolean analyzePrefixGimatriaAndInvertedCommas(String base, String prefix) {

		boolean foundAnalysis = false;
		PrefixRecord pr = null;
		ENUM_OUTPUT_PATTERN outputPattern = ENUM_OUTPUT_PATTERN.GEMATRIA;
		DBInflectionsRecord inflectionRecDB = new DBInflectionsRecord();
		InflectedRecordNum inflectionRecNum = new InflectedRecordNum();

		int gimatriaVal = -1;
		try {
			gimatriaVal = Data.getGimatrias(base);
		} catch (Exception e2) {
			System.out.println(
					"XMLAnalyzer:analyzePrefixGimatriaAndInvertedCommas Exception occured while getting Gimatria for base="
							+ base + " and prefix=" + prefix);
			e2.printStackTrace();
		}
		// ///////////////////////////////////////////////////////////////
		// The base was found in the Gimatria table
		// ///////////////////////////////////////////////////////////////
		if (gimatriaVal != -1) {
			inflectionRecNum.setOutputPattern(outputPattern);
			inflectionRecDB.setValue(String.valueOf(gimatriaVal));
			// System.out.println("gimatriaVal="+ gimatriaVal);
			if (prefix.length() == 1 && prefix.charAt(0) == 'h') {
				foundAnalysis = true;
				inflectionRecNum.setHAttribute(ENUM_HATTRIBUTE.BASE_DEFINITNESS_TRUE_TRUE);
				try {
					TextOutput.buildXMLPrefixOutput(pr, inflectionRecDB, inflectionRecNum, createXML, base);

				} catch (Exception e1) {
					System.out.println(
							"XMLAnalyzer:analyzePrefixGimatriaAndInvertedCommas Exception occured while buildXMLPrefixOutput for base="
									+ base + " and prefix=" + prefix);
					e1.printStackTrace();
				}
			} else {
				if (prefix.endsWith("h")) {
					prefix = prefix.substring(0, prefix.length() - 1);
					inflectionRecNum.setHAttribute(ENUM_HATTRIBUTE.BASE_DEFINITNESS_TRUE_TRUE);
				}
				int prefixListSize = 0;
				try {
					prefixListSize = Data.getPrefixes(prefix);
				} catch (Exception e1) {
					System.out.println(
							"XMLAnalyzer:analyzePrefixGimatriaAndInvertedCommas Exception occured while getting prefixes for base="
									+ base + " and prefix=" + prefix);
					e1.printStackTrace();
				}
				// ////////////////////////////////////////////////////
				// The prefix was found in the prefix table
				// ////////////////////////////////////////////////////////
				if (prefixListSize > 0)
					for (int j = 0; j < prefixListSize; j++) {
						foundAnalysis = true;
						pr = new PrefixRecord();
						pr = Data.analyzePrefixList(j);
						try {

							TextOutput.buildXMLPrefixOutput(pr, inflectionRecDB, inflectionRecNum, createXML, base);

						} catch (Exception e) {
							System.out.println(
									"XMLAnalyzer:analyzePrefixGimatriaAndInvertedCommas Exception occured while buildXMLPrefixOutput for base="
											+ base + " and prefix=" + prefix);
							e.printStackTrace();
						}
					}
			}
		}
		return foundAnalysis;
	}

	/**
	 * Check if the token is a punctuation
	 * 
	 * @param hebWord
	 * @return
	 * @throws Exception
	 */
	protected boolean analyzePunctuations(String hebWord) throws Exception {
		ENUM_OUTPUT_PATTERN outputPattern = null;
		boolean isPunctuation = false;

		isPunctuation = StringUtils.analyzePunctuations(hebWord);

		if (isPunctuation) {
			try {
				outputPattern = ENUM_OUTPUT_PATTERN.PUNCTUATION;
				TextOutput.buildSimpleXMLAnalysis(outputPattern, createXML, hebWord, -1);
			} catch (JAXBException e1) {
				System.out.println("XMLAnalyzer:analyzePunctuations JAXBException occured  for hebWord=" + hebWord);
				e1.printStackTrace();
			}
		}
		return isPunctuation;
	}

	/**
	 * Check if the token is a URL (link or mail)
	 * 
	 * @param hebWord
	 * @return
	 * @throws Exception
	 */
	protected boolean analyzeURL(String hebWord) throws Exception {
		boolean isURL = false;
		ENUM_OUTPUT_PATTERN outputPattern = null;
		isURL = StringUtils.analyzeURL(hebWord);
		if (isURL) {
			try {
				outputPattern = ENUM_OUTPUT_PATTERN.URL;
				TextOutput.buildSimpleXMLAnalysis(outputPattern, createXML, hebWord, -1);
			} catch (JAXBException e1) {
				System.out.println("XMLAnalyzer:analyzeURL Exception occured  for hebWord=" + hebWord);
				e1.printStackTrace();
			}
		}
		return isURL;
	}

	/**
	 * This method handling tokens containg ' or "
	 * 
	 * מטפל בקיצורים וראשי תיבות כמו ג'ורג' וג'ורג' בשתל"ח בתשל"ח
	 * 
	 * @param hebWord
	 */

	protected boolean apostropheInvertedCommasHandling(String hebWord) {
		boolean foundAnalysis = false;
		String transliterated = Translate.Heb2Eng(hebWord);
		int gimatriaVal = -1;
		if (transliterated.indexOf("\'") != -1 && transliterated.indexOf("\"") != -1)
			return foundAnalysis;
		// ///////////////////////////////////////////////////////////
		// טיפול בגרש
		// //////////////////////////////////////////////////////////
		// handle ג'ורג' וג'ורג'
		if (transliterated.endsWith("\'")) {
			// analyze the whole token
			try {
				gimatriaVal = Data.getGimatrias(transliterated);
				// /////////////////////////////////////////////////
				// Gimatria analysis
				// /////////////////////////////////////////////////
				if (gimatriaVal != -1) {
					// look for the word with the apostrophe at the end
					foundAnalysis = true;

					createXML.createNumeralAnalysis("", null, null, null, "", "", "", String.valueOf(gimatriaVal), "", "",
							"", "", "gematria", "", "", "", "", "");
					// ///////////////////////////////////////////////
					// Non Gimatria analysis
					// /////////////////////////////////////////////
				}
				// ייתן לב' ניתוח של גימטריא וגם של יום ב'
				// else {
				foundAnalysis = analyzeBaseNoPrefix(hebWord, transliterated) | analyzeBaseAndPrefix(hebWord, transliterated)
						| analyzeAcronymsBaseAndPrefix(transliterated);
				// }
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			// ///////////////////////////////////////////////////////////
			// טיפול בגרשיים
			// //////////////////////////////////////////////////////////
			// handle תשל"ח בתש"ל"ח
		} else if (transliterated.indexOf("\"") == (transliterated.length() - 2)) {
			try {
				gimatriaVal = Data.getGimatrias(transliterated);
				if (gimatriaVal != -1) {
					foundAnalysis = true;
					// //////////////////////////////////////////////////////////
					// The whole token was found in Gimatria table
					// /////////////////////////////////////////////////////////
					// create gimatria analysis
					createXML.createNumeralAnalysis("", null, null, null, "", "", "", String.valueOf(gimatriaVal), "", "",
							"", "", "gematria", "", "", "", "", "");
					// /////////////////////////////////////////////////////////////////
					// The whole token was ***not found*** in Gimatria table
					// ////////////////////////////////////////////////////////////////
				} else {
					// check if it a combination of prefix + gimatria
					foundAnalysis = foundAnalysis || analyzeAcronymsBaseAndPrefix(transliterated);
				}

				// הוספתי את 2 הפונקציות הבאות כדי שנקבל גם ניתוח גימטריא וגם לא
				// גימטריא למשל
				// עבור ש"ח
				// בפונקציות אלו לא מתבצע חיפוש בטבלת גימטריא אלא רק בטבלאות
				// התחילית והנטיות
				// check if the whole token is in the inflections table
				// check if it a combination of prefix + inflection
				// need to keep | and not || because we want all options to be
				// checked in any case
				foundAnalysis = foundAnalysis | analyzeBaseNoPrefix(hebWord, transliterated)
						| analyzeBaseAndPrefix(hebWord, transliterated);

			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return foundAnalysis;
	}

	/**
	 * handle cases like רצ סירופ where the letter at the end of the word can be
	 * sofit or not sofit and we want to keep it as it is originally written This
	 * problem occures because we don't differentiate between sofit and not sofit in
	 * the transliteration
	 * 
	 * @param transliterated
	 * @param hebWord
	 * @param inflectionsHebWord
	 * @return
	 */
	protected boolean checkEndOfToken(String transliterated, String hebWord, String inflectionsHebWord) {
		boolean rt = false;
		if (inflectionsHebWord != null && inflectionsHebWord.length() > 0) {
			try {
				// System.out.println(inflectionsHebWord);
				inflectionsHebWord = URLDecoder.decode(inflectionsHebWord, "UTF-8");
				// System.out.println(inflectionsHebWord);
			} catch (UnsupportedEncodingException e1) {
				// // TODO Auto-generated catch block
				e1.printStackTrace();
			}
			char endChar = transliterated.charAt(transliterated.length() - 1);
			if (endChar == 'c' || endChar == 'n' || endChar == 'p' || endChar == 'k') {
				if (hebWord.equals(inflectionsHebWord))
					rt = true;
			} else
				rt = true;
			;
		}
		return rt;

	}

	/*
	 * (non-Javadoc) This method relates to the SAX parsing of the XML (the output
	 * of the tokenizer)
	 * 
	 * @see org.xml.sax.helpers.DefaultHandler#endElement(java.lang.String,
	 * java.lang.String, java.lang.String)
	 */
	@Override
	public void endElement(String namespaceURI, String localName, String rowName) throws SAXException {

		if (rowName.equals("paragraph")) {
			createXML.finalizeParagraph();

		} else if (rowName.equals("sentence")) {
			createXML.finalizeSentence();
		}
	}

	/**
	 * This function handles cases of a combination of token and " and a token - we
	 * identify these cases in tokenization stage we set prefix= in the surface
	 * attribute - we know for sure that we have three tokens here we also know that
	 * the first must be prefix
	 * 
	 * @param hebPrefix
	 */
	protected void handlePrefix(String hebPrefix) {
		ENUM_OUTPUT_PATTERN outputPattern = null;

		int prefixListSize = 0;
		PrefixRecord pr = null;
		DBInflectionsRecord inflectionRecDB = null;
		InflectedRecordNum inflectionRecNum = new InflectedRecordNum();
		// int equallIndex = hebPrefix.indexOf("=");
		// hebPrefix = hebPrefix.substring(equallIndex + 1);
		String prefix = Translate.Heb2Eng(hebPrefix);
		if (prefix.length() == 1 && prefix.charAt(0) == 'h') {
			try {
				outputPattern = ENUM_OUTPUT_PATTERN.PREFIXES;
				inflectionRecNum.setOutputPattern(outputPattern);
				TextOutput.buildXMLPrefixOutput(null, inflectionRecDB, inflectionRecNum, createXML, hebPrefix);

			} catch (Exception e) {
				System.out.println(
						"XMLAnalyzer:handlePrefix Exception occured while buildXMLPrefixOutput for prefix=" + prefix);
				e.printStackTrace();
			}
		} else {
			if (prefix.endsWith("h")) {
				prefix = prefix.substring(0, prefix.length() - 1);
				ENUM_HATTRIBUTE hAttributei = ENUM_HATTRIBUTE.PREFIX_STANDALONE_H;
				inflectionRecNum.setHAttribute(hAttributei);
			}
			try {
				prefixListSize = Data.getPrefixes(prefix);
			} catch (Exception e1) {
				System.out
						.println("XMLAnalyzer:handlePrefix Exception occured while getting prefixes for prefix=" + prefix);
				e1.printStackTrace();
			}
			if (prefixListSize > 0) {
				for (int j = 0; j < prefixListSize; j++) {
					// foundBase = true;
					outputPattern = ENUM_OUTPUT_PATTERN.PREFIXES;
					inflectionRecNum.setOutputPattern(outputPattern);
					pr = new PrefixRecord();
					pr = Data.analyzePrefixList(j);
					boolean isDefiniteArticle = pr.isDefiniteArticleTag();
					boolean prepKAF = pr.isPrepKAF();
					boolean prepLAMED = pr.isPrepLAMED();
					boolean prepMEM = pr.isPrepMEM();
					// ל-100 לא רוצים פעמיים אנליזה של ל
					if ((!isDefiniteArticle) && ((!prepKAF) || (!prepMEM) || (!prepLAMED)))
						try {
							TextOutput.buildXMLPrefixOutput(pr, inflectionRecDB, inflectionRecNum, createXML, "");

						} catch (Exception e) {
							System.out
									.println("XMLAnalyzer:handlePrefix Exception occured while buildXMLPrefixOutput for prefix="
											+ prefix);
							e.printStackTrace();
						}
				}
			}
		}
	}

	/**
	 * This method handles the case in which no analysis was found
	 * 
	 * @param hebWord
	 * @param transliterated
	 * @return
	 * @throws Exception
	 */
	protected boolean noEntryInInflections(final String hebWord, final String transliterated) throws Exception {

		createXML.createUnknownAnalysis(hebWord, transliterated);
		// System.out.println("No entry=" + hebWord);

		return true;
	}

	public void parse(InputStream in) {
		try {
			_parser.parse(in, this);
		} catch (SAXParseException spe) {
			System.err.println("Input file is not a tokenized XML file by Mila standards");
			// spe.printStackTrace();
		} catch (SAXException se) {
			se.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}

	/**
	 * This is an important method because this is the intesection from which all
	 * options to analyze are checked
	 * 
	 * @param hebWord
	 * @throws IOException
	 * @throws Exception
	 */
	public void readInput(String hebWord) throws IOException, Exception {
		// if (hebWord.equals("היוסדה"))
		// System.out.println(hebWord);
		if (!analyzeURL(hebWord))
			if (!analyzeForeign(hebWord)) {
				if (!analyzePunctuations(hebWord) && !analyzeNumbers(hebWord) && !analyzeNumberExpression(hebWord)) {
					String transliterated = Translate.Heb2Eng(hebWord);
					if (StringUtils.gimatriaPossibility(hebWord)) {
						if (!apostropheInvertedCommasHandling(hebWord))
							noEntryInInflections(hebWord, transliterated);
					} else {
						// System.out.println(hebWord);
						// it is important to be & and not && because we
						// want both to be done anyway
						if (!analyzeBaseNoPrefix(hebWord, transliterated) & !analyzeBaseAndPrefix(hebWord, transliterated)
								& !analyzeHebrewDotSingleLetter(hebWord) & !analyzeHebrewSingleLetter(hebWord))
							noEntryInInflections(hebWord, transliterated);
					}

				}
			}
	}

	/*
	 * (non-Javadoc) This method relates to the SAX parsing of the XML (the output
	 * of the tokenizer)
	 * 
	 * @see org.xml.sax.helpers.DefaultHandler#startElement(java.lang.String,
	 * java.lang.String, java.lang.String, org.xml.sax.Attributes)
	 */
	@Override
	public void startElement(String namespaceURI, String localName, String rowName, Attributes attributes)
			throws SAXException {
		// Create XML outputFile
		// Handle paragraph
		// if (rowName.equals("article")) {
		// createXML.createParagraph();
		// }
		if (rowName.equals("paragraph")) {
			// createXML.finalizeSentence();
			// createXML.finalizeParagraph();
			createXML.createParagraph();

			// handle sentence
		} else if (rowName.equals("sentence")) {
			// if (firstTimeSentence) {
			// createXML.createSentence();
			// firstTimeSentence = false;
			// } else {
			// createXML.finalizeSentence();
			createXML.createSentence();
			// }
			// handle token
		} else if (rowName.equals("token")) {
			// tokensCount++;
			String hebWord = attributes.getValue("surface");
			// System.out.println(hebWord);
			// טיפול בסימונים - שמוסרים לנו אינפורמציה שנאספה בזמן טוקניזציה
			if (hebWord.startsWith("prefix=")) {
				if (hebWord.indexOf("token=") != -1) {
					boolean returnValue = false;
					int prefixEquallIndex = hebWord.indexOf("=");
					int tokenEqualIndex = hebWord.lastIndexOf("=");
					int spaceIndex = hebWord.lastIndexOf(" ");
					String hebPrefix = hebWord.substring(prefixEquallIndex + 1, spaceIndex);
					String hebBase = hebWord.substring(tokenEqualIndex + 1);
					// טפול במקרה של יידוע - יש להעביר את הה' לתמנית
					// מהתחילית
					if (hebPrefix.endsWith("ה")) {
						hebPrefix = (hebPrefix.replace('ה', ' ')).trim();
						hebBase = "ה" + hebBase;
					}
					String hebTokenWithoutSigning = hebPrefix + hebBase;
					createXML.createToken(hebTokenWithoutSigning);
					String transliteratedHebBase = Translate.Heb2Eng(hebPrefix + hebBase);
					if (hebPrefix.length() > 0)
						returnValue = analyzeBase(Translate.Heb2Eng(hebBase), Translate.Heb2Eng(hebPrefix),
								Translate.Heb2Eng(hebTokenWithoutSigning), hebWord);
					else
						try {
							returnValue = analyzeBaseNoPrefix(hebPrefix + hebBase, transliteratedHebBase);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					if (!returnValue)
						try {
							noEntryInInflections(hebPrefix + hebBase, transliteratedHebBase);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					// טיפול במקרה של תחילית + בסיס מחוברים - מזוהה עוד בשלב
					// טוקניזציה ומעבירים רמזים
				} else {
					int equallIndex = hebWord.indexOf("=");
					String hebPrefix = hebWord.substring(equallIndex + 1);
					createXML.createToken(hebPrefix);
					handlePrefix(hebPrefix);
				}
				// //////////////////////////////////////////////////////////////
				// there is not prefix= in the surface attribute
				// טיפול במקרים הרגילים - אין רמזים משלב טוקניזציה
				// //////////////////////////////////////////////////////////////
			} else { //
				createXML.createToken(hebWord);
				try {
					readInput(hebWord);
				} catch (IOException e1) {
					System.out.println("XMLMorphAnalyzer:startElemen(element) - IOException while readInput");
					e1.printStackTrace();
				} catch (Exception e1) {
					System.out.println("XMLMorphAnalyzer:startElemen(element) - Exception while readInput");
					e1.printStackTrace();
				}
			} //
			createXML.finalizeToken();
		}
	}

	// ------------------------------------------------------------------------------------------------------------------
	// MODIFED VERSION BY ALON
	/**
	 * This method contains the rules for concatenation a prefix to base The
	 * decision whether the concatenation is valid depends on the part of speech of
	 * the base and more attributes
	 * 
	 * @param base
	 * @param prefix
	 * @param inflectedRecordNum
	 * @param pr
	 *           - data structure of the current prefix
	 * @return
	 */
	protected boolean validateByRules(final String base, final String prefix,
			final InflectedRecordNum inflectedRecordNum, final PrefixRecord pr) {

		ENUM_TENSE tensei;
		// ENUM_HATTRIBUTE haaributei = inflectedRecordNum.getHAttribute();
		ENUM_POS posi = inflectedRecordNum.getPos();
		ENUM_SUFFIX_FUNCTION suffixFunctioni = inflectedRecordNum.getSuffixFunction();
		ENUM_STATUS constructi = inflectedRecordNum.getStatus();
		ENUM_HATTRIBUTE hAttributei = inflectedRecordNum.getHAttribute();
		char prefixPerEntry = inflectedRecordNum.getPrefixPerEntry();
		int type = inflectedRecordNum.getType();

		boolean definiteArticleTag = pr.isDefiniteArticleTag();
		boolean adverbKAF = pr.isAdverbKAF();
		boolean subConOrRelSHIN = pr.isSubConOrRelSHIN();
		boolean tempSubConKAFSHIN = pr.isTempSubConKAFSHIN();
		boolean tempSubConBETSHIN = pr.isTempSubConBETSHIN();
		boolean tempSubConMEMSHIN = pr.isTempSubConMEMSHIN();
		boolean tempSubConLAMEDKAFSHIN = pr.isTempSubConLAMEDKAFSHIN();
		boolean relativizerTag = pr.isRelativizerTag();
		boolean subordinatingConjunctionTag = pr.isSubordinatingConjunctionTag();
		boolean temporalSubConjTag = pr.isTemporalSubConjTag();
		boolean prepBET = pr.isPrepBET();
		boolean prepKAF = pr.isPrepKAF();
		boolean prepLAMED = pr.isPrepLAMED();
		boolean prepMEM = pr.isPrepMEM();
		boolean prefPartUnit = pr.isPrefPartUnit();
		boolean prepositionTag = pr.isPrepositionTag();
		boolean conjunctionTag = pr.isConjunctionTag();

		// /////////////////////////////////////////////////////////////////////////////////////////
		// // חוקים מנוונים
		// /////////////////////////////////////////////////////////////////////////////////////////
		if (definiteArticleTag) {
			// ה' הידיעה לא תופיע לפני צורת נסמך - בילדת
			if ((constructi == ENUM_STATUS.CONSTRUCT_TRUE)
					// 'ה' הידיעה לא תופיע לפני צירוף קנייני - חוק זה חשוב עבור
					// ה'
					// חבויה למשל כילדייך
					|| (suffixFunctioni == ENUM_SUFFIX_FUNCTION.SUFFIX_FUNCTION_POSSESSIVE)
					|| (suffixFunctioni == ENUM_SUFFIX_FUNCTION.SUFFIX_FUNCTION_PRONOMIAL)) {
				return false;
			}
		}

		// ///////////////////////////////////////////////////////////////////////////////
		// ////// סוף חוקים מנוונים
		// ////////////////////////////////////////////////////////////////////////////

		// ארגון השומר חייב להיות מיודע - אסור שיופיע בשומר ללא יידוע רק
		// ב(ה)שומר
		if (!definiteArticleTag && (posi == ENUM_POS.PROPERNAME)
				&& (hAttributei == ENUM_HATTRIBUTE.BASE_DEFINITNESS_REQUIRED_TRUE)) {
			return false;
		}

		// תחילית ואם לאלהקים - יש שם פרטי קים - אסור שיופיע מיודע אם מצטרף אליו
		// תחילית ואם לא
		if (hAttributei == ENUM_HATTRIBUTE.BASE_DEFINITNESS_REQUIRED_FALSE) {
			return false;
		}

		// /////////////////////////////////////////////////////////////////////////////////
		// ////// ה' חבויה
		// ///////////////////////////////////////////////////////////////////////
		// כדי למנוע הופעת אנליזה ל-בהילד כהילד להילד
		if ((prepBET || prepKAF || prepLAMED) && hAttributei == ENUM_HATTRIBUTE.BASE_DEFINITNESS_TRUE_TRUE) {
			return false;
		}
		// למנוע הופעה כפולה עבור תחיליות שמופיעות עם ה ובלי ה - למשל ש
		// ושה כאשר מצטרפים לש"ע או שם פרטי מיודע

		if (prefPartUnit && hAttributei == ENUM_HATTRIBUTE.BASE_DEFINITNESS_FALSE && definiteArticleTag) {
			return false;
		}

		if (adverbKAF && (posi == ENUM_POS.PROPERNAME
				// || posi == ENUM_POS.VERB
				// ושלכשעה, ושבכשעה
				|| posi == ENUM_POS.ADJECTIVE
				// || posi == ENUM_POS.PRONOUN
				|| posi == ENUM_POS.PREPOSITION || posi == ENUM_POS.ADVERB
				// || posi == ENUM_POS.CONJUNCTION
				// || posi == ENUM_POS.INTERJECTION
				// || posi == ENUM_POS.INTERROGATIVE
				|| posi == ENUM_POS.NEGATION || posi == ENUM_POS.PARTICIPLE)) {
			return false;
		}
		// / POS
		switch (posi) {
		// /////////////////////////////////////////////////////////////////////////
		// Existential
		// /////////////////////////////////////////////////////////////////////////
		// כמת ישיי יופיע רק עם תחיליות ו' וש
		case EXISTENTIAL: {
			if (hAttributei == ENUM_HATTRIBUTE.BASE_DEFINITNESS_TRUE_TRUE) {
				return false;
			}
			if ((subordinatingConjunctionTag && relativizerTag) || conjunctionTag) {
				return true;
			}
			return false;
		}
		// ///////////////////////////////////////////////////////////////////////
		// למנוע ל+לגביך
		// - נחליף את החוק הבא בחוקים יותר מעודנים
		// צריך עוד לבדוק אפשרות זאת
		/***************************************************************
		 * case PREPOSITION: //if (posi == ENUM_POS.PREPOSITION) { if(prepositionTag ||
		 * adverbKAF) { return false; } return true; }
		 ***************************************************************/

		// /////////////////////////////////////////////////////////////////////////
		// Pronoun
		// /////////////////////////////////////////////////////////////////////////

		// למנוע הופעת ניתוח כינוי גוף ל- בהם, להם - הניתוח צריך להיות
		// של מילת יחס
		// אבל אנחנו רוצים לאפשר בכל"ם עם מספרים נטויים - בשלושתנו
		// בארבעתנו

		// לפי רשימה של נועם - כזה הלזו זהו לא יקבלו בכלם
		// כינויי גוף מסוג שאלה מקבלים בכל"ם עלפ"י העיול הספציפי - לכן
		// נכליל שלא ועבוא עיולים מסויימים נשתמש בשדה
		// תחילית בטבלת inflections
		// לסמן אם מקבל תחילית בכל"ם או לא
		case PRONOUN:
		// if(posi == ENUM_POS.PRONOUN)
		{
			if ((prepLAMED || prepBET || prepMEM || prepKAF || adverbKAF) && (prefixPerEntry == 'u')) {
				return false;
			}
			if (prepBET && prepKAF) {
				return false;
			}
			if (hAttributei == ENUM_HATTRIBUTE.UNSPECIFIED && definiteArticleTag) {
				return false;
			}
			if (adverbKAF) {
				return false;
			}
			return true;
		}

		// /////////////////////////////////////////////////////////////////
		// ///////////QUANTIFIER
		// //////////////////////////////////////////////////////////
		case QUANTIFIER:
		// if(posi == ENUM_POS.QUANTIFIER)
		{
			if (type == QUANTIFIER_TYPE_PARTITIVE) {
				if (constructi == ENUM_STATUS.CONSTRUCT_FALSE && !definiteArticleTag) {
					return false;
				}
				return true;
			} else if (definiteArticleTag) {
				return false;
			}
			return true;
		}

		// /////////////////////////////////////////////////////////////////////////
		// / verbs
		// /////////////////////////////////////////////////////////////////////////
		// אנו מעוניינים למנוע הופעה של ב\כ\ל\מ\ש\לכש\ ועוד כתחילית עם
		// ציווי אבל כן נאפשר הופעת ו+ ציווי - ולכי
		case VERB:
		// if(posi == ENUM_POS.VERB)
		{
			tensei = inflectedRecordNum.getTense();
			if (tensei == ENUM_TENSE.IMPERATIVE
					&& (prepLAMED || prepBET || prepMEM || prepKAF || adverbKAF || subConOrRelSHIN || tempSubConKAFSHIN
							|| tempSubConBETSHIN || tempSubConMEMSHIN || tempSubConLAMEDKAFSHIN)) {
				return false;
			}
			// לא יופיעו בכל"ם לפני פעלים בעבר ועתיד
			if (prepositionTag && (tensei == ENUM_TENSE.PAST || tensei == ENUM_TENSE.FUTURE)) {
				return false;
			}
			// למנוע ניתוח של כ+מלאך מ+מלאך ל+מלאך ב+מלאך אבל לאפשר ניתוח של
			// למלאך במלאך כלומר תחיליות ב ול יופיעו
			// כחלק מהצורה ולא כתחיליות כי מייצרים אותן
			// לאפשר ניתוח של בלהלביש ובלשמור
			if (prepositionTag && tensei == ENUM_TENSE.INFINITIVE
					&& suffixFunctioni == ENUM_SUFFIX_FUNCTION.SUFFIX_FUNCTION_ACCUSATIVE_OR_NOMINATIVE
					&& base.charAt(0) != 'l' && base.charAt(0) != 'b') {
				return false;
			}
			// למנוע ניתוח של בבשמור ממשמור ללשמור ככשמור
			if (tensei == ENUM_TENSE.INFINITIVE) {
				if ((prepBET && base.charAt(0) == 'b') || (prepKAF && base.charAt(0) == 'k')
						|| (prepMEM && base.charAt(0) == 'm') || (prepLAMED && base.charAt(0) == 'l')) {

					return false;
				}
			}
			if (adverbKAF) {
				return false;
			}
			return true;
		}
		// מקור מוחלט לא מקבל מילות יחס
		case INDEPENDENTINFINITIVE:
		// if(posi == ENUM_POS.INDEPENDENTINFINITIVE)
		{
			if (prepositionTag || adverbKAF) {
				return false;
			}
			return true;
		}

		// //////////////////////////////////////////////////////////////////////
		// / (conjunction) מילת חיובר
		// /////////////////////////////////////////////////////////////////////

		case CONJUNCTION:
		// if(posi == ENUM_POS.CONJUNCTION)
		{
			if (prepositionTag) {
				return false;
			}
			if (adverbKAF) {
				return false;
			}
			return true;
		}
		// //////////////////////////////////////////////////////////////////////
		// / (interjection) מילת קריאה
		// /////////////////////////////////////////////////////////////////////
		case INTERJECTION:
		// if(posi == ENUM_POS.INTERJECTION)
		{
			if (definiteArticleTag) {
				return false;
			}
			if (subConOrRelSHIN || tempSubConKAFSHIN || tempSubConMEMSHIN || tempSubConLAMEDKAFSHIN) {
				return false;
			}
			if (relativizerTag || subordinatingConjunctionTag || temporalSubConjTag) {
				return false;
			}
			if (prepositionTag) {
				return false;
			}
			if (adverbKAF) {
				return false;
			}
			return true;
		}
		// //////////////////////////////////////////////////////////////////////
		// / (interogative) מילת שאלה
		// /////////////////////////////////////////////////////////////////////
		case INTERROGATIVE:
		// if((posi == ENUM_POS.INTERROGATIVE) && (type !=
		// INTERROGATIVE_TYPE_PRONOUN))
		{
			if (adverbKAF) {
				return false;
			}
			if (type != INTERROGATIVE_TYPE_PRONOUN) {
				if (subConOrRelSHIN || tempSubConKAFSHIN || tempSubConMEMSHIN || tempSubConLAMEDKAFSHIN) {
					return false;
				}
				if (prepositionTag) {
					return false;
				}

				return true;
			}
		}

		// /////////////////////////////////////////////////////////////////////////////
		// ///////////// שם פרטי
		// ////////////////////////////////////////////////////////////////////////////
		// למנוע הופעה של שם פרטי עם תחילית בש - כמו בשדליה
		case PROPERNAME:
		// if(posi == ENUM_POS.PROPERNAME)
		{
			if (tempSubConBETSHIN) {
				return false;
			}
			return true;
		}

		// //////////////////////////////////////////////////////////////////////////////
		// תואר הפועל
		// ///////////////////////////////////////////////////////////////////////////////
		case ADVERB:
		// if(posi == ENUM_POS.ADVERB)
		{
			if (prepositionTag) {
				return false;
			}
			return true;
		}

		// /////////////////////////////////////////////////////////////////////

		// למנוע כ+אל ב+יש
		case MODAL:
		// if(posi == ENUM_POS.MODAL)
		{
			if (prepositionTag) {
				return false;
			}
			return true;
		}

		// לפי בקשת מני, אלון לא מסכים - נתווכח אח"כ
		case COPULA:
		// if(posi == ENUM_POS.COPULA)
		{
			if (prepositionTag) {
				return false;
			}
			return true;
		}

		// participleType handling
		case PARTICIPLE:
		case PASSIVEPARTICIPLE:
		// if(posi == ENUM_POS.PARTICIPLE || posi == ENUM_POS.PASSIVEPARTICIPLE)
		{
			if (!conjunctionTag && type == PARTICIPLE_TYPE_ADJECTIVE) {
				return false;
			}
			if (!conjunctionTag && !subConOrRelSHIN && type == PARTICIPLE_TYPE_VERB) {
				return false;
			}
			return true;
		}

		// /////////////////////////////////////////////////////////////////
		// ///////////PREPOSITION
		// //////////////////////////////////////////////////////////
		// לא לאפשר ש+לי - מותר רק שלי
		// כן לאפשר ש+בי - לכן אי אפשר חוק כללי וצריך לבדוק שמדובר בעיול
		// ספציפי
		case PREPOSITION:
		// if(posi == ENUM_POS.PREPOSITION
		// && suffixFunctioni == ENUM_SUFFIX_FUNCTION.SUFFIX_FUNCTION_PRONOMIAL)
		{
			// ///////////////////////////////////////////////////////////////////////
			// למנוע ל+לגביך
			// - נחליף את החוק הבא בחוקים יותר מעודנים
			// צריך עוד לבדוק אפשרות זאת
			if (prepositionTag || adverbKAF) {
				return false;
			}
			if (suffixFunctioni == ENUM_SUFFIX_FUNCTION.SUFFIX_FUNCTION_PRONOMIAL) {
				if (prefix.equals("e") && (base.equals("li") || base.equals("lk") || base.equals("lw") || base.equals("lh")
						|| base.equals("lnw") || base.equals("lkm") || base.equals("lkn") || base.equals("lhm")
						|| base.equals("lhn"))) {
					return false;
				}
			}
		}

		default: {
			return true;
		}
		}
	}

	// ------------------------------------------------------------------------------------------------------------------

	/**
	 * This method contains the rules for concatenation a prefix to base The
	 * decision whether the concatenation is valid depends on the part of speech of
	 * the base and more attributes
	 * 
	 * @param base
	 * @param prefix
	 * @param inflectedRecordNum
	 * @param pr
	 *           - data structure of the current prefix
	 * @return
	 */
	/*
	 * protected boolean validateByRules(final String base, final String
	 * prefix,final InflectedRecordNum inflectedRecordNum, final PrefixRecord pr) {
	 * 
	 * ENUM_TENSE tensei = inflectedRecordNum.getTense(); ENUM_HATTRIBUTE haaributei
	 * = inflectedRecordNum.getHAttribute(); ENUM_POS posi =
	 * inflectedRecordNum.getPos(); ENUM_SUFFIX_FUNCTION suffixFunctioni =
	 * inflectedRecordNum.getSuffixFunction(); ENUM_STATUS constructi =
	 * inflectedRecordNum.getStatus(); ENUM_HATTRIBUTE hAttributei =
	 * inflectedRecordNum.getHAttribute(); char prefixPerEntry =
	 * inflectedRecordNum.getPrefixPerEntry(); int type =
	 * inflectedRecordNum.getType();
	 * 
	 * boolean validate = false; boolean definiteArticleTag =
	 * pr.isDefiniteArticleTag(); boolean defArtHE = pr.isDefArtHE(); // boolean
	 * relHE = pr.isRelHE(); boolean adverbKAF = pr.isAdverbKAF(); boolean
	 * subConOrRelSHIN = pr.isSubConOrRelSHIN(); boolean tempSubConKAFSHIN =
	 * pr.isTempSubConKAFSHIN(); boolean tempSubConBETSHIN =
	 * pr.isTempSubConBETSHIN(); boolean tempSubConMEMSHIN =
	 * pr.isTempSubConMEMSHIN(); boolean tempSubConLAMEDKAFSHIN =
	 * pr.isTempSubConLAMEDKAFSHIN(); boolean relativizerTag =
	 * pr.isRelativizerTag(); boolean subordinatingConjunctionTag =
	 * pr.isSubordinatingConjunctionTag(); boolean temporalSubConjTag =
	 * pr.isTemporalSubConjTag(); boolean prepBET = pr.isPrepBET(); boolean prepKAF
	 * = pr.isPrepKAF(); boolean prepLAMED = pr.isPrepLAMED(); boolean prepMEM =
	 * pr.isPrepMEM(); boolean prefPartUnit = pr.isPrefPartUnit(); boolean
	 * prepositionTag = pr.isPrepositionTag(); boolean conjunctionTag =
	 * pr.isConjunctionTag();
	 * 
	 * if (
	 * 
	 * // ///////////////////////////////////////////////////////////////////////
	 * ////////////////// // // חוקים מנוונים // ////////////////////////////////
	 * ///////////////////////////////////////////////////////// // כל החוקים הבאים
	 * מנוטרלים - לאור העובדה שעבור צורות מיודעות - אנו // מייצרים את הצורה המיודעת
	 * // וטבלת התחיליות לא כוללת את ה' הידיעה // במקרים בהם יש ה' חבויה למשל
	 * ב(ה)בית - יש עדיין להשתמש // בחוקים!!!!!!!!!!!!!!!!!!!!!!!! // /
	 * /////////////////////////////////////////////////////////////////////////
	 * ////////////// // !((definiteArticleTag || defArtHE || relHE) && (posi == //
	 * INTERROGATIVE)) // ה' הידיעה לא תופיע לפני מילת שאלה
	 * 
	 * // && !((definiteArticleTag || defArtHE) && (posi == CONJUNCTION))// ה' //
	 * הידיעה לא תופיע לפני מילת חיבור
	 * 
	 * // && !(definiteArticleTag && posi == INTERJECTION) //ה' הידיעה לא תופיע //
	 * לפני מילת קריאה
	 * 
	 * // && // ה' הידיעה לא תופיע לפני צורת נסמך - בילדת !(definiteArticleTag &&
	 * constructi == ENUM_STATUS.CONSTRUCT_TRUE) // חוק // זה // חשוב // עבור ה'
	 * חבויה // למשל כילדייך
	 * 
	 * // && !(definiteArticleTag && posi != VERB && suffixFunctioni == //
	 * SUFFIX_FUNCTION_POSSESSIVE)
	 * 
	 * // ה' הידיעה לא תופיע לפני צירוף קנייני - חוק זה חשוב עבור ה' // חבויה למשל
	 * כילדייך && !(definiteArticleTag && suffixFunctioni ==
	 * ENUM_SUFFIX_FUNCTION.SUFFIX_FUNCTION_POSSESSIVE)
	 * 
	 * && !(definiteArticleTag && suffixFunctioni ==
	 * ENUM_SUFFIX_FUNCTION.SUFFIX_FUNCTION_PRONOMIAL)
	 * 
	 * // ה' הידיעה לא תופיע לפני פועל - אלא רק עבור צורות שם פועל // && //
	 * !(definiteArticleTag && posi == VERB && (tensei != // TENSE_INFINITIVE))
	 * 
	 * // כינויי גוף לא מקבלים ה' הידיעה // && !(definiteArticleTag && posi ==
	 * PRONOUN)
	 * 
	 * // לפי בקשת מני, אלון לא מסכים - נתווכח אח"כ // && // !(definiteArticleTag &&
	 * posi == COPULA) // && // !(definiteArticleTag && basePronounTypei == //
	 * BASE_PRONOUN_TYPE_INTERROGATIVE) // && // !(definiteArticleTag &&
	 * basePronounTypei == // BASE_PRONOUN_TYPE_RELATIVIZER) // && //
	 * !(definiteArticleTag && posi == ADVERB)
	 * 
	 * // ///////////////////////////////////////////////////////////////////////
	 * //////// // ////// סוף חוקים מנוונים // //////////////////////////////////
	 * //////////////////////////////////////////
	 * 
	 * // ארגון השומר חייב להיות מיודע - אסור שיופיע בשומר ללא יידוע רק // ב(ה)שומר
	 * && !(!definiteArticleTag && (posi == ENUM_POS.PROPERNAME) && (hAttributei ==
	 * ENUM_HATTRIBUTE.BASE_DEFINITNESS_REQUIRED_TRUE))
	 * 
	 * // להקים - יש שם פרטי קים - אסור שיופיע מיודע אם מצטרף אליו // תחילית ואם לא
	 * && !(hAttributei == ENUM_HATTRIBUTE.BASE_DEFINITNESS_REQUIRED_FALSE)
	 * 
	 * // /////////////////////////////////////////////////////////////////////////
	 * // Existential //
	 * ///////////////////////////////////////////////////////////////////////// //
	 * כמת ישיי יופיע רק עם תחיליות ו' וש && !(posi == ENUM_POS.EXISTENTIAL &&
	 * !((subordinatingConjunctionTag && relativizerTag) || conjunctionTag))
	 * 
	 * // למנוע הופעת והיש שהיש && !(hAttributei ==
	 * ENUM_HATTRIBUTE.BASE_DEFINITNESS_TRUE_TRUE && posi == ENUM_POS.EXISTENTIAL)
	 * 
	 * // /////////////////////////////////////////////////////////////////////////
	 * // Pronoun //
	 * /////////////////////////////////////////////////////////////////////////
	 * 
	 * // למנוע הופעת ניתוח כינוי גוף ל- בהם, להם - הניתוח צריך להיות // של מילת יחס
	 * // אבל אנחנו רוצים לאפשר בכל"ם עם מספרים נטויים - בשלושתנו // בארבעתנו // &&
	 * !(prepositionTag // && basePronounTypei == BASE_PRONOUN_TYPE_PERSONAL && //
	 * suffixFunctioni != SUFFIX_FUNCTION_PRONOMIAL) // לפי רשימה של נועם - כזה הלזו
	 * זהו לא יקבלו בכלם // && !(prepositionTag && basePronounTypei == //
	 * BASE_PRONOUN_TYPE_DEMONSTRATIVE) // כינויי גוף מסוג שאלה מקבלים בכל "ם עלפ"י
	 * העיול הספציפי - לכן // נכליל שלא ועבוא עיולים מסויימים נשתמש בשדה // תחילית
	 * בטבלת inflections // לסמן אם מקבל תחילית בכל"ם או לא && !((prepLAMED ||
	 * prepBET || prepMEM || prepKAF || adverbKAF) && posi == ENUM_POS.PRONOUN &&
	 * prefixPerEntry == 'u')
	 * 
	 * && !((prepBET && prepKAF) && posi == ENUM_POS.PRONOUN) && !(posi ==
	 * ENUM_POS.PRONOUN && hAttributei == ENUM_HATTRIBUTE.UNSPECIFIED &&
	 * definiteArticleTag) // && !(prefixPerEntry.equals("unspecified") && posi ==
	 * PRONOUN) // && !(prefix.endsWith(prefixPerEntry) && prepositionTag && //
	 * basePronounTypei == BASE_PRONOUN_TYPE_INTERROGATIVE) // מתנהגים כמו כינויי
	 * גוף שאלה // && !(prefix.endsWith(prefixPerEntry) && prepositionTag && //
	 * basePronounTypei == BASE_PRONOUN_TYPE_PERSONAL) //
	 * ///////////////Quantifier//////////////////////////////////////// &&
	 * !(definiteArticleTag && posi == ENUM_POS.QUANTIFIER && type !=
	 * QUANTIFIER_TYPE_PARTITIVE)
	 * 
	 * // /////////////////////////////////////////////////////////////////////////
	 * // / verbs //
	 * ///////////////////////////////////////////////////////////////////////// //
	 * אנו מעוניינים למנוע הופעה של ב\כ\ל\מ\ש\לכש\ ועוד כתחילית עם // ציווי אבל כן
	 * נאפשר הופעת ו+ ציווי - ולכי && !(posi == ENUM_POS.VERB && tensei ==
	 * ENUM_TENSE.IMPERATIVE && (prepLAMED || prepBET || prepMEM || prepKAF ||
	 * adverbKAF || subConOrRelSHIN || tempSubConKAFSHIN || tempSubConBETSHIN ||
	 * tempSubConMEMSHIN || tempSubConLAMEDKAFSHIN))
	 * 
	 * // לא יופיעו בכל"ם לפני פעלים בעבר ועתיד && !(prepositionTag && (posi ==
	 * ENUM_POS.VERB && (tensei == ENUM_TENSE.PAST || tensei == ENUM_TENSE.FUTURE)))
	 * 
	 * // למנוע ניתוח של כ+מלאך מ+מלאך ל+מלאך ב+מלאך אבל לאפשר ניתוח של // למלאך
	 * במלאך כלומר תחיליות ב ול יופיעו // כחלק מהצורה ולא כתחיליות כי מייצרים אותן
	 * // לאפשר ניתוח של בלהלביש ובלשמור && !(prepositionTag && tensei ==
	 * ENUM_TENSE.INFINITIVE && suffixFunctioni ==
	 * ENUM_SUFFIX_FUNCTION.SUFFIX_FUNCTION_ACCUSATIVE_OR_NOMINATIVE &&
	 * base.charAt(0) != 'l' && base.charAt(0) != 'b')
	 * 
	 * // למנוע ניתוח של בבשמור && !(prepBET && posi == ENUM_POS.VERB && tensei ==
	 * ENUM_TENSE.INFINITIVE && base.charAt(0) == 'b')
	 * 
	 * // למנוע ניתוח של ככשמור && !(prepKAF && posi == ENUM_POS.VERB && tensei ==
	 * ENUM_TENSE.INFINITIVE && base.charAt(0) == 'k')
	 * 
	 * // למנוע ניתוח של ממשמור && !(prepMEM && posi == ENUM_POS.VERB && tensei ==
	 * ENUM_TENSE.INFINITIVE && base.charAt(0) == 'm')
	 * 
	 * // למנוע ניתוח של ללשמור && !(prepLAMED && posi == ENUM_POS.VERB && tensei ==
	 * ENUM_TENSE.INFINITIVE && base.charAt(0) == 'l')
	 * 
	 * && !(prepositionTag && (posi == ENUM_POS.INDEPENDENTINFINITIVE))
	 * 
	 * // ////////////////////////////////////////////////////////////////////// //
	 * / (interjection) מילת שאלה ,(interrogative) מילת קריאה //
	 * ///////////////////////////////////////////////////////////////////// &&
	 * !((subConOrRelSHIN || tempSubConKAFSHIN || tempSubConMEMSHIN ||
	 * tempSubConLAMEDKAFSHIN) && ( // כיכבתי כי לא נותן לנתח ש+הרי // (posi ==
	 * CONJUNCTION)|| posi == ENUM_POS.INTERJECTION || (posi ==
	 * ENUM_POS.INTERROGATIVE && type != INTERROGATIVE_TYPE_PRONOUN)))
	 * 
	 * && !(prepositionTag && (posi == ENUM_POS.INTERJECTION))
	 * 
	 * && !(prepositionTag && posi == ENUM_POS.INTERROGATIVE && type !=
	 * INTERROGATIVE_TYPE_PRONOUN)
	 * 
	 * && !((relativizerTag || subordinatingConjunctionTag || temporalSubConjTag) &&
	 * posi == ENUM_POS.INTERJECTION)
	 * 
	 * // ///////////////////////////////////////////////////////////////////////
	 * ///////////// // /////////// מילת חיבור // ///////////////////////////////
	 * ////////////////////////////////////////////////////// // כיכבתי כי לא נותן
	 * לנתח ש+הרי // && !((relativizerTag || subordinatingConjunctionTag || //
	 * temporalSubConjTag) && posi == CONJUNCTION) && !(prepositionTag && posi ==
	 * ENUM_POS.CONJUNCTION)
	 * 
	 * // ///////////////////////////////////////////////////////////////////////
	 * ////// // ///////////// שם פרטי // ///////////////////////////////////////
	 * /////////////////////////////////////
	 * 
	 * // למנוע הופעה של שם פרטי עם תחילית בש - כמו בשדליה && !(tempSubConBETSHIN &&
	 * posi == ENUM_POS.PROPERNAME)
	 * 
	 * // ///////////////////////////////////////////////////////////////////////
	 * ////////// // ////// ה' חבויה //
	 * /////////////////////////////////////////////////////////////////////// //
	 * כדי למנוע הופעת אנליזה ל-בהילד כהילד להילד && !((prepBET || prepKAF ||
	 * prepLAMED) && hAttributei == ENUM_HATTRIBUTE.BASE_DEFINITNESS_TRUE_TRUE)
	 * 
	 * // ///////////////////////////////////////////////////////////////////////
	 * /////// // תואר הפועל // /////////////////////////////////////////////////
	 * //////////////////////////////
	 * 
	 * && !(prepositionTag && posi == ENUM_POS.ADVERB)
	 * 
	 * && !(adverbKAF && (posi == ENUM_POS.PROPERNAME || posi == ENUM_POS.VERB //
	 * ושלכשעה, ושבכשעה // || posi == NOUN || posi == ENUM_POS.ADJECTIVE || posi ==
	 * ENUM_POS.PRONOUN || posi == ENUM_POS.PREPOSITION || posi == ENUM_POS.ADVERB
	 * || posi == ENUM_POS.CONJUNCTION || posi == ENUM_POS.INTERJECTION || posi ==
	 * ENUM_POS.INTERROGATIVE || posi == ENUM_POS.NEGATION || posi ==
	 * ENUM_POS.PARTICIPLE))
	 * 
	 * // למנוע הופעה כפולה עבור תחיליות שמופיעות עם ה ובלי ה - למשל ש // ושה כאשר
	 * מצטרפים לש"ע או שם פרטי מיודע // && !((prepMEM || subConOrRelSHIN ||
	 * tempSubConKAFSHIN // || tempSubConMEMSHIN || tempSubConLAMEDKAFSHIN) // &&
	 * definiteArticleTag && baseDefinitnessi == // BASE_DEFINITNESS_TRUE_TRUE) //
	 * רצים ניתוח ש+ב+יידוע +ש"ע שהוא לא נסמך // && !((prepLAMED ||prepBET||prepMEM
	 * || subConOrRelSHIN || // tempSubConKAFSHIN // || tempSubConMEMSHIN ||
	 * tempSubConLAMEDKAFSHIN) && // definiteArticleTag && constructi ==
	 * CONSTRUCT_TRUE)
	 * 
	 * && !((prefPartUnit) && hAttributei == ENUM_HATTRIBUTE.BASE_DEFINITNESS_FALSE
	 * && definiteArticleTag)
	 * 
	 * // /////////////////////////////////////////////////////////////////////// //
	 * למנוע ל+לגביך // - נחליף את החוק הבא בחוקים יותר מעודנים // צריך עוד לבדוק
	 * אפשרות זאת && !(prepositionTag && posi == ENUM_POS.PREPOSITION) // &&
	 * !(subConOrRelSHIN && posi ==PREPOSITION) // && !(prefix.endsWith("k") &&
	 * base.startsWith("k") && posi == // PREPOSITION) // && !(prefix.endsWith("l")
	 * && base.startsWith("l") && posi == // PREPOSITION) //
	 * /////////////////////////////////////////////////////////////////////
	 * 
	 * // למנוע כ+אל ב+יש && !(prepositionTag && posi == ENUM_POS.MODAL)
	 * 
	 * // לפי בקשת מני, אלון לא מסכים - נתווכח אח"כ && !(prepositionTag && posi ==
	 * ENUM_POS.COPULA)
	 * 
	 * // participleType handling && !((posi == ENUM_POS.PARTICIPLE || posi ==
	 * ENUM_POS.PASSIVEPARTICIPLE) && !conjunctionTag && type ==
	 * PARTICIPLE_TYPE_ADJECTIVE) && !((posi == ENUM_POS.PARTICIPLE || posi ==
	 * ENUM_POS.PASSIVEPARTICIPLE) && (!conjunctionTag && !subConOrRelSHIN) && type
	 * == PARTICIPLE_TYPE_VERB)
	 * 
	 * // ///////////////////////////////////////////////////////////////// //
	 * ///////////QUANTIFIER //
	 * ////////////////////////////////////////////////////////// && !(posi ==
	 * ENUM_POS.QUANTIFIER && type == QUANTIFIER_TYPE_PARTITIVE && constructi ==
	 * ENUM_STATUS.CONSTRUCT_FALSE && !definiteArticleTag) // && !((conjunctionTag
	 * || definiteArticleTag || subConOrRelSHIN // || tempSubConKAFSHIN ||
	 * tempSubConMEMSHIN || // tempSubConLAMEDKAFSHIN) // && posi == VERB && tensei
	 * == TENSE_INFINITIVE) //
	 * ///////////////////////////////////////////////////////////////// //
	 * ///////////PREPOSITION //
	 * ////////////////////////////////////////////////////////// // לא לאפשר ש+לי -
	 * מותר רק שלי // כן לאפשר ש+בי - לכן אי אפשר חוק כללי וצריך לבדוק שמדובר בעיול
	 * // ספציפי && !((base.equals("li") || base.equals("lk") || base.equals("lw")
	 * || base.equals("lh") || base.equals("lnw") || base.equals("lkm") ||
	 * base.equals("lkn") || base.equals("lhm") || base .equals("lhn")) &&
	 * prefix.equals("e") && posi == ENUM_POS.PREPOSITION && suffixFunctioni ==
	 * ENUM_SUFFIX_FUNCTION.SUFFIX_FUNCTION_PRONOMIAL))
	 * 
	 * validate = true;
	 * 
	 * return validate; }
	 */

	// ------------------------------------------------------------------------------------------------------------

	/**
	 * This method contains the rules for a token to stand alone without prefix The
	 * decision whether it is valid depends on the part of speech of the base and
	 * more attributes
	 * 
	 * @param transliterated
	 * @param inflectedRecordNum
	 * @return
	 */
	protected boolean validateByRulesWithoutPrefixes(String transliterated, InflectedRecordNum inflectedRecordNum) {
		boolean validate = false;
		// מקור יופיע אלא אם נמנע
		// שם פועל מוטה לא יופיע כעומד בפני עצמו רק בצירוף תחילית
		// שם פועל מוטה עם תחילית כ - לא יופיע אלא כיוצא דופן
		// שם פועל מוטה עם תחילית מ לא יופיע אלא כיוצא דופן
		// שם פועל עם תחילית ב - יופיע אלא אם מונעים
		// שם פועל עם תחילית ל - יופיע אלא אם מונעים
		// יש שמות פרטיים שלא יופיעו לעולם עם ה' ורוצים למנוע ניתוח שלהם
		// כמיודעים כמו הקולורדו האשדוד
		// יש שמות פרטיים שרוצים למנוע הופעה שלהם כלא מיודעים
		ENUM_TENSE tensei = inflectedRecordNum.getTense();
		ENUM_HATTRIBUTE haaributei = inflectedRecordNum.getHAttribute();
		ENUM_POS posi = inflectedRecordNum.getPos();
		ENUM_SUFFIX_FUNCTION suffixFunctioni = inflectedRecordNum.getSuffixFunction();
		ENUM_STATUS constructi = inflectedRecordNum.getStatus();

		if (!(haaributei == ENUM_HATTRIBUTE.BASE_DEFINITNESS_REQUIRED_FALSE) && !(posi == ENUM_POS.QUANTIFIER
				&& haaributei == ENUM_HATTRIBUTE.BASE_DEFINITNESS_TRUE_FALSE && constructi == ENUM_STATUS.CONSTRUCT_FALSE)
		// שונה בגלל ליצואן
		// && !((posi != ENUM_POS.PRONOUN )&& haaributei ==
		// ENUM_HATTRIBUTE.BASE_DEFINITNESS_REQUIRED_TRUE)
				&& !((posi != ENUM_POS.PRONOUN && posi != ENUM_POS.PROPERNAME)
						&& haaributei == ENUM_HATTRIBUTE.BASE_DEFINITNESS_REQUIRED_TRUE)
				&& !(posi == ENUM_POS.VERB && tensei == ENUM_TENSE.INFINITIVE
						&& suffixFunctioni == ENUM_SUFFIX_FUNCTION.SUFFIX_FUNCTION_ACCUSATIVE_OR_NOMINATIVE
						&& transliterated.charAt(0) != 'b' && transliterated.charAt(0) != 'l'))
			validate = true;
		return validate;
	}

}
