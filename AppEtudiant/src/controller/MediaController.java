package controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import ExerciseGestion.OculText;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.collections.MapChangeListener;
import javafx.event.ActionEvent;
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
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.media.MediaPlayer.Status;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

public class MediaController implements Initializable {
	@FXML private MediaView mvPlayer;
	@FXML private ImageView mp3Player;
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
	private String textoread;
	private OculText text;
	private File f;
	private File tmpFile;
	private int mediaLength;
	@Override
	public void initialize(URL arg0, ResourceBundle arg1){
		f=MenuController.f;
		//TODO GENERATE TEXT AVEC UNE FONCTION EMBARQUEE
		try {
			if(MenuController.ismp3) {
				tmpFile = File.createTempFile("media", ".mp3");
			}
			else {
				tmpFile = File.createTempFile("media", ".mp4");
			}
			tmpFile.deleteOnExit();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void launchexo() throws IOException {
		FileInputStream fis = new FileInputStream(f);
		BufferedReader bf = new BufferedReader(new FileReader(f));
		while(bf.ready()) {
			String bfLine=bf.readLine();
			if(bfLine.contains("nb :")){
				String numberOnly= bfLine.replaceAll("[^0-9]", "");
				System.out.println(numberOnly);
				mediaLength=Integer.parseInt(numberOnly);
			}
			if(bfLine.contains("TextOcculte:")) {
				textoread=bfLine.substring(13);
				bfLine=bf.readLine();
				while(!bfLine.contains("Caractere:")) {
					System.out.println(textoread);
					textoread+=" "+bfLine;
					bfLine=bf.readLine();
				}
			}
		}
		fis.close();
		FileInputStream fas = new FileInputStream(f);
		FileOutputStream fos = new FileOutputStream(tmpFile);
		int nb=0;
		byte[] bytes = fas.readNBytes(mediaLength);
			fos.write(bytes);
			System.out.println(nb);
		
		String path = tmpFile.getAbsolutePath();
		System.out.println(path);
		me = new Media(new File(path).toURI().toString());
		if(MenuController.ismp3) {
			me.getMetadata().addListener(new MapChangeListener<String, Object>() {
				@Override
				public void onChanged(Change<? extends String, ? extends Object> ch) {
					if (ch.wasAdded()) {
						handleMetadata(ch.getKey(), ch.getValueAdded());
					}
				}
			});
		}
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
		response.addEventFilter(KeyEvent.ANY, keyEvent -> {
			System.out.println(keyEvent);
			if(keyEvent.getCode() == KeyCode.ENTER)validateInput();
		});
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
		//text = new Text("Ce discours de Kennedy est considéré comme l'un de ses meilleurs, mais aussi comme un moment fort de la guerre froide. Il avait pour but de montrer le soutien des États-Unis aux habitants de l'Allemagne de l'Ouest, et notamment aux Berlinois de l'Ouest qui vivaient dans une enclave en Allemagne de l'Est — au milieu de territoires communistes, alors délimités depuis presque deux ans par le mur de Berlin — et craignaient une possible invasion de la part des troupes du bloc soviétique. Le discours tranche avec l'attitude peu engagée et assez tiède des États-Unis au début de la crise berlinoise. ");
		text = new OculText(textoread);
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
		Parent saved = FXMLLoader.load(getClass().getResource("/view/PopupSave.fxml"));
		Scene save = new Scene(saved);
		Stage saving = new Stage();
		saving.initModality(Modality.APPLICATION_MODAL);
		saving.setScene(save);
		saving.show();
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
	@FXML private void return10sec() {
		mp.seek(mp.getCurrentTime().subtract(Duration.seconds(10)));
	}
	@FXML private void skip10sec() {
		mp.seek(mp.getCurrentTime().add(Duration.seconds(10)));
	}
	@FXML private void showSolution() {
		response.setDisable(true);
		TextQuestion.setText(text.getText());
		
		
	}
	//TODO Dark mode
	//TODO Interface plus propre
	//TODO vérif Balsamik

}
