package mila.lexicon.analyse;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Arrays;

import mila.corpus.CreateCorpusXML;
import mila.lexicon.utils.StringUtils;
import mila.lexicon.utils.Translate;

/**
 *
 * XMLMorphAnalyzer.java Purpose: interface to the morphological analyzer -
 * includes all the API's to the morphological analyzer
 *
 * @author Dalia Bojan
 * @version %G%
 */

public class XMLMorphAnalyzer {
	/**
	 * boolean flag implies whether to print messages of upload data duration during
	 * the run
	 */
	static boolean myVerboseFlag = false;

	/**
	 * database mode working
	 */
	public static void dataLoad() {
		Data.webFlag = true;
		Data.dinflectionsFile = "";
		Data.dprefixesFile = "";
		Data.gimatriaFile = "";
		Data.init(false);
	}

	/**
	 * data files mode working
	 *
	 * @param dinflectionsFile
	 *           dinflections input data file path
	 * @param dprefixesFile
	 *           dprefixes data file path
	 * @param gimatriasFile
	 *           gimatrias data file path
	 */
	public static void dataLoad(String dinflectionsFile, String dprefixesFile, String gimatriasFile) {
		final long startTime = System.currentTimeMillis();
		Data.webFlag = false;
		Data.dinflectionsFile = dinflectionsFile;
		Data.dprefixesFile = dprefixesFile;
		Data.gimatriaFile = gimatriasFile;
		Data.dinflectionsFile = dinflectionsFile;
		Data.init(myVerboseFlag);
		printTimesHandling(startTime);
	}

	public static void main(String[] args) {
		final XMLMorphAnalyzer a = new XMLMorphAnalyzer();

		// 1) working with input / output file/directories

		final int argc = args.length;
		boolean webFlag = false;
		String dinflectionsFile = "";
		String dprefixesFile = "";
		String gimartiasFile = "";
		String input = "";
		String output = "";
		File in;
		File out;
		switch (argc) {
		// Analyzing a single file
		case 6:
			webFlag = new Boolean(args[0]).booleanValue();
			// System.out.println("webFlag=" + webFlag);
			input = args[1];
			// System.out.println("input =" + input);
			output = args[2];
			// System.out.println("output=" + output);
			dinflectionsFile = args[3];
			// System.out.println("dinflections File=" + dinflectionsFile);
			dprefixesFile = args[4];
			// System.out.println("dprefixes File=" + dprefixesFile);
			gimartiasFile = args[5];
			// System.out.println("gimatrias File=" + gimartiasFile);
			in = new File(input);
			out = new File(output);
			if (in.isDirectory() && out.isDirectory()) {
				if (webFlag) {
					a.processDirectory(input, output);
				} else {
					a.processDirectory(input, output, dinflectionsFile, dprefixesFile, gimartiasFile);
				}
			} else if (!in.isDirectory() && !out.isDirectory()) {
				if (webFlag) {
					a.processSingleFile(input, output);
				} else {
					a.processSingleFile(input, output, dinflectionsFile, dprefixesFile, gimartiasFile);
				}
			} else {
				System.err.println("Input and output should both be files or should both be existing directories");
			}

			break;
		case 7:
			webFlag = new Boolean(args[0]).booleanValue();
			System.out.println("webFlag=" + webFlag);
			input = args[1];
			System.out.println("input =" + input);
			output = args[2];
			System.out.println("output=" + output);
			dinflectionsFile = args[3];
			System.out.println("dinflections File=" + dinflectionsFile);
			dprefixesFile = args[4];
			System.out.println("dprefixes File=" + dprefixesFile);
			gimartiasFile = args[5];
			System.out.println("gimatrias File=" + gimartiasFile);
			if (args[6].equals("-v") || args[6].equals("-verbose")) {
				myVerboseFlag = true;
			}
			System.out.println("verbose flag is on");
			in = new File(input);
			out = new File(output);
			if (in.isDirectory() && out.isDirectory()) {
				if (webFlag) {
					a.processDirectory(input, output);
				} else {
					a.processDirectory(input, output, dinflectionsFile, dprefixesFile, gimartiasFile);
				}
			} else if (!in.isDirectory() && !out.isDirectory()) {
				if (webFlag) {
					a.processSingleFile(input, output);
				} else {
					a.processSingleFile(input, output, dinflectionsFile, dprefixesFile, gimartiasFile);
				}
			} else {
				System.err.println("Input and output should both be files or should both be existing directories");
			}

			break;

		default:
			System.err.println("wrong parameters number");
			System.err.println(
					"java -Xmx1G -jar morphAnalyzer false <input File /directory> <output file / directory> dinflections.data dprefixes.data gimaria.data [-v]");

		}

		// /////////////////////////////////////////////////////
		// 2) working with input single token via web
		// StringWriter sw = new StringWriter();
		// PrintWriter pw = new PrintWriter(sw);
		// String inputString = "שב\"סניק";
		//
		// dataLoad();
		// a.analyzeSingleToken(pw, inputString);
		//
		// String result = sw.toString();
		// result = result.trim();
		// System.out.println(result);
		// System.exit(0);

		// /////////////////////////////////////////////////////
		// 2) working with input single token via files
		// StringWriter sw = new StringWriter();
		// PrintWriter pw = new PrintWriter(sw);
		// String inputString = "גן";
		//
		// boolean webFlag = (new Boolean(args[0]).booleanValue());
		// System.out.println("webFlag=" + webFlag);
		//
		// String dinflectionsFile = args[3];
		// System.out.println("dinflections File=" + dinflectionsFile);
		// String dprefixesFile = args[4];
		// System.out.println("dprefixes File=" + dprefixesFile);
		// String gimatriasFile = args[5];
		// System.out.println("gimatrias File=" + gimatriasFile);
		// dataLoad(dinflectionsFile, dprefixesFile, gimatriasFile);
		// a.analyzeSingleToken(pw, inputString);
		// String result = sw.toString();
		// result = result.trim();
		// System.out.println(result);

		System.exit(0);
	}

