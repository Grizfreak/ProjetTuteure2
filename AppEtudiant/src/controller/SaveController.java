package controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import ExerciseGestion.OculText;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;

public class SaveController implements Initializable {
	@FXML private TextField name;
	@FXML private TextField firstName;
	@FXML private TextField path;
	@FXML private TextField fileName;
	@FXML private Button done;
	private OculText finalText;
	private int nbMotTrouve;
	private int nbMot;
	private String finalResponse;
	private boolean casse=false;
	private boolean partiel =false;
	private boolean allowSol =false;
	private boolean allowStat =false;
	private boolean mode_eval=false;
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		this.casse=MediaController.casse;
		this.partiel=MediaController.partiel;
		this.allowSol=MediaController.allowSol;
		this.allowStat=MediaController.allowStat;
		this.mode_eval=MediaController.mode_eval;
		finalText=MediaController.text;
		finalResponse=finalText.getTextCache();
		nbMot=finalText.getNbmots();
		nbMotTrouve=finalText.getMotTrouves();
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
		if (!path.getText().trim().isEmpty() && !fileName.getText().trim().isEmpty())done.setDisable(false); 
		if(fileName.getText().trim()=="")done.setDisable(true);
	}
	
	@FXML private void save() throws IOException{
		
	}

}
