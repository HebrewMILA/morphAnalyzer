package mila.dataStructures;

import java.util.ArrayList;

import mila.lexicon.utils.PrefixRec;

public class OutputAnalysisRecord {
	DBInflectionsRecord baseAnalysis = null;

	ArrayList<PrefixRec> prefixList = new ArrayList<PrefixRec>();

	public DBInflectionsRecord getBaseAnalysis() {
		return baseAnalysis;
	}

	public ArrayList<PrefixRec> getPrefixList() {
		return prefixList;
	}

	public void setBaseAnalysis(DBInflectionsRecord baseAnalysis) {
		this.baseAnalysis = baseAnalysis;
	}

	public void setPrefixList(ArrayList<PrefixRec> prefixList) {
		this.prefixList = prefixList;
	}

}
