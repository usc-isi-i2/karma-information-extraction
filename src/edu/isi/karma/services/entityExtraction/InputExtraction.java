package edu.isi.karma.services.entityExtraction;

public class InputExtraction {
	String rowId, text;
	
	public void setRowId(String id) {
		this.rowId = id;
	}
	
	public void setText(String text) {
		this.text = text;
	}
	
	public String getText() {
		return this.text;
	}
	
	public String getRowId() {
		return this.rowId;
	}
	
	@Override
	public String toString() {
		return "InputExtraction [rowId="+this.rowId+", text="+this.text+"]"; 
	}

}
