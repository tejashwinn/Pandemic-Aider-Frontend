package pandemic.aider.client.ui.main;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import org.springframework.security.crypto.bcrypt.BCrypt;
import pandemic.aider.client.CONSTANTS;
import pandemic.aider.client.model.*;
import pandemic.aider.client.service.ClientSidePostService;
import pandemic.aider.client.service.ClientSideUserService;
import pandemic.aider.client.service.JsonServiceClient;

import java.io.*;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class MainController implements Initializable {
	
	public static StackPane topStackPanePointerVarForViewingSearchUser;
	
	public static UserDetails userDetailsStatic;
	
	public static Label userNameLabelForRefresh, postRequestUsernameLabelForRefresh;
	
	public static GridPane userGridPaneStatic;
	
	public static TitledPane userTitledPaneStatic;
	
	private final ToggleGroup radioToggle = new ToggleGroup();
	
	public static HBox viewUserHBoxStatic, signInHBoxStatic;
	
	public static String Otp;
	
	@FXML
	public Button mainSignInButton, userRefreshButton;
	
	@FXML
	private GridPane userGridPane;
	
	@FXML
	private BorderPane userBorderPane, searchBorderPane, postRequestBorderPane, settingsBorderPane;
	
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
	
	@FXML
	private HBox viewUserHBox, signInHBox, signUpHBox;
	
	@FXML
	private StackPane insiderUserStackPane;
	
	@FXML
	private ImageView mainLogo;
	
	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		
		Image logo = new Image(Objects.requireNonNull(getClass().getResource("main_display_logo.CEF.png")).toExternalForm());
		mainLogo.setImage(logo);

//		ServerSideUserServer.runUserService();
//		ServerSidePostService.runServerPost();
		
		//this will initialize the top stack pane which will be used to modify the content
		userTitledPaneStatic = userTitledPane;
		topStackPanePointerVarForViewingSearchUser = topStackPane;
		
		//set toggle
		postsRadioButton.setToggleGroup(radioToggle);
		usersRadioButton.setToggleGroup(radioToggle);
		pincodeRadioButton.setToggleGroup(radioToggle);

//		postsRadioButton.setSelected(true);
		userBorderPane.setVisible(false);
		postRequestBorderPane.setVisible(true);
		searchBorderPane.setVisible(false);
		
		//sets the title pane fo search to remain non collapsed
		postsTitledPane.setCollapsible(false);
		usersTitledPane.setCollapsible(false);
		
		//initializes the static panes
		userNameLabelForRefresh = userUsernameLabel;
		postRequestUsernameLabelForRefresh = postRequestUsernameLabel;
		userGridPaneStatic = userGridPane;
		
		userTitledPane.setCollapsible(false);
		//initialize the static for hBox
		viewUserHBoxStatic = viewUserHBox;
		signInHBoxStatic = signInHBox;
		
		signInHBox.setVisible(false);
		signUpHBox.setVisible(false);
		
		//sets the search button in search menu to default so that it can be accessed with RETURN
		searchButton.setDefaultButton(true);
		
		signUpButton.setVisible(false);
		
		loadLogInJson();
		
	}

//sidebar controls
//------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	
	@FXML
	public void showUserDetails() {
		
		if(!userBorderPane.isVisible()) {
			postRequestBorderPane.setVisible(false);
			searchBorderPane.setVisible(false);
			settingsBorderPane.setVisible(false);
			
			userBorderPane.setVisible(true);
		}
	}
	
	@FXML
	public void showPostRequest() {
		
		if(!postRequestBorderPane.isVisible()) {
			userBorderPane.setVisible(false);
			searchBorderPane.setVisible(false);
			settingsBorderPane.setVisible(false);
			
			postRequestBorderPane.setVisible(true);
		}
	}
	
	@FXML
	public void showSearchDetails() {
		
		if(!searchBorderPane.isVisible()) {
			userBorderPane.setVisible(false);
			postRequestBorderPane.setVisible(false);
			settingsBorderPane.setVisible(false);
			
			searchBorderPane.setVisible(true);
		}
	}
	
	@FXML
	public void showSettings() {
		
		if(!settingsBorderPane.isVisible()) {
			userBorderPane.setVisible(false);
			postRequestBorderPane.setVisible(false);
			searchBorderPane.setVisible(false);
			
			settingsBorderPane.setVisible(true);
		} else {
			settingsBorderPane.setVisible(false);
		}
	}

