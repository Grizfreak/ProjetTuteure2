package application;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Set;
import javax.swing.Timer;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.collections.MapChangeListener;
import javafx.collections.ObservableMap;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayer.Status;
import javafx.scene.media.MediaView;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.Duration;

public class Controller implements Initializable{
	@FXML private MediaView mvPlayer;
	@FXML private ImageView mp3Player;
	@FXML private TextField path;
	@FXML private TextField fileName;
	@FXML private TextField name;
	@FXML private TextField firstName;
	@FXML private Button done;
	@FXML private Button playPause;
	@FXML private VBox responsebox;
	@FXML private Button launch;
	@FXML private Pane help;
	@FXML private Label timer;
	@FXML private Slider timeSlider;
	@FXML private Slider volumeSlider;
	@FXML private TextArea TextQuestion;
	@FXML private TextField response;
	private MediaPlayer mp;
	private Media me; 
	private Integer exotime=120;
	private Timeline timeline;
	private Integer timeSeconds = exotime;
	private boolean helpopened=false;
	private boolean volumeopened=false;
	private Text text;

	static private File f;
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		System.out.println("Launching....");

	}

	@FXML
	public void searchFile() throws IOException {
		System.out.println("Opening..");
		FileChooser fc = new FileChooser();
		fc.getExtensionFilters().add(new ExtensionFilter("mp3","*.mp3"));
		fc.getExtensionFilters().add(new ExtensionFilter("mp4","*.mp4"));
		f = fc.showOpenDialog(null);
		openFile(f);
	}
	private void openFile(File f) throws IOException {
		if (f!=null) {
			System.out.println("Selected File : "+ f.getAbsolutePath());
			if(f.getAbsolutePath().contains(".mp4")) {
				changeScene(FXMLLoader.load(getClass().getResource("/application/OpenDocEtu.fxml")));


			}
			if(f.getAbsolutePath().contains(".mp3")) {
				changeScene(FXMLLoader.load(getClass().getResource("/application/OpenDocEtump3.fxml")));
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
	public void onDragOver(DragEvent event) {
		if (event.getDragboard().hasFiles()) {
			event.acceptTransferModes(TransferMode.ANY);
		}
	}
	@FXML
	public void Drop(DragEvent event) throws IOException{
		List<File> files = event.getDragboard().getFiles();
		f = files.get(0);
		System.out.println("Sélection effectuée");
		openFile(f);
	}
	@FXML private void launchsong() {
		System.out.println("Entrée en mode mp3");
		String path = f.getAbsolutePath();
		Media m = new Media(new File(path).toURI().toString());
		m.getMetadata().addListener(new MapChangeListener<String, Object>() {
			@Override
			public void onChanged(Change<? extends String, ? extends Object> ch) {
				if (ch.wasAdded()) {
					handleMetadata(ch.getKey(), ch.getValueAdded());
				}
			}
		});
		launchexo();
		launch.setVisible(false);
	}

	@FXML
	public void exit() {
		Platform.exit();
		System.out.println("Execution ended");
	}

	public void launchexo() {
		String path = f.getAbsolutePath();
		System.out.println(path);
		me = new Media(new File(path).toURI().toString());
		System.out.println(me);
		mp = new MediaPlayer(me);
		System.out.println("vidéo trouvée");
		mvPlayer.setMediaPlayer(mp);
		mp.setAutoPlay(true);
		launch.setVisible(false);
		timeSlider.setDisable(false);
		volumeSlider.setDisable(false);
		timer.setText("Temps restant : " + timeSeconds.toString()+"s");
		handleTime();
		//*******************************************************ICI SE TROUVENT LES FONCTIONS CHRONO et TIMER************************************//
		//timerCreation();
		//stopWatchCreation();
		//*******************************************************ICI SE TROUVENT LES FONCTIONS CHRONO et TIMER************************************//
		volumeSlider.setValue(mp.getVolume() * 100);
		volumeSlider.valueProperty().addListener(new InvalidationListener() {
			@Override
			public void invalidated(Observable observable) {
				mp.setVolume(volumeSlider.getValue()/100);
			}
		});
		text = new Text("Ce discours de Kennedy est considéré comme l'un de ses meilleurs, mais aussi comme un moment fort de la guerre froide. Il avait pour but de montrer le soutien des États-Unis aux habitants de l'Allemagne de l'Ouest, et notamment aux Berlinois de l'Ouest qui vivaient dans une enclave en Allemagne de l'Est — au milieu de territoires communistes, alors délimités depuis presque deux ans par le mur de Berlin — et craignaient une possible invasion de la part des troupes du bloc soviétique. Le discours tranche avec l'attitude peu engagée et assez tiède des États-Unis au début de la crise berlinoise. ");
		TextQuestion.setText(text.getTextCache());
	}

	@FXML public void gotoHelp() throws IOException {
		if(helpopened) {
			help.setVisible(false);
			responsebox.setDisable(false);
			helpopened=false;
		}
		else {
			help.setVisible(true);
			responsebox.setDisable(true);
			helpopened=true;
		}
	}
	@FXML public void showVolume() throws IOException{
		if(volumeopened) {
			volumeSlider.setVisible(false); 
			volumeopened=false;
		}
		else {
			volumeSlider.setVisible(true);
			volumeopened=true;
		}
	}


	private void handleMetadata(String key, Object valueAdded) {
		System.out.println("Modif Handle");
		System.out.println(key +" | "+valueAdded);
		if(key.equals("image"))mp3Player.setImage((Image) valueAdded);

	}

	@FXML public void openSave() throws IOException {
		Parent saved = FXMLLoader.load(getClass().getResource("/application/PopupSave.fxml"));
		Scene save = new Scene(saved);
		Stage saving = new Stage();
		saving.setScene(save);
		saving.show();
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
	@FXML public void playOrPause() {
		if(mp.getStatus() == Status.PAUSED) {
			mp.play();
			playPause.setText("Pause");
		}
		else{
			mp.pause();
			playPause.setText("Play");
		}
	}
	public void handleTime() {
		InvalidationListener sliderChangeListener = o-> {
			Duration seekTo = Duration.seconds(((timeSlider.getValue())*me.getDuration().toSeconds())/timeSlider.getMax());
			mp.seek(seekTo);
			System.out.println(timeSlider.getValue());
		};
		timeSlider.valueProperty().addListener(sliderChangeListener);

		// Link the player's time to the slider
		mp.currentTimeProperty().addListener(l-> {
			// Temporarily remove the listener on the slider, so it doesn't respond to the change in playback time
			// I thought timeSlider.isValueChanging() would be useful for this, but it seems to get stuck at true
			// if the user slides the slider instead of just clicking a position on it.
			timeSlider.valueProperty().removeListener(sliderChangeListener);

			// Keep timeText's text up to date with the slider position.
			Duration currentTime = mp.getCurrentTime();
			int value = (int)((currentTime.toSeconds()*timeSlider.getMax())/me.getDuration().toSeconds());
			timeSlider.setValue(value);    

			// Re-add the slider listener
			timeSlider.valueProperty().addListener(sliderChangeListener);
		});
	}
	public void timerCreation() {
		timeline = new Timeline();
		timeline.setCycleCount(Timeline.INDEFINITE);
		timeline.getKeyFrames().add(
				new KeyFrame(Duration.seconds(1),
						new EventHandler<ActionEvent>() {
					// KeyFrame event handler
					@Override
					public void handle(ActionEvent arg0) {
						timeSeconds--;
						// update timerLabel
						timer.setText(
								"Temps restant : " + timeSeconds.toString()+"s");
						if (timeSeconds <= 0) {
							timeline.stop();
							//TODO fermer les moyens d'écrire à la fin du timer
						}
					}
				}));
		timeline.playFromStart();
	}
	public void stopWatchCreation() {
		timeSeconds=0;
		timeline = new Timeline();
		timeline.setCycleCount(Timeline.INDEFINITE);
		timeline.getKeyFrames().add(
				new KeyFrame(Duration.seconds(1),
						new EventHandler<ActionEvent>() {
					// KeyFrame event handler
					@Override
					public void handle(ActionEvent arg0) {
						timeSeconds++;
						// update timerLabel
						timer.setText(
								"Temps restant : " + timeSeconds.toString()+"s");
						if (helpopened) {
							timeline.stop();
						}
					}
				}));
		timeline.playFromStart();
	}
	
	@FXML public void validateInput() {
		String moche = response.getText();
		moche.trim();
		text.searchAndReplace(moche);
		TextQuestion.setText(text.getTextCache());
		response.setText("");
		
	}
	//TODO Dark mode
	//TODO Interface plus propre
	//TODO vérif Balsamik
}
