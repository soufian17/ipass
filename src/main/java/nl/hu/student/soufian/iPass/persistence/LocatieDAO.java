package nl.hu.student.soufian.iPass.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import nl.hu.student.soufian.iPass.model.Abonnement;
import nl.hu.student.soufian.iPass.model.Locatie;

public class LocatieDAO extends BaseDAO{
	
	//vind locatie met adres 'adres' en plaatsnaam 'plaats'.
	public Locatie findLocatie(String locatieStr) {
		try(Connection con = super.getConnection()){
			String[] split = locatieStr.split(" - ");
			String adres = split[0];
			String plaats = split[1];
			String query = "select * from locatie where adres=? and plaatsnaam=?";
			PreparedStatement ps = con.prepareStatement(query);
			ps.setString(1, adres);
			ps.setString(2, plaats);
			System.out.println(ps.toString());
			ResultSet rs = ps.executeQuery();
			
			rs.next();
			String adr = rs.getString("Adres");
			String plaatsnaam = rs.getString("Plaatsnaam");
			String postcode = rs.getString("postcode");
			int locID = rs.getInt("LocatieID");
			Locatie loc = new Locatie(plaatsnaam, postcode, adr,locID);
			return loc;
		}catch(Exception e){
			return null;
		}
	}
	//returnt alle locaties
	public List<Locatie> allelocaties(){
		try(Connection con = super.getConnection()){
			List <Locatie> allelocaties = new ArrayList<Locatie>();
			String query = "select * from locatie";
			PreparedStatement ps = con.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				String adres = rs.getString("Adres");
				String plaatsnaam = rs.getString("Plaatsnaam");
				String postcode = rs.getString("Postcode");
				int locID = rs.getInt("LocatieID");
				Locatie loc = new Locatie(plaatsnaam, postcode, adres, locID);
				allelocaties.add(loc);
			}
			return allelocaties;
		}catch(Exception e){
			return null;
		}
	}
	
	
	
	//returnt alle abonnementen BEHALVE degene met locatieid 'locatieid'.
	//wordt gebruikt om de selectie aan te vullen in de forms(naast de waarde gekoppeld aan het lid, die wordt eerder aangevuld.)
	public List<Locatie> getRestLocaties(int locatieid) {
		try(Connection con = super.getConnection()){
			
		ArrayList<Locatie> allelocaties= new ArrayList<Locatie>();
		String queryText = "SELECT * FROM locatie where locatieid !=?";
		PreparedStatement ps = con.prepareStatement(queryText);
		ps.setInt(1, locatieid);
		ResultSet rs = ps.executeQuery();
		while(rs.next()){
			String adres = rs.getString("Adres");
			String plaatsnaam = rs.getString("Plaatsnaam");
			String postcode = rs.getString("Postcode");
			int locID = rs.getInt("LocatieID");
			Locatie loc = new Locatie(plaatsnaam, postcode, adres, locID);
			allelocaties.add(loc);
		}
		return allelocaties;
	}catch(Exception e){
		return null;
	}
	}
	
	//vindt locatie met id 'id'
	public Locatie findLocatieById(int locatieid) {
		try(Connection con = super.getConnection()){
		String query = "select * from locatie where locatieid=?";
		PreparedStatement ps = con.prepareStatement(query);
		ps.setInt(1, locatieid);
		ResultSet rs = ps.executeQuery();
		
		rs.next();
		String adr = rs.getString("Adres");
		String plaatsnaam = rs.getString("Plaatsnaam");
		String postcode = rs.getString("postcode");
		int locID = rs.getInt("LocatieID");
		Locatie loc = new Locatie(plaatsnaam, postcode, adr,locID);
		return loc;
	}catch(Exception e){
		return null;
	}
	}
	public boolean addLocatie(String postcode, String plaatsnaam, String adres) {
		try(Connection con = super.getConnection()){
			String q = "insert into locatie (postcode,plaatsnaam,adres) values (?,?,?)";
			PreparedStatement ps = con.prepareStatement(q);
			ps.setString(1, postcode);
			ps.setString(2, plaatsnaam);
			ps.setString(3, adres);
			ps.executeUpdate();
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}

}
