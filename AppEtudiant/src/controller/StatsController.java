package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import ExerciseGestion.OculText;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class StatsController implements Initializable{
	@FXML private TextArea eleve;
	@FXML private TextArea solution;
	@FXML private Label score;
	@FXML private Button save;
	private OculText text;
	protected static Stage stage;
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		text=MediaController.getText();
		eleve.setText(text.getTextCache());
		solution.setText(text.displayText());
		score.setText("Score : "+text.getMotTrouves()+"/"+text.getNbmots());
	}
	@FXML public void openSave() throws IOException {
		Parent saved = FXMLLoader.load(getClass().getResource("/view/PopupSave.fxml"));
		Scene save = new Scene(saved);
		Stage saving = new Stage();
		SaveController.stage=saving;
		saving.initStyle(StageStyle.UNDECORATED);
		saving.initModality(Modality.APPLICATION_MODAL);
		saving.setScene(save);
		saving.show();
		stage.close();
	}

}
