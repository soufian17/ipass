package nl.hu.student.soufian.iPass.model;

public class Locatie {
	private String plaatsnaam_;
	private String postcode_;
	private String adres_;
	private int LocatieID;
	
	public Locatie(String plaatsnaam,String postcode,String adres,int Locatieid) {
		plaatsnaam_=plaatsnaam;
		postcode_=postcode;
		adres_=adres;
		LocatieID = Locatieid;
	}
	public String getAdres_() {
		return adres_;
	}
	public String getPlaatsnaam_() {
		return plaatsnaam_;
	}
	public String getPostcode_() {
		return postcode_;
	}
	public int getLocatieID() {
		return LocatieID;
	}
}
