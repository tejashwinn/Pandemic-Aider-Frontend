//package pandemic.aider.client.oldfx.logIn;
//
//import pandemic.oldfx.model.Credential;
//import javafx.event.ActionEvent;
//import javafx.fxml.FXML;
//import javafx.scene.control.Label;
//import javafx.scene.control.PasswordField;
//import javafx.scene.control.TextField;
//
//import java.io.IOException;
//
//public class SignUpController {
//
//	@FXML
//	private TextField nameSignUpTextField, usernameSignUpTextField;
//
//	@FXML
//	private PasswordField passwordSignUpTextField, reEnterSignUpPasswordTextField;
//
//	@FXML
//	private Label reEnterSignUpLabel;
//
//	//todo the problem why it is not re displaying the values is because when the new instance is created it copys the empty values and it displays null;;
//
//	public boolean checkPassword(Credential obj) {
//		boolean checkBool;
//		if (obj.getPassword() == null || obj.getPassword().equals("")) {
//			reEnterSignUpLabel.setText("Password Cannot be empty");
//			return false;
//		} else if (obj.getRePassword() == null || obj.getRePassword().equals("")) {
//			reEnterSignUpLabel.setText("Re-type the password");
//			return false;
//		}
//
//		if (obj.getPassword().equals(obj.getRePassword())) {
//			reEnterSignUpLabel.setText("");
//			checkBool = true;
//		} else {
//			passwordSignUpTextField.setText("");
//			passwordSignUpTextField.setPromptText("Password");
//			reEnterSignUpPasswordTextField.setText("");
//			reEnterSignUpPasswordTextField.setPromptText("Re-Enter Password");
//			checkBool = false;
//		}
//		return checkBool;
//	}
//
//	public boolean checkUsername(Credential obj) {
//		boolean checkBool;
//		//todo pandemic.networking with database check with existing username
//		if (obj.getUsername().equals("") || obj.getUsername() == null) {
//			reEnterSignUpLabel.setText("Username can't be empty");
//			return false;
//		} else {
//			checkBool = true;
//		}
//		return checkBool;
//	}
//
//	public boolean checkName(Credential obj) {
//		boolean checkBool;
//		if (obj.getName().equals("") || obj.getName() == null) {
//			reEnterSignUpLabel.setText("Name can't be empty");
//			return false;
//		} else {
//			checkBool = true;
//		}
//
//		return checkBool;
//	}
//
//	public void createAccount(ActionEvent event) throws IOException {
//		boolean validCredential;
//		Credential newUser = new Credential();
//
//		newUser.setName(nameSignUpTextField.getText());
//		newUser.setUsername(usernameSignUpTextField.getText());
//		newUser.setPassword(passwordSignUpTextField.getText());
//		newUser.setRePassword(reEnterSignUpPasswordTextField.getText());
//		//todo have  show password
//		validCredential = checkName(newUser);
//		if (validCredential) {
//			validCredential = checkUsername(newUser);
//		}
//		if (validCredential) {
//			validCredential = checkPassword(newUser);
//		}
//		//after verification add to database
//
//	}
//}
