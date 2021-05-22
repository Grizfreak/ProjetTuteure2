package controller;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

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
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub

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

}
