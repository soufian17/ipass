package nl.hu.student.soufian.iPass.webservice;

import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

import javax.ws.rs.Produces;

import nl.hu.student.soufian.iPass.model.Locatie;
import nl.hu.student.soufian.iPass.persistence.LocatieDAO;

@Path("locatie")
public class LocatieResource {
	
	//returnt alle locaties in json uit de database.
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
	@Path("add")
	@POST
	@RolesAllowed("admin")
	public String addLocatie(
			@FormParam("postcode")String postcode,
			@FormParam("plaatsnaam")String plaatsnaam,
			@FormParam("adres")String adres
			){
		if(postcode=="" || plaatsnaam=="" || adres=="" || postcode==null || plaatsnaam==null || adres==null){
			return "Doorgegeven waardes kloppen niet!";
		}else{
		LocatieDAO ldao = new LocatieDAO();
		if(ldao.addLocatie(postcode,plaatsnaam,adres)){
		return "Succes!";
		}else{
			return "Niet gelukt!";
		}
		}
	}
}
