package de.dis2011.data;

public class Immobilie {
	private int id = -1;
	private String stadt;
	private String plz;
	private String stra�e;
	private String hausnummer;
	private String Fl�che;
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getstadt() {
		return stadt;
	}
	
	public void setstadt(String stadt) {
		this.stadt = stadt;
	}
	
	public String getstra�e() {
		return stra�e;
	}
	
	public void setstra�e(String stra�e) {
		this.stra�e = stra�e;
	}
	
	public String gethausnummer() {
		return hausnummer;
	}
	
	public void sethausnummer(String hausnummer) {
		this.hausnummer = hausnummer;
	}
	
	public String getplz() {
		return plz;
	}
	
	public void setplz(String plz) {
		this.plz = plz;
	}
	
	public String getfl�che(){
		return Fl�che;
	}
	
	public void setfl�che(String fl�che){
		this.Fl�che= fl�che;
	}

}
