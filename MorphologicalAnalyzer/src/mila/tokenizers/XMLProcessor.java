/*

 * Created on 18/07/2005

 *

 * TODO To change the template for this generated file go to

 * Window - Preferences - Java - Code Style - Code Templates

 */

package mila.tokenizers;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.util.Date;
import java.util.HashMap;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

/**
 *
 *
 *
 * XMLProcessor.java Purpose: build the XML of the output of the tokenizer using
 * j4dom
 *
 *
 *
 * @author Dalia Bojan
 *
 * @version %G%
 *
 */
public class XMLProcessor {

	/**
	 *
	 * @param heb
	 *
	 * @return
	 *
	 */
	public static String getHebToEng(String heb) {
		if (hebToEng == null) {
			initHebToEng();
		}
		return hebToEng.get(heb);
	}

	/**
	 *
	 * @param hebStr
	 *
	 * @return
	 *
	 */
	private static String translateHebEng(String hebStr) {
		final StringBuffer transliterated = new StringBuffer();
		String transliteratedStr = "";
		char curChar;
		char transChar;
		for (int i = 0; i < hebStr.length(); i++) {
			curChar = hebStr.charAt(i);
			if (curChar >= 'à' && curChar <= 'ú') {
				final String charStr = new Character(curChar).toString();
				transChar = getHebToEng(charStr).charAt(0);
				transliterated.append(transChar);
			} else {
				transliterated.append(curChar);
			}
		}
		transliteratedStr = transliterated.toString();
		return transliteratedStr;
	}

	Document document;
	int countSentence = 0;
	int countParagraph = 0;
	int countToken = 0;
	Element root;
	Element article;
	Element paragraph;
	Element sentence;
	Element token;
	int globalTokenCounter = 0;
	private static HashMap<String, String> hebToEng;

	private static void initHebToEng() {
		hebToEng = new HashMap<>();
		hebToEng.put("à", "a");
		hebToEng.put("á", "b");
		hebToEng.put("â", "g");
		hebToEng.put("ã", "d");
		hebToEng.put("ä", "h");
		hebToEng.put("å", "w");
		hebToEng.put("æ", "z");
		hebToEng.put("ç", "x");
		hebToEng.put("è", "v");
		hebToEng.put("é", "i");
		hebToEng.put("ë", "k");
		hebToEng.put("ê", "k");
		hebToEng.put("ì", "l");
		hebToEng.put("î", "m");
		hebToEng.put("í", "m");
		hebToEng.put("ð", "n");
		hebToEng.put("ï", "n");
		hebToEng.put("ñ", "s");
		hebToEng.put("ò", "y");
		hebToEng.put("ô", "p");
		hebToEng.put("ó", "p");
		hebToEng.put("ö", "c");
		hebToEng.put("õ", "c");
		hebToEng.put("÷", "q");
		hebToEng.put("ø", "r");
		hebToEng.put("ù", "e");
		hebToEng.put("ú", "t");
	}

	public void createArticle() {
		article = root.addElement("article");
		article.addAttribute("id", "1");
	}

