package com.karma.extractionservice;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import edu.stanford.nlp.ie.AbstractSequenceClassifier;
import edu.stanford.nlp.ie.crf.CRFClassifier;
import edu.stanford.nlp.ling.CoreLabel;

public class StanfordNLPExtraction {

	public List<OutputExtraction> performExtraction(List<InputExtraction> input) {

		String serializedClassifier = "english.muc.7class.distsim.crf.ser.gz";
		
		AbstractSequenceClassifier<CoreLabel> classifier = CRFClassifier
				.getClassifierNoExceptions(serializedClassifier);

		List<OutputExtraction> output = new ArrayList<OutputExtraction>();

		for (InputExtraction paragraph : input) {
			OutputExtraction outputExt = new OutputExtraction();
			Extractions e = new Extractions();

			Set<Extraction> people = new HashSet<Extraction>();
			Set<Extraction> places = new HashSet<Extraction>();
			Set<Extraction> dates = new HashSet<Extraction>();
			
			String stanMarkedOutput = classifier.classifyWithInlineXML(paragraph.getText());
			System.out.println(stanMarkedOutput);
			
			String peopleRegEx = "<PERSON>(.+?)</PERSON>";
			Pattern p = Pattern.compile(peopleRegEx);
			Matcher m = p.matcher(stanMarkedOutput);
			
			while(m.find()) {
				String person = m.group(1);
				Extraction e1 = new Extraction();
				e1.setExtraction(person);
				people.add(e1);
			}
			
			String locationRegEx = "<LOCATION>(.+?)</LOCATION>";
			p = Pattern.compile(locationRegEx);
			m = p.matcher(stanMarkedOutput);
			
			while(m.find()) {
				String location = m.group(1);	
				Extraction e1 = new Extraction();
				e1.setExtraction(location);
				places.add(e1);
			}
			
			String dateRegEx = "<DATE>(.+?)</DATE>";
			p = Pattern.compile(dateRegEx);
			m = p.matcher(stanMarkedOutput);
			
			while(m.find()) {
				String date = m.group(1);
				Extraction e1 = new Extraction();
				e1.setExtraction(date);
				dates.add(e1);
			}
			
			
			e.setPeople(new ArrayList<Extraction>(people));
			e.setPlaces(new ArrayList<Extraction>(places));
			e.setDates(new ArrayList<Extraction>(dates));

			outputExt.setRowId(paragraph.getRowId());
			outputExt.setExtractions(e);

			output.add(outputExt);
		}

		return output;
	}
	
	public static void main(String[] args) {
		StanfordNLPExtraction extractor = new StanfordNLPExtraction();
		ArrayList<InputExtraction> data = new ArrayList<InputExtraction>();
		InputExtraction row = new InputExtraction();
		row.setRowId("1");
		row.setText("There was Peter Thomas in the train");
		data.add(row);
		List<OutputExtraction> out = extractor.performExtraction(data);
		for(OutputExtraction output : out) {
			System.out.println(output);
		}
	}
}
