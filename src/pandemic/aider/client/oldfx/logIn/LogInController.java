package pandemic.aider.client.oldfx.logIn;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class LogInController {
	
	@FXML
	TextField usernameLogInTextField;
	
	@FXML
	PasswordField passwordLogInTextField; //login values
	
	@FXML
	Label reEnterLogInLabel;
	
	public void signInAction(ActionEvent event) {
		//todo
		
	}
	
	public void signUpAction(ActionEvent event) throws IOException {
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource("SignUpScene.fxml"));
		Parent root = loader.load();
		
		Stage stage = (Stage) (((Node) event.getSource()).getScene().getWindow());
		
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.setResizable(false);
		stage.show();
	}

//    public void createNewAccount(ActionEvent event) throws IOException {
//        String name = nameSignUpTextField.getText();
//        String username = usernameSignUPTextField.getText();
//        String password = passwordSignUPTextField.getText();
//        String rePassword = reEnterSignUpPasswordTextField.getText();
//
//        FXMLLoader loader = new FXMLLoader(getClass().getResource("SignUpScene.fxml"));
//        Parent root = loader.load();
//
//
//        SignUpController signUpController = loader.getController();
//        signUpController.wrongPassword(name, username, password, rePassword);
//
//        Stage stage = (Stage) (((Node) event.getSource()).getScene().getWindow());
//        Scene scene = new Scene(root);
//
//        stage.setScene(scene);
//        stage.show();
//
//    }
}
