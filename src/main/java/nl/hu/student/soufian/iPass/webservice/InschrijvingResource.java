package nl.hu.student.soufian.iPass.webservice;

import java.util.ArrayList;
import java.util.List;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import org.glassfish.hk2.utilities.binding.AbstractBinder;

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
				@FormParam("locatie")int locatieid
			){
		InschrijvingDAO insdao = new InschrijvingDAO();
		KlantDAO kdao = new KlantDAO();
		LocatieDAO ldao = new LocatieDAO();
		AbonnementDAO adao = new AbonnementDAO();
		Klant klant = new Klant(voornaam, achternaam, bankrek, telefoonnummer, woonplaats, adres, mail);
		kdao.AddKlant(klant);				//Query
		
		Locatie locatie = ldao.findLocatieById(locatieid);		//Query
		Abonnement abbo = adao.findAbonnement(abbosoort);		//Query
		Klant klanterin = kdao.findKlant(voornaam,achternaam,bankrek);		//Query

		Inschrijving inschrijving = new Inschrijving(klanterin,locatie,abbo);
		if(insdao.SaveInschrijving(inschrijving) != null);									//Query
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
			JOB.add("locatieid", l.getLocatieID());
			JsonArrayBuilder.add(JOB);
		}
		String JsonStr = JsonArrayBuilder.build().toString();
		return JsonStr;
	}
	@DELETE
	@Path("verwijder/{id}")
	public String verwijderLid(@PathParam("id")int id){
		InschrijvingDAO idao = new InschrijvingDAO();
		if(idao.removeInschrijving(id)){
		return "Lid met ID:'"+id+"' succesvol verwijderd!";
		}else{
			return "Er is een error opgetreden.\n Neem contact op met uw geweldige ICT'er.";
		}
	}
	@GET
	@Path("getById/{id}")
	@Produces("application/json")
	public String getById(@PathParam("id")int id){
		InschrijvingDAO idao = new InschrijvingDAO();
		AbonnementDAO adao = new AbonnementDAO();
		LocatieDAO ldao = new LocatieDAO();
		
		Inschrijving inschrijving = idao.FindInschrijving(id);
		JsonObjectBuilder JOB = Json.createObjectBuilder();
		
		Abonnement a = inschrijving.getAbbonement_();
		Klant k = inschrijving.getKlant();
		Locatie l = inschrijving.getLocatie();
		
		List<Abonnement> restabbo = adao.getRestAbbonement(a.getAbbonementnaam());
		List<Locatie> restloc = ldao.getRestLocaties(l.getLocatieID());
		
		JsonArrayBuilder JAB = Json.createArrayBuilder();
		JsonArrayBuilder JAB2 = Json.createArrayBuilder();
		
		for (Abonnement ab : restabbo){
			JAB.add(ab.getAbbonementnaam()+" - "+ab.getPrijs());
		}
		for (Locatie lo : restloc){
			JsonObjectBuilder JOBv1 = Json.createObjectBuilder();
			JOBv1.add("locnaam",lo.getAdres_()+" - "+lo.getPlaatsnaam_());
			JOBv1.add("locid", lo.getLocatieID());
			JAB2.add(JOBv1);
		}
		
		JOB.add("id", k.getId_());
		JOB.add("voornaam", k.getVoornaam_());
		JOB.add("achternaam", k.getAchternaam_());
		JOB.add("bankrekeningnummer", k.getBankrekeningnummer_());
		JOB.add("telefoonnummer", k.getTelefoonnummer_());
		JOB.add("email", k.getMail_()); 
		JOB.add("woonplaats", k.getWoonplaats_());
		JOB.add("adres", k.getAdres_());
		
		JOB.add("abonnement", a.getAbbonementnaam()+" - "+a.getPrijs());
		JOB.add("locatie", l.getPlaatsnaam_()+" - "+l.getAdres_());
		JOB.add("locatieid", l.getLocatieID());
		JOB.add("restabonnementen", JAB);
		JOB.add("restlocaties", JAB2);
		
		String JsonStr = JOB.build().toString();
		return JsonStr;
	}

	@PUT
	@Path("update/{id}")
	public String updateInschrijving(
			@PathParam("id")int id,
			@FormParam("voornaam")String voornaam,
			@FormParam("achternaam")String achternaam,
			@FormParam("bankrekeningnummer")String bankrek,
			@FormParam("telefoonnummer")String telefoonnummer,
			@FormParam("email")String mail,
			@FormParam("woonplaats")String woonplaats,
			@FormParam("adres")String adres,
			@FormParam("abonnementnaam")String abbosoort,
			@FormParam("locatie")int locatieInt){
		
		InschrijvingDAO idao = new InschrijvingDAO();
		KlantDAO kdao = new KlantDAO();
		if(kdao.updateKlant(id,voornaam,achternaam,bankrek,telefoonnummer,mail,woonplaats,adres)){
			if(idao.updateInschrijving(idao.FindInschrijving(id),abbosoort,locatieInt)){
				return "Lid met ID: '"+id+"' succesvol geüpdatet!";
			}else{
					return "Er is een error opgetreden.\n Neem contact op met uw geweldige ICT'er.";
				}
		}else{
			return "Er is een error opgetreden.\n Neem contact op met uw magnefieke ICT'er.\n Klant is geüpdatet";
		}
	}

		
}
