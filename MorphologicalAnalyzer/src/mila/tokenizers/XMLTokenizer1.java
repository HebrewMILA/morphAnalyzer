package mila.tokenizers;

/**
 *
 * XMLTokenizer1.java
 * Purpose: segment the input text (Hebrew without niqud UTF-8 text) to paragraphs, sentences and tokens.
 * Sometimes we add some clues for the morphological analyzer.
 * For example when there is a token like b-10 - we know for sure that b is a stand alone prefix
 * The output is an XML file or stream.
 * The XML is built using dom4j
 *
 * @author Dalia Bojan
 * @version     %G%
 */

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

import mila.lexicon.analyse.XMLMorphAnalyzer;
import mila.lexicon.utils.StringUtils;

import org.dom4j.Document;

import static mila.lexicon.analyse.Constants.*;

public class XMLTokenizer1 {
	protected BufferedReader bi = null;
	String line = "";
	protected XMLProcessor xmlTokenizer = null;
	boolean emptyLineFlag = false;
	int lineCounter = 0;
	boolean webFlag = false;

	final static int MAX_EMPTY_LINES = 100;
	final static private Set<String> kitzurim = new HashSet<>();

	static {
		populateKitzurim();
	}

	static private void populateKitzurim() {
		kitzurim.add("וכד'");
		kitzurim.add("וכדו'");
		kitzurim.add("מס'");
		kitzurim.add("נק'");
		kitzurim.add("מע'");
		kitzurim.add("מח'");
		kitzurim.add("וכו'");
		kitzurim.add("רח'");
		kitzurim.add("טל'");
		kitzurim.add("שכ'");
		kitzurim.add("שד'");
		kitzurim.add("גר'");
		kitzurim.add("פרופ'");
		kitzurim.add("אונ'");
		kitzurim.add("וגו'");
		kitzurim.add("גב'");
		kitzurim.add("להית'");
		kitzurim.add("להת'");
		kitzurim.add("י-ם");
	}

	public static void main(String[] args) {
		int argc = args.length;
		XMLTokenizer1 t = new XMLTokenizer1();

		switch (argc) {
		case 2:

			String in = args[0];
			String out = args[1];

			File inputFile = new File(in);
			System.out.println("input file=" + in);
			File outputFile = new File(out);
			System.out.println("output file=" + out);
			if (inputFile.isDirectory() && outputFile.isDirectory()) {
				t.processDirectory(in, out);

			} else if (!inputFile.isDirectory() && !outputFile.isDirectory()) {
				t.processSingleFile(in, out);

			} else {
				System.err.println("Both input parameters should be files or both input parameters should be directories");
				System.err.println("output directory must be an existing directory !!");
				System.err.println("exiting...");
			}
			// break;

			// case 3:
			// String st = "שומר";
			// String dinflectionsFile = args[0];
			// String dprefixesFile = args[1];
			// String gimartiasFile = args[2];
			// String XMLAnalysisResult = t.tokenizeAndAnalyzeSingleToken(st,
			// dinflectionsFile, dprefixesFile, gimartiasFile);
			//
			// System.out.println(XMLAnalysisResult);
			break;

		default:
			System.err.println(
					"Usage: java [-Xmx1024m] -jar tokenizer.java <inputfile/directory>" + " <output file/directory>");
			System.err.println("Both input parameters should be files or both input parameters should be directories");
			System.err.println(
					"in case of input files - the input must be a UTF-8 text file and the output file name must be with XML suffix");
			System.err.println("exiting...");
		}
	}