	/**
	 * Utility method for following the data load duration
	 *
	 * @param startTime
	 * @return
	 */
	protected static long printTimesHandling(long startTime) {
		final long afterLoadTime = System.currentTimeMillis();
		final long load2MemoryElapsedTime = afterLoadTime - startTime;
		if (myVerboseFlag) {
			System.out.println("load2Memory Elapsed time = " + load2MemoryElapsedTime + " ms");
		}
		return afterLoadTime;
	}

	/**
	 * This method is used for processing directories - it runs the tree of
	 * directories and analyzed each file in it
	 *
	 * @param inputDirectory
	 * @param outputDirectory
	 * @param pos
	 *           - index in the string which holds the input directory, it is used
	 *           for building the input directories tree in the output
	 */
	private void analyzeDirectory(File inputDirectory, String outputDirectory, final int pos) {
		if (inputDirectory.isDirectory()) {
			// create correspond directory for xml

			final String out = outputDirectory + inputDirectory.getAbsolutePath().substring(pos);
			// System.out.println(out);
			if (!new File(out).exists()) {
				if (new File(out).mkdir()) {
					System.out.println("Success creating directory: " + out);
				} else {
					System.err.println("Error in creation of directory: " + out);
				}
			}
			// call for analysis of each file/dir under the currect directory
			final File[] files = inputDirectory.listFiles();
			Arrays.sort(files);
			for (final File file : files) {
				analyzeDirectory(file, outputDirectory, pos);
			}

		} else { // file to be analyzed
			final String inputFile = inputDirectory.getAbsolutePath();
			final String outputFile = outputDirectory + inputFile.substring(pos);
			try {
				System.out.println(outputFile);
				analyzeFile(inputFile, outputFile);
			} catch (final Exception e) {

				e.printStackTrace();
			}
		}

	}

