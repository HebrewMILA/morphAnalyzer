package mila.mw;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.bind.JAXBException;

import mila.dataStructures.CustomResult;
import mila.generated.AnalysisType;
import mila.generated.ArticleType;
import mila.generated.BaseType;
import mila.generated.MWEType;
import mila.generated.ObjectFactory;
import mila.generated.ParagraphType;
import mila.generated.PrefixType;
import mila.generated.ProperNameType;
import mila.generated.SentenceType;
import mila.generated.SuffixType;
import mila.generated.TokenType;
import mila.generated.UnknownType;
import mila.lexicon.analyse.Data;
import mila.lexicon.dbUtils.Connected;
import mila.lexicon.dbUtils.MWE1record;
import mila.lexicon.dbUtils.MWErecord;
import mila.lexicon.utils.StringUtils;
import mila.lexicon.utils.Translate;

public class PostProcessor1 extends Connected {
	public static void main(String[] args) throws Exception {
		final PostProcessor1 postProcessor = new PostProcessor1();
		final String inputFile = args[0];
		final String outputFile = args[1];
		postProcessor.process(inputFile, outputFile);
	}

	boolean useDataFiles = false; // like webFlag = true
	boolean removeAnalysisFlag = false;
	boolean inMultyWord = false;
	int candidateForRemove = -1;
	String multiWord = "";

	int lastMWTokenIndex = -1;

	public PostProcessor1() // and an empty one for default
	{
	}

	public PostProcessor1(boolean _useDataFiles) {
		useDataFiles = _useDataFiles;
		// System.err.println("(D) Postprocessor initialied, data files? "
		// + _useDataFiles);
	}

	public void arrangeAnalysisIdAfterRemove(TokenType token) {
		final List<AnalysisType> analysisList = token.getAnalysis();
		final int analysisListSize = analysisList.size();
		for (int i = 0; i < analysisListSize; i++) {
			final AnalysisType analysis = analysisList.get(i);
			final int newId = i + 1;
			analysis.setId(String.valueOf(newId));
			token.getAnalysis().set(i, analysis);

		}

	}

	// -----------------------------------------------------------------------------------------------------------------------------------
	public boolean checkBackwords(String id, String expectedPrevId, String prevId, String transliterated,
			ArrayList<String> prevTransliteratedList, AnalysisType analysis, TokenType prevToken, TokenType token)
			throws UnsupportedEncodingException, JAXBException {
		if (this.useDataFiles) {
			return this.checkBackwordsNew(id, expectedPrevId, prevId, transliterated, prevTransliteratedList, analysis,
					prevToken, token);
		} else {
			return this.checkBackwordsOld(id, expectedPrevId, prevId, transliterated, prevTransliteratedList, analysis,
					prevToken, token);
		}
	}

