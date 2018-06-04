package mila.tools;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.Socket;

import mila.HMM.HMM2Morph;
import mila.HMM.MorphMult2TaggerFormat;
import mila.mw.MWXMLMorphAnalyzer;
import mila.mw.MWXMLTokenizer;
import mila.mw.PostProcessor1;

public class ClientWorker implements Runnable {
	public static boolean deleteDir(File dir) {
		if (dir.isDirectory()) {
			String[] children = dir.list();
			for (int i = 0; i < children.length; i++) {
				boolean success = deleteDir(new File(dir, children[i]));
				if (!success) {
					return false;
				}
			}
		}
		// The directory is now empty so delete it
		return dir.delete();
	}

	private Socket client;

	// ClientWorker(Socket client, XMLMorphAnalyzer analyzer)
	ClientWorker(Socket client, MWXMLMorphAnalyzer analyzer) {
		this.client = client;
	}

	@Override
	public void run() {
		// System.out.println("(F) ClientWorker:run()");
		String inputLine = "";
		StringBuffer analysis = new StringBuffer();
		String analysisStr = "";
		BufferedReader sin = null;
		PrintWriter sout = null;
		int inputSize = 0;

		long startTime = System.currentTimeMillis();

		// ///////////////////////////////////////////////////////////////////
		// Tokenize and Analyze
		// //////////////////////////////////////////////////////////////////
		try {
			sin = new BufferedReader(new InputStreamReader(client.getInputStream()));
			sout = new PrintWriter(new OutputStreamWriter(client.getOutputStream(), "UTF8"), true);

			// analysisStr = readInputFile1(client.getInputStream());

			inputLine = sin.readLine();
			while (inputLine == null)
				inputLine = sin.readLine();

			inputSize += inputLine.length();

			while (!inputLine.equals("Bye")) {
				analysis.append(inputLine);
				analysis.append(System.getProperty("line.separator"));
				inputLine = sin.readLine();
				inputSize += inputLine.length();
			}

			analysisStr = analysis.toString();
			// System.out.println("***********************ClientWorker:
			// analysis="+analysisStr);
			java.io.InputStream in = null;
			in = new java.io.ByteArrayInputStream(analysisStr.getBytes("UTF-8"));
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);

			// XMLTokenizer1 tokenizer = new XMLTokenizer1();
			MWXMLTokenizer tokenizer = new MWXMLTokenizer();
			// working in database mode

			// int tokensNumber = tokenizer.tokenizeAndAnalyze(in,pw,analyzer);
			// // pw -> sw -> result
			tokenizer.tokenizeAndAnalyze(in, pw); // MWXMLTokenizer version
			// insted of the line above

			// System.out.println("************************************************************");
			// System.out.println("tokensNum \t tokenization&analysing \t
			// morph2HMM \t tagger \tHMM2Morph \t total");
			// System.out.println();
			// System.out.print("\t"+tokensNumber);

			// long endTokenizeAndAnalyze = System.currentTimeMillis();
			// long tokenizationAndAnalyzingTime = endTokenizeAndAnalyze -
			// startTime;

			// System.out.print("\t\t "+tokenizationAndAnalyzingTime );

			// ///////////////////////////////////////////////////////////////////////////////////////////////////
			// This check is done to make sure that a valid XML file was created
			// by the morphological analyzer
			// In case of invalid input file such as non UTF-8 input text file
			// this check will lead to program exit
			// ////////////////////////////////////////////////////////////////////////////////////////////////////
			// if(sw.toString().length()==0)
			// return;

			// In case a valid morphological analyzer XML output was created -
			// we will continue to the next stage
			String result = sw.toString();

			// System.out.println("(F) ClientWorker: post-processing...");
			InputStream myin = new java.io.ByteArrayInputStream(result.getBytes("UTF-8"));
			StringWriter ppSW = new StringWriter();
			PrintWriter ppPW = new PrintWriter(ppSW);
			PostProcessor1 p = new PostProcessor1(true); // true uses the data
			// files and not the
			// mysql server
			p.process(myin, ppPW);
			String postProcessorResult = ppSW.toString();
			postProcessorResult = postProcessorResult.trim();

			// System.out.println(result);
			// myOutputFile.nf - roy's format before performing uniqe - removing
			// multiple analysis occurence
			// Random generator = new Random();
			// String dir = "mydir" + generator.nextInt();
			String tempDirectoryPath = "";
			// int randomValue = generator.nextInt();
			// String randomValStr = String.valueOf(randomValue);
			File tempFile = null;
			String path = File.separator + System.getProperty("user.dir") + File.separator + "/tmp";
			File tempDirectory = new File(path);
			// File tempDirectory= new File("/tmp");
			try {
				tempFile = File.createTempFile("mydir", "ClientServerHMMTagger", tempDirectory);
				if (!tempFile.delete())
					throw new IOException();
				// tempFile.mkdir();
				if (!tempFile.mkdir())
					throw new IOException();
				tempDirectoryPath = tempFile.getAbsolutePath();
				// System.out.println("tempDirectoryPath="+ tempDirectoryPath);
			} catch (IOException ex) {
				System.err.println("Cannot create temp file: " + ex.getMessage());
				ex.printStackTrace();
				System.exit(-1);
			}

			// ////////////////////////////////////////////////////////////
			// Change directory !!!!!!!!!!!!!!!!!!!!!!!!!!
			// /////////////////////////////////////////////////
			// move from morphological analyzer format to tagger format
			// for each input file we create a new directory in /tmp directory
			// which contains
			// input file for the tagger
			// //////////////////////////////////////////////////
			MorphMult2TaggerFormat mm = new MorphMult2TaggerFormat();

			String dprefixesFile = "dataFiles/dprefixes.data";
			// String taggerFormat = mm.myMorp2Tagger(result,
			// tempDirectoryPath); // convert to TAGGER format
			String taggerFormat = mm.myMorp2Tagger(postProcessorResult, tempDirectoryPath); // convert
			// to
			// TAGGER
			// format
			// NEW
			// with
			// post
			// processor
			// 27.7.11
			// -yossi
			String taggerInputFile = tempDirectoryPath + taggerFormat;

			// Added by Gennadi 03/11/2008
			String HMMTaggerDir = null;
			String RoyTaggerDir = null;
			String TaggerLOFDir = null;
			HMMTaggerDir = System.getProperty("user.dir") + File.separator;
			RoyTaggerDir = HMMTaggerDir + File.separator + "royTagger";
			TaggerLOFDir = HMMTaggerDir + File.separator + "taggerLearningOutputFile";
			// End Added by Gennadi 03/11/2008

			try {
				// long afterFormatTime = System.currentTimeMillis();
				// long changeFormatTime = afterFormatTime -
				// endTokenizeAndAnalyze;
				// System.out.print("\t\t\t"+ changeFormatTime);
				// ////////////////////////////////////////////////////////////////////////
				// Run Tagger
				// ///////////////////////////////////////////////////////////////////////
				// myOutputFile1.nf - roy's format after performing remove
				// multiple format analysis occurence
				// this name is hard coded in the Java program

				// Added by Gennadi 03/11/2008
				// Execute perl script using "perl -Imodule_dir" option.
				// Module directory should point to royTagger directory.
				// This option tells the perl interpreter where to look for the
				// included modules.
				// You don't need "use lib" directives,
				// if you use this option you can simply remove it.

				// in order to avoid tmp files creation - add the option -rmtmp
				// before taggerInputFile

				String command = "perl -I" + RoyTaggerDir + " " + RoyTaggerDir + File.separator + "MTTest.pl -dir "
						+ RoyTaggerDir + File.separator + "workdir  -rmtmp " + taggerInputFile + " " + TaggerLOFDir
						+ File.separator + "corpus.lm " + TaggerLOFDir + File.separator + "corpus.lex.prob";

				// End Added by Gennadi 03/11/2008

				// System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%"+ command);
				Process process = Runtime.getRuntime().exec(command); // run
				// tagger
				// using
				// pearl
				process.waitFor();

				// endHMMTagger = System.currentTimeMillis();
				// long taggerTime = endHMMTagger -afterFormatTime;
				// System.out.print("\t\t\t\t"+ taggerTime);

			} catch (IOException e3) {
				e3.printStackTrace();
			} catch (Exception e3) {
				e3.printStackTrace();
			}

			// -------------------------------------------------------------------------------------------
			// Move from Tagger format to XML format
			// -------------------------------------------------------------------------------------------
			HMM2Morph h = new HMM2Morph();
			String fileName = taggerFormat.substring(1);
			String homeDirectoy = new java.io.File(".").getCanonicalPath();
			// System.out.println("My home directory=" + homeDirectoy);
			// System.out.println("ClientWorker: file = " + fileName);
			String taggedFile = homeDirectoy + "/royTagger/workdir" + "/tagging-" + fileName;

			// sw = new StringWriter(); // this seems to be dead code not needed
			// (07.04.11 commented by yossi)
			pw = new PrintWriter(sout);
			// System.out.println("---> RESULT = " + result); // result contains
			// the analyzed text

			// h.process(result,taggedFile,sout,dprefixesFile);
			h.process(postProcessorResult, taggedFile, sout, dprefixesFile);

			// System.out.println("---> sw.toString = " + sw.toString());
			// result = sw.toString(); // this seems to be dead code not needed
			// (07.04.11 commented by yossi)
			// result=result.trim(); // this seems to be dead code not needed
			// (07.04.11 commented by yossi)

			// sout.write(result); // this seems to be dead code not needed
			// (07.04.11 commented by yossi)

			if (tempFile.exists()) {
				deleteDir(tempFile);
			}

			sout.write(" #*");

			// System .out.println("result="+result);

			// long endHMMToMorphFormat = System.currentTimeMillis();
			// long HMMToMorphTime = endHMMToMorphFormat-endHMMTagger;
			// System.out.print("\t\t\t\t\t"+ HMMToMorphTime);
			// long total = endHMMToMorphFormat - startTime;
			// System.out.print("\t\t\t\t\t\t"+ total);

			sout.close();
			sin.close();
			client.close();

			// ////////////////////////////////////////////////////
			// Deleting Files
			// /////////////////////////////////////////////////
			// delete the temporay directory under /tmp
			// if too much files under the /tmp there is a point when a new tmp
			// file can't be created
			// System.out.println("directory to delete"+tempDirectoryPath);
			// File n = new File(tempDirectoryPath);
			// File[] files = n.listFiles();
			// for(int i=0; i<files.length; i++) {
			// files[i].delete();
			// }
			// n.delete();

			// File n = new File(taggedFile);
			// n.delete();
			// n= new File( homeDirectoy + "/royTagger/workdir/"+fileName +
			// ".analyses.morph.map.sentmap");
			// n.delete();
			// n= new File( homeDirectoy + "/royTagger/workdir/"+fileName +
			// ".corpus.morph.lm");
			// n.delete();
			// n= new File( homeDirectoy + "/royTagger/workdir/"+fileName +
			// ".analyses.morph.map");
			// n.delete();
			// n= new File( homeDirectoy + "/royTagger/workdir/"+fileName +
			// ".analyses.morph.map.sentrevmap");
			// n.delete();
			// n= new File( homeDirectoy + "/royTagger/workdir/"+fileName +
			// ".analyses.morph.map.tagging");
			// n.delete();
			// n= new File( homeDirectoy + "/royTagger/workdir/"+fileName +
			// ".analyses.morph.revmap");
			// n.delete();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				sout.close();
				sin.close();
				client.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		long elapsedTime = System.currentTimeMillis() - startTime;
		System.out.println("Elapsed Time = " + elapsedTime);
		System.out.println("Input Length = " + inputSize);
		float speed = (float) inputSize / (elapsedTime / 1000);
		System.out.println("Processed " + speed + " byte/sec");
	}
}