	/**
	 * This method is called when using input/output files - it is called by
	 * analyzeDirectory and processSingleFile
	 */
	private void analyzeFile(String inputFile, String outputFile) {
		try {
			// ///////////////////////////////////////
			ReadXMLFile(inputFile, outputFile);
			// //////////////////////////////////////
			// long elapsedTime = System.currentTimeMillis() - afterLoadTime;
			// System.out.println("analyze elapsed time = " + elapsedTime +
			// "ms");
			// System.out.println("tokens count = " + tokensCount);

			// createXML.printDoc();

		} catch (final Exception e) {
			System.err.println(
					"An error occured make sure you have tokenized the input file, if error still existes send the developer the input file");
			e.printStackTrace();
		} finally {
			// System.exit(0);
		}
	}

	/**
	 * This method is used when the input is a single token - we skip the tokenizer
	 * and get an analysis.<br>
	 * This method should be used in cases when we have the text already tokenized.
	 * It is quicker than any other way.<br>
	 *
	 * @param pw
	 *           output XML format according to Mila standards
	 * @param inputSt
	 *           input Hebrew single token string
	 */
	public void analyzeSingleToken(PrintWriter pw, String inputSt) {
		System.out.println("inputSt=" + inputSt);
		String hebWord = "";
		try {
			hebWord = URLDecoder.decode(inputSt, "UTF-8");
		} catch (final UnsupportedEncodingException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}

		final CreateCorpusXML createXML = new CreateCorpusXML();
		try {
			createXML.createXMLDoc();
		} catch (final Exception e) {
			System.out.println("XMLMorphAnalyzer:processXMLOutput while createXMLdOC - Exception");
			e.printStackTrace();
		}
		createXML.createArticle();
		createXML.createParagraph();
		createXML.createSentence();
		createXML.createToken(hebWord);

		final TokenizationParser tokenizationParser = new TokenizationParser(createXML);
		if (!checkKitzur(hebWord, tokenizationParser)) {
			try {
				tokenizationParser.readInput(hebWord);
			} catch (final IOException e) {
				e.printStackTrace();
			} catch (final Exception e) {
				e.printStackTrace();
			}
		}
		createXML.finalizeToken();
		createXML.finalizeSentence();
		createXML.finalizeParagraph();

		createXML.printDoc(pw);

	}

	/**
	 * This method is used only when input is a single token it simulates tokenizer
	 * kitzurim handling
	 *
	 * @param hebWord
	 * @return
	 */
	private boolean checkKitzur(String hebWord, TokenizationParser tokenizationParser) {
		boolean isKitzur = false;
		if (hebWord.endsWith("'")) {
			// System.out.println("analyzeStringInput : hebWord=" + hebWord);
			// token length
			int indexKitzur = 0;
			if ((indexKitzur = hebWord.indexOf("וכד'")) != -1 || (indexKitzur = hebWord.indexOf("וכדו'")) != -1
					|| (indexKitzur = hebWord.indexOf("מס'")) != -1 || (indexKitzur = hebWord.indexOf("מע'")) != -1
					|| (indexKitzur = hebWord.indexOf("מח'")) != -1 || (indexKitzur = hebWord.indexOf("וכו'")) != -1
					|| (indexKitzur = hebWord.indexOf("רח'")) != -1 || (indexKitzur = hebWord.indexOf("נק'")) != -1
					|| (indexKitzur = hebWord.indexOf("טל'")) != -1 || (indexKitzur = hebWord.indexOf("שכ'")) != -1
					|| (indexKitzur = hebWord.indexOf("שד'")) != -1 || (indexKitzur = hebWord.indexOf("גר'")) != -1
					|| (indexKitzur = hebWord.indexOf("פרופ'")) != -1 || (indexKitzur = hebWord.indexOf("עמ'")) != -1
					|| (indexKitzur = hebWord.indexOf("אונ'")) != -1 || (indexKitzur = hebWord.indexOf("וגו'")) != -1
					|| (indexKitzur = hebWord.indexOf("גב'")) != -1 || (indexKitzur = hebWord.indexOf("להית'")) != -1
					|| (indexKitzur = hebWord.indexOf("להת'")) != -1 || (indexKitzur = hebWord.indexOf("ושות'")) != -1
					|| (indexKitzur = hebWord.indexOf("ש\"ס")) != -1 || (indexKitzur = hebWord.indexOf("ש''ס")) != -1
					|| (indexKitzur = hebWord.indexOf("ש\"ח")) != -1 || (indexKitzur = hebWord.indexOf("ק\"ג")) != -1) {
				if (indexKitzur > 0) {
					isKitzur = true;

					String checkedPrefix = hebWord.substring(0, indexKitzur);
					String hebBase = hebWord.substring(indexKitzur);
					if (checkedPrefix.endsWith("ה")) {
						checkedPrefix = checkedPrefix.replace('ה', ' ').trim();
						hebBase = "ה" + hebBase;
					}
					if (StringUtils.moshevkaleb(Translate.Heb2Eng(checkedPrefix))) {

						tokenizationParser.analyzeBase(Translate.Heb2Eng(hebBase), Translate.Heb2Eng(checkedPrefix),
								Translate.Heb2Eng(hebWord), hebWord);
					}
				}
			}
		}
		return isKitzur;

	}

