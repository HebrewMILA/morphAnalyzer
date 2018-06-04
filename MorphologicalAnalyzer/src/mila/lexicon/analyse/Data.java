package mila.lexicon.analyse;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.StringTokenizer;

import mila.dataStructures.DBInflectionsRecord;
import mila.dataStructures.InflectedRecordNum;
import mila.lexicon.dbUtils.PrefixRecord;
import mila.lexicon.utils.Load2memory;
import mila.lexicon.utils.Translate;
import static mila.lexicon.analyse.Constants.*;

/**
 *
 * Data.java Purpose: interface to the databse
 *
 * @author Dalia Bojan
 * @version %G%
 */

public class Data {

	/** path to the inflections data file */
	public static String dinflectionsFile = "";

	/** path to the mwinflections data file */
	public static String dmwinflectionsFile = "";

	/** path to the mwe1 data file */
	public static String dmwe1File = "";

	/** path to the mwe1 data file */
	public static String dmwe2File = "";

	/** path to the mwe1 data file */
	public static String dmwe3File = "";

	/** path to the mwe1 data file */
	public static String dmwe4File = "";

	/** path to the prefixes data file */
	public static String dprefixesFile = "";

	/** path to the gimatria data file */
	public static String gimatriaFile = "";

	/**
	 * A flag to indicate whether we work on data files mode or database mode
	 */
	public static boolean webFlag = true;

	/**
	 * Every entry in the prefixes table can also be break to several prefixes for
	 * example wl consists of two sub prefixes w and l this arrayList holds them
	 */
	static ArrayList<?> prefixesList = null;

	/** holds prefixes data when uploaded to memory */
	public static mila.lexicon.utils.Prefixes prefixes = null;;

	/** holds inflections data when uploaded to memory */
	public static mila.lexicon.utils.Inflections inflections = null;

	/** holds mwinflections data when uploaded to memory */
	public static mila.lexicon.utils.MWinflections mwinflections = null;

	/** holds mwe1 records data when uploaded to memory */
	public static mila.lexicon.utils.MWE1records mwe1 = null;

	/** holds mwe1 records data when uploaded to memory */
	public static mila.lexicon.utils.MWErecords mwe2 = null;

	/** holds mwe1 records data when uploaded to memory */
	public static mila.lexicon.utils.MWErecords mwe3 = null;

	/** holds mwe1 records data when uploaded to memory */
	public static mila.lexicon.utils.MWErecords mwe4 = null;

	/** holds gimatria data when uploaded to memory */
	public static mila.lexicon.utils.Gimatria gimatrias = null;

