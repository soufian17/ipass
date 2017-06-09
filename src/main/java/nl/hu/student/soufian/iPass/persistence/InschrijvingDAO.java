package nl.hu.student.soufian.iPass.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.util.ArrayList;

import nl.hu.student.soufian.iPass.model.Abonnement;
import nl.hu.student.soufian.iPass.model.Inschrijving;
import nl.hu.student.soufian.iPass.model.Klant;
import nl.hu.student.soufian.iPass.model.Locatie;


public class InschrijvingDAO extends BaseDAO{

		
	//voeg een inschrijving toe toe in de inschrijvingt table
	public boolean SaveInschrijving(int id, String abonnementnaam, String adres) throws SQLException {
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
	public boolean removeInschrijving(int id) throws SQLException {
		try(Connection con = super.getConnection()){
		String Querry = "delete from inschrijving where id=?";
		PreparedStatement ps = con.prepareStatement(Querry);
		ps.setInt(1, id);
		ps.executeUpdate();
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

}
