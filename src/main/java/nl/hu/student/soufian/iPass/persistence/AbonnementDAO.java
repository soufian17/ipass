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
import nl.hu.student.soufian.iPass.persistence.BaseDAO;


public class AbonnementDAO extends BaseDAO{
	
	//voeg een abbonementsoort toe in de abbonement(abonnement) table
	public boolean addAbonnement(String naam,double prijs) {
		try (Connection con = super.getConnection()){
		String Querry = "insert into abbonement values (?,?)";
		PreparedStatement ps = con.prepareStatement(Querry);
		ps.setString(1, naam);
		ps.setDouble(2, prijs);
		ps.executeUpdate();
		con.close();
		return true;
	}catch(Exception e){
		return false;
	}
	}
	public Abonnement findAbonnement(String naam){
		try(Connection con = super.getConnection()){
			String query = "select * from abonnement where typeabonnement=?";
			PreparedStatement ps = con.prepareStatement(query);
			ps.setString(1, naam);
			ResultSet rs = ps.executeQuery();
			rs.next();
			String abonnementtype = rs.getString("typeabonnement");
			double prijs = rs.getDouble("prijs");
			Abonnement abbo = new Abonnement(abonnementtype, prijs);
			return abbo;
		}catch(Exception e){
			return null;
		}
	}
	 
	//haal een abbonement weg met de naam die je meegeeft
	public boolean removeAbonnement(String naam){
		try(Connection con = super.getConnection()){
		
		String Querry = "delete from abbonement where abbonementnaam=?)";
		PreparedStatement ps = con.prepareStatement(Querry);
		ps.setString(1, naam);
		ps.executeUpdate();
		return true;
		}catch(Exception e){
			return false;
		}
		
	}

	
	//return alle abbonementen
	public ArrayList<Abonnement> getAllAbbonementen(){
		try(Connection con = super.getConnection()){
		
		Statement stmt = con.createStatement();
		ArrayList<Abonnement> alleabbo= new ArrayList<Abonnement>();
		String queryText = "SELECT typeabonnement,prijs FROM abonnement";
		ResultSet rs = stmt.executeQuery(queryText);
		String naam;
		double prijs;
		while (rs.next()) {
			naam = 	rs.getString("typeabonnement");
			prijs = rs.getDouble("prijs");
			alleabbo.add(new Abonnement(naam,prijs));
		}
		rs.close();
		stmt.close();
		return alleabbo;
		}catch(Exception e){
			return null;
		}
	}
}
