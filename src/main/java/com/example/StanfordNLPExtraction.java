package com.example;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import edu.stanford.nlp.ie.AbstractSequenceClassifier;
import edu.stanford.nlp.ie.crf.CRFClassifier;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;

public class StanfordNLPExtraction {

	public List<OutputExtraction> performExtraction(List<InputExtraction> input) {

		String serializedClassifier = "classifiers/english.muc.7class.distsim.crf.ser.gz";

		AbstractSequenceClassifier<CoreLabel> classifier = CRFClassifier
				.getClassifierNoExceptions(serializedClassifier);

		List<OutputExtraction> output = new ArrayList<OutputExtraction>();

		String s1 = "Good afternoon Rajat Raina, how are you today?";
		String s2 = "I go to school at Stanford University, which is located in California. March 17, 2013";
		//System.out.println(classifier.classifyToString(s1));
		//System.out.println(classifier.classifyWithInlineXML(s2));
		//System.out.println(classifier.classifyToString(s2, "xml", true));

		for (InputExtraction paragraph : input) {
			OutputExtraction outputExt = new OutputExtraction();
			Extractions e = new Extractions();

			List<Extraction> people = new ArrayList<Extraction>();
			List<Extraction> places = new ArrayList<Extraction>();
			List<Extraction> dates = new ArrayList<Extraction>();
			String stanMarkedOutput = classifier.classifyWithInlineXML(paragraph.getText());
			System.out.println(stanMarkedOutput);
			
			String peopleRegEx = "<PERSON>(.+?)</PERSON>";
			Pattern p = Pattern.compile(peopleRegEx);
			Matcher m = p.matcher(stanMarkedOutput);
			
			while(m.find()) {
				String person = m.group(1);
				
				Extraction ext = new Extraction();
				ext.setExtraction(person);
				people.add(ext);
			}
			
			String locationRegEx = "<LOCATION>(.+?)</LOCATION>";
			p = Pattern.compile(locationRegEx);
			m = p.matcher(stanMarkedOutput);
			
			while(m.find()) {
				String location = m.group(1);
				
				Extraction ext = new Extraction();
				ext.setExtraction(location);
				places.add(ext);
			}
			
			String dateRegEx = "<DATE>(.+?)</DATE>";
			p = Pattern.compile(dateRegEx);
			m = p.matcher(stanMarkedOutput);
			
			while(m.find()) {
				String date = m.group(1);
				
				Extraction ext = new Extraction();
				ext.setExtraction(date);
				dates.add(ext);
			}
			
			
			e.setPeople(people);
			e.setPlaces(places);
			e.setDates(dates);

			outputExt.setRowId(paragraph.getRowId());
			outputExt.setExtractions(e);

			output.add(outputExt);
		}

		return output;
	}
}
