/* Auteur: Zachary Xie
 * Application: Arcade
 * Cette classe est un utilisateur, qui comporte un nom, surnom, crédits et les points.
 * 
 */

package application;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="utilisateur")
public class UtilisateurListWrapper {
	private List<Utilisateur> utilisateurs;
	@XmlElement(name="utilisateur")
	public List<Utilisateur> getUtilisateurs(){
		return utilisateurs;
	}
	
	public void setUtilisateurs(List<Utilisateur> utilisateurs) {
		this.utilisateurs = utilisateurs;
	}
	
	
}
