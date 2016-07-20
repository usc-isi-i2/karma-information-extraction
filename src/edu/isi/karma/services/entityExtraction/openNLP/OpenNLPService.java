package edu.isi.karma.services.entityExtraction.openNLP;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.json.JSONArray;
import org.json.JSONObject;

import edu.isi.karma.services.entityExtraction.IExtractionService;
import edu.isi.karma.services.entityExtraction.InputExtraction;
import edu.isi.karma.services.entityExtraction.OutputExtraction;

/**
 * Root resource (exposed at "Extract" path)
 */
@Path("OpenNLP")
public class OpenNLPService implements IExtractionService {	
	/**
	 * @return String that will be returned as a text/plain response.
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Override
	public List<OutputExtraction> performExt(List<InputExtraction> input) {
		return new OpenNLPExtraction().performExtraction(input);
	}
	
	@GET
	@Path("/getCapabilities")
    	@Produces({"application/json"})
	public Response getCapabilities() {
		ResponseBuilder respBuild = null;

		try {
			JSONObject people = new JSONObject();
			people.put("capability", "People");

			JSONObject places = new JSONObject();
			places.put("capability", "Places");

			JSONObject dates = new JSONObject();
			dates.put("capability", "Dates");

			JSONArray jsonRes = new JSONArray();
			jsonRes.put(people);
			jsonRes.put(places);
			jsonRes.put(dates);

			String output = jsonRes.toString();

			respBuild = Response.status(200);
			respBuild.entity(output);
			respBuild.header("Access-Control-Allow-Origin", "*");
		} catch (Exception e) {
			e.printStackTrace();
			respBuild = Response.status(500);
			return respBuild.build();
		}

		return respBuild.build();
	}	
}
