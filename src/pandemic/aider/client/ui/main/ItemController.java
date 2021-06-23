package pandemic.aider.client.ui.main;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import pandemic.aider.client.model.PostDetails;
import pandemic.aider.client.service.ClientSidePostService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

public class ItemController {
	
	@FXML
	private Label usernameLabelView;
	
	@FXML
	private Label pincodeLabel;
	
	@FXML
	private Label postLabel;
	
	@FXML
	private Label timeLabel;
	
	public PostDetails postDetails;
	
	private String changeTime(String time) throws ParseException {
		SimpleDateFormat previousFormat = new SimpleDateFormat("yyyy:MM:dd::HH:mm:ss");
		
		SimpleDateFormat displayFormat = new SimpleDateFormat("dd MMM yyyy HH:mm:ss");
		
		Date date = previousFormat.parse(time);
		return displayFormat.format(date);
	}
	
	public void setData(PostDetails post) throws ParseException {
		postDetails = post;
		usernameLabelView.setText(post.getUserUsername());
		pincodeLabel.setText("Location: " + post.getPincode());
		postLabel.setText(post.getContent());
		timeLabel.setText("Posted On: " + changeTime(post.getTime()));
	}
	
//	public void setData(){
//		postDetails = ;
//		usernameLabelView.setText(post.getUserUsername());
//		pincodeLabel.setText("Location: " + post.getPincode());
//		postLabel.setText(post.getContent());
//		timeLabel.setText("Posted On: " + changeTime(post.getTime()));
//	}
	
	public void deletePost(ActionEvent event) {
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
		alert.setTitle("Conformation");
		alert.setHeaderText("The post cannot be retrieved");
		alert.setContentText("Press Ok to delete the post");
		Optional<ButtonType> result = alert.showAndWait();
		if (result.isPresent() && result.get() == ButtonType.OK) {
			if (MainController.userDetailsStatic.getUsername().equals(postDetails.getUserUsername())) {
				if (ClientSidePostService.deletePost(50010, postDetails.getPostUniqueId())) {
					
					Alert alertSuccess = new Alert(Alert.AlertType.INFORMATION);
					alertSuccess.setTitle("Deletion");
					alertSuccess.setHeaderText("Successfully deleted");
					alertSuccess.setContentText(postDetails.getPostUniqueId() + "was successfully deleted from the database");
					Optional<ButtonType> resultSuccess = alertSuccess.showAndWait();
					if (resultSuccess.isPresent() && resultSuccess.get() == ButtonType.OK) {
						MainController.refreshUser();
					}
				} else {
					Alert alertFailedDeletion = new Alert(Alert.AlertType.ERROR);
					alertFailedDeletion.setTitle("Deletion");
					alertFailedDeletion.setHeaderText("Deletion Failed");
					alertFailedDeletion.setContentText(postDetails.getPostUniqueId() + "was not deleted ");
					Optional<ButtonType> resultFailedDeletion = alertFailedDeletion.showAndWait();
					if (resultFailedDeletion.isPresent() && resultFailedDeletion.get() == ButtonType.OK) {
						MainController.refreshUser();
					}
				}
			} else {
				Alert alertWrongUsername = new Alert(Alert.AlertType.ERROR);
				alertWrongUsername.setTitle("Deletion");
				alertWrongUsername.setHeaderText("Deletion Failed");
				alertWrongUsername.setContentText("The post wasn't posted by you");
				Optional<ButtonType> resultWrongUsername = alertWrongUsername.showAndWait();
				if (resultWrongUsername.isPresent() && resultWrongUsername.get() == ButtonType.OK) {
					MainController.refreshUser();
				}
			}
		}
	}
}
