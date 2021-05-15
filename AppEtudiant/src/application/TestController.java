package application; 

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.nio.file.Files;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.FileChooser;

public class TestController implements Initializable{
	@FXML private MediaView mvPlayer;
	static private File f;
	static private File media;
	static private byte[] bytes;
	static private Media me;
	static private MediaPlayer mp;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
	}
	@FXML private void openFile() throws IOException {
		System.out.println("Opening..");
		FileChooser fc = new FileChooser();
		f = fc.showOpenDialog(null);
		System.out.println(f.toURI().toString());
		bytes = Files.readAllBytes(f.toPath());
		for (int i=0;i<bytes.length;i++) {
			System.out.println(bytes[i]);
		}
		media=File.createTempFile("media", ".mp4");
		ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
		me = new Media(bis.toString());
		mp = new MediaPlayer(me);
		mp.setAutoPlay(true);
		mvPlayer.setMediaPlayer(mp);
	}

}
