package mila.tools.api;

public class MilaSettings {
	/** path to the inflections data file */
	protected String dinflectionsFile = null;

	/** path to the mwe1 data file */
	protected String dmwe1File = null;

	/** path to the mwe1 data file */
	protected String dmwe2File = null;

	/** path to the mwe1 data file */
	protected String dmwe3File = null;

	/** path to the mwe1 data file */
	protected String dmwe4File = null;

	/** path to the mwinflections data file */
	protected String dmwinflectionsFile = null;

	/** path to the prefixes data file */
	protected String dprefixesFile = null;

	/** path to the gimatria data file */
	protected String gimatriaFile = null;

	/** path to tagger */
	protected String royTaggerPath = null;

	/** path to tagger model files */
	protected String taggerLearningOutputDir = null;

	/** path to a directory where temporary files may be created */
	protected String tempDirectory = null;

	/** Database mode? True is use the database. False is use data-files. */
	protected boolean webFlag = true;

	/**
	 * Generic constructor to use while working against data files.
	 * 
	 * @param webFlag
	 * @param tempDirectory
	 * @param royTaggerPath
	 * @param taggerLearningOutputDir
	 * @param dinflectionsFile
	 * @param dmwe1File
	 * @param dmwe2File
	 * @param dmwe3File
	 * @param dmwe4File
	 * @param dmwinflectionsFile
	 * @param dprefixesFile
	 * @param gimatriaFile
	 */
	public MilaSettings(boolean webFlag, String tempDirectory, String royTaggerPath, String taggerLearningOutputDir,
			String dinflectionsFile, String dmwe1File, String dmwe2File, String dmwe3File, String dmwe4File,
			String dmwinflectionsFile, String dprefixesFile, String gimatriaFile) {
		super();
		this.webFlag = webFlag;
		this.tempDirectory = tempDirectory;
		this.royTaggerPath = royTaggerPath;
		this.taggerLearningOutputDir = taggerLearningOutputDir;
		this.dinflectionsFile = dinflectionsFile;
		this.dmwe1File = dmwe1File;
		this.dmwe2File = dmwe2File;
		this.dmwe3File = dmwe3File;
		this.dmwe4File = dmwe4File;
		this.dmwinflectionsFile = dmwinflectionsFile;
		this.dprefixesFile = dprefixesFile;
		this.gimatriaFile = gimatriaFile;
	}

	/**
	 * Constructor to use while working against local DB.
	 * 
	 * @param tempDirectory
	 * @param royTaggerPath
	 * @param taggerLearningOutputDir
	 */
	public MilaSettings(String tempDirectory, String royTaggerPath, String taggerLearningOutputDir) {
		this.webFlag = true;
		this.tempDirectory = tempDirectory;
		this.royTaggerPath = royTaggerPath;
		this.taggerLearningOutputDir = taggerLearningOutputDir;
	}

	public String getDinflectionsFile() {
		return dinflectionsFile;
	}

	public String getDmwe1File() {
		return dmwe1File;
	}

	public String getDmwe2File() {
		return dmwe2File;
	}

	public String getDmwe3File() {
		return dmwe3File;
	}

	public String getDmwe4File() {
		return dmwe4File;
	}

	public String getDmwinflectionsFile() {
		return dmwinflectionsFile;
	}

	public String getDprefixesFile() {
		return dprefixesFile;
	}

	public String getGimatriaFile() {
		return gimatriaFile;
	}

	public String getRoyTaggerPath() {
		return royTaggerPath;
	}

	public String getTaggerLearningOutputDir() {
		return taggerLearningOutputDir;
	}

	public String getTempDirectory() {
		return tempDirectory;
	}

	public boolean isWebFlag() {
		return webFlag;
	}

	public void setDinflectionsFile(String dinflectionsFile) {
		this.dinflectionsFile = dinflectionsFile;
	}

	public void setDmwe1File(String dmwe1File) {
		this.dmwe1File = dmwe1File;
	}

	public void setDmwe2File(String dmwe2File) {
		this.dmwe2File = dmwe2File;
	}

	public void setDmwe3File(String dmwe3File) {
		this.dmwe3File = dmwe3File;
	}

	public void setDmwe4File(String dmwe4File) {
		this.dmwe4File = dmwe4File;
	}

	public void setDmwinflectionsFile(String dmwinflectionsFile) {
		this.dmwinflectionsFile = dmwinflectionsFile;
	}

	public void setDprefixesFile(String dprefixesFile) {
		this.dprefixesFile = dprefixesFile;
	}

	public void setGimatriaFile(String gimatriaFile) {
		this.gimatriaFile = gimatriaFile;
	}

	public void setRoyTaggerPath(String royTaggerPath) {
		this.royTaggerPath = royTaggerPath;
	}

	public void setTaggerLearningOutputDir(String taggerLearningOutputDir) {
		this.taggerLearningOutputDir = taggerLearningOutputDir;
	}

	public void setTempDirectory(String tempDirectory) {
		this.tempDirectory = tempDirectory;
	}

	public void setWebFlag(boolean webFlag) {
		this.webFlag = webFlag;
	}
}
