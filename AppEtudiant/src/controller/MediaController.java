package controller;

import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import ExerciseGestion.OculText;
import application.Main;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayer.Status;
import javafx.scene.media.MediaView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

public class MediaController implements Initializable {
	@FXML private MediaView mvPlayer;
	@FXML private ImageView mp3Player;
	@FXML private Button playPause;
	@FXML private Button responsebox;
	@FXML private Button launch;
	@FXML private ImageView sound;
	@FXML private Label timer;
	@FXML private Slider timeSlider;
	@FXML private Slider volumeSlider;
	@FXML private TextArea TextQuestion;
	@FXML private Menu bonusmenu;
	@FXML private MenuItem afficheSol;
	@FXML private MenuItem afficheStat;
	@FXML private TextField response;
	@FXML private TextArea aide_text;
	@FXML private Label videoTime;
	@FXML private Button helpButton;
	@FXML private MenuItem save;
	@FXML private Label responseTextDisplay;
	@FXML private ImageView responseDisplay;
	@FXML private Label score;
	private MediaPlayer mp;
	private Media me; 
	private Integer minTime;
	private Integer secTime;
	private Timeline timeline;
	private boolean helpopened=false;
	private boolean volumeopened=false;
	private String textoread;
	private String aide;
	public static OculText text;
	private File f;
	private File tmpFile;
	private int mediaLength;
	private char mechar;
	public static boolean casse=false;
	public static boolean partiel =false;
	public static boolean allowSol =false;
	public static Integer minFinalTime;
	public static Integer secFinalTime;
	public static boolean allowStat =false;
	public static boolean mode_eval=false;
	@Override
	public void initialize(URL arg0, ResourceBundle arg1){
		score.setVisible(false);
		f=MenuController.f;
		sound.setImage(new Image(getClass().getResource("/images/png-clipart-computer-icons-sound-sound-logo-monochrome.png").toString()));
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

	public static OculText getText() {
		return text;
	}

	@SuppressWarnings({ "resource", "unused" })
	public void launchexo() throws IOException {
		FileInputStream fis = new FileInputStream(f);
		BufferedReader bf = new BufferedReader(new FileReader(f));
		while(bf.ready()) {
			String bfLine=bf.readLine();
			if(bfLine.contains("Nombre octet:")){
				String numberOnly= bfLine.trim().replaceAll("[^0-9]", "");
				System.out.println(numberOnly);
				mediaLength=Integer.parseInt(numberOnly);
			}
			if(bfLine.contains("TextOcculte:")) {
				textoread=bfLine.substring(13);
				bfLine=bf.readLine();
				while(!bfLine.contains("Caractere:")) {
					System.out.println(textoread);
					textoread+=bfLine;
					bfLine=bf.readLine();
					System.out.println(textoread);
				}
			}
			if(bfLine.contains("Caractere:")) {
				if(!bfLine.contains("null"))
					mechar=bfLine.charAt(12);
				else {
					mechar='#';
				}
			}
			if(bfLine.contains("Eval:")) {
				if(bfLine.contains("1"))
					mode_eval=true;
			}
			if(bfLine.contains("AffichR:")) {
				if(bfLine.contains("1"))
					allowStat=true;
			}
			if(bfLine.contains("RemplacementP:")) {
				if(bfLine.contains("1"))
					partiel=true;
			}
			if(bfLine.contains("BtnSolution:")) {
				if(bfLine.contains("1"))
					allowSol=true;
			}
			if(bfLine.contains("Time:")) {
				if(bfLine.contains("null")) {

				}
				else{
					minTime=Integer.parseInt(bfLine.substring(6,8));
					System.out.println(minTime);
					if(bfLine.contains("00")) {
						secTime=0;
					}
				}

			}
			if(bfLine.contains("Casse:")) {
				if(bfLine.contains("1"))
					casse=true;
			}
			if(bfLine.contains("aide:")) {
				aide=bfLine.substring(6);
				bfLine=bf.readLine();
				while(bf.ready()) {
					aide +=" "+bfLine;
					bfLine=bf.readLine();
				}
			}
		}
		bf.close();
		fis.close();
		if(mode_eval) {
			bonusmenu.setVisible(false); 
			save.setDisable(true);
		}
		else bonusmenu.setDisable(false);
		if(!allowSol)afficheSol.setDisable(true);
		if(!allowStat)afficheStat.setDisable(true);
		FileInputStream fas = new FileInputStream(f);
		FileOutputStream fos = new FileOutputStream(tmpFile);
		int nb=0;
		byte[] bytes = fas.readNBytes(mediaLength);
		fos.write(bytes);
		System.out.println("g fini de lire");
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
		videoTime.setVisible(true);
		helpButton.setVisible(true);


		videoTime.setText("Video : "+formatTime(mp.getCurrentTime(), me.getDuration()));
		response.addEventFilter(KeyEvent.KEY_RELEASED, keyEvent -> {
			if(keyEvent.getCode() == KeyCode.ENTER)
				try {
					validateInput();
				} catch (IOException e) {
					e.printStackTrace();
				}
		});
		text = new OculText(textoread+' ',aide,mechar,casse,partiel,allowSol,allowStat);
		System.out.println(text.getText());
		System.out.println(text.getTextCache());
		TextQuestion.setText(text.getTextCache());
		aide_text.setText(aide);
		//*******************************************************ICI SE TROUVENT LES FONCTIONS CHRONO et TIMER************************************//
		if(mode_eval) {
			timerCreation();	
		}
		else {
			stopWatchCreation();
		}
		//*******************************************************ICI SE TROUVENT LES FONCTIONS CHRONO et TIMER************************************//
		timer.setText("Temps restant : " + minTime.toString()+":"+secTime.toString()+"s");
		handleTime();
		volumeSlider.setValue(mp.getVolume() * 100);
		volumeSlider.valueProperty().addListener(new InvalidationListener() {
			@Override
			public void invalidated(Observable observable) {
				mp.setVolume(volumeSlider.getValue()/100);
			}
		});
		Main.actualRoot.getScene().addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>(){

			@Override
			public void handle(KeyEvent arg0) {
				if(arg0.getCode() == KeyCode.SPACE)playOrPause();
				if(arg0.getCode() == KeyCode.RIGHT)skip10sec();
				if(arg0.getCode() == KeyCode.LEFT)return10sec();
				if(arg0.getCode() == KeyCode.UP)volumeSlider.setValue(volumeSlider.getValue()+10);
				if(arg0.getCode() == KeyCode.DOWN)volumeSlider.setValue(volumeSlider.getValue()-10);;
				
			}
			
		});
	}

