/* Auteur: Zachary Xie
 * Application: Arcade
 * Cette classe est le controller de l'interface principal du arcade, ou il y a deux paneaux:
 * Un pour l'utilisateur, ou tu peux créer un nouveau utilisateur, choisir un utilisateur et acheter plus de crédits pour jouer au Snake.
 * L'autre est pour l'administrateur, qui peut ajouter, modifier et effacer des utilisateurs. 
 * 
 */

package application;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

public class ArcadeController implements Initializable {
	
	//Variables FXML

	@FXML
    private TableView<Utilisateur> utilisateursTable;

    @FXML
    private TableColumn<Utilisateur, Integer> colCredits;

    @FXML
    private TextField txtPoints;

    @FXML
    private TextField txtCredits;

    @FXML
    private TextField txtSurnom;

    @FXML
    private TableColumn<Utilisateur, Integer> colPoints;

    @FXML
    private Button btnEffacer;

    @FXML
    private TableColumn<Utilisateur, String> colNom;

    @FXML
    private Button btnRecommencer;

    @FXML
    private Button btnModifier;

    @FXML
    private Button btnAjouter;

    @FXML
    private TableColumn<Utilisateur, String> colSurnom;

    @FXML
    private TextField txtNom;
    
    @FXML
    private Button btnConfirmez;
    
    @FXML
    private TextField txtFNom;
    
    @FXML
    private TextField txtFSurnom;

    @FXML
    private Button btnAjoutezCredits3;

    @FXML
    private Button btnAjoutezCredits2;
    
    @FXML
    private Button btnAjoutezCredits1;
    
    @FXML
    private ComboBox<Utilisateur> cboUtilisateur;
    
    @FXML
    private Label lblCredits;
    
    @FXML
    private Label lblPoints;
    
    @FXML
    private Button btnJouez;
    
    // Données des utilisateurs pour le tableau et combobox
    //public ObservableList<Utilisateur> utilisateurData = FXCollections.observableArrayList(); 
    
    private	ObservableList<Utilisateur> UtilisateurList;
    
    public ObservableList<Utilisateur> getUtilisateurData(){
    	return UtilisateurList;
    }
    
    // Préparations Initiales
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
		try {
			UtilisateurList = ArcadeDAO.getAllRecords();
			colNom.setCellValueFactory(new PropertyValueFactory<>("nom"));
			colSurnom.setCellValueFactory(new PropertyValueFactory<>("surnom"));
			colCredits.setCellValueFactory(new PropertyValueFactory<>("credits"));
			colPoints.setCellValueFactory(new PropertyValueFactory<>("points"));
			
			utilisateursTable.setItems(UtilisateurList);
			
			cboUtilisateur.setItems(UtilisateurList);
			
			showUtilisateur(null);
			
			utilisateursTable.getSelectionModel().selectedItemProperty().addListener((
					observable, oldValue, newValue)-> showUtilisateur(newValue));
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
	}
	
	@FXML
	public void reinitialiser()throws ClassNotFoundException, SQLException
	{
		UtilisateurList = ArcadeDAO.getAllRecords();
		cboUtilisateur.setItems(UtilisateurList);
		
	}
	
	// Panneau Utilisateur
	
	// Dans le fiche d'utilisateur - ajoute un nouveau utilisateur. Il peut fixer le nom, surnom et montant de crédits, mais pas les points,
	// car tu dois gagner les points en jouant les jeux.
	public void ajouterUtilisateurAlt(String nom, String surnom, int credits) throws ClassNotFoundException, SQLException {
		
		ArcadeDAO.insertUtilisateur(nom, surnom, credits, 0);
		
		reinitialiser();
	}
	
	// Confirmez le choix de compte
	@FXML
	public void confirmezChoix() throws ClassNotFoundException, SQLException {
		btnJouez.setDisable(false);
		btnAjoutezCredits1.setDisable(false);
		btnAjoutezCredits2.setDisable(false);
		btnAjoutezCredits3.setDisable(false);
		refreshAll();
	}
	
	// Refreshe le texte dans le paneau d'utilisateur et le tableau
	public void refreshAll() throws ClassNotFoundException, SQLException {
		Utilisateur u = cboUtilisateur.getValue();
		if(u != null) {
			txtFNom.setText(u.getNom());
			txtFSurnom.setText(u.getSurnom());
			lblCredits.setText("" + u.getCredits());
			lblPoints.setText("" + u.getPoints());
		}
		reinitialiser();
		utilisateursTable.refresh();
	}
	
