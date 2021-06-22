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

import java.io.*;
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
	
	public static TitledPane userTitledPaneForRefresh;
	
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
	private TitledPane userTitledPane;
	
	@FXML
	public Button searchButton;
	
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
		userTitledPaneForRefresh = userTitledPane;
		topStackPanePointerVarForViewingSearchUser = topStackPane;
		
		userTitledPaneForRefresh.setExpanded(false);
		userTitledPaneForRefresh.setCollapsible(false);
		
		//set toggle
		postsRadioButton.setToggleGroup(radioToggle);
		usersRadioButton.setToggleGroup(radioToggle);
		pincodeRadioButton.setToggleGroup(radioToggle);

//		postsRadioButton.setSelected(true);
		
		userBorderPane.setVisible(false);
		postRequestBorderPane.setVisible(false);
		searchBorderPane.setVisible(true);
		
		postsTitledPane.setCollapsible(false);
		usersTitledPane.setCollapsible(false);
		
		userNameLabelForRefresh = userUsernameLabel;
		postRequestUsernameLabelForRefresh = postRequestUsernameLabel;
		userGridPaneForRefresh = userGridPane;
		
		searchButton.setDefaultButton(true);
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
			MainController.refreshUser();
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
		} else if (postsRadioButton.isSelected()) {
			usersTitledPane.setVisible(false);
			postsTitledPane.setVisible(true);
		} else if (pincodeRadioButton.isSelected()) {
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
				refreshUserPage();
			} else {
				userStaticForRefresh = null;
				SignInStarter in = new SignInStarter();
				in.run();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//helps in logging out and logging in
	public static void refreshUser() {
		userGridPaneForRefresh.getChildren().clear();
		
		String str = null;
		int row = 1;
		try {
			try {
				BufferedReader br = new BufferedReader(new FileReader("src/pandemic/aider/client/json/log.json"));
				str = br.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			if (str != null) {
				userNameLabelForRefresh.setText(userStaticForRefresh.getUsername());
				postRequestUsernameLabelForRefresh.setText(userStaticForRefresh.getUsername());
				GetPostArrayList list = new GetPostArrayList();
				list.setPostsList(ClientSidePostService.retrieveRequest(50006, userStaticForRefresh.getUsername()));
				
				if (list.getPostsList() != null) {
					
					userTitledPaneForRefresh.setExpanded(true);
					userTitledPaneForRefresh.setCollapsible(true);
					for (int i = 0; i < list.getPostsList().size(); i++) {
						
						FXMLLoader fxmlLoader = new FXMLLoader();
						fxmlLoader.setLocation(MainController.class.getResource("ItemFXML.fxml"));
						AnchorPane anchorPane = fxmlLoader.load();
						
						ItemController itemController = fxmlLoader.getController();
						itemController.setData(list.getPostsList().get(i));
						
						userGridPaneForRefresh.add(anchorPane, 0, row++);
					}
				} else {
					
					userTitledPaneForRefresh.setExpanded(false);
					userTitledPaneForRefresh.setCollapsible(false);
				}
			} else {
				
				userStaticForRefresh = null;
				userGridPaneForRefresh.getChildren().clear();
//				topStackPanePointerVarForViewingSearchUser.getChildren().clear();
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setTitle("Audit Error");
				alert.setHeaderText("Sign in/up");
				alert.setContentText("You need to sign in/up to use this application");
				Optional<ButtonType> result = alert.showAndWait();
				
				if (result.isPresent() && result.get() == ButtonType.OK) {
					SignInStarter in = new SignInStarter();
					in.run();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void refreshUserPage() {
		userGridPane.getChildren().clear();
//		user.display();
		//display posts on users
		int row = 1;
		try {
			if (userStaticForRefresh.getUsername() != null) {
				//display the user name on user scene
				userUsernameLabel.setText(userStaticForRefresh.getUsername());
				postRequestUsernameLabel.setText(userStaticForRefresh.getUsername());
				
				GetPostArrayList list = new GetPostArrayList();
				list.setPostsList(ClientSidePostService.retrieveRequest(50006, userStaticForRefresh.getUsername()));
				
				if (list.getPostsList() != null) {
					userTitledPane.setCollapsible(true);
					userTitledPane.setExpanded(true);
					
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
					userTitledPane.setCollapsible(false);
					userTitledPane.setExpanded(false);
				}
			} else {
				SignInStarter in = new SignInStarter();
				in.run();
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
		
		if (!post.getContent().equals("")) {
			post.setUserTags(checkHashtags(post.getContent()));
			if (post.getUserTags() != null) {
				if (checkForContent(post.getContent())) {
					if (checkForPincode(post.getPincode())) {
						//client

//				System.out.println(post.getContent());
						if (ClientSidePostService.postRequest(50005, post)) {
							
							postRequestWarningLabel.setText("Successfully posted");
							postRequestContentTextArea.setText("");
							postRequestPincodeTextField.setText("");
						}
					} else {
						postRequestWarningLabel.setText("Pin-code can only contain 6 numbers");
					}
				}
			} else {
				postRequestWarningLabel.setText("Enter valid pincode");
			}
		} else {
			postRequestWarningLabel.setText("The request cannot be empty");
		}
	}
	
	@FXML
	public void cancelAction(ActionEvent event) {
		postRequestContentTextArea.setText("");
		postRequestPincodeTextField.setText("");
		postRequestWarningLabel.setText("");
	}
	
	public ArrayList<String> checkHashtags(String string) {
		if (string != null) {
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
				} else {
					finalList.add("null");
					return finalList;
				}
			}
			return finalList;
		} else {
			return null;
		}
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
	
	//!------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	
	@FXML
	private GridPane searchPostGridPane, searchUserGridPane;
	
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
		int row = 1;
		try {
			String search = searchTextField.getText();
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
	public void mainLogOutAction(ActionEvent event) {
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter("src/pandemic/aider/client/json/log.json"));
			bw.write("");
			bw.close();
			if (userStaticForRefresh != null) {
				userStaticForRefresh.setToNull();
			}
			MainController.refreshUser();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}