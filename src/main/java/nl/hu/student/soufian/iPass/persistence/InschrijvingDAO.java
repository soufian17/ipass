package nl.hu.student.soufian.iPass.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import nl.hu.student.soufian.iPass.model.Abonnement;
import nl.hu.student.soufian.iPass.model.Inschrijving;
import nl.hu.student.soufian.iPass.model.Klant;
import nl.hu.student.soufian.iPass.model.Locatie;


public class InschrijvingDAO extends BaseDAO{

		
	
	public boolean updateInschrijving(Inschrijving i,String naam_prijs, int locInt){
		try(Connection con = super.getConnection()){
			String Querry = "update inschrijving set typeabonnement=? , locatieID=? where klant_id=?";
			PreparedStatement ps = con.prepareStatement(Querry);
			LocatieDAO ldao = new LocatieDAO();
			String[] split = naam_prijs.split(" - ");
			String naam = split[0];
			String prijs = split[1];
			double prix = Double.parseDouble(prijs);
			Abonnement abonnement = new Abonnement(naam, prix);
			Locatie locatie = ldao.findLocatieById(locInt);
			ps.setString(1, abonnement.getAbbonementnaam());
			ps.setInt(2, locatie.getLocatieID());
			ps.setInt(3, i.getKlant().getId_());
			ps.executeUpdate();

			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
					
	}


	//voeg een inschrijving toe toe in de inschrijvingt table
	public boolean SaveInschrijving(int id, String abonnementnaam, String adres) {
		try(Connection con = super.getConnection()){
		String Querry = "insert into Inschrijving values (?,?,?)";
		PreparedStatement ps = con.prepareStatement(Querry);
		ps.setInt	(1, id);
		ps.setString(2, abonnementnaam);
		ps.setString(3, adres);
		ps.executeUpdate();
		return true;
		}catch(Exception ex){
			ex.printStackTrace();
			return false;
		}
	}
	public Inschrijving FindInschrijving(int id){
		try(Connection con = super.getConnection()){
			String Querry = "select * from inschrijving i join klant k on i.klant_id=k.id join locatie l on i.locatieid=l.locatieid join abonnement a on a.typeabonnement=i.typeabonnement where id=?";
			PreparedStatement ps = con.prepareStatement(Querry);
			ps.setInt	(1, id);
			ResultSet rs = ps.executeQuery();
			String voornaam;
			String achternaam;
			String bankrekeningnummer;
			String telefoonnummer;
			String woonplaatsklant;
			String adresklant;
			String email;
			
			String plaatsnaam;
			String postcode;
			String adres;
			int Locatieid;
			
			String abbonementnaam;
			double prijs;
			rs.next();
			voornaam = 			rs.getString("voornaam");
			achternaam = 		rs.getString("achternaam");
			id = 				rs.getInt("id");
			bankrekeningnummer =rs.getString("bankrekeningnummer");
			telefoonnummer =	rs.getString("telefoonnummer");
			woonplaatsklant = 	rs.getString("woonplaats");
			adresklant = 		rs.getString("adresklant");
			email = 			rs.getString("email");
			
			plaatsnaam = 		rs.getString("plaatsnaam");
			postcode = 			rs.getString("postcode");
			adres = 			rs.getString("adres");
			Locatieid = 		rs.getInt("locatieid");
			
			abbonementnaam = 	rs.getString("typeabonnement");
			prijs = 			rs.getDouble("prijs");
			Klant klant = new Klant(voornaam, achternaam, id, bankrekeningnummer, telefoonnummer, woonplaatsklant, adresklant, email);
			Locatie locatie = new Locatie(plaatsnaam, postcode, adres, Locatieid);
			Abonnement abonnement = new Abonnement(abbonementnaam, prijs);
			Inschrijving inschrijving = new Inschrijving(klant, locatie, abonnement);
			return inschrijving;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
		
	}
	public Inschrijving SaveInschrijving(Inschrijving inschrijving){
		try(Connection con = super.getConnection()){
			String Querry = "insert into Inschrijving values (?,?,?)";
			PreparedStatement ps = con.prepareStatement(Querry);
			ps.setInt	(1, inschrijving.getKlant().getId_());
			ps.setString(2, inschrijving.getAbbonement_().getAbbonementnaam());
			ps.setInt(3, inschrijving.getLocatie().getLocatieID());
			ps.executeUpdate();
			return inschrijving;
			}catch(Exception ex){
				ex.printStackTrace();
				return null;
			}
	}
	public boolean removeInschrijving(int id){
		try(Connection con = super.getConnection()){
		
		String Querry = "delete from inschrijving where klant_id=?";
		String Querry2 = "delete from klant where id = ?";
		
		PreparedStatement ps = con.prepareStatement(Querry);
		PreparedStatement ps2 = con.prepareStatement(Querry2);

		ps.setInt(1, id);
		ps2.setInt(1, id);

		ps.executeUpdate();
		ps2.executeUpdate();

		return true;
		}catch(Exception ex){
			return false;
		}

		
	}

	
	public ArrayList<Inschrijving> getAlleInschrijvingen()  {
		try(Connection con = super.getConnection()){
			Statement stmt = con.createStatement();
			ArrayList<Inschrijving> alleinschrijvingen= new ArrayList<Inschrijving>();
			
			String querrytest = "SELECT * FROM klant t JOIN inschrijving i ON t.ID = I.klant_ID JOIN locatie l on i.locatieid= l.locatieid JOIN abonnement a on i.typeabonnement = a.typeabonnement where i.typeabonnement is  not null";
			ResultSet rs = stmt.executeQuery(querrytest);
			int id;
			String voornaam;
			String achternaam;
			String bankrekeningnummer;
			String telefoonnummer;
			String woonplaatsklant;
			String adresklant;
			String email;
			
			String plaatsnaam;
			String postcode;
			String adres;
			int Locatieid;
			
			String abbonementnaam;
			double prijs;
			while (rs.next()) {   
				voornaam = 			rs.getString("voornaam");
				achternaam = 		rs.getString("achternaam");
				id = 				rs.getInt("id");
				bankrekeningnummer =rs.getString("bankrekeningnummer");
				telefoonnummer =	rs.getString("telefoonnummer");
				woonplaatsklant = 	rs.getString("woonplaats");
				adresklant = 		rs.getString("adresklant");
				email = 			rs.getString("email");
				
				plaatsnaam = 		rs.getString("plaatsnaam");
				postcode = 			rs.getString("postcode");
				adres = 			rs.getString("adres");
				Locatieid = 		rs.getInt("locatieid");
				
				abbonementnaam = 	rs.getString("typeabonnement");
				prijs = 			rs.getDouble("prijs");
				alleinschrijvingen.add(new Inschrijving(new Klant(voornaam, achternaam, id, bankrekeningnummer, telefoonnummer, woonplaatsklant, adresklant, email),new Locatie(plaatsnaam, postcode, adres, Locatieid), new Abonnement(abbonementnaam, prijs)));
			}
			stmt.close();
			con.close();
			return alleinschrijvingen;
		}catch(Exception ex){
			ex.printStackTrace();
			return null;
		}
	}


	public List<Inschrijving> getFilteredAchternaam(String filter) {
		try(Connection con = super.getConnection()){
		Statement stmt = con.createStatement();
		String cap = filter.substring(0, 1).toUpperCase() + filter.substring(1);
		String Querry = "SELECT * FROM klant t JOIN inschrijving i ON t.ID = I.klant_ID JOIN locatie l on i.locatieid= l.locatieid JOIN abonnement a on i.typeabonnement = a.typeabonnement WHERE achternaam Like '%"+filter+"%' or achternaam Like '"+filter+"%' or achternaam Like '%"+filter+"' or achternaam Like '%"+cap+"%' or achternaam Like '"+cap+"%' or achternaam Like '%"+cap+"'";
		ResultSet rs = stmt.executeQuery(Querry);
		int id;
		String voornaam;
		String achternaam;
		String bankrekeningnummer;
		String telefoonnummer;
		String woonplaatsklant;
		String adresklant;
		String email;
		
		String plaatsnaam;
		String postcode;
		String adres;
		int Locatieid;
		
		String abbonementnaam;
		double prijs;
		
		ArrayList<Inschrijving> alleinschrijvingen= new ArrayList<Inschrijving>();

		while (rs.next()) {   
			voornaam = 			rs.getString("voornaam");
			achternaam = 		rs.getString("achternaam");
			id = 				rs.getInt("id");
			bankrekeningnummer =rs.getString("bankrekeningnummer");
			telefoonnummer =	rs.getString("telefoonnummer");
			woonplaatsklant = 	rs.getString("woonplaats");
			adresklant = 		rs.getString("adresklant");
			email = 			rs.getString("email");
			
			plaatsnaam = 		rs.getString("plaatsnaam");
			postcode = 			rs.getString("postcode");
			adres = 			rs.getString("adres");
			Locatieid = 		rs.getInt("locatieid");
			
			abbonementnaam = 	rs.getString("typeabonnement");
			prijs = 			rs.getDouble("prijs");
			alleinschrijvingen.add(new Inschrijving(new Klant(voornaam, achternaam, id, bankrekeningnummer, telefoonnummer, woonplaatsklant, adresklant, email),new Locatie(plaatsnaam, postcode, adres, Locatieid), new Abonnement(abbonementnaam, prijs)));
		}
		stmt.close();
		con.close();
		return alleinschrijvingen;
	}catch(Exception ex){
		ex.printStackTrace();
		return null;
	}
	}
	
	public List<Inschrijving> getFilteredID(int filter) {
		try(Connection con = super.getConnection()){
		Statement stmt = con.createStatement();
		String Querry = "SELECT * FROM klant t JOIN inschrijving i ON t.ID = I.klant_ID JOIN locatie l on i.locatieid= l.locatieid JOIN abonnement a on i.typeabonnement = a.typeabonnement WHERE id Like '%"+filter+"%' or id Like '"+filter+"%' or id Like '%"+filter+"'";
		ResultSet rs = stmt.executeQuery(Querry);
		int id;
		String voornaam;
		String achternaam;
		String bankrekeningnummer;
		String telefoonnummer;
		String woonplaatsklant;
		String adresklant;
		String email;
		
		String plaatsnaam;
		String postcode;
		String adres;
		int Locatieid;
		
		String abbonementnaam;
		double prijs;
		
		ArrayList<Inschrijving> alleinschrijvingen= new ArrayList<Inschrijving>();

		while (rs.next()) {   
			voornaam = 			rs.getString("voornaam");
			achternaam = 		rs.getString("achternaam");
			id = 				rs.getInt("id");
			bankrekeningnummer =rs.getString("bankrekeningnummer");
			telefoonnummer =	rs.getString("telefoonnummer");
			woonplaatsklant = 	rs.getString("woonplaats");
			adresklant = 		rs.getString("adresklant");
			email = 			rs.getString("email");
			
			plaatsnaam = 		rs.getString("plaatsnaam");
			postcode = 			rs.getString("postcode");
			adres = 			rs.getString("adres");
			Locatieid = 		rs.getInt("locatieid");
			
			abbonementnaam = 	rs.getString("typeabonnement");
			prijs = 			rs.getDouble("prijs");
			alleinschrijvingen.add(new Inschrijving(new Klant(voornaam, achternaam, id, bankrekeningnummer, telefoonnummer, woonplaatsklant, adresklant, email),new Locatie(plaatsnaam, postcode, adres, Locatieid), new Abonnement(abbonementnaam, prijs)));
		}
		stmt.close();
		con.close();
		return alleinschrijvingen;
	}catch(Exception ex){
		ex.printStackTrace();
		return null;
	}
	}
	public List<Inschrijving> getFilteredWoonplaats(String filter) {
		try(Connection con = super.getConnection()){
		Statement stmt = con.createStatement();
		String cap = filter.substring(0, 1).toUpperCase() + filter.substring(1);
		String Querry = "SELECT * FROM klant t JOIN inschrijving i ON t.ID = I.klant_ID JOIN locatie l on i.locatieid= l.locatieid JOIN abonnement a on i.typeabonnement = a.typeabonnement WHERE woonplaats Like '%"+filter+"%' or woonplaats Like '"+filter+"%' or woonplaats Like '%"+filter+"' or woonplaats Like '%"+cap+"%' or woonplaats Like '"+cap+"%' or woonplaats Like '%"+cap+"'";
		ResultSet rs = stmt.executeQuery(Querry);
		int id;
		String voornaam;
		String achternaam;
		String bankrekeningnummer;
		String telefoonnummer;
		String woonplaatsklant;
		String adresklant;
		String email;
		
		String plaatsnaam;
		String postcode;
		String adres;
		int Locatieid;
		
		String abbonementnaam;
		double prijs;
		
		ArrayList<Inschrijving> alleinschrijvingen= new ArrayList<Inschrijving>();

		while (rs.next()) {   
			voornaam = 			rs.getString("voornaam");
			achternaam = 		rs.getString("achternaam");
			id = 				rs.getInt("id");
			bankrekeningnummer =rs.getString("bankrekeningnummer");
			telefoonnummer =	rs.getString("telefoonnummer");
			woonplaatsklant = 	rs.getString("woonplaats");
			adresklant = 		rs.getString("adresklant");
			email = 			rs.getString("email");
			
			plaatsnaam = 		rs.getString("plaatsnaam");
			postcode = 			rs.getString("postcode");
			adres = 			rs.getString("adres");
			Locatieid = 		rs.getInt("locatieid");
			
			abbonementnaam = 	rs.getString("typeabonnement");
			prijs = 			rs.getDouble("prijs");
			alleinschrijvingen.add(new Inschrijving(new Klant(voornaam, achternaam, id, bankrekeningnummer, telefoonnummer, woonplaatsklant, adresklant, email),new Locatie(plaatsnaam, postcode, adres, Locatieid), new Abonnement(abbonementnaam, prijs)));
		}
		stmt.close();
		con.close();
		return alleinschrijvingen;
	}catch(Exception ex){
		ex.printStackTrace();
		return null;
	}
	}
	public List<Inschrijving> getFilteredAbonnement(String filter) {
		try(Connection con = super.getConnection()){
		Statement stmt = con.createStatement();
		String cap = filter.substring(0, 1).toUpperCase() + filter.substring(1);
		String Querry = "SELECT * FROM klant t JOIN inschrijving i ON t.ID = I.klant_ID JOIN locatie l on i.locatieid= l.locatieid JOIN abonnement a on i.typeabonnement = a.typeabonnement WHERE i.typeabonnement Like '%"+filter+"%' or i.typeabonnement Like '"+filter+"%' or i.typeabonnement Like '%"+filter+"' or i.typeabonnement Like '%"+cap+"%' or i.typeabonnement Like '"+cap+"%' or i.typeabonnement Like '%"+cap+"'";
		ResultSet rs = stmt.executeQuery(Querry);
		int id;
		String voornaam;
		String achternaam;
		String bankrekeningnummer;
		String telefoonnummer;
		String woonplaatsklant;
		String adresklant;
		String email;
		
		String plaatsnaam;
		String postcode;
		String adres;
		int Locatieid;
		
		String abbonementnaam;
		double prijs;
		
		ArrayList<Inschrijving> alleinschrijvingen= new ArrayList<Inschrijving>();

		while (rs.next()) {   
			voornaam = 			rs.getString("voornaam");
			achternaam = 		rs.getString("achternaam");
			id = 				rs.getInt("id");
			bankrekeningnummer =rs.getString("bankrekeningnummer");
			telefoonnummer =	rs.getString("telefoonnummer");
			woonplaatsklant = 	rs.getString("woonplaats");
			adresklant = 		rs.getString("adresklant");
			email = 			rs.getString("email");
			
			plaatsnaam = 		rs.getString("plaatsnaam");
			postcode = 			rs.getString("postcode");
			adres = 			rs.getString("adres");
			Locatieid = 		rs.getInt("locatieid");
			
			abbonementnaam = 	rs.getString("typeabonnement");
			prijs = 			rs.getDouble("prijs");
			alleinschrijvingen.add(new Inschrijving(new Klant(voornaam, achternaam, id, bankrekeningnummer, telefoonnummer, woonplaatsklant, adresklant, email),new Locatie(plaatsnaam, postcode, adres, Locatieid), new Abonnement(abbonementnaam, prijs)));
		}
		stmt.close();
		con.close();
		return alleinschrijvingen;
	}catch(Exception ex){
		ex.printStackTrace();
		return null;
	}
	}
}
