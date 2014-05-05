package com.karma.extractionservice;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import edu.stanford.nlp.ie.AbstractSequenceClassifier;
import edu.stanford.nlp.ie.crf.CRFClassifier;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;

public class StanfordNLPExtraction {

	public List<OutputExtraction> performExtraction(List<InputExtraction> input) {

		//Need to give a local path on disk. Might break if the app folder is moved.
		//String serializedClassifier = "H:/Trojan/Tomcat7/wtpwebapps/ExtractionService/classifiers/english.muc.7class.distsim.crf.ser.gz";
		String serializedClassifier = "/home/vishal/apache-tomcat-7.0.53/webapps/ExtractionService/classifiers/english.muc.7class.distsim.crf.ser.gz";
		
		AbstractSequenceClassifier<CoreLabel> classifier = CRFClassifier
				.getClassifierNoExceptions(serializedClassifier);

		List<OutputExtraction> output = new ArrayList<OutputExtraction>();

		for (InputExtraction paragraph : input) {
			OutputExtraction outputExt = new OutputExtraction();
			Extractions e = new Extractions();

			Set<String> people = new HashSet<String>();
			Set<String> places = new HashSet<String>();
			Set<String> dates = new HashSet<String>();
			
			String stanMarkedOutput = classifier.classifyWithInlineXML(paragraph.getText());
			System.out.println(stanMarkedOutput);
			
			String peopleRegEx = "<PERSON>(.+?)</PERSON>";
			Pattern p = Pattern.compile(peopleRegEx);
			Matcher m = p.matcher(stanMarkedOutput);
			
			while(m.find()) {
				String person = m.group(1);				
				people.add(person);
			}
			
			String locationRegEx = "<LOCATION>(.+?)</LOCATION>";
			p = Pattern.compile(locationRegEx);
			m = p.matcher(stanMarkedOutput);
			
			while(m.find()) {
				String location = m.group(1);				
				places.add(location);
			}
			
			String dateRegEx = "<DATE>(.+?)</DATE>";
			p = Pattern.compile(dateRegEx);
			m = p.matcher(stanMarkedOutput);
			
			while(m.find()) {
				String date = m.group(1);
				dates.add(date);
			}
			
			
			e.setPeople(new ArrayList<String>(people));
			e.setPlaces(new ArrayList<String>(places));
			e.setDates(new ArrayList<String>(dates));

			outputExt.setRowId(paragraph.getRowId());
			outputExt.setExtractions(e);

			output.add(outputExt);
		}

		return output;
	}
}
