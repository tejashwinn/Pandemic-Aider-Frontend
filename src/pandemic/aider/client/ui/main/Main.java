package pandemic.aider.client.ui.main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import pandemic.aider.client.CONSTANTS;

import java.util.Objects;

public class Main extends Application {
	
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage stage) throws Exception {
		
		Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("MainFXML.fxml")));
		Scene scene = new Scene(root, 1028, 701);
		stage.setScene(scene);
		stage.setTitle("Pandemic Aider");
		stage.getIcons().add(CONSTANTS.MAIN_LOGO);
		stage.show();
	}
}