	/**
	 * A recursive handling of the provided input directory
	 *
	 * @param inputDirectory
	 *           inputDirectory containing UTF-8 text files
	 * @param outputDirectory
	 *           outputDirectory an existing or not existing directory which will
	 *           contain the output XML files in the same staructure they appear in
	 *           the input directory
	 * @param pos
	 */
	private void analyzeDirectory(File inputDirectory, String outputDirectory, final int pos) {
		if (inputDirectory.isDirectory()) {
			// create correspond directory for xml
			String out = outputDirectory + inputDirectory.getAbsolutePath().substring(pos);
			// System.out.println(out);
			if (!new File(out).exists()) {
				if (new File(out).mkdir()) {
					System.out.println("Success creating directory: " + out);
				} else {
					System.out.println("Error in creation of directory: " + out);
				}
			}
			// call for analysis of each file/dir under the currect directory
			File[] files = inputDirectory.listFiles();
			Arrays.sort(files);
			for (File file : files) {// int i = 0; i < files.length; i++)
				xmlTokenizer = null;
				lineCounter = 0;
				analyzeDirectory(file, outputDirectory, pos);
			}

		} else { // file to be analyzed
			String inputFile = inputDirectory.getAbsolutePath();
			String outputFile = outputDirectory + inputFile.substring(pos + 1);
			String newOutputFile = outputFile.substring(0, outputFile.lastIndexOf(".")) + ".xml";
			System.out.println(inputFile);
			System.out.println(newOutputFile);
			try {
				// System.out.println(outputFile);
				processSingleFile(inputFile, newOutputFile);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	// --------------------------------------------------------------------------------------------------------------------------
	/**
	 * Control function which works with input output files
	 *
	 * @param outputFile
	 * @return
	 * @throws Exception
	 */
	public int analyzeTokens(String outputFile) throws Exception {
		String token = "";
		String returnToken = "";

		// handling empty lines at the begining of the file
		dropStartFileEmptyLines();
		// creating infrastructure for creating xml document
		createTokenizedXML();
		// create the first paragraph
		xmlTokenizer.createParapraphes();
		// create the first sentence
		xmlTokenizer.createSentences();
		// analyzing the input file line by line
		while (line != null) {
			// Translate non-breaking spaces to regular spaces.
			line = line.replace((char) 160, ' ');
			line = line.trim();
			line = stripNonValidXMLCharacters(line); // remove invalid XML
			// characters
			line = line.replace('–', '-'); // replace width dash with small dash

			// handling the case of empty lines at the middle of the file - they
			// signify a
			// begining of a new paragraph
			if (dropMiddleFileEmptyLines()) {
				continue;
			}

			// analyzing the line tokens, tokens are separated from each other
			// by white spaces
			StringTokenizer st = new StringTokenizer(line);
			int tokenCount = st.countTokens();
			String[] result = new String[tokenCount];
			int index = 0;
			int i;
			while (st.hasMoreTokens()) {
				token = st.nextToken();
				result[index] = token;
				index++;
			}

			for (i = 0; i < tokenCount; i++) {
				token = result[i];
				returnToken = "";
				// System.out.println(token);
				if (token.indexOf("ה25/11") != -1) {
					System.out.println();
				}

				token = removeTnoaot(token);
				// System.out.println(token);

				if (token.indexOf("'") != -1 || token.indexOf("\"") != -1 || token.indexOf("-") != -1) {
					if (kitzurExists(token)) {
						continue;
					}
				}

				int prefixLen = findPrefix(token, result, i);
				String tokenWithoutPrefixWithSuffix = token.substring(prefixLen);

				if (tokenWithoutPrefixWithSuffix.length() > 0) {
					returnToken = handleToken(tokenWithoutPrefixWithSuffix);
				}

				if (returnToken != null && returnToken.length() > 0) {
					tokenWithoutPrefixWithSuffix = returnToken;
					int suffixLen = findSuffix(tokenWithoutPrefixWithSuffix);

					int tokenWithoutPrefixWithSuffixLen = tokenWithoutPrefixWithSuffix.length();

					String tokenSuffix = tokenWithoutPrefixWithSuffix.substring(tokenWithoutPrefixWithSuffixLen - suffixLen);

					int suffixStartIndex = tokenWithoutPrefixWithSuffixLen - suffixLen;
					String tokenWithoutPrefixWithoutSuffix = tokenWithoutPrefixWithSuffix.substring(0, suffixStartIndex);
					if (tokenWithoutPrefixWithoutSuffix.length() > 0) {
						xmlTokenizer.createTokens(tokenWithoutPrefixWithoutSuffix);
					}

					createSuffixTokens(tokenSuffix);
				}
			}

			lineCounter++;
			line = bi.readLine();
			// if (webFlag && xmlTokenizer.getGlobalTokenCounter() > 100)
			// break;
		}
		// System.out.println("Total number of tokens is:" +
		// xmlTokenizer.getTokenCounter());
		xmlTokenizer.printDoc(outputFile);
		bi.close();
		return xmlTokenizer.getGlobalTokenCounter();
	}

	/**
	 * We need to take a decision for tokens which ends with ' like g'wrg' The ' can
	 * be the end of quote or belongs to the token like in g'wrg' We defined several
	 * letters for which there is a high chance that the ' belongs to the token when
	 * they appear at the end of the token
	 *
	 * @param token
	 * @return
	 */
	public String apostropeeNotGimatriaHandling(String token) {
		int tokenLen = token.length();
		String returnValue = token;
		int apostropeIndex = token.lastIndexOf("'");
		if (apostropeIndex == -1) {
			apostropeIndex = token.lastIndexOf("`");
		}
		int beforeApostropeIndex = apostropeIndex - 1;
		if (beforeApostropeIndex >= 0) {
			char beforeApostropheeChar = token.charAt(beforeApostropeIndex);
			switch (beforeApostropheeChar) {
			case 'ג':
			case 'ד':
			case 'ז':
			case 'צ':
			case 'ץ':
			case 'ת':
			case 'ע':
				int afterApostropeeIndex = apostropeIndex + 1;
				if (afterApostropeeIndex < tokenLen) {
					char afterApostropheeChar = token.charAt(afterApostropeeIndex);
					if (!(afterApostropheeChar >= 'א' && afterApostropheeChar <= 'ת')) {
						String subTokenWithApostropee = token.substring(0, apostropeIndex + 1);
						xmlTokenizer.createTokens(subTokenWithApostropee);
						returnValue = token.substring(apostropeIndex + 1);
					}
				} else if (apostropeIndex == tokenLen - 1) {
					xmlTokenizer.createTokens(token);
					returnValue = null;
				}

				break;
			}

		}

		return returnValue;
	}

	/**
	 * Building the xml entity of end of sentence
	 *
	 * @param token
	 */
	public void createSuffixTokens(String token) {
		int tokenLen = token.length();
		int tokenIndex = 0;
		char currentChar = 0;
		while (tokenIndex < tokenLen) {
			currentChar = token.charAt(tokenIndex);
			xmlTokenizer.createTokens(String.valueOf(currentChar));
			tokenIndex++;
		}
		if (currentChar == '.' || currentChar == '!' || currentChar == '?') {
			xmlTokenizer.createSentences();
		}
	}

	/**
	 * dom4j is used for creating the xml document. this function handles creating
	 * the new xml document
	 *
	 * @throws IOException
	 */
	private void createTokenizedXML() throws IOException {
		xmlTokenizer = new XMLProcessor();
		xmlTokenizer.createDocument();
		xmlTokenizer.createArticle();
	}

	/**
	 * Handling empty lines appear at the middle of the file. Empty lines separates
	 * between paragraphes. This is the way ynet articles separates paragraphes.
	 * After the last empty line and the first text line - we close the last
	 * paragraph and start a new paragraph and a new sentence. In case there are no
	 * more text lines - the new sentence tag will be removed.
	 *
	 * @return
	 * @throws IOException
	 */
	private boolean dropMiddleFileEmptyLines() {
		if (line.length() == 0) {
			try {
				line = bi.readLine();
			} catch (IOException e) {
				line = null;
				System.out.println("XMLTokenizer:dropMiddleFileEmptyLines - Exception in readLine line= " + line);
				e.printStackTrace();
			}
			emptyLineFlag = true;
			return emptyLineFlag;
		}

		if (emptyLineFlag) {
			if (lineCounter > 0) {
				xmlTokenizer.createParapraphes();
				xmlTokenizer.createSentences();
			}
			emptyLineFlag = false;
		}
		return emptyLineFlag;
	}

	/**
	 * drops empty lines at the begining of the file. We allow maximum
	 * MAX_EMPTY_LINES empty lines at the begining of the file until text appears.
	 * If there are more than MAX_EMPTY_LINES we will assume that the file is empty.
	 * BOM is also handled, if there are empty lines after the BOM they are also
	 * removed until text is reached
	 *
	 * @return first line whic is not empty
	 */
	private void dropStartFileEmptyLines() {
		int emptyLinesCounter = 0;
		try {
			line = bi.readLine();

			while ((line == null || line.length() == 0) && emptyLinesCounter < MAX_EMPTY_LINES) {
				line = bi.readLine();
				emptyLinesCounter++;
			}
			if (emptyLinesCounter == MAX_EMPTY_LINES) {
				System.err.println(
						"No input to process or there are more than MAX_EMPTY_LINES at the begining of the file, Exiting");
				return;
			}

			// BOM handling - skip BOM and continue processing
			if (line.charAt(0) == 0xFEFF) {
				line = line.substring(1);
			}

			// handling case of empty lines after BOM
			emptyLinesCounter = 0;
			while ((line == null || line.length() == 0) && emptyLinesCounter < MAX_EMPTY_LINES) {
				line = bi.readLine();
				emptyLinesCounter++;
			}

			if (emptyLinesCounter == MAX_EMPTY_LINES) {
				System.err.println("No input to process");
				System.exit(-1);
			}

		} catch (IOException e) {
			System.out.println("Tokenizer:dropEmptyLines - IOException occured while trying to read input file lines ");
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("Tokenizer:dropEmptyLines - Exception occured while trying to read input file lines");
			e.printStackTrace();
		}
		return;
	}

	/**
	 * This method recognize non alphabet character in the beginning of the token or
	 * sometimes there are cases where the token is just non alphabet characters
	 *
	 * @param token
	 * @param result
	 * @param index
	 * @return
	 */
	public int findPrefix(String token, String[] result, int index) {
		int tokenLen = token.length();
		int tokenIndex = 0;
		char currentChar;
		int resultLen = result.length;

		while (tokenIndex < tokenLen) {
			currentChar = token.charAt(tokenIndex);
			if ((currentChar < 'א' || currentChar > 'ת') && !Character.isDigit(currentChar)
					&& !Character.isLetter(currentChar)) {
				xmlTokenizer.createTokens(String.valueOf(currentChar));
				if (currentChar == '.' && token.indexOf("...") == -1 || currentChar == '!' || currentChar == '?') {
					// checking cases in which we will not create an end of
					// sentence though the current
					// character is . or ! or ?
					if (!(index + 1 < resultLen && index + 2 < resultLen && result[index].equals("?")
							&& result[index + 2].equals("."))

							&& !(index + 1 < resultLen && index + 2 < resultLen && result[index].equals("!")
									&& result[index + 2].equals("."))

							&& !(index + 1 < resultLen && result[index].equals("?") && result[index + 1].equals(")"))

							&& !(index + 1 < resultLen && result[index].equals("!") && result[index + 1].equals(")"))

							&& !(index + 1 < resultLen && result[index].equals("!") && result[index + 1].equals("\""))

							&& !(index + 1 < resultLen && result[index].equals("?") && result[index + 1].equals("\""))) {
						xmlTokenizer.createSentences();
					}
				}
			} else {
				break;
			}
			tokenIndex++;
		}

		return tokenIndex;
	}

	/**
	 * This method recognize non alphabet character in the end of the token or
	 * sometimes there are cases where the token is just non alphabet characters
	 *
	 * @param token
	 * @return
	 */
	public int findSuffix(String token) {
		int tokenLen = token.length();
		int tokenIndex = tokenLen - 1;
		char currentChar;
		while (tokenIndex >= 0) {
			currentChar = token.charAt(tokenIndex);
			if ((currentChar < 'א' || currentChar > 'ת') && !Character.isDigit(currentChar)
					&& !Character.isLetter(currentChar)) {

				tokenIndex--;
			} else {
				break;
			}
		}
		tokenIndex++;
		return tokenLen - tokenIndex;
	}

	/**
	 * @param token
	 * @return
	 */
	public String handleAfterPrefixSeparator(String token) {
		int tokenLen = token.length();

		String returnValue = token;
		token.length();
		char afterSeparatorChar = token.charAt(0);

		// digits/date/hour
		if (Character.isDigit(afterSeparatorChar)) {
			// hour
			if (token.indexOf(':') != -1) {
				returnValue = handleHour(token);
			} else if (token.indexOf('/') != -1) {
				returnValue = handleDate(token);
			} else if (token.indexOf(':') == -1 && token.indexOf('/') == -1) {
				returnValue = handleNumber(token);
			}
		}

		// handle simple hebrew token

		else if (afterSeparatorChar >= 'א' && afterSeparatorChar <= 'ת') {
			int i = 0;
			char currentChar;
			while (i < tokenLen) {
				currentChar = token.charAt(i);
				if (Character.isDigit(currentChar)) {
					break;
				}
				i++;
			}
			// ב1,000,000
			String possiblePrefix = token.substring(0, i);
			if (StringUtils.moshevkaleb(possiblePrefix)) {
				xmlTokenizer.createTokens(String.valueOf(possiblePrefix));
				token = token.substring(i);
				if (token.length() > 0) {
					returnValue = handleNumber(token);
				} else {
					returnValue = null;
				}
			} else {
				returnValue = handleSimpleHebrewToken(token);
			}

		} else if (afterSeparatorChar >= 'a' && afterSeparatorChar <= 'z'
				|| afterSeparatorChar >= 'A' && afterSeparatorChar <= 'Z') {
			returnValue = handleSimpleForeignToken(token);
		}

		return returnValue;
	}

	/**
	 * @param token
	 * @return
	 */
	public String handleDate(String token) {
		String returnValue = token;

		return returnValue;
	}

	/**
	 * @param token
	 * @return
	 */
	public String handleHour(String token) {
		String returnValue = token;

		return returnValue;
	}

	/**
	 * This method handles numbers they can be like 345 or 45.8 or 1,000 or rage
	 * 20-30 or 5% or 5$
	 *
	 * @param token
	 * @return
	 */
	public String handleNumber(String token) {
		int index;
		int tokenLen = token.length();
		String returnValue = token;
		int i = 0;
		char currentChar;
		String number = "";
		if (token.matches("[0-9]+\\.") && tokenLen < 3) {
			xmlTokenizer.createTokens(token);
			return null;

		} else if (token.matches("[0-9]*\\.\\.\\.[0-9]+\\.\\.\\.")) {
			String[] result = token.split("(?=[.])|(?<=[.])");
			int resultLen = result.length;
			for (i = 0; i < resultLen; i++) {
				xmlTokenizer.createTokens(result[i]);
			}
			returnValue = null;

			// date ה25/11
		} else if (token.indexOf("/") != -1) {
			if (token.matches("[0-9]{2}/[0-9]{2}")) {
				xmlTokenizer.createTokens(token);
				return null;
			}
		} else if (token.indexOf("\\") != -1) {
			i = 0;
			index = 0;
			while (i < tokenLen) {
				currentChar = token.charAt(i);
				if (Character.isDigit(currentChar)) {
					i++;
				} else if (currentChar == '.' || currentChar == ',') {
					if (i + 1 < tokenLen && Character.isDigit(token.charAt(i + 1))) {
						i++;
					} else {
						break;
					}
				} else if (currentChar == '\\') {
					xmlTokenizer.createTokens(token.substring(0, i));
					xmlTokenizer.createTokens("\\");
					index = i;
					i++;
				} else {
					break;
				}

			}
			xmlTokenizer.createTokens(token.substring(index + 1, i));
			token = token.substring(i);

		} else if (token.indexOf("$") != -1 || token.indexOf("%") != -1) {
			String[] result = token.split("(?=[%$-])|(?<=[%$-])");
			int resultLen = result.length;
			// ///////////////////////////////////////////////
			// check if last token contains suffix at it's end
			// ///////////////////////////////////////////////
			String lastToken = result[resultLen - 1];
			int lastTokenLen = lastToken.length();
			int j = lastTokenLen - 1;
			while (j > 0) {
				currentChar = lastToken.charAt(j);
				if (!Character.isDigit(currentChar) && currentChar != '%' && currentChar != '$') {
					j--;
				} else {
					break;
				}
			}
			String lastTokenSuffix = lastToken.substring(0, lastTokenLen - j);
			List<String> list = null;
			String[] subResult = new String[result.length - 1];

			// tokens with $ or % are displayed reversed without special
			// treatment
			if (lastTokenSuffix.length() == 0 || lastTokenSuffix.length() == 1 && lastTokenSuffix.charAt(0) == '%'
					|| lastTokenSuffix.charAt(0) == '$') {
				list = Arrays.asList(result);
			} else {
				System.arraycopy(result, 0, subResult, 0, resultLen - 1);
				list = Arrays.asList(subResult);
			}
			Collections.reverse(list);
			String[] reversedResult = (String[]) list.toArray();
			int reversedResultLen = reversedResult.length;
			for (j = 0; j < reversedResultLen; j++) {
				xmlTokenizer.createTokens(reversedResult[j]);
			}
			if (lastTokenSuffix.length() > 0 && !(lastTokenSuffix.length() == 1 && lastTokenSuffix.charAt(0) == '%'
					|| lastTokenSuffix.charAt(0) == '$')) {
				token = result[resultLen - 1];
			} else {
				return null;
			}
		} else if ((index = token.indexOf(".")) == -1
				|| index + 1 < tokenLen && Character.isDigit(token.charAt(index + 1)) || token.indexOf("*") != -1
				|| token.indexOf("=") != -1 || token.indexOf("(") != -1 || token.indexOf(")") != -1
				|| token.indexOf("-") != -1 || token.indexOf("&") != -1 || token.indexOf("+") != -1) {
			String[] result = token.split("(?=[*&()\\+~\\=-])|(?<=[*&()\\+~\\=-])");
			int resultLen = result.length;
			for (int j = 0; j < resultLen - 1; j++) {
				xmlTokenizer.createTokens(result[j]);
			}
			String lastToken = result[resultLen - 1];
			int lastTokenLen = lastToken.length();
			int j = lastTokenLen - 1;
			while (j > 0) {
				currentChar = lastToken.charAt(j);
				if (!Character.isDigit(currentChar) && currentChar != '-') {
					j--;
				} else {
					break;
				}
			}
			// תלוי אם רץ מימין לשמאל או שמאל לימין הקומבינציה של מספרים ותוים
			// אחרים משפיעה
			if (j + 1 == lastTokenLen) {
				token = lastToken;
			} else if (Character.isDigit(lastToken.charAt(0))) {
				token = lastToken.substring(0, j + 2);
			} else {
				token = lastToken.substring(0, lastTokenLen - j);
			}

		}
		// identify number 1,000,000 1.25 200
		// handle the last token which may be a combination of a number and
		// non digits characters
		i = 0;
		tokenLen = token.length();
		while (i < tokenLen) {
			currentChar = token.charAt(i);
			if (Character.isDigit(currentChar)) {
				i++;
			} else if (currentChar == '.' || currentChar == ',') {
				if (i + 1 < tokenLen && Character.isDigit(token.charAt(i + 1))) {
					i++;
				} else if (i + 1 < tokenLen && token.charAt(i + 1) >= 'א' && token.charAt(i + 1) <= 'ת') {
					if (i > 0) {
						xmlTokenizer.createTokens(token.substring(0, i));
					}
					xmlTokenizer.createTokens(String.valueOf(currentChar));
					returnValue = token.substring(i + 1);
					if (returnValue.length() == 0) {
						returnValue = null;
					}
					return returnValue;
				} else {
					break;
				}
			} else {
				break;
			}
		}

		number = token.substring(0, i);
		if (number.length() > 0) {
			xmlTokenizer.createTokens(number);
		}

		String afterNumberToken = token.substring(i);

		returnValue = afterNumberToken;
		if (returnValue.length() == 0) {
			returnValue = null;
		}

		return returnValue;
	}

	/**
	 * this method handle cases like b-10 for which we know for sure that b is a
	 * standalone prefix
	 *
	 * @param token
	 * @param c
	 * @param index
	 * @return
	 */
	public String handlePrefix(String token, char c, int index) {
		String returnValue = token;
		String possiblePrefix = token.substring(0, index);
		if (StringUtils.moshevkaleb(possiblePrefix)) {
			xmlTokenizer.createTokens("prefix=" + possiblePrefix);
			xmlTokenizer.createTokens(String.valueOf(c));
			returnValue = token.substring(index + 1);
		}
		return returnValue;
	}

	/**
	 * This method handles cases like prefix + " + some quote or prefix+"+quote or
	 * prefix+-+quote In these cases we know for sure that there is a standalone
	 * prefix
	 *
	 * @param token
	 * @return
	 */
	public String handlePrefixExists(String token) {

		String returnValue = token;
		String possiblePrefix = "";
		int tokenLen = token.length();
		int i = 0;

		// find a possible prefix
		if (!(token.charAt(0) >= 'א' && token.charAt(0) <= 'ת')) {
			returnValue = handleAfterPrefixSeparator(token);
		} else if (token.indexOf("'") != -1 || token.indexOf("-") != -1 || token.indexOf("\"") != -1) {
			while (i < tokenLen && token.charAt(i) >= 'א' && token.charAt(i) <= 'ת') {
				i++;
			}

			possiblePrefix = token.substring(0, i);

			char afterPrefixChar = token.charAt(i);

			String afterAfterPrefixString = token.substring(i + 1);

			// לכ"כ
			int afterAfterPrefixStringLen = afterAfterPrefixString.length();
			if (StringUtils.moshevkaleb(possiblePrefix)
					&& (afterPrefixChar == '-' || afterPrefixChar == '\'' || afterPrefixChar == '"')
					&& afterAfterPrefixStringLen > 1) {
				xmlTokenizer.createTokens("prefix=" + possiblePrefix);
				xmlTokenizer.createTokens(String.valueOf(afterPrefixChar));

				if (i < tokenLen - 1) {
					i++;
					String afterPrefixTokenAndSeparator = token.substring(i);
					// ב-צ.ה.ל.
					returnValue = isDotAcronymWithDotEnding(afterPrefixTokenAndSeparator);
					if (returnValue != null && returnValue.equals(afterPrefixTokenAndSeparator)) {
						returnValue = handleAfterPrefixSeparator(afterPrefixTokenAndSeparator);
					}

				}
			} else {
				returnValue = handleSimpleHebrewToken(token);
			}

		} else {

			returnValue = handleAfterPrefixSeparator(token);

		}

		return returnValue;
	}

	/**
	 * @param token
	 * @return
	 */
	public String handleSimpleForeignToken(String token) {
		String returnValue = token;
		int tokenLen = token.length();
		if (token.charAt(tokenLen - 1) >= 'A' && token.charAt(tokenLen - 1) <= 'Z'
				|| token.charAt(tokenLen - 1) >= 'a' && token.charAt(tokenLen - 1) <= 'z') {
			xmlTokenizer.createTokens(token);
			returnValue = null;
			return returnValue;
		} else {
			int i = tokenLen - 1;
			char currentChar;
			while (i > 0) {
				currentChar = token.charAt(i);
				if (!(currentChar >= 'A' && currentChar <= 'Z') && !(currentChar >= 'a' && currentChar <= 'z')) {
					i--;
				} else {
					break;
				}

			}
			String foreign = token.substring(0, i + 1);
			xmlTokenizer.createTokens(foreign);
			returnValue = handleSimpleHebrewToken(token.substring(i + 1));
		}

		return returnValue;
	}

	/**
	 * @param token
	 * @return
	 */
	public String handleSimpleHebrewToken(String token) {
		token = removeTnoaot(token);
		String returnValue = token;
		int i = 0;
		char currentChar = 0;
		int tokenLen = token.length();

		// gimatria
		if (tokenLen == 2 && token.charAt(1) == '\'') {
			xmlTokenizer.createTokens(token);
			returnValue = null;
		}
		// simpleToken
		else if (tokenLen > 2) {
			// Check if contains number

			// //////////////////
			// אף-על-פי-כן
			if (token.indexOf('-') != -1 || token.indexOf('(') != -1 || token.indexOf('!') != -1
					|| token.indexOf('/') != -1 || token.indexOf(')') != -1 || token.indexOf('[') != -1
					|| token.indexOf(']') != -1 || token.indexOf(',') != -1 && token.indexOf(',') != tokenLen - 1) {
				returnValue = splitToken(token);
			} else if (token.indexOf("'") != -1 || token.indexOf("`") != -1) {
				returnValue = apostropeeNotGimatriaHandling(token);
			} else {
				boolean found = false;
				while (!found && i < tokenLen) {
					currentChar = token.charAt(i);
					if (currentChar >= 'א' && currentChar <= 'ת') {
						i++;
					} else if ((currentChar == '"' || currentChar == '\\') && i + 1 < tokenLen && token.charAt(i + 1) >= 'א'
							&& token.charAt(i + 1) <= 'ת') {
						i++;
					} else {
						found = true;
					}

				}

				String hebrewToken = "";
				String possibleSuffix = "";

				if (i <= tokenLen) {
					hebrewToken = token.substring(0, i);
					possibleSuffix = token.substring(i);
				}
				int hebrewTokenLength = hebrewToken.length();
				if (hebrewTokenLength > 0) {
					xmlTokenizer.createTokens(hebrewToken);
				}
				if (possibleSuffix.length() > 0) {
					returnValue = possibleSuffix;
				} else {
					returnValue = null;
				}
			}

		}
		return returnValue;
	}

	/**
	 * @param token
	 * @return
	 */
	public String handleToken(String token) {
		// System.out.println(token);
		String returnValue = null;
		returnValue = isDotAcronymWithDotEnding(token);
		if (returnValue == null || !returnValue.equals(token)) {
			return returnValue;
		}
		returnValue = handlePrefixExists(token);

		return returnValue;
	}

	/**
	 * Input file handling
	 *
	 * @param inputFile
	 */
	private void ioFileHandling(String inputFile) {
		if (!inputFile.equals("")) {
			try {
				bi = new BufferedReader(new InputStreamReader(new FileInputStream(inputFile), "UTF8"));
			} catch (UnsupportedEncodingException e) {
				System.err.println("XMLTokenizer:ioFileHandling UnsupportedEncodingException happened");
				e.printStackTrace();
			} catch (FileNotFoundException e) {
				System.err.println("XMLTokenizer:ioFileHandling File not find - please check "
						+ "input/output file parameter. exiting...");
				System.exit(-1);
				e.printStackTrace();
			}
		} else {
			try {
				// Getting inputFile path+name from stdin
				bi = new BufferedReader(new InputStreamReader(System.in, "UTF8"));
			} catch (UnsupportedEncodingException e) {
				System.out.println("XMLTokenizer:ioFileHandling UnsupportedEncodingException happened");
				e.printStackTrace();
			}
		}
	}

	/**
	 * identify ת.ז. אי.די.בי.
	 *
	 * @param token
	 * @return
	 */
	public String isDotAcronymWithDotEnding(String token) {
		String returnValue = token;
		if (token.indexOf(".") != -1) {
			int tokenLen = token.length();
			if (tokenLen == 2 && token.charAt(0) >= 'א' && token.charAt(0) <= 'ת') {
				xmlTokenizer.createTokens(token);
				returnValue = null;
				return returnValue;
			}
			if (token.charAt(tokenLen - 1) == '.' && token.matches("[משהוכלב]" + ALEFBAIT + "\\." + ALEFBAIT + "\\.")) {
				xmlTokenizer.createTokens(token);
				returnValue = null;
			} else {
				if (token.charAt(tokenLen - 1) == '.' && token.matches(ALEFBAIT + "\\." + ALEFBAIT + "\\.")
						|| token.matches(ALEFBAIT + "+\\." + ALEFBAIT + "+\\." + ALEFBAIT + "+\\.")
						|| tokenLen == 6 && token.matches(ALEFBAIT + "{2}" + "\\." + ALEFBAIT + "{2}" + "\\.")
						|| tokenLen == 3 && token.matches(ALEFBAIT + "{2}" + "\\.")
						// בי.ג'יי. ארמסטרונג
						|| tokenLen == 8 && token.matches(ALEFBAIT + "{2}" + "\\." + ALEFBAIT + "\\'" + ALEFBAIT + "{2}\\.")
						|| tokenLen == 5 && token.matches(ALEFBAIT + "{2}" + "\\." + ALEFBAIT + "{2}") || tokenLen == 8
								&& token.matches(ALEFBAIT + "{2}" + "\\." + ALEFBAIT + "{2}" + "\\." + ALEFBAIT + "{2}\\.")) {
					xmlTokenizer.createTokens(token);
					returnValue = null;
				} else {
					int i = tokenLen;
					String subToken = "";
					char endTokenChar = 0;
					while (i > 0) {
						i--;
						endTokenChar = token.charAt(i);
						if (!(endTokenChar >= 'א' && endTokenChar <= 'ת') && endTokenChar != '.') {
							subToken = token.substring(0, i);
							if (subToken.matches(ALEFBAIT + "\\." + ALEFBAIT + "\\.")
									|| subToken.matches(ALEFBAIT + "+\\." + ALEFBAIT + "+\\." + ALEFBAIT + "+\\.")) {
								xmlTokenizer.createTokens(subToken);
								returnValue = token.substring(i);
								break;
							} else {
								returnValue = isDotAcronymWithoutDotEnding(token);
								break;
							}
						} else {
							returnValue = isDotAcronymWithoutDotEnding(token);
							break;
						}

					}
				}
			}
		}
		return returnValue;
	}

	/**
	 * identify ת.ז אי.די.בי
	 *
	 * @param token
	 * @return
	 */
	public String isDotAcronymWithoutDotEnding(String token) {
		String returnValue = token;
		if (token.indexOf(".") != -1) {
			int tokenLen = token.length();
			int i = tokenLen;
			if (token.matches(ALEFBAIT + "\\." + ALEFBAIT + "+")
					|| token.matches(ALEFBAIT + "+\\." + ALEFBAIT + "+\\." + ALEFBAIT + "+")) {
				xmlTokenizer.createTokens(token);
				returnValue = null;

			} else {

				String subToken = "";
				char endTokenChar = 0;
				while (i > 0) {
					i--;
					endTokenChar = token.charAt(i);
					if (!(endTokenChar >= 'א' && endTokenChar <= 'ת')) {
						subToken = token.substring(0, i);
						if (subToken.matches(ALEFBAIT + "\\." + ALEFBAIT)
								|| subToken.matches(ALEFBAIT + "+\\." + ALEFBAIT + "+\\." + ALEFBAIT + "+")) {
							xmlTokenizer.createTokens(subToken);
							returnValue = token.substring(i);
							break;
						}
					}
				}
			}

		}
		return returnValue;

	}

	/**
	 * @param token
	 * @return
	 */
	public boolean isGimatriaApostropeeHandling(String token) {
		boolean returnValue = false;
		int tokenLen = token.length();
		int index = token.lastIndexOf("'");
		if (tokenLen == 2 && index == tokenLen - 1) {
			returnValue = true;
		}
		return returnValue;
	}

	/**
	 * We must recognize known acronym ends with ' because our default is to
	 * separate the ' from the token In this specific case we need to leave it as a
	 * token
	 *
	 * @param token
	 * @return
	 */
	private boolean kitzurExists(String token) {
		int tokenLen = token.length();
		boolean returnValue = false;
		int indexKitzur = 0;
		for (String kitzur : kitzurim) {
			if ((indexKitzur = token.indexOf(kitzur)) != -1) {
				break;
			}
		}
		if (indexKitzur == -1) {
			return false;
		}
		if (indexKitzur == 0) { // is it at the begining of the token ?
			if (token.endsWith("'") // if ends with ' or in range of letters
					|| token.charAt(tokenLen - 1) >= 'א' && token.charAt(tokenLen - 1) <= 'ת') {
				xmlTokenizer.createTokens(token);
				returnValue = true;
			} else {
				char currentChar;
				int i = tokenLen - 1;

				while (i > 0) {
					currentChar = token.charAt(i);
					if (!(currentChar >= 'א' && currentChar <= 'ת') && currentChar != '\'' && token.charAt(i - 1) == '\'') {
						xmlTokenizer.createTokens(token.substring(0, i));
						break;
					} else {
						i--;
					}
				}
				String suffix = token.substring(i);
				if (suffix.length() > 0) {
					createSuffixTokens(suffix);
				}
				returnValue = true;
			}
		} else {// not at the beginning of the token
			String checkedPrefix = token.substring(0, indexKitzur);
			if (StringUtils.moshevkaleb(checkedPrefix)) {
				xmlTokenizer.createTokens("prefix=" + checkedPrefix + " token=" + token.substring(indexKitzur));
				returnValue = true;
			}
		}
		return returnValue;
	}

	/**
	 * Handle the case when both input and output are directories Output directory
	 * can be an already existing diectory otherwise the program will create a new
	 * directory with the providede name
	 *
	 * @param inputDirectory
	 *           containing UTF-8 text files
	 * @param outputDirectory
	 *           an existing or not existing directory which will contain the output
	 *           XML files
	 */
	public void processDirectory(String inputDirectory, String outputDirectory) {
		try {
			File in = new File(inputDirectory);
			int pos = in.isDirectory() ? in.getAbsolutePath().length() : in.getParent().length();
			analyzeDirectory(in, outputDirectory, pos);
		} catch (Exception e) {
			System.err.println("Please provide full path to input file/directory :" + inputDirectory + " exiting..");
			System.exit(-1);
		}
	}

	/**
	 * This method is used when handling input directory
	 *
	 * @param inputFile
	 *           UTF-8 text file
	 * @param outputFile
	 *           XML output file
	 */
	public void processFile(final String inputFile, final String outputFile) {
		int index = outputFile.lastIndexOf(".");
		String newOutputFile = outputFile.substring(0, index);
		newOutputFile = newOutputFile + ".xml";
		ioFileHandling(inputFile);
		try {
			analyzeTokens(newOutputFile);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * This method handles the case in which the input parameter is a single file
	 * and not a directory
	 *
	 * @param inputFile
	 *           UTF-8 text file
	 * @param outputFile
	 *           XML output file
	 */
	public void processSingleFile(final String inputFile, final String outputFile) {
		if (outputFile.indexOf("xml") == -1) {
			System.err.println("output file must have an xml suffix");
		}
		ioFileHandling(inputFile);
		try {
			analyzeTokens(outputFile);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String removeTnoaot(String token) {
		int i = 0;
		char currentChar = 0;

		int tokenLen = token.length();
		boolean found = false;

		while (!found && i < tokenLen) {
			currentChar = token.charAt(i);
			if (currentChar >= 'א' && currentChar <= 'ת') {
				i++;
			} else if ((currentChar == '"' || currentChar == '\\' || currentChar == '-' || currentChar == '\'')
					&& i + 1 < tokenLen && token.charAt(i + 1) >= 'א' && token.charAt(i + 1) <= 'ת') {
				i++;
			} else if (currentChar == 'ּ' || currentChar == 'ֹ' || currentChar == 'ָ' || currentChar == 'ִ'
					|| currentChar == 'ֶ' || currentChar == 'ֵ' || currentChar == 'ַ' || currentChar == 'ּ'
					|| currentChar == 'ׂ' || currentChar == 'ֳ' || currentChar == 'ֲ' || currentChar == 'ׁ'
					|| currentChar == 'ְ' || currentChar == 'ֱ' || currentChar == 'ֻ') {
				token = token.toString().substring(0, i) + token.substring(i + 1);
				tokenLen = token.length();
			} else {
				found = true;
			}

		}

		return token;
	}

	/**
	 * This method split the token by -)([]{}!,
	 *
	 * @param token
	 * @return
	 */
	public String splitToken(String token) {
		String returnValue = "";
		int i = 0;
		String[] result = token.trim().split("(?=[-)(\\[\\]{},!/])|(?<=[-)(\\[\\]{},!/])");
		int resultLen = result.length;
		for (i = 0; i < resultLen - 1; i++) {
			String subToken = result[i];
			int subTokenLen = subToken.length();
			// avoid empty strings
			if (subTokenLen > 1) {
				if (subToken.charAt(subTokenLen - 1) == '"') {
					xmlTokenizer.createTokens(subToken.substring(0, subTokenLen - 1));
					xmlTokenizer.createTokens("\"");
				} else if (subToken.matches("\\.{2,3}")) {
					String[] resultPoint = subToken.trim().split("(?=[\\.])|(?<=[\\.])");
					int resultPointLen = resultPoint.length;
					for (int j = 0; j < resultPointLen; j++) {
						String pointToken = resultPoint[j];
						if (pointToken.length() > 0) {
							xmlTokenizer.createTokens(pointToken);
						}
					}

				} else {
					xmlTokenizer.createTokens(subToken);
				}
			} else if (subTokenLen == 1) {
				xmlTokenizer.createTokens(subToken);
			}
		}
		if (result[i].matches(ALEFBAIT + "+")) {
			xmlTokenizer.createTokens(result[i]);
			returnValue = null;
		} else {
			returnValue = result[i];
		}

		return returnValue;
	}

	// --------------------------------------------------------------------------------------------------------------------------
	/**
	 * ADDED 21.11.10 by yossi jacob. This method ensures that the output String has
	 * only valid XML unicode characters as specified by the XML 1.0 standard. For
	 * reference, please see
	 * <a href="http://www.w3.org/TR/2000/REC-xml-20001006#NT-Char">the
	 * standard</a>. This method will return an empty String if the input is null or
	 * empty.
	 *
	 * @param in
	 *           The String whose non-valid characters we want to remove.
	 * @return The in String, stripped of non-valid characters.
	 */
	public String stripNonValidXMLCharacters(String in) {
		StringBuffer out = new StringBuffer(); // Used to hold the output.
		char current; // Used to reference the current character.

		if (in == null || "".equals(in)) {
			return ""; // vacancy test.
		}
		for (int i = 0; i < in.length(); i++) {
			current = in.charAt(i); // NOTE: No IndexOutOfBoundsException caught
			// here; it should not happen.
			if (current == 0x9 || current == 0xA || current == 0xD || current >= 0x20 && current <= 0xD7FF
					|| current >= 0xE000 && current <= 0xFFFD || current >= 0x10000 && current <= 0x10FFFF) {
				out.append(current);
			}
		}
		return out.toString();
	}

	// //////////////////////////////////////////////////////////////////////////////////////////////
	// API's
	// //////////////////////////////////////////////////////////////////////////////////////////////
	/**
	 * Tokenize the input which is an inputStream (used in our web)
	 *
	 * @param in
	 * @return
	 */
	public String tokenize(InputStream in) {
		Document document = null;
		try {
			bi = new BufferedReader(new InputStreamReader(in, "UTF8"));
			webProcess();
			xmlTokenizer.finalizeDoc();
			document = xmlTokenizer.getDocument();

		} catch (Exception e) {
			System.out.println(
					"XMLTokenizer:tokenizeAndAnalyze -web interface - Exception in BufferedReader or process function");
			e.printStackTrace();
		}

		String documentStr = document.asXML();
		return documentStr;
	}

	/**
	 * used for XMLHMMTagger and webView web applications
	 *
	 * @param in
	 * @param pw
	 */

	public void tokenizeAndAnalyze(InputStream in, PrintWriter pw) {
		System.out.println("(F) XMLTokenizer: tokenizeAndAnalyze()");
		webFlag = true;
		Document document = null;
		try {
			bi = new BufferedReader(new InputStreamReader(in, "UTF8"));
			webProcess();
			xmlTokenizer.finalizeDoc();
			document = xmlTokenizer.getDocument();

		} catch (Exception e) {
			System.out.println(
					"XMLTokenizer:tokenizeAndAnalyze -web interface - Exception in BufferedReader or process function");
			e.printStackTrace();
		}

		String documentStr = document.asXML();
		XMLMorphAnalyzer xmlMorphAnalyzer = new XMLMorphAnalyzer();
		xmlMorphAnalyzer.morphologicalAnalyzer(pw, documentStr);
	}

	/**
	 * For Users who uses data files
	 *
	 * @param in
	 * @param pw
	 * @param dinflectionsFile
	 * @param dprefixesFile
	 * @param gimatriaFile
	 */
	public void tokenizeAndAnalyze(InputStream in, PrintWriter pw, String dinflectionsFile, String dprefixesFile,
			String gimatriaFile) {
		// System.out.println("Starting XMLTokenizer");
		Document document = null;
		try {
			bi = new BufferedReader(new InputStreamReader(in, "UTF8"));
			webProcess();
			xmlTokenizer.finalizeDoc();
			document = xmlTokenizer.getDocument();

		} catch (Exception e) {
			System.out.println(
					"XMLTokenizer:tokenizeAndAnalyze -web interface - Exception in BufferedReader or process function");
			e.printStackTrace();
		}
		String documentStr = document.asXML();
		XMLMorphAnalyzer xmlMorphAnalyzer = new XMLMorphAnalyzer();
		xmlMorphAnalyzer.morphologicalAnalyzer(documentStr, pw, dinflectionsFile, dprefixesFile, gimatriaFile);
	}

	/**
	 * This method is used in the Client Server architecture This method get a UTF-8
	 * Hebrew text as inputStream, the text is tokenized, and it also activate the
	 * morphological analyzer.
	 *
	 * @param in
	 * @param pw
	 * @param xmlMorphAnalyzer
	 *           - an instance of the morphological analyzer - so that the data can
	 *           be loaded only once
	 * @return
	 */
	public int tokenizeAndAnalyze(InputStream in, PrintWriter pw, XMLMorphAnalyzer xmlMorphAnalyzer) {
		// System.out.println("Starting XMLTokenizer");
		Document document = null;
		try {
			bi = new BufferedReader(new InputStreamReader(in, "UTF8"));
			webProcess();
			xmlTokenizer.finalizeDoc();
			document = xmlTokenizer.getDocument();

		} catch (Exception e) {
			System.out.println(
					"XMLTokenizer:tokenizeAndAnalyze -web interface - Exception in BufferedReader or process function");
			e.printStackTrace();
		}
		String documentStr = document.asXML();
		int globalTokensNumber = xmlTokenizer.getGlobalTokenCounter();

		xmlMorphAnalyzer.morphologicalAnalyzerNoDataLoad(pw, documentStr);
		return globalTokensNumber;
	}

	/**
	 * This method tokenize and analyze an input String of Hebrew text
	 *
	 * @param inputText
	 *           input String of hebrew text
	 * @param dinflectionsFile
	 * @param dprefixesFile
	 * @param gimartiasFile
	 * @return String of the XML analysis of the input text
	 */
	public String tokenizeAndAnalyzeSingleToken(String inputText, String dinflectionsFile, String dprefixesFile,
			String gimartiasFile) {
		InputStream inputStraem = null;
		try {
			inputStraem = new ByteArrayInputStream(inputText.getBytes("UTF8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		tokenizeAndAnalyze(inputStraem, pw, dinflectionsFile, dprefixesFile, gimartiasFile);
		return sw.toString();
	}

	/**
	 * Control function which works in our web applications
	 *
	 * @return
	 * @throws Exception
	 */
	public String webProcess() throws Exception {
		// System.out.println("(F) XMLTokenizer: webProcess()");
		String token = "";
		String returnToken = "";
		String rt = "";
		// handling empty lines at the begining of the file
		dropStartFileEmptyLines();
		try {
			// creating infrastructure for creating xml document
			createTokenizedXML();
		} catch (IOException e) {
			System.out.println("XMLTokenizer:process - Exception during createTokenizedXML");
			e.printStackTrace();
		}
		// create the first paragraph
		xmlTokenizer.createParapraphes();
		// create the first sentence
		xmlTokenizer.createSentences();
		// analyzing the input file line by line
		while (line != null) {
			// System.out.println("line=" + line);
			// Translate non-breaking spaces to regular spaces.
			line = line.replace((char) 160, ' ');
			line = line.trim();
			line = stripNonValidXMLCharacters(line); // remove invalid XML
			// characters
			line = line.replace('–', '-'); // replace width dash with small dash
			// handling the case of empty lines at the middle of the file - they
			// signify a
			// begining of a new paragraph
			if (dropMiddleFileEmptyLines()) {
				continue;
			}

			// analyzing the line tokens, tokens are separated from each other
			// by white spaces
			StringTokenizer st = new StringTokenizer(line);

			int tokenCount = st.countTokens();
			String[] result = new String[tokenCount];
			int index = 0;
			int i;
			while (st.hasMoreTokens()) {
				token = st.nextToken();
				result[index] = token;
				index++;
			}

			for (i = 0; i < tokenCount; i++) {
				token = result[i];
				returnToken = "";
				// System.out.println("(F) webProcess: token = " + token +
				// " TRANSLITERATED: " + Translate.Heb2Eng(token));

				if (token.indexOf("'") != -1 || token.indexOf("\"") != -1) {
					if (kitzurExists(token)) {
						continue;
					}
				}

				int prefixLen = findPrefix(token, result, i);
				String tokenWithoutPrefixWithSuffix = token.substring(prefixLen);
				// System.out.println("(F) webProcess:
				// tokenWithoutPrefixWithSuffix = "
				// + tokenWithoutPrefixWithSuffix);

				if (tokenWithoutPrefixWithSuffix.length() > 0) {
					returnToken = handleToken(tokenWithoutPrefixWithSuffix);
					// System.out.println("(F) webProcess: returnToken = " +
					// returnToken);
				}

				if (returnToken != null && returnToken.length() > 0) {
					tokenWithoutPrefixWithSuffix = returnToken;
					int suffixLen = findSuffix(tokenWithoutPrefixWithSuffix);

					int tokenWithoutPrefixWithSuffixLen = tokenWithoutPrefixWithSuffix.length();

					String tokenSuffix = tokenWithoutPrefixWithSuffix.substring(tokenWithoutPrefixWithSuffixLen - suffixLen);

					int suffixStartIndex = tokenWithoutPrefixWithSuffixLen - suffixLen;
					String tokenWithoutPrefixWithoutSuffix = tokenWithoutPrefixWithSuffix.substring(0, suffixStartIndex);
					if (tokenWithoutPrefixWithoutSuffix.length() > 0) {
						xmlTokenizer.createTokens(tokenWithoutPrefixWithoutSuffix);
					}
					createSuffixTokens(tokenSuffix);

					// if (handleToken(token))
					// continue;
					//
					// int i1 = analyzePrefixToken(token, len);
					// analyzeSuffixToken(token, len, i1);
				}
			}

			lineCounter++;
			line = bi.readLine();
			// if (webFlag && xmlTokenizer.getGlobalTokenCounter() > 100)
			// break;
		}
		// System.out.println("Total number of tokens is:" +
		// xmlTokenizer.getTokenCounter());
		rt = xmlTokenizer.printDoc();

		return rt;
	}
}