	/**
	 * This method populate a data structure with values from the prefix table/ data
	 * file the value - means unspecified
	 *
	 * @param j
	 * @return
	 */
	public static PrefixRecord analyzePrefixList(int j) {
		// System.out.println("(F) analyzePrefixList("+j+")" );
		PrefixRecord pr = new PrefixRecord();
		// gete from databse
		if (webFlag) {
			// System.out.println("(F) Data:analyzePrefixList() webFlag = TRUE
			// ");
			pr = (PrefixRecord) prefixesList.get(j);
		}
		// get from files
		else {
			StringTokenizer st = null;
			String field = "";
			st = new StringTokenizer((String) prefixesList.get(j), "|");
			// System.out.println((String) prefixesList.get(j));
			pr.setPrefix(st.nextToken());
			// System.out.println("prefix =" + pr.getPrefix());
			pr.setDescription(st.nextToken());
			// System.out.println("(F) analyzePrefixList description =" +
			// pr.getDescription());
			field = st.nextToken();
			if (field.equals("-")) {
				pr.setDefiniteArticleTag(false);
			} else {
				pr.setDefiniteArticleTag(true);
			}
			// System.out.println("DefiniteArticleTag =" +
			// pr.isDefiniteArticleTag());
			field = st.nextToken();
			if (field.equals("-")) {
				pr.setDefArtHE(false);
			} else {
				pr.setDefArtHE(true);
			}
			// System.out.println("DefArtHE =" + pr.isDefArtHE());
			field = st.nextToken();
			if (field.equals("-")) {
				pr.setRelHE(false);
			} else {
				pr.setRelHE(true);
			}
			// System.out.println("RelHE =" + pr.isRelHE());
			field = st.nextToken();
			if (field.equals("-")) {
				pr.setAdverbKAF(false);
			} else {
				pr.setAdverbKAF(true);
			}
			// System.out.println("AdverbKAF =" + pr.isAdverbKAF());
			field = st.nextToken();
			if (field.equals("-")) {
				pr.setSubConOrRelSHIN(false);
			} else {
				pr.setSubConOrRelSHIN(true);
			}
			// System.out.println("SubConOrRelSHIN =" + pr.isSubConOrRelSHIN());
			field = st.nextToken();
			if (field.equals("-")) {
				pr.setTempSubConKAFSHIN(false);
			} else {
				pr.setTempSubConKAFSHIN(true);
			}
			// System.out.println("TempSubConKAFSHIN =" +
			// pr.isTempSubConKAFSHIN());
			field = st.nextToken();
			if (field.equals("-")) {
				pr.setTempSubConMEMSHIN(false);
			} else {
				pr.setTempSubConMEMSHIN(true);
			}
			// System.out.println("TempSubConMEMSHIN =" +
			// pr.isTempSubConMEMSHIN());
			field = st.nextToken();
			if (field.equals("-")) {
				pr.setTempSubConLAMEDKAFSHIN(false);
			} else {
				pr.setTempSubConLAMEDKAFSHIN(true);
			}
			// System.out.println("TempSubConLAMEDKAFSHIN ="
			// + pr.isTempSubConLAMEDKAFSHIN());
			field = st.nextToken();
			if (field.equals("-")) {
				pr.setTempSubConBETSHIN(false);
			} else {
				pr.setTempSubConBETSHIN(true);
			}
			field = st.nextToken();
			if (field.equals("-")) {
				pr.setRelativizerTag(false);
			} else {
				pr.setRelativizerTag(true);
			}
			// System.out.println("RelativizerTag =" + pr.isRelativizerTag());
			field = st.nextToken();
			if (field.equals("-")) {
				pr.setTemporalSubConjTag(false);
			} else {
				pr.setTemporalSubConjTag(true);
			}
			// System.out.println("TemporalSubConjTag =" +
			// pr.isTemporalSubConjTag());
			field = st.nextToken();
			if (field.equals("-")) {
				pr.setSubordinatingConjunctionTag(false);
			} else {
				pr.setSubordinatingConjunctionTag(true);
			}
			// System.out.println("SubordinatingConjunctionTag ="
			// + pr.isSubordinatingConjunctionTag());
			field = st.nextToken();
			if (field.equals("-")) {
				pr.setPrefPartUnit(false);
			} else {
				pr.setPrefPartUnit(true);
			}
			// System.out.println("PrefPartUnit =" + pr.isPrefPartUnit());
			field = st.nextToken();
			if (field.equals("-")) {
				pr.setPrepBET(false);
			} else {
				pr.setPrepBET(true);
			}
			// System.out.println("PrepBET =" + pr.isPrepBET());
			field = st.nextToken();
			if (field.equals("-")) {
				pr.setPrepKAF(false);
			} else {
				pr.setPrepKAF(true);
			}
			// System.out.println("PrepKAF =" + pr.isPrepKAF());
			field = st.nextToken();
			if (field.equals("-")) {
				pr.setPrepLAMED(false);
			} else {
				pr.setPrepLAMED(true);
			}
			// System.out.println("PrepLAMED =" + pr.isPrepLAMED());
			field = st.nextToken();
			if (field.equals("-")) {
				pr.setPrepMEM(false);
			} else {
				pr.setPrepMEM(true);
			}
			// System.out.println("PrepMEM =" + pr.isPrepMEM());
			field = st.nextToken();
			if (field.equals("-")) {
				pr.setPrepositionTag(false);
			} else {
				pr.setPrepositionTag(true);
			}
			// System.out.println("PrepositionTag =" + pr.isPrepositionTag());
			field = st.nextToken();
			if (field.equals("-")) {
				pr.setConjunctionTag(false);
			} else {
				pr.setConjunctionTag(true);
				// System.out.println("PrepositionTag =" +
				// pr.isPrepositionTag());
			}
		}
		return pr;
	}