//shows different views according to the option selected
//!------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	
	@FXML
	public void onRadioSelectedAction() {
		
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

//	@FXML
//	public void reloadPageStatic(ActionEvent event) {
//		refreshUserPage();
//	}
	
	//takes the values from the json format to the user
	
	public void loadLogInJson() {
		
		try {
			BufferedReader br = new BufferedReader(new FileReader("src/pandemic/aider/client/json/log.json"));
			String str = br.readLine();
			if(str != null && !str.equals("{ ") && !str.equals("{\n")) {
				userDetailsStatic = JsonServiceClient.jsonToUser(str);
				refreshUserPage();
				unblockUsage();
			} else {
				userDetailsStatic = null;
				refreshUserPage();
				blockUsage();
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	private void showSignInAlert() {
		
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
		alert.setTitle("Audit Error");
		alert.setHeaderText("Sign in/up");
		alert.setContentText("You need to sign in/up to use this application");
		alert.setWidth(450);
		alert.setHeight(450);
		
		Optional<ButtonType> result = alert.showAndWait();
		if(result.isPresent() && result.get() == ButtonType.OK) {
			userTitledPane.setVisible(true);
			userTitledPane.setCollapsible(false);
			userTitledPane.setExpanded(true);
			
			viewUserHBox.setVisible(false);
			signInHBox.setVisible(true);
		} else {
			
			userTitledPane.setVisible(true);
			userTitledPane.setCollapsible(false);
			userTitledPane.setExpanded(true);
			System.out.print("");
			viewUserHBox.setVisible(false);
			signInHBox.setVisible(true);
		}
	}
	
	@FXML
	public void refreshUserPage() {
		
		try {
			int row = 1;
			
			if(userDetailsStatic != null) {
				
				//display the user name on user scene
				userUsernameLabel.setText(userDetailsStatic.getUsername());
				postRequestUsernameLabel.setText(userDetailsStatic.getUsername());
				
				GetPostArrayList list = new GetPostArrayList();
				list.setPostsList(ClientSidePostService.retrieveRequest(50006, userDetailsStatic.getUsername()));
				
				if(list.getPostsList() != null) {
					
					userGridPane.getChildren().clear();
					
					usersTitledPane.setVisible(true);
					userTitledPane.setExpanded(true);
					userTitledPane.setCollapsible(false);
					
					viewUserHBox.setVisible(true);
					signInHBox.setVisible(false);
					signUpHBox.setVisible(false);
					
					for(int i = 0; i < list.getPostsList().size(); i++) {
						
						FXMLLoader fxmlLoader = new FXMLLoader();
						fxmlLoader.setLocation(getClass().getResource("ItemFXML.fxml"));
						AnchorPane anchorPane = fxmlLoader.load();
						
						ItemController itemController = fxmlLoader.getController();
						itemController.setData(list.getPostsList().get(i), true);
						userGridPane.add(anchorPane, 0, row++);
						
					}
				} else {
					userTitledPane.setVisible(true);
					userTitledPane.setCollapsible(false);
					userTitledPane.setExpanded(false);
					userGridPane.getChildren().clear();
					
					signInHBox.setVisible(false);
					signUpHBox.setVisible(false);
				}
			} else {
				showSignInAlert();
				
				viewUserHBox.setVisible(false);
				
				signInHBox.setVisible(true);
				signUpHBox.setVisible(false);
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

//adding post
// ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	
	@FXML
	public void addPost() {
		
		boolean validPost = false;
		PostDetails post = new PostDetails();
		
		post.setUserUsername(userDetailsStatic.getUsername());
		post.setPincode(postRequestPincodeTextField.getText().strip());
		post.setContent(postRequestContentTextArea.getText().strip());
		
		//time generator
		DateTimeFormatter dateTimeFormatterClientAdduser = DateTimeFormatter.ofPattern("yyyy:MM:dd::HH:mm:ss");
		LocalDateTime userCreatedTimeGenerator = LocalDateTime.now();
		post.setTime(dateTimeFormatterClientAdduser.format(userCreatedTimeGenerator));
		if(!post.getContent().equals("") && postContent(post.getContent())) {
			
			post.setUserTags(checkHashtags(post.getContent()));
			
			if(post.getUserTags() != null) {
				validPost = true;
			}
			
		} else {
			postRequestWarningLabel.setText("The request cannot be empty");
			validPost = false;
		}
		
		if(checkForContent(post.getContent())) {
			if(checkForPincode(post.getPincode())) {
				validPost = true;
			} else {
				postRequestWarningLabel.setText("Pin-code can only contain 6 numbers");
				validPost = false;
			}
		}
		
		if(validPost) {
			post.setPostUniqueId(UUID.randomUUID().toString());
			if(ClientSidePostService.postRequest(50005, post)) {
				postRequestWarningLabel.setText("Successfully posted");
				postRequestContentTextArea.setText("");
				postRequestPincodeTextField.setText("");
			} else {
				postRequestWarningLabel.setText("Post wasn't able to post");
			}
		}
		
	}
	
	private boolean postContent(String string) {
		
		for(char ch : string.toCharArray()) {
			if(Character.isLetterOrDigit((int) ch)) {
				return true;
			}
		}
		postRequestWarningLabel.setText("Cannot be empty");
		return false;
	}
	
	@FXML
	public void cancelAction() {
		
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
			postRequestWarningLabel.setText("Request cannot exceed 512 characters");
			return false;
		} else if(string.length() < 1) {
			postRequestWarningLabel.setText("Request cannot be empty");
			return false;
		} else {
			return true;
		}
	}

//search function for different types for search
//!------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	
	/**
	 * main action for searching
	 * distributes according to the radiobutton selected
	 */
	@FXML
	public void search() {
		
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
	public void mainLogOutAction() {
		
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter("src/pandemic/aider/client/json/log.json"));
			bw.write("");
			bw.close();
			userUsernameLabel.setText(" ");
			postRequestUsernameLabel.setText(" ");
			if(userDetailsStatic != null) {
				userDetailsStatic.setToNull();
			}
			blockUsage();
			clearAuditValues();
			MainController.reloadPageStatic();
		} catch(IOException e) {
			e.printStackTrace();
		}
		
	}
	
	@FXML
	private Button showPostRequestButton;
	
	@FXML
	private Button mainSearchButton;
	
	@FXML
	private Button userMainButton;
	
	@FXML
	private Button settingsMainButton;
	
	@FXML
	private Button helpMainButton;
	
	//todo
	public void blockUsage() {
		
		showPostRequestButton.setDisable(true);
		mainSearchButton.setDisable(true);
		userMainButton.setDisable(true);
		settingsMainButton.setDisable(true);
		helpMainButton.setDisable(true);
		userRefreshButton.setDisable(true);
	}
	
	public void unblockUsage() {
		
		showPostRequestButton.setDisable(false);
		mainSearchButton.setDisable(false);
		userMainButton.setDisable(false);
		settingsMainButton.setDisable(false);
		helpMainButton.setDisable(false);
		userRefreshButton.setDisable(false);
	}
	
	public static void mainLogOutActionStatic() {
		
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter("src/pandemic/aider/client/json/log.json"));
			bw.write("");
			bw.close();
			
			if(userDetailsStatic != null) {
				userDetailsStatic.setToNull();
			}
			userNameLabelForRefresh.setText("");
			postRequestUsernameLabelForRefresh.setText("");
			
			MainController.reloadPageStatic();
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
//audit
//------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	
	//static
//------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
//helps in logging out and logging in
	public static void reloadPageStatic() {
		
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
			
			if(str != null && userDetailsStatic != null && !str.equals("{")) {
				
				userNameLabelForRefresh.setText(userDetailsStatic.getUsername());
				postRequestUsernameLabelForRefresh.setText(userDetailsStatic.getUsername());
				
				userTitledPaneStatic.setVisible(true);
				userTitledPaneStatic.setExpanded(true);
				userTitledPaneStatic.setCollapsible(false);
				
				//views for the HBox inside the titled pane
				viewUserHBoxStatic.setVisible(true);
				signInHBoxStatic.setVisible(false);
//				userDetailsStatic.display();
				GetPostArrayList list = new GetPostArrayList();
				list.setPostsList(ClientSidePostService.retrieveRequest(50006, userDetailsStatic.getUsername()));
				
				if(list.getPostsList() != null) {
					userGridPaneStatic.getChildren().clear();
					
					for(int i = 0; i < list.getPostsList().size(); i++) {
						
						FXMLLoader fxmlLoader = new FXMLLoader();
						fxmlLoader.setLocation(MainController.class.getResource("ItemFXML.fxml"));
						AnchorPane anchorPane = fxmlLoader.load();
						
						ItemController itemController = fxmlLoader.getController();
						itemController.setData(list.getPostsList().get(i), false);
						
						userGridPaneStatic.add(anchorPane, 0, row++);
						
					}
				} else {
					
					userGridPaneStatic.getChildren().clear();
					
					userTitledPaneStatic.setVisible(true);
					userTitledPaneStatic.setCollapsible(false);
					userTitledPaneStatic.setExpanded(false);
					
					signInHBoxStatic.setVisible(false);
					viewUserHBoxStatic.setVisible(false);
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
					viewUserHBoxStatic.setVisible(false);
					signInHBoxStatic.setVisible(true);
				}
				viewUserHBoxStatic.setVisible(false);
				signInHBoxStatic.setVisible(true);
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	//general audi
// ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	private void clearAuditValues() {
		
		signUpWarningLabel.setText("");
		signInWarningLabel.setText("");
		
		usernameTextField.setText("");
		usernameTextFieldSignUp.setText("");
		
		passwordTextField.setText("");
		passwordHiddenField.setText("");
		
		passwordTextFieldSignUp.setText("");
		passwordHiddenFieldSignUp.setText("");
		
		confirmPasswordTextFieldSignUp.setText("");
		confirmPasswordHiddenFieldSignUp.setText("");
		
		usernameTextFieldSignUp.setText("");
		nameTextFieldSignUp.setText("");
		
		phoneNumberTextField.setText("");
		otpTextField.setText("");
		
		signUpButton.setVisible(false);
		
	}
	
	@FXML
	public void showSignIn() {
		
		userTitledPane.setExpanded(true);
		viewUserHBox.setVisible(false);
		signInHBox.setVisible(true);
		signUpHBox.setVisible(false);
		
		clearAuditValues();
		
	}
	
	//sign in button creates
// ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	@FXML
	private TextField passwordTextField, usernameTextField;
	
	@FXML
	private PasswordField passwordHiddenField;
	
	@FXML
	private CheckBox passwordCheckBoxToggle;
	
	@FXML
	private Label signInWarningLabel;
	
	@FXML
	public void checkCorrectCredentials() {
		//to change values when edited on different toggles
		boolean correctCredentials = false;
		
		if(passwordCheckBoxToggle.isSelected()) {
			passwordHiddenField.setText(passwordTextField.getText());
		}
		if(!passwordCheckBoxToggle.isSelected()) {
			passwordTextField.setText(passwordHiddenField.getText());
		}
		
		UserDetails user = new UserDetails();
		user.setUsername(usernameTextField.getText());
		user.setPassword(passwordHiddenField.getText());
		
		if(checkPasswordSignIn(user)) {
			user.setPassword("");
			user.setPassword(BCrypt.hashpw(passwordHiddenField.getText(), CONSTANTS.PEPPER_PASSWORD));
		}
		if(checkUsername(user)) {
			if(checkPasswordSignIn(user)) {
				user = ClientSideUserService.checkCredentials(50004, user);
				if(user != null) {
					correctCredentials = user.getName() != null;
				} else {
					correctCredentials = false;
				}
			}
		}
		if(correctCredentials) {
			String jsonString = JsonServiceClient.userToJson(user);
			try {
				BufferedWriter bw = new BufferedWriter(new FileWriter("src/pandemic/aider/client/json/log.json"));
				
				bw.write(jsonString);
				bw.close();
				
				MainController.userDetailsStatic = JsonServiceClient.jsonToUser(jsonString);
				signInWarningLabel.setText("Successfully Logged In");
				showAlert();
			} catch(IOException e) {
				e.printStackTrace();
			}
		} else {
			signInWarningLabel.setText("Wrong credentials entered");
		}
	}
	
	private boolean checkUsername(UserDetails obj) {
		
		boolean checkBool = false;
		if(obj.getUsername().equals("") || obj.getUsername() == null) {
			signInWarningLabel.setText("Username can't be empty");
			return false;
		} else {
			/*
			 * checks if username contains any different characters
			 * first for loop is for the string
			 * second for loop is to iterate through the main constant string
			 * if the stringChar matches the any of the char then the loop is broken and the loop is checked for new char from the string
			 * even if one char fails it will return false
			 */
			for(char stringChar : obj.getUsername().toCharArray()) {
				
				for(char constChar : CONSTANTS.ALLOWED_USERNAME_CHARS.toCharArray()) {
					
					if(stringChar == constChar) {
						checkBool = true;
						break;
					}
					checkBool = false;
				}
				if(!checkBool) {
					signInWarningLabel.setText("'" + stringChar + "' Not allowed in Username");
					return false;
				}
			}
			if(obj.getUsername().length() > 30) {
				signInWarningLabel.setText("Username cannot exceed 30 characters");
				return false;
			} else {
				return true;
			}
		}
	}
	
	private boolean checkPasswordSignIn(UserDetails obj) {
		
		if(obj.getPassword() == null || obj.getPassword().equals("")) {
			signInWarningLabel.setText("Password Cannot be empty");
			return false;
		} else {
			return true;
		}
	}
	
	private void showAlert() {
		
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
		alert.setTitle("Successful");
		alert.setHeaderText("You are now logged in");
		alert.setContentText("Press Ok to continue");
		Optional<ButtonType> result = alert.showAndWait();
		usernameTextField.setText("");
		passwordHiddenField.setText("");
		passwordTextField.setText("");
		if(result.isPresent() && result.get() == ButtonType.OK) {
			unblockUsage();
			MainController.reloadPageStatic();
		}
	}
	
	//other functions sign in
//------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	@FXML
	public void switchToSignUp() {
		
		otpButton.setVisible(true);
		signUpButton.setVisible(false);
		
		signInHBox.setVisible(false);
		signUpHBox.setVisible(true);
	}
	
	@FXML
	public void showPassword() {
		
		if(passwordCheckBoxToggle.isSelected()) {
			//shows the text fields
			passwordTextField.setText(passwordHiddenField.getText());
			passwordTextField.setVisible(true);
			passwordHiddenField.setVisible(false);
			return;
		}
		//shows the hidden fields
		passwordHiddenField.setText(passwordTextField.getText());
		passwordHiddenField.setVisible(true);
		passwordTextField.setVisible(false);
		
	}
	
	//signup general action
// -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	@FXML
	public void cancelSignUpAction() {
		
		viewUserHBox.setVisible(true);
		signInHBox.setVisible(false);
		signUpHBox.setVisible(false);
		
	}
	
	//sign up action
// ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	@FXML
	public Button signUpButton, otpButton;
	
	@FXML
	private TextField passwordTextFieldSignUp;
	
	@FXML
	private TextField confirmPasswordTextFieldSignUp;
	
	@FXML
	private TextField usernameTextFieldSignUp;
	
	@FXML
	private TextField nameTextFieldSignUp;
	
	@FXML
	private PasswordField passwordHiddenFieldSignUp;
	
	@FXML
	private PasswordField confirmPasswordHiddenFieldSignUp;
	
	@FXML
	private CheckBox passwordCheckBoxToggleSignUp;
	
	@FXML
	private Label signUpWarningLabel;
	
	@FXML
	private TextField phoneNumberTextField, otpTextField;
	
	private static UserDetails tempUserForSignUp;
	
	@FXML
	public void validationAndOtp() {
		
		try {
			
			DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("yyyy:MM:dd::HH:mm:ss");
			LocalDateTime localTime = LocalDateTime.now();
			
			boolean validEntry = false;
			UserRePassword userRePassword = new UserRePassword();
			UserDetails userDetails;

//			adding this code because when the user edits the password in view mode and hits sign up
//			it doesn't get updated in hidden mode so this will help us to set it back to the normal
			if(passwordCheckBoxToggleSignUp.isSelected()) {
				passwordHiddenFieldSignUp.setText(passwordTextFieldSignUp.getText());
				confirmPasswordHiddenFieldSignUp.setText(confirmPasswordTextFieldSignUp.getText());
			}
			
			// adding this code because when the user edits the password in hidden mode
			if(!passwordCheckBoxToggleSignUp.isSelected()) {
				passwordTextFieldSignUp.setText(passwordHiddenFieldSignUp.getText());
				confirmPasswordHiddenFieldSignUp.setText(confirmPasswordHiddenFieldSignUp.getText());
			}
			
			//assigning the entered values to the object
			userRePassword.setName(nameTextFieldSignUp.getText());
			userRePassword.setUsername(usernameTextFieldSignUp.getText().toLowerCase());
			userRePassword.setPassword(passwordHiddenFieldSignUp.getText());
			userRePassword.setConfirmPassword(confirmPasswordHiddenFieldSignUp.getText());
			userRePassword.setTime(timeFormat.format(localTime));
			userRePassword.setUniqueId(UUID.randomUUID().toString());
			userRePassword.setPhoneNo(phoneNumberTextField.getText());
			
			if(checkNameSignUp(userRePassword)) {
				if(checkUsernameSignUp(userRePassword)) {
					if(checkPasswordSignUp(userRePassword)) {
						if(checkPhoneNo(userRePassword)) {
							
							String tempPassword = userRePassword.getPassword();
							userRePassword.setPassword("");
							
							//refer: https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/crypto/bcrypt/BCrypt.html
							//there are two BCRYPT values the one used here is from spring boot
							userRePassword.setPassword(BCrypt.hashpw(tempPassword, CONSTANTS.PEPPER_PASSWORD));
							validEntry = true;
							
						}
					}
				}
			}
			
			if(validEntry) {
				String readJsonOtp;
				
				userDetails = new UserDetails(userRePassword);
				
				BufferedReader br = new BufferedReader(new FileReader("src/pandemic/aider/client/json/otpSignUp.json"));
				readJsonOtp = br.readLine();
				br.close();
				tempUserForSignUp = userDetails;
				if(readJsonOtp != null) {
					
					OtpSignUp otpSignUp = JsonServiceClient.jsonToOtpSignUp(readJsonOtp);
					
					SimpleDateFormat originalFormat = new SimpleDateFormat("yyyy:MM:dd::HH:mm:ss");
					SimpleDateFormat intFormat = new SimpleDateFormat("yyyyMMddHHmmss");
					
					//converts the last generated otp time to int
					long lastTime =
							Long.parseLong(intFormat.format(originalFormat.parse(otpSignUp.getOtpGeneratedTime())));
					//converts the current time to int
					long currentTime =
							Long.parseLong(intFormat.format(originalFormat.parse(timeFormat.format(localTime))));
					
					if(currentTime - lastTime <= 180) {
						signUpWarningLabel.setText("Wait " + (currentTime - lastTime) + " secs for another OTP");
						
						signUpButton.setVisible(false);
						otpButton.setVisible(false);
					} else {
						
						otpSignUp.setOtpGeneratedTime(timeFormat.format(localTime));
						
						validEntry = generateOtp(otpSignUp);
						if(validEntry) {
							signUpWarningLabel.setText("otp sent");
							otpButton.setVisible(false);
							signUpButton.setVisible(true);
							
						}
						
					}
					
				} else {
					
					OtpSignUp otpSignUp = new OtpSignUp(userDetails);
					otpSignUp.setOtpGeneratedTime(timeFormat.format(localTime));
					
					validEntry = generateOtp(otpSignUp);
					if(validEntry) {
						signUpWarningLabel.setText("OTP sent");
						otpButton.setVisible(false);
						signUpButton.setVisible(true);
					} else {
						signUpWarningLabel.setText("OTP not sent");
						
					}
					
				}
			}
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	@FXML
	public void signUpAction() {
		
		boolean validEntry;
		
		String enteredOtp = otpTextField.getText();
//		tempUserForSignUp.display();
		if(enteredOtp.equals(Otp)) {
			
			String jsonString = JsonServiceClient.userToJson(tempUserForSignUp);
			validEntry = ClientSideUserService.addUser(50003, tempUserForSignUp);
			
			if(validEntry) {
				try {
					BufferedWriter bw = new BufferedWriter(new FileWriter("src/pandemic/aider/client/json/log.json"));
					
					bw.write(jsonString);
					bw.close();
					
					MainController.userDetailsStatic = JsonServiceClient.jsonToUser(jsonString);
					
					signUpWarningLabel.setText("Successfully created the account");
					
					showAlertSignUp();
					
				} catch(IOException e) {
					e.printStackTrace();
				}
			} else {
				if(signUpWarningLabel.getText().equals("")) {
					signUpWarningLabel.setText("Account was not created");
					
				}
			}
		} else {
			signUpWarningLabel.setText("Wrong OTP entered");
			
		}
		
	}
	
	private boolean generateOtp(OtpSignUp otpSignUP) {
		
		try {
			MainController.Otp = ClientSideUserService.generateOtp(50015, phoneNumberTextField.getText().strip());
			signUpWarningLabel.setText("OTP successfully generated " + Otp);
			BufferedWriter bw = new BufferedWriter(new FileWriter("src/pandemic/aider/client/json/otpSignUp.json"));
			bw.write(JsonServiceClient.otpSignUpTOJson(otpSignUP));
			bw.close();
			return true;
			
		} catch(Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	//to view password when toggled
	@FXML
	public void showPasswordSignUp() {
		
		if(passwordCheckBoxToggleSignUp.isSelected()) {
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

//---------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	
	private boolean checkPhoneNo(UserRePassword user) {
		
		if(user.getPhoneNo().matches("[0-9]+") && user.getPhoneNo().length() == 10) {
			if(ClientSideUserService.checkPhoneNo(50016, user.getPhoneNo())) {
				signUpWarningLabel.setText("");
				return true;
			} else {
				signUpWarningLabel.setText("An account already exists with the Ph No.");
				return false;
			}
		} else {
			signUpWarningLabel.setText("Enter valid Phone Number");
			return false;
		}
		
	}
	
	private boolean checkNameSignUp(UserRePassword obj) {
		
		boolean checkBool;
		if(obj.getName().equals("") || obj.getName() == null) {
			signUpWarningLabel.setText("Name can't be empty");
			checkBool = false;
		} else {
			checkBool = true;
		}
		if(obj.getName().length() > 30) {
			signUpWarningLabel.setText("Name cannot exceed 30 characters");
			checkBool = false;
		}
		setExistingValuesSignUp();
		return checkBool;
	}
	
	private boolean checkUsernameSignUp(UserRePassword obj) {
		
		boolean checkBool = false;
		if(obj.getUsername().equals("") || obj.getUsername() == null) {
			signUpWarningLabel.setText("Username can't be empty");
			return false;
		} else {
			/*
			 * checks if username contains any different characters
			 * first for loop is for the string
			 * second for loop is to iterate through the main constant string
			 * if the stringChar matches the any of the char then the loop is broken and the loop is checked for new char from the string
			 * even if one char fails it will return false
			 */
			for(char stringChar : obj.getUsername().toCharArray()) {
				
				for(char constChar : CONSTANTS.ALLOWED_USERNAME_CHARS.toCharArray()) {
					
					if(stringChar == constChar) {
						checkBool = true;
						break;
					}
					checkBool = false;
				}
				if(!checkBool) {
					signUpWarningLabel.setText("'" + stringChar + "' Not allowed in Username");
					return false;
				}
			}
			if(obj.getUsername().length() > 30) {
				signUpWarningLabel.setText("Username cannot exceed 30 characters");
				checkBool = false;
			} else {
				//if the username doesn't exist it will return true
				//else if the username exists it will return false
				checkBool = ClientSideUserService.checkExistingUserName(50000, obj.getUsername());
				if(!checkBool) {
//check delete				System.out.println("IN sign up: " + checkBool);
					signUpWarningLabel.setText("Username already exists");
				}
			}
		}
		setExistingValuesSignUp();
		return checkBool;
	}
	
	private boolean checkPasswordSignUp(UserRePassword obj) {
		
		boolean checkBool;
		if(obj.getPassword() == null || obj.getPassword().equals("")) {
			signUpWarningLabel.setText("Password Cannot be empty");
			return false;
		} else if(obj.getConfirmPassword() == null || obj.getConfirmPassword().equals("")) {
			signUpWarningLabel.setText("Confirm Password can't be empty");
			return false;
		}
		
		if(obj.getPassword().equals(obj.getConfirmPassword())) {
			checkBool = true;
		} else {
			signUpWarningLabel.setText("Password doesn't match\nRe-enter password");
			checkBool = false;
		}
		setExistingValuesSignUp();
		return checkBool;
	}
	
	private void setExistingValuesSignUp() {
		
		nameTextFieldSignUp.setText(nameTextFieldSignUp.getText());
		
		usernameTextFieldSignUp.setText(usernameTextFieldSignUp.getText());
		
		passwordTextFieldSignUp.setText(passwordTextFieldSignUp.getText());
		passwordHiddenFieldSignUp.setText(passwordHiddenFieldSignUp.getText());
		
		confirmPasswordTextFieldSignUp.setText(confirmPasswordTextFieldSignUp.getText());
		confirmPasswordHiddenFieldSignUp.setText(confirmPasswordHiddenFieldSignUp.getText());
	}
	
	private void showAlertSignUp() {
		
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
		alert.setTitle("Successful");
		alert.setHeaderText("Sign up complete");
		alert.setContentText("Press Ok to continue");
		Optional<ButtonType> result = alert.showAndWait();
		
		nameTextFieldSignUp.setText("");
		usernameTextFieldSignUp.setText("");
		
		passwordHiddenFieldSignUp.setText("");
		passwordTextField.setText("");
		
		confirmPasswordHiddenFieldSignUp.setText("");
		confirmPasswordTextFieldSignUp.setText("");
		
		if(result.isPresent() && result.get() == ButtonType.OK) {
			unblockUsage();
			MainController.reloadPageStatic();
			clearAuditValues();
			
		}
	}
	
	//settings
// ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	
	@FXML
	public void settingsChangePassword() {
		//todo
		System.out.println("Change Password");
		
	}
	
	@FXML
	public void settingsChangeUsername() {
		//todo
		System.out.println("Change Username");
		
	}
	
	@FXML
	public void deleteAllPosts() {
		
		System.out.println("Delete all Posts");
		
	}
	
	@FXML
	public void deleteAccount() {
		
		System.out.println("Delete Account");
		
	}
	
	@FXML
	public void forgotPassword() {
		//todo
		
		try {
			
			FXMLLoader newFxmlLoader = new FXMLLoader();
			newFxmlLoader.setLocation(getClass().getResource("ForgotPasswordFXML.fxml"));
			
			HBox hBox = newFxmlLoader.load();
			ForgotPasswordController forgotPasswordController = newFxmlLoader.getController();
			
			forgotPasswordController.getChangePasswordButton().setVisible(false);
			forgotPasswordController.getGetOtpButton().setVisible(true);
			
			//adds new children to the previous stack pane which was assign
			insiderUserStackPane.getChildren().addAll(hBox);
			
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
}