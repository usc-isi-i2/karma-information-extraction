package com.example;

public class InputExtraction {
	String rowHash, text;
	
	public void setRowHash(String hash) {
		this.rowHash = hash;
	}
	
	public void setText(String text) {
		this.text = text;
	}
	
	public String getText() {
		return this.text;
	}
	
	public String getRowHash() {
		return this.rowHash;
	}
	
	@Override
	public String toString() {
		return "InputExtraction [rowHash="+this.rowHash+", text="+this.text+"]"; 
	}

}
