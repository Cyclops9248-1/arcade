/* Auteur: Zachary Xie
 * Application: Arcade
 * Cette classe est l'extension du serpent. Chaque fois qu'on gagne un point, le serpend devient plus long, et d�veloppe un extension de plus
 *  
 */

package application;

import javafx.scene.paint.Color;

public class SnakeExtention {
	
	// Position pr�c�dent, pour bouger l'extention apr�s lui.
	int[] previousPos = new int[2];
	//int[] currentPos = new int[2];
	
	// Un r�f�rence de l'extension apr�s celui-ci. Quand cette extension bouge a une nouvelle position, on va bouger cette extension au position pr�c�dent.
	SnakeExtention extention = null;
	SnakeController controller; 
	
	Color snakeCouleur;
	
	// Constructeur pour initialiser les valeurs
	public SnakeExtention(int x, int y, SnakeController c, Color couleur) {
		previousPos[0] = x;
		previousPos[1] = y;
		controller = c;
		snakeCouleur = couleur;
		controller.changeTileColor(x, y, snakeCouleur);
	}
	
	// Retourne la position.
	public int[] GetPos() {
		return previousPos;
	}
	
	// Bouge l'extension � une position
	public void MoveToPos(int x, int y) {
		// Si c'est le dernie extension on va changer les couleurs des unit�s. 
		if(extention == null) {
			controller.changeTileColor(previousPos[0], previousPos[1], Color.WHITE);
			controller.changeTileColor(x, y, snakeCouleur);
		}
		// Sinon on appelle le m�me fonction sur l'extention apr�s celui-ci
		else {
			extention.MoveToPos(previousPos[0], previousPos[1]);
		}
		
		previousPos[0] = x;
		previousPos[1] = y;
	}
	
	// Ajoute un extension apr�s lui
	public void AddExtension(SnakeExtention ext) {
		extention = ext;
	}
	
}
