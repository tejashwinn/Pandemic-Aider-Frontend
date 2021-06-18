package pandemic.aider.client.ui.log;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class SignInStarter extends Application implements Runnable {
	
	@Override
	public void run() {
//		launch(CONSTANTS.JAVA_FX_ARGUMENTS);
		try {
			Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("SignInFXML.fxml")));
			Scene scene = new Scene(root, 280, 310);
			Stage stage = new Stage();
			stage.setTitle("Sign In");
			stage.setScene(scene);
			stage.setResizable(false);
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	@Override
	public void start(Stage stage) throws Exception {
	
	}
	
}

class temp1 {
	public static void main(String[] ags) {
		SignInStarter in = new SignInStarter();
		in.run();
	}
}