package pandemic.aider.client.ui.log;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.springframework.security.crypto.bcrypt.BCrypt;
import pandemic.aider.client.CONSTANTS;
import pandemic.aider.client.model.UserDetails;
import pandemic.aider.client.service.ClientSideUserService;
import pandemic.aider.client.service.JsonServiceClient;
import pandemic.aider.client.ui.main.MainController;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

public class SignInController {
	
	@FXML
	private TextField passwordTextField, usernameTextField;
	
	@FXML
	private PasswordField passwordHiddenField;
	
	@FXML
	private CheckBox passwordCheckBoxToggle;
	
	@FXML
	private Label signInWarningLabel;
	
	//static variables
	public static Stage stage;
	
	public static Scene scene;
	
	public static Parent root;
	
	@FXML
	public void showPassword(ActionEvent event) {
		
		if(passwordCheckBoxToggle.isSelected()) {
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
		boolean correctCredentials = false;
		
		if(passwordCheckBoxToggle.isSelected()) {
			passwordHiddenField.setText(passwordTextField.getText());
		}
		if(!passwordCheckBoxToggle.isSelected()) {
			passwordTextField.setText(passwordHiddenField.getText());
		}
		
		UserDetails user = new UserDetails();
		user.setUsername(usernameTextField.getText());
		user.setPassword(passwordHiddenField.getText());
		
		if(checkPasswordSignIn(user)) {
			user.setPassword("");
			user.setPassword(BCrypt.hashpw(passwordHiddenField.getText(), CONSTANTS.PEPPER_PASSWORD));
		}
		if(checkUsername(user)) {
			if(checkPasswordSignIn(user)) {
				user = ClientSideUserService.checkCredentials(50004, user);
				assert user != null;
				correctCredentials = user.getName() != null;
			}
		}
		if(correctCredentials) {
			String jsonString = JsonServiceClient.userToJson(user);
			try {
				BufferedWriter bw = new BufferedWriter(new FileWriter("src/pandemic/aider/client/json/log.json"));
				
				bw.write(jsonString);
				bw.close();
				
				MainController.userDetailsStatic = JsonServiceClient.jsonToUser(jsonString);
				signInWarningLabel.setText("Successfully Logged In");
				showAlert();
			} catch(IOException e) {
				e.printStackTrace();
			}
		} else {
			signInWarningLabel.setText("Wrong credentials entered");
		}
	}
	
	@FXML
	public void switchToSignUp(ActionEvent event) throws IOException {
		//switch to sign up scene
		try {
			//doesn't work if fxml is in different page
			root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("SignUpFXML.fxml")));
			stage = (Stage) (((Node) event.getSource()).getScene().getWindow());
			
			scene = new Scene(root);
			stage.setScene(scene);
			stage.setResizable(false);
			stage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

//---------------------------------------------------------------------------------------------------------------------------------------------
	
	private boolean checkUsername(UserDetails obj) {
		
		boolean checkBool = false;
		if(obj.getUsername().equals("") || obj.getUsername() == null) {
			signInWarningLabel.setText("Username can't be empty");
			return false;
		} else {
			/*
			 * checks if username contains any different characters
			 * first for loop is for the string
			 * second for loop is to iterate through the main constant string
			 * if the stringChar matches the any of the char then the loop is broken and the loop is checked for new char from the string
			 * even if one char fails it will return false
			 */
			for(char stringChar : obj.getUsername().toCharArray()) {
				
				for(char constChar : CONSTANTS.ALLOWED_USERNAME_CHARS.toCharArray()) {
					
					if(stringChar == constChar) {
						checkBool = true;
						break;
					}
					checkBool = false;
				}
				if(!checkBool) {
					signInWarningLabel.setText("'" + stringChar + "' Not allowed in Username");
					return false;
				}
			}
			if(obj.getUsername().length() > 30) {
				signInWarningLabel.setText("Username cannot exceed 30 characters");
				return false;
			} else {
				return true;
			}
		}
	}
	
	private boolean checkPasswordSignIn(UserDetails obj) {
		
		if(obj.getPassword() == null || obj.getPassword().equals("")) {
			signInWarningLabel.setText("Password Cannot be empty");
			return false;
		} else {
			return true;
		}
	}
	
	private void showAlert() {
		
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
		alert.setTitle("Successful");
		alert.setHeaderText("You are now logged in");
		alert.setContentText("Press Ok to continue");
		Optional<ButtonType> result = alert.showAndWait();
		if(result.isPresent() && result.get() == ButtonType.OK) {
			SignInController.stage.close();
		}
	}
}