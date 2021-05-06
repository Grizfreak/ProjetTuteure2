package application;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.collections.ObservableMap;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

public class Controller implements Initializable{
	@FXML private MediaView mvPlayer;
	@FXML private ImageView mp3Player;
	private MediaPlayer mp;
	private Media me; 
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		System.out.println("Launching....");
		
	}
	
	@FXML
	public void searchOpenFile() throws IOException {
		System.out.println("Opening..");
		FileChooser fc = new FileChooser();
		fc.getExtensionFilters().add(new ExtensionFilter("Fichiers étudiants","*.txt"));
		fc.getExtensionFilters().add(new ExtensionFilter("mp3","*.mp3"));
		fc.getExtensionFilters().add(new ExtensionFilter("mp4","*.mp4"));
		File f = fc.showOpenDialog(null);
		
		if (f!=null) {
			System.out.println("Selected File : "+ f.getAbsolutePath());
			if(f.getAbsolutePath().contains(".mp4")) changeScene(FXMLLoader.load(getClass().getResource("/application/OpenDocEtu.fxml")));
			if(f.getAbsolutePath().contains(".mp3")) changeScene(FXMLLoader.load(getClass().getResource("/application/OpenDocEtump3.fxml")));
		}
	}
	private void openFile(File f) {
		System.out.println("Selected File : " + f.getAbsolutePath());
	}
	
	private void changeScene (Parent root) {
		Stage thisStage = (Stage) Main.actualRoot.getScene().getWindow();
		Main.actualRoot=root;
		Scene next = new Scene(root,Main.width,Main.height);
		thisStage.setScene(next);
	}
	@FXML
	public void onDragOver(DragEvent event) {
		if (event.getDragboard().hasFiles()) {
			event.acceptTransferModes(TransferMode.ANY);
		}
	}
	@FXML
	public void Drop(DragEvent event) throws FileNotFoundException{
		List<File> files = event.getDragboard().getFiles();
		File f = files.get(0);
		System.out.println("Sélection effectuée");
		if(f.getAbsolutePath().contains(".txt")) openFile(f);
		else System.out.println("impossible");
		if(f.getAbsolutePath().contains(".mp4")) launchvideo(f);
		if(f.getAbsolutePath().contains(".mp3")) launchsong(f);
	}
	private void launchsong(File f) {
		// TODO Auto-generated method stub
		System.out.println("Entrée en mode mp3");
		String path = f.getAbsolutePath();
		Media m = new Media(new File(path).toURI().toString());
		/*ObservableMap<String,Object> picinfo = m.getMetadata();
		picinfo.entrySet().forEach(entry -> {
		    System.out.println(entry.getKey() + " " + entry.getValue());
		});*/
		launchvideo(f);
		
	}

	@FXML
	public void exit() {
		Platform.exit();
		System.out.println("Execution ended");
	}
	@FXML
	public void test() throws IOException {
		changeScene(FXMLLoader.load(getClass().getResource("/application/close.fxml")));
	}
	
	public void launchvideo(File f) {
		String path = f.getAbsolutePath();
		me = new Media(new File(path).toURI().toString());
		mp = new MediaPlayer(me);
		System.out.println("vidéo trouvée");
		mvPlayer.setMediaPlayer(mp);
		mp.setAutoPlay(true);
	}
	
	@FXML public void gotoHelp() throws IOException {
		changeScene(FXMLLoader.load(getClass().getResource("/application/OpenDocEtuHelp.fxml")));
	}
	@FXML public void exitHelp() throws IOException{
		changeScene(FXMLLoader.load(getClass().getResource("/application/OpenDocEtu.fxml")));
	}
}
