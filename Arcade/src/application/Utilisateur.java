/* Auteur: Zachary Xie
 * Application: Arcade
 * Cette classe est un utilisateur, qui comporte un nom, surnom, cr�dits et les points.
 * 
 */

package application;

public class Utilisateur {
	
	private String nom;
	private String surnom;
	private int credits;
	private int points;
	
	// Constucteur d�faut
	public Utilisateur() {
		nom = "";
		surnom = "";
		credits = 0;
		points = 0;
	}
	
	// Constucteurs utilis� quand quelqu'un cr�e un compte
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
	
	// M�thodes pour chercher les variables
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
	
	// M�thodes pour modifier les variables
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
	
	// Tourne le classe en un string; un fa�on de d�montrer le nom et surnom de l'utilisateur quand l'utilisateur choisit un compte
	public String toString() {
		return surnom + ": " + nom;
	}
	
	// Ajoute des cr�dits/points
	public void addCredits(int montant) {
		credits += montant;
	}
	
	public void addPoints(int montant) {
		points += montant;
	}
}
