/* Auteur: Zachary Xie
 * Application: Arcade
 * Cette classe est le controller du jeu Snake. Il contrôle toutes les éléments du GUI et les inputs dans le jeu.
 *  
 */

package application;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class SnakeController implements Initializable{
	
	// Variable FXML
	
	@FXML
    private Slider sliderMontant;

    @FXML
    private Slider sliderCouleur;

    @FXML
    private Button btnLeft;

    @FXML
    private Rectangle previewPommeCouleur;

    @FXML
    private AnchorPane optionsAnchorPane;

    @FXML
    private Slider sliderVitesse;

    @FXML
    private Slider sliderPommeCouleur;

    @FXML
    private Button btnUp;

    @FXML
    private Rectangle previewCouleur;

    @FXML
    private Button btnDown;

    @FXML
    private GridPane gridPlayArea;

    @FXML
    private Button btnRight;
    
    @FXML
    private Label lblVitesse;

    @FXML
    private Label lblMontant;
    
    @FXML
    private Label lblPoints;
    
    @FXML
    private Label lblUtilisateurNom;

    @FXML
    private Label lblUtilisateurCredits;
    
    @FXML
    private Button btnCommencez;
    
    @FXML
    private Label lblPointsTotal;
    
    @FXML
    private Button btnRetournez;
    
    // Un liste de deux dimensions pour garder les unités. Pour montrer le serpent et le pomme, on utilise des différentes couleurs pour eux.
    private Rectangle[][] tiles = new Rectangle[12][12]; // int[xPos][yPos]
    
    // Une classe pour régler la logique du jeu, utilisé car les fonctions dans le controlleur ne peut pas actualiser les couleurs des réctangles en vrai temps
    SnakeThread thread;
    
    Snake joueur;
    
    int points;
    
    // Pour extraire les données de l'utilisateur et pour communiquer au controlleur principal.
    Utilisateur utilisateurActive;
    ArcadeController arcadeController;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub\
		
		// On désactive les boutons pour controller le serpent dans le jeu 
		btnUp.setDisable(true);
		btnDown.setDisable(true);
		btnLeft.setDisable(true);
		btnRight.setDisable(true);
		
		// On crée les unités, et on le met dans un gridpane. 
		for(int i = 0; i < 12; i++) {
			for(int j = 0; j < 12; j++) {
				Rectangle rect = new Rectangle(0,0,50,50);
				rect.setFill(Color.WHITE);
				tiles[i][j] = rect;
			    gridPlayArea.add(rect, i, j);
			}
		}
		
		
	}
	
	// Fixer l'utilisateur active, et extraire les données
	public void SetUtilisateur(Utilisateur u) {
		utilisateurActive = u;
		// Afficher leur nom et leur montant de crédits
		lblUtilisateurNom.setText(utilisateurActive.toString());
		lblUtilisateurCredits.setText("Crédits: " + utilisateurActive.getCredits());
		// Si ils n'ont pas de crédits, désactiver le bouton pour commencer le jeu
		if(utilisateurActive.getCredits() <= 0) btnCommencez.setDisable(true);
	}
	
	// Commence le jeu
	@FXML
	void game() {
		// Enlève un crédit de l'utilisateur
		utilisateurActive.addCredits(-1);
		// Affiche le montant de crédits et désactive le bouton si nécessaire
		lblUtilisateurCredits.setText("Crédits: " + utilisateurActive.getCredits());
		if(utilisateurActive.getCredits() <= 0) btnCommencez.setDisable(true);
		// Activer les boutons de jeu
		btnUp.setDisable(false);
		btnDown.setDisable(false);
		btnLeft.setDisable(false);
		btnRight.setDisable(false);
		// Créer et démarrer un nouveau thread pour régler la logique du jeu
		thread = new SnakeThread(this,utilisateurActive);
		thread.start();
	}
	
	// Fixer le controlleur active
	public void setArcadeController(ArcadeController a) {
		arcadeController = a;
	}
	
	// Afficher le montant de points. On doit utiliser une méthode spéciaux car on n'a pas le droit de modifier le texte directement du classe SnakeThread.
	public void UpdatePoints(int points) {
		Platform.runLater(new Runnable(){

			@Override
			public void run() {
				// TODO Auto-generated method stub
				lblPointsTotal.setText("Total: " + points);
			}
			// do your GUI stuff here
			});
		
	}
	
	// Actualise les données après qu'on quitte le jeu. On doit utiliser une méthode spéciaux car on n'a pas le droit de modifier le texte directement du classe SnakeThread.
	public void refreshUpdateController() {
		Platform.runLater(new Runnable(){

			@Override
			public void run() {
				// TODO Auto-generated method stub
				arcadeController.refreshAll();
			}
			// do your GUI stuff here
			});
		
	}
	
	// Méthodes pour changer le direction
	@FXML
	void haut() {
		thread.ChangeDirection(0);
	}
	
	@FXML
	void bas() {
		thread.ChangeDirection(1);
	}
	
	@FXML
	void gauche() {
		thread.ChangeDirection(2);
	}
	
	@FXML
	void droite() {
		thread.ChangeDirection(3);
	}
	
	// Change le couleur des unités
	public void changeTileColor(int x, int y, Color color) {
		tiles[x][y].setFill(color);
	}
	
	// Retourne le couleur d'un unité
	public Color getTileColor(int x, int y) {
		return (Color) tiles[x][y].getFill();
	}
	
	// Affiche le montant de points. On doit utiliser une méthode spéciaux car on n'a pas le droit de modifier le texte directement du classe SnakeThread.
	public void ChangerPoints(int p) {
		points = p;
		Platform.runLater(new Runnable(){

			@Override
			public void run() {
				// TODO Auto-generated method stub
				lblPoints.setText("Points: " + points);
			}
			// do your GUI stuff here
			});
	}
	
	// Ferme le panneau de Snake
	@FXML
	public void fermezSnake() {
		refreshUpdateController();
		Stage stage = (Stage) btnRetournez.getScene().getWindow();
    	stage.close();
	}


}


