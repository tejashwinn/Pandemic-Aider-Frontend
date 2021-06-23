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
import java.util.Arrays;
import java.util.Optional;
import java.util.ResourceBundle;

public class MainController implements Initializable {
	
	public static StackPane topStackPanePointerVarForViewingSearchUser;
	
	public static UserDetails userDetailsStatic;
	
	public static Label userNameLabelForRefresh, postRequestUsernameLabelForRefresh;
	
	public static GridPane userGridPaneStatic;
	
	public static TitledPane userTitledPaneStatic;
	
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
	
	@FXML
	private GridPane searchPostGridPane, searchUserGridPane;
	
	ToggleGroup radioToggle = new ToggleGroup();
	
	public UserDetails getUser() {
		
		return userDetailsStatic;
	}
	
	public void setUser(UserDetails user) {
		
		MainController.userDetailsStatic = user;
	}
	
	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		//this will initialize the top stack pane which will be used to modify the content
		userTitledPaneStatic = userTitledPane;
		topStackPanePointerVarForViewingSearchUser = topStackPane;
		
		userTitledPaneStatic.setExpanded(false);
		userTitledPaneStatic.setCollapsible(false);
		
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
		userGridPaneStatic = userGridPane;
		
		searchButton.setDefaultButton(true);
		loadLogInJson();
	}
	
	//------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
//sign in button creates
	@FXML
	public void showSignIn(ActionEvent event) {
		
		SignInStarter in = new SignInStarter();
		in.run();
	}

//sidebar controls
//------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	
	@FXML
	public void showUserDetails(ActionEvent event) {
		
		if(userBorderPane.isVisible()) {
			userBorderPane.setVisible(false);
		} else if(!userBorderPane.isVisible()) {
			userBorderPane.setVisible(true);
			postRequestBorderPane.setVisible(false);
			searchBorderPane.setVisible(false);
		}
	}
	
	@FXML
	public void showPostRequest(ActionEvent event) {
		
		if(postRequestBorderPane.isVisible()) {
			postRequestBorderPane.setVisible(false);
		} else if(!postRequestBorderPane.isVisible()) {
			userBorderPane.setVisible(false);
			postRequestBorderPane.setVisible(true);
			searchBorderPane.setVisible(false);
		}
	}
	
	@FXML
	public void showSearchDetails(ActionEvent event) {
		
		if(searchBorderPane.isVisible()) {
			searchBorderPane.setVisible(false);
		} else if(!searchBorderPane.isVisible()) {
			userBorderPane.setVisible(false);
			postRequestBorderPane.setVisible(false);
			searchBorderPane.setVisible(true);
		}
	}

//shows different views according to the option selected
//!------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	
	@FXML
	public void onRadioSelectedAction(ActionEvent event) {
		
		if(usersRadioButton.isSelected()) {
			postsTitledPane.setVisible(false);
			usersTitledPane.setVisible(true);
		} else if(postsRadioButton.isSelected()) {
			usersTitledPane.setVisible(false);
			postsTitledPane.setVisible(true);
		} else if(pincodeRadioButton.isSelected()) {
			usersTitledPane.setVisible(false);
			postsTitledPane.setVisible(true);
		}
	}

