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

import nl.hu.student.soufian.iPass.model.Abonnement;
import nl.hu.student.soufian.iPass.persistence.AbonnementDAO;

@Path("abonnement")
public class AbonnementResource {
	//Stopt elk object in een JSON object en stopt deze JSON objecten in een JSON array.
	@GET
	@Path("all")
	@Produces("application/json")
	public String GetAllAbonnementen(){
		JsonArrayBuilder JsonArrayBuilder = Json.createArrayBuilder();
		AbonnementDAO adao = new AbonnementDAO();
		List<Abonnement> alleabonnementen = adao.getAllAbbonementen();

		for(Abonnement abbo : alleabonnementen){
			JsonObjectBuilder JOB = Json.createObjectBuilder();
			JOB.add("naam",abbo.getAbbonementnaam());
			JOB.add("prijs", "€ "+abbo.getPrijs());
			JsonArrayBuilder.add(JOB);
		}
		String jsonStr = JsonArrayBuilder.build().toString();

		return jsonStr;
	}
	@Path("add")
	@POST
	@RolesAllowed("admin")
	public String addAbonnement(
			@FormParam("naam")String naam,
			@FormParam("prijs")double prijs
			){
		if(prijs<=0.0 || naam=="" || naam==null){
			return "Waarde is niet geldig";
		}else{
		AbonnementDAO adao = new AbonnementDAO();
		if(adao.addAbonnement(naam, prijs)){
		return "Succes!";
		}else{
			return "Niet gelukt!";
		}
		}
	}
}
