package edu.isi.karma.services.entityExtraction.openNLP;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import edu.isi.karma.services.entityExtraction.Extraction;
import edu.isi.karma.services.entityExtraction.Extractions;
import edu.isi.karma.services.entityExtraction.InputExtraction;
import edu.isi.karma.services.entityExtraction.OutputExtraction;
import opennlp.tools.namefind.NameFinderME;
import opennlp.tools.namefind.TokenNameFinderModel;
import opennlp.tools.util.InvalidFormatException;
import opennlp.tools.util.Span;

public class OpenNLPExtraction {
	
	NameFinderME nameDetector = null, locationDetector = null, dateDetector = null;
		
	InputStream modelIn = null;
	TokenNameFinderModel personModel = null, locationModel = null, dateModel = null; 
		
	public List<OutputExtraction> performExtraction(List<InputExtraction> input) {
		try {
			// Need to give a local path on disk. Might break if the app folder is moved.
			InputStream modelIn = new FileInputStream("/home/vishal/apache-tomcat-7.0.53/webapps/ExtractionService/resources/en-ner-person.bin");
			
			personModel = new TokenNameFinderModel(modelIn);
			modelIn.close();
			
			nameDetector = new NameFinderME(personModel);

			// Need to give a local path on disk. Might break if the app folder is moved.
			modelIn =  new FileInputStream("/home/vishal/apache-tomcat-7.0.53/webapps/ExtractionService/resources/en-ner-location.bin");		
			
			locationModel = new TokenNameFinderModel(modelIn);
			modelIn.close();
			
			locationDetector = new NameFinderME(locationModel);
			
			//Need to give a local path on disk. Might break if the app folder is moved.
			modelIn =  new FileInputStream("/home/vishal/apache-tomcat-7.0.53/webapps/ExtractionService/resources/en-ner-date.bin");
			
			dateModel = new TokenNameFinderModel(modelIn);
			modelIn.close();
			
			dateDetector = new NameFinderME(dateModel);
		} catch (InvalidFormatException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
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
		Span names[] = null;
		Set<Extraction> people = new HashSet<Extraction>();

		String[] st = paragraph.getText().split("\\s");
		names = nameDetector.find(st);
					
		String[] namesStr = Span.spansToStrings(names, st);
		
		for (String str : namesStr) {
			Extraction e = new Extraction();
			e.setExtraction(str);
			people.add(e);
		}
			
		return new ArrayList<Extraction>(people);
	}
	
	public List<Extraction> findPlaces(InputExtraction paragraph) {
		Span names[] = null;
		Set<Extraction> places = new HashSet<Extraction>();

			String[] st = paragraph.getText().split("\\s");
			names = locationDetector.find(st);
			
			String[] namesStr = Span.spansToStrings(names, st);

			for (String str : namesStr) {
				Extraction e = new Extraction();
				e.setExtraction(str);
				places.add(e);
			}

		return new ArrayList<Extraction>(places);
	}
	
	public List<Extraction> findDates(InputExtraction paragraph) {
		Span names[] = null;
		Set<Extraction> dates = new HashSet<Extraction>();

			String[] st = paragraph.getText().split("\\s");
			names = dateDetector.find(st);
			
			String[] namesStr = Span.spansToStrings(names, st);

			for (String str : namesStr) {
				Extraction e = new Extraction();
				e.setExtraction(str);
				dates.add(e);
			}

		return new ArrayList<Extraction>(dates);
	}
}