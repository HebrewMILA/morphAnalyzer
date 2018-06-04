/*
 * Created on 23/03/2011
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package mila.tools;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;

import mila.mw.MWXMLTokenizer;

/**
 * @author syjacob
 * 
 *         going to use this class for testing
 */
public class AutoActivities {

	public static void main(String[] args) {

		MWXMLTokenizer mwxmlTokenizer = new MWXMLTokenizer();
		java.io.InputStream in = null;
		try {
			in = new java.io.ByteArrayInputStream("קרית".getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		try {
			mwxmlTokenizer.tokenizeAndAnalyze(in, pw);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String result = sw.toString();
		System.out.println("(F) main() result = " + result);
	}
}