	/**
	 * This method is used in data files mode Each line in the data file matches an
	 * entry in the inflections table. The various fields in the inflections table
	 * appears in the data file line separated by the delimiter|
	 *
	 * @param inflectionsList
	 *           - all the inflections data file entries relevant to the key
	 * @param i
	 *           - index of the current item in the inflections list
	 * @return - populated data structure (record)
	 * @throws Exception
	 */
	public static DBInflectionsRecord extractDataFileData(ArrayList<String> inflectionsList, int i) throws Exception {
		DBInflectionsRecord dbInflectionsRec = new DBInflectionsRecord();
		StringTokenizer st = null;
		st = new StringTokenizer(inflectionsList.get(i), "|");
		// System.out.println((String) inflectionsList.get(i));

		String transliterated = st.nextToken();
		dbInflectionsRec.setTransliterated(transliterated);

		// ////////////////////////////////////////////////
		String codeBasePos = st.nextToken();
		String strPos = Num2Str.strNum2StrPos(codeBasePos, "", transliterated);
		dbInflectionsRec.setBasePos(strPos);
		// //////////////////////////////////////////////

		String surface = "";// st.nextToken();
		surface = Translate.Eng2Heb(transliterated);
		dbInflectionsRec.setSurface(surface);

		String lexiconItemTransliterated = "";

		// /////////////////////////////////////////////////////////////////////////
		String codeRegister = st.nextToken();
		String register = Num2Str.strNum2StrRegister(codeRegister, "", transliterated);
		dbInflectionsRec.setRegister(register);
		// /////////////////////////////////////////////////////////////////////

		// //////////////////////////////////////////////////////////
		String codeSpelling = st.nextToken();
		String spelling = Num2Str.strNum2StrSpelling(codeSpelling, "", transliterated);
		dbInflectionsRec.setSpelling(spelling);
		// //////////////////////////////////////////////////////////

		// /////////////////////////////////////////////////
		String codeStatus = st.nextToken();
		String strStatus = Num2Str.strNum2StrStatus(codeStatus, lexiconItemTransliterated, transliterated);
		dbInflectionsRec.setStatus(strStatus);
		// ////////////////////////////////////////////////

		String lexiconPointer = st.nextToken();
		dbInflectionsRec.setBaseLexiconPointer(lexiconPointer);

		String PGN = st.nextToken();

		dbInflectionsRec.setPGN(PGN);

		// /////////////////////////////////////////////////
		String codeBinyan = st.nextToken();
		String binyan = Num2Str.strNum2StrBinyan(codeBinyan, lexiconItemTransliterated, transliterated);
		dbInflectionsRec.setBinyan(binyan);
		// System.out.println(binyan);
		// /////////////////////////////////////////////////

		// //////////////////////////////////////////////////
		String codeTense = st.nextToken();
		String tense = Num2Str.strNum2StrTense(codeTense, lexiconItemTransliterated, transliterated);
		dbInflectionsRec.setTense(tense);
		// System.out.println(tense);
		// //////////////////////////////////////////////////

		String root = st.nextToken();
		// System.out.println("(F) Data:extractDataFileData() root="+root);
		dbInflectionsRec.setRoot(root);

		String baseNumber = st.nextToken();
		dbInflectionsRec.setBaseNumber(baseNumber);

		String baseGender = st.nextToken();
		dbInflectionsRec.setBaseGender(baseGender);

		// //////////////////////////////////////////////////////
		String codeHAttribute = st.nextToken();
		String hAttribute = Num2Str.strNum2StrhAttribute(codeHAttribute, lexiconItemTransliterated, transliterated);
		dbInflectionsRec.setHAttribute(hAttribute);
		// ////////////////////////////////////////////////////

		String person = st.nextToken();
		if (person.charAt(0) == '4') {
			person = "any";
		} else if (person.charAt(0) == '-') {
			person = "unspecified";
		}
		dbInflectionsRec.setBasePerson(person);

		// /////////////////////////////////////////////////
		String codeSuffixFunction = st.nextToken();
		String suffixFunction = Num2Str.strNum2StrSuffixFunction(codeSuffixFunction, lexiconItemTransliterated,
				transliterated);
		dbInflectionsRec.setSuffixFunction(suffixFunction);
		// ////////////////////////////////////////////////

		String dottedLexiconItem = st.nextToken();
		if (dottedLexiconItem.charAt(0) == '-') {
			dottedLexiconItem = "";
		}
		dbInflectionsRec.setDottedLexiconItem(dottedLexiconItem);

		String baseUndottedLItem = st.nextToken();
		dbInflectionsRec.setBaseUndottedLItem(baseUndottedLItem);

		String polarity = st.nextToken();
		dbInflectionsRec.setPolarity(polarity);

		String value = st.nextToken();
		dbInflectionsRec.setValue(value);

		String type = st.nextToken();
		dbInflectionsRec.setType(type);

		char foreign = st.nextToken().charAt(0);
		dbInflectionsRec.setForeign(foreign);

		char prefix = st.nextToken().charAt(0);
		if (prefix == '-') {
			prefix = 'u';
		}
		dbInflectionsRec.setPrefixPerEntry(prefix);

		return dbInflectionsRec;
	}

