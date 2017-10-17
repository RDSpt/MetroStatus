package application.javafx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
		primaryStage.setTitle("Metro Status");
		primaryStage.setScene(new Scene(root, 300, 225));
		primaryStage.getIcons().add(new Image("/graphics/logo.png"));
		primaryStage.show();
	}
	
	public static void main(String[] args) {
		
		launch(args);
	}
}
