package edu.isi.karma.services.entityExtraction;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.google.gson.Gson;

import edu.isi.karma.services.entityExtraction.stanfordNLP.StanfordCoreNLPService;


public class Service {
	public String execute(String data) throws IOException {
		StanfordCoreNLPService service = new StanfordCoreNLPService();
		
		JSONArray json = new JSONArray(data);
		List<InputExtraction> inputExt = new ArrayList<InputExtraction>();
		for(int i=0; i<json.length(); i++) {
			JSONObject input = json.getJSONObject(i);
			InputExtraction ie = new InputExtraction();
			ie.rowId = input.getString("rowId");
			ie.text = input.getString("text");
			inputExt.add(ie);
		}

		List<OutputExtraction> result = service.performExt(inputExt);
		
		Gson writer = new Gson();
		String output = writer.toJson(result);
		return output;
	}
}
