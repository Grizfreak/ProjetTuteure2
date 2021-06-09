package application;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;


public class Main extends Application {
	public static Parent actualRoot;
	public static double width;
	public static double height;
	@Override
	public void start(Stage primaryStage) {
		try {
			Rectangle2D screenBounds = Screen.getPrimary().getBounds();
			width=screenBounds.getWidth();
			height=screenBounds.getHeight();
			Parent root = FXMLLoader.load(getClass().getResource("/view/AppEtu.fxml"));
			actualRoot=root;
			Scene scene = new Scene(root,width,height);
			scene.getStylesheets().add(getClass().getResource("/application/application.css").toString());
			primaryStage.setScene(scene);
			primaryStage.setResizable(false);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
