package com.example;

public class OutputExtraction {
	String rowHash;
	Extractions extractions;
	
	public void setRowHash(String hash) {
		this.rowHash = hash;
	}

	public String getRowHash() {
		return this.rowHash;
	}

	public void setExtractions(Extractions e) {
		this.extractions = e;
	}
	
	public Extractions getExtractions() {
		return this.extractions;
	}
	/*	
	@Override
	public String toString() {
		return "InputExtraction [rowHash="+this.rowHash+", text="+this.text+"]"; 
	}
*/
}
