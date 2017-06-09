package nl.hu.student.soufian.iPass.model;


public class Inschrijving {
	private Klant klant_;
	private Locatie locatie_;
	private Abonnement abbonement_;
	
	public Inschrijving(Klant klant,Locatie locatie, Abonnement abbonement){
		klant_=klant;
		locatie_=locatie;
		abbonement_=abbonement;
	}
	
	public Abonnement getAbbonement_() {
		return abbonement_;
	}
	public Klant getKlant() {
		return klant_;
	}
	public Locatie getLocatie() {
		return locatie_;
	}
}
