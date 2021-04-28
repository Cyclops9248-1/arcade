package application;

public class Utilisateur {
	
	private String nom;
	private String surnom;
	private int credits;
	private int points;
	
	public Utilisateur() {
		nom = "";
		surnom = "";
		credits = 0;
		points = 0;
	}
	
	public Utilisateur(String _nom, String _surnom) {
		nom = _nom;
		surnom = _surnom;
		credits = 0;
		points = 0;
	}
	
	public Utilisateur(String _nom, String _surnom, int _credits) {
		nom = _nom;
		surnom = _surnom;
		credits = _credits;
		points = 0;
	}
	
	public Utilisateur(String _nom, String _surnom, int _credits, int _points) {
		nom = _nom;
		surnom = _surnom;
		credits = _credits;
		points = _points;
	}
	
	public Utilisateur(String _nom, String _surnom, String _credits, String _points) {
		nom = _nom;
		surnom = _surnom;
		credits = Integer.parseInt(_credits);
		points = Integer.parseInt(_points);
	}
	
	public String getNom() {
		return nom;
	}
	
	public String getSurnom() {
		return surnom;
	}
	
	public int getCredits() {
		return credits;
	}
	
	public int getPoints() {
		return points;
	}
	
	public void setNom(String param) {
		nom = param;
	}
	
	public void setSurnom(String param) {
		surnom = param;
	}
	
	public void setCredits(int param) {
		credits = param;
	}
	
	public void setPoints(int param) {
		points = param;
	}
	
	public String toString() {
		return surnom + ": " + nom;
	}
	
	
}
