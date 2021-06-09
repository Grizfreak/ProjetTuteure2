package controller;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ResourceBundle;

import ExerciseGestion.OculText;
import application.Main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

public class SaveController implements Initializable {
	@FXML private TextField name;
	@FXML private TextField firstName;
	@FXML private TextField path;
	@FXML private TextField fileName;
	@FXML private Button done;
	private OculText finalText;
	@FXML private ImageView file;
	@FXML private ImageView folder;
	private String finalResponse;
	private boolean casse=false;
	private boolean partiel =false;
	private boolean allowSol =false;
	private boolean allowStat =false;
	private boolean mode_eval=false;
	private Integer minTime;
	private Integer secTime;
	private String filepath;
	public static Stage stage;
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		file.setImage(new Image(getClass().getResource("/images/7fd2e46b2da9819e667fb75caf475cf7.png").toString()));
		folder.setImage(new Image(getClass().getResource("/images/folder.png").toString()));
		this.casse=MediaController.casse;
		this.partiel=MediaController.partiel;
		this.allowSol=MediaController.allowSol;
		this.allowStat=MediaController.allowStat;
		this.mode_eval=MediaController.mode_eval;
		finalText=MediaController.text;
		finalResponse=finalText.getTextCache();
		minTime=MediaController.minFinalTime;
		secTime=MediaController.secFinalTime;
	}
	@FXML public void choosePath() {
		DirectoryChooser dc = new DirectoryChooser();
		File directorysave = dc.showDialog(null);
		path.setText(directorysave.getAbsolutePath());
		setDoneActive();
	}
	@FXML public void changeFileName() {
		fileName.setText(name.getText()+"_"+firstName.getText()+".exo");
		if(name.getText().trim().isEmpty() && firstName.getText().trim().isEmpty())fileName.setText("");;
		setDoneActive();
	}

	private void setDoneActive() {
		if (!path.getText().trim().isEmpty() && !fileName.getText().trim().isEmpty()) {
			done.setDisable(false); 
			filepath=path.getText()+"/"+fileName.getText();
		}
		if(fileName.getText().trim()=="")done.setDisable(true);
	}
	
	@FXML private void save() throws IOException{
		if(filepath !=null) {
			System.out.println(filepath);
			PrintWriter pt = new PrintWriter(filepath);
			pt.print(finalResponse);
			pt.print(System.lineSeparator());
			pt.print("Score : "+finalText.getMotTrouves()+"/"+finalText.getNbmots());
			pt.print(System.lineSeparator());
			pt.print("Casse : "+(casse ? 1 : 0));
			pt.print(System.lineSeparator());
			pt.print("Remplacement partiel : "+(partiel ? 1 : 0));
			pt.print(System.lineSeparator());
			pt.print("Autorisation de la solution : "+(allowSol ? 1 : 0));
			pt.print(System.lineSeparator());
			pt.print("Autorisation de l'affichage des statistiques : "+(allowStat ? 1 : 0));
			pt.print(System.lineSeparator());
			pt.print("Mode évaluation : "+(mode_eval ? 1 : 0));
			if(mode_eval) {
				pt.print(System.lineSeparator());
				pt.print("Temps restant : "+minTime+":"+secTime);
			}
			else{
				pt.print(System.lineSeparator());
				pt.print("Temps écoulé : "+minTime+":"+secTime);
			}
			pt.close();
		}
		Parent root = FXMLLoader.load(getClass().getResource("/view/AppEtu.fxml"));
		Stage thisStage = (Stage) Main.actualRoot.getScene().getWindow();
		Main.actualRoot=root;
		Scene next = new Scene(root,Main.width,Main.height);
		thisStage.setScene(next);
		stage.close();
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Confirmation");
		alert.setHeaderText("Votre fichier a bien été enregistré");
		alert.setContentText("Votre fichier a été enregistré à l'emplacement suivant : "+filepath);
		alert.show();
	}

}
