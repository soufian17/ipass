package nl.hu.student.soufian.iPass.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.util.ArrayList;

import javassist.compiler.ast.Stmnt;
import nl.hu.student.soufian.iPass.model.Klant;
import nl.hu.student.soufian.iPass.model.Locatie;

public class KlantDAO extends BaseDAO{


	
	//voeg een klant toe in de klant table
	public boolean AddKlant(String voornaam,String achternaam,String bankrekeningnummer,String telefoonnummer,String email,String woonplaats,String adres){
		try(Connection con = super.getConnection()){
		String Querry = "insert into klant (voornaam,achternaam,bankrekeningnummer,telefoonnummer,woonplaats,email,adresklant) values (?,?,?,?,?,?,?)";
		PreparedStatement ps = con.prepareStatement(Querry);
		ps.setString(1, voornaam);
		ps.setString(2, achternaam);
		ps.setString(3, bankrekeningnummer);
		ps.setString(4, telefoonnummer);
		ps.setString(5, woonplaats);
		ps.setString(6, email);
		ps.setString(7, adres);
		ps.executeUpdate();
		return true;
		}catch(Exception e){
			return false;
		}
	}
	
	
	//verwijder een klant met id="id" 
	public boolean removeKlant(int id){
		try (Connection con = super.getConnection()){
		String Querry = "delete from klant where id=?";
		PreparedStatement ps = con.prepareStatement(Querry);
		ps.setInt(1, id);
		ps.executeUpdate();
		return true;
		}catch(Exception ex){
			return false;
		}	
	}
	
	//voeg een klant toe op basis van een klant object.
	public boolean AddKlant(Klant klant) {
		try(Connection con = super.getConnection()){
		String Querry = "insert into klant (voornaam,achternaam,bankrekeningnummer,telefoonnummer,woonplaats,email,adresklant) values (?,?,?,?,?,?,?)";
		PreparedStatement ps = con.prepareStatement(Querry);
		ps.setString(1, klant.getVoornaam_());
		ps.setString(2, klant.getAchternaam_());
		ps.setString(3, klant.getBankrekeningnummer_());
		ps.setString(4, klant.getTelefoonnummer_());
		ps.setString(5, klant.getWoonplaats_());
		ps.setString(6, klant.getMail_());
		ps.setString(7, klant.getAdres_());

		ps.executeUpdate();
		return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}

	
	//returnt alle klanten met abbonement
	public ArrayList<Klant> getAlleKlanten() {
		try (Connection con = super.getConnection()){
		Statement stmt = con.createStatement();
		ArrayList<Klant> alleklanten= new ArrayList<Klant>();
		String queryText = "select * from INSCHRIJVING i join KLANT k on k.ID = i.klant_ID";
		ResultSet rs = stmt.executeQuery(queryText);
		String voornaam;
		String achternaam;
		int id;
		String bankrekeningnummer;
		String telefoonnummer;
		String woonplaats;
		String adres;
		String email;
		while (rs.next()) {   
			voornaam = 			rs.getString("voornaam");
			achternaam = 		rs.getString("achternaam");
			id = 				rs.getInt("id");
			bankrekeningnummer =rs.getString("bankrekeningnummer");
			telefoonnummer =	rs.getString("telefoonnummer");
			woonplaats = 		rs.getString("woonplaats");
			adres = 			rs.getString("adresklant");
			email = 			rs.getString("email");
			alleklanten.add(new Klant(voornaam, achternaam, id, bankrekeningnummer, telefoonnummer, woonplaats, adres, email));
		}
		rs.close();
		stmt.close();
		return alleklanten;
		}catch(Exception ex){
			ex.printStackTrace();
			return null;
		}
	}
	
	
	//vind de klant met voornaam 'voornaam', achternaam 'achternaam' en bankrekeningnummer 'bankrek'.
	public Klant findKlant(String voornaam, String achternaam, String bankrek) {
		try(Connection con = super.getConnection()){
			String query = "select * from klant where voornaam=? and achternaam=? and bankrekeningnummer=?";
			PreparedStatement ps = con.prepareStatement(query);
			ps.setString(1, voornaam);
			ps.setString(2, achternaam);
			ps.setString(3, bankrek);
			ResultSet rs = ps.executeQuery();
			rs.next();
			int ID = rs.getInt("ID");
			String telefoonnummer = rs.getString("telefoonnummer");
			String woonplaats = rs.getString("woonplaats");
			String adres = rs.getString("adresklant");
			String mail = rs.getString("email");
			rs.close();
			ps.close();
			Klant klant = new Klant(voornaam, achternaam, ID, bankrek, telefoonnummer, woonplaats, adres, mail);
			return klant;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	//vind klant met id 'id'
	public Klant findKlant(int id) {
		try(Connection con = super.getConnection()){
			String query = "select * from klant  id=?";
			PreparedStatement ps = con.prepareStatement(query);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			rs.next();
			String voornaam = rs.getString("voornaam");
			String achternaam = rs.getString("achternaam");
			int ID = rs.getInt("ID");
			String bankrek = rs.getString("bankrekening");
			String telefoonnummer = rs.getString("telefoonnummer");
			String woonplaats = rs.getString("woonplaats");
			String adres = rs.getString("adresklant");
			String mail = rs.getString("email");
			rs.close();
			ps.close();
			Klant klant = new Klant(voornaam, achternaam, ID, bankrek, telefoonnummer, woonplaats, adres, mail);
			return klant;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	
	//update alle gegevens van een klant met id 'id'
	public boolean updateKlant(int id, String voornaam, String achternaam, String bankrek, String telefoonnummer,String mail, String woonplaats, String adres) {
		try (Connection con = super.getConnection()){
			String Querry = "update klant set voornaam=?, achternaam=?, adresklant=?,woonplaats=?,bankrekeningnummer=?,telefoonnummer=?,email=? where id = ?";
			PreparedStatement ps = con.prepareStatement(Querry);
			ps.setString(1, voornaam);
			ps.setString(2, achternaam);
			ps.setString(3, adres);
			ps.setString(4, woonplaats);
			ps.setString(5, bankrek);
			ps.setString(6, telefoonnummer);
			ps.setString(7, mail);
			ps.setInt	(8, id);
			ps.executeUpdate();
		return true;
		}catch(Exception ex){
			return false;
		}	
	}

	
}
