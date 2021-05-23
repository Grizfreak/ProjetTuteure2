package controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import application.Main;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.DragEvent;
import javafx.scene.input.TransferMode;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.FileChooser.ExtensionFilter;

public class MenuController implements Initializable {
	public static File f;
	public static boolean ismp3=false;
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub

	}
	@FXML
	public void searchFile() throws IOException {
		System.out.println("Opening..");
		FileChooser fc = new FileChooser();
		fc.getExtensionFilters().add(new ExtensionFilter("Fichiers exercices","*.ang"));
		f = fc.showOpenDialog(null);
		openFile(f);
	}
	private void openFile(File f) throws IOException {
		BufferedReader bf = new BufferedReader(new FileReader(f));
		while(bf.ready()) {
			if(bf.readLine().contains("ID3")) {
				ismp3=true;
				Parent root = FXMLLoader.load(getClass().getResource("/view/OpenDocEtump3.fxml"));
				changeScene(root);
				return;
			}
			else {
				Parent root = FXMLLoader.load(getClass().getResource("/view/OpenDocEtu.fxml"));
				changeScene(root);
				return;
			}
		}
	}
	private void changeScene (Parent root) {
		Stage thisStage = (Stage) Main.actualRoot.getScene().getWindow();
		Main.actualRoot=root;
		Scene next = new Scene(root,Main.width,Main.height);
		thisStage.setScene(next);

	}
	@FXML
	public void exit() {
		Platform.exit();
		System.out.println("Execution ended");
	}
	@FXML
	public void Drop(DragEvent event) throws IOException{
		List<File> files = event.getDragboard().getFiles();
		f = files.get(0);
		System.out.println("Sélection effectuée");
		openFile(f);
	}
	@FXML
	public void onDragOver(DragEvent event) {
		if (event.getDragboard().hasFiles()) {
			event.acceptTransferModes(TransferMode.ANY);
		}
	}

}
