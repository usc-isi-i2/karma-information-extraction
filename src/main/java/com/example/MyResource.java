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
	
	/**
	 * @return String that will be returned as a text/plain response.
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	
	public List<OutputExtraction> performExt(List<InputExtraction> input) {
		
		return new StanfordNLPExtraction().performExtraction(input);
		
	}

	
	
	}