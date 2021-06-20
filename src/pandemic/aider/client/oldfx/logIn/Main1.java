package pandemic.aider.client.oldfx.logIn;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Objects;

public class Main1 extends Application {
	
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		try {
			Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("LogInScene.fxml")));
			primaryStage.setTitle("Pandemic-Aider");
			Scene scene = new Scene(root);

//		Image icon = new Image("pandemic/oldfx/res/new_icon.png");
//		primaryStage.getIcons().add(icon);
			
			primaryStage.setScene(scene);

//		String CSS = Objects.requireNonNull(this.getClass().getResource("Style.css")).toExternalForm();
//		scene.getStylesheets().add(CSS);
			
			primaryStage.show();
			primaryStage.setResizable(false);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
