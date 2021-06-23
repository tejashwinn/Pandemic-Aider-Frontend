package pandemic.aider.client.service;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import pandemic.aider.client.ui.log.SignInController;

import java.util.Optional;

public class ShowAlert {
	
	public static void Alert() {
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setTitle("Error");
		alert.setHeaderText("Network failure");
		alert.setContentText("Press Ok to continue");
		Optional<ButtonType> result = alert.showAndWait();
		if (result.isPresent() && result.get() == ButtonType.OK) {
			SignInController.stage.close();
		}
	}
}
