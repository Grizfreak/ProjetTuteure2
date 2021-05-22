package application;
	
import javafx.application.Application;
import javafx.fxml.*;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;



public class Main extends Application {
	
	public static Parent actualRoot;
	public static double width;
	public static double height;


	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fx = new FXMLLoader(getClass().getResource("/application/Prof.fxml"));
        Parent root = fx.load();   
        Controller controller = fx.getController();
        Scene scene = new Scene(root, width, height);
        actualRoot =root;
        stage.setTitle("Application Anglais");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
        controller.stage = stage;
    }
	
	
}
