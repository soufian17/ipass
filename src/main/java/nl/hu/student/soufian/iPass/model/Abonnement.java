package nl.hu.student.soufian.iPass.model;

public class Abonnement {
	private String abbonementnaam_;
	private double prijs_;
	
	public Abonnement(String abbonementnaam,double prijs){
		abbonementnaam_=abbonementnaam;
		prijs_=prijs;
	}
	
	public String getAbbonementnaam() {
		return abbonementnaam_;
	}
	public double getPrijs() {
		return prijs_;
	}
}
