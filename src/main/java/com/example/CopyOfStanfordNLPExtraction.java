package com.example;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import edu.stanford.nlp.ie.AbstractSequenceClassifier;
import edu.stanford.nlp.ie.crf.CRFClassifier;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;

public class CopyOfStanfordNLPExtraction {

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
			// String stanMarkedOutput =
			// classifier.classifyWithInlineXML(paragraph.getText());

			int i = 0;
			for (List<CoreLabel> lcl : classifier.classify(paragraph.getText())) {
				String currentAnnotation = "";
				StringBuffer resultString = new StringBuffer();
				resultString.setLength(0);

				for (CoreLabel cl : lcl) {
					//System.out.println(i++ + ":");
					System.out.println(cl);
					System.out.print(cl.get(CoreAnnotations.AnswerAnnotation.class));

					String labelAnnotation = cl
							.get(CoreAnnotations.AnswerAnnotation.class);

					if (labelAnnotation.equals("PERSON")) {
						if (labelAnnotation.equals(currentAnnotation)) {
							resultString.append(cl + " ");
						} else {
							if (resultString.length()!=0) {
								Extraction ext = new Extraction();
								ext.setExtraction(resultString.toString());
								System.out.println("Extraction created: "+resultString.toString());
								
								if(currentAnnotation.equals("PERSON"))
									people.add(ext);
								else if(currentAnnotation.equals("LOCATION"))
									places.add(ext);
								else if(currentAnnotation.equals("DATE"))
									dates.add(ext);
							}
						
							// reset resultString
							resultString.setLength(0);
							// set new Annotation as current Annotation
							currentAnnotation = labelAnnotation;
							resultString.append(cl + " ");
							System.out.println("current result="+resultString.toString());
						}
					}
					// I was walking in Los Angeles California 27th May, 2013 , when on March 17, 2013 Michael Jordan became a champion.
					else if (labelAnnotation.equals("LOCATION")) {
						if (labelAnnotation.equals(currentAnnotation)) {
							resultString.append(cl + " ");
						} else {
							if (resultString.length()!=0) {
								Extraction ext = new Extraction();
								ext.setExtraction(resultString.toString());
								System.out.println("Extraction created: "+resultString.toString());
								
								if(currentAnnotation.equals("PERSON"))
									people.add(ext);
								else if(currentAnnotation.equals("LOCATION"))
									places.add(ext);
								else if(currentAnnotation.equals("DATE"))
									dates.add(ext);
							}
							// reset resultString
							resultString.setLength(0);
							// set new Annotation as current Annotation
							currentAnnotation = labelAnnotation;
							resultString.append(cl + " ");
							System.out.println("current result="+resultString.toString());
						}
					}

					else if (labelAnnotation.equals("DATE")) {
						if (labelAnnotation.equals(currentAnnotation)) {
							resultString.append(cl + " ");
						} else {
							if (resultString.length()!=0) {
								Extraction ext = new Extraction();
								ext.setExtraction(resultString.toString());
								System.out.println("Extraction created: "+resultString.toString());
								
								if(currentAnnotation.equals("PERSON"))
									people.add(ext);
								else if(currentAnnotation.equals("LOCATION"))
									places.add(ext);
								else if(currentAnnotation.equals("DATE"))
									dates.add(ext);
							}
							
							// reset resultString
							resultString.setLength(0);
							// set new Annotation as current Annotation
							currentAnnotation = labelAnnotation;
							resultString.append(cl + " ");
							System.out.println("current result="+resultString.toString());
						}
					}

					else {
						currentAnnotation = "";
						resultString.setLength(0);
					}

				}
			}

			e.setPeople(people);
			e.setDates(places);
			e.setPlaces(dates);

			outputExt.setRowId(paragraph.getRowId());
			outputExt.setExtractions(e);

			output.add(outputExt);
		}

		return output;
	}
}
