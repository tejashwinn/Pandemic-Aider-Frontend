package pandemic.aider.client.ui.log.signup;

//import at.favre.lib.crypto.bcrypt.BCrypt;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.springframework.security.crypto.bcrypt.BCrypt;
import pandemic.aider.client.CONSTANTS;
import pandemic.aider.client.model.UserRePassword;
import pandemic.aider.client.service.ClientSideService;

public class SignUpController {
	@FXML
	private TextField passwordTextField, confirmPasswordTextField, usernameTextField, nameTextField;
	@FXML
	private PasswordField passwordHiddenField, confirmPasswordHiddenField;
	@FXML
	private CheckBox passwordCheckBoxToggle;
	@FXML
	private Label signUpWarningLabel;
	
	public void signUpAction(ActionEvent event) {
		
		boolean success = false;
		UserRePassword userAdd = new UserRePassword();
		/*
		 * adding this code because when the user edits the password in view mode and hits sign up
		 * it doesn't get updated in hidden mode so this will help us to set it back to the normal
		 */
		if (passwordCheckBoxToggle.isSelected()) {
			passwordHiddenField.setText(passwordTextField.getText());
			confirmPasswordHiddenField.setText(confirmPasswordTextField.getText());
		}
		/*
		 * adding this code because when the user edits the password in hidden mode
		 */
		if (!passwordCheckBoxToggle.isSelected()) {
			passwordTextField.setText(passwordHiddenField.getText());
			confirmPasswordHiddenField.setText(confirmPasswordHiddenField.getText());
		}
		
		userAdd.setName(nameTextField.getText());
		userAdd.setUsername(usernameTextField.getText().toLowerCase());
		userAdd.setPassword(passwordHiddenField.getText());
		userAdd.setConfirmPassword(confirmPasswordHiddenField.getText());
//		userAdd.display();
		
		if (checkName(userAdd)) {
			if (checkUsername(userAdd)) {
				if (checkPassword(userAdd)) {
					userAdd.setPassword("");
					//refer: https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/crypto/bcrypt/BCrypt.html
					//there are two BRYPT values the one used here is from spring boot
					userAdd.setPassword(BCrypt.hashpw(passwordHiddenField.getText(), CONSTANTS.PEPPER_PASSWORD));
//					userAdd.display();
				}
			}
		}

//		if (success) {
//			//proceed
//		} else {
//			//stay here
//		}
	}
	
	public boolean checkName(UserRePassword obj) {
		boolean checkBool;
		if (obj.getName().equals("") || obj.getName() == null) {
			signUpWarningLabel.setText("Name can't be empty");
			checkBool = false;
		} else {
			checkBool = true;
		}
		setExistingValues();
		return checkBool;
	}
	
	public boolean checkUsername(UserRePassword obj) {
		boolean checkBool = false;
		if (obj.getUsername().equals("") || obj.getUsername() == null) {
			signUpWarningLabel.setText("Username can't be empty");
			return false;
		} else {
			/*
			 * checks if username contains any different characters
			 * first for loop is for the string
			 * second for loop is to iterate through the main constant string
			 * if the stringChar matches the any of the char then the loop is broken and the loop is checked for new char from the string
			 * even if one char fails it will return false
			 */
			for (char stringChar : obj.getUsername().toCharArray()) {
				
				for (char constChar : CONSTANTS.ALLOWED_USERNAME_CHARS.toCharArray()) {
					
					if (stringChar == constChar) {
						checkBool = true;
						break;
					}
				}
				if (!checkBool) {
					signUpWarningLabel.setText(stringChar + " Not allowed in Username");
					return false;
				}
			}

			//if the username doesn't exist it will return true
			//else if the username exists it will return false
			checkBool = ClientSideService.checkExistingUserName(50000, obj.getUsername());
			if (!checkBool) {
//check delete				System.out.println("IN sign up: " + checkBool);
				signUpWarningLabel.setText("Username already exists");
			}
			return checkBool;
		}
	}
	
	public boolean checkPassword(UserRePassword obj) {
		boolean checkBool;
		if (obj.getPassword() == null || obj.getPassword().equals("")) {
			signUpWarningLabel.setText("Password Cannot be empty");
			return false;
		} else if (obj.getConfirmPassword() == null || obj.getConfirmPassword().equals("")) {
			signUpWarningLabel.setText("Confirm Password can't be empty");
			return false;
		}
		
		if (obj.getPassword().equals(obj.getConfirmPassword())) {
			signUpWarningLabel.setText("");
			checkBool = true;
		} else {
//			passwordTextField.setText("");
//			passwordTextField.setPromptText("Password");

//			confirmPasswordTextField.setText("");
//			confirmPasswordTextField.setPromptText(" Password");
			signUpWarningLabel.setText("Password doesn't match\nRe-enter password");
			
			checkBool = false;
		}
		setExistingValues();
		return checkBool;
	}
	
	//to view password when toggled
	@FXML
	public void showPassword(ActionEvent event) {
		if (passwordCheckBoxToggle.isSelected()) {
			//sets the text field value
			passwordTextField.setText(passwordHiddenField.getText());
			//shows the password text field
			passwordTextField.setVisible(true);
			passwordHiddenField.setVisible(false);
			
			//sets the value for the confirm text field
			confirmPasswordTextField.setText(confirmPasswordHiddenField.getText());
			//shows the confirm password text field
			confirmPasswordTextField.setVisible(true);
			confirmPasswordHiddenField.setVisible(false);
			return;
		}
		//sets the value for the text field
		passwordHiddenField.setText(passwordTextField.getText());
		passwordHiddenField.setVisible(true);
		passwordTextField.setVisible(false);
		//sets the value for the confirm password field
		confirmPasswordHiddenField.setText(confirmPasswordTextField.getText());
		confirmPasswordHiddenField.setVisible(true);
		confirmPasswordTextField.setVisible(false);
	}
	
	private void setExistingValues() {
		nameTextField.setText(nameTextField.getText());
		
		usernameTextField.setText(usernameTextField.getText());
		
		passwordTextField.setText(passwordTextField.getText());
		passwordHiddenField.setText(passwordHiddenField.getText());
		
		confirmPasswordTextField.setText(confirmPasswordTextField.getText());
		confirmPasswordHiddenField.setText(confirmPasswordHiddenField.getText());
	}
}