	/**
	 * The method handles gimatria identification It looks in the gimatria
	 * table/data file for the existence of the gimatria provided as a key In this
	 * case as opposed to prefixes or inflections, the return value is not an array
	 * list but the numeric value of the gimatria
	 *
	 * @param key
	 *           - candidate to be gimatria
	 * @return - the numeric value of the gimatria
	 * @throws Exception
	 */
	public static int getGimatrias(String key) throws Exception {
		int val = 0;
		// get from databse
		if (webFlag) {
			// System.out.println("(F) Data:getGimatrias() webFlag = TRUE ");
			val = mila.lexicon.dbUtils.Gimatria.get(key);
		} else {// get from lists
			val = gimatrias.get(key);
		}
		return val;
	}

	/**
	 * This method handles getting all the relevant analysis according to the key
	 * from the inflections table or from the data file table
	 *
	 * @param key
	 *           - the transliterated form of the inflected item
	 * @return
	 * @throws Exception
	 */
	public static ArrayList<DBInflectionsRecord> getInflections(final String key) throws Exception {
		ArrayList<DBInflectionsRecord> inflectionsList = new ArrayList<DBInflectionsRecord>();
		ArrayList<String> dataFileInflectionsList = new ArrayList<String>();

		if (webFlag) // get from database
			inflectionsList = webGetInflections(key);

		else { // get from data file
			dataFileInflectionsList = inflections.get(key);
			DBInflectionsRecord dbInflectionsRec = null;
			if (dataFileInflectionsList != null) {
				int dataFileInflectionsListSize = dataFileInflectionsList.size();

				for (int dataFileInflectionsListIndex = 0; dataFileInflectionsListIndex < dataFileInflectionsListSize; dataFileInflectionsListIndex++) {
					dbInflectionsRec = extractDataFileData(dataFileInflectionsList, dataFileInflectionsListIndex);
					inflectionsList.add(dbInflectionsRec);
				}
			}

		}

		/*
		 * This piece of code checks for every inflection if its not a basic one (i.e if
		 * it has an alternateLexiconPointer != 0) and if it isn't a one changes the
		 * lexiconPointer to be the alternatePointer
		 */
		for (DBInflectionsRecord inf : inflectionsList) {
			String alternatePointer = inf.getBaseAlternatePointer();
			if (alternatePointer != null && !alternatePointer.equals("0") && !alternatePointer.equals("null"))
				inf.setBaseLexiconPointer(alternatePointer);
		}

		/*
		 * Since Alon wanted the inflections from the base word be included together
		 * with the inflections of alternate words for each alternate word (i.e
		 * alternate words include both their own inflections and the basic word's
		 * inflcetions) the previous action creates duplicates hence we filter them out
		 * here.
		 */
		ArrayList<DBInflectionsRecord> al = new ArrayList<DBInflectionsRecord>(inflectionsList);
		HashSet<DBInflectionsRecord> hs = new HashSet<DBInflectionsRecord>();
		hs.addAll(al);
		al.clear();
		al.addAll(hs);

		return al;
	}

	/**
	 * The method handles getting all the relevant prefixes from the prefix table or
	 * prefix data file for example there can be two options for the prefix wb
	 * option 1 - w + b which is not definited option 2 - w + b which is definited
	 *
	 * @param key
	 * @return the number of relevant prefixes which were found
	 * @throws Exception
	 */
	public static int getPrefixes(String key) throws Exception {
		// System.out.println("(F) getPrefixes("+key+")");
		int size = 0;
		// get from database
		if (webFlag) {
			// System.out.println("(F) Data:getPrefixes() webFlag = TRUE ");
			mila.lexicon.dbUtils.Prefixes pref = new mila.lexicon.dbUtils.Prefixes();
			prefixesList = pref.get(key);
			// get from lists
		} else {
			prefixesList = prefixes.get(key);
		}
		if (prefixesList != null) {
			size = prefixesList.size();
		}
		return size;
	}

