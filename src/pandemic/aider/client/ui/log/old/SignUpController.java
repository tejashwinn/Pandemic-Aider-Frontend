package pandemic.aider.client.ui.log.old;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.springframework.security.crypto.bcrypt.BCrypt;
import pandemic.aider.client.CONSTANTS;
import pandemic.aider.client.model.UserDetails;
import pandemic.aider.client.model.UserRePassword;
import pandemic.aider.client.service.ClientSideService;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class SignUpController {
	@FXML
	private TextField passwordTextField, confirmPasswordTextField, usernameTextField, nameTextField;
	@FXML
	private PasswordField passwordHiddenField, confirmPasswordHiddenField;
	@FXML
	private CheckBox passwordCheckBoxToggle;
	@FXML
	private Label signUpWarningLabel;
	
	@FXML
	public void signUpAction(ActionEvent event) {
		
		boolean validEntry = false;
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
		//assigning the entered values to the object
		userAdd.setName(nameTextField.getText());
		userAdd.setUsername(usernameTextField.getText().toLowerCase());
		userAdd.setPassword(passwordHiddenField.getText());
		userAdd.setConfirmPassword(confirmPasswordHiddenField.getText());
		
		if (checkName(userAdd)) {
			if (checkUsername(userAdd)) {
				if (checkPassword(userAdd)) {
					userAdd.setPassword("");
					//refer: https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/crypto/bcrypt/BCrypt.html
					//there are two BCRYPT values the one used here is from spring boot
					userAdd.setPassword(BCrypt.hashpw(passwordHiddenField.getText(), CONSTANTS.PEPPER_PASSWORD));
					validEntry = true;
				}
			}
		}
		if (validEntry) {
			//sets UUID
			userAdd.setUniqueId(UUID.randomUUID().toString());//
			
			//time generator
			DateTimeFormatter dateTimeFormatterClientAdduser = DateTimeFormatter.ofPattern("yyyy:MM:dd::HH:mm:ss");
			LocalDateTime userCreatedTimeGenerator = LocalDateTime.now();
			
			//sets user creation time
			userAdd.setTime(dateTimeFormatterClientAdduser.format(userCreatedTimeGenerator));
			
			UserDetails newCopyObject = new UserDetails(userAdd);
			
			validEntry = ClientSideService.addUser(50003, newCopyObject);
		}
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
	
	@FXML
	public void switchToSignIn(ActionEvent event) throws IOException {
//		try {
//
//			SignInController.root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("SignInFXML.fxml")));
//			SignInController.stage = (Stage) (((Node) event.getSource()).getScene().getWindow());
//
//			SignInController.scene = new Scene(SignInController.root);
//			SignInController.stage.setScene(SignInController.scene);
//			SignInController.stage.setResizable(false);
//			SignInController.stage.show();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		
		System.exit(0);
	}

//---------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	
	private boolean checkName(UserRePassword obj) {
		boolean checkBool;
		if (obj.getName().equals("") || obj.getName() == null) {
			signUpWarningLabel.setText("Name can't be empty");
			checkBool = false;
		} else {
			checkBool = true;
		}
		if (obj.getName().length() > 30) {
			signUpWarningLabel.setText("Name cannot exceed 30 characters");
			checkBool = false;
		}
		setExistingValues();
		return checkBool;
	}
	
	private boolean checkUsername(UserRePassword obj) {
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
					checkBool = false;
				}
				if (!checkBool) {
					signUpWarningLabel.setText("'" + stringChar + "' Not allowed in Username");
					return false;
				}
			}
			if (obj.getUsername().length() > 30) {
				signUpWarningLabel.setText("Username cannot exceed 30 characters");
				checkBool = false;
			} else {
				//if the username doesn't exist it will return true
				//else if the username exists it will return false
				checkBool = ClientSideService.checkExistingUserName(50000, obj.getUsername());
				if (!checkBool) {
//check delete				System.out.println("IN sign up: " + checkBool);
					signUpWarningLabel.setText("Username already exists");
				}
			}
		}
		setExistingValues();
		return checkBool;
	}
	
	private boolean checkPassword(UserRePassword obj) {
		boolean checkBool;
		if (obj.getPassword() == null || obj.getPassword().equals("")) {
			signUpWarningLabel.setText("Password Cannot be empty");
			return false;
		} else if (obj.getConfirmPassword() == null || obj.getConfirmPassword().equals("")) {
			signUpWarningLabel.setText("Confirm Password can't be empty");
			return false;
		}
		
		if (obj.getPassword().equals(obj.getConfirmPassword())) {
			checkBool = true;
		} else {
			signUpWarningLabel.setText("Password doesn't match\nRe-enter password");
			checkBool = false;
		}
		setExistingValues();
		return checkBool;
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