//initializer for
//!------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	
	@FXML
	public void refreshUser(ActionEvent event) {
		
		refreshUserPage();
	}
	
	//takes the values from the json format to the user
	private void loadLogInJson() {
		
		try {
			BufferedReader br = new BufferedReader(new FileReader("src/pandemic/aider/client/json/log.json"));
			String str = br.readLine();
			if(str != null) {
				userDetailsStatic = JsonServiceClient.jsonToUser(str);
				JsonSettings.LoggedIn = true;
				refreshUserPage();
			} else {
				userDetailsStatic = null;
				SignInStarter in = new SignInStarter();
				in.run();
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void refreshUserPage() {
		
		try {
			int row = 1;
			userGridPane.getChildren().clear();
			if(userDetailsStatic.getUsername() != null) {
				//display the user name on user scene
				userUsernameLabel.setText(userDetailsStatic.getUsername());
				postRequestUsernameLabel.setText(userDetailsStatic.getUsername());
				
				GetPostArrayList list = new GetPostArrayList();
				list.setPostsList(ClientSidePostService.retrieveRequest(50006, userDetailsStatic.getUsername()));
				
				if(list.getPostsList() != null) {
					if(!userTitledPaneStatic.isVisible()) {
						userTitledPaneStatic.setVisible(true);
					}
					userTitledPaneStatic.setExpanded(true);
					userTitledPaneStatic.setCollapsible(true);
					for(int i = 0; i < list.getPostsList().size(); i++) {
						
						FXMLLoader fxmlLoader = new FXMLLoader();
						fxmlLoader.setLocation(getClass().getResource("ItemFXML.fxml"));
						AnchorPane anchorPane = fxmlLoader.load();
						
						ItemController itemController = fxmlLoader.getController();
						itemController.setData(list.getPostsList().get(i));
//						list.getPostsList().get(i).display();
						userGridPane.add(anchorPane, 0, row++);
					}
				} else {
					userGridPane.getChildren().clear();
				}
			} else {
				SignInStarter in = new SignInStarter();
				in.run();
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

//adding post
// ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	
	@FXML
	public void addPost(ActionEvent event) {
		
		boolean validPost = false;
		PostDetails post = new PostDetails();
		
		post.setUserUsername(userDetailsStatic.getUsername());
		post.setPincode(postRequestPincodeTextField.getText().strip());
		post.setContent(postRequestContentTextArea.getText().strip());
		
		//time generator
		DateTimeFormatter dateTimeFormatterClientAdduser = DateTimeFormatter.ofPattern("yyyy:MM:dd::HH:mm:ss");
		LocalDateTime userCreatedTimeGenerator = LocalDateTime.now();
		post.setTime(dateTimeFormatterClientAdduser.format(userCreatedTimeGenerator));
		
		if(!post.getContent().equals("")) {
			post.setUserTags(checkHashtags(post.getContent()));
			if(post.getUserTags() != null) {
				validPost = true;
			} else {
				postRequestWarningLabel.setText("Enter valid pincode");
			}
		} else {
			postRequestWarningLabel.setText("The request cannot be empty");
		}
		if(checkForContent(post.getContent())) {
			if(checkForPincode(post.getPincode())) {
				validPost = true;
			} else {
				postRequestWarningLabel.setText("Pin-code can only contain 6 numbers");
			}
		}
		if(validPost) {
			if(ClientSidePostService.postRequest(50005, post)) {
				postRequestWarningLabel.setText("Successfully posted");
				postRequestContentTextArea.setText("");
				postRequestPincodeTextField.setText("");
			} else {
				postRequestWarningLabel.setText("Post wasn't able to post");
			}
		}
	}
	
	@FXML
	public void cancelAction(ActionEvent event) {
		
		postRequestContentTextArea.setText("");
		postRequestPincodeTextField.setText("");
		postRequestWarningLabel.setText("");
	}
	
	//checks for hashtag
	public ArrayList<String> checkHashtags(String string) {
		
		if(string != null) {
			if(!string.equals("")) {
				String[] arrOfString = string.split("\n");
				ArrayList<String> main = new ArrayList<>();
				for(String i : arrOfString) {
					String[] temp = i.split(" ");
					main.addAll(Arrays.asList(temp));
				}
				ArrayList<String> finalList = new ArrayList<>();
				for(String finalStr : main) {
					if('#' == finalStr.strip().charAt(0)) {
						finalList.add(finalStr);
					}
				}
				return finalList;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}
	
	//validates pincode
	public boolean checkForPincode(String string) {
		
		if(string.length() == 6) {
			for(char i : string.toCharArray()) {
				if(!Character.isDigit(i)) {
					return false;
				}
			}
			return true;
		} else {
			postRequestWarningLabel.setText("Pin-code must contain 6 numbers");
			return false;
		}
	}
	
	//validates content
	public boolean checkForContent(String string) {
		
		if(string.length() > 512) {
			postRequestPincodeTextField.setText("Request cannot exceed 512 characters");
			return false;
		}
		return true;
	}

//search function for different types for search
//!------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	
	/**
	 * main action for searching
	 * distributes according to the radiobutton selected
	 */
	@FXML
	public void search(ActionEvent event) {
		
		if(usersRadioButton.isSelected()) {
			userSearchResult();
		} else if(pincodeRadioButton.isSelected()) {
			pincodeSearchResult();
		} else if(postsRadioButton.isSelected()) {
			postSearchResult();
		}
	}
	
	//searches for pincode
	public void pincodeSearchResult() {
		
		int row = 1;
		try {
			String searchFieldText = searchTextField.getText();
			searchPostGridPane.getChildren().clear();
			GetPostArrayList list = new GetPostArrayList();
			
			list.setPostsList(ClientSidePostService.searchPincodeRequest(50009, searchFieldText));
			
			if(list.getPostsList() != null) {
				for(int i = 0; i < list.getPostsList().size(); i++) {
					
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
				
				if(result.isPresent() && result.get() == ButtonType.OK) {
					searchPostGridPane.getChildren().clear();
					searchTextField.setText("");
				}
			}
		} catch(Exception e) {
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
			
			if(list.getPostsList() != null) {
				for(int i = 0; i < list.getPostsList().size(); i++) {
					
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
				
				if(result.isPresent() && result.get() == ButtonType.OK) {
					searchPostGridPane.getChildren().clear();
					searchTextField.setText("");
				}
			}
		} catch(Exception e) {
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
			
			if(list.getUsersList() != null) {
				for(int i = 0; i < list.getUsersList().size(); i++) {
					
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
				
				if(result.isPresent() && result.get() == ButtonType.OK) {
					searchUserGridPane.getChildren().clear();
					searchTextField.setText("");
				}
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

//------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	
	@FXML
	public void mainLogOutAction(ActionEvent event) {
		
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter("src/pandemic/aider/client/json/log.json"));
			bw.write("");
			bw.close();
			if(userDetailsStatic != null) {
				userDetailsStatic.setToNull();
			}
			refreshUserPage();
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	//static
//------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
//helps in logging out and logging in
	public static void refreshUser() {
		
		userGridPaneStatic.getChildren().clear();
		String str = null;
		int row = 1;
		try {
			try {
				BufferedReader br = new BufferedReader(new FileReader("src/pandemic/aider/client/json/log.json"));
				str = br.readLine();
			} catch(IOException e) {
				e.printStackTrace();
			}
			
			if(str != null) {
				if(!str.equals("{")) {
					
					userNameLabelForRefresh.setText(userDetailsStatic.getUsername());
					postRequestUsernameLabelForRefresh.setText(userDetailsStatic.getUsername());
					
					GetPostArrayList list = new GetPostArrayList();
					list.setPostsList(ClientSidePostService.retrieveRequest(50006, userDetailsStatic.getUsername()));
					
					if(list.getPostsList() != null) {
						
						userTitledPaneStatic.setExpanded(true);
						userTitledPaneStatic.setCollapsible(true);
						
						for(int i = 0; i < list.getPostsList().size(); i++) {
							
							FXMLLoader fxmlLoader = new FXMLLoader();
							fxmlLoader.setLocation(MainController.class.getResource("ItemFXML.fxml"));
							AnchorPane anchorPane = fxmlLoader.load();
							
							ItemController itemController = fxmlLoader.getController();
							itemController.setData(list.getPostsList().get(i));
							
							userGridPaneStatic.add(anchorPane, 0, row++);
							if(!userTitledPaneStatic.isVisible()) {
								userTitledPaneStatic.setVisible(true);
							}
						}
					} else {
						if(userTitledPaneStatic.isVisible()) {
							userTitledPaneStatic.setVisible(false);
						}
					}
				}
				
			} else {
				
				userDetailsStatic = null;
				userGridPaneStatic.getChildren().clear();
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setTitle("Audit Error");
				alert.setHeaderText("Sign in/up");
				alert.setContentText("You need to sign in/up to use this application");
				Optional<ButtonType> result = alert.showAndWait();
				
				if(result.isPresent() && result.get() == ButtonType.OK) {
					SignInStarter in = new SignInStarter();
					in.run();
				}
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}