	/**
	 * In this method we assume that the data files are already loaded We use it in
	 * Client server architecture.
	 *
	 * @param pw
	 * @param tokenizationOutputStr
	 */
	public void morphologicalAnalyzer(PrintWriter pw, String tokenizationOutputStr) {
		dataLoad();
		// ///////////////////////////////////////
		ReadXMLFile(tokenizationOutputStr, pw);
		// //////////////////////////////////////
	}

	/**
	 * This method accepts XML String of tokenized text (the output of Mila
	 * Tokenizer) and produce analyzed text in XML format according to Mila
	 * standards hebrew_MWcorpus.xsd
	 *
	 * @param tokenizationOutputStr
	 *           input string containing tokenized Hebrew in XML format - the output
	 *           of Mila tokenizer
	 * @param pw
	 *           output XML analysis according to Mila standards
	 * @param dinflectionsFile
	 *           input data inflections file
	 * @param dprefixesFile
	 *           input data prefixes file
	 * @param gimatriasFile
	 *           input data gimatria file
	 */
	public void morphologicalAnalyzer(String tokenizationOutputStr, PrintWriter pw, String dinflectionsFile,
			String dprefixesFile, String gimatriasFile) {
		dataLoad(dinflectionsFile, dprefixesFile, gimatriasFile);
		// ///////////////////////////////////////
		ReadXMLFile(tokenizationOutputStr, pw);
		// //////////////////////////////////////
	}

	/**
	 * In this method we assume that the data files are already loaded We use it in
	 * data files mode.
	 *
	 * @param pw
	 * @param tokenizationOutputStr
	 */
	public void morphologicalAnalyzerNoDataLoad(PrintWriter pw, String tokenizationOutputStr) {
		// ///////////////////////////////////////
		ReadXMLFile(tokenizationOutputStr, pw);
		// //////////////////////////////////////
	}

	/**
	 * This method is used for mass processing in database mode working- It can
	 * process directory of files/directories. It is especially useful for
	 * processing corpora.
	 *
	 * @param inputDirectory
	 * @param outputDirectory
	 */
	public void processDirectory(String inputDirectory, String outputDirectory) {
		final File in = new File(inputDirectory);
		final int pos = in.isDirectory() ? in.getAbsolutePath().length() : in.getParent().length();

		dataLoad();
		analyzeDirectory(in, outputDirectory, pos);
	}

