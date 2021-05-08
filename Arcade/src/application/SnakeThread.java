/* Auteur: Zachary Xie
 * Application: Arcade
 * Cette class est pour r�gler la logique du jeu Snake. 
 *  
 */

package application;

import java.lang.Thread;
import java.util.ArrayList;

import javafx.scene.paint.Color;

public class SnakeThread extends Thread{
	
	// Variables pour le joueur, le controlleur, et l'utilisateur.
	Snake joueur;
	SnakeController controller;
	Utilisateur utilisateurActive;
	
	// Es-ce qu'on a perdu le jeu?
	boolean gameLost = false;
    
	// La derni�re position; pour que les extensions du serpent lui entr�ine. 
    int[] previousPos = new int[2];
    
    // Une liste des extensions du serpent. Chaque fois qu'on mange un pomme et gagne un point, il y a un extension de plus, 
    // et par cons�quent, le serpent devient un unit� plus grand. 
    ArrayList<SnakeExtention> extentions = new ArrayList<SnakeExtention>();
    
    // La vitesse est le montant de millisecondes avant que le serpent fait une pause avant de bouger. 
    int vitesse = 500;
    Color snakeCouleur = Color.GREEN;
    Color pommeCouleur = Color.RED;
    int points = 0;
	
    // Constructeur pour d�f�nir le controlleur et utilisateur
	public SnakeThread(SnakeController c, Utilisateur u) {
		controller = c;
		utilisateurActive = u;
		controller.UpdatePoints(utilisateurActive.getPoints());
	}
	
	// Retourne le montant de points eus dans cette jeu
	public int GetPoints() {
		return points;
	}
	
	// La m�thode principale, appel�e dans le ligne thread.start() dans le SnakeController
	public void run() {
		
		// R�initialise le jeu, tout les unit�s devient vide
		for(int i = 0; i < 12; i++) {
			for(int j = 0; j < 12; j++) {
				controller.changeTileColor(i, j, Color.WHITE);
			}
		}
		// Cr�er un nouveau serpent
		joueur = new Snake(3,6,6);
		previousPos = joueur.getCoords();
		controller.changeTileColor(previousPos[0], previousPos[1], snakeCouleur);
		// Ajoutez un extension, pour qu'il soit deux unit�s de long
		extentions.add(new SnakeExtention(previousPos[0], previousPos[1], controller, snakeCouleur));
		// Ajoutez un pomme pour qu'il peut manger
		AjouterPomme();
		points = 0;
		controller.ChangerPoints(points);
		gameLost = false;
		// Refraicher pour que l'utilisateur sait qu'il a d�pens� un cr�dit
		controller.refreshUpdateController();
		// Ex�cutez cette code continuellement avant qu'on perd le jeu
		while(!gameLost) {
			// Bougez le serpent et obtenez son position
			int[] a = joueur.move();
			// si a[0] == -1, le serpent � frapp� les fronti�res, le jeu est perdu, et on doit sortir du boucle.
			if(a[0] == -1) {
				gameLost = true;
				break;
			}
			
			// Si le position du serpent est occup� par un unit� rouge, ou un pomme:
			if(controller.getTileColor(a[0], a[1]).equals(pommeCouleur)) {
				// Ajoutez un point, diminuer le delai par 20 millisecondes, ajoutez un extension et un pomme.
				points += 1;
				controller.ChangerPoints(points);
				if(vitesse > 20) vitesse -= 20;
				AjoutezExtention();
				AjouterPomme();
			}
			
			// Si le position du serpent est occup� par un unit� vert, ou lui-m�me, le jeu est perdu, et on doit sortir du boucle.
			if(controller.getTileColor(a[0], a[1]).equals(snakeCouleur)) {
				gameLost = true;
				break;
			}
			
			//controller.changeTileColor(previousPos[0], previousPos[1], Color.WHITE);
			// Afficher la position de l'unit�
			controller.changeTileColor(a[0], a[1], snakeCouleur);
			// Bouger le premier extension au position en avant.
			extentions.get(0).MoveToPos(previousPos[0], previousPos[1]);
			// R�actualiser le position en avant
			previousPos = joueur.getCoords();
			// Delai
			try {
				Thread.sleep(vitesse);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		// Quand le jeu est fini, ajoutez les points au utilisateur, et r�actualiser le texte dans le sc�ne
		utilisateurActive.addPoints(points);
		controller.UpdatePoints(utilisateurActive.getPoints());
		controller.refreshUpdateController();
	}
	
	// On trouve la position du dernier extension et on lui positionne l�-bas. 
	// Ensuite, on ajoute cette extension au dernier extension. Plus de d�tails dans le classe d'extensions. 
	void AjoutezExtention() {
		int[] pos = extentions.get(extentions.size() - 1).GetPos();
		
		SnakeExtention temp = new SnakeExtention(pos[0],pos[1],controller, snakeCouleur);
		extentions.add(temp);
		extentions.get(extentions.size() - 2).AddExtension(temp);
	}
	
	// Ajoute un pomme. On veut que le pomme n'appara�t pas dans une zone occup� par le serpent, alors on trouve des coordon�es al�atoires
	// jusqu'a ce qu'on trouve un qui n'est pas dans un place occup�. 
	void AjouterPomme() {
		boolean bonPommePosTrouve = false;
		int[] pommePos = new int[2];
		while (!bonPommePosTrouve){
			pommePos[0] = (int)(Math.random() * 12);
			pommePos[1] = (int)(Math.random() * 12);
			if(controller.getTileColor(pommePos[0], pommePos[1]).equals(Color.WHITE)) {
				controller.changeTileColor(pommePos[0], pommePos[1], pommeCouleur);
				bonPommePosTrouve = true;
			}
		}
	}
	
	// Change le direction du serpent, appel�e du controlleur et les boutons. 
	public void ChangeDirection(int d) {
		joueur.ChangeDirection(d);
	}
}