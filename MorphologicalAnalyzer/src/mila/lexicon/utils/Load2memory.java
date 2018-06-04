package mila.lexicon.utils;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

import mila.lexicon.dbUtils.MWE1record;
import mila.lexicon.dbUtils.MWEinflectionsRecord;
import mila.lexicon.dbUtils.MWErecord;

/*
 * Created on 28/11/2005
 */

/**
 * @author daliabo
 * 
 *         use sort of unix to create files "dindlections.data" and
 *         "dprefixes.data"
 * 
 *         sort inflections.data > dinflections.data sort prefixes.data >
 *         dprefixes.data
 */
public class Load2memory {

	private static Inflections inflections = null;

	private static MWinflections mwinflections = null;

	private static MWE1records mwe1 = null;

	private static MWErecords mwe2 = null;
	private static MWErecords mwe3 = null;
	private static MWErecords mwe4 = null;

	private static Prefixes prefixes = null;

	private static Gimatria gimatria = null;

	private static boolean sortFile = true;

	// ---------------------------------------------------------------------------------
	public static Gimatria loadGimatria(String gimatriaFile) {
		// we already know the number of gimatria entries - improves performance
		gimatria = new Gimatria(1044);
		FileInputStream fileInputStream = null;
		BufferedReader in = null;
		String line = "";
		String transliterated = "";
		String val = "";
		try {
			fileInputStream = new FileInputStream(gimatriaFile);
			in = new BufferedReader(new InputStreamReader(fileInputStream));
			while ((line = in.readLine()) != null) {
				StringTokenizer gimatriaFields = new StringTokenizer(line, "|");
				transliterated = gimatriaFields.nextToken();
				transliterated = transliterated.replaceAll("%22", "\"");
				val = gimatriaFields.nextToken();
				gimatria.put(transliterated, val);
			}
			in.close();
		} catch (Exception e) {
			System.err.println("gimatria file not found");
			System.err.println("Exiting...");
			System.exit(1);
		}
		return gimatria;
	}

	// -------------------------------------------------------------------------------------------------------
	public static Inflections loadInflections(String dinflectionsFile) {
		// System.out.println("(F) Load2memory:loadInflections()");
		inflections = new Inflections(840000);
		FileInputStream fileInputStream = null;
		BufferedReader in = null;
		try {
			fileInputStream = new FileInputStream(dinflectionsFile);
			in = new BufferedReader(new InputStreamReader(fileInputStream));
			String decodedInflectionRecord = "";
			String transliterated = "";
			ArrayList<String> chain = null;
			boolean first = true;
			String currentTransliterated = "";
			while ((decodedInflectionRecord = in.readLine()) != null) {
				StringTokenizer inflectionsFields = new StringTokenizer(decodedInflectionRecord, "|");
				transliterated = inflectionsFields.nextToken();
				if (!sortFile)
					inflections.put(transliterated, decodedInflectionRecord);
				else {
					if (first) {
						chain = new ArrayList<String>();
						first = false;
						chain.add(decodedInflectionRecord);
						currentTransliterated = transliterated;
					} else {
						if (currentTransliterated.equals(transliterated))
							chain.add(decodedInflectionRecord);
						else {
							inflections.sput(currentTransliterated, chain);
							chain = new ArrayList<String>();
							chain.add(decodedInflectionRecord);
							currentTransliterated = transliterated;
						}
					}
				}
			}
			// for the last one
			inflections.sput(currentTransliterated, chain);
			in.close();
		} catch (Exception e) {
			// e.printStackTrace();
			System.err.println("dinflections file not found");
			System.err.println("Exiting...");
			System.exit(1);
		}
		return inflections;
	}

	// -------------------------------------------------------------------------------------------------------
	public static MWE1records LoadMwe1Records(String dmwe1File) {
		mwe1 = new MWE1records(5000);
		FileInputStream fileInputStream = null;
		BufferedReader in = null;

		try {
			fileInputStream = new FileInputStream(dmwe1File);
			in = new BufferedReader(new InputStreamReader(fileInputStream));

			String decodedMwe1Record = "";

			String id = "";
			String transliterated = "";
			String surface = "";
			String pos = "";
			String consecutive = "";
			String type = "";

			while ((decodedMwe1Record = in.readLine()) != null) {
				MWE1record mwe1Rec = new MWE1record();
				StringTokenizer inflectionsFields = new StringTokenizer(decodedMwe1Record, "|"); // tokenize

				transliterated = inflectionsFields.nextToken();
				id = inflectionsFields.nextToken();
				surface = inflectionsFields.nextToken();
				pos = inflectionsFields.nextToken();
				consecutive = inflectionsFields.nextToken();
				type = inflectionsFields.nextToken();

				mwe1Rec.setTransliterated(transliterated); // create record
				mwe1Rec.setSurface(surface);
				mwe1Rec.setPos(pos);
				mwe1Rec.setId(id);
				mwe1Rec.setType(type);
				mwe1Rec.setConsecutive(consecutive);

				mwe1.put(transliterated, mwe1Rec);
			}

			in.close();
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("dmwe1 file not found");
			System.err.println("Exiting...");
			System.exit(1);
		}
		return mwe1;
	}

