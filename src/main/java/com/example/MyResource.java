package com.example;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import opennlp.tools.namefind.NameFinderME;
import opennlp.tools.namefind.TokenNameFinderModel;
import opennlp.tools.util.InvalidFormatException;
import opennlp.tools.util.Span;

/**
 * Root resource (exposed at "Extract" path)
 */
@Path("myresource")
public class MyResource {

	//mvn clean compile exec:java
	// Loading person name detection model
	NameFinderME nameDetector = null, locationDetector = null, dateDetector = null;
	
	InputStream modelIn = null;
	TokenNameFinderModel personModel = null, locationModel = null, dateModel = null; 
	
	/**
	 * @return String that will be returned as a text/plain response.
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	
	public List<OutputExtraction> performExt(List<InputExtraction> input) {
		
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

			outputExt.setRowHash(paragraph.getRowHash());
			outputExt.setExtractions(e);
			
			output.add(outputExt);
		}

		return output;
	}

	
	public List<Extraction> findPeople(InputExtraction paragraph) {
		System.out.println("Service called");
		System.out.println(paragraph.getText()+" "+paragraph.getRowHash());
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