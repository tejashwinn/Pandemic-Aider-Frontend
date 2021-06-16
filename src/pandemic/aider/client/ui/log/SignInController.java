package pandemic.aider.client.ui.log;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class SignInController {
	@FXML
	private TextField passwordTextField, usernameTextField;
	@FXML
	private PasswordField passwordHiddenField;
	@FXML
	private CheckBox passwordCheckBoxToggle;
	
	public static Stage stage;
	public static Scene scene;
	public static Parent root;
	
	@FXML
	public void showPassword(ActionEvent event) {
		if (passwordCheckBoxToggle.isSelected()) {
			//shows the text fields
			passwordTextField.setText(passwordHiddenField.getText());
			passwordTextField.setVisible(true);
			passwordHiddenField.setVisible(false);
			return;
		}
		//shows the hidden fields
		passwordHiddenField.setText(passwordTextField.getText());
		passwordHiddenField.setVisible(true);
		passwordTextField.setVisible(false);
	}
	
	@FXML
	public void checkCorrectCredentials(ActionEvent event) {
		//to change values when edited on different toggles
		if (passwordCheckBoxToggle.isSelected()) {
			passwordHiddenField.setText(passwordTextField.getText());
		}
		if (!passwordCheckBoxToggle.isSelected()) {
			passwordTextField.setText(passwordHiddenField.getText());
		}
	}
	
	@FXML
	public void switchToSignUp(ActionEvent event) throws IOException {
		//switch to sign up scene
		try {
//			Parent root = FXMLLoader.load((Objects.requireNonNull(getClass().getResource("pandemic/aider/client/ui/log/signup/SignUpFXML.fxml"))));
			root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("SignUpFXML.fxml")));
			stage = (Stage) (((Node) event.getSource()).getScene().getWindow());
			
			scene = new Scene(root);
			stage.setScene(scene);
			stage.setResizable(false);
			stage.show();
		} catch (Exception e) {
//			System.out.println(e);
			e.printStackTrace();
		}
	}
}
