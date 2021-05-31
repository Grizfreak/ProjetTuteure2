package controller;

import java.net.URL;
import java.util.ResourceBundle;

import ExerciseGestion.OculText;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

public class StatsController implements Initializable{
	@FXML private Label score;
	private OculText text;
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub	
		System.out.println("j'suis con");
		text=MediaController.getText();
		score.setText("Score : "+text.getMotTrouves()+"/"+text.getNbmots());
	}

}
