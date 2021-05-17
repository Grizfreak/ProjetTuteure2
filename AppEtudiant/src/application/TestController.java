package application; 

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
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
		String path = f.getAbsolutePath().toString();
		System.out.println(path);
		File tmpFile = File.createTempFile("video", ".mp4");
		try {
			int nb=0;
			FileInputStream fis = new FileInputStream(f);
			FileOutputStream fos = new FileOutputStream(tmpFile);
			int octet = fis.read();
			System.out.println(f.length());
			while (octet != -1) {
					fos.write(octet);
					System.out.println(octet);
					octet=fis.read();
					nb++;
			}
			fos.close();
			fis.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		me= new Media(new File(path).toURI().toString());
		mp = new MediaPlayer(me);
		mp.setAutoPlay(true);
		mvPlayer.setMediaPlayer(mp);
	}

}
