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
	public boolean equals(Object obj){
		if (this == obj){
			return true;
		}if (obj  instanceof Abonnement){
			Abonnement var = (Abonnement)obj;
			return this.abbonementnaam_.equals(var.abbonementnaam_) &&
					this.prijs_==var.prijs_;
		}
		return false;
	}
}
