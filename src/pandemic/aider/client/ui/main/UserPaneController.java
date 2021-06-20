package pandemic.aider.client.ui.main;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import pandemic.aider.client.model.UserDetails;

import java.io.IOException;

public class UserPaneController {
	UserDetails selectedUser;
	@FXML
	private Label usernameLabel;
	@FXML
	private Label nameLabel;
	
	public void setData(UserDetails user) {
		selectedUser = user;
		usernameLabel.setText("Name: " + user.getName());
		nameLabel.setText("UserName: " + user.getUsername());
	}
	
	@FXML
	public void viewUser(ActionEvent event) {
		try {
			FXMLLoader newFxmlLoader = new FXMLLoader();
			newFxmlLoader.setLocation(getClass().getResource("DisplayUserPosts.fxml"));
			BorderPane borderPane = newFxmlLoader.load();
			DisplayUserPosts displayUserPosts = newFxmlLoader.getController();
			displayUserPosts.setData(selectedUser.getUsername());
			//adds new children to the previous stack pane which was assign
			MainController.topStackPanePointerVarForViewingSearchUser.getChildren().addAll(borderPane);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
