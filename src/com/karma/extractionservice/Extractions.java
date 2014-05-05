package com.karma.extractionservice;

import java.util.List;

public class Extractions {
	List<String> people;
	List<String> places;
	List<String> dates;
	
	public void setPeople(List<String> ext) {
		this.people = ext;
	}
	
	public List<String> getPeople() {
		return this.people;
	}
	
	public void setPlaces(List<String> ext) {
		this.places = ext;
	}

	public List<String> getPlaces() {
		return this.places;
	}
	
	public void setDates(List<String> ext) {
		this.dates = ext;
	}

	public List<String> getDates() {
		return this.dates;
	}


}
