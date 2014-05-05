package com.karma.extractionservice;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import edu.stanford.nlp.ie.AbstractSequenceClassifier;
import edu.stanford.nlp.ie.crf.CRFClassifier;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;

public class Copy_of_StanfordNLPExtraction_Less_Greater_Symbol_Logic {

	public List<OutputExtraction> performExtraction(List<InputExtraction> input) {

		String serializedClassifier = "classifiers/english.muc.7class.distsim.crf.ser.gz";

		AbstractSequenceClassifier<CoreLabel> classifier = CRFClassifier
				.getClassifierNoExceptions(serializedClassifier);

		List<OutputExtraction> output = new ArrayList<OutputExtraction>();

		String s1 = "Good afternoon Rajat Raina, how are you today?";
		String s2 = "I go to school at Stanford University, which is located in California. March 17, 2013";
		//System.out.println(classifier.classifyToString(s1));
		System.out.println(classifier.classifyWithInlineXML(s2));
		//System.out.println(classifier.classifyToString(s2, "xml", true));

		for (InputExtraction paragraph : input) {
			OutputExtraction outputExt = new OutputExtraction();
			Extractions e = new Extractions();

			List<Extraction> people = new ArrayList<Extraction>();
			List<Extraction> places = new ArrayList<Extraction>();
			List<Extraction> dates = new ArrayList<Extraction>();
			String stanMarkedOutput = classifier.classifyWithInlineXML(paragraph.getText());
			
			int current = 0;
			
			while(current<stanMarkedOutput.length()) {
				while(current<stanMarkedOutput.length() && stanMarkedOutput.charAt(current) != '<')
					current++;
				//start from character next to <
				current++;
				StringBuffer type = new StringBuffer();
				
				while(current<stanMarkedOutput.length()) {
					char currChar = stanMarkedOutput.charAt(current);
					if(currChar == '>')
						break;
					current++;
					type.append(currChar);
				}
				System.out.println(type.toString());
				
				//start from character next to >
				current++;
				StringBuffer content = new StringBuffer();
				
				while(current<stanMarkedOutput.length()) {
					char currChar = stanMarkedOutput.charAt(current);
					if(currChar == '<')
						break;
					current++;
					content.append(currChar);
				}
				
				//start from character next to <
				current++;
				
				System.out.println(content.toString());
				
				if(type.toString().equals("PERSON")) {
					Extraction ext = new Extraction();
					ext.setExtraction(content.toString());
					people.add(ext);
				}
				
				else if(type.toString().equals("LOCATION")) {
					Extraction ext = new Extraction();
					ext.setExtraction(content.toString());
					places.add(ext);
				}
				else if(type.toString().equals("DATE")) {
					Extraction ext = new Extraction();
					ext.setExtraction(content.toString());
					dates.add(ext);
				}

				//reset type and content
				type.setLength(0);
				content.setLength(0);
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
