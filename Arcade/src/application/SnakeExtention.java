/* Auteur: Zachary Xie
 * Application: Arcade
 * Cette classe est l'extension du serpent. Chaque fois qu'on gagne un point, le serpend devient plus long, et développe un extension de plus
 *  
 */

package application;

import javafx.scene.paint.Color;

public class SnakeExtention {
	
	// Position précédent, pour bouger l'extention après lui.
	int[] previousPos = new int[2];
	//int[] currentPos = new int[2];
	
	// Un référence de l'extension après celui-ci. Quand cette extension bouge a une nouvelle position, on va bouger cette extension au position précédent.
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
	
	// Bouge l'extension à une position
	public void MoveToPos(int x, int y) {
		// Si c'est le dernie extension on va changer les couleurs des unités. 
		if(extention == null) {
			controller.changeTileColor(previousPos[0], previousPos[1], Color.WHITE);
			controller.changeTileColor(x, y, snakeCouleur);
		}
		// Sinon on appelle le même fonction sur l'extention après celui-ci
		else {
			extention.MoveToPos(previousPos[0], previousPos[1]);
		}
		
		previousPos[0] = x;
		previousPos[1] = y;
	}
	
	// Ajoute un extension après lui
	public void AddExtension(SnakeExtention ext) {
		extention = ext;
	}
	
}