	// -------------------------------------------------------------------------------------------------------
	/**
	 * loads the mwe 2 3 and 4 to a MWErecords structure
	 * 
	 * @param dmweFile
	 * @return
	 */
	public static MWErecords LoadMwe3Records(String dmweFile) {
		MWErecords mwe3 = new MWErecords(5000);
		// mwe3 = new MWErecords(5000, 1);
		FileInputStream fileInputStream = null;
		BufferedReader in = null;

		try {
			fileInputStream = new FileInputStream(dmweFile);
			in = new BufferedReader(new InputStreamReader(fileInputStream));

			String decodedMweRecord = "";

			String aid = "";
			String id = "";
			String formerItemId = "";
			String surface = "";
			String transliterated = "";
			String consecutive = "";
			String lexiconId = "";
			String transliteratedLexiconItem = "";
			String dottedLexiconItem = "";
			String undottedLexiconItem = "";
			String mwTransliterated = "";
			String mwUndotted = "";
			String PGN = "";
			String spelling = "";
			String register = "";
			String gender = "";
			String number = "";
			String definiteness = "";

			while ((decodedMweRecord = in.readLine()) != null) {
				MWErecord mweRec = new MWErecord();
				String[] array = decodedMweRecord.split("\\|", -1);
				// cause stringtokenizer cant get empty tokens
				int index = 0;

				transliterated = array[index++];
				aid = array[index++];
				id = array[index++];
				formerItemId = array[index++];
				surface = array[index++];
				consecutive = array[index++];
				lexiconId = array[index++];
				transliteratedLexiconItem = array[index++];
				dottedLexiconItem = array[index++];
				undottedLexiconItem = array[index++];
				mwTransliterated = array[index++];
				mwUndotted = array[index++];
				PGN = array[index++];
				spelling = array[index++];
				register = array[index++];
				gender = array[index++];
				number = array[index++];
				definiteness = array[index++];
				mweRec.setTransliterated(transliterated); // create record
				mweRec.setAid(aid);
				mweRec.setId(id);
				mweRec.setFormerItemId(formerItemId);
				mweRec.setSurface(surface);
				mweRec.setConsecutive(consecutive);
				mweRec.setLexiconId(lexiconId);
				mweRec.setTransliteratedLexiconItem(transliteratedLexiconItem);
				mweRec.setDottedLexiconItem(dottedLexiconItem);
				mweRec.setUndottedLexiconItem(undottedLexiconItem);
				mweRec.setMwTransliterated(mwTransliterated);
				mweRec.setMwUndotted(mwUndotted);
				mweRec.setPGN(PGN);
				mweRec.setSpelling(spelling);
				mweRec.setRegister(register);
				mweRec.setGender(gender);
				mweRec.setNumber(number);
				mweRec.setDefiniteness(definiteness);

				mwe3.put(transliterated, mweRec);
			}

			in.close();
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("dmwe file not found");
			System.err.println("Exiting...");
			System.exit(1);
		}

		return mwe3;
	}