	// FXML méthodes pour ajouter des crédits, qui appelle une autre méthode avec des différentes paramètres
	@FXML
	public void AjoutezCredits1() throws ClassNotFoundException, SQLException {
		ModifierCredits(1);
	}
	
	@FXML
	public void AjoutezCredits2() throws ClassNotFoundException, SQLException {
		ModifierCredits(5);
	}
	
	@FXML
	public void AjoutezCredits3() throws ClassNotFoundException, SQLException {
		ModifierCredits(20);
	}
	
	// Augmente le montant de crédits et modifie le texte
	void ModifierCredits(int montant) throws ClassNotFoundException, SQLException {
		cboUtilisateur.getValue().addCredits(montant);
		lblCredits.setText("" + cboUtilisateur.getValue().getCredits());
		refreshAll();
	}
	
	// Ouvre un fênetre pour créer un nouveau utilisateur
	@FXML
	void handleNewUtilisateur() {
		try {
			FXMLLoader loader = new FXMLLoader(Main.class.getResource("NouveauUtilisateur.fxml"));
			AnchorPane pane = loader.load();
			Scene scene = new Scene(pane);
			NouveauUtilisateurController nouveauUtilisateur = loader.getController();
			nouveauUtilisateur.controller = this;
			Stage stage = new Stage();
			stage.setScene(scene);
			stage.setTitle("Nouveau Utilisateur");
			stage.show();
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	// Ouvre un fênetre pour jouer Snake
	@FXML
	void jouerSnake() {
		try {
			FXMLLoader loader = new FXMLLoader(Main.class.getResource("Snake.fxml"));
			AnchorPane pane = loader.load();
			Scene scene = new Scene(pane);
			SnakeController snakeController = loader.getController();
			snakeController.SetUtilisateur(cboUtilisateur.getValue());
			snakeController.setArcadeController(this);
			Stage stage = new Stage();
			stage.setScene(scene);
			stage.setTitle("Jouez Snake");
			stage.show();
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
		
	
	// Panneau Administrateur
	
	// Efface les inputs dans la liste des utilisateurs
	@FXML
	void clearFields() {
		txtNom.setText("");
		txtSurnom.setText("");
		txtCredits.setText("");
		txtPoints.setText("");
	}
	
	// Montre l'utilisateur sélectionné
	void showUtilisateur(Utilisateur utilisateur) {
		if(utilisateur != null) {
			txtNom.setText(utilisateur.getNom());
			txtSurnom.setText(utilisateur.getSurnom());
			txtCredits.setText("" + utilisateur.getCredits());
			txtPoints.setText("" + utilisateur.getPoints());
		}
		else {
			clearFields();
		}
	}
	
	// Ajoute un nouveau utilisateur parmi les boîtes de textes
	@FXML
	void ajouterUtilisateur() throws ClassNotFoundException, SQLException {
		try {
			ArcadeDAO.insertUtilisateur(txtNom.getText(),txtSurnom.getText(),Integer.parseInt(txtCredits.getText()),Integer.parseInt(txtPoints.getText()));
			cboUtilisateur.setItems(UtilisateurList);
			refreshAll();
		}
		catch(Exception e){
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Erreur");
			alert.setHeaderText("Les données sont incomplètes");
			alert.setContentText("Tu dois remplir toutes les cases pour créer un utilisateur");
			alert.showAndWait();
		}	
	}
	
	
	// Modifie l'utiliisateur sélectionné
	@FXML
	public void updateUtilisateur() throws ClassNotFoundException, SQLException {		
		ArcadeDAO.updateUtilisateur(0, txtNom.getText(), txtSurnom.getText(), Integer.parseInt(txtCredits.getText()), Integer.parseInt(txtPoints.getText()));
		
		refreshAll();
		
	}
	
	// Efface l'utiliisateur sélectionné
	@FXML
	public void deleteUtilisateur() throws ClassNotFoundException, SQLException {
		int selectedIndex = utilisateursTable.getSelectionModel().getSelectedIndex();
		if(selectedIndex >= 0) {
			utilisateursTable.getItems().remove(selectedIndex);
		}
		
		cboUtilisateur.setItems(utilisateurData);
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
	
	// Vérifie que l'input des points est valide
	@FXML
	public void verifPoints() {
		txtCredits.textProperty().addListener((observable, oldValue, newValue)->{
			if(!newValue.matches("^[0-9](\\.[0-9]+)?$")) {
				txtPoints.setText(newValue.replaceAll("[^\\d*\\.]", ""));
			}
		});
	}

	//Sauvegarde des données
	
	// Retourne le path ou le document .xml est localisée
	/*
	public File getUtilisateurFilePath() {
		Preferences prefs = Preferences.userNodeForPackage(Main.class);
		String filePath = prefs.get("filePath", null);
		
		if(filePath != null) {
			return new File(filePath);
		}
		else {
			return null;
		}
			
	}
	
	// Défenit le path du document .xml dans les préferences du classe
	public void setUtilisateurFilePath(File file) {
		Preferences prefs = Preferences.userNodeForPackage(Main.class);
		if(file != null) {
			prefs.put("filePath", file.getPath());
		}
		else {
			prefs.remove("filePath");
		}
	}
	
	// Affiche les données du document .xml dans le variable utilisateurData
	public void loadUtilisateurDataFromFile(File file) {
		try {
			JAXBContext context = JAXBContext.newInstance(UtilisateurListWrapper.class);
			Unmarshaller um = context.createUnmarshaller();
			UtilisateurListWrapper wrapper = (UtilisateurListWrapper) um.unmarshal(file);
			utilisateurData.clear();
			utilisateurData.addAll(wrapper.getUtilisateurs());
			setUtilisateurFilePath(file);
		} catch (Exception e) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Erreur");
			alert.setHeaderText("Les données n'ont pas été trouvées");
			alert.setContentText("Les données ne pouvaient pas être trouvées dans le fichier: \n" + file.getPath());
			alert.showAndWait();
		}
	}
	
	// Sauve les données dans le utilisateurData dans un document .xml
	public void saveUtilisateurDataToFile(File file) {
		try {
			JAXBContext context = JAXBContext.newInstance(UtilisateurListWrapper.class);
			Marshaller m = context.createMarshaller();
			
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			UtilisateurListWrapper wrapper = new UtilisateurListWrapper();
			
			wrapper.setUtilisateurs(utilisateurData);
			m.marshal(wrapper, file);
			setUtilisateurFilePath(file); 
			
		} catch (Exception e) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Erreur");
			alert.setHeaderText("Donnéees non sauvegardées");
			alert.setContentText("Les données ne pouvaient pas être sauvegardées dans le fichier: \n" + file.getPath());
			alert.showAndWait();
		}
	}
	
	// Crée un nouveau document
	@FXML
	private void handleNew() {
		getUtilisateurData().clear();
		setUtilisateurFilePath(null);
	}
	
	// Ouvre un document
	@FXML
	private void handleOpen() {
		FileChooser fileChooser = new FileChooser();
		
		FileChooser.ExtensionFilter extFilter = new ExtensionFilter("XML files (*.xml)", "*.xml");
		fileChooser.getExtensionFilters().add(extFilter);
		
		File file = fileChooser.showOpenDialog(null);
		
		if(file != null) {
			loadUtilisateurDataFromFile(file);
		}
		
		
	}
	
	// Sauvegarde un document
	@FXML
	private void handleSave() {
		
		File etudiantFile = getUtilisateurFilePath();
		if(etudiantFile != null) {
			saveUtilisateurDataToFile(etudiantFile);
		}
		else {
			handleSaveAs();
		}
	}
	
	// Sauvegarde dans un nouveau document
	@FXML
	private void handleSaveAs() {
		FileChooser fileChooser = new FileChooser();
		
		FileChooser.ExtensionFilter extFilter = new ExtensionFilter("XML files (*.xml)", "*.xml");
		fileChooser.getExtensionFilters().add(extFilter);
		
		File file = fileChooser.showSaveDialog(null);
		
		if(file != null) {
			if(!file.getPath().endsWith(".xml")) {
				file = new File(file.getPath() + ".xml");
			}
			saveUtilisateurDataToFile(file);
		}
	}
	
	*/

}
