/* Auteur: Zachary Xie
 * Application: Arcade
 * Cette classe r�presente le serpent dans le jeu. Il peut contenir la direction et ses coordon�es, et peut bouger dans une certaine direction.
 * 
 */

package application;

public class Snake {
	
	int direction; // 0 = haut, 1 = bas, 2 = gauche, 3 = droite
	
	int posX;
	int posY;
	
	// Constructeur d�faut
	public Snake() {
		direction = 0;
		posX = 0;
		posY = 0;
	}
	
	// Constructeur compl�te
	public Snake(int direction_, int posX_, int posY_) {
		direction = direction_;
		posX = posX_;
		posY = posY_;
	}
	
	// Retourne les coordon�es dans un liste avec deux �l�ments
	public int[] getCoords() {
		int[] a = {posX,posY};
		return a;
	}
	
	// Change la direction du serpent
	public void ChangeDirection(int direction_) {
		direction = direction_;
	}
	
	// Bouge le serpent. On retourne les coordon�es nouvelles pour que le classe principale peut l'afficher.
	public int[] move() {
		// Variable temporaire
		int[] a = new int[2];
		
		// Si on va en haut...
		if(direction == 0) {
			// On bouge un unit� vers le haut
			posY -= 1;
			// Si on d�passe le fronti�re, on retourne -1, qui signifie qu'on perds le jeu
			if(posY == -1) {
				a[0] = -1;
			}
			// Sinon, on met la nouvelle position dans le variable temporaire et on le retourne au classe principale.
			else {
				a[0] = posX;
				a[1] = posY;
			}
			return a;
		}
		// Si on va en bas...
		else if(direction == 1) {
			// On bouge un unit� vers le bas
			posY += 1;
			// Si on d�passe le fronti�re, on retourne -1, qui signifie qu'on perds le jeu
			if(posY == 12) {
				a[0] = -1;
			}
			// Sinon, on met la nouvelle position dans le variable temporaire et on le retourne au classe principale.
			else {
				a[0] = posX;
				a[1] = posY;
			}
			return a;
		}
		// Si on va vers la gauche...
		else if(direction == 2) {
			// On bouge un unit� vers le gauche
			posX -= 1;
			// Si on d�passe le fronti�re, on retourne -1, qui signifie qu'on perds le jeu
			if(posX == -1) {
				a[0] = -1;
			}
			// Sinon, on met la nouvelle position dans le variable temporaire et on le retourne au classe principale.
			else {
				a[0] = posX;
				a[1] = posY;
			}
			return a;
		}
		// Si on va vera la droite...
		else {
			// On bouge un unit� vers le droite
			posX += 1;
			// Si on d�passe le fronti�re, on retourne -1, qui signifie qu'on perds le jeu
			if(posX == 12) {
				a[0] = -1;
			}
			// Sinon, on met la nouvelle position dans le variable temporaire et on le retourne au classe principale.
			else {
				a[0] = posX;
				a[1] = posY;
			}
			return a;
		}
	}

}
