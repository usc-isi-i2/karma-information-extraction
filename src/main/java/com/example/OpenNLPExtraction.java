package com.example;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import opennlp.tools.namefind.NameFinderME;
import opennlp.tools.namefind.TokenNameFinderModel;
import opennlp.tools.util.InvalidFormatException;
import opennlp.tools.util.Span;

public class OpenNLPExtraction {
	
	// Loading person name detection model
		NameFinderME nameDetector = null, locationDetector = null, dateDetector = null;
		
		InputStream modelIn = null;
		TokenNameFinderModel personModel = null, locationModel = null, dateModel = null; 
		
	public List<OutputExtraction> performExtraction(List<InputExtraction> input) {
		
		modelIn = getClass().getResourceAsStream("/en-ner-person.bin");
		try {
			personModel = new TokenNameFinderModel(
					modelIn);
			
			modelIn.close();
			
			nameDetector = new NameFinderME(personModel);

			modelIn = getClass().getResourceAsStream("/en-ner-location.bin");
					
			locationModel = new TokenNameFinderModel(
					modelIn);
			
			modelIn.close();
			
			locationDetector = new NameFinderME(locationModel);
			
			modelIn = getClass().getResourceAsStream("/en-ner-date.bin");
			
			dateModel = new TokenNameFinderModel(
					modelIn);
			
			modelIn.close();
			
			dateDetector = new NameFinderME(dateModel);
			
			
		} catch (InvalidFormatException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		List<OutputExtraction> output = new ArrayList<OutputExtraction>();

		for (InputExtraction paragraph : input) {
			OutputExtraction outputExt = new OutputExtraction();
			Extractions e = new Extractions();
			
			e.setPeople(findPeople(paragraph));
			e.setDates(findDates(paragraph));
			e.setPlaces(findPlaces(paragraph));

			outputExt.setRowId(paragraph.getRowId());
			outputExt.setExtractions(e);
			
			output.add(outputExt);
		}
		
		return output;
	}

	public List<Extraction> findPeople(InputExtraction paragraph) {
		System.out.println("Service called");
		System.out.println(paragraph.getText()+" "+paragraph.getRowId());
		Span names[] = null;
		List<Extraction> people = new ArrayList<Extraction>();

			String[] st = paragraph.text.split("\\s");
			names = nameDetector.find(st);
			
			System.out.println("reached here");
			
			String[] namesStr = Span.spansToStrings(names, st);
			
			for (String str : namesStr) {
				Extraction person = new Extraction();
				person.setExtraction(str);
				people.add(person);
			}
			
		return people;
	}
	
	public List<Extraction> findPlaces(InputExtraction paragraph) {
		Span names[] = null;
		List<Extraction> places = new ArrayList<Extraction>();

			String[] st = paragraph.text.split("\\s");
			names = locationDetector.find(st);
			
			String[] namesStr = Span.spansToStrings(names, st);

			for (String str : namesStr) {
				Extraction person = new Extraction();
				person.setExtraction(str);
				places.add(person);
			}

		return places;
	}
	
	public List<Extraction> findDates(InputExtraction paragraph) {
		Span names[] = null;
		List<Extraction> dates = new ArrayList<Extraction>();

			String[] st = paragraph.text.split("\\s");
			names = dateDetector.find(st);
			
			String[] namesStr = Span.spansToStrings(names, st);

			for (String str : namesStr) {
				Extraction date = new Extraction();
				date.setExtraction(str);
				dates.add(date);
			}


		return dates;
	}

}
