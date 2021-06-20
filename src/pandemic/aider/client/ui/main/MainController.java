package pandemic.aider.client.ui.main;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import pandemic.aider.client.model.*;
import pandemic.aider.client.service.ClientSidePostService;
import pandemic.aider.client.service.ClientSideUserService;
import pandemic.aider.client.service.JsonServiceClient;
import pandemic.aider.client.ui.log.SignInStarter;

import java.io.BufferedReader;
import java.io.FileReader;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class MainController implements Initializable {
	
	public static StackPane topStackPanePointerVarForViewingSearchUser;
	
	public static UserDetails userStaticForRefresh;
	
	public static Label userNameLabelForRefresh, postRequestUsernameLabelForRefresh;
	
	public static GridPane userGridPaneForRefresh;
	
	@FXML
	public Button mainSignInButton, userRefreshButton;
	
	@FXML
	private GridPane userGridPane;
	
	@FXML
	private BorderPane userBorderPane, searchBorderPane, postRequestBorderPane;
	
	@FXML
	private RadioButton postsRadioButton, usersRadioButton, pincodeRadioButton;
	
	@FXML
	private TitledPane postsTitledPane, usersTitledPane;
	
	@FXML
	private Label userUsernameLabel, postRequestUsernameLabel;
	
	@FXML
	private StackPane topStackPane;
	
	@FXML
	private TextArea postRequestContentTextArea;
	
	@FXML
	private TextField postRequestPincodeTextField;
	
	@FXML
	private Label postRequestWarningLabel;
	
	@FXML
	private TextField searchTextField;
	
	@FXML
	private GridPane searchUserGridPane;
	
	ToggleGroup radioToggle = new ToggleGroup();
	
	public UserDetails getUser() {
		return userStaticForRefresh;
	}
	
	public void setUser(UserDetails user) {
		MainController.userStaticForRefresh = user;
	}
	
	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		//this will initialize the top stack pane which will be used to modify the content
		topStackPanePointerVarForViewingSearchUser = topStackPane;
		
		//set toggle
		postsRadioButton.setToggleGroup(radioToggle);
		usersRadioButton.setToggleGroup(radioToggle);
		pincodeRadioButton.setToggleGroup(radioToggle);
		
		userBorderPane.setVisible(false);
		postRequestBorderPane.setVisible(false);
		searchBorderPane.setVisible(true);
		
		userNameLabelForRefresh = userUsernameLabel;
		
		postRequestUsernameLabelForRefresh = postRequestUsernameLabel;
		
		userGridPaneForRefresh = userGridPane;
		
		loadLogInJson();
	}
	
	@FXML
	public void showUserDetails(ActionEvent event) {
		if (userBorderPane.isVisible()) {
			userBorderPane.setVisible(false);
		} else if (!userBorderPane.isVisible()) {
			userBorderPane.setVisible(true);
			postRequestBorderPane.setVisible(false);
			searchBorderPane.setVisible(false);
		}
	}

//------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	
	@FXML
	public void showPostRequest(ActionEvent event) {
		if (postRequestBorderPane.isVisible()) {
			postRequestBorderPane.setVisible(false);
		} else if (!postRequestBorderPane.isVisible()) {
			userBorderPane.setVisible(false);
			postRequestBorderPane.setVisible(true);
			searchBorderPane.setVisible(false);
		}
	}
	
	@FXML
	public void showSearchDetails(ActionEvent event) {
		if (searchBorderPane.isVisible()) {
			searchBorderPane.setVisible(false);
		} else if (!searchBorderPane.isVisible()) {
			userBorderPane.setVisible(false);
			postRequestBorderPane.setVisible(false);
			searchBorderPane.setVisible(true);
		}
	}

