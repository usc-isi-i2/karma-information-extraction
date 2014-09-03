package edu.isi.karma.services.entityExtraction;

import java.util.List;

public class Extractions {
	List<Extraction> people;
	List<Extraction> places;
	List<Extraction>dates;
	
	public void setPeople(List<Extraction>ext) {
		this.people = ext;
	}
	
	public List<Extraction> getPeople() {
		return this.people;
	}
	
	public void setPlaces(List<Extraction> ext) {
		this.places = ext;
	}

	public List<Extraction> getPlaces() {
		return this.places;
	}
	
	public void setDates(List<Extraction> ext) {
		this.dates = ext;
	}

	public List<Extraction> getDates() {
		return this.dates;
	}


}
