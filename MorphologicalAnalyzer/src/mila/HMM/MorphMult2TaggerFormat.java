/*
 * Created on 03/05/2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package mila.HMM;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Random;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import mila.generated.AnalysisType;
import mila.generated.ArticleType;
import mila.generated.BaseType;
import mila.generated.Corpus;
import mila.generated.GenderNumberStatusDefinitenessType;
import mila.generated.ParagraphType;
import mila.generated.ParticipleType;
import mila.generated.PrefixType;
import mila.generated.ProperNameType;
import mila.generated.SentenceType;
import mila.generated.TokenType;
import mila.generated.VerbType;
import mila.lexicon.analyse.Constants;
import mila.lexicon.analyse.Data;
import mila.lexicon.dbUtils.PrefixRecord;
import mila.lexicon.utils.PrefixRec;
import mila.lexicon.utils.StringUtils;
import mila.lexicon.utils.Translate;
import mila.HMM.PerformUniqeOutput;
/**
 * @author daliabo
 * 
 *         TODO To change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public class MorphMult2TaggerFormat implements Constants {
	// final String JAXB_PACKAGE = "mila.generated";

	public static void main(String[] args) {
		String inputFile = "C:\\Documents and Settings\\daliabo\\My Documents\\lexicon\\diffTests\\output1.xml";
		String outputFile = "C:\\Documents and Settings\\daliabo\\My Documents\\lexicon\\diffTests\\outputRoy1.txt";
		// String inputFile = args[0];
		// String outputFile = args[1];
		MorphMult2TaggerFormat m = new MorphMult2TaggerFormat();
		m.process(inputFile, outputFile);
		System.exit(0);

	}

	BufferedWriter bw = null;

	BufferedReader bi = null;

	StringBuffer outputString = new StringBuffer();

	public void handleAdjective(BaseType base, String hebWord, boolean prefixExistFlag) throws IOException {
		GenderNumberStatusDefinitenessType adjective = base.getAdjective();
		ParticipleType participle = base.getParticiple();
		String definiteness = "";
		String status = "";
		if (adjective != null) {
			definiteness = adjective.getDefiniteness();
			status = adjective.getStatus();
		} else {
			definiteness = participle.getDefiniteness();
			status = participle.getStatus();
		}
		if (definiteness != null && definiteness.equals("true")) {
			if (!prefixExistFlag) {
				bw.write("\t");
				outputString.append("\t" + "(ADJECTIVE-DEF " + hebWord + ")" + "\n");
			} else
				outputString.append("(ADJECTIVE-DEF " + hebWord + ")" + "\n");
			bw.write("(ADJECTIVE-DEF " + hebWord + ")");
			bw.newLine();

		} else if (status != null && status.equals("construct")) {
			if (!prefixExistFlag) {
				bw.write("\t");
				outputString.append("\t" + "(ADJECTIVE-CONST " + hebWord + ")" + "\n");
			} else
				outputString.append("(ADJECTIVE-CONST " + hebWord + ")" + "\n");
			bw.write("(ADJECTIVE-CONST " + hebWord + ")");
			bw.newLine();

			// �� ������������ ���� ������ �status=absolute
		} else if (status != null && status.equals("absolute") || status.equals("unspecified")) {
			if (!prefixExistFlag) {
				bw.write("\t");
				outputString.append("\t" + "(ADJECTIVE " + hebWord + ")" + "\n");
			} else
				outputString.append("(ADJECTIVE " + hebWord + ")" + "\n");
			bw.write("(ADJECTIVE " + hebWord + ")");
			bw.newLine();

		}
	}

	public void handleDefinitedProperName(String hebWord, boolean prefixExistFlag) throws IOException {
		if (!prefixExistFlag) {
			bw.write("\t");
			outputString.append("\t" + "(PROPERNAME-DEF " + hebWord + ")" + "\n");
		} else
			outputString.append("(PROPERNAME-DEF " + hebWord + ")" + "\n");

		bw.write("(PROPERNAME-DEF " + hebWord + ")");
		bw.newLine();
	}

	public boolean handleHebWordAndPunctuation(String hebWord, List analysisList) throws IOException {
		boolean punctuation = false;
		if (analysisList.size() > 0) {
			AnalysisType analysis = (AnalysisType) analysisList.get(0);
			if (analysis.getBase() != null && analysis.getBase().getPunctuation() != null) {
				if (hebWord.length() == 1) {
					if (hebWord.charAt(0) == '%') {
						bw.write("O");
						bw.newLine();
						outputString.append("O\n");
						punctuation = true;

					} else if (hebWord.charAt(0) == '"') {
						bw.write("U");
						bw.newLine();
						outputString.append("U\n");
						punctuation = true;

					} else if (hebWord.charAt(0) == '\'') {
						bw.write("A");
						bw.newLine();
						outputString.append("A\n");
						punctuation = true;
					}
					if (hebWord.charAt(0) == '"') {
						bw.write("\t");
						bw.write("(yyQUOT yyQUOT)");
						bw.newLine();
						outputString.append("\t(yyQUOT yyQUOT)\n");
						punctuation = true;

					}
					// Roy doesn't know this punctuation - so we handle it a
					// little bit diffrent from quat "
					if (hebWord.charAt(0) == '\'') {
						bw.write("\t");
						bw.write("(APOS APOS)");
						bw.newLine();
						outputString.append("\t(APOS APOS)\n");
						punctuation = true;

						// Roy doesn't know this punctuation - so we handle it a
						// little bit diffrent from quat "
					} else if (hebWord.charAt(0) == '<') {
						bw.write(hebWord);
						bw.newLine();
						bw.write("\t");
						bw.write("(SML SML)");
						bw.newLine();
						outputString.append(hebWord + "\n\t(SML SML)\n");
						punctuation = true;
						// Roy doesn't know this punctuation - so we handle it a
						// little bit diffrent from quat "
					} else if (hebWord.charAt(0) == '>') {
						bw.write(hebWord);
						bw.newLine();
						bw.write("\t");
						bw.write("(LRG LRG)");
						bw.newLine();
						outputString.append(hebWord + "\n\t(LRG LRG)\n");
						punctuation = true;

					} else if (hebWord.charAt(0) == '[') {
						bw.write(hebWord);
						bw.newLine();
						bw.write("\t");
						bw.write("(SLRB SLRB)");
						bw.newLine();
						outputString.append(hebWord + "\n\t(SLRB SLRB)\n");
						punctuation = true;

					} else if (hebWord.charAt(0) == ']') {
						bw.write(hebWord);
						bw.newLine();
						bw.write("\t");
						bw.write("(SRRB SRRB)");
						bw.newLine();
						outputString.append(hebWord + "\n\t(SRRB SRRB)\n");
						punctuation = true;

					} else if (hebWord.charAt(0) == '{') {
						bw.write(hebWord);
						bw.newLine();
						bw.write("\t");
						bw.write("(CLRB CLRB)");
						bw.newLine();
						outputString.append(hebWord + "\n\t(CLRB CLRB)\n");
						punctuation = true;
					} else if (hebWord.charAt(0) == '/') {
						bw.write(hebWord);
						bw.newLine();
						bw.write("\t");
						bw.write("(LL LL)");
						bw.newLine();
						outputString.append(hebWord + "\n\t(LL LL)\n");
						punctuation = true;

					} else if (hebWord.charAt(0) == '}') {
						bw.write(hebWord);
						bw.newLine();
						bw.write("\t");
						bw.write("(CRRB CRRB)");
						bw.newLine();
						outputString.append(hebWord + "\n\t(CRRB CRRB)\n");
						punctuation = true;

					} else if (hebWord.charAt(0) == '(') {
						bw.write(hebWord);
						bw.newLine();
						bw.write("\t");
						bw.write("(yyLRB yyLRB)");
						bw.newLine();
						outputString.append(hebWord + "\n\t(yyLRB yyLRB)\n");
						punctuation = true;

					} else if (hebWord.charAt(0) == ')') {
						bw.write(hebWord);
						bw.newLine();
						bw.write("\t");
						bw.write("(yyRRB yyRRB)");
						bw.newLine();
						outputString.append(hebWord + "\n\t(yyRRB yyRRB)\n");
						punctuation = true;

					} else if (hebWord.charAt(0) == '.') {
						bw.write(hebWord);
						bw.newLine();
						bw.write("\t");
						bw.write("(yyDOT yyDOT)");
						bw.newLine();
						outputString.append(hebWord + "\n\t(yyDOT yyDOT)\n");
						punctuation = true;

					} else if (hebWord.charAt(0) == ',') {
						bw.write(hebWord);
						bw.newLine();
						bw.write("\t");
						bw.write("(yyCM yyCM)");
						bw.newLine();
						outputString.append(hebWord + "\n\t(yyCM yyCM)\n");
						punctuation = true;

					} else if (hebWord.charAt(0) == ':') {
						bw.write(hebWord);
						bw.newLine();
						bw.write("\t");
						bw.write("(yyCLN yyCLN)");
						bw.newLine();
						outputString.append(hebWord + "\n\t(yyCLN yyCLN)\n");
						punctuation = true;

					} else if (hebWord.charAt(0) == '!') {
						bw.write(hebWord);
						bw.newLine();
						bw.write("\t");
						bw.write("(yyEXCL yyEXCL)");
						bw.newLine();
						outputString.append(hebWord + "\n\t(yyEXCL yyEXCL)\n");
						punctuation = true;

					}
					if (hebWord.charAt(0) == '%') {
						bw.write("\t");
						bw.write("(yyPERC yyPERC)");
						bw.newLine();
						outputString.append("\t(yyPERC yyPERC)\n");
						punctuation = true;

					} else if (hebWord.charAt(0) == '?') {
						bw.write(hebWord);
						bw.newLine();
						bw.write("\t");
						bw.write("(yyQM yyQM)");
						bw.newLine();
						outputString.append(hebWord + "\n\t(yyQM yyQM)\n");
						punctuation = true;

					} else if (hebWord.charAt(0) == ';') {
						bw.write(hebWord);
						bw.newLine();
						bw.write("\t");
						bw.write("(yySCLN yySCLN)");
						bw.newLine();
						outputString.append(hebWord + "\n\t(yySCLN yySCLN)\n");
						punctuation = true;

					} else if (hebWord.charAt(0) == '-') {
						bw.write(hebWord);
						bw.newLine();
						bw.write("\t");
						bw.write("(yyDASH yyDASH)");
						bw.newLine();
						outputString.append(hebWord + "\n\t(yyDASH yyDASH)\n");
						punctuation = true;
						// otherdash
					/*} else if (hebWord.charAt(0) == '�') {
						bw.write(hebWord);
						bw.newLine();
						bw.write("\t");
						bw.write("(yyDASH yyDASH)");
						bw.newLine();
						outputString.append(hebWord + "\n\t(yyDDASH yyDDASH)\n");
						punctuation = true;
					} else if (hebWord.charAt(0) == '�') {
						bw.write(hebWord);
						bw.newLine();
						bw.write("\t");
						bw.write("(yyUDASH yyUDASH)");
						bw.newLine();
						outputString.append(hebWord + "\n\t(yyUDASH yyUDASH)\n");
						punctuation = true;*/
					} else if (hebWord.equals("=")) {
						bw.write(hebWord);
						bw.newLine();
						bw.write("\t");
						bw.write("(EQL EQL)");
						bw.newLine();
						outputString.append(hebWord + "\n\t(EQL EQL)\n");
						punctuation = true;
					} else if (hebWord.equals("$")) {
						bw.write(hebWord);
						bw.newLine();
						bw.write("\t");
						bw.write("(DOLAR DOLAR)");
						bw.newLine();
						outputString.append(hebWord + "\n\t(DOLAR DOLAR)\n");
						punctuation = true;
						// unrecognized punctuation
					} else if (!punctuation) {
						// if(!hebWord.equals("\"")){
						bw.write(hebWord);
						bw.newLine();
						bw.write("\t");
						bw.write("(PUNC PUNC)");
						bw.newLine();
						outputString.append(hebWord + "\n\t(PUNC PUNC)\n");
						punctuation = true;
					}
				}
			}
		}
		return punctuation;
	}

	// public String myMorp2Tagger(String inStr, String outputDir) throws
	// JAXBException,
	// IOException {
	// String finalOutputString = "";
	// InputStream in = null;
	// FileOutputStream out = null;
	// try {
	// out = new FileOutputStream(outputDir + "/myOutputFile.nf");
	// } catch (FileNotFoundException e1) {
	// // TODO Auto-generated catch block
	// e1.printStackTrace();
	// }
	// OutputStreamWriter pOut = new OutputStreamWriter(out);
	// bw = new BufferedWriter(pOut);
	//
	// try {
	// in = new java.io.ByteArrayInputStream(inStr.getBytes("UTF-8"));
	// } catch (UnsupportedEncodingException e1) {
	// // TODO Auto-generated catch block
	// e1.printStackTrace();
	// }
	// parseXML(in);
	// bw.flush();
	// bw.close();
	//
	// Random generator = new Random();
	// String randomNum = String.valueOf(generator.nextInt());
	// String outputFileName = "/outputFile" + randomNum + ".nf";
	//
	// PerformUniqeOutput p = new PerformUniqeOutput();
	// finalOutputString = p.myUniqueOutput(outputString.toString(), outputDir
	// + outputFileName);
	// // return finalOutputString;
	// return outputFileName;
	// }

	public void handleNoun(AnalysisType analysis, BaseType base, String hebWord, boolean prefixExistFlag)
			throws IOException {
		ParticipleType participle = base.getParticiple();
		GenderNumberStatusDefinitenessType noun = base.getNoun();
		String definiteness = "";
		String status = "";
		if (noun != null) {
			definiteness = noun.getDefiniteness();
			status = noun.getStatus();
		} else {
			definiteness = participle.getDefiniteness();
			status = participle.getStatus();
		}
		if (analysis.getSuffix() != null) {
			if (!prefixExistFlag) {
				bw.write("\t");
				outputString.append("\t").append("(NOUN-POSSESSIVE " + hebWord + ")").append("\n");
			} else
				outputString.append("(NOUN-POSSESSIVE " + hebWord + ")").append("\n");
			bw.write("(NOUN-POSSESSIVE " + hebWord + ")");
			bw.newLine();

		} else if (definiteness != null && definiteness.equals("true")) {
			if (!prefixExistFlag) {
				bw.write("\t");
				outputString.append("\t" + "(NOUN-DEF " + hebWord + ")" + "\n");
			} else
				outputString.append("(NOUN-DEF " + hebWord + ")" + "\n");
			bw.write("(NOUN-DEF " + hebWord + ")");
			bw.newLine();

		} else if (status != null && status.equals("construct")) {
			if (!prefixExistFlag) {
				bw.write("\t");
				outputString.append("\t" + "(NOUN-CONST " + hebWord + ")" + "\n");
			} else
				outputString.append("(NOUN-CONST " + hebWord + ")" + "\n");
			bw.write("(NOUN-CONST " + hebWord + ")");
			bw.newLine();

		} else if (status != null && status.equals("absolute")) {
			if (!prefixExistFlag) {
				bw.write("\t");
				outputString.append("\t" + "(NOUN " + hebWord + ")" + "\n");
			} else
				outputString.append("(NOUN " + hebWord + ")" + "\n");
			bw.write("(NOUN " + hebWord + ")");
			bw.newLine();

		}
	}

	public void handleParticiple(AnalysisType analysis, String hebWord, boolean prefixExistFlag) throws IOException {
		BaseType base = analysis.getBase();
		ParticipleType participle = base.getParticiple();
		String type = participle.getType();
		if (type.equals("verb")) {
			if (!prefixExistFlag) {
				bw.write("\t");
				outputString.append("\t" + "(VERB " + hebWord + ")" + "\n");
			} else
				outputString.append("(VERB " + hebWord + ")" + "\n");
			bw.write("(VERB " + hebWord + ")");
			bw.newLine();
		} else if (type != null && !type.equals("verb")) {
			if (analysis.getSuffix() != null) {
				bw.write("\t");
				bw.write("(NOUN-POSSESSIVE " + hebWord + ")");
				bw.newLine();
			} else {
				String status = base.getParticiple().getStatus();
				String definiteness = base.getParticiple().getDefiniteness();
				base.getParticiple().getGender();
				base.getParticiple().getNumber();
				// type=noun or type=adjective
				if (status != null && status.equals("construct")) {
					bw.write("\t");
					bw.write("(PARTICIPLE-CONST " + hebWord + ")");
					bw.newLine();
					bw.write("\t");
					bw.write("(NOUN-CONST " + hebWord + ")");
					bw.newLine();
					bw.write("\t");
					bw.write("(ADJECTIVE-CONST " + hebWord + ")");
					bw.newLine();
				} else if (definiteness != null && definiteness.equals("true")) {
					if (!prefixExistFlag)
						hebWord = hebWord.substring(1);
					bw.write("\t");
					bw.write("(PARTICIPLE-DEF " + hebWord + ")");
					bw.newLine();
					bw.write("\t");
					bw.write("(NOUN-DEF " + hebWord + ")");
					bw.newLine();
					bw.write("\t");
					bw.write("(ADJECTIVE-DEF " + hebWord + ")");
					bw.newLine();
				} else if (status != null && status.equals("absolute")) {
					bw.write("\t");
					bw.write("(PARTICIPLE " + hebWord + ")");
					bw.newLine();
					bw.write("\t");
					bw.write("(NOUN " + hebWord + ")");
					bw.newLine();
					bw.write("\t");
					bw.write("(ADJECTIVE " + hebWord + ")");
					bw.newLine();
				}
			}
		}
	}

	public String handlePrefix(AnalysisType analysis) throws IOException {
		// System.out.println("(F) handlePrefix");
		List prefixList = analysis.getPrefix();
		int prefixListSize = prefixList.size();
		StringBuilder prefixSurfaceSB = new StringBuilder();
		for (int prefixIndex = 0; prefixIndex < prefixListSize; prefixIndex++) {
			PrefixType prefix = (PrefixType) prefixList.get(prefixIndex);
			String prefixSurface = prefix.getSurface();
			// System.out.println("(F) handlePrefix prefixSurface " +
			// prefixSurface);
			String transliteratedPrefix = Translate.Heb2Eng(prefixSurface);
			// System.out.println("(F) handlePrefix transliteratedPrefix " +
			// transliteratedPrefix);
			prefixSurfaceSB.append(prefixSurface);
			bw.write("\t");
			bw.write("(PREFIX " + transliteratedPrefix + ")");
			outputString.append("\t" + "(PREFIX " + transliteratedPrefix + ")");
		}
		return prefixSurfaceSB.toString();
	}

	public void handlePrefixProperName(String hebWord, boolean prefixExistFlag) throws IOException {
		outputString.append("(PROPERNAME " + hebWord + ")" + "\n");
		bw.write("(PROPERNAME " + hebWord + ")");
		bw.newLine();
	}

	public void handleProperName(BaseType base, String hebWord, boolean prefixExistFlag) throws IOException {
		ProperNameType properName = base.getProperName();
		String definiteness = properName.getDefiniteness();
		if (definiteness != null && definiteness.equals("true")) {
			if (!prefixExistFlag) {
				bw.write("\t");
				outputString.append("\t" + "(PROPERNAME-DEF " + hebWord + ")" + "\n");
			} else
				outputString.append("(PROPERNAME-DEF " + hebWord + ")" + "\n");
			bw.write("(PROPERNAME-DEF " + hebWord + ")");
			bw.newLine();

		} else {
			if (!prefixExistFlag) {
				bw.write("\t");
				outputString.append("\t" + "(PROPERNAME " + hebWord + ")" + "\n");
			} else
				outputString.append("(PROPERNAME " + hebWord + ")" + "\n");
			bw.write("(PROPERNAME " + hebWord + ")");
			bw.newLine();

		}
	}

	public void handleSimplePos(BaseType base, String hebWord, String pos, boolean prefixExistFlag) throws IOException {
		if (!prefixExistFlag) {
			bw.write("\t");
			outputString.append("\t" + "(" + pos + " " + hebWord + ")" + "\n");
		} else
			outputString.append("(" + pos + " " + hebWord + ")" + "\n");
		bw.write("(" + pos + " " + hebWord + ")");
		bw.newLine();
	}

	public void handleVerb(BaseType base, String hebWord, boolean prefixExistFlag) throws IOException {
		VerbType verb = base.getVerb();
		String tense = verb.getTense();
		if (tense != null && tense.equals("infinitive")) {
			if (!prefixExistFlag) {
				bw.write("\t");
				outputString.append("\t" + "(VERB-INF " + hebWord + ")" + "\n");
			} else
				outputString.append("(VERB-INF " + hebWord + ")" + "\n");
			bw.write("(VERB-INF " + hebWord + ")");
			bw.newLine();
		} else {
			if (!prefixExistFlag) {
				bw.write("\t");
				outputString.append("\t" + "(VERB " + hebWord + ")" + "\n");
			} else
				outputString.append("(VERB " + hebWord + ")" + "\n");
			bw.write("(VERB " + hebWord + ")");
			bw.newLine();
		}
	}

	// This is the function that is actually used in HMM web GUI - the above
	// function simukates it
	public String myMorp2Tagger(String inStr, String outputDir) throws JAXBException, IOException {
		InputStream in = null;
		FileOutputStream out = null;
		try {
			out = new FileOutputStream(outputDir + "/myOutputFile.nf");
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		OutputStreamWriter pOut = new OutputStreamWriter(out);
		bw = new BufferedWriter(pOut);

		try {
			in = new java.io.ByteArrayInputStream(inStr.getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		parseXML(in, false);
		bw.flush();
		bw.close();

		Random generator = new Random();
		String randomNum = String.valueOf(generator.nextInt());
		String outputFileName = "/outputFile" + randomNum + ".nf";

		PerformUniqeOutput p = new PerformUniqeOutput();
		p.myUniqueOutput(outputString.toString(), outputDir + outputFileName);
		// return finalOutputString;
		return outputFileName;
	}

	public String myWEBMorp2Tagger(String inStr, String outputDir) throws JAXBException, IOException {
		InputStream in = null;
		FileOutputStream out = null;
		try {
			out = new FileOutputStream(outputDir + "/myOutputFile.nf");
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		OutputStreamWriter pOut = new OutputStreamWriter(out);
		bw = new BufferedWriter(pOut);

		try {
			in = new java.io.ByteArrayInputStream(inStr.getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		parseXML(in, true);
		bw.flush();
		bw.close();

		Random generator = new Random();
		String randomNum = String.valueOf(generator.nextInt());
		String outputFileName = "/outputFile" + randomNum + ".nf";

		PerformUniqeOutput p = new PerformUniqeOutput();
		p.myUniqueOutput(outputString.toString(), outputDir + outputFileName);
		// return finalOutputString;
		return outputFileName;
	}

	public void parseXML(InputStream in, boolean webFlag) throws JAXBException, IOException {
		// System.out.println("(F) parseXML");
		// for running in my PC with eclipse uncomment Data.webFlag = true;
		if (webFlag)
			Data.webFlag = webFlag;

		List articleTypeList;
		List paragraphTypeList;
		List sentenceTypeList;
		List tokenTypeList;
		List analysisTypeList;
		Corpus collection = null;
		JAXBContext jc = JAXBContext.newInstance(JAXB_PACKAGE);
		Unmarshaller unmarshaller = jc.createUnmarshaller();
		unmarshaller.setValidating(false);
		collection = (Corpus) unmarshaller.unmarshal(in);
		articleTypeList = collection.getArticle();
		ArticleType article = (ArticleType) articleTypeList.get(0);
		String gatheredPrefixSurface = "";
		String pos = "";
		boolean prefixExistFlag = false;

		paragraphTypeList = article.getParagraph();
		int paragraphTypeListSize = paragraphTypeList.size();
		for (int paragraphIndex = 0; paragraphIndex < paragraphTypeListSize; paragraphIndex++) {

			ParagraphType paragraph = (ParagraphType) paragraphTypeList.get(paragraphIndex);
			sentenceTypeList = paragraph.getSentence();
			int sentenceTypeListSize = sentenceTypeList.size();
			for (int sentenceIndex = 0; sentenceIndex < sentenceTypeListSize; sentenceIndex++) {

				SentenceType sentence = (SentenceType) sentenceTypeList.get(sentenceIndex);
				tokenTypeList = sentence.getToken();
				int tokenTypeListSize = tokenTypeList.size();
				for (int tokenIndex = 0; tokenIndex < tokenTypeListSize; tokenIndex++) {
					TokenType token = (TokenType) tokenTypeList.get(tokenIndex);
					analysisTypeList = token.getAnalysis();
					int analysisTypeListSize = analysisTypeList.size();

					String surface = token.getSurface();
					// System.out.println("(F) parseXML surface " + surface);
					if (surface.startsWith("#"))
						continue; // if its '#' then skip to next token
					/*
					 * if (surface.startsWith(";")) continue; // if its ';' then skip to next token
					 * if (surface.startsWith("&")) continue; // if its '&' then skip to next token
					 * if (surface.startsWith("-")) continue; // if its '-' then skip to next token
					 */
					String hebWord = Translate.HMMHeb2Eng(surface);

					// System.out.println("(F) parseXML hebWord " + hebWord);

					String originalHebWord = hebWord;

					boolean punctuatuionFlag = handleHebWordAndPunctuation(hebWord, analysisTypeList);
					if (!punctuatuionFlag) {
						hebWord = hebWord.replaceAll("\"", "U");
						hebWord = hebWord.replaceAll("\'", "A");
						bw.write(hebWord);
						bw.newLine();
						outputString.append(hebWord).append("\n");

						for (int analysisIndex = 0; analysisIndex < analysisTypeListSize; analysisIndex++) {
							AnalysisType analysis = (AnalysisType) analysisTypeList.get(analysisIndex);
							BaseType base = analysis.getBase();

							if (analysis.getPrefix().size() > 0) {
								gatheredPrefixSurface = handlePrefix(analysis);
								// System.out.println("(F) parseXML gatheredPrefixSurface "
								// +
								// Translate.HMMHeb2Eng(gatheredPrefixSurface));
								// System.out.println("(F) parseXML originalHebWord "
								// + originalHebWord);
								int gatheredPrefixLen = gatheredPrefixSurface.length();
								int originalHebWordLen = originalHebWord.length(); // added 3.5.11
								// gatheredPrefixLen = (gatheredPrefixLen >
								// originalHebWordLen) ? (originalHebWordLen +
								// 1) : gatheredPrefixLen; // added 3.5.11
								// protection from
								// StringIndexOutOfBoundsException
								if (gatheredPrefixLen > originalHebWordLen) // if
								// its
								// longer
								// then
								// cant
								// read
								{
									hebWord = "";
								} else {
									hebWord = originalHebWord.substring(gatheredPrefixLen);
								}
								prefixExistFlag = true;
							} else
								prefixExistFlag = false;

							if (base != null) {
								if (base.getAdjective() != null)
									handleAdjective(base, hebWord, prefixExistFlag);
								else if (base.getNoun() != null)
									handleNoun(analysis, base, hebWord, prefixExistFlag);
								else if (base.getParticiple() != null) {
									ParticipleType participle = base.getParticiple();
									String participleType = participle.getType();
									switch (participleType.charAt(0)) {
									case 'n':
										handleNoun(analysis, base, hebWord, prefixExistFlag);
										break;
									case 'a':
										handleAdjective(base, hebWord, prefixExistFlag);
										break;
									case 'v':
										handleParticiple(analysis, hebWord, prefixExistFlag);
										break;
									}
								} else if (base.getProperName() != null) {
									handleProperName(base, hebWord, prefixExistFlag);
								} else if (base.getVerb() != null) {
									handleVerb(base, hebWord, prefixExistFlag);
								} else if (base.getAdverb() != null) {
									pos = "ADVERB";
									handleSimplePos(base, hebWord, pos, prefixExistFlag);
								} else if (base.getConjunction() != null) {
									pos = "CONJUNCTION";
									handleSimplePos(base, hebWord, pos, prefixExistFlag);
								} else if (base.getMWE() != null) {
									pos = "MWE";
									handleSimplePos(base, hebWord, pos, prefixExistFlag);
								} else if (base.getCopula() != null) {
									pos = "COPULA";
									handleSimplePos(base, hebWord, pos, prefixExistFlag);
								} else if (base.getExistential() != null) {
									pos = "EXISTENTIAL";
									handleSimplePos(base, hebWord, pos, prefixExistFlag);
								} else if (base.getForeign() != null) {
									pos = "FOREIGN";
									handleSimplePos(base, hebWord, pos, prefixExistFlag);
								} else if (base.getInterjection() != null) {
									pos = "INTERJECTION";
									handleSimplePos(base, hebWord, pos, prefixExistFlag);
								} else if (base.getInterrogative() != null) {
									pos = "INTERROGATIVE";
									handleSimplePos(base, hebWord, pos, prefixExistFlag);
								} else if (base.getModal() != null) {
									pos = "MODAL";
									handleSimplePos(base, hebWord, pos, prefixExistFlag);
								} else if (base.getModal() != null) {
									pos = "MODAL";
									handleSimplePos(base, hebWord, pos, prefixExistFlag);
								} else if (base.getNegation() != null) {
									pos = "NEGATION";
									handleSimplePos(base, hebWord, pos, prefixExistFlag);
								} else if (base.getNumberExpression() != null) {
									pos = "NUMBEREXPRESSION";
									handleSimplePos(base, hebWord, pos, prefixExistFlag);
								} else if (base.getPreposition() != null) {
									pos = "PREPOSITION";
									handleSimplePos(base, hebWord, pos, prefixExistFlag);
								} else if (base.getPronoun() != null) {
									pos = "PRONOUN";
									handleSimplePos(base, hebWord, pos, prefixExistFlag);
								} else if (base.getQuantifier() != null) {
									pos = "QUANTIFIER";
									handleSimplePos(base, hebWord, pos, prefixExistFlag);
								} else if (base.getTitle() != null) {
									pos = "TITLE";
									handleSimplePos(base, hebWord, pos, prefixExistFlag);
									// ////////////////////////////////////////////////
								} else if (base.getUnknown() != null) {
									pos = "PROPERNAME";
									handleSimplePos(base, hebWord, pos, prefixExistFlag);

									if (hebWord.charAt(0) == 'h') {
										hebWord = hebWord.substring(1);
										prefixExistFlag = false;
										handleDefinitedProperName(hebWord, prefixExistFlag);

									}
									// handle prefix + propername
									// Data.init(dprefixesFile);

									for (int i = 1; i < 6 && i < hebWord.length(); i++) {
										String properNamePrefix = hebWord.substring(0, i);
										String properNameBase = hebWord.substring(i);
										if (StringUtils.moshevkaleb(properNamePrefix)) {
											int prefixListSize = 0;
											try {

												prefixListSize = Data.getPrefixes(properNamePrefix);
											} catch (Exception e) {
												// TODO Auto-generated catch
												// block
												e.printStackTrace();
											}
											for (int prefixIndex = 0; prefixIndex < prefixListSize; prefixIndex++) {
												PrefixRecord pr = Data.analyzePrefixList(prefixIndex);
												String description = pr.getDescription();
												List list = null;
												list = Translate.analyzeMixedHebEng(description);
												int size = list.size();
												for (int prefixesCounter = 0; prefixesCounter < size; prefixesCounter++) {
													PrefixRec prefixRec = (PrefixRec) list.get(prefixesCounter);
													String prefixSurface = prefixRec.getSurface();
													String transliteratedPrefix = Translate.Heb2Eng(prefixSurface);
													bw.write("\t");
													bw.write("(PREFIX " + transliteratedPrefix + ")");
													outputString.append("\t" + "(PREFIX " + transliteratedPrefix + ")");

												}
												handlePrefixProperName(properNameBase, prefixExistFlag);
											}
										}
									}
									// ////////////////////////////////////////////////
								} else if (base.getUrl() != null) {
									pos = "URL";
									handleSimplePos(base, hebWord, pos, prefixExistFlag);
								} else if (base.getNumeral() != null) {
									pos = "NUMERAL";
									handleSimplePos(base, hebWord, pos, prefixExistFlag);
								} else if (base.getWPrefix() != null) {
									pos = "WPREFIX";
									handleSimplePos(base, hebWord, pos, prefixExistFlag);
								}

								// ������ �� ������ ���� ��� �5-
							} else {
								bw.newLine();
								outputString.append("\n");
								hebWord = originalHebWord;
							}

						}
					}
				}
				outputString.append("#\n");
				bw.write("#");
				bw.newLine();
			}
			outputString.append("</paragraph>\n");
			bw.write("</paragraph>");
			bw.newLine();
		}
	}

	public void process(String inputFile, String outputFile) {
		// System.out.println("(F) MorphMult2TaggerFormat.process()");
		System.out.println(inputFile);
		System.out.println(outputFile);
		try {
			readMorphFile(inputFile, outputFile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void readMorphFile(String TaggedMorphFile, String outputFile) throws IOException, JAXBException {
		// System.out.println("(F) MorphMult2TaggerFormat.readMorphFile()");
		FileOutputStream out = null;
		try {
			out = new FileOutputStream(outputFile);
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		OutputStreamWriter pOut = new OutputStreamWriter(out);
		bw = new BufferedWriter(pOut);
		InputStream in = new FileInputStream(new File(TaggedMorphFile));
		parseXML(in, true);
		bw.flush();
		bw.close();
		// System.out.println("input to uniq"+outputString.toString());
		PerformUniqeOutput p = new PerformUniqeOutput();
		p.myUniqueOutput(outputString.toString(),
				"C:\\Documents and Settings\\daliabo\\My Documents\\lexicon\\diffTests\\outputRoy2.txt");
	}
}
