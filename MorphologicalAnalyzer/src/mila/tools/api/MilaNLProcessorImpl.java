package mila.tools.api;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PipedReader;
import java.io.PipedWriter;
import java.io.PrintWriter;
import java.io.StringWriter;

import mila.HMM.HMM2Morph;
import mila.HMM.MorphMult2TaggerFormat;
import mila.mw.MWXMLTokenizer;
import mila.mw.PostProcessor1;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.input.ReaderInputStream;

public class MilaNLProcessorImpl implements MilaNLProcessor {
	private MilaSettings settings;

	public MilaNLProcessorImpl(MilaSettings settings) {
		this.settings = settings;
	}

	@Override
	public void process(InputStream in, OutputStream out) throws MilaException {
		try {
			tag(in, out);
		} catch (Throwable e) {
			throw new MilaException("Error occured while tagging", e);
		}
	}

	@Override
	public void setSettings(MilaSettings settings) {
		this.settings = settings;
	}

	protected void tag(final InputStream in, final OutputStream out) throws MilaException {
		final String tempPath;
		try {
			final File _tempDir;
			_tempDir = File.createTempFile("MILANLP_temp", "", new File(settings.tempDirectory));
			if (!_tempDir.delete())
				throw new IOException();
			if (!_tempDir.mkdir())
				throw new IOException();
			tempPath = _tempDir.getAbsolutePath();
			_tempDir.deleteOnExit();
		} catch (IOException e) {
			throw new MilaException(e);
		}

		doTagging(in, out, tempPath);
		try {
			FileUtils.deleteDirectory(new File(tempPath));
		} catch (IOException e) {
			/* ok =( */
		}

	}

	private void doTagging(final InputStream in2, final OutputStream out2, final String tempPath) throws MilaException {
		final PipedReader in = new PipedReader();
		final PipedWriter out;
		try {
			out = new PipedWriter(in);
		} catch (IOException e1) {
			throw new MilaException(e1);
		}
		final MWXMLTokenizer tokenizer = new MWXMLTokenizer();

		new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					PrintWriter pw = new PrintWriter(out);
					tokenizer.tokenizeAndAnalyze(in2, pw);
					pw.close();
				} finally {
					IOUtils.closeQuietly(out);
				}
			}
		}).start();

		PostProcessor1 p = new PostProcessor1(!settings.webFlag);
		StringWriter ppSW = new StringWriter();
		PrintWriter ppPW = new PrintWriter(ppSW);
		try {
			p.process(new ReaderInputStream(in), ppPW);
		} catch (Exception e) {
			throw new MilaException("Post-processing barfed, giving up on tagging", e);
		}
		String pp = ppSW.toString().trim();

		MorphMult2TaggerFormat mm = new MorphMult2TaggerFormat();
		String taggerFormat;
		try {
			taggerFormat = mm.myMorp2Tagger(pp, tempPath);
		} catch (Exception e) {
			throw new MilaException("Failed converting to HMM format, giving up on tagging", e);
		}
		String taggerInputFile = tempPath + taggerFormat;

		try {
			String command = "perl -I" + settings.royTaggerPath + " " + settings.royTaggerPath + File.separator
					+ "MTTest.pl -dir " + settings.royTaggerPath + File.separator + "workdir  -rmtmp " + taggerInputFile
					+ " " + settings.taggerLearningOutputDir + File.separator + "corpus.lm "
					+ settings.taggerLearningOutputDir + File.separator + "corpus.lex.prob";

			Process process = Runtime.getRuntime().exec(command); // pearl
			process.waitFor();
		} catch (Exception e) {
			throw new MilaException("Failed running tagger, giving up on tagging", e);
		}

		String taggedFile = settings.royTaggerPath + "/workdir" + "/tagging-" + taggerFormat.substring(1);
		HMM2Morph h = new HMM2Morph();
		PrintWriter hmm2morphpw = new PrintWriter(out2);
		try {
			h.process(pp, taggedFile, hmm2morphpw);
		} catch (Exception e) {
			throw new MilaException("Failed converting back to tagger format", e);
		}
	}

}