	public boolean checkBackwordsNew(String id, String expectedPrevId, String prevId, String transliterated,
			ArrayList<String> prevTransliteratedList, AnalysisType analysis, TokenType prevToken, TokenType token)
			throws UnsupportedEncodingException, JAXBException {
		// System.out.println("\n(F) PostProcessor1:checkBackwords(id =
		// "+id+",expecedPrevId = "+expectedPrevId+",prevId =
		// "+prevId+","+transliterated+")");
		boolean rt = false;
		final int prevTransliteratedListSize = prevTransliteratedList.size();
		final int intId = Integer.parseInt(id);
		if (intId > prevTransliteratedListSize) {
			return rt;
		}

		// injections. did not finish since found no use
		// of it.
		String lexiconId = "-1";
		String dottedLexiconItem = "";
		String undottedLexiconItem = "";
		String pos = "";
		String type = "";
		String transliteratedLexiconItem = "";
		transliterated = transliterated.replaceAll("'", "\\\\'");
		String PGN = "";
		String lastPGN = "";
		String spelling = "";
		String lastSpelling = "";
		String register = "";
		String gender = "";
		String number = "";
		String mwTransliterated = "";
		String mwUndotted = "";
		ObjectFactory objFactory = null;
		AnalysisType newAnalysis = null;
		String lastLexiconId = "";
		String phraze = "";
		String prevTransliterated = "";
		String definiteness = "";
		int resultSetSize = 0;

		for (int prevTransliteratedListIndex = prevTransliteratedListSize
				- 2; prevTransliteratedListIndex >= 0; prevTransliteratedListIndex--) {
			prevTransliterated = prevTransliteratedList.get(prevTransliteratedListIndex);
			prevTransliterated = prevTransliterated.replaceAll("'", "\\\\'");

			final ArrayList<CustomResult> customResultSet = new ArrayList<CustomResult>(); // the
			// result
			// set
			if (expectedPrevId.equals("1")) {
				String.format(
						"SELECT mwe2.spelling, mwe2.register, mwe2.gender, mwe2.number,mwe2.definiteness, mwe2.mwTransliterated, mwe2.mwUndotted, "
								+ "mwe%1$s.lexiconId,  mwe%1$s.undottedLexiconItem , mwe%1$s.PGN, mwe%1$s.dottedLexiconItem, mwe%1$s.transliteratedLexiconItem, "
								+ "mwe1.pos,  mwe1.type " + "FROM  mwe%1$s,  mwe1 "
								+ "WHERE mwe%1$s.transliterated=? AND mwe1.transliterated=? and mwe%1$s.formerItemId=mwe1.id",
						id);

				// #####################################
				// this part replaces the mysql part by using data files instead
				// and simulating the queries
				final ArrayList<MWE1record> result = MWE1data.getMWE1records(prevTransliterated); // get
				// first
				final ArrayList<MWErecord> result2 = MWEdata.getMWErecords(transliterated, 2); // get
				// 2nd
				final Iterator<MWE1record> itr = result.iterator();
				while (itr.hasNext()) // goind over first list and finding match
				// in 2nd
				{
					final MWE1record me1rec = itr.next();
					// System.out.println("(F) PostProcessor1:checkBackwords():
					// pos = " + me1rec.getPos());
					final Iterator<MWErecord> itr2 = result2.iterator();
					while (itr2.hasNext()) {
						final MWErecord mwerec = itr2.next();
						// System.out.println("me1rec.getId() = " +
						// me1rec.getId() + " mwerec.getFormerItemId() " +
						// mwerec.getFormerItemId());
						if (me1rec.getId().equals(mwerec.getFormerItemId()))
						// if they have the same id then we have a match
						{
							// System.out.println("(F)
							// PostProcessor1:checkBackwords(): found MATCH
							// trans = "
							// + me1rec.getTransliterated());
							final CustomResult cRes = new CustomResult();
							cRes.LoadRecord(me1rec, mwerec); // load values
							customResultSet.add(cRes); // add to result set
						}
					}
				}

				// System.out.println("Printing CustomResultSet size = " +
				// customResultSet.size() );
				final Iterator<CustomResult> itr3 = customResultSet.iterator();
				while (itr3.hasNext()) {
					itr3.next();
					// ((CustomResult)itr3.next()).Print();
				}

			}

			else if (expectedPrevId.equals("2")) {
				final String firstWord = prevTransliteratedList.get(prevTransliteratedListSize - 3).replaceAll("'",
						"\\\\'");

				phraze = firstWord + " "
						+ prevTransliteratedList.get(prevTransliteratedListSize - 2).replaceAll("'", "\\\\'") + " "
						+ prevTransliteratedList.get(prevTransliteratedListSize - 1).replaceAll("'", "\\\\'");

				String.format(
						"SELECT mwe3.PGN, mwe3.spelling, mwe3.register,  mwe3.gender, mwe3.number ,mwe3.definiteness,  mwe3.mwTransliterated,  mwe3.mwUndotted,"
								+ "mwe%1$s.lexiconId, mwe%1$s.undottedLexiconItem, mwe%1$s.dottedLexiconItem, mwe%1$s.transliteratedLexiconItem,mwe1.type, mwe1.pos"
								+ "FROM  mwe%1$s, mwe%2$s, mwe1"
								+ "WHERE  mwe3.mwTransliterated=? AND mwe%1$s.transliterated=? AND mwe%2$s.transliterated=? "
								+ "AND mwe%1$s.formerItemId=mwe%2$s.aid AND mwe2.formerItemId=mwe1.id",
						id, expectedPrevId);

				// this part replaces the mysql part by using data files instead
				// and simulating the queries
				final ArrayList<MWE1record> result = MWE1data.getMWE1records(firstWord); // get
				// first
				final ArrayList<MWErecord> result2 = MWEdata.getMWErecords(prevTransliterated, 2); // get
				// 2nd
				final ArrayList<MWErecord> result3 = MWEdata.getMWErecords(transliterated, 3); // get
				// 3nd

				final Iterator<MWErecord> itr = result3.iterator();
				while (itr.hasNext()) // going over first list and finding match
				// in 3rd
				{
					final MWErecord mwe3rec = itr.next();
					if (mwe3rec.getMwTransliterated().equals(phraze) && mwe3rec.getTransliterated().equals(transliterated)) {
						final Iterator<MWErecord> itr2 = result2.iterator();
						while (itr2.hasNext()) {
							final MWErecord mwe2rec = itr2.next();
							if (mwe2rec.getTransliterated().equals(prevTransliterated)
									&& mwe2rec.getAid().equals(mwe3rec.getFormerItemId())) // if
							// they have the same id then we have a match
							{
								final Iterator<MWE1record> itr1 = result.iterator();
								while (itr1.hasNext()) {
									final MWE1record mwe1rec = itr1.next();
									if (mwe1rec.getId().equals(mwe2rec.getFormerItemId())) {
										final CustomResult cRes = new CustomResult();
										cRes.LoadRecord(mwe1rec, mwe3rec); // load
										// values
										customResultSet.add(cRes); // add to
										// result
										// set
									}
								}
							}
						}
					}
				}
				resultSetSize = customResultSet.size();

			} else if (expectedPrevId.equals("3")) {

				// ############################################################################

				final String firstWord = prevTransliteratedList.get(prevTransliteratedListSize - 4).replaceAll("'",
						"\\\\'");
				final String secondWord = prevTransliteratedList.get(prevTransliteratedListSize - 3).replaceAll("'",
						"\\\\'");

				phraze = firstWord + " " + secondWord + " "
						+ prevTransliteratedList.get(prevTransliteratedListSize - 2).replaceAll("'", "\\\\'") + " "
						+ prevTransliteratedList.get(prevTransliteratedListSize - 1).replaceAll("'", "\\\\'");
				// System.out.println("*********************" + phraze);

				String.format(
						"SELECT mwe4.PGN, mwe4.spelling, mwe4.register  , mwe4.gender, mwe4.number, mwe4.mwTransliterated,  mwe4.definiteness, mwe4.mwUndotted, "
								+ "mwe%1$s.lexiconId, mwe%1$s.undottedLexiconItem ,mwe%1$s.dottedLexiconItem, mwe%1$s.transliteratedLexiconItem, mwe1.type, mwe1.pos"
								+ "FROM  mwe%1$s, mwe%2$s, mwe2 , mwe1 where mwe4.mwTransliterated=? AND mwe%1$s.transliterated=? AND mwe%2$s.transliterated=?"
								+ " AND mwe%1$s.formerItemId=mwe%2$s.aid AND mwe3.formerItemId=mwe2.aid and mwe2.formerItemId=mwe1.id",
						id, expectedPrevId);

				final ArrayList<MWE1record> result = MWE1data.getMWE1records(firstWord); // get
				// first
				final ArrayList<MWErecord> result2 = MWEdata.getMWErecords(secondWord, 2); // get
				// 2nd
				final ArrayList<MWErecord> result3 = MWEdata.getMWErecords(prevTransliterated, 3); // get
				// 3nd
				final ArrayList<MWErecord> result4 = MWEdata.getMWErecords(transliterated, 4); // get
				// 3nd
				final Iterator<MWErecord> itr4 = result4.iterator();
				while (itr4.hasNext()) // going over first list and finding
				// match in 4rd
				{
					final MWErecord mwe4rec = itr4.next();
					if (mwe4rec.getMwTransliterated().equals(phraze) && mwe4rec.getTransliterated().equals(transliterated)) // MwTransliterated
					// ==
					// phraze
					{
						final Iterator<MWErecord> itr3 = result3.iterator();
						while (itr3.hasNext()) {
							final MWErecord mwe3rec = itr3.next();
							if (mwe3rec.getTransliterated().equals(prevTransliterated)
									&& mwe3rec.getAid().equals(mwe4rec.getFormerItemId())) // if
							// they have the same id then we have a match
							{
								final Iterator<MWErecord> itr2 = result2.iterator();
								while (itr2.hasNext()) {
									final MWErecord mwe2rec = itr2.next();
									if (mwe2rec.getAid().equals(mwe3rec.getFormerItemId()))
									// if they have the same id then we have a
									// match
									{
										final Iterator<MWE1record> itr1 = result.iterator();
										while (itr1.hasNext()) {
											final MWE1record mwe1rec = itr1.next();
											if (mwe1rec.getId().equals(mwe2rec.getFormerItemId())) // if
											// they have the same id then we
											// have a match
											{
												final CustomResult cRes = new CustomResult();
												cRes.LoadRecord(mwe1rec, mwe4rec); // load
												// values
												customResultSet.add(cRes); // add
												// to
												// result
												// set
											}
										}
									}
								}
							}
						}
					}
				}
			}
			resultSetSize = customResultSet.size();
			// System.out.println("(F) PostProcessor1:checkBackwords SQL = " +
			// selectSql);

			/*
			 * ResultSet rs = null; try { rs = getData(selectSql); try { rs.last();
			 *
			 * if (resultSetSize != rs.getRow()) { System.out.println(
			 * "(F) PostProcessor1:checkBackwords THIS IS NOT GOOD !!! expectedPrevId="
			 * +expectedPrevId); System.out.println("    resultSetSize=" +resultSetSize
			 * +" rs.getRow()="+rs.getRow()); System.out.println(selectSql); try {
			 * System.in.read(); } catch (IOException e) { e.printStackTrace(); } } } catch
			 * (SQLException e) { e.printStackTrace(); } } finally { releaseConnection(); if
			 * (rs == null) return false; }
			 */

			if (resultSetSize > 0) {
				for (final CustomResult cRes : customResultSet) {

					/*
					 * if (expectedPrevId.equals("3")) { resultSetSize--; System.out.println(
					 * "(F) PostProcessor1:checkBackwords RS is not empty !!!"); if (resultSetSize <
					 * 0 ) { System.out.println(
					 * "(F) PostProcessor1:checkBackwords THIS IS NOT GOOD !!!" ); try {
					 * System.in.read(); } catch (IOException e) { e.printStackTrace(); } } }
					 */

					if (!prevId.equals(expectedPrevId)) {
						if (!removeAnalysisByPrevId(prevToken, expectedPrevId)) {
							return rt;
						}
					}

					lastLexiconId = lexiconId;
					// lexiconId = rs.getString("lexiconId");
					lexiconId = cRes.getLexiconId();

					if (!lexiconId.equals("0")) {
						transliteratedLexiconItem = cRes.getTransliteratedLexiconItem();
						undottedLexiconItem = cRes.getUndottedLexiconItem();
						dottedLexiconItem = cRes.getDottedLexiconItem();
						type = cRes.getType();
						pos = cRes.getPos();
						lastSpelling = spelling;
						spelling = cRes.getSpelling();
						register = cRes.getRegister();
						gender = cRes.getGender();
						number = cRes.getNumber();
						definiteness = cRes.getDefiniteness();
						mwTransliterated = cRes.getMwTransliterated();
						mwUndotted = cRes.getMwUndotted();

						final BaseType base = analysis.getBase();

						if (dottedLexiconItem != null && dottedLexiconItem.length() > 0
								&& dottedLexiconItem.charAt(0) != 'u') {
							base.setDottedLexiconItem(URLDecoder.decode(dottedLexiconItem, "UTF-8"));
						}
						base.setLexiconPointer(lexiconId);
						base.setTransliteratedLexiconItem(transliteratedLexiconItem);
						final MWEType mwtype = base.getMWE();
						if (type.charAt(0) != 'u') {
							mwtype.setType(type);
						}
						mwtype.setPos(pos);

						base.setLexiconItem(URLDecoder.decode(undottedLexiconItem, "UTF-8"));
						mwtype.setSpelling(spelling);
						mwtype.setRegister(register);
						if (!number.equals("unspecified")) {
							mwtype.setNumber(number);
						}
						if (!gender.equals("unspecified")) {
							mwtype.setGender(gender);
						}
						if (!definiteness.equals("unspecified")) {
							if (definiteness.equals("tt") || definiteness.equals("true")) {
								definiteness = "true";
							} else if (definiteness.equals("tf") || definiteness.equals("f") || definiteness.equals("false")) {
								definiteness = "false";
							}
							mwtype.setDefiniteness(definiteness);
						}

						mwtype.setMultiWordTransliterated(mwTransliterated);
						mwtype.setMultiWordUndotted(URLDecoder.decode(mwUndotted, "UTF-8"));
						if (expectedPrevId.equals("1")) {
							// pos = rs.getString("pos");
							removeAnalysisByPos(prevToken, pos);
						}
					}

					rt = true;
					lastPGN = PGN;
					// PGN = rs.getString("PGN");
					PGN = cRes.getPGN();

					if (lastPGN.length() > 0 && !lastPGN.equals(PGN)) {
						final int newId = token.getAnalysis().size() + 1;
						objFactory = new ObjectFactory();
						newAnalysis = objFactory.createAnalysisType();

						newAnalysis.setId(String.valueOf(newId));
						BaseType base = null;

						base = objFactory.createBaseType();

						final BaseType oldBase = analysis.getBase();
						if (lastLexiconId.length() > 0 && !lastLexiconId.equals(lexiconId)) {
							oldBase.setLexiconPointer(lastLexiconId);
						}
						oldBase.getMWE().setSpelling(lastSpelling);
						analysis.setBase(oldBase);

						base.setDottedLexiconItem(oldBase.getDottedLexiconItem());
						base.setLexiconItem(oldBase.getLexiconItem());

						if (lastLexiconId.length() > 0 && !lastLexiconId.equals(lexiconId)) {
							base.setLexiconPointer(lexiconId);
						} else {
							base.setLexiconPointer(oldBase.getLexiconPointer());
						}

						base.setTransliteratedLexiconItem(oldBase.getTransliteratedLexiconItem());

						MWEType mwe = null;
						mwe = objFactory.createMWEType();
						mwe.setConsecutive("true");
						mwe.setSpelling(spelling);
						mwe.setPos(pos);
						mwe.setId(id);
						mwe.setConsecutive("true");
						mwe.setRegister(register);
						base.setMWE(mwe);
						newAnalysis.setBase(base);

						if (!PGN.equals("unspecified")) {
							objFactory = new ObjectFactory();
							SuffixType suffix = null;
							try {
								suffix = objFactory.createSuffixType();
							} catch (final JAXBException e3) {
								e3.printStackTrace();
							}
							suffix.setFunction("pronomial");
							suffix.setPerson(StringUtils.getPersonPGN(PGN));
							suffix.setGender(StringUtils.getGenderPGN(PGN));
							suffix.setNumber(StringUtils.getNumberPGN(PGN));
							newAnalysis.setSuffix(suffix);
						}
						token.getAnalysis().add(newAnalysis);
					} else if (!PGN.equals("unspecified")) {
						objFactory = new ObjectFactory();
						SuffixType suffix = null;
						try {
							suffix = objFactory.createSuffixType();
						} catch (final JAXBException e3) {
							e3.printStackTrace();
						}
						suffix.setFunction("pronomial");
						suffix.setPerson(StringUtils.getPersonPGN(PGN));
						suffix.setGender(StringUtils.getGenderPGN(PGN));
						suffix.setNumber(StringUtils.getNumberPGN(PGN));
						analysis.setSuffix(suffix);
					}
				}
			}
			if (rt) {
				return rt;
			}
		}
		return rt;
	}

