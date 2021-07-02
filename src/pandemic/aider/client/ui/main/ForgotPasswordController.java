package pandemic.aider.client.ui.main;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import org.springframework.security.crypto.bcrypt.BCrypt;
import pandemic.aider.client.CONSTANTS;
import pandemic.aider.client.service.ClientSideUserService;

import java.util.Optional;

public class ForgotPasswordController {
	
	@FXML
	private HBox forgotPasswordHBox;
	
	@FXML
	private Label forgotPasswordWarningLabel;
	
	@FXML
	private TextField otpTextField;
	
	@FXML
	private TextField passwordTextFieldSignUp;
	
	@FXML
	private PasswordField passwordHiddenFieldSignUp;
	
	@FXML
	private TextField confirmPasswordTextFieldSignUp;
	
	@FXML
	private PasswordField confirmPasswordHiddenFieldSignUp;
	
	public Button getChangePasswordButton() {
		
		return changePasswordButton;
	}
	
	public void setChangePasswordButton(Button changePasswordButton) {
		
		this.changePasswordButton = changePasswordButton;
	}
	
	public Button getGetOtpButton() {
		
		return getOtpButton;
	}
	
	@FXML
	private Button changePasswordButton;
	
	@FXML
	private Button getOtpButton;
	
	private String otp;
	
	@FXML
	private CheckBox passwordCheckBoxToggle;
	
	@FXML
	public void cancelForgotPassword(ActionEvent event) {
		
		forgotPasswordHBox.setVisible(false);
		forgotPasswordHBox.getChildren().clear();
		
	}
	
	@FXML
	public void getOtpAction(ActionEvent event) {
		
		try {
			otp = ClientSideUserService.generateOtp(50015, MainController.userDetailsStatic.getPhoneNo());
			forgotPasswordWarningLabel.setText("OTP has been sent");
			
			changePasswordButton.setVisible(true);
			getOtpButton.setVisible(false);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	@FXML
	public void changePassword(ActionEvent event) {
//			it doesn't get updated in hidden mode so this will help us to set it back to the normal
		if(passwordCheckBoxToggle.isSelected()) {
			passwordHiddenFieldSignUp.setText(passwordTextFieldSignUp.getText());
			confirmPasswordHiddenFieldSignUp.setText(confirmPasswordTextFieldSignUp.getText());
		}
		
		// adding this code because when the user edits the password in hidden mode
		if(!passwordCheckBoxToggle.isSelected()) {
			passwordTextFieldSignUp.setText(passwordHiddenFieldSignUp.getText());
			confirmPasswordHiddenFieldSignUp.setText(confirmPasswordHiddenFieldSignUp.getText());
		}
		
		if(otp.equals(otpTextField.getText())) {
			if(!passwordHiddenFieldSignUp.getText().equals("")  ) {
				if(passwordHiddenFieldSignUp.getText().equals(confirmPasswordHiddenFieldSignUp.getText()))
				{
					if(ClientSideUserService.changePassword(50017, BCrypt.hashpw(passwordHiddenFieldSignUp.getText(), CONSTANTS.PEPPER_PASSWORD))) {
						forgotPasswordWarningLabel.setText("Password Changed Successfully");
						Alert alert = new Alert(Alert.AlertType.INFORMATION);
						
						alert.setTitle("Password changed successfully");
						alert.setHeaderText("Sign in/up");
						alert.setContentText("You need to sign in with your new password");
						
						Optional<ButtonType> result = alert.showAndWait();
						
						if(result.isPresent() && result.get() == ButtonType.OK) {
							MainController.mainLogOutActionStatic();
							cancelForgotPassword(event);
						}
					} else {
						forgotPasswordWarningLabel.setText("Password was not changed");
					}
				}else {
					forgotPasswordWarningLabel.setText("Passwrord doesn't match");
				}
			} else {
				forgotPasswordWarningLabel.setText("Password cannot be empty");
			}
		} else {
			forgotPasswordWarningLabel.setText("Wrong OTP entered. Verify!");
		}
	}
	
	@FXML
	public void showPassword(ActionEvent event) {
		
		if(passwordCheckBoxToggle.isSelected()) {
			//sets the text field value
			passwordTextFieldSignUp.setText(passwordHiddenFieldSignUp.getText());
			//shows the password text field
			passwordTextFieldSignUp.setVisible(true);
			passwordHiddenFieldSignUp.setVisible(false);
			
			//sets the value for the confirm text field
			confirmPasswordTextFieldSignUp.setText(confirmPasswordHiddenFieldSignUp.getText());
			//shows the confirm password text field
			confirmPasswordTextFieldSignUp.setVisible(true);
			confirmPasswordHiddenFieldSignUp.setVisible(false);
			return;
		}
		
		//sets the value for the text field
		passwordHiddenFieldSignUp.setText(passwordTextFieldSignUp.getText());
		passwordHiddenFieldSignUp.setVisible(true);
		passwordTextFieldSignUp.setVisible(false);
		//sets the value for the confirm password field
		confirmPasswordHiddenFieldSignUp.setText(confirmPasswordTextFieldSignUp.getText());
		confirmPasswordHiddenFieldSignUp.setVisible(true);
		confirmPasswordTextFieldSignUp.setVisible(false);
	}
	
}
