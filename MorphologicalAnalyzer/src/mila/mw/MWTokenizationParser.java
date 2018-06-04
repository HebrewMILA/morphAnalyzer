package mila.mw;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import mila.dataStructures.DBInflectionsRecord;
import mila.dataStructures.InflectedRecordNum;
import mila.lexicon.analyse.Data;
import mila.lexicon.analyse.Str2Num;
import mila.lexicon.analyse.TextOutput;
import mila.lexicon.analyse.TokenizationParser;
import mila.lexicon.dbUtils.MWEinflectionsRecord;
import mila.lexicon.dbUtils.PrefixRecord;
import mila.lexicon.utils.StringUtils;
import mila.lexicon.utils.Translate;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import static mila.lexicon.analyse.Constants.*;

/**
 * @author daliabo
 *
 */
public class MWTokenizationParser extends TokenizationParser {

	private MWCreateCorpusXML mwcreateXML = null;
	public SAXParser _parser;

	public MWTokenizationParser() {

	}

	public MWTokenizationParser(MWCreateCorpusXML mwcreateXML) {
		// System.out.println("(F) MWTokenizationParser() constructor ");
		try {
			this.mwcreateXML = mwcreateXML;
			final SAXParserFactory factory = SAXParserFactory.newInstance();
			factory.setNamespaceAware(false);
			factory.setValidating(false);
			_parser = factory.newSAXParser();
		} catch (final SAXException e) {
			e.printStackTrace();
		} catch (final ParserConfigurationException e) {
			e.printStackTrace();
		}
	}

	protected boolean analyzeBase(final String base, final String prefix, final String transliterated,
			DBInflectionsRecord inflectionsRecDB, InflectedRecordNum inflectionsRecNum) {
		boolean foundAnalysis = false;
		PrefixRecord pr = null;

		int prefixListSize = 0;

		try {
			prefixListSize = Data.getPrefixes(prefix);
		} catch (final Exception e2) {
			System.err.println("XMLMorphAnalyzer:analyzeBase - Exception while getting prefixes list for word=" + base
					+ "and prefix=" + prefix);
			e2.printStackTrace();
		}
		// System.out.println("prefixListSize =" + prefixListSize);

		for (int j = 0; j < prefixListSize; j++) {
			pr = new PrefixRecord();
			pr = Data.analyzePrefixList(j);
			if (validateByRules(base, prefix, inflectionsRecNum, pr)) {
				try {
					foundAnalysis = true;

					TextOutput.buildXMLPrefixOutput(pr, inflectionsRecDB, inflectionsRecNum, mwcreateXML,
							Translate.Eng2Heb(base));
				} catch (final Exception e3) {
					System.err.println("XMLMorphAnalyzer:analyzeBase - Exception while buildXMLPrefixOutput for word=" + base
							+ "and prefix=" + prefix);
					e3.printStackTrace();
				}
			}
		}
		return foundAnalysis;
	}

	@Override
	public boolean analyzeBase(final String base, final String prefix, final String transliterated,
			final String hebWord) {
		// System.out.println("(F) analyzeBase (base,prefix) (" + base + "," +
		// prefix +")" );
		boolean foundAnalysis = false; // האם מצאנו ניתוח המבוסס על תחילית +
		// בסיס
		PrefixRecord pr = null;
		int baseListSize = 0;
		ArrayList<DBInflectionsRecord> inflectionsList = null;
		DBInflectionsRecord inflectionsRecDB = null;
		InflectedRecordNum inflectionsRecNum = null;
		try {
			inflectionsList = Data.getInflections(base);
		} catch (final Exception e) {
			System.err
					.println("XMLMorphAnalyzer:analyzeBase - Exception while getting inflections list for base = " + base);
			e.printStackTrace();
		}
		baseListSize = inflectionsList.size();
		if (baseListSize > 0) {
			for (int i = 0; i < baseListSize; i++) {
				try {
					inflectionsRecDB = inflectionsList.get(i);
					inflectionsRecNum = Data.Str2NumBeforeValidation(inflectionsRecDB, hebWord);
				} catch (final Exception e1) {
					System.err
							.println("XMLMorphAnalyzer:analyzeBase - Exception while analyzeInflectionList for word=" + base);
					e1.printStackTrace();
				}

				final char prefixPerEntry = inflectionsRecDB.getPrefixPerEntry();
				// מטפל במקרה של -ולמי - ל היא התחילית למי מופיע כתמנית בקובץ
				// ההטיות
				// אבל ולמי לא מופיע בקובץ ההטיות
				String combinedPrefix = prefix;
				if (prefixPerEntry != 'u') {
					// System.out.println("(F) analyzeBase prefixPerEntry !=
					// u");
					if (!prefix.equals(prefixPerEntry)) {
						combinedPrefix = prefix + prefixPerEntry;
					}

					foundAnalysis = analyzeBase(base, combinedPrefix, transliterated, inflectionsRecDB, inflectionsRecNum);
					continue;
				}

				int prefixListSize = 0;

				try {
					prefixListSize = Data.getPrefixes(prefix);
				} catch (final Exception e2) {
					System.err.println("XMLMorphAnalyzer:analyzeBase - Exception while getting prefixes list for word="
							+ base + "and prefix=" + prefix);
					e2.printStackTrace();
				}
				// System.out.println("prefixListSize =" + prefixListSize);

				for (int j = 0; j < prefixListSize; j++) {
					pr = new PrefixRecord();
					pr = Data.analyzePrefixList(j);

					if (validateByRules(base, prefix, inflectionsRecNum, pr)) {
						try {
							foundAnalysis = true;
							// System.out.println("(F) analyzeBase
							// validateByRules buildXMLPrefixOutput for base = "
							// + base);
							TextOutput.buildXMLPrefixOutput(pr, inflectionsRecDB, inflectionsRecNum, mwcreateXML,
									Translate.Eng2Heb(base));
						} catch (final Exception e3) {
							System.err.println("XMLMorphAnalyzer:analyzeBase - Exception while buildXMLPrefixOutput for word="
									+ base + " and prefix=" + prefix);
							e3.printStackTrace();
						}
					}
				}
			}
		}
		return foundAnalysis;
	}