	/**
	 * This method is used for mass processing in data files mode working- It can
	 * process a directory of files/directories. It is especially useful for
	 * processing corpora.
	 *
	 * @param inputDirectory
	 *           path of directory which contains either xml tokenized files or
	 *           directories of xml tokenized file
	 * @param outputDirectory
	 *           path to the output created files. This directory will contain
	 *           output file with the same structure of the input files ( the
	 *           directories tree will be generated automaticaly)
	 * @param dinflectionsFile
	 *           inflections data file path
	 * @param dprefixesFile
	 *           prefixes data file path
	 * @param gimartiasFile
	 *           gimatrias data file path
	 */
	public void processDirectory(String inputDirectory, String outputDirectory, String dinflectionsFile,
			String dprefixesFile, String gimartiasFile) {

		final File in = new File(inputDirectory);
		final int pos = in.isDirectory() ? in.getAbsolutePath().length() : in.getParent().length();
		dataLoad(dinflectionsFile, dprefixesFile, gimartiasFile);
		analyzeDirectory(in, outputDirectory, pos);

	}

	/**
	 * This method is used for mass processing without dataLoading- It can process a
	 * directory of files/directories. It is especially useful for processing
	 * corpora. it is used in case data was loaded externally
	 *
	 * @param inputDirectory
	 * @param outputDirectory
	 */
	public void processDirectoryNoDataLoad(String inputDirectory, String outputDirectory) {
		final File in = new File(inputDirectory);
		final int pos = in.isDirectory() ? in.getAbsolutePath().length() : in.getParent().length();
		analyzeDirectory(in, outputDirectory, pos);
	}

	/**
	 * This method is used for processing a single xml already tokenized file in
	 * database mode working. This input file is prepared with Mila tokenizer. There
	 * is no need to provide the paths of the data files. Database detailes appears
	 * as hard coded in the connected file
	 *
	 * @param inputFile
	 *           - xml tokenized file
	 * @param outputFile
	 *           - xml output morphololgically analyzed file
	 */
	public void processSingleFile(String inputFile, String outputFile) {
		dataLoad();
		analyzeFile(inputFile, outputFile);

	}

	/**
	 * This method is used for processing a single xml file already tokenized by
	 * Mila tokenizer in data files mode working.
	 *
	 * @param inputFile
	 *           the output of Mila tokenizer - an XML file by Mila standards ( path
	 *           and name)
	 * @param outputFile
	 *           morphologically analyzed xml file according to hebrew_MWcorpus.xsd
	 *           ( path and name)
	 * @param dinflectionsFile
	 *           inflections data file path
	 * @param dprefixesFile
	 *           prefixes data file path
	 * @param gimartiasFile
	 *           gimatrias data file path
	 */
	public void processSingleFile(String inputFile, String outputFile, String dinflectionsFile, String dprefixesFile,
			String gimartiasFile) {
		dataLoad(dinflectionsFile, dprefixesFile, gimartiasFile);
		analyzeFile(inputFile, outputFile);

	}

	/**
	 * This method is used for handling an input string containing an XML
	 * tokenization according to Mila standards It handles building the output XML
	 * using jaxb The input XML is parsed using SAX
	 */
	private void ReadXMLFile(String tokenizationOutputStr, PrintWriter pw) {
		final CreateCorpusXML createXML = new CreateCorpusXML();
		createXML.createXMLDoc();
		createXML.createArticle();
		final TokenizationParser tokenizationParser = new TokenizationParser(createXML);
		try (final InputStream in = new ByteArrayInputStream(tokenizationOutputStr.getBytes("UTF-8"))) {
			tokenizationParser.parse(in);
			createXML.printDoc(pw);
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * This method is used for handling an input XML file which contains the
	 * tokenization It handles building the output XML using jaxb The input XML is
	 * parsed using SAX
	 */
	private void ReadXMLFile(String inputFile, String outputFile) {
		final CreateCorpusXML createXML = new CreateCorpusXML(outputFile);
		createXML.createXMLDoc();
		createXML.createArticle();
		try (InputStream in = new FileInputStream(new File(inputFile))) {
			new TokenizationParser(createXML).parse(in);
			createXML.printDoc();
		} catch (final Exception e) {
			e.printStackTrace();
		}

	}
}