	/**
	 * This method is used only on data file working mode
	 *
	 * @param myVerboseFlag
	 */
	public static void init(boolean myVerboseFlag) {
		// System.out.println("(F) Data.init() ");
		// get from lists

		if (!webFlag && inflections == null) {
			System.out.println("loading data to memory...(this may take a few minutes)");

			// load to hashmap inflections file

			System.out.print("(F) loading dinflectionsFile...");
			inflections = Load2memory.loadInflections(dinflectionsFile);
			System.out.println("OK");
			System.out.print("(F) loading dmwinflectionsFile...");
			mwinflections = Load2memory.LoadMwInflections(dmwinflectionsFile);
			System.out.println("OK");
			System.out.print("(F) loading dmwe1File...");
			mwe1 = Load2memory.LoadMwe1Records(dmwe1File);
			System.out.println("OK");
			System.out.print("(F) loading dmwe2File...");
			mwe2 = Load2memory.LoadMweRecords(dmwe2File, 2);
			System.out.println("OK");
			// System.out.println("(F) data:init() mwe2 was loaded size = " +
			// mwe2.GetSize());
			System.out.print("(F) loading dmwe3File...");
			mwe3 = Load2memory.LoadMweRecords(dmwe3File, 3);
			System.out.println("OK");
			// System.out.println("(F) data:init() mwe3 was loaded size = " +
			// mwe3.GetSize());
			System.out.print("(F) loading dmwe4File...");
			mwe4 = Load2memory.LoadMwe4Records(dmwe4File);
			System.out.println("OK");
			// System.out.println("(F) data:init() mwe4 was loaded size = " +
			// mwe4.GetSize());

			// System.out.println("End Loading Inflections file ");
			// load to hashmap prefixes file
			System.out.print("(F) loading dprefixesFile...");
			prefixes = Load2memory.loadPrefixes(dprefixesFile);
			System.out.println("OK");
			// System.out.println("End Loading Prefixes file ");
			// long afterLoadTime = printTimesHandling(startTime);
			// load to hashmap gimatria file
			System.out.print("(F) loading gimatriaFile...");
			gimatrias = Load2memory.loadGimatria(gimatriaFile);
			System.out.println("OK");
			// System.out.println("End Loading gimatrias file ");
			// long afterLoadTime = printTimesHandling(startTime);
		}

	}

	/**
	 * This method is used by HMMTagger:MorphMultTaggerFormat.parseXML when the
	 * morphological analyzer provide an unknown analysis the program which
	 * translate the morphological analyzer output to the tagger format replaces
	 * every unknown analysis with propername analysis It also try to find out
	 * whether there is a prefix for the propername
	 *
	 * @param dprefixesFile
	 */
	public static void init(String dprefixesFile) {
		// load to hashmap prefixes file
		// System.out.println("(F) init: calling Load2memory.loadPrefixes");
		// prefixes = Load2memory.loadPrefixes(dprefixesFile); // REMOVED BY
		// YOSSI 21.8.11
	}

