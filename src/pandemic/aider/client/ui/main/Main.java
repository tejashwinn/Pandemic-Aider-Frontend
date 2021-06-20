package pandemic.aider.client.ui.main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.util.Objects;

public class Main extends Application {
	
	public static String[] vmArguments;
	
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage stage) throws Exception {
		
		Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("MainFXML.fxml")));
		Scene scene = new Scene(root, 1028, 701);
		stage.setScene(scene);
		stage.setTitle("Pandemic Aider");
		Image image = new Image("pandemic/aider/client/res/icons8-covid-19-64-main-icon-green.png");
		stage.getIcons().add(image);
//		stage.setResizable(false);
		stage.show();
	}
}

