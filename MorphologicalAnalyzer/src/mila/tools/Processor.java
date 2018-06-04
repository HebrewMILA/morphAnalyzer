package mila.tools;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBException;

import mila.lexicon.analyse.Data;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.Parser;
import org.apache.commons.cli.PosixParser;

public class Processor {
	private static String userDir = System.getProperty("user.dir") + File.separator;
	private static String tmpDir = userDir + "processor_tmp" + File.separator;

	/* Hard-coding until I have a chance to remove this nastiness */
	public static boolean WEB_FLAG = false;

	private static List<Tool> getTools(CommandLine cl) {
		List<Tool> ret = new ArrayList<Tool>();
		if (cl.hasOption("k")) {
			/* TODO: Tokenizer not implemented */
		}
		if (cl.hasOption("a")) {
			/* TODO: Analyzer not implemented */
		}
		if (cl.hasOption("t")) {
			ret.add(new OnlyTagger());
		}

		return ret;
	}

	public static void main(String args[]) throws ParseException, IOException, JAXBException {

		Data.dinflectionsFile = "dataFiles/dinflections.data";
		Data.dprefixesFile = "dataFiles/dprefixes.data";
		Data.gimatriaFile = "dataFiles/gimatria.data";
		Data.dmwinflectionsFile = "dataFiles/dmwinflections.data";
		Data.dmwe1File = "dataFiles/dmwe1.data";
		Data.dmwe2File = "dataFiles/dmwe2.data";
		Data.dmwe3File = "dataFiles/dmwe3.data";
		Data.dmwe4File = "dataFiles/dmwe4.data";

		Parser p = new PosixParser();
		Options ops = new Options();
		ops.addOption("k", "tokenize", false, "Run the tokenizer on the input");
		ops.addOption("a", "analyze", false, "Run the analyzer on the input");
		ops.addOption("t", "tag", false, "Run the tagger on the input");
		ops.addOption("S", "database", false, "Use database connection");
		CommandLine cl = p.parse(ops, args);

		if (cl.hasOption("S"))
			Processor.WEB_FLAG = true;
		else
			Processor.WEB_FLAG = false;

		Data.webFlag = Processor.WEB_FLAG;
		if (!Processor.WEB_FLAG)
			Data.init(false);

		File input = new File((String) cl.getArgList().get(0));
		File output = new File((String) cl.getArgList().get(1));
		File tmpDir = new File(Processor.tmpDir);
		if (!tmpDir.exists()) {
			tmpDir.mkdirs();
		}

		Processor proc = new Processor();
		proc.setTools(getTools(cl));
		if (input.isDirectory()) {
			if (!output.isDirectory())
				throw new FileNotFoundException("Input is a directory, output path must be a directory too");
			proc.processDir(input, output, tmpDir);
		} else {
			proc.processFile(input, output, tmpDir);
		}

		System.out.println("Done, encountered " + Integer.toString(proc.getErrorCount()) + " errors.");
		System.exit(0);
	}

	private List<ProcessorError> errors;

	private List<Tool> tools;

	public Processor() {
		super();
		this.errors = new ArrayList<ProcessorError>();
	}

	public int getErrorCount() {
		return errors.size();
	}

	private void processDir(File input, File output, File tmpDir) throws IOException, JAXBException {
		if (input.isDirectory()) {
			output.mkdirs();
			for (File f : input.listFiles()) {
				processDir(f, new File(output.getCanonicalPath() + File.separator + input.getName()), tmpDir);
			}

		} else { /* input is file */
			output.mkdirs();
			processFile(input, new File(output.getCanonicalFile() + File.separator + input.getName()), tmpDir);
		}

	}

	private void processFile(File input, File output, File tmpDir) throws IOException, JAXBException {
		File cur_input = input;
		try {
			for (Tool tool : tools) {
				File temporary = null;
				if (tools.get(tools.size() - 1) == tool) {
					/* last tool, use real output */
					temporary = output;
				} else {
					temporary = File.createTempFile("processor", "", tmpDir.getAbsoluteFile());
				}
				tool.processFile(cur_input, temporary);
				if (cur_input != input)
					cur_input.delete();
				cur_input = temporary;
			}
		} catch (Exception e) {
			/* TODO: Add support for more tools */
			errors.add(new ProcessorError(e, cur_input.getCanonicalPath(), ToolType.TAGGER));
			e.printStackTrace();
		}
	}

	public void setTools(List<Tool> tools) {
		this.tools = tools;
	}
}

enum ToolType {
	ANALYZER, TAGGER, TOKENIZER
}