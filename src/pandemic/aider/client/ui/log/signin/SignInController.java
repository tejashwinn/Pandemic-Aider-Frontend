package pandemic.aider.client.ui.log.signin;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class SignInController {
	@FXML
	private TextField passwordTextField;
	@FXML
	private PasswordField passwordHiddenField;
	@FXML
	private CheckBox passwordCheckBoxToggle;
	
	@FXML
	public void showPassword(ActionEvent event) {
		if (passwordCheckBoxToggle.isSelected()) {
			
			passwordTextField.setText(passwordHiddenField.getText());
			passwordTextField.setVisible(true);
			passwordHiddenField.setVisible(false);
			return;
		}
		passwordHiddenField.setText(passwordTextField.getText());
		passwordHiddenField.setVisible(true);
		passwordTextField.setVisible(false);
	}
	
	
}
