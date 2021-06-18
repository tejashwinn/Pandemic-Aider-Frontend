package pandemic.aider.client.ui.main;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import pandemic.aider.client.model.GetPostArrayList;
import pandemic.aider.client.ui.log.SignInStarter;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class MainController implements Initializable {
	
	@FXML
	private GridPane userGridPane;
	
	@FXML
	private BorderPane userBorderPane;
	
	@FXML
	public Button mainSignInButton;
	
	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		int row = 1;
		try {
			GetPostArrayList list = new GetPostArrayList();
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
	public void showUserDetails(ActionEvent event) {
		if (userBorderPane.isVisible()) {
			userBorderPane.setVisible(false);
		} else if (!userBorderPane.isVisible()) {
			userBorderPane.setVisible(true);
		}
	}
	
	@FXML
	public void showSignIn(ActionEvent event) throws Exception {
		SignInStarter in = new SignInStarter();
		
		in.run();
	}
	
	public void closeStage() {
	
	}
}
