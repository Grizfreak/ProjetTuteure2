package application;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.media.Media;
import javafx.scene.media.MediaView;
import javafx.stage.FileChooser;

public class TestController implements Initializable{
	@FXML private MediaView mvPlayer;
	static private File f;
	static private byte[] bytes;
	static private Media me;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
	}
	@FXML private void openFile() throws IOException {
		System.out.println("Opening..");
		FileChooser fc = new FileChooser();
		f = fc.showOpenDialog(null);
		me = new Media(f.getAbsolutePath());
		bytes = me.getSource().getBytes();
		for (int i=0;i<bytes.length;i++) {
			System.out.println(bytes[i]);
		}
	}

}
