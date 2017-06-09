//package nl.hu.student.soufian.iPass.webservice;
//
//
//import java.util.List;
//
//import javax.json.*;
//import javax.ws.rs.GET;
//import javax.ws.rs.Path;
//import javax.ws.rs.Produces;
//
//import nl.hu.student.soufian.iPass.model.Klant;
//import nl.hu.student.soufian.iPass.persistence.KlantDAO;
//
//@Path("lid")
//public class KlantResource {
//	@GET
//	@Path("/all")
//	@Produces("application/json")
//	public String getAll(){
//		KlantDAO kdao = new KlantDAO();
//		List<Klant> alleleden = kdao.getAlleKlanten();
//		JsonArrayBuilder JsonArrayBuilder = Json.createArrayBuilder();
//		for(Klant lid : alleleden){
//			JsonObjectBuilder JOB = Json.createObjectBuilder();
//			JOB.add("id", lid.getId_());
//			JOB.add("voornaam", lid.getVoornaam_());
//			JOB.add("achternaam", lid.getAchternaam_());
//			JOB.add("abonnement", lid.get);
//			JOB.add("locatie", arg1);
//		}
//		return "";
//	}
//}
