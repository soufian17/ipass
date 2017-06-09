package nl.hu.student.soufian.iPass.webservice;

import java.util.ArrayList;
import java.util.List;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nl.hu.student.soufian.iPass.model.Abonnement;
import nl.hu.student.soufian.iPass.model.Inschrijving;
import nl.hu.student.soufian.iPass.model.Klant;
import nl.hu.student.soufian.iPass.model.Locatie;
import nl.hu.student.soufian.iPass.persistence.AbonnementDAO;
import nl.hu.student.soufian.iPass.persistence.InschrijvingDAO;
import nl.hu.student.soufian.iPass.persistence.KlantDAO;
import nl.hu.student.soufian.iPass.persistence.LocatieDAO;
@Path("inschrijvingen")
public class InschrijvingResource {
	
	@Path("add")
	@POST
	public String SaveInschrijving(
				@FormParam("voornaam")String voornaam,
				@FormParam("achternaam")String achternaam,
				@FormParam("bankrekeningnummer")String bankrek,
				@FormParam("telefoonnummer")String telefoonnummer,
				@FormParam("email")String mail,
				@FormParam("woonplaats")String woonplaats,
				@FormParam("adres")String adres,
				@FormParam("abonnementnaam")String abbosoort,
				@FormParam("locatie")String locatieStr
			){
		InschrijvingDAO insdao = new InschrijvingDAO();
		KlantDAO kdao = new KlantDAO();
		LocatieDAO ldao = new LocatieDAO();
		AbonnementDAO adao = new AbonnementDAO();
		Klant klant = new Klant(voornaam, achternaam, bankrek, telefoonnummer, woonplaats, adres, mail);
		kdao.AddKlant(klant);				//Query
		
		
		Locatie locatie = ldao.findLocatie(locatieStr);		//Query
		Abonnement abbo = adao.findAbonnement(abbosoort);		//Query
		Klant klanterin = kdao.findKlant(voornaam,achternaam,bankrek);		//Query

		Inschrijving inschrijving = new Inschrijving(klanterin,locatie,abbo);
		insdao.SaveInschrijving(inschrijving);									//Query
		return "lid succesvol toegevoegd!";
	}
	@GET
	@Path("/all")
	@Produces("application/json")
	public String getAll(){
		InschrijvingDAO idao = new InschrijvingDAO();
		List<Inschrijving> alleleden = idao.getAlleInschrijvingen();
		JsonArrayBuilder JsonArrayBuilder = Json.createArrayBuilder();
		for(Inschrijving lid : alleleden){
			JsonObjectBuilder JOB = Json.createObjectBuilder();
			Abonnement a = lid.getAbbonement_();
			Klant k = lid.getKlant();
			Locatie l = lid.getLocatie();
			JOB.add("id", k.getId_());
			JOB.add("voornaam", k.getVoornaam_());
			JOB.add("achternaam", k.getAchternaam_());
			JOB.add("abonnement", a.getAbbonementnaam());
			JOB.add("locatie", l.getPlaatsnaam_()+" - "+l.getAdres_());
			JsonArrayBuilder.add(JOB);
		}
		String JsonStr = JsonArrayBuilder.build().toString();
		return JsonStr;
	}

}
