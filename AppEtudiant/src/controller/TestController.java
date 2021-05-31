package controller; 


import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.FileChooser;

public class TestController implements Initializable{
	@FXML private MediaView mvPlayer;
	static private File f;
	static private File tmpFile;
	static private Media me;
	static private MediaPlayer mp;
	int video_length;
	@FXML private ProgressIndicator pi;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
	}
	@FXML private void openFile() throws IOException {
		 
		System.out.println("Opening..");
		FileChooser fc = new FileChooser();
		f = fc.showOpenDialog(null);
		String path = f.getAbsolutePath().toString();
		System.out.println(path);
		tmpFile = File.createTempFile("video", ".mp4");
		System.out.println(tmpFile.getAbsolutePath());
		
		
		FileInputStream fis = new FileInputStream(f);
		BufferedReader bf = new BufferedReader(new FileReader(f));
		while(bf.ready()) {
			String bfLine=bf.readLine();
			if(bfLine.contains("nb :")){
				String numberOnly= bfLine.replaceAll("[^0-9]", "");
				System.out.println(numberOnly);
				video_length=Integer.parseInt(numberOnly);
			}
		}
		fis.close();
		FileInputStream fas = new FileInputStream(f);
		FileOutputStream fos = new FileOutputStream(tmpFile);
		int octet = fas.read();
		int nb=0;
		while (nb <= video_length) {
				fos.write(octet);
				/*if(octet == 58) {
					System.out.println(nb+" / "+f.length());
					System.out.println(octet);
				}*/
				System.out.println(nb);
				octet=fas.read();
				nb++;
				
		}
		System.out.println("file used");
		fos.close();
		fas.close();
		
		me= new Media(new File(tmpFile.getAbsolutePath()).toURI().toString());
		mp = new MediaPlayer(me);
		mp.setAutoPlay(true);
		mvPlayer.setMediaPlayer(mp);
		tmpFile.deleteOnExit();
	}

}
