package edu.isi.karma.services.entityExtraction;

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
		return "Extraction [text=" + this.extraction + "]";
	}

	@Override
	public boolean equals(Object arg0) {
		Extraction obj = (Extraction) arg0;
		if (this.extraction.equals(obj.extraction)) {
			return true;
		}
		return false;
	}

	@Override
	public int hashCode() {
		return this.extraction.hashCode();
	}

}
