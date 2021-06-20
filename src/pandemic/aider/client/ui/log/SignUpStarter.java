package pandemic.aider.client.ui.log;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class SignUpStarter implements Runnable {
	
	@Override
	public void run() {
		Parent root = null;
		try {
			root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("SignUpFXML.fxml")));
			Scene scene = new Scene(root, 280, 310);
			Stage stage = new Stage();
			stage.setScene(scene);
			stage.setTitle("Sign Up");
			Image image = new Image("pandemic/aider/client/res/icons8-covid-19-64-main-icon-green.png");
			stage.getIcons().add(image);
			stage.setResizable(false);
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

class temp {
	public static void main(String[] ags) throws InterruptedException {
		SignUpStarter in = new SignUpStarter();
		in.run();
	}
}

