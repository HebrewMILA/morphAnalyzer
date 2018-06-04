// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3)
// Source File Name:   MWXMLTokenizer.java

package mila.mw;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;

import mila.tokenizers.XMLTokenizer1;

import org.dom4j.Document;

// Referenced classes of package mw:
//            MWXMLMorphAnaslyzer

public class MWXMLTokenizer extends XMLTokenizer1 {

	static final int MAX_EMPTY_LINES = 100;

	public static void main(String args[]) {
		XMLTokenizer1 t = new XMLTokenizer1();
		String st = "\u05DB\u05E4\u05E8 \u05D7\u05D1\"\u05D3";
		InputStream inputStraem = null;
		try {
			inputStraem = new ByteArrayInputStream(st.getBytes("UTF8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		t.tokenizeAndAnalyze(inputStraem, pw);
		String result = sw.toString();
		System.out.println(result);
		System.out.println("******************************************************");
		System.out.println("******************************************************");
		st = "\u05D6\u05D5\u05D4\u05D9 \u05D1\u05D3\u05D9\u05E7\u05D4 \u05E0\u05D5\u05E1\u05E4\u05EA";
		try {
			inputStraem = new ByteArrayInputStream(st.getBytes("UTF8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	boolean webFlag;

	public MWXMLTokenizer() {
		webFlag = false;
	}

	@Override
	public void tokenizeAndAnalyze(InputStream in, PrintWriter pw) {
		// System.out.println("(F) MWXMLTokenizer: tokenizeAndAnalyze()");
		// webFlag = true; // looks like this doesnt matter - yossi 23.8.11
		// webFlag = false;
		Document document = null;
		try {
			bi = new BufferedReader(new InputStreamReader(in, "UTF8")); // read
			// into
			// buffer
			// reader
			webProcess();
			xmlTokenizer.finalizeDoc();
			document = xmlTokenizer.getDocument();
		} catch (Exception e) {
			System.out.println(
					"XMLTokenizer:tokenizeAndAnalyze -web interface - Exception in BufferedReader or process function");
			e.printStackTrace();
		}
		String documentStr = document.asXML();
		// System.out.println("(F) documentStr = " + documentStr);
		MWXMLMorphAnalyzer mwxmlMorphAnalyzer = new MWXMLMorphAnalyzer();
		mwxmlMorphAnalyzer.morphologicalAnalyzer(pw, documentStr);
	}
}