	/**
	 * This method populate a data structure with values from the inflections table/
	 * data file The values are codes for making performance better when doing
	 * equality tests
	 *
	 * @param dbInfRec
	 * @param word
	 * @return
	 * @throws Exception
	 */
	public static InflectedRecordNum Str2NumBeforeValidation(final DBInflectionsRecord dbInfRec, final String word)
			throws Exception {
		// get from the database

		InflectedRecordNum infRecNum = new InflectedRecordNum();

		String transliteratedLexiconItem = dbInfRec.getBaseTransliteratedLItem();
		infRecNum.setTransliteratedLexiconItem(transliteratedLexiconItem);

		String construct = dbInfRec.getStatus();
		ENUM_STATUS constructi = Str2Num.str2NumConstruct(construct, transliteratedLexiconItem, word);
		infRecNum.setStatus(constructi);

		String hAttribute = dbInfRec.getHAttribute();
		ENUM_HATTRIBUTE hAttributei = Str2Num.str2NumHAttribute(hAttribute, transliteratedLexiconItem, word);
		infRecNum.setHAttribute(hAttributei);

		String suffixFunction = dbInfRec.getSuffixFunction();
		ENUM_SUFFIX_FUNCTION suffixFunctioni = Str2Num.str2NumSuffixFunction(suffixFunction, transliteratedLexiconItem,
				word);
		infRecNum.setSuffixFunction(suffixFunctioni);

		String pos = dbInfRec.getBasePos();
		ENUM_POS posi = Str2Num.str2NumPos(pos, transliteratedLexiconItem, word);
		infRecNum.setPos(posi);
		ENUM_OUTPUT_PATTERN outputPatterni = Str2Num.str2NumOutputPattern(pos, transliteratedLexiconItem, word);
		infRecNum.setOutputPattern(outputPatterni);

		char prefixPerEntry = dbInfRec.getPrefixPerEntry();
		infRecNum.setPrefixPerEntry(prefixPerEntry);

		// tense and binyan are relevant only for verb, participle,
		// passiveParticiple, independentInfinitive
		if (posi == ENUM_POS.VERB && word.indexOf('"') == -1 || posi == ENUM_POS.PARTICIPLE && word.indexOf('"') == -1
				|| posi == ENUM_POS.PASSIVEPARTICIPLE || posi == ENUM_POS.INDEPENDENTINFINITIVE) {
			String binyan = dbInfRec.getBinyan();
			ENUM_BINYAN binyani = Str2Num.str2NumBinyan(binyan, transliteratedLexiconItem, word);
			infRecNum.setBinyan(binyani);

			String tense = dbInfRec.getTense();
			ENUM_TENSE tensei = Str2Num.str2NumTense(tense, transliteratedLexiconItem, word);
			infRecNum.setTense(tensei);
		}
		if (posi == ENUM_POS.PARTICIPLE && word.indexOf('"') == -1 || posi == ENUM_POS.PASSIVEPARTICIPLE) {
			String participleType = dbInfRec.getType();
			int participleTypei = Str2Num.str2NumStrParticipleType(participleType, transliteratedLexiconItem, word);
			infRecNum.setType(participleTypei);
		}
		if (posi == ENUM_POS.QUANTIFIER) {
			String quantifierType = dbInfRec.getType();
			int quantifierTypei = Str2Num.str2NumStrQuantifierType(quantifierType, transliteratedLexiconItem, word);
			infRecNum.setType(quantifierTypei);
		} else if (posi == ENUM_POS.INTERROGATIVE) {
			String interrogativeType = dbInfRec.getType();
			int interrogativei = Str2Num.str2NumStrInterrogativeType(interrogativeType, transliteratedLexiconItem, word);
			infRecNum.setType(interrogativei);
		}

		return infRecNum;
	}

	/**
	 * This method handles getting all the relevant analysis according to the key
	 * from the inflections table
	 *
	 * @param key
	 *           - the transliterated form of the inflected item
	 * @return an arrayList of all the analysis conform to the key
	 */
	private static ArrayList<DBInflectionsRecord> webGetInflections(final String key) {

		ArrayList<DBInflectionsRecord> inflectionsList = new ArrayList<DBInflectionsRecord>();
		mila.lexicon.dbUtils.Inflections inf = new mila.lexicon.dbUtils.Inflections();

		try {
			inflectionsList = inf.get(key);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return inflectionsList;
	}

	/**
	 * Empty constructor
	 */
	public Data() {
	}

	/**
	 * In case of data files mode working we initialize the data file with the data
	 * files paths
	 *
	 * @param dinflectionsFile
	 *           - inflections data file path
	 * @param dprefixesFile
	 *           - prefixes data file path
	 * @param gimatriaFile
	 *           - gimatria data file path
	 * @param webFlag
	 *           - a flag to indicate whether it is working in data file mode or
	 *           database mode
	 */
	public Data(String dinflectionsFile, String dprefixesFile, String gimatriaFile, String dmwinflections,
			String dmwe1File, String dmwe2File, String dmwe3File, String dmwe4File, boolean webFlag) {
		Data.dinflectionsFile = dinflectionsFile;
		Data.dprefixesFile = dprefixesFile;
		Data.gimatriaFile = gimatriaFile;
		Data.webFlag = webFlag;
		Data.dmwinflectionsFile = dmwinflections;
		Data.dmwe1File = dmwe1File;
		Data.dmwe2File = dmwe2File;
		Data.dmwe3File = dmwe3File;
		Data.dmwe4File = dmwe4File;
	}

	/**
	 * @return Returns the inflections.
	 */
	public mila.lexicon.utils.Inflections getInflections() {
		return inflections;
	}

	/**
	 * @return Returns the prefixes.
	 */
	public mila.lexicon.utils.Prefixes getPrefixes() {
		return prefixes;
	}

	/**
	 * @return Returns the prefixesList.
	 */
	public ArrayList<?> getPrefixesList() {
		return prefixesList;
	}

	/**
	 * @return Returns the webFlag.
	 */
	public boolean isWebFlag() {
		return webFlag;
	}

}
