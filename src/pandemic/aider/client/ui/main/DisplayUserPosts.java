package pandemic.aider.client.ui.main;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import pandemic.aider.client.model.GetPostArrayList;
import pandemic.aider.client.service.ClientSidePostService;

import java.util.Optional;

public class DisplayUserPosts {
	
	@FXML
	private Label userUsernameLabel;
	
	@FXML
	private GridPane userGridPane;
	
	@FXML
	private BorderPane userBorderPane;
	
	@FXML
	private TitledPane postViewTitledPane;
	
	public void setData(String string) {
		
		int row = 1;
		try {
			postViewTitledPane.setCollapsible(false);
			GetPostArrayList list = new GetPostArrayList();
			userUsernameLabel.setText(string);
			list.setPostsList(ClientSidePostService.retrieveRequest(50006, string));
			if(list.getPostsList() != null) {
				for(int i = 0; i < list.getPostsList().size(); i++) {
					
					FXMLLoader fxmlLoader = new FXMLLoader();
					fxmlLoader.setLocation(getClass().getResource("ItemFXML.fxml"));
					AnchorPane anchorPane = fxmlLoader.load();
					
					ItemController itemController = fxmlLoader.getController();
					itemController.setData(list.getPostsList().get(i), true);
					
					userGridPane.add(anchorPane, 0, row++);
				}
			} else {
				Alert alert = new Alert(Alert.AlertType.INFORMATION);
				alert.setTitle("Null");
				alert.setHeaderText("No posts");
				alert.setContentText("The user hasn't posted anything");
				Optional<ButtonType> result = alert.showAndWait();
				
				if(result.isPresent() && result.get() == ButtonType.OK) {
					userGridPane.getChildren().clear();
				}
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	@FXML
	public void backActionEvent() {
		
		userBorderPane.setVisible(!userBorderPane.isVisible());
		userBorderPane.getChildren().clear();
	}
}
