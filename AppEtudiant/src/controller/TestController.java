package controller; 


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
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
	@FXML private ProgressIndicator pi;

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
		tmpFile = File.createTempFile("video", ".mp3");
		System.out.println(tmpFile.getAbsolutePath());
		loadAppConfigurationFile();
		me= new Media(new File(tmpFile.getAbsolutePath()).toURI().toString());
		mp = new MediaPlayer(me);
		mp.setAutoPlay(true);
		mvPlayer.setMediaPlayer(mp);
		tmpFile.deleteOnExit();
	}
	 private void loadAppConfigurationFile () {
	        Task<Void> task = new Task<Void>() {
	            @Override
	            public Void call() throws InterruptedException {
	                int max = 1000000;
	                int nb=0;
	                for (int i = 1; i <= max; i = i + 10) {
	                    if (isCancelled()) {
	                        break;
	                    }
	                    updateProgress(i, max);
	                    try {
	            			//TODO réfléchir à cette immondice
	            			FileInputStream fis = new FileInputStream(f);
	            			FileOutputStream fos = new FileOutputStream(tmpFile);
	            			int octet = fis.read();
	            			while (octet != -1) {
	            					fos.write(octet);
	            					/*if(octet == 58) {
	            						System.out.println(nb+" / "+f.length());
		            					System.out.println(octet);
	            					}*/
	            					octet=fis.read();
	            					nb++;
	            					i=max+1;
	            					
	            			}
	            			System.out.println("file used");
	            			fos.close();
	            			fis.close();
	            		} catch (IOException e) {
	            			e.printStackTrace();
	            		}
	                }
	                return null;
	            }
	        };
	        new Thread(task).start();
	    }

}