	/**
	 *
	 *
	 *
	 */
	public void createDocument() {
		document = DocumentHelper.createDocument();
		root = document.addElement("corpus");
		root.addAttribute("name", "Demo Hebrew tokenized text in XML");
		root.addAttribute("version", "1.7.0").addAttribute("maintainer", "Slava Demender").addAttribute("email",
				"mila@cs.technion.ac.il");
		final Element metadata = root.addElement("metadata");
		metadata.addElement("name").addText("Demo Hebrew tokenized text in XML");
		metadata.addElement("version").addText("1.0");
		final Date now = new Date();
		metadata.addElement("date")
				.addText(DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT).format(now));
		metadata.addElement("maintainer").addText("Slava Demender");
		metadata.addElement("email").addText("mila@cs.technion.ac.il");
		metadata.addElement("license")
				.addText("Tokenization and XML representation: Copyright (C) 2009 Mila.\n"
						+ "This resource is released to the public licensed under the GNU Free Documentation License\n."
						+ "Note that all software, data files and documentation are licensed under the FDL.\n"
						+ "There is no warranty of any kind for the contents of this distribution.");
		/*
		 * Element transliteration =
		 * metadata.addElement("transliteration").addAttribute("from", "he");
		 * 
		 * transliteration.addElement("string ").addAttribute("hebrew",
		 * "à").addAttribute("latin", "a");
		 * 
		 * transliteration.addElement("string ").addAttribute("hebrew",
		 * "á").addAttribute("latin", "b");
		 * 
		 * transliteration.addElement("string ").addAttribute("hebrew",
		 * "â").addAttribute("latin", "g");
		 * 
		 * transliteration.addElement("string ").addAttribute("hebrew",
		 * "ã").addAttribute("latin", "d");
		 * 
		 * transliteration.addElement("string ").addAttribute("hebrew",
		 * "ä").addAttribute("latin", "h");
		 * 
		 * transliteration.addElement("string ").addAttribute("hebrew",
		 * "å").addAttribute("latin", "w");
		 * 
		 * transliteration.addElement("string ").addAttribute("hebrew",
		 * "æ").addAttribute("latin", "z");
		 * 
		 * transliteration.addElement("string ").addAttribute("hebrew",
		 * "ç").addAttribute("latin", "x");
		 * 
		 * transliteration.addElement("string ").addAttribute("hebrew",
		 * "è").addAttribute("latin", "v");
		 * 
		 * transliteration.addElement("string ").addAttribute("hebrew",
		 * "é").addAttribute("latin", "i");
		 * 
		 * transliteration.addElement("string ").addAttribute("hebrew",
		 * "ë").addAttribute("latin", "k");
		 * 
		 * transliteration.addElement("string ").addAttribute("hebrew",
		 * "ê").addAttribute("latin", "k");
		 * 
		 * transliteration.addElement("string ").addAttribute("hebrew",
		 * "ì").addAttribute("latin", "l");
		 * 
		 * transliteration.addElement("string ").addAttribute("hebrew",
		 * "î").addAttribute("latin", "m");
		 * 
		 * transliteration.addElement("string ").addAttribute("hebrew",
		 * "í").addAttribute("latin", "m");
		 * 
		 * transliteration.addElement("string ").addAttribute("hebrew",
		 * "ð").addAttribute("latin", "n");
		 * 
		 * transliteration.addElement("string ").addAttribute("hebrew",
		 * "ï").addAttribute("latin", "n");
		 * 
		 * transliteration.addElement("string ").addAttribute("hebrew",
		 * "ñ").addAttribute("latin", "s");
		 * 
		 * transliteration.addElement("string ").addAttribute("hebrew",
		 * "ò").addAttribute("latin", "y");
		 * 
		 * transliteration.addElement("string ").addAttribute("hebrew",
		 * "ô").addAttribute("latin", "p");
		 * 
		 * transliteration.addElement("string ").addAttribute("hebrew",
		 * "ó").addAttribute("latin", "p");
		 * 
		 * transliteration.addElement("string ").addAttribute("hebrew",
		 * "ö").addAttribute("latin", "c");
		 * 
		 * transliteration.addElement("string ").addAttribute("hebrew",
		 * "õ").addAttribute("latin", "c");
		 * 
		 * transliteration.addElement("string ").addAttribute("hebrew",
		 * "÷").addAttribute("latin", "q");
		 * 
		 * transliteration.addElement("string ").addAttribute("hebrew",
		 * "ø").addAttribute("latin", "r");
		 * 
		 * transliteration.addElement("string ").addAttribute("hebrew",
		 * "ù").addAttribute("latin", "e");
		 * 
		 * transliteration.addElement("string ").addAttribute("hebrew",
		 * "ú").addAttribute("latin", "t");
		 */
		metadata.addElement("comment")
				.addText("Tokenized corpuses and the segmentation program are available at http://mila.cs.technion.ac.il/");
	}

	public void createParapraphes() {

		if (countToken == 0 && countParagraph > 0) {
			paragraph.remove(sentence);
		}
		countToken = 0;
		countSentence = 0;
		countParagraph++;
		final String countst = String.valueOf(countParagraph);
		paragraph = article.addElement("paragraph").addAttribute("id", countst);
	}

	public void createSentences() {
		countToken = 0;
		countSentence++;
		final String countst = String.valueOf(countSentence);
		sentence = paragraph.addElement("sentence").addAttribute("id", countst);
	}

	/**
	 *
	 * @param word
	 *
	 */
	public void createTokens(String word) {
		countToken++;
		globalTokenCounter++;
		final String counttk = String.valueOf(countToken);
		final String transliterated = translateHebEng(word);
		try {
			token = sentence.addElement("token").addAttribute("id", counttk).addAttribute("surface", word)
					.addAttribute("transliterated", transliterated);
			// System.out.println(word);
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}

	public void finalizeDoc() {
		if (countToken == 0 && countParagraph > 0) {
			paragraph.remove(sentence);
		}
	}

	/**
	 *
	 * @return Returns the document.
	 *
	 */
	public Document getDocument() {
		return document;
	}

	/**
	 *
	 * @return Returns the globalTokenCounter.
	 *
	 */
	public int getGlobalTokenCounter() {
		return globalTokenCounter;
	}

	public String printDoc() {
		if (countToken == 0 && countParagraph > 0) {
			paragraph.remove(sentence);
		}
		return document.asXML();
	}

	/**
	 *
	 * @param outputFileName
	 *
	 */
	public void printDoc(String outputFileName) {
		if (countToken == 0 && countParagraph > 0) {
			paragraph.remove(sentence);
		}
		final String text = document.asXML();
		FileOutputStream out = null;
		try {
			out = new FileOutputStream(outputFileName);
		} catch (final FileNotFoundException e) {
			System.err.println("XMLProcessor: printDoc - FileNotFoundException");
			e.printStackTrace();
		}
		OutputStreamWriter pOut = null;
		try {
			pOut = new OutputStreamWriter(out, "UTF8");
		} catch (final UnsupportedEncodingException e4) {
			System.err.println("XMLProcessor: printDoc - UnsupportedEncodingException");
			e4.printStackTrace();
		}

		final BufferedWriter bw = new BufferedWriter(pOut);
		try {
			bw.write(text);
			bw.close();
			pOut.close();
		} catch (final IOException e3) {
			System.err.println("XMLProcessor: printDoc - Exception while writing/closing buffered writer");
			e3.printStackTrace();
		}
	}

	/**
	 *
	 * @param document
	 *
	 *           The document to set.
	 *
	 */
	public void setDocument(Document document) {
		this.document = document;
	}
}
