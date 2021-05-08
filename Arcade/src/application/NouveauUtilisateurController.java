/* Auteur: Zachary Xie
 * Application: Arcade
 * Cette classe est le controlleur d'un fênetre ou l'utilisateur peut créer un nouveau utilisateur.
 *  
 */

package application;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class NouveauUtilisateurController {
	
	// Référence au arcadecontroller principal
	public ArcadeController controller;

    @FXML
    private TextField txtCredits;

    @FXML
    private TextField txtSurnom;

    @FXML
    private Button btnAnnuler;

    @FXML
    private Button btnCreer;

    @FXML
    private TextField txtNom;
    
    // Ferme la fênetre
    @FXML
    void annuler() {
    	Stage stage = (Stage) btnAnnuler.getScene().getWindow();
    	stage.close();
    }
    
    // Confirme les choix faites
    @FXML
    void confirmer() {
    	// Si toutes les cases sont replis correctement un nouveau utilisateur est crée et le fênetre est fermé
    	try {
    		controller.ajouterUtilisateurAlt(txtNom.getText(), txtSurnom.getText(), Integer.parseInt(txtCredits.getText()));
        	Stage stage = (Stage) btnAnnuler.getScene().getWindow();
        	stage.close();
    	}
    	// Sinon on lance une alerte
    	catch(Exception e) {
    		Alert alert = new Alert(AlertType.ERROR);
    		alert.setTitle("Erreur");
    		alert.setHeaderText("Les données sont incomplètes");
    		alert.setContentText("Tu dois remplir toutes les cases pour créer un utilisateur");
    		alert.showAndWait();
    	}
    	
    	
    	
    }
    
    // Vérifie que l'input des crédits est valide
 	@FXML
 	public void verifCredits() {
 		txtCredits.textProperty().addListener((observable, oldValue, newValue)->{
 			if(!newValue.matches("^[0-9](\\.[0-9]+)?$")) {
 				txtCredits.setText(newValue.replaceAll("[^\\d*\\.]", ""));
 			}
 		});
 	}

}
