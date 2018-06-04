package mila.tools;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.CharBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;

import javax.xml.bind.JAXBException;

import mila.HMM.HMM2Morph;
import mila.HMM.MorphMult2TaggerFormat;
import mila.lexicon.analyse.Data;

import org.apache.commons.io.FileUtils;

public class OnlyTagger implements Tool {
	private String hmmTaggerDir = System.getProperty("user.dir");
	private String royTaggerDir = hmmTaggerDir + File.separator + "royTagger";
	private String taggerLOFDir = hmmTaggerDir;
	private String dprefixesFile = "dataFiles/dprefixes.data";

	public static void main(String args[]) throws IOException, JAXBException {
		final String input_name = args[0];
		final String output_name = args[1];

		final File input = new File(input_name);
		final File output = new File(output_name);

		final Tool tool = new OnlyTagger();

		tool.processFile(input, output);

		System.exit(0);
	}

	public void processDirectory(File input, File output) {
		// TODO Auto-generated method stub

	}

	@Override
	public void processFile(File input, File output) throws IOException, JAXBException {
		final String processedXML = readFile(input).toString();
		final File tempDir = createTempDir();

		try {
			Data.webFlag = Processor.WEB_FLAG;
			final String taggerFormat = goRoyTag(processedXML, tempDir);
			goConvertToXML(output, processedXML, taggerFormat);
		} finally {
			FileUtils.deleteDirectory(tempDir);
		}
	}

	private void goConvertToXML(File output, String processedXML, String taggerFormat)
			throws IOException, FileNotFoundException {
		final HMM2Morph h = new HMM2Morph();
		final String fileName = taggerFormat.substring(1);
		final String homeDirectoy = new java.io.File(".").getCanonicalPath();
		final StringBuilder sb = new StringBuilder();
		sb.append(homeDirectoy).append(File.separator).append("royTagger").append(File.separator).append("workdir")
				.append(File.separator).append("tagging-").append(fileName);
		final String taggedFile = sb.toString();
		final PrintWriter pw = new PrintWriter(new FileOutputStream(output));

		System.err.println("Start output to file");
		try {
			Data.webFlag = Processor.WEB_FLAG;
			h.process(processedXML, taggedFile, pw, dprefixesFile);
		} catch (final Exception e) {
			throw new IOException(e);
		}
		System.err.println("End output to file");
	}

	private String goRoyTag(String processedXML, File tempDir) throws JAXBException, IOException {
		Data.webFlag = Processor.WEB_FLAG;
		final MorphMult2TaggerFormat mm2tf = new MorphMult2TaggerFormat();
		final String taggerFormat = mm2tf.myMorp2Tagger(processedXML, tempDir.getAbsolutePath());
		final String cmdline = createPerlCommand(tempDir.toString() + taggerFormat);
		System.err.println("Running command: " + cmdline);

		final Process proc = Runtime.getRuntime().exec(cmdline);

		boolean flag = true;
		int rc = -1;
		while (flag) {
			try {
				rc = proc.waitFor();
				flag = false;
			} catch (final InterruptedException e) {
				/* Do nothing -- flag is true so we'll continue waiting */
				System.err.println("Waiting for tagger, go kill that if stuck");
			}
		}

		System.err.println("Tagger process terminated with return code " + Integer.toString(rc));

		return taggerFormat;
	}

	public StringBuilder readFile(File f) throws IOException {
		final FileInputStream fis = new FileInputStream(f);
		final FileChannel fc = fis.getChannel();

		final int sz = (int) fc.size();
		final MappedByteBuffer bb = fc.map(FileChannel.MapMode.READ_ONLY, 0, sz);

		final Charset utf8 = Charset.forName("UTF-8");
		final CharsetDecoder decoder = utf8.newDecoder();
		final CharBuffer cb = decoder.decode(bb);

		final StringBuilder outBuffer = new StringBuilder(cb);
		fc.close();
		fis.close();
		return outBuffer;
	}

	private String createPerlCommand(String taggerInput) {
		final StringBuilder sb = new StringBuilder();
		sb.append("perl -I").append(royTaggerDir).append(" ").append(royTaggerDir).append(File.separator)
				.append("MTTest.pl -dir ").append(royTaggerDir).append(File.separator).append("workdir -rmtmp ")
				.append(taggerInput).append(" ").append(taggerLOFDir).append(File.separator).append("corpus.lm ")
				.append(taggerLOFDir).append(File.separator).append("corpus.lex.prob");
		return sb.toString();
	}

	private File createTempDir() throws IOException {
		final StringBuilder sb = new StringBuilder();
		sb.append(File.separator).append(System.getProperty("user.dir")).append(File.separator);
		final File tempParentDir = new File(sb.toString());
		File tempDir = null;

		try {
			tempDir = File.createTempFile("mydir", "ClientServerHMMTagger", tempParentDir);
			if (!tempDir.delete()) {
				throw new IOException();
			}
			// tempFile.mkdir();
			if (!tempDir.mkdir()) {
				throw new IOException();
			}
		} catch (final IOException ex) {
			System.err.println("Cannot create temp file (" + tempParentDir.getAbsolutePath() + "): " + ex.getMessage());
			throw ex;
		}
		return tempDir;
	}

	public void setHmmTaggerDir(String hmmTaggerDir) {
		this.hmmTaggerDir = hmmTaggerDir;
	}

	public void setRoyTaggerDir(String royTaggerDir) {
		this.royTaggerDir = royTaggerDir;
	}

	public void setTaggerLOFDir(String taggerLOFDir) {
		this.taggerLOFDir = taggerLOFDir;
	}

	public void setDprefixesFile(String dprefixesFile) {
		this.dprefixesFile = dprefixesFile;
	}
}