//postRequest
//!------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	
	@FXML
	public void showSignIn(ActionEvent event) throws Exception {
		SignInStarter in = new SignInStarter();
		in.run();
	}
	
	@FXML
	public void onRadioSelectedAction(ActionEvent event) throws Exception {
		
		if (usersRadioButton.isSelected()) {
			postsTitledPane.setVisible(false);
			usersTitledPane.setVisible(true);
		} else {
			usersTitledPane.setVisible(false);
			postsTitledPane.setVisible(true);
		}
	}
	
	@FXML
	public void refreshUser(ActionEvent event) {
		refreshUserPage();
	}
	
	private void loadLogInJson() {
		//takes the values from the json format to the user
		try {
			BufferedReader br = new BufferedReader(new FileReader("src/pandemic/aider/client/json/log.json"));
			String str = br.readLine();
			if (str != null) {
				userStaticForRefresh = JsonServiceClient.jsonToUser(str);
//				user.display();
				JsonSettings.LoggedIn = true;
			} else {
				userStaticForRefresh = null;
				SignInStarter in = new SignInStarter();
				in.run();
			}
			refreshUserPage();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void refreshUser() {
		userNameLabelForRefresh.setText(userStaticForRefresh.getUsername());
		postRequestUsernameLabelForRefresh.setText(userStaticForRefresh.getUsername());

//		user.display();
		//display posts on users
		int row = 1;
		try {
			GetPostArrayList list = new GetPostArrayList();
			list.setPostsList(ClientSidePostService.retrieveRequest(50006, userStaticForRefresh.getUsername()));
			
			if (list.getPostsList() != null) {
				for (int i = 0; i < list.getPostsList().size(); i++) {
					
					FXMLLoader fxmlLoader = new FXMLLoader();
					fxmlLoader.setLocation(MainController.class.getResource("ItemFXML.fxml"));
					AnchorPane anchorPane = fxmlLoader.load();
					
					ItemController itemController = fxmlLoader.getController();
					itemController.setData(list.getPostsList().get(i));
					
					userGridPaneForRefresh.add(anchorPane, 0, row++);
				}
			} else {
				userGridPaneForRefresh.getChildren().clear();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void refreshUserPage() {
		//display the user name on user scene
		userUsernameLabel.setText(userStaticForRefresh.getUsername());
		postRequestUsernameLabel.setText(userStaticForRefresh.getUsername());

//		user.display();
		//display posts on users
		int row = 1;
		try {
			userGridPane.getChildren().clear();
			GetPostArrayList list = new GetPostArrayList();
			list.setPostsList(ClientSidePostService.retrieveRequest(50006, userStaticForRefresh.getUsername()));
			
			if (list.getPostsList() != null) {
				for (int i = 0; i < list.getPostsList().size(); i++) {
					
					FXMLLoader fxmlLoader = new FXMLLoader();
					fxmlLoader.setLocation(getClass().getResource("ItemFXML.fxml"));
					AnchorPane anchorPane = fxmlLoader.load();
					
					ItemController itemController = fxmlLoader.getController();
					itemController.setData(list.getPostsList().get(i));
					
					userGridPane.add(anchorPane, 0, row++);
				}
			} else {
				userGridPane.getChildren().clear();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@FXML
	public void postAction(ActionEvent event) {
		PostDetails post = new PostDetails();
		
		post.setUserUsername(userStaticForRefresh.getUsername());
		post.setPincode(postRequestPincodeTextField.getText().strip());
		post.setContent(postRequestContentTextArea.getText().strip());
		
		//time generator
		DateTimeFormatter dateTimeFormatterClientAdduser = DateTimeFormatter.ofPattern("yyyy:MM:dd::HH:mm:ss");
		LocalDateTime userCreatedTimeGenerator = LocalDateTime.now();
		post.setTime(dateTimeFormatterClientAdduser.format(userCreatedTimeGenerator));
		
		post.setUserTags(checkHashtags(post.getContent()));
		
		if (checkForContent(post.getContent())) {
			if (checkForPincode(post.getPincode())) {
				//client

//				System.out.println(post.getContent());
				if (ClientSidePostService.postRequest(50005, post)) {
					postRequestWarningLabel.setText("Successfully posted");
					postRequestContentTextArea.setText("");
					postRequestPincodeTextField.setText("");
				}
			}
		}
	}
	
	@FXML
	public void cancelAction(ActionEvent event) {
		postRequestContentTextArea.setText("");
		postRequestPincodeTextField.setText("");
		postRequestWarningLabel.setText("");
	}
	
	//!------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	public ArrayList<String> checkHashtags(String string) {
		String[] arrOfString = string.split(" ");
		ArrayList<String> finalList = new ArrayList<>();
		boolean bool = false;
		for (String finalStr : arrOfString) {
			if ('#' == finalStr.strip().charAt(0)) {
				StringBuilder stringBuilder = new StringBuilder(finalStr);
				stringBuilder.deleteCharAt(0);
				for (int ch : stringBuilder.toString().toCharArray()) {
					if (Character.isLetterOrDigit(ch)) {
						bool = true;
					} else {
						bool = false;
						break;
					}
				}
				if (bool) {
					finalStr = "#" + stringBuilder;
					finalList.add(finalStr);
				}
			}
		}
		
		return finalList;
	}
	//-----------------------------------------------------------------------------------------------------------------------------------------
	
	public boolean checkForPincode(String string) {
		
		if (string.length() == 6) {
			for (char i : string.toCharArray()) {
				if (!Character.isDigit(i)) {
					return false;
				}
			}
			return true;
		} else {
			postRequestWarningLabel.setText("Pin-code must contain 6 numbers");
			return false;
		}
	}
	
	public boolean checkForContent(String string) {
		if (string.length() > 512) {
			postRequestPincodeTextField.setText("Request cannot exceed 512 characters");
			return false;
		}
		return true;
	}
	
	@FXML
	public void search(ActionEvent event) {
		if (usersRadioButton.isSelected()) {
			userSearchResult();
		} else if (pincodeRadioButton.isSelected()) {
			pincodeSearchResult();
		} else if (postsRadioButton.isSelected()) {
			postSearchResult();
		}
	}
	
	@FXML
	private GridPane searchPostGridPane;
	
	public void pincodeSearchResult() {
		String search = searchTextField.getText();
		int row = 1;
		try {
			
			searchPostGridPane.getChildren().clear();
			GetPostArrayList list = new GetPostArrayList();
			list.setPostsList(ClientSidePostService.searchPincodeRequest(50009, search));
			
			if (list.getPostsList() != null) {
				for (int i = 0; i < list.getPostsList().size(); i++) {
					
					FXMLLoader fxmlLoader = new FXMLLoader();
					fxmlLoader.setLocation(getClass().getResource("ItemFXML.fxml"));
					AnchorPane anchorPane = fxmlLoader.load();
					
					ItemController itemController = fxmlLoader.getController();
					itemController.setData(list.getPostsList().get(i));
					
					searchPostGridPane.add(anchorPane, 0, row++);
				}
			} else {
				Alert alert = new Alert(Alert.AlertType.INFORMATION);
				alert.setTitle("Null");
				alert.setHeaderText("No search results");
				alert.setContentText("Try different search values");
				Optional<ButtonType> result = alert.showAndWait();
				
				if (result.isPresent() && result.get() == ButtonType.OK) {
					searchPostGridPane.getChildren().clear();
					searchTextField.setText("");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void postSearchResult() {
		String search = searchTextField.getText();
		
		int row = 1;
		
		try {
			
			searchPostGridPane.getChildren().clear();
			GetPostArrayList list = new GetPostArrayList();
			list.setPostsList(ClientSidePostService.searchPostRequest(50008, search));
			
			if (list.getPostsList() != null) {
				for (int i = 0; i < list.getPostsList().size(); i++) {
					
					FXMLLoader fxmlLoader = new FXMLLoader();
					fxmlLoader.setLocation(getClass().getResource("ItemFXML.fxml"));
					AnchorPane anchorPane = fxmlLoader.load();
					
					ItemController itemController = fxmlLoader.getController();
					itemController.setData(list.getPostsList().get(i));
					
					searchPostGridPane.add(anchorPane, 0, row++);
				}
			} else {
				Alert alert = new Alert(Alert.AlertType.INFORMATION);
				alert.setTitle("Null");
				alert.setHeaderText("No search results");
				alert.setContentText("Try different search values");
				Optional<ButtonType> result = alert.showAndWait();
				
				if (result.isPresent() && result.get() == ButtonType.OK) {
					searchPostGridPane.getChildren().clear();
					searchTextField.setText("");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void userSearchResult() {
		String search = searchTextField.getText();
		int row = 1;
		try {
			searchUserGridPane.getChildren().clear();
			GetUserArrayPostList list = new GetUserArrayPostList();
			
			list.setUsersList(ClientSideUserService.searchUsers(50007, search));
			
			if (list.getUsersList() != null) {
				for (int i = 0; i < list.getUsersList().size(); i++) {
					
					FXMLLoader fxmlLoader = new FXMLLoader();
					fxmlLoader.setLocation(getClass().getResource("UserPaneFXML.fxml"));
					AnchorPane anchorPane = fxmlLoader.load();
					
					UserPaneController userPaneController = fxmlLoader.getController();
					userPaneController.setData(list.getUsersList().get(i));
					
					searchUserGridPane.add(anchorPane, 0, row++);
				}
			} else {
				Alert alert = new Alert(Alert.AlertType.INFORMATION);
				alert.setTitle("Null");
				alert.setHeaderText("No search results");
				alert.setContentText("Try different search values");
				Optional<ButtonType> result = alert.showAndWait();
				
				if (result.isPresent() && result.get() == ButtonType.OK) {
					searchUserGridPane.getChildren().clear();
					searchTextField.setText("");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@FXML
	public void mainBackAction(ActionEvent event) {
		//todo
		System.out.println("back");
	}
}