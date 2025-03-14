package nl.hu.student.soufian.iPass.model;

public class Klant {
	private String Voornaam_;
	private String Achternaam_;
	private int id_;
	private String bankrekeningnummer_;
	private String telefoonnummer_;
	private String woonplaats_;
	private String adres_;
	private String mail_;
	
	public Klant(String voornaam,String achternaam,int id,String bankrek,String telefoonnummer,String woonplaats,String adres,String mail){
		Voornaam_=voornaam;
		Achternaam_=achternaam;
		id_=id;
		bankrekeningnummer_=bankrek;
		telefoonnummer_=telefoonnummer;
		woonplaats_=woonplaats;
		adres_=adres;
		mail_=mail;
	}
	public Klant(String voornaam,String achternaam,String bankrek,String telefoonnummer,String woonplaats,String adres,String mail){
		Voornaam_=voornaam;
		Achternaam_=achternaam;
		bankrekeningnummer_=bankrek;
		telefoonnummer_=telefoonnummer;
		woonplaats_=woonplaats;
		adres_=adres;
		mail_=mail;
	}
	public int getId_() {
		return id_;
	}
	public String getBankrekeningnummer_() {
		return bankrekeningnummer_;
	}
	public String getMail_() {
		return mail_;
	}
	public String getTelefoonnummer_() {
		return telefoonnummer_;
	}
	public String getWoonplaats_() {
		return woonplaats_;
	}
	public String getVoornaam_() {
		return Voornaam_;
	}
	public String getAchternaam_() {
		return Achternaam_;
	}
	public String getAdres_() {
		return adres_;
	}
	public boolean equals(Object obj){
		if (this == obj){
			return true;
		}if (obj  instanceof Abonnement){
			Klant var = (Klant)obj;
			return this.Voornaam_.equals(var.Voornaam_) &&
					this.Achternaam_.equals(var.Achternaam_)&&
					this.bankrekeningnummer_.equals(var.bankrekeningnummer_)&&
					this.mail_.equals(var.mail_)&&
					this.adres_.equals(var.adres_)&&
					this.id_==(var.id_);
		}
		return false;
	}
}