	// -------------------------------------------------------------------------------------------------------
	/**
	 * loads the mwe 2 3 and 4 to a MWErecords structure
	 * 
	 * @param dmweFile
	 * @return
	 */
	public static MWErecords LoadMwe4Records(String dmweFile) {
		mwe4 = new MWErecords(5000);
		FileInputStream fileInputStream = null;
		BufferedReader in = null;

		try {
			fileInputStream = new FileInputStream(dmweFile);
			in = new BufferedReader(new InputStreamReader(fileInputStream));

			String decodedMweRecord = "";

			String aid = "";
			String id = "";
			String formerItemId = "";
			String surface = "";
			String transliterated = "";
			String consecutive = "";
			String lexiconId = "";
			String transliteratedLexiconItem = "";
			String dottedLexiconItem = "";
			String undottedLexiconItem = "";
			String mwTransliterated = "";
			String mwUndotted = "";
			String PGN = "";
			String spelling = "";
			String register = "";
			String gender = "";
			String number = "";
			String definiteness = "";

			while ((decodedMweRecord = in.readLine()) != null) {
				MWErecord mweRec = new MWErecord();
				String[] array = decodedMweRecord.split("\\|", -1);
				// cause string tokenizer cant get empty tokens
				int index = 0;

				transliterated = array[index++];
				aid = array[index++];
				id = array[index++];
				formerItemId = array[index++];
				surface = array[index++];
				consecutive = array[index++];
				lexiconId = array[index++];
				transliteratedLexiconItem = array[index++];
				dottedLexiconItem = array[index++];
				undottedLexiconItem = array[index++];
				mwTransliterated = array[index++];
				mwUndotted = array[index++];
				PGN = array[index++];
				spelling = array[index++];
				register = array[index++];
				gender = array[index++];
				number = array[index++];
				definiteness = array[index++];
				mweRec.setTransliterated(transliterated); // create record
				mweRec.setAid(aid);
				mweRec.setId(id);
				mweRec.setFormerItemId(formerItemId);
				mweRec.setSurface(surface);
				mweRec.setConsecutive(consecutive);
				mweRec.setLexiconId(lexiconId);
				mweRec.setTransliteratedLexiconItem(transliteratedLexiconItem);
				mweRec.setDottedLexiconItem(dottedLexiconItem);
				mweRec.setUndottedLexiconItem(undottedLexiconItem);
				mweRec.setMwTransliterated(mwTransliterated);
				mweRec.setMwUndotted(mwUndotted);
				mweRec.setPGN(PGN);
				mweRec.setSpelling(spelling);
				mweRec.setRegister(register);
				mweRec.setGender(gender);
				mweRec.setNumber(number);
				mweRec.setDefiniteness(definiteness);

				mwe4.put(transliterated, mweRec);
			}

			in.close();
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("dmwe file not found");
			System.err.println("Exiting...");
			System.exit(1);
		}

		return mwe4;
	}

	// -------------------------------------------------------------------------------------------------------
	/**
	 * loads the mwe 2 3 and 4 to a MWErecords structure
	 * 
	 * @param dmweFile
	 * @return
	 */
	public static MWErecords LoadMweRecords(String dmweFile, int tableNum) {
		switch (tableNum) {
		case 2: {
			mwe2 = new MWErecords(5000);
			break;
		}
		case 3: {
			mwe3 = new MWErecords(5000);
			break;
		}
		case 4: {
			mwe4 = new MWErecords(5000);
			break;
		}
		}

		FileInputStream fileInputStream = null;
		BufferedReader in = null;

		try {
			fileInputStream = new FileInputStream(dmweFile);
			in = new BufferedReader(new InputStreamReader(fileInputStream));

			String decodedMweRecord = "";

			String aid = "";
			String id = "";
			String formerItemId = "";
			String surface = "";
			String transliterated = "";
			String consecutive = "";
			String lexiconId = "";
			String transliteratedLexiconItem = "";
			String dottedLexiconItem = "";
			String undottedLexiconItem = "";
			String mwTransliterated = "";
			String mwUndotted = "";
			String PGN = "";
			String spelling = "";
			String register = "";
			String gender = "";
			String number = "";
			String definiteness = "";

			while ((decodedMweRecord = in.readLine()) != null) {
				MWErecord mweRec = new MWErecord();
				String[] array = decodedMweRecord.split("\\|", -1);
				int index = 0;

				transliterated = array[index++];
				aid = array[index++];
				id = array[index++];
				formerItemId = array[index++];
				surface = array[index++];
				consecutive = array[index++];
				lexiconId = array[index++];
				transliteratedLexiconItem = array[index++];
				dottedLexiconItem = array[index++];
				undottedLexiconItem = array[index++];
				mwTransliterated = array[index++];
				mwUndotted = array[index++];
				PGN = array[index++];
				spelling = array[index++];
				register = array[index++];
				gender = array[index++];
				number = array[index++];
				definiteness = array[index++];

				mweRec.setTransliterated(transliterated); // create record
				mweRec.setAid(aid);
				mweRec.setId(id);
				mweRec.setFormerItemId(formerItemId);
				mweRec.setSurface(surface);
				mweRec.setConsecutive(consecutive);
				mweRec.setLexiconId(lexiconId);
				mweRec.setTransliteratedLexiconItem(transliteratedLexiconItem);
				mweRec.setDottedLexiconItem(dottedLexiconItem);
				mweRec.setUndottedLexiconItem(undottedLexiconItem);
				mweRec.setMwTransliterated(mwTransliterated);
				mweRec.setMwUndotted(mwUndotted);
				mweRec.setPGN(PGN);
				mweRec.setSpelling(spelling);
				mweRec.setRegister(register);
				mweRec.setGender(gender);
				mweRec.setNumber(number);
				mweRec.setDefiniteness(definiteness);

				switch (tableNum) {
				case 2: {
					mwe2.put(transliterated, mweRec);
					break;
				}
				case 3: {
					mwe3.put(transliterated, mweRec);
					break;
				}
				case 4: {
					mwe4.put(transliterated, mweRec);
					break;
				}
				}

			}

			in.close();
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("dmwe file not found");
			System.err.println("Exiting...");
			System.exit(1);
		}
		switch (tableNum) {
		case 2: {
			return mwe2;
		}
		case 3: {
			return mwe3;
		}
		case 4: {
			return mwe4;
		}
		}

		return mwe2;
	}

