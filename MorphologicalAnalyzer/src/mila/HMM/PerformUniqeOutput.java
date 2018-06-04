/*
 * Created on 10/05/2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package mila.HMM;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Vector;

/**
 * @author daliabo
 * 
 *         TODO To change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public class PerformUniqeOutput {
	public static void main(String[] args) throws IOException {
		PerformUniqeOutput r = new PerformUniqeOutput();
		String outputFile = "C:\\Documents and Settings\\daliabo\\My Documents\\lexicon\\diffTests\\outputRoy2.txt";
		r.myUniqueOutput("</paragraph>\n" + "#\n" + "hhzdmnwt\n" + "\t(NOUN hzdmnwt)" + "U\n" + "(yyQUOT yyQUOT)\n"
				+ ".\n" + "(yyDOT yyDOT)", outputFile);
	}

	BufferedWriter bw = null;

	BufferedReader bi = null;

	StringBuffer outputString = new StringBuffer();

	void ioHandling(String inputFile, String outputFile) throws FileNotFoundException {

		bi = new BufferedReader(new InputStreamReader(new FileInputStream(inputFile)));
		FileOutputStream out = null;
		try {
			out = new FileOutputStream(outputFile);
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		OutputStreamWriter pOut = new OutputStreamWriter(out);
		bw = new BufferedWriter(pOut);

	}

	public String myUniqueOutput(String input, String outputFile) {
		String rt = "";
		FileOutputStream out = null;
		try {
			out = new FileOutputStream(outputFile);
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		OutputStreamWriter pOut = new OutputStreamWriter(out);
		bw = new BufferedWriter(pOut);
		try {
			rt = process(input);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rt;
	}

	public String process(String input) throws IOException {
		String line = "";
		new Vector();
		HashMap<String, String> hashmap = null;
		String token = "";
		StringReader reader = new StringReader(input);
		bi = new BufferedReader(reader);
		// start handling tokens and analysis - loop through the file lines
		line = bi.readLine();
		while (line != null) {
			if (line.indexOf("</paragraph>") != -1) {
				bw.write(line);
				bw.newLine();
				outputString.append(line);
				line = bi.readLine();
			} else if (line.equals("#")) {
				bw.write(line);
				bw.newLine();
				outputString.append(line);
				line = bi.readLine();
			} else if (!line.startsWith("\t")) {
				token = line;
				bw.write(token);
				bw.newLine();
				line = bi.readLine();

			} else {
				boolean MWFlag = false;
				hashmap = new HashMap<String, String>();
				if (line.startsWith("\t(MWE")) {
					token = line;
					bw.write(token);
					bw.newLine();
					line = bi.readLine();
					hashmap = null;
					MWFlag = true;
					// while ((line = bi.readLine()) != null
					// && (line.startsWith("\t"))) {
					//
					// }
				} else {
					if (!hashmap.containsKey(line)) {
						hashmap.put(line, line);
					}
					while ((line = bi.readLine()) != null && (line.startsWith("\t"))) {
						if (line.startsWith("\t(MWE")) {
							token = line;
							bw.write(token);
							bw.newLine();
							outputString.append(line);
							MWFlag = true;
						} else if (!MWFlag && !hashmap.containsKey(line)) {
							hashmap.put(line, line);
						}

					}
					if (!MWFlag) {
						for (String analysis : hashmap.keySet()) {
							bw.write(analysis);
							bw.newLine();
							outputString.append(analysis);
						}
					}
				}
			}
		}
		bw.flush();
		bw.close();
		// System.out.println(outputString.toString());
		return outputString.toString();
	}
}
