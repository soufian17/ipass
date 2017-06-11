package nl.hu.student.soufian.iPass.webservice;

import java.util.List;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nl.hu.student.soufian.iPass.model.Abonnement;
import nl.hu.student.soufian.iPass.persistence.AbonnementDAO;

@Path("abonnement")
public class AbonnementResource {
	
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
}
