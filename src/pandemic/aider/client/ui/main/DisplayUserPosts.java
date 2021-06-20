package pandemic.aider.client.ui.main;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import pandemic.aider.client.model.GetPostArrayList;
import pandemic.aider.client.service.ClientSidePostService;

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
			userGridPane.getChildren().clear();//to clear the  previous values
			GetPostArrayList list = new GetPostArrayList();
			userUsernameLabel.setText(string);
			list.setPostsList(ClientSidePostService.retrieveRequest(50006, string));
			for (int i = 0; i < list.getPostsList().size(); i++) {
				
				FXMLLoader fxmlLoader = new FXMLLoader();
				fxmlLoader.setLocation(getClass().getResource("ItemFXML.fxml"));
				AnchorPane anchorPane = fxmlLoader.load();
				
				ItemController itemController = fxmlLoader.getController();
				itemController.setData(list.getPostsList().get(i));
				
				userGridPane.add(anchorPane, 0, row++);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@FXML
	public void backActionEvent(ActionEvent event) {
		userBorderPane.setVisible(!userBorderPane.isVisible());
		userBorderPane.getChildren().clear();
	}
}