	// -----------------------------------------------------------------------------------------------------------------

	// ---------------------------------------------------------------------------------------------------------------
	/**
	 * Check whether the token is comprised of prefix + subToken Special treatment
	 * is given to prefix (bklm) per entry - currently implemented for pronouns only
	 *
	 * @param hebWord
	 * @param anaanlysis
	 *           was found /and analysis was not found
	 */
	@Override
	protected boolean analyzeBaseAndPrefix(final String hebWord, final String transliterated) {
		// System.out.println("(F) analyzeBaseAndPrefix");
		boolean foundAnalysisPerPrefixEntry = false;
		boolean foundAnalysisNoPerPrefixEntry = false;
		if (prefixPerEntryExistFlag) {
			// System.out.println("(F) analyzeBaseAndPrefix A");
			foundAnalysisPerPrefixEntry = analyzeBaseAndPrefixAndPrefixPerEntry(hebWord, transliterated);
		}
		try {
			// System.out.println("(F) analyzeBaseAndPrefix B");
			foundAnalysisNoPerPrefixEntry = analyzeBaseAndPrefixNoPrefixPreEntry(hebWord, transliterated);
		} catch (final Exception e) {
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
	 * @param anaanlysis
	 *           was found /and analysis was not found
	 */
	@Override
	protected boolean analyzeBaseAndPrefixAndPrefixPerEntry(final String hebWord, final String transliterated) {
		// System.out.println(hebWord);
		// System.out.println("(F) analyzeBaseAndPrefixAndPrefixPerEntry
		// (transliterated) ("
		// + transliterated +")" );
		boolean foundAnalysis = false;
		char prefixPerEntry = 'u'; // unspecified
		int listSize = 0;
		ArrayList<DBInflectionsRecord> inflectionsList = null;
		DBInflectionsRecord inflectionsRecDB = null;
		InflectedRecordNum inflectionsRecNum = null;
		try {
			inflectionsList = Data.getInflections(transliterated);
		} catch (final Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		listSize = inflectionsList.size();
		if (listSize > 0) {
			for (int i = 0; i < listSize; i++) {
				prefixPerEntry = 'u';
				try {
					inflectionsRecDB = inflectionsList.get(i);
					inflectionsRecNum = Data.Str2NumBeforeValidation(inflectionsRecDB, hebWord);
				} catch (final Exception e1) {
					System.err.println(
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
							if (prefix.length() == 1 && prefixPerEntry == prefix.charAt(0)) {
								base = transliterated;
							} else if (prefix.charAt(k - 1) == prefixPerEntry) {
								base = transliterated.substring(k - 1);
								prefix = transliterated.substring(0, k - 1);
							} else {
								break;
							}

							int prefixListSize = 0;

							try {
								prefixListSize = Data.getPrefixes(prefix);
							} catch (final Exception e2) {
								// System.out.println("XMLMorphAnalyzer:analyzeBase
								// - Exception while getting prefixeslist for
								// word="+
								// base + "and prefix=" + prefix);
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
										// System.out.println("(F)
										// analyzeBaseAndPrefixAndPrefixPerEntry
										// validateByRules buildXMLPrefixOutput
										// base "
										// + base);
										TextOutput.buildXMLPrefixOutput(pr, inflectionsRecDB, inflectionsRecNum, mwcreateXML,
												Translate.Eng2Heb(base));
									} catch (final Exception e3) {
										System.err.println(
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
		}
		return foundAnalysis;
	}

	/**
	 * This method handles separating the token to prefix + base combinations for
	 * looking in the inflections and prefixes table
	 *
	 * @param hebWord
	 * @return
	 * @throws Exception
	 */

	@Override
	protected boolean analyzeBaseAndPrefixNoPrefixPreEntry(final String hebWord, final String transliterated)
			throws Exception {
		// System.out.println("(F)
		// analyzeBaseAndPrefixNoPrefixPreEntry("+hebWord+","+transliterated+")");
		boolean foundAnalysis = false;
		boolean currentFoundAnalysis = false;
		boolean currentMWFoundAnalysis = false;
		// the longest prefix know is 6 chars long
		for (int j = 1; j < 6 && j < transliterated.length(); j++) {
			final String prefix = transliterated.substring(0, j);
			// System.out.println("(F) analyzeBaseAndPrefixNoPrefixPreEntry
			// prfix "
			// + prefix);
			// לא תיתכן תחילית שאינה משהוכלב
			if (StringUtils.moshevkaleb(prefix)) {
				final String base = transliterated.substring(j);
				// System.out.println("(F) analyzeBaseAndPrefixNoPrefixPreEntry
				// base "
				// + base);
				currentFoundAnalysis = analyzeBase(base, prefix, transliterated, hebWord);
				currentMWFoundAnalysis = analyzeMWBase(base, prefix, transliterated);
				if (currentFoundAnalysis || currentMWFoundAnalysis) {
					foundAnalysis = true;
				}
			} else {
				break;
			}
		}
		return foundAnalysis;
	}

	@Override
	protected boolean analyzeBaseNoPrefix(String hebWord, String transliterated) {
		// System.out.println("(F) analyzeBaseNoPrefix");
		boolean foundAnalysis = false;
		char prefixPerEntry = 'u'; // unspecified
		prefixPerEntryExistFlag = false;
		InflectedRecordNum inflectionsRecNum = null;
		DBInflectionsRecord inflectionsRecDB = null;
		ArrayList<DBInflectionsRecord> inflectionsList = null;
		ArrayList<MWEinflectionsRecord> mweInflectionsList = null;

		// handle cases where in tokenization stage we can't guess for sure that
		// the token is whole prefix
		// for exampe ב 20
		if (StringUtils.moshevkaleb(hebWord)) {
			// System.out.println("(F) analyzeBaseNoPrefix moshevkaleb");
			handlePrefix(hebWord);
			// עבור ב - שלא ייצר לנו ניתוח של שם פרטי כי אין אנליזה
			foundAnalysis = true;
		}
		try {
			inflectionsList = Data.getInflections(transliterated);
			mweInflectionsList = MWData.getMWEinflections(transliterated);
		} catch (final Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		final int inflectionListSize = inflectionsList.size();
		for (int i = 0; i < inflectionListSize; i++) {
			// System.out.println("(F) analyzeBaseNoPrefix IN FOR");
			try {
				inflectionsRecDB = inflectionsList.get(i);
				inflectionsRecNum = Data.Str2NumBeforeValidation(inflectionsRecDB, hebWord);
			} catch (final Exception e1) {
				System.err.println(
						"TokenizationParser: Exception occured while analyzeBaseNoPrefix for word=" + transliterated);
				e1.printStackTrace();
			}
			if (!checkEndOfToken(transliterated, hebWord, inflectionsRecDB.getSurface())) {
				continue;
			}
			prefixPerEntry = inflectionsRecDB.getPrefixPerEntry();
			if (prefixPerEntry == 'u') {
				if (validateByRulesWithoutPrefixes(transliterated, inflectionsRecNum)) {
					// System.out.println("(F) analyzeBaseNoPrefix
					// validateByRulesWithoutPrefixes");
					foundAnalysis = true;
					try {
						TextOutput.buildXMLOutput(inflectionsRecDB, inflectionsRecNum, mwcreateXML, hebWord);
					} catch (final Exception e) {
						// TODO Auto-generated catch block
						System.err
								.println("Error occured when analyzing item no " + inflectionsRecDB.getBaseLexiconPointer());
						e.printStackTrace();
					}
				}
			} else if (!prefixPerEntryExistFlag) {
				prefixPerEntryExistFlag = true;
			}
		}
		final boolean foundMWAnalysis = mwinflectionsHandling(mweInflectionsList);
		return foundMWAnalysis || foundAnalysis;
	}

	// ------------------------------------------------------------------------------------------------------------------------------------------

	@Override
	protected boolean analyzeForeign(String hebWord) {
		// System.out.println(hebWord);
		boolean isForeign = false;

		isForeign = StringUtils.analyzeForeign(hebWord);
		if (isForeign) {
			final ENUM_OUTPUT_PATTERN outputPattern = ENUM_OUTPUT_PATTERN.FOREIGN;
			try {
				TextOutput.buildSimpleXMLAnalysis(outputPattern, mwcreateXML, hebWord, -1);
			} catch (final Exception e) {
				System.err.println("MWTokenizationParser: error for " + hebWord);
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return isForeign;
	}

	@Override
	protected boolean analyzeHebrewDotSingleLetter(String hebWord) throws Exception {
		boolean isHebrewSingleLetter = false;
		ENUM_OUTPUT_PATTERN outputPattern = null;
		final int hebWordLen = hebWord.length();
		if (hebWordLen == 2 && hebWord.charAt(1) == '.'
				|| hebWordLen == 4 && hebWord.charAt(1) == '.' && hebWord.charAt(3) == '.') {

			try {
				isHebrewSingleLetter = StringUtils.analyzeHebrewSingleLetter(hebWord.substring(0, 1));
				if (hebWordLen == 4 && isHebrewSingleLetter) {
					isHebrewSingleLetter = StringUtils.analyzeHebrewSingleLetter(hebWord.substring(2, 3));
				}
			} catch (final Exception e) {
				System.err.println("XMLAnalyzer:analyzeHebrewDotSingleLetter Exception occured  for hebWord=" + hebWord);
				e.printStackTrace();
			}
			if (isHebrewSingleLetter) {
				try {
					outputPattern = ENUM_OUTPUT_PATTERN.HEBREWDOTLETTER;
					TextOutput.buildSimpleXMLAnalysis(outputPattern, mwcreateXML, hebWord, -1);
				} catch (final JAXBException e1) {
					System.err
							.println("XMLAnalyzer:analyzeHebrewDotSingleLetter JAXBException occured  for hebWord=" + hebWord);
					e1.printStackTrace();
				}
			}
		}
		return isHebrewSingleLetter;
	}

	@Override
	protected boolean analyzeHebrewSingleLetter(String hebWord) throws Exception {
		boolean isHebrewSingleLetter = false;
		ENUM_OUTPUT_PATTERN outputPattern = null;
		try {
			isHebrewSingleLetter = StringUtils.analyzeHebrewSingleLetter(hebWord);
		} catch (final Exception e) {
			System.err.println("XMLAnalyzer:analyzeNumbers Exception occured  for hebWord=" + hebWord);
			e.printStackTrace();
		}
		if (isHebrewSingleLetter) {
			try {
				outputPattern = ENUM_OUTPUT_PATTERN.HEBREWLETTER;
				TextOutput.buildSimpleXMLAnalysis(outputPattern, mwcreateXML, hebWord, -1);
			} catch (final JAXBException e1) {
				System.err.println("XMLAnalyzer:analyzeNumbers JAXBException occured  for hebWord=" + hebWord);
				e1.printStackTrace();
			}
		}
		return isHebrewSingleLetter;
	}

	protected boolean analyzeMWBase(String base, String prefix, String transliterated) {
		// System.out.println("(F) analyzeMWBase (base,prefix) (" + base + "," +
		// prefix +")" );
		ArrayList<MWEinflectionsRecord> mweInflectionsList = null;
		boolean foundAnalysis = false; // האם מצאנו ניתוח המבוסס על תחילית +
		// בסיס
		PrefixRecord pr = null;
		InflectedRecordNum inflectionsRecNum = null;

		MWEinflectionsRecord mweinflectionsRec = new MWEinflectionsRecord();

		int baseListSize = 0;
		try {
			mweInflectionsList = MWData.getMWEinflections(base);
		} catch (final Exception e) {
			System.err
					.println("XMLMorphAnalyzer:analyzeBase - Exception while getting inflections list for base = " + base);
			e.printStackTrace();
		}
		baseListSize = mweInflectionsList.size();
		// System.out.println("prefix=" + prefix);
		// System.out.println("base=" + base);
		// System.out.println("baseListSize =" + baseListSize);
		for (int i = 0; i < baseListSize; i++) {
			try {
				mweinflectionsRec = mweInflectionsList.get(i);
			} catch (final Exception e1) {
				System.err.println("XMLMorphAnalyzer:analyzeBase - Exception while analyzeInflectionList for word=" + base);
				e1.printStackTrace();
			}
			final String id = mweinflectionsRec.getMweId();
			final String pos = mweinflectionsRec.getPos();
			ENUM_POS posi = null;
			try {
				posi = Str2Num.str2NumPos(pos, transliterated, base);
			} catch (final Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			inflectionsRecNum = new InflectedRecordNum();
			inflectionsRecNum.setPos(posi);
			if (id.equals("1")) {
				int prefixListSize = 0;
				try {
					prefixListSize = Data.getPrefixes(prefix);
				} catch (final Exception e2) {
					System.err.println("XMLMorphAnalyzer:analyzeBase - Exception while getting prefixes list for word="
							+ base + "and prefix=" + prefix);
					e2.printStackTrace();
				}
				// System.out.println("prefixListSize =" + prefixListSize);

				for (int j = 0; j < prefixListSize; j++) {
					pr = new PrefixRecord();
					pr = Data.analyzePrefixList(j);

					if (validateByRules(base, prefix, inflectionsRecNum, pr, mweinflectionsRec)) {
						try {
							foundAnalysis = true;
							// System.out.println("(F) analyzeMWBase
							// validateByRules buildPrefixMWXMLOutput");
							MWTextOutput.buildPrefixMWXMLOutput(pr, mwcreateXML, mweinflectionsRec);
						} catch (final Exception e3) {
							System.err.println("XMLMorphAnalyzer:analyzeBase - Exception while buildXMLPrefixOutput for word="
									+ base + "and prefix=" + prefix);
							e3.printStackTrace();
						}
					}
				}
			}
		}

		return foundAnalysis;
	}

	@Override
	protected boolean analyzeNumberExpression(String hebWord) {
		boolean isNumberExpression = false;
		ENUM_OUTPUT_PATTERN outputPattern = null;

		try {
			outputPattern = StringUtils.analyzeNumberExpression(hebWord);
			if (outputPattern != ENUM_OUTPUT_PATTERN.UNSPECIFIED) {
				isNumberExpression = true;
			}
		} catch (final Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (isNumberExpression) {
			try {
				TextOutput.buildSimpleXMLAnalysis(outputPattern, mwcreateXML, hebWord, -1);
			} catch (final Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return isNumberExpression;
	}

	@Override
	protected boolean analyzeNumbers(String hebWord) {
		boolean isNumber = false;
		ENUM_OUTPUT_PATTERN outputPattern = null;
		try {
			isNumber = StringUtils.analyzeNumbers(hebWord);
		} catch (final Exception e) {
			System.err.println("XMLAnalyzer:analyzeNumbers Exception occured  for hebWord=" + hebWord);
			e.printStackTrace();
		}
		if (isNumber) {
			outputPattern = ENUM_OUTPUT_PATTERN.LITERAL_NUMBERS;
			try {
				TextOutput.buildSimpleXMLAnalysis(outputPattern, mwcreateXML, hebWord, -1);
			} catch (final Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return isNumber;
	}

	@Override
	protected boolean analyzePrefixGimatriaAndInvertedCommas(String base, String prefix) {

		boolean foundAnalysis = false;
		PrefixRecord pr = null;
		final ENUM_OUTPUT_PATTERN outputPattern = ENUM_OUTPUT_PATTERN.GEMATRIA;
		final DBInflectionsRecord inflectionRecDB = new DBInflectionsRecord();
		final InflectedRecordNum inflectionRecNum = new InflectedRecordNum();

		int gimatriaVal = -1;
		try {
			gimatriaVal = Data.getGimatrias(base);
		} catch (final Exception e2) {
			System.err.println(
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
					TextOutput.buildXMLPrefixOutput(pr, inflectionRecDB, inflectionRecNum, mwcreateXML, base);

				} catch (final Exception e1) {
					System.err.println(
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
				} catch (final Exception e1) {
					System.err.println(
							"XMLAnalyzer:analyzePrefixGimatriaAndInvertedCommas Exception occured while getting prefixes for base="
									+ base + " and prefix=" + prefix);
					e1.printStackTrace();
				}
				// ////////////////////////////////////////////////////
				// The prefix was found in the prefix table
				// ////////////////////////////////////////////////////////
				if (prefixListSize > 0) {
					for (int j = 0; j < prefixListSize; j++) {
						foundAnalysis = true;
						pr = new PrefixRecord();
						pr = Data.analyzePrefixList(j);
						try {

							TextOutput.buildXMLPrefixOutput(pr, inflectionRecDB, inflectionRecNum, mwcreateXML, base);

						} catch (final Exception e) {
							System.err.println(
									"XMLAnalyzer:analyzePrefixGimatriaAndInvertedCommas Exception occured while buildXMLPrefixOutput for base="
											+ base + " and prefix=" + prefix);
							e.printStackTrace();
						}
					}
				}
			}
		}
		return foundAnalysis;
	}

	@Override
	protected boolean analyzePunctuations(String hebWord) {
		ENUM_OUTPUT_PATTERN outputPattern = null;
		boolean isPunctuation = false;

		isPunctuation = StringUtils.analyzePunctuations(hebWord);

		if (isPunctuation) {
			outputPattern = ENUM_OUTPUT_PATTERN.PUNCTUATION;
			try {
				TextOutput.buildSimpleXMLAnalysis(outputPattern, mwcreateXML, hebWord, -1);
			} catch (final Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return isPunctuation;
	}

	@Override
	protected boolean analyzeURL(String hebWord) {
		boolean isURL = false;
		ENUM_OUTPUT_PATTERN outputPattern = null;
		isURL = StringUtils.analyzeURL(hebWord);
		if (isURL) {
			outputPattern = ENUM_OUTPUT_PATTERN.URL;
			try {
				TextOutput.buildSimpleXMLAnalysis(outputPattern, mwcreateXML, hebWord, -1);
			} catch (final Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return isURL;
	}

	/**
	 * This method handling tokens containg ' or ''
	 *
	 * מטפל בקיצורים וראשי תיבות כמו ג'ורג' וג'ורג' בשתל"ח בתשל"ח
	 *
	 * @param hebWord
	 */

	@Override
	protected boolean apostropheInvertedCommasHandling(String hebWord) {
		boolean foundAnalysis = false;
		final String transliterated = Translate.Heb2Eng(hebWord);
		int gimatriaVal = -1;
		if (transliterated.indexOf("\'") != -1 && transliterated.indexOf("\"") != -1) {
			return foundAnalysis;
		}
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

					mwcreateXML.createNumeralAnalysis("", null, null, null, "", "", "", String.valueOf(gimatriaVal), "", "",
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
			} catch (final Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			// ///////////////////////////////////////////////////////////
			// טיפול בגרשיים
			// //////////////////////////////////////////////////////////
			// handle תשל"ח בתש"ל"ח
		} else if (transliterated.indexOf("\"") == transliterated.length() - 2) {
			try {
				gimatriaVal = Data.getGimatrias(transliterated);
				if (gimatriaVal != -1) {
					foundAnalysis = true;
					// //////////////////////////////////////////////////////////
					// The whole token was found in Gimatria table
					// /////////////////////////////////////////////////////////
					// create gimatria analysis
					mwcreateXML.createNumeralAnalysis("", null, null, null, "", "", "", String.valueOf(gimatriaVal), "", "",
							"", "", "gematria", "", "", "", "", "");
					// /////////////////////////////////////////////////////////////////
					// The whole token was ***not found*** in Gimatria table
					// ////////////////////////////////////////////////////////////////
				} else {
					// check if it a combination of prefix + gimatria
					analyzeAcronymsBaseAndPrefix(transliterated);
				}

				// הוספתי את 2 הפונקציות הבאות כדי שנקבל גם ניתוח גימטריא וגם לא
				// גימטריא למשל
				// עבור ש"ח
				// בפונקציות אלו לא מתבצע חיפוש בטבלת גימטריא אלא רק בטבלאות
				// התחילית והנטיות
				// check if the whole token is in the inflections table
				foundAnalysis = foundAnalysis | analyzeBaseNoPrefix(hebWord, transliterated)
						| analyzeBaseAndPrefix(hebWord, transliterated);
			} catch (final UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (final Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return foundAnalysis;
	}

	@Override
	public void endElement(String namespaceURI, String localName, String rowName) throws SAXException {
		if (rowName.equals("paragraph")) {
			mwcreateXML.finalizeParagraph();
		} else if (rowName.equals("sentence")) {
			mwcreateXML.finalizeSentence();
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
	@Override
	protected void handlePrefix(String hebPrefix) {
		// System.out.println("(F) handlePrefix");

		ENUM_OUTPUT_PATTERN outputPattern = null;
		int prefixListSize = 0;
		PrefixRecord pr = null;
		final DBInflectionsRecord inflectionRecDB = null;
		final InflectedRecordNum inflectionRecNum = new InflectedRecordNum();
		// int equallIndex = hebPrefix.indexOf("=");
		// hebPrefix = hebPrefix.substring(equallIndex + 1);
		String prefix = Translate.Heb2Eng(hebPrefix);
		if (prefix.length() == 1 && prefix.charAt(0) == 'h') {
			try {
				// System.out.println("(F) handlePrefix A");
				outputPattern = ENUM_OUTPUT_PATTERN.PREFIXES;
				inflectionRecNum.setOutputPattern(outputPattern);
				TextOutput.buildXMLPrefixOutput(null, inflectionRecDB, inflectionRecNum, mwcreateXML, hebPrefix);

			} catch (final Exception e) {
				System.err.println(
						"XMLAnalyzer:handlePrefix Exception occured while buildXMLPrefixOutput for prefix=" + prefix);
				e.printStackTrace();
			}
		} else {
			if (prefix.endsWith("h")) {
				prefix = prefix.substring(0, prefix.length() - 1);
				final ENUM_HATTRIBUTE hAttributei = ENUM_HATTRIBUTE.PREFIX_STANDALONE_H;
				inflectionRecNum.setHAttribute(hAttributei);
			}
			try {
				// System.out.println("(F) handlePrefix
				// Data.getPrefixes("+prefix+")");
				prefixListSize = Data.getPrefixes(prefix);
			} catch (final Exception e1) {
				System.err
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
					final boolean isDefiniteArticle = pr.isDefiniteArticleTag();
					final boolean prepKAF = pr.isPrepKAF();
					final boolean prepLAMED = pr.isPrepLAMED();
					final boolean prepMEM = pr.isPrepMEM();
					// ל-100 לא רוצים פעמיים אנליזה של ל
					if (!isDefiniteArticle && (!prepKAF || !prepMEM || !prepLAMED)) {
						try {
							// System.out.println("(F) handlePrefix B");
							TextOutput.buildXMLPrefixOutput(pr, inflectionRecDB, inflectionRecNum, mwcreateXML, "");
						} catch (final Exception e) {
							System.err
									.println("XMLAnalyzer:handlePrefix Exception occured while buildXMLPrefixOutput for prefix="
											+ prefix);
							e.printStackTrace();
						}
					}
				}
			}
		}
	}

	protected boolean mwinflectionsHandling(ArrayList<MWEinflectionsRecord> mweInflectionsList) {
		final int mweinflectionsListSize = mweInflectionsList.size();
		MWEinflectionsRecord mweinflectionsRec = new MWEinflectionsRecord();
		boolean foundAnalysis = false;
		for (int i = 0; i < mweinflectionsListSize; i++) {
			mweinflectionsRec = mweInflectionsList.get(i);
			foundAnalysis = true;
			MWTextOutput.buildMWXMLOutput(mwcreateXML, mweinflectionsRec);
		}
		return foundAnalysis;
	}

	@Override
	protected boolean noEntryInInflections(final String hebWord, final String transliterated) throws Exception {
		// System.out.println("(F) noEntryInInflections (transliterated) (" +
		// transliterated +")" );
		mwcreateXML.createUnknownAnalysis(hebWord, transliterated);

		return true;
	}

	@Override
	public void parse(InputStream in) {
		// System.out.println("(F) mwtokenizationParser.parse() ");
		try {
			_parser.parse(in, this);
		} catch (final SAXParseException spe) {
			System.err.println("Input file is not a tokenized XML file by Mila standards");
			// spe.printStackTrace();
		} catch (final SAXException se) {
			se.printStackTrace();
		} catch (final IOException ioe) {
			ioe.printStackTrace();
		}
	}

	/*
	 * from this function all options of analyse of a token are called
	 */
	@Override
	public void readInput(String hebWord) throws IOException, Exception {
		// System.out.println("(F) readInput("+ hebWord+")");
		// System.out.println(hebWord);
		// if(hebWord.equals("היוסדה"))
		// System.out.println();
		// System.out.println("(F) readInput A");
		if (!analyzeURL(hebWord)) {
			// System.out.println("(F) readInput B");
			if (!analyzeForeign(hebWord)) {
				// System.out.println("(F) readInput C");
				if (!analyzePunctuations(hebWord) && !analyzeNumbers(hebWord) && !analyzeNumberExpression(hebWord)) {
					final String transliterated = Translate.Heb2Eng(hebWord);
					// System.out.println("(F) readInput transliterated " +
					// transliterated);
					if (StringUtils.gimatriaPossibility(hebWord)) {
						// System.out.println("(F) readInput gimatriaPossibility
						// ");
						if (!apostropheInvertedCommasHandling(hebWord)) {
							noEntryInInflections(hebWord, transliterated);
						}
					} else {
						// System.out.println("(F) readInput NOT
						// gimatriaPossibility ");
						// System.out.println(hebWord);
						// it is important to be & and not && because we
						// want both to be done anyway
						if (!analyzeBaseNoPrefix(hebWord, transliterated) & !analyzeBaseAndPrefix(hebWord, transliterated)
								& !analyzeHebrewDotSingleLetter(hebWord) & !analyzeHebrewSingleLetter(hebWord)) {
							noEntryInInflections(hebWord, transliterated);
						}
					}

				}
			}
		}
	}

	/*
	 * Main Parsing method
	 */
	@Override
	public void startElement(String namespaceURI, String localName, String rowName, Attributes attributes)
			throws SAXException {
		// System.out.println("(F) startElement(localName,rowName):(" +
		// localName + ","+rowName+")" );
		if (rowName.equals("paragraph")) {
			mwcreateXML.createParagraph();

			// handle sentence
		} else if (rowName.equals("sentence")) {
			mwcreateXML.createSentence();
			// handle token
		} else if (rowName.equals("token")) {
			// tokensCount++;
			final String hebWord = attributes.getValue("surface");
			// System.out.println("(F) MWTokenizationParser.startElement()
			// hebWord = "
			// + hebWord );
			// System.out.println("======================================");
			// System.out.println("(F) startElement hebWord: " + hebWord);
			// System.out.println(hebWord);
			// טיפול בסימונים - שמוסרים לנו אינפורמציה שנאספה בזמן טוקניזציה
			if (hebWord.startsWith("prefix=")) {
				// System.out.println("(F) MWTokenizationParser.startElement()
				// hebWord.startsWith(prefix=)");
				if (hebWord.indexOf("token=") != -1) {
					final int prefixEquallIndex = hebWord.indexOf("=");
					final int tokenEqualIndex = hebWord.lastIndexOf("=");
					final int spaceIndex = hebWord.lastIndexOf(" ");
					String hebPrefix = hebWord.substring(prefixEquallIndex + 1, spaceIndex);
					String hebBase = hebWord.substring(tokenEqualIndex + 1);
					// טפול במקרה של יידוע - יש להעביר את הה' לתמנית
					// מהתחילית
					if (hebPrefix.endsWith("ה")) {
						hebPrefix = hebPrefix.replace('ה', ' ').trim();
						hebBase = "ה" + hebBase;
					}
					final String hebTokenWithoutSigning = hebPrefix + hebBase;
					mwcreateXML.createToken(hebTokenWithoutSigning);
					analyzeBase(Translate.Heb2Eng(hebBase), Translate.Heb2Eng(hebPrefix),
							Translate.Heb2Eng(hebTokenWithoutSigning), hebWord);

					// טיפול במקרה של תחילית + בסיס מחוברים - מזוהה עוד בשלב
					// טוקניזציה ומעבירים רמזים
				} else {
					final int equallIndex = hebWord.indexOf("=");
					final String hebPrefix = hebWord.substring(equallIndex + 1);
					mwcreateXML.createToken(hebPrefix);
					handlePrefix(hebPrefix);
				}
				// //////////////////////////////////////////////////////////////
				// there is not prefix= in the surface attribute
				// טיפול במקרים הרגילים - אין רמזים משלב טוקניזציה
				// //////////////////////////////////////////////////////////////
			} else // hebWord Dont startsWith("prefix=")
			{
				// System.out.println("(F) MWTokenizationParser.startElement()
				// hebWord Dont startsWith(prefix=)");
				mwcreateXML.createToken(hebWord);
				try {
					readInput(hebWord);
				} catch (final IOException e1) {
					System.err.println("XMLMorphAnalyzer:startElemen(element) - IOException while readInput");
					e1.printStackTrace();
				} catch (final Exception e1) {
					System.err.println("XMLMorphAnalyzer:startElemen(element) - Exception while readInput");
					e1.printStackTrace();
				}
			} //
			mwcreateXML.finalizeToken();
		}
	}

	protected boolean validateByRules(final String base, final String prefix,
			final InflectedRecordNum inflectedRecordNum, final PrefixRecord pr,
			final MWEinflectionsRecord mweinflectionsRec) {
		boolean rt = false;
		boolean rtSuper = false;
		final boolean prepBET = pr.isPrepBET();
		final boolean prepKAF = pr.isPrepKAF();
		final boolean prepLAMED = pr.isPrepLAMED();
		final boolean isDefiniteArticle = pr.isDefiniteArticleTag();
		final String mwType = mweinflectionsRec.getType();
		final ENUM_POS mwposi = inflectedRecordNum.getPos();
		if (!(isDefiniteArticle && (prepLAMED || prepBET || prepKAF) && mwType.equals("town")
				&& mwposi == ENUM_POS.PROPERNAME)) {
			rt = true;
		}
		rtSuper = super.validateByRules(base, prefix, inflectedRecordNum, pr);

		return rt && rtSuper;
	}

}