	@FXML public void gotoHelp() throws IOException {
		if(helpopened) {
			TextQuestion.setDisable(false);
			response.setDisable(false);
			responsebox.setDisable(false);
			aide_text.setVisible(false);
			helpopened=false;
		}
		else {
			TextQuestion.setDisable(true);
			aide_text.setEditable(false);
			response.setDisable(true);
			responsebox.setDisable(true);
			aide_text.setVisible(true);
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
	public void pause() {
		mp.stop();
		timeline.stop();
		minFinalTime=minTime;
		secFinalTime=secTime;

	}
	@FXML public void openSave() throws IOException {
		pause();
		Parent saved = FXMLLoader.load(getClass().getResource("/view/PopupSave.fxml"));
		Scene save = new Scene(saved);
		Stage saving = new Stage();
		SaveController.stage=saving;
		saving.initStyle(StageStyle.UNDECORATED);
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
			videoTime.setText("Video : "+formatTime(mp.getCurrentTime(), me.getDuration()));
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
						secTime--;
						if (secTime < 0) {
							minTime--;
							secTime=59;
						}
						// update timerLabel
						if(secTime<10) {
							timer.setText("Temps restant : " + minTime.toString()+":0"+secTime.toString()+"s");
						}
						else timer.setText("Temps restant : " + minTime.toString()+":"+secTime.toString()+"s");
						if (secTime <= 0 && minTime <=0) {
							timeline.stop();
							try {
								openStats();
							} catch (IOException e) {
								e.printStackTrace();
							}
							return;
						}

					}
				}));
		timeline.playFromStart();
	}
	public void stopWatchCreation() {
		minTime=0;
		secTime=0;
		timeline = new Timeline();
		timeline.setCycleCount(Timeline.INDEFINITE);
		timeline.getKeyFrames().add(
				new KeyFrame(Duration.seconds(1),
						new EventHandler<ActionEvent>() {
					// KeyFrame event handler
					@Override
					public void handle(ActionEvent arg0) {
						secTime++;
						// update timerLabel
						if(secTime<10) {
							timer.setText("Temps écoulé : " + minTime.toString()+":0"+secTime.toString()+"s");
						}
						else timer.setText("Temps écoulé : " + minTime.toString()+":"+secTime.toString()+"s");
						if (helpopened) {
							timeline.stop();
						}
						if(secTime>=59) {
							minTime++;
							secTime=0;
						}
					}
				}));
		timeline.playFromStart();
	}

	@FXML public void validateInput() throws IOException {
		String moche = response.getText();
		moche.trim();
		String previous = text.getTextCache();
		text.searchAndReplace(moche);
		responseDisplay.setVisible(true);
		responseTextDisplay.setVisible(true);
		if(previous.equals(text.getTextCache())) {
			System.out.println("fonction entrée -----------------------------------------------------");
			responseDisplay.setImage(new Image(getClass().getResource("/images/red_cross.png").toString()));
			responseTextDisplay.setText("Mince ! Le mot n'existe pas");
		}
		else {
			responseTextDisplay.setVisible(true);
			responseDisplay.setImage(new Image(getClass().getResource("/images/green_check.png").toString()));
			responseTextDisplay.setText("Bravo ! le mot est validé");
		}
		if(score.isVisible()) {
			score.setText("Score actuel : "+text.getMotTrouves()+"/"+text.getNbmots());
		}
		TextQuestion.setText(text.getTextCache());
		response.setText("");
		if(text.isFinished()) {
			timeline.stop();
			openStats();
		}
	}

	@FXML private void return10sec() {
		mp.seek(mp.getCurrentTime().subtract(Duration.seconds(10)));
	}

	@FXML private void skip10sec() {
		mp.seek(mp.getCurrentTime().add(Duration.seconds(10)));
	}

	@FXML private void showSolution() throws IOException {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Confirmation");
		alert.setHeaderText("Souhaitez-vous terminer l'exercice et voir la solution ?");

		ButtonType confirm = new ButtonType("Oui");
		ButtonType cancel = new ButtonType("Annuler");

		// Remove default ButtonTypes
		alert.getButtonTypes().clear();

		alert.getButtonTypes().addAll(confirm, cancel);

		// option != null.
		Optional<ButtonType> option = alert.showAndWait();
		if (option.get() == confirm) {
			openStats();
		} else if (option.get() == cancel) {
		}
		
	}

	public void openStats() throws IOException {
		pause();
		Parent saved = FXMLLoader.load(getClass().getResource("/view/Stats.fxml"));
		Scene save = new Scene(saved);
		Stage saving = new Stage();
		StatsController.stage=saving;
		saving.initStyle(StageStyle.UTILITY);
		saving.initModality(Modality.APPLICATION_MODAL);
		saving.setScene(save);
		saving.show();
	}

	@FXML private void showStats() {
		score.setVisible(true);
		score.setText("Score actuel : "+text.getMotTrouves()+"/"+text.getNbmots());
		afficheStat.setDisable(true);
	}
	@FXML public void gotoTuto() throws MalformedURLException, IOException, URISyntaxException {
		Desktop.getDesktop().browse(new URL("https://docs.google.com/document/d/1c6n0R5gJ0yDycuakt2MQ-LhVJZ7AuPJbn3NZLCKV4TQ/edit?usp=sharing").toURI());
	}

	private static String formatTime(Duration elapsed, Duration duration) {
		int intElapsed = (int)Math.floor(elapsed.toSeconds());
		int elapsedHours = intElapsed / (60 * 60);
		if (elapsedHours > 0) {
			intElapsed -= elapsedHours * 60 * 60;
		}
		int elapsedMinutes = intElapsed / 60;
		int elapsedSeconds = intElapsed - elapsedHours * 60 * 60 
				- elapsedMinutes * 60;

		if (duration.greaterThan(Duration.ZERO)) {
			int intDuration = (int)Math.floor(duration.toSeconds());
			int durationHours = intDuration / (60 * 60);
			if (durationHours > 0) {
				intDuration -= durationHours * 60 * 60;
			}
			int durationMinutes = intDuration / 60;
			int durationSeconds = intDuration - durationHours * 60 * 60 - 
					durationMinutes * 60;
			if (durationHours > 0) {
				return String.format("%d:%02d:%02d/%d:%02d:%02d", 
						elapsedHours, elapsedMinutes, elapsedSeconds,
						durationHours, durationMinutes, durationSeconds);
			} else {
				return String.format("%02d:%02d/%02d:%02d",
						elapsedMinutes, elapsedSeconds,durationMinutes, 
						durationSeconds);
			}
		} else {
			if (elapsedHours > 0) {
				return String.format("%d:%02d:%02d", elapsedHours, 
						elapsedMinutes, elapsedSeconds);
			} else {
				return String.format("%02d:%02d",elapsedMinutes, 
						elapsedSeconds);
			}
		}
	}
}
