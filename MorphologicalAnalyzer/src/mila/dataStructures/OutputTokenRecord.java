package mila.dataStructures;

import java.util.ArrayList;

public class OutputTokenRecord {
	String surface = "";
	ArrayList<OutputAnalysisRecord> outputAnalysisList = new ArrayList<OutputAnalysisRecord>();

	public ArrayList<OutputAnalysisRecord> getOutputAnalysisList() {
		return outputAnalysisList;
	}

	public String getSurface() {
		return surface;
	}

	public void setOutputAnalysisList(ArrayList<OutputAnalysisRecord> outputAnalysisList) {
		this.outputAnalysisList = outputAnalysisList;
	}

	public void setSurface(String surface) {
		this.surface = surface;
	}

}
