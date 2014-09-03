package edu.isi.karma.services.entityExtraction;

public class OutputExtraction {
	String rowId;
	Extractions extractions;
	
	public void setRowId(String hash) {
		this.rowId = hash;
	}

	public String getRowId() {
		return this.rowId;
	}

	public void setExtractions(Extractions e) {
		this.extractions = e;
	}
	
	public Extractions getExtractions() {
		return this.extractions;
	}
	

}
