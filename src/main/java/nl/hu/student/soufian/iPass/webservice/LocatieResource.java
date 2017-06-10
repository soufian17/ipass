package nl.hu.student.soufian.iPass.webservice;

import java.util.List;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nl.hu.student.soufian.iPass.model.Locatie;
import nl.hu.student.soufian.iPass.persistence.LocatieDAO;

@Path("locatie")
public class LocatieResource {
	
	@Path("all")
	@GET
	@Produces("application/json")
	public String AllLocaties(){
		JsonArrayBuilder JsonArrayBuilder = Json.createArrayBuilder();
		LocatieDAO ldao = new LocatieDAO();
		List<Locatie> allelocaties = ldao.allelocaties();
		for(Locatie locatie : allelocaties){
			JsonObjectBuilder JOB = Json.createObjectBuilder();
			JOB.add("adres",		locatie.getAdres_());
			JOB.add("plaatsnaam",locatie.getPlaatsnaam_());
			JOB.add("locatieid",locatie.getLocatieID());
			JsonArrayBuilder.add(JOB);
		}
		String jsonStr = JsonArrayBuilder.build().toString();
		return jsonStr;
	}
}
