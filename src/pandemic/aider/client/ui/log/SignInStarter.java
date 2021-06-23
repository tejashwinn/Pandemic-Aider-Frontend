package pandemic.aider.client.ui.log;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class SignInStarter extends Application implements Runnable {
	
	@Override
	public void run() {
		try {
			Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("SignInFXML.fxml")));
			Scene scene = new Scene(root, 280, 310);
			Stage stage = new Stage();
			stage.setTitle("Sign In");
			Image image = new Image("pandemic/aider/client/res/icons8-calendar-19-60.png");
			stage.getIcons().add(image);
			stage.setScene(scene);
			stage.setResizable(false);
			SignInController.stage=stage;
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void start(Stage stage) throws Exception {
	
	}
}
