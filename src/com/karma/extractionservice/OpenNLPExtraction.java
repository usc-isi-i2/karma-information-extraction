package com.karma.extractionservice;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
		
		try {
			//Need to give a local path on disk. Might break if the app folder is moved.
			//InputStream modelIn = new FileInputStream("H:/Trojan/Tomcat7/wtpwebapps/ExtractionService/resources/en-ner-person.bin");
			InputStream modelIn = new FileInputStream("/home/vishal/apache-tomcat-7.0.53/webapps/ExtractionService/resources/en-ner-person.bin");
			
			personModel = new TokenNameFinderModel(
					modelIn);
			
			modelIn.close();
			
			nameDetector = new NameFinderME(personModel);

			//Need to give a local path on disk. Might break if the app folder is moved.
			//modelIn =  new FileInputStream("H:/Trojan/Tomcat7/wtpwebapps/ExtractionService/resources/en-ner-location.bin");
			modelIn =  new FileInputStream("/home/vishal/apache-tomcat-7.0.53/webapps/ExtractionService/resources/en-ner-location.bin");		
			
			locationModel = new TokenNameFinderModel(
					modelIn);
			
			modelIn.close();
			
			locationDetector = new NameFinderME(locationModel);
			
			//Need to give a local path on disk. Might break if the app folder is moved.
			//modelIn =  new FileInputStream("H:/Trojan/Tomcat7/wtpwebapps/ExtractionService/resources/en-ner-date.bin");
			modelIn =  new FileInputStream("/home/vishal/apache-tomcat-7.0.53/webapps/ExtractionService/resources/en-ner-date.bin");
			
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

	public List<String> findPeople(InputExtraction paragraph) {
		System.out.println("Service called");
		System.out.println(paragraph.getText()+" "+paragraph.getRowId());
		Span names[] = null;
		Set<String> people = new HashSet<String>();

			String[] st = paragraph.text.split("\\s");
			names = nameDetector.find(st);
			
			System.out.println("reached here");
			
			String[] namesStr = Span.spansToStrings(names, st);
			
			for (String str : namesStr) {
				people.add(str);
			}
			
		return new ArrayList<String>(people);
	}
	
	public List<String> findPlaces(InputExtraction paragraph) {
		Span names[] = null;
		Set<String> places = new HashSet<String>();

			String[] st = paragraph.text.split("\\s");
			names = locationDetector.find(st);
			
			String[] namesStr = Span.spansToStrings(names, st);

			for (String str : namesStr) {
				places.add(str);
			}

		return new ArrayList<String>(places);
	}
	
	public List<String> findDates(InputExtraction paragraph) {
		Span names[] = null;
		Set<String> dates = new HashSet<String>();

			String[] st = paragraph.text.split("\\s");
			names = dateDetector.find(st);
			
			String[] namesStr = Span.spansToStrings(names, st);

			for (String str : namesStr) {
				dates.add(str);
			}


		return new ArrayList<String>(dates);
	}

}