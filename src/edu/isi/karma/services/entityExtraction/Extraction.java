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

		System.out.println("in equals");

		Extraction obj = (Extraction) arg0;

		//System.out.println("1st " + this.extraction);
		//System.out.println("2nd " + obj.extraction);

		if (this.extraction.equals(obj.extraction)) {
			return true;
		}
		return false;
	}

	@Override
	public int hashCode() {

		//System.out.println("in hash code");
		return this.extraction.hashCode();
	}

}