	// -------------------------------------------------------------------------------------------------------
	public static MWinflections LoadMwInflections(String dmwinflectionsFile) {
		// System.out.println("(F) Load2memory:LoadMwInflections()");
		mwinflections = new MWinflections(5000);
		FileInputStream fileInputStream = null;
		BufferedReader in = null;

		try {
			fileInputStream = new FileInputStream(dmwinflectionsFile);
			in = new BufferedReader(new InputStreamReader(fileInputStream));
			String decodedMwinflectionRecord = "";
			String transliterated = "";
			String surface = "";
			String pos = "";
			String mweId = "";
			String type = "";
			Boolean prefix = false;

			while ((decodedMwinflectionRecord = in.readLine()) != null) {
				MWEinflectionsRecord mwInfRec = new MWEinflectionsRecord();
				StringTokenizer inflectionsFields = new StringTokenizer(decodedMwinflectionRecord, "|"); // tokenize
				transliterated = inflectionsFields.nextToken();
				surface = inflectionsFields.nextToken();
				pos = inflectionsFields.nextToken();
				mweId = inflectionsFields.nextToken();
				type = inflectionsFields.nextToken();
				prefix = (inflectionsFields.nextToken()).equals("0") ? false : true;

				mwInfRec.setTransliterated(transliterated); // create record
				mwInfRec.setSurface(surface);
				mwInfRec.setPos(pos);
				mwInfRec.setMweId(mweId);
				mwInfRec.setType(type);
				mwInfRec.setPrefix(prefix);

				mwinflections.put(transliterated, mwInfRec);
			}

			in.close();
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("dmwinflections file not found");
			System.err.println("Exiting...");
			System.exit(1);
		}
		return mwinflections;
	}

	// ---------------------------------------------------------------------------------
	public static Prefixes loadPrefixes(String dprefixesFile) {
		prefixes = new Prefixes(174);
		FileInputStream fileInputStream = null;
		BufferedReader in = null;
		try {
			fileInputStream = new FileInputStream(dprefixesFile);
			in = new BufferedReader(new InputStreamReader(fileInputStream));
			String decodedPrefixesRecord = "";
			String prefix = "";
			ArrayList<String> chain = null;
			boolean first = true;
			String currentPrefix = "";
			while ((decodedPrefixesRecord = in.readLine()) != null) {
				StringTokenizer inflectionsFields = new StringTokenizer(decodedPrefixesRecord, "|");
				prefix = inflectionsFields.nextToken();
				if (!sortFile)
					prefixes.put(prefix, decodedPrefixesRecord);
				else {
					if (first) {
						chain = new ArrayList<String>();
						first = false;
						chain.add(decodedPrefixesRecord);
						currentPrefix = prefix;
					} else {
						if (currentPrefix.equals(prefix))
							chain.add(decodedPrefixesRecord);
						else {
							prefixes.sput(currentPrefix, chain);
							chain = new ArrayList<String>();
							chain.add(decodedPrefixesRecord);
							currentPrefix = prefix;
						}
					}
				}
			}
			// for the last one
			prefixes.sput(currentPrefix, chain);
			in.close();
		} catch (Exception e) {
			System.err.println("dprefixes file not found");
			System.err.println("Exiting...");
			System.exit(1);
		}
		return prefixes;
	}
}
