//package pandemic.aider.client.ui.log;
//
//import javafx.event.ActionEvent;
//import javafx.fxml.FXML;
//import javafx.scene.control.*;
//import org.springframework.security.crypto.bcrypt.BCrypt;
//import pandemic.aider.client.CONSTANTS;
//import pandemic.aider.client.model.UserDetails;
//import pandemic.aider.client.model.UserRePassword;
//import pandemic.aider.client.service.ClientSideUserService;
//import pandemic.aider.client.service.JsonServiceClient;
//import pandemic.aider.client.ui.main.MainController;
//
//import java.io.BufferedWriter;
//import java.io.FileWriter;
//import java.io.IOException;
//import java.time.LocalDateTime;
//import java.time.format.DateTimeFormatter;
//import java.util.Optional;
//import java.util.UUID;
//
//public class SignUpController {
//
//	@FXML
//	private TextField passwordTextFieldSignUp, confirmPasswordTextFieldSignUp, usernameTextFieldSignUp, nameTextFieldSignUp;
//
//	@FXML
//	private PasswordField passwordHiddenFieldSignUp, confirmPasswordHiddenFieldSignUp;
//
//	@FXML
//	private CheckBox passwordCheckBoxToggleSignUp;
//
//	@FXML
//	private Label signUpWarningLabelSignUp;
//
//	@FXML
//	public void signUpActionSignUp(ActionEvent event) {
//
//		boolean validEntry = false;
//		UserRePassword userAdd = new UserRePassword();
//		UserDetails newCopyObject = null;
//		/*
//		 * adding this code because when the user edits the password in view mode and hits sign up
//		 * it doesn't get updated in hidden mode so this will help us to set it back to the normal
//		 */
//		if(passwordCheckBoxToggleSignUp.isSelected()) {
//			passwordHiddenFieldSignUp.setText(passwordTextFieldSignUp.getText());
//			confirmPasswordHiddenFieldSignUp.setText(confirmPasswordTextFieldSignUp.getText());
//		}
//		/*
//		 * adding this code because when the user edits the password in hidden mode
//		 */
//		if(!passwordCheckBoxToggleSignUp.isSelected()) {
//			passwordTextFieldSignUp.setText(passwordHiddenFieldSignUp.getText());
//			confirmPasswordHiddenFieldSignUp.setText(confirmPasswordHiddenFieldSignUp.getText());
//		}
//		//assigning the entered values to the object
//		userAdd.setName(nameTextFieldSignUp.getText());
//		userAdd.setUsername(usernameTextFieldSignUp.getText().toLowerCase());
//		userAdd.setPassword(passwordHiddenFieldSignUp.getText());
//		userAdd.setConfirmPassword(confirmPasswordHiddenFieldSignUp.getText());
//
//		if(checkNameSignUp(userAdd)) {
//			if(checkUsernameSignUp(userAdd)) {
//				if(checkPasswordSignUp(userAdd)) {
//					userAdd.setPassword("");
//					//refer: https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/crypto/bcrypt/BCrypt.html
//					//there are two BCRYPT values the one used here is from spring boot
//					userAdd.setPassword(BCrypt.hashpw(passwordHiddenFieldSignUp.getText(), CONSTANTS.PEPPER_PASSWORD));
//					validEntry = true;
//				}
//			}
//		}
//		if(validEntry) {
//			//sets UUID
//			userAdd.setUniqueId(UUID.randomUUID().toString());//
//
//			//time generator
//			DateTimeFormatter dateTimeFormatterClientAdduser = DateTimeFormatter.ofPattern("yyyy:MM:dd::HH:mm:ss");
//			LocalDateTime userCreatedTimeGenerator = LocalDateTime.now();
//
//			//sets user creation time
//			userAdd.setTime(dateTimeFormatterClientAdduser.format(userCreatedTimeGenerator));
//
//			newCopyObject = new UserDetails(userAdd);
//
//			validEntry = ClientSideUserService.addUser(50003, newCopyObject);
//
//			if(validEntry) {
//
//				newCopyObject.setPassword("");
//				String jsonString = JsonServiceClient.userToJson(newCopyObject);
//				try {
//					BufferedWriter bw = new BufferedWriter(new FileWriter("src/pandemic/aider/client/json/log.json"));
//
//					bw.write(jsonString);
//					bw.close();
//
//					MainController.userDetailsStatic = JsonServiceClient.jsonToUser(jsonString);
////					newUserSign = JsonServiceClient.jsonToUser(jsonString);
////					MainController.reloadPageStatic();
//					signUpWarningLabelSignUp.setText("Successfully created the account");
//					showAlertSignUp();
//				} catch(IOException e) {
//					e.printStackTrace();
//				}
//			} else {
//				signUpWarningLabelSignUp.setText("Account was not created");
//			}
//		}
//	}
//
//	//to view password when toggled
//	@FXML
//	public void showPasswordSignUp(ActionEvent event) {
//
//		if(passwordCheckBoxToggleSignUp.isSelected()) {
//			//sets the text field value
//			passwordTextFieldSignUp.setText(passwordHiddenFieldSignUp.getText());
//			//shows the password text field
//			passwordTextFieldSignUp.setVisible(true);
//			passwordHiddenFieldSignUp.setVisible(false);
//
//			//sets the value for the confirm text field
//			confirmPasswordTextFieldSignUp.setText(confirmPasswordHiddenFieldSignUp.getText());
//			//shows the confirm password text field
//			confirmPasswordTextFieldSignUp.setVisible(true);
//			confirmPasswordHiddenFieldSignUp.setVisible(false);
//			return;
//		}
//		//sets the value for the text field
//		passwordHiddenFieldSignUp.setText(passwordTextFieldSignUp.getText());
//		passwordHiddenFieldSignUp.setVisible(true);
//		passwordTextFieldSignUp.setVisible(false);
//		//sets the value for the confirm password field
//		confirmPasswordHiddenFieldSignUp.setText(confirmPasswordTextFieldSignUp.getText());
//		confirmPasswordHiddenFieldSignUp.setVisible(true);
//		confirmPasswordTextFieldSignUp.setVisible(false);
//	}
//
////---------------------------------------------------------------------------------------------------------------------------------------------------------------------------
//
//	private boolean checkNameSignUp(UserRePassword obj) {
//
//		boolean checkBool;
//		if(obj.getName().equals("") || obj.getName() == null) {
//			signUpWarningLabelSignUp.setText("Name can't be empty");
//			checkBool = false;
//		} else {
//			checkBool = true;
//		}
//		if(obj.getName().length() > 30) {
//			signUpWarningLabelSignUp.setText("Name cannot exceed 30 characters");
//			checkBool = false;
//		}
//		setExistingValuesSignUp();
//		return checkBool;
//	}
//
//	private boolean checkUsernameSignUp(UserRePassword obj) {
//
//		boolean checkBool = false;
//		if(obj.getUsername().equals("") || obj.getUsername() == null) {
//			signUpWarningLabelSignUp.setText("Username can't be empty");
//			return false;
//		} else {
//			/*
//			 * checks if username contains any different characters
//			 * first for loop is for the string
//			 * second for loop is to iterate through the main constant string
//			 * if the stringChar matches the any of the char then the loop is broken and the loop is checked for new char from the string
//			 * even if one char fails it will return false
//			 */
//			for(char stringChar : obj.getUsername().toCharArray()) {
//
//				for(char constChar : CONSTANTS.ALLOWED_USERNAME_CHARS.toCharArray()) {
//
//					if(stringChar == constChar) {
//						checkBool = true;
//						break;
//					}
//					checkBool = false;
//				}
//				if(!checkBool) {
//					signUpWarningLabelSignUp.setText("'" + stringChar + "' Not allowed in Username");
//					return false;
//				}
//			}
//			if(obj.getUsername().length() > 30) {
//				signUpWarningLabelSignUp.setText("Username cannot exceed 30 characters");
//				checkBool = false;
//			} else {
//				//if the username doesn't exist it will return true
//				//else if the username exists it will return false
//				checkBool = ClientSideUserService.checkExistingUserName(50000, obj.getUsername());
//				if(!checkBool) {
////check delete				System.out.println("IN sign up: " + checkBool);
//					signUpWarningLabelSignUp.setText("Username already exists");
//				}
//			}
//		}
//		setExistingValuesSignUp();
//		return checkBool;
//	}
//
//	private boolean checkPasswordSignUp(UserRePassword obj) {
//
//		boolean checkBool;
//		if(obj.getPassword() == null || obj.getPassword().equals("")) {
//			signUpWarningLabelSignUp.setText("Password Cannot be empty");
//			return false;
//		} else if(obj.getConfirmPassword() == null || obj.getConfirmPassword().equals("")) {
//			signUpWarningLabelSignUp.setText("Confirm Password can't be empty");
//			return false;
//		}
//
//		if(obj.getPassword().equals(obj.getConfirmPassword())) {
//			checkBool = true;
//		} else {
//			signUpWarningLabelSignUp.setText("Password doesn't match\nRe-enter password");
//			checkBool = false;
//		}
//		setExistingValuesSignUp();
//		return checkBool;
//	}
//
//	private void setExistingValuesSignUp() {
//
//		nameTextFieldSignUp.setText(nameTextFieldSignUp.getText());
//
//		usernameTextFieldSignUp.setText(usernameTextFieldSignUp.getText());
//
//		passwordTextFieldSignUp.setText(passwordTextFieldSignUp.getText());
//		passwordHiddenFieldSignUp.setText(passwordHiddenFieldSignUp.getText());
//
//		confirmPasswordTextFieldSignUp.setText(confirmPasswordTextFieldSignUp.getText());
//		confirmPasswordHiddenFieldSignUp.setText(confirmPasswordHiddenFieldSignUp.getText());
//	}
//
//	private void showAlertSignUp() {
//
//		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
//		alert.setTitle("Successful");
//		alert.setHeaderText("Sign up complete");
//		alert.setContentText("Press Ok to continue");
//		Optional<ButtonType> result = alert.showAndWait();
//		if(result.isPresent() && result.get() == ButtonType.OK) {
//			SignInController.stage.close();
//		}
//	}
//}
