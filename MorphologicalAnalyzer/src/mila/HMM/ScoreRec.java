package mila.HMM;

class ScoreRec {
	String scoresList = "";
	String transliterated = "";
	String posList = "";
	String rowNameList = "";

	public String getPosList() {
		return posList;
	}

	public String getRowNameList() {
		return rowNameList;
	}

	public String getScoresList() {
		return scoresList;
	}

	public String getTransliterated() {
		return transliterated;
	}

	public void setPosList(String val) {
		posList = val;
	}

	public void setRowNameList(String val) {
		rowNameList = val;
	}

	public void setScoresList(String val) {
		scoresList = val;
	}

	public void setTransliterated(String val) {
		transliterated = val;
	}

}
