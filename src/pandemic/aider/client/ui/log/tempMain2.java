package pandemic.aider.client.ui.log;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Objects;

public class tempMain2 extends Application {
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage stage) throws Exception {
		
		try {
			Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("SignInFXML.fxml")));
			Scene scene = new Scene(root);
			stage.setScene(scene);
			stage.show();
			stage.setResizable(false);
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}
