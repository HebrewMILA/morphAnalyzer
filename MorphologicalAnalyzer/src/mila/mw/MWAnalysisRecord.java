package mila.mw;

public class MWAnalysisRecord {
	String paragraphIndex = "";
	String sentenceIndex = "";
	String tokenIndex = "";
	String mwId = "";
	String consecutive = "";
	String pos = "";
	boolean prefix = false;

	public void setConsecutive(String consecutive) {
		this.consecutive = consecutive;
	}

	public void setMwId(String mwId) {
		this.mwId = mwId;
	}

	public void setParagraphIndex(String paragraphIndex) {
		this.paragraphIndex = paragraphIndex;
	}

	public void setPos(String pos) {
		this.pos = pos;
	}

	public void setPrefix(boolean prefix) {
		this.prefix = prefix;
	}

	public void setSentenceIndex(String sentenceIndex) {
		this.sentenceIndex = sentenceIndex;
	}

	public void setTokenIndex(String tokenIndex) {
		this.tokenIndex = tokenIndex;
	}

}
