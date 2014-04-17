package com.example;

public class Extraction {
	String extraction;
	
	public void setExtraction(String text) {
		this.extraction = text;
	}
	
	public String getExtraction() {
		return this.extraction;
	}
	
	@Override
	public String toString() {
		return "Extraction [text="+this.extraction+"]"; 
	}

}
