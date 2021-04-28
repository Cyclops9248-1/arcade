package application;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

public class ArcadeController implements Initializable {

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
    private Button btnCredits2;

    @FXML
    private Button btnPoints1;

    @FXML
    private Button btnPoints2;

    @FXML
    private Button btnCredits1;

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
    
    public ObservableList<Utilisateur> utilisateurData = FXCollections.observableArrayList(); 
    
    public ObservableList<Utilisateur> getEtudiantData(){
    	return utilisateurData;
    }

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		colNom.setCellValueFactory(new PropertyValueFactory<>("nom"));
		colSurnom.setCellValueFactory(new PropertyValueFactory<>("surnom"));
		colCredits.setCellValueFactory(new PropertyValueFactory<>("credits"));
		colPoints.setCellValueFactory(new PropertyValueFactory<>("points"));
		
		utilisateursTable.setItems(utilisateurData);
		
		cboUtilisateur.setItems(utilisateurData);
		
		showUtilisateur(null);
		
		utilisateursTable.getSelectionModel().selectedItemProperty().addListener((
				observable, oldValue, newValue)-> showUtilisateur(newValue));
	}
	
	@FXML
	void clearFields() {
		txtNom.setText("");
		txtSurnom.setText("");
		txtCredits.setText("");
		txtPoints.setText("");
	}
	
	@FXML
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
	
	@FXML
	void ajouterUtilisateur() {
		Utilisateur tmp = new Utilisateur(txtNom.getText(),txtSurnom.getText(),txtCredits.getText(),txtPoints.getText());
		
		utilisateurData.add(tmp);
		
		cboUtilisateur.setItems(utilisateurData);
	}
	
	@FXML
	public void updateUtilisateur() {
		Utilisateur utilisateur = utilisateursTable.getSelectionModel().getSelectedItem();
		
		utilisateur.setNom(txtNom.getText());
		utilisateur.setSurnom(txtSurnom.getText());
		utilisateur.setCredits(Integer.parseInt(txtCredits.getText()));
		utilisateur.setPoints(Integer.parseInt(txtPoints.getText()));
		
		utilisateursTable.refresh();
		
		cboUtilisateur.setItems(utilisateurData);
	}
	
	@FXML
	public void deleteUtilisateur() {
		int selectedIndex = utilisateursTable.getSelectionModel().getSelectedIndex();
		if(selectedIndex >= 0) {
			utilisateursTable.getItems().remove(selectedIndex);
		}
		
		cboUtilisateur.setItems(utilisateurData);
	}
	
	@FXML
	public void confirmezChoix() {
		Utilisateur u = cboUtilisateur.getValue();
		
		txtFNom.setText(u.getNom());
		txtFSurnom.setText(u.getSurnom());
		lblCredits.setText("" + u.getCredits());
		lblPoints.setText("" + u.getPoints());
	}
	
	//Sauvegarde des données
	
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
	
	public void setUtilisateurFilePath(File file) {
		Preferences prefs = Preferences.userNodeForPackage(Main.class);
		if(file != null) {
			prefs.put("filePath", file.getPath());
		}
		else {
			prefs.remove("filePath");
		}
	}
	
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
	
	@FXML
	private void handleNew() {
		getEtudiantData().clear();
		setUtilisateurFilePath(null);
	}
	
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

}