	// -----------------------------------------------------------------------------------------------------------------------------------
	public boolean checkBackwordsOld(String id, String expectedPrevId, String prevId, String transliterated,
			ArrayList<String> prevTransliteratedList, AnalysisType analysis, TokenType prevToken, TokenType token)
			throws UnsupportedEncodingException, JAXBException {
		boolean rt = false;
		final int prevTransliteratedListSize = prevTransliteratedList.size();
		final int intId = Integer.parseInt(id);
		if (intId > prevTransliteratedListSize) {
			return rt;
		}

		String lexiconId = "-1";
		String dottedLexiconItem = "";
		String undottedLexiconItem = "";
		String pos = "";
		String type = "";
		String transliteratedLexiconItem = "";
		transliterated = transliterated.replaceAll("'", "\\\\'");
		String PGN = "";
		String lastPGN = "";
		String spelling = "";
		String lastSpelling = "";
		String register = "";
		String gender = "";
		String number = "";
		String mwTransliterated = "";
		String mwUndotted = "";
		ObjectFactory objFactory = null;
		AnalysisType newAnalysis = null;
		String lastLexiconId = "";
		String phraze = "";
		String prevTransliterated = "";
		String definiteness = "";
		ResultSet rs = null;
		String query = "";
		try {
			for (int prevTransliteratedListIndex = prevTransliteratedListSize
					- 2; prevTransliteratedListIndex >= 0; prevTransliteratedListIndex--) {
				prevTransliterated = prevTransliteratedList.get(prevTransliteratedListIndex);
				prevTransliterated = prevTransliterated.replaceAll("'", "\\\\'");

				if (expectedPrevId.equals("1")) {
					phraze = prevTransliteratedList.get(prevTransliteratedListSize - 2).replaceAll("'", "\\\\'") + " "
							+ prevTransliteratedList.get(prevTransliteratedListSize - 1).replaceAll("'", "\\\\'");
					// System.out.println("*********************" + phraze);
					query = String.format(
							"SELECT mwe2.spelling, mwe2.register, mwe2.gender, mwe2.number, mwe2.definiteness,  mwe2.mwTransliterated,  mwe2.mwUndotted, "
									+ " mwe%1$s.lexiconId, mwe%1$s.undottedLexiconItem, mwe%1$s.PGN, mwe%1$s.dottedLexiconItem, mwe%1$s.transliteratedLexiconItem, mwe1.pos, mwe1.type "
									+ " FROM  mwe%1$s,  mwe1 WHERE mwe%1$s.transliterated=? AND mwe1.transliterated=? AND mwe%1$s.formerItemId=mwe1.id",
							id);
					rs = getData(query, transliterated, prevTransliterated);
				} else if (expectedPrevId.equals("2")) {
					phraze = prevTransliteratedList.get(prevTransliteratedListSize - 3).replaceAll("'", "\\\\'") + " "
							+ prevTransliteratedList.get(prevTransliteratedListSize - 2).replaceAll("'", "\\\\'") + " "
							+ prevTransliteratedList.get(prevTransliteratedListSize - 1).replaceAll("'", "\\\\'");
					// System.out.println("*********************" + phraze);
					query = String.format(
							"SELECT mwe3.PGN, mwe3.spelling, mwe3.register, mwe3.gender, mwe3.number, mwe2.definiteness,  mwe3.mwTransliterated, "
									+ " mwe3.mwUndotted, mwe%1$s.lexiconId, mwe%1$s.undottedLexiconItem , mwe%1$s.dottedLexiconItem, mwe%1$s.transliteratedLexiconItem,"
									+ " mwe1.type, mwe1.pos "
									+ " FROM  mwe%1$s, mwe%2$s, mwe1 WHERE  mwe3.mwTransliterated=? AND mwe%1$s.transliterated=? AND mwe%2$s.transliterated=? AND "
									+ "	mwe%1$s.formerItemId=mwe%2$s.aid and mwe2.formerItemId=mwe1.id",
							id, expectedPrevId);
					rs = getData(query, phraze, transliterated, prevTransliterated);
				} else if (expectedPrevId.equals("3")) {
					phraze = prevTransliteratedList.get(prevTransliteratedListSize - 4).replaceAll("'", "\\\\'") + " "
							+ prevTransliteratedList.get(prevTransliteratedListSize - 3).replaceAll("'", "\\\\'") + " "
							+ prevTransliteratedList.get(prevTransliteratedListSize - 2).replaceAll("'", "\\\\'") + " "
							+ prevTransliteratedList.get(prevTransliteratedListSize - 1).replaceAll("'", "\\\\'");
					query = String.format(
							"SELECT mwe4.PGN, mwe4.spelling, mwe4.register, mwe4.gender, mwe4.number, mwe4.mwTransliterated,  mwe4.definiteness, "
									+ " mwe4.mwUndotted, mwe%1$s.lexiconId, mwe%1$s.undottedLexiconItem, mwe%1$s.dottedLexiconItem, mwe%1$s.transliteratedLexiconItem,"
									+ " mwe1.type, mwe1.pos "
									+ " FROM  mwe%1$s,  mwe%2$s, mwe2 , mwe1 WHERE mwe4.mwTransliterated=? AND mwe%1$s.transliterated=? AND mwe%2$s.transliterated=?"
									+ " AND mwe%1$s.formerItemId=mwe%2$s.aid AND mwe3.formerItemId=mwe2.aid AND mwe2.formerItemId=mwe1.id",
							id, expectedPrevId);
					rs = getData(query, phraze, transliterated, prevTransliterated);

				}
				// System.out.println("prev selectSql =" + selectSql);

				if (rs != null) {
					try {
						while (rs.next()) {
							if (!prevId.equals(expectedPrevId)) {
								if (!removeAnalysisByPrevId(prevToken, expectedPrevId)) {
									return rt;
								}
							}
							lastLexiconId = lexiconId;
							lexiconId = rs.getString("lexiconId");

							if (!lexiconId.equals("0")) {
								// System.out.println("lexiconId=" + lexiconId);
								transliteratedLexiconItem = rs.getString("transliteratedLexiconItem");
								// System.out.println("transliteratedLexiconItem="+
								// transliteratedLexiconItem);

								undottedLexiconItem = rs.getString("undottedLexiconItem");

								dottedLexiconItem = rs.getString("dottedLexiconItem");
								type = rs.getString("type");
								pos = rs.getString("pos");

								lastSpelling = spelling;
								spelling = rs.getString("spelling");
								register = rs.getString("register");

								gender = rs.getString("gender");
								number = rs.getString("number");
								definiteness = rs.getString("definiteness");

								mwTransliterated = rs.getString("mwTransliterated");
								mwUndotted = rs.getString("mwUndotted");

								// System.out.println(spelling);

								if (dottedLexiconItem != null && dottedLexiconItem.length() > 0
										&& dottedLexiconItem.charAt(0) != 'u') {
									analysis.getBase().setDottedLexiconItem(URLDecoder.decode(dottedLexiconItem, "UTF-8"));
								}
								analysis.getBase().setLexiconPointer(lexiconId);
								analysis.getBase().setTransliteratedLexiconItem(transliteratedLexiconItem);
								if (type.charAt(0) != 'u') {
									analysis.getBase().getMWE().setType(type);
								}
								analysis.getBase().getMWE().setPos(pos);

								analysis.getBase().setLexiconItem(URLDecoder.decode(undottedLexiconItem, "UTF-8"));
								analysis.getBase().getMWE().setSpelling(spelling);
								analysis.getBase().getMWE().setRegister(register);
								if (!number.equals("unspecified")) {
									analysis.getBase().getMWE().setNumber(number);
								}
								if (!gender.equals("unspecified")) {
									analysis.getBase().getMWE().setGender(gender);
								}
								if (!definiteness.equals("unspecified")) {
									if (definiteness.equals("tt") || definiteness.equals("true")) {
										definiteness = "true";
									} else if (definiteness.equals("tf") || definiteness.equals("f")
											|| definiteness.equals("false")) {
										definiteness = "false";
									}
									analysis.getBase().getMWE().setDefiniteness(definiteness);
								}

								analysis.getBase().getMWE().setMultiWordTransliterated(mwTransliterated);
								analysis.getBase().getMWE().setMultiWordUndotted(URLDecoder.decode(mwUndotted, "UTF-8"));
								if (expectedPrevId.equals("1")) {
									pos = rs.getString("pos");
									removeAnalysisByPos(prevToken, pos);
								}

							}
							rt = true;
							lastPGN = PGN;
							PGN = rs.getString("PGN");

							if (lastPGN.length() > 0 && !lastPGN.equals(PGN)) {
								final int newId = token.getAnalysis().size() + 1;
								objFactory = new ObjectFactory();
								newAnalysis = objFactory.createAnalysisType();

								newAnalysis.setId(String.valueOf(newId));
								BaseType base = null;

								base = objFactory.createBaseType();

								final BaseType oldBase = analysis.getBase();
								if (lastLexiconId.length() > 0 && !lastLexiconId.equals(lexiconId)) {
									oldBase.setLexiconPointer(lastLexiconId);
								}
								oldBase.getMWE().setSpelling(lastSpelling);
								analysis.setBase(oldBase);

								base.setDottedLexiconItem(oldBase.getDottedLexiconItem());
								base.setLexiconItem(oldBase.getLexiconItem());
								if (lastLexiconId.length() > 0 && !lastLexiconId.equals(lexiconId)) {
									base.setLexiconPointer(lexiconId);
								} else {
									base.setLexiconPointer(oldBase.getLexiconPointer());
								}
								base.setTransliteratedLexiconItem(oldBase.getTransliteratedLexiconItem());

								MWEType mwe = null;
								mwe = objFactory.createMWEType();
								mwe.setConsecutive("true");
								mwe.setSpelling(spelling);
								mwe.setPos(pos);
								mwe.setId(id);

								mwe.setConsecutive("true");
								mwe.setRegister(register);

								base.setMWE(mwe);
								newAnalysis.setBase(base);

								if (!PGN.equals("unspecified")) {
									objFactory = new ObjectFactory();
									SuffixType suffix = null;
									try {
										suffix = objFactory.createSuffixType();
									} catch (final JAXBException e3) {
										e3.printStackTrace();
									}
									suffix.setFunction("pronomial");
									suffix.setPerson(StringUtils.getPersonPGN(PGN));
									suffix.setGender(StringUtils.getGenderPGN(PGN));
									suffix.setNumber(StringUtils.getNumberPGN(PGN));

									newAnalysis.setSuffix(suffix);
								}
								token.getAnalysis().add(newAnalysis);

							} else if (!PGN.equals("unspecified")) {
								objFactory = new ObjectFactory();
								SuffixType suffix = null;
								try {
									suffix = objFactory.createSuffixType();
								} catch (final JAXBException e3) {
									e3.printStackTrace();
								}
								suffix.setFunction("pronomial");
								suffix.setPerson(StringUtils.getPersonPGN(PGN));
								suffix.setGender(StringUtils.getGenderPGN(PGN));
								suffix.setNumber(StringUtils.getNumberPGN(PGN));

								analysis.setSuffix(suffix);
							}
						}
					} catch (final SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					if (rt) {
						return rt;
					}
				}
			}
		} finally {
			releaseConnection();
		}
		return rt;
	}

	// ----------------------------------------------------------------------------------------------------
	public boolean checkForward(String transliterated, String nextTransliterated, String prevTransliterated, int id)
			throws UnsupportedEncodingException {
		boolean rt = false;
		final int nextId = id + 1;
		String.valueOf(nextId);
		final String phraze = prevTransliterated + " " + transliterated + " " + nextTransliterated;
		ResultSet rs = null;
		try {
			rs = getData(
					"SELECT mwe3.mwTransliterated, mwe4.mwTransliterated"
							+ "  FROM  mwe3, mwe4  WHERE mwe3.mwTransliterated=? OR  mwe4.mwTransliterated like ?",
					phraze, phraze);
			if (rs != null) {

				try {
					if (rs.next()) {
						rt = true;
					}
				} catch (final SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} finally {
			releaseConnection();
		}

		return rt;

	}

	/*--------------------------------------------------------------------------------------------------------------------------*/
	public boolean checkForward(String transliterated, String nextTransliterated, String pos, String type)
			throws UnsupportedEncodingException {
		if (this.useDataFiles) {
			return this.checkForwardNew(transliterated, nextTransliterated, pos, type);
		} else {
			return this.checkForwardOld(transliterated, nextTransliterated, pos, type);
		}
	}

	public boolean checkForwardNew(String transliterated, String nextTransliterated, String pos, String type)
			throws UnsupportedEncodingException {
		// System.out.println("############# (F)
		// PostProcessor1:checkForward(pos="+pos+")");
		final boolean rt = false;

		transliterated = transliterated.replaceAll("'", "\\\\'"); // update line
		// added at 21.11.10 by yossi
		nextTransliterated = nextTransliterated.replaceAll("'", "\\\\'");

		final ArrayList<MWE1record> result = MWE1data.getMWE1records(transliterated); // get
		// first
		// from
		// mwe1
		final ArrayList<MWErecord> result2 = MWEdata.getMWErecords(nextTransliterated, 2); // get
		// 2nd
		// from
		// mwe2

		final Iterator<MWE1record> itr = result.iterator();
		while (itr.hasNext()) // going over first list and finding match in 2nd
		{
			final MWE1record mwe1rec = itr.next();
			if (mwe1rec.getPos().equalsIgnoreCase(pos)) // mwe1.pos= pos
			{
				if (!pos.equals("propername") || mwe1rec.getType().equalsIgnoreCase(type))
				// if pos == propername so need also mwe1.type=type
				{
					final Iterator<MWErecord> itr2 = result2.iterator();
					while (itr2.hasNext()) {
						final MWErecord mwerec = itr2.next();
						if (mwe1rec.getId().equals(mwerec.getFormerItemId())) // if
						// they have the same id then we have a match
						{
							return true; // found one so return true testB =
												// true;
						}
					}
				}
			}
		}
		return rt;
		// return testB;
	}

	// --------------------------------------------------------------------------------------------------------------------------------------
	public boolean checkForwardOld(String transliterated, String nextTransliterated, String pos, String type)
			throws UnsupportedEncodingException {
		boolean rt = false;
		ResultSet rs = null;
		try {
			if (pos.equals("propername")) {
				rs = getData("SELECT mwe1.id  FROM  mwe1, mwe2  WHERE mwe1.transliterated=? "
						+ "AND mwe2.transliterated=? AND mwe1.pos=? AND mwe2.formerItemId=mwe1.id " + "AND mwe1.type=? ",
						transliterated, nextTransliterated, pos, type);
			} else {
				rs = getData(
						"SELECT mwe1.id FROM  mwe1, mwe2 WHERE mwe1.transliterated=? "
								+ " AND mwe2.transliterated=? AND mwe1.pos=? AND mwe2.formerItemId=mwe1.id",
						transliterated, nextTransliterated, pos);
			}
			if (rs != null) {

				try {
					if (rs.next()) {
						rt = true;
					}
				} catch (final SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} finally {
			releaseConnection();
		}
		return rt;
	}

	// -------------------------------------------------------------------------------------------------
	public void checkNextMWExistence(List<TokenType> tokenList, int tokenIndex, int tokenListSize) {
		boolean foundNext = false;
		String expectedNextIdStr = "";
		final TokenType token = tokenList.get(tokenIndex);
		final List<AnalysisType> analysisList = token.getAnalysis();
		int analysisListSize = analysisList.size();
		for (int analysisIndex = 0; analysisIndex < analysisListSize; analysisIndex++) {
			final AnalysisType analysis = analysisList.get(analysisIndex);
			final BaseType base = analysis.getBase();
			if (base != null) {
				final MWEType mweType = base.getMWE();
				if (mweType != null) {
					// System.out.println(" tokenIndex =" + tokenIndex);
					// System.out.println("token surface=" +
					// token.getSurface());
					final String lexiconPointer = analysis.getBase().getLexiconPointer();

					if (lexiconPointer != null) {
						return;
					}
					final boolean multiWordPrefixExist = analysis.getBase().getMWE().isMultiWordPrefixExist();
					final String id = analysis.getBase().getMWE().getId();
					final int expectedNextId = Integer.parseInt(id) + 1;
					expectedNextIdStr = String.valueOf(expectedNextId);
					final int nextTokenIndex = tokenIndex + 1;
					if (nextTokenIndex < tokenListSize) {
						TokenType nextToken = tokenList.get(nextTokenIndex);
						final String nextTokenSurface = nextToken.getSurface();
						if (nextTokenSurface.equals("-") && nextTokenIndex + 1 < tokenListSize) {
							nextToken = tokenList.get(nextTokenIndex + 1);
						}
						final List<AnalysisType> nextAnalysisList = nextToken.getAnalysis();
						final int nextAnalysisListSize = nextAnalysisList.size();
						foundNext = false;

						// System.out.println("next token surface="+
						// nextToken.getSurface());
						for (int nextAnalysisIndex = 0; nextAnalysisIndex < nextAnalysisListSize; nextAnalysisIndex++) {
							final AnalysisType nextAnalysis = nextAnalysisList.get(nextAnalysisIndex);
							if (multiWordPrefixExist) {
								final List<PrefixType> nextPrefixList = nextAnalysis.getPrefix();
								if (nextPrefixList.size() > 0) {
									if (nextPrefixList.get(0).isMultiWord()) {
										foundNext = true;
										break;
									}
								}
							}
							final BaseType nextBase = nextAnalysis.getBase();
							if (nextBase != null) {
								final MWEType nextMweType = nextBase.getMWE();
								if (nextMweType != null) {
									final String nextIdStr = nextAnalysis.getBase().getMWE().getId();
									final int nextId = Integer.parseInt(nextIdStr);
									final String nextLexiconPointer = nextAnalysis.getBase().getLexiconPointer();

									if (nextIdStr.equals(expectedNextIdStr) && nextLexiconPointer == null) {
										foundNext = checkNextNext(nextTokenIndex, tokenListSize, tokenList, nextId + 1);
										if (foundNext) {
											break;
										}
									} else if (nextIdStr.equals(expectedNextIdStr) && nextLexiconPointer != null) {
										foundNext = true;
										break;
									}
								}
							}
						}
						if (!foundNext) {
							token.getAnalysis().remove(analysisIndex);
							analysisIndex--;
							arrangeAnalysisIdAfterRemove(token);
							analysisListSize = analysisList.size();
						}
					}
				}
			}
		}
	}

	// -------------------------------------------------------------------------------------------------------------
	public boolean checkNextNext(int tokenIndex, int tokenListSize, List<TokenType> tokenList, int expectedNextId) {
		boolean foundNext = false;
		MWEType nextMweType = null; // NEW
		final String expectedNextIdStr = String.valueOf(expectedNextId);
		final int nextTokenIndex = tokenIndex + 1;
		if (nextTokenIndex < tokenListSize) {
			final TokenType nextToken = tokenList.get(nextTokenIndex);
			final List<AnalysisType> nextAnalysisList = nextToken.getAnalysis();
			final int nextAnalysisListSize = nextAnalysisList.size();

			// System.out.println("next token surface=" +
			// nextToken.getSurface());
			for (int nextAnalysisIndex = 0; nextAnalysisIndex < nextAnalysisListSize; nextAnalysisIndex++) {
				final AnalysisType nextAnalysis = nextAnalysisList.get(nextAnalysisIndex);
				final BaseType nextBase = nextAnalysis.getBase();
				if (nextBase != null) {
					nextMweType = nextBase.getMWE();
				}
				if (nextMweType != null) {
					final String nextId = nextAnalysis.getBase().getMWE().getId();
					final String lexiconPointer = nextAnalysis.getBase().getLexiconPointer();
					if (nextId.equals(expectedNextIdStr) && lexiconPointer != null) {
						foundNext = true;
					}
				}
			}
		}
		return foundNext;
	}

	// ----------------------------------------------------------------------------------------------------------------------------
	public String checkNextToken(TokenType nextToken, int tokenListSize) {
		final StringBuffer nextTokenIdList = new StringBuffer();
		Translate.Heb2Eng(nextToken.getSurface());
		final List<AnalysisType> nextTokenAnalysesList = nextToken.getAnalysis();
		final int nextTokenAnalysisListSize = nextTokenAnalysesList.size();
		for (int nextTokenAnalysisIndex = 0; nextTokenAnalysisIndex < nextTokenAnalysisListSize; nextTokenAnalysisIndex++) {
			final AnalysisType nextTokenAnalysis = nextTokenAnalysesList.get(nextTokenAnalysisIndex);
			if (nextTokenAnalysis.getBase() != null) {
				final MWEType nextTokenMWE = nextTokenAnalysis.getBase().getMWE();
				if (nextTokenMWE != null) {
					final String nextTokenMWId = nextTokenMWE.getId();
					nextTokenIdList.append(nextTokenMWId);
					nextTokenIdList.append(",");
				}
			}
		}

		return nextTokenIdList.toString();
	}

	// ---------------------------------------------------------------------------------------------------------------
	public boolean checkPrefixForward(String transliterated, TokenType nextToken, String pos, String type)
			throws UnsupportedEncodingException {
		boolean rt = false;
		boolean testB = false;

		final List<AnalysisType> analysisList = nextToken.getAnalysis();
		final int analysisListSize = analysisList.size();
		for (int i = 0; i < analysisListSize; i++) {
			final AnalysisType analysis = analysisList.get(i);
			final List<PrefixType> prefixList = analysis.getPrefix();
			if (prefixList.size() == 1) {
				final PrefixType prefix = prefixList.get(0);
				final String prefixTranslietarted = Translate.getHebToEng(prefix.getSurface());

				final ArrayList<MWE1record> result = MWE1data.getMWE1records(transliterated); // get
				// first
				// from
				// mwe1
				final ArrayList<MWErecord> result2 = MWEdata.getMWErecords(prefixTranslietarted, 2); // get
				// 2nd
				// from
				// mwe2

				final Iterator<MWE1record> itr = result.iterator();
				while (itr.hasNext()) // goind over first list and finding match
				// in 2nd
				{
					final MWE1record mwe1rec = itr.next();
					// System.out.println("(F) PostProcessor1:checkForward(): id
					// = "
					// + mwe1rec.getId());
					// System.out.println("(F) PostProcessor1:checkForward(): if
					// ("+mwe1rec.getPos()+".equals("+pos+"))");
					if (mwe1rec.getPos().equalsIgnoreCase(pos)) // mwe1.pos= pos
					{
						// System.out.println("(F)
						// PostProcessor1:checkForward(): pos = "
						// + mwe1rec.getPos());
						final Iterator<MWErecord> itr2 = result2.iterator();
						while (itr2.hasNext()) {
							final MWErecord mwerec = itr2.next();
							// System.out.println("(F)
							// PostProcessor1:checkForward(): mwe1rec.getId() "
							// + mwe1rec.getId()+" mwerec.getFormerItemId() " +
							// mwerec.getFormerItemId());
							if (mwe1rec.getId().equals(mwerec.getFormerItemId())) // if
							// they have the same id then we have a match
							{
								prefix.setMultiWord(true);
								analysis.getPrefix().set(0, prefix);
								nextToken.getAnalysis().set(i, analysis);
								testB = true;
								// return true; //found one so return true
							}
						}
					}
				}

				if (Data.webFlag == true) {

					ResultSet rs = null;
					try {
						rs = getData("SELECT mwe1.id FROM  mwe1, mwe2  "
								+ " WHERE mwe1.transliterated=? AND mwe2.transliterated=? AND mwe1.pos=? AND mwe2.formerItemId=mwe1.id",
								transliterated, prefixTranslietarted, pos);
						if (rs != null) {
							try {
								if (rs.next()) {
									prefix.setMultiWord(true);
									analysis.getPrefix().set(0, prefix);
									nextToken.getAnalysis().set(i, analysis);
									rt = true;
								}
							} catch (final SQLException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					} finally {
						releaseConnection();
					}
				} else {
					rt = testB;
				}

			}
		}
		if (testB != rt) {
			System.out.println("(W) PostProcessor1:checkPrefixForward() mismatch between datafiles and DB");
		}
		return rt;
	}

	public void createMWWAnalysis(TokenType token, BaseType originalBase, int originalAnalysisIndex, String multiWord,
			String id) throws JAXBException {
		ObjectFactory objFactory = null;
		objFactory = new ObjectFactory();

		final AnalysisType analysis = objFactory.createAnalysisType();
		analysis.setId(String.valueOf(originalAnalysisIndex + 1));

		// analysis.setId(analysisId);
		BaseType base = null;
		base = objFactory.createBaseType();
		base.setLexiconPointer(originalBase.getLexiconPointer());
		base.setLexiconItem(originalBase.getLexiconPointer());
		base.setDottedLexiconItem(originalBase.getDottedLexiconItem());
		base.setTransliteratedLexiconItem(originalBase.getTransliteratedLexiconItem());
		MWEType mw = null;
		mw = objFactory.createMWEType();
		mw.setId(id);
		mw.setConsecutive("true");
		mw.setMultiWord(multiWord);
		mw.setPos("properName");
		mw.setGender(originalBase.getProperName().getGender());
		mw.setNumber(originalBase.getProperName().getNumber());
		mw.setType(originalBase.getProperName().getType());
		mw.setRegister(originalBase.getProperName().getRegister());
		base.setMWE(mw);
		analysis.setBase(base);
		token.getAnalysis().remove(originalAnalysisIndex);
		token.getAnalysis().add(originalAnalysisIndex, analysis);
	}

	// ---------------------------------------------------------------------------------------------------------------------------
	public TokenType createUnknownAnalysis(TokenType token, final String hebWord, final String transliterated)
			throws JAXBException {
		ObjectFactory objFactory;
		objFactory = new ObjectFactory();

		final AnalysisType analysis = objFactory.createAnalysisType();

		analysis.setId("1");
		BaseType base = null;

		base = objFactory.createBaseType();

		UnknownType unknown = null;

		unknown = objFactory.createUnknownType();
		setBase(base, transliterated, hebWord, "0", "");
		base.setUnknown(unknown);
		analysis.setBase(base);

		token.getAnalysis().add(analysis);

		return token;
	}

	public List<BaseType> getProperName(TokenType token, List<Integer> analysisIndexList) {
		final List<BaseType> list = new ArrayList<BaseType>();
		final List<AnalysisType> analysisList = token.getAnalysis();
		final int size = analysisList.size();
		for (int i = 0; i < size; i++) {
			final AnalysisType analysis = analysisList.get(i);
			final BaseType base = analysis.getBase();
			if (base != null) {
				final ProperNameType properName = base.getProperName();
				if (properName != null) {
					list.add(base);
					analysisIndexList.add(Integer.valueOf(i));
				}
			}
		}
		return list;
	}

	public String MWEAnalysisExist(TokenType token) {
		String id = "";
		final List<AnalysisType> analysesList = token.getAnalysis();
		final int analysisListSize = analysesList.size();
		for (int i = 0; i < analysisListSize; i++) {
			final AnalysisType analysis = analysesList.get(i);
			final BaseType base = analysis.getBase();
			if (base != null) {
				final MWEType mw = base.getMWE();
				if (mw != null) {
					id = mw.getId();
					break;
				}
			}
		}
		return id;
	}

	// ----------------------------------------------------------------------------------------------------------
	public void postAnalysis(List<ArticleType> articles) {
		ArticleType article = null;
		ParagraphType paragraph = null;
		SentenceType sentence = null;
		List<ParagraphType> paragraphList = null;
		List<SentenceType> sentenceList = null;
		List<TokenType> tokenList = null;
		int tokenIndex = -1;
		new ArrayList<Object>();

		article = articles.get(0); // a);
		paragraphList = article.getParagraph();
		final int paragraphListSize = paragraphList.size();
		for (int paragraphIndex = 0; paragraphIndex < paragraphListSize; paragraphIndex++) {
			paragraph = paragraphList.get(paragraphIndex); // p);
			sentenceList = paragraph.getSentence();
			final int sentenceListSize = sentenceList.size();
			for (int sentenceIndex = 0; sentenceIndex < sentenceListSize; sentenceIndex++) {
				sentence = sentenceList.get(sentenceIndex); // s);
				tokenList = sentence.getToken();
				final int tokenListSize = tokenList.size();

				tokenIndex = 0;
				while (tokenIndex < tokenListSize) {
					tokenList.get(tokenIndex);
					// System.out.println("token surface=" +
					// token.getSurface());
					checkNextMWExistence(tokenList, tokenIndex, tokenListSize);
					tokenIndex++;

				}
			}

		}
	}

	// -------------------------------------------------------------------------------------------------------------
	public void process(InputStream morphXMLanalysis, PrintWriter pw)
			throws SQLException, UnsupportedEncodingException, JAXBException {
		ArticleType article = null;
		ParagraphType paragraph = null;
		SentenceType sentence = null;
		TokenType token = null;
		AnalysisType analysis = null;
		List<ParagraphType> paragraphList = null;
		List<SentenceType> sentenceList = null;
		List<TokenType> tokenList = null;
		List<AnalysisType> analysesList = null;
		List<ArticleType> articles = null;

		int tokenIndex = -1;
		String nextTokenTransliterated = "";
		String prevId = "";
		String id = "";
		boolean mwMWExistFlag = false;
		boolean mwprefixExistCurrent = false;

		ArrayList<String> prevTransliteratedList = new ArrayList<String>();

		int firstMWTokenIndexInSentence = -1;

		final CorpusAnalysisReader reader = new CorpusAnalysisReader(morphXMLanalysis);

		/*
		 * if (reader == null) { System.out.println(
		 * "input to postProcessor is null - existing now"); return; }
		 */

		articles = reader.getCorpus().getArticle();

		if (articles == null) {// UPDATE 21.11.10 (yossi) - insted of exiting
			// return so it can move to next file

			return;
		}

		article = articles.get(0); // a);
		paragraphList = article.getParagraph();
		final int paragraphListSize = paragraphList.size();
		for (int paragraphIndex = 0; paragraphIndex < paragraphListSize; paragraphIndex++) {
			paragraph = paragraphList.get(paragraphIndex); // p);
			sentenceList = paragraph.getSentence();
			final int sentenceListSize = sentenceList.size();

			for (int sentenceIndex = 0; sentenceIndex < sentenceListSize; sentenceIndex++) {
				multiWord = "";
				firstMWTokenIndexInSentence = -1;

				sentence = sentenceList.get(sentenceIndex); // s);
				tokenList = sentence.getToken();
				final int tokenListSize = tokenList.size();

				for (tokenIndex = 0; tokenIndex < tokenListSize; tokenIndex++) {
					TokenType prevToken = null;
					TokenType nextToken = null;
					token = tokenList.get(tokenIndex);
					if (((AnalysisType) token.getAnalysis().get(0)).getBase() != null
							&& ((AnalysisType) token.getAnalysis().get(0)).getBase().getPunctuation() != null) {
						continue;
					}
					// check whether the previous token had mw analysis
					// ////////////////////////////////////////////
					if (tokenIndex > 0) {
						prevToken = tokenList.get(tokenIndex - 1);
						final String prevTokenSurface = prevToken.getSurface();
						if (prevTokenSurface.equals("-") && tokenIndex - 1 > 0) {
							prevToken = tokenList.get(tokenIndex - 2);
						}
						prevId = MWEAnalysisExist(prevToken);

						if (!prevId.equals("")) {
							firstMWTokenIndexInSentence = tokenIndex;
							Integer.parseInt(prevId);
						} else {
							firstMWTokenIndexInSentence = -1;
						}
					}
					// ///////////////////////////////////////////

					if (tokenIndex + 1 < tokenListSize) {
						nextToken = tokenList.get(tokenIndex + 1);
						String nextSurface = nextToken.getSurface();
						if (nextSurface.equals("-")) {
							if (tokenIndex + 2 < tokenListSize) {
								nextToken = tokenList.get(tokenIndex + 2);
								nextSurface = nextToken.getSurface();
							}
						}
						nextTokenTransliterated = Translate.Heb2Eng(nextSurface);
						// System.out.println("nextTokenTransliterated ="+
						// nextTokenTransliterated);

						checkNextToken(nextToken, tokenListSize);
					} else {
						nextTokenTransliterated = "";
					}
					// System.out.println(" tokenIndex =" + tokenIndex);
					// System.out.println("token surface=" +
					// token.getSurface());

					/*
					 * if(token.getSurface().equals("פי")) System.out.println();
					 */

					analysesList = token.getAnalysis();
					int analysisListSize = analysesList.size();
					if (analysisListSize == 0) {
						System.err.println("No analysis for " + token.getSurface());
					}
					mwMWExistFlag = false;

					for (int analysisIndex = 0; analysisIndex < analysisListSize; analysisIndex++) {
						analysis = analysesList.get(analysisIndex);

						final BaseType base = analysis.getBase();
						if (base != null) {

							// ////////////////////////////////////////////////////////
							// MultiWord
							// //////////////////////////////////////////////////////

							final MWEType mweType = base.getMWE();
							if (mweType != null) {
								if (analysis.getSuffix() != null) {
									continue;
								}

								removeAnalysisFlag = false;

								final String pos = analysis.getBase().getMWE().getPos();
								final String type = analysis.getBase().getMWE().getType();
								// if (pos.equals("properName"))
								// continue;
								id = analysis.getBase().getMWE().getId();

								String transliterated = Translate.Heb2Eng(token.getSurface());

								final List<PrefixType> prefixList = analysis.getPrefix();
								String prefixTransliterated = "";
								int prefixLen = 0;
								if (prefixList.size() > 0) {
									for (int prefixIndex = 0; prefixIndex < prefixList.size(); prefixIndex++) {
										prefixTransliterated = prefixList.get(prefixIndex).getSurface();
										prefixLen = prefixLen + prefixTransliterated.length();
									}
								}

								transliterated = transliterated.substring(prefixLen);
								// System.out.println(" transliterated ="+
								// transliterated);
								if (!mwMWExistFlag) {
									prevTransliteratedList.add(transliterated);
								}

								mwMWExistFlag = true;

								final String consecutive = analysis.getBase().getMWE().getConsecutive();

								mwprefixExistCurrent = analysis.getBase().getMWE().isMultiWordPrefixExist();

								final int expectedPrevId = Integer.parseInt(id) - 1;
								final String expectedPrevIdStr = String.valueOf(expectedPrevId);

								// System.out.println(token.getSurface());
								// ביטוי עם מזהה שונה מ1 לא יכול להיות
								// התמנית
								// הראשונה של ביטוי
								if (firstMWTokenIndexInSentence == -1 && !id.equals("1")) {
									removeAnalysisFlag = true;
								} else if (tokenIndex == tokenListSize - 1 && id.equals("1")) {
									removeAnalysisFlag = true;

								} else if (consecutive.equals("true")) {

									if (!id.equals("1")) {
										try {
											if (!checkBackwords(id, expectedPrevIdStr, prevId, transliterated,
													prevTransliteratedList, analysis, prevToken, token)) {
												removeAnalysisFlag = true;
											}

										} catch (final UnsupportedEncodingException e) {
											e.printStackTrace();
										}

									} else {
										if (!mwprefixExistCurrent) {
											if (!checkForward(transliterated, nextTokenTransliterated, pos, type)) {
												removeAnalysisFlag = true;
											}
										} else if (!checkPrefixForward(transliterated, nextToken, pos, "unspecified")) {
											removeAnalysisFlag = true;
										}

									}
								}
								if (removeAnalysisFlag) {
									token.getAnalysis().remove(analysisIndex);
									analysisIndex--;
									arrangeAnalysisIdAfterRemove(token);
									analysisListSize = analysesList.size();
									if (analysisListSize == 0) {
										token = createUnknownAnalysis(token, token.getSurface(), transliterated);
									}

								}
							}

						}
					}

					if (!mwMWExistFlag) {
						prevTransliteratedList = new ArrayList<String>();
					}

				}

			}

		}
		postAnalysis(articles);
		reader.save(pw);
	}

	public void process(String inputFile, String outputFile)
			throws SQLException, UnsupportedEncodingException, JAXBException, FileNotFoundException {
		ArticleType article = null;
		ParagraphType paragraph = null;
		SentenceType sentence = null;
		TokenType token = null;
		AnalysisType analysis = null;
		List<ParagraphType> paragraphList = null;
		List<SentenceType> sentenceList = null;
		List<TokenType> tokenList = null;
		List<AnalysisType> analysesList = null;
		List<ArticleType> articles = null;

		int tokenIndex = -1;
		String nextTokenTransliterated = "";
		String prevId = "";
		String id = "";
		ArrayList<String> prevTransliteratedList = new ArrayList<String>();
		boolean mwMWExistFlag = false;
		boolean mwprefixExistCurrent = false;
		int firstMWTokenIndexInSentence = -1;

		final CorpusAnalysisReader reader = new CorpusAnalysisReader(inputFile);

		articles = reader.getCorpus().getArticle();
		if (articles == null) {
			// return so it can move to next file
			return;
		}
		article = articles.get(0);
		paragraphList = article.getParagraph();
		final int paragraphListSize = paragraphList.size();
		for (int paragraphIndex = 0; paragraphIndex < paragraphListSize; paragraphIndex++) {
			paragraph = paragraphList.get(paragraphIndex); // p);
			sentenceList = paragraph.getSentence();
			final int sentenceListSize = sentenceList.size();

			for (int sentenceIndex = 0; sentenceIndex < sentenceListSize; sentenceIndex++) {
				multiWord = "";
				firstMWTokenIndexInSentence = -1;

				sentence = sentenceList.get(sentenceIndex); // s);
				tokenList = sentence.getToken();
				final int tokenListSize = tokenList.size();

				for (tokenIndex = 0; tokenIndex < tokenListSize; tokenIndex++) {
					TokenType prevToken = null;
					TokenType nextToken = null;
					token = tokenList.get(tokenIndex);
					if (token.getAnalysis() == null || token.getAnalysis().size() == 0) {
						System.err.println("Token id:" + token.getId() + " " + token.getSurface() + " has no analysis");
						System.out.println("Token id:" + token.getId() + " " + token.getSurface() + " has no analysis");
					}
					if ((AnalysisType) token.getAnalysis().get(0) != null
							&& ((AnalysisType) token.getAnalysis().get(0)).getBase() != null
							&& ((AnalysisType) token.getAnalysis().get(0)).getBase().getPunctuation() != null) {
						continue;
					}
					// check whether the previous token had mw analysis
					// ////////////////////////////////////////////
					if (tokenIndex > 0) {
						prevToken = tokenList.get(tokenIndex - 1);
						final String prevTokenSurface = prevToken.getSurface();
						if (prevTokenSurface.equals("-") && tokenIndex - 1 > 0) {
							prevToken = tokenList.get(tokenIndex - 2);
						}
						prevId = MWEAnalysisExist(prevToken);

						if (!prevId.equals("")) {
							firstMWTokenIndexInSentence = tokenIndex;
							Integer.parseInt(prevId);
						} else {
							firstMWTokenIndexInSentence = -1;
						}
					}
					// ///////////////////////////////////////////

					if (tokenIndex + 1 < tokenListSize) {
						nextToken = tokenList.get(tokenIndex + 1);
						String nextSurface = nextToken.getSurface();
						if (nextSurface.equals("-")) {
							if (tokenIndex + 2 < tokenListSize) {
								nextToken = tokenList.get(tokenIndex + 2);
								nextSurface = nextToken.getSurface();
							}
						}
						nextTokenTransliterated = Translate.Heb2Eng(nextSurface);
						checkNextToken(nextToken, tokenListSize);
					} else {
						nextTokenTransliterated = "";
					}

					if (token.getSurface().equals("בהתחשב")) {
						System.out.println();
					}

					analysesList = token.getAnalysis();
					int analysisListSize = analysesList.size();
					mwMWExistFlag = false;

					for (int analysisIndex = 0; analysisIndex < analysisListSize; analysisIndex++) {
						analysis = analysesList.get(analysisIndex);

						final BaseType base = analysis.getBase();
						if (base != null) {
							// ////////////////////////////////////////////////////////
							// MultiWord
							// //////////////////////////////////////////////////////

							final MWEType mweType = base.getMWE();
							if (mweType != null) {
								if (analysis.getSuffix() != null) {
									continue;
								}

								removeAnalysisFlag = false;

								final String pos = analysis.getBase().getMWE().getPos();
								final String type = analysis.getBase().getMWE().getType();
								id = analysis.getBase().getMWE().getId();

								String transliterated = Translate.Heb2Eng(token.getSurface());

								final List<PrefixType> prefixList = analysis.getPrefix();
								String prefixTransliterated = "";
								int prefixLen = 0;
								if (prefixList.size() > 0) {
									for (int prefixIndex = 0; prefixIndex < prefixList.size(); prefixIndex++) {
										prefixTransliterated = prefixList.get(prefixIndex).getSurface();
										prefixLen = prefixLen + prefixTransliterated.length();
									}
								}

								transliterated = transliterated.substring(prefixLen);
								if (!mwMWExistFlag) {
									prevTransliteratedList.add(transliterated);
								}

								mwMWExistFlag = true;

								final String consecutive = analysis.getBase().getMWE().getConsecutive();

								mwprefixExistCurrent = analysis.getBase().getMWE().isMultiWordPrefixExist();

								final int expectedPrevId = Integer.parseInt(id) - 1;
								final String expectedPrevIdStr = String.valueOf(expectedPrevId);

								// System.out.println(token.getSurface());
								// ביטוי עם מזהה שונה מ1 לא יכול להיות
								// התמנית
								// הראשונה של ביטוי
								if (firstMWTokenIndexInSentence == -1 && !id.equals("1")) {
									removeAnalysisFlag = true;
								} else if (tokenIndex == tokenListSize - 1 && id.equals("1")) {
									removeAnalysisFlag = true;
								} else if (consecutive.equals("true")) {
									if (!id.equals("1")) {
										try {
											if (!checkBackwords(id, expectedPrevIdStr, prevId, transliterated,
													prevTransliteratedList, analysis, prevToken, token)) {
												removeAnalysisFlag = true;
											}
										} catch (final UnsupportedEncodingException e) {
											e.printStackTrace();
										}
									} else {
										if (!mwprefixExistCurrent) {
											if (!checkForward(transliterated, nextTokenTransliterated, pos, type)) {
												removeAnalysisFlag = true;
											}
										} else if (!checkPrefixForward(transliterated, nextToken, pos, "unspecified")) {
											removeAnalysisFlag = true;
										}
									}
								}
								if (removeAnalysisFlag) {
									token.getAnalysis().remove(analysisIndex);
									analysisIndex--;
									arrangeAnalysisIdAfterRemove(token);
									analysisListSize = analysesList.size();
									if (analysisListSize == 0) {
										token = createUnknownAnalysis(token, token.getSurface(), transliterated);
									}
								}
							}
						}
					}
					if (!mwMWExistFlag) {
						prevTransliteratedList = new ArrayList<String>();
					}
				}
			}
		}
		postAnalysis(articles);
		reader.save(outputFile);
		// System.exit(0);
	}

	public void processProperNameToken(int i, int j, List<BaseType> prevBaseList, List<BaseType> nextBaseList,
			BaseType currentBase, TokenType token, TokenType prevToken, TokenType nextToken, int currentAnalysisIndex,
			List<Integer> prevAnalysisIndexList, List<Integer> nextAnalysisIndexList) throws JAXBException {
		int prefixListsize = -1;
		final List<PrefixType> prefixList = new ArrayList<PrefixType>();

		final BaseType prevBase = prevBaseList.get(i);
		final BaseType nextBase = nextBaseList.get(j);

		final String prevProperNameType = prevBase.getProperName().getType();

		final String nextProperNameType = nextBase.getProperName().getType();

		final String currentProperNameType = currentBase.getProperName().getType();

		final String prevLexiconPointer = prevBase.getLexiconItem();
		final String nextLexiconPointer = nextBase.getLexiconItem();
		final String currentLexiconPointer = currentBase.getLexiconItem();

		if (prevProperNameType.equals(currentProperNameType) && currentProperNameType.equals(nextProperNameType)
				|| prevProperNameType == null || currentProperNameType == null || nextProperNameType == null) {
			multiWord = prevLexiconPointer + " " + currentLexiconPointer + " " + nextLexiconPointer;

			if (((AnalysisType) prevToken.getAnalysis().get(i)).getPrefix().size() > 0) {
				prefixListsize = ((AnalysisType) prevToken.getAnalysis().get(i)).getPrefix().size();
				PrefixType pref = null;
				for (int l = 0; l < prefixListsize; l++) {
					pref = (PrefixType) ((AnalysisType) prevToken.getAnalysis().get(i)).getPrefix().get(l);
					prefixList.add(pref);
				}

			}

			createMWWAnalysis(prevToken, prevBase, prevAnalysisIndexList.get(i).intValue(), multiWord, "1");
			createMWWAnalysis(token, currentBase, currentAnalysisIndex, multiWord, "2");
			for (int l = 0; l < prefixListsize; l++) {
				final PrefixType pref = prefixList.get(l);

				((AnalysisType) prevToken.getAnalysis().get(i)).getPrefix().add(pref);
			}
			createMWWAnalysis(nextToken, nextBase, nextAnalysisIndexList.get(j).intValue(), multiWord, "3");
		}

	}

	public void ProperNameAnalysisExists(TokenType prevToken, TokenType token, TokenType nextToken,
			int currentAnalysisIndex, BaseType currentBase) throws JAXBException {
		String multiWord = "";
		int prefixListsize = -1;
		List<BaseType> nextBaseList = null;
		final List<Integer> prevAnalysisIndexList = new ArrayList<Integer>();
		final List<Integer> nextAnalysisIndexList = new ArrayList<Integer>();
		final List<BaseType> prevBaseList = getProperName(prevToken, prevAnalysisIndexList);
		if (nextToken != null) {
			nextBaseList = getProperName(nextToken, nextAnalysisIndexList);
		}

		for (int i = 0; i < prevBaseList.size(); i++) {
			if (nextToken == null || nextBaseList.size() == 0) {
				final BaseType prevBase = prevBaseList.get(i);

				final String prevProperNameType = prevBase.getProperName().getType();

				final String currentProperNameType = currentBase.getProperName().getType();

				final String prevLexiconPointer = prevBase.getLexiconItem();

				final String currentLexiconPointer = currentBase.getLexiconItem();

				if (((AnalysisType) prevToken.getAnalysis().get(i)).getPrefix().size() > 0) {
					prefixListsize = ((AnalysisType) prevToken.getAnalysis().get(i)).getPrefix().size();
					for (int l = 0; l < prefixListsize; l++) {
						// FIX: There is a bug here. I don't know what, but the
						// result of the following expression should be kept
						// somewhere
						((AnalysisType) prevToken.getAnalysis().get(i)).getPrefix().get(l);
					}

				}

				if (prevProperNameType.equals(currentProperNameType) || prevProperNameType == null
						|| currentProperNameType == null) {
					multiWord = prevLexiconPointer + " " + currentLexiconPointer;
					// System.out.println(multiWord);
					createMWWAnalysis(prevToken, prevBase, prevAnalysisIndexList.get(i).intValue(), multiWord, "1");
					createMWWAnalysis(token, currentBase, currentAnalysisIndex, multiWord, "2");
					if (nextToken != null) {
						for (int l = 0; l < prefixListsize; l++) {
							final AnalysisType nextAnalysis = (AnalysisType) nextToken.getAnalysis().get(currentAnalysisIndex);

							final PrefixType pref = (PrefixType) nextAnalysis.getPrefix().get(l);

							((AnalysisType) token.getAnalysis().get(currentAnalysisIndex)).getPrefix().add(pref);
						}
					}

				}
			} else {
				if (nextToken != null) {
					final int nextBaseListSize = nextBaseList.size();
					for (int j = 0; j < nextBaseListSize; j++) {
						if (((AnalysisType) nextToken.getAnalysis().get(nextAnalysisIndexList.get(j).intValue())).getPrefix()
								.size() == 0) {
							processProperNameToken(i, j, prevBaseList, nextBaseList, currentBase, token, prevToken, nextToken,
									currentAnalysisIndex, prevAnalysisIndexList, nextAnalysisIndexList);
						}
					}
				}
			}

		}

	}

	// ------------------------------------------------------------------------------------------------------
	public void removeAnalysisByPos(TokenType token, String pos) {
		final List<AnalysisType> analysesList = token.getAnalysis();
		final int analysisListSize = analysesList.size();
		for (int i = 0; i < analysisListSize; i++) {
			final AnalysisType analysis = analysesList.get(i);
			final BaseType base = analysis.getBase();
			if (base != null) {
				final MWEType mw = base.getMWE();
				if (mw != null) {
					final String currentPos = mw.getPos();
					final String id = mw.getId();
					if (id.equals("1") && !currentPos.equals(pos)) {
						token.getAnalysis().remove(i);
						break;
					}
				}
			}
		}

	}

	public boolean removeAnalysisByPrevId(TokenType prevToken, String expectedPrevId) {
		boolean rt = false;
		final List<AnalysisType> analysesList = prevToken.getAnalysis();
		final int analysisListSize = analysesList.size();
		for (int i = 0; i < analysisListSize; i++) {
			final AnalysisType analysis = analysesList.get(i);
			final BaseType base = analysis.getBase();
			if (base != null) {
				final MWEType mw = base.getMWE();
				if (mw != null) {
					final String id = mw.getId();
					if (id.equals(expectedPrevId)) {
						rt = true;
						break;
					}
				}
			}
		}
		return rt;
	}

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
}
