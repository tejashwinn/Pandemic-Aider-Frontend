package pandemic.aider.client.ui.main;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import org.springframework.security.crypto.bcrypt.BCrypt;
import pandemic.aider.client.CONSTANTS;
import pandemic.aider.client.model.*;
import pandemic.aider.client.service.ClientSidePostService;
import pandemic.aider.client.service.ClientSideUserService;
import pandemic.aider.client.service.JsonServiceClient;
import pandemic.aider.server.service.ServerSidePostService;
import pandemic.aider.server.service.UserServer;

import java.io.*;
import java.net.URL;
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
	
	@FXML
	private HBox viewUserHBox, signInHBox, signUpHBox;
	
	public UserDetails getUser() {
		
		return userDetailsStatic;
	}
	
	public void setUser(UserDetails user) {
		
		MainController.userDetailsStatic = user;
	}
	
	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		
		UserServer.runUserService();
		ServerSidePostService.runServerPost();
		
		//this will initialize the top stack pane which will be used to modify the content
		userTitledPaneStatic = userTitledPane;
		topStackPanePointerVarForViewingSearchUser = topStackPane;

//		userTitledPaneStatic.setExpanded(false);
//		userTitledPaneStatic.setCollapsible(false);
		
		//set toggle
		postsRadioButton.setToggleGroup(radioToggle);
		usersRadioButton.setToggleGroup(radioToggle);
		pincodeRadioButton.setToggleGroup(radioToggle);

//		postsRadioButton.setSelected(true);
		
		userBorderPane.setVisible(false);
		postRequestBorderPane.setVisible(false);
		searchBorderPane.setVisible(false);
		
		postsTitledPane.setCollapsible(false);
		usersTitledPane.setCollapsible(false);
		
		userNameLabelForRefresh = userUsernameLabel;
		postRequestUsernameLabelForRefresh = postRequestUsernameLabel;
		userGridPaneStatic = userGridPane;
		
		viewUserHBoxStatic = viewUserHBox;
		signInHBoxStatic = signInHBox;
		
		searchButton.setDefaultButton(true);
		loadLogInJson();
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

//	@FXML
//	public void reloadPageStatic(ActionEvent event) {
//		refreshUserPage();
//	}
	
	//takes the values from the json format to the user
	private void loadLogInJson() {
		
		try {
			BufferedReader br = new BufferedReader(new FileReader("src/pandemic/aider/client/json/log.json"));
			String str = br.readLine();
			if(str != null) {
				if(!str.equals("{ ") && !str.equals("{\n")) {
					userDetailsStatic = JsonServiceClient.jsonToUser(str);
					JsonSettings.LoggedIn = true;
					refreshUserPage();
				} else {
					userDetailsStatic = null;
					viewUserHBox.setVisible(false);
					signInHBox.setVisible(true);
				}
			} else {
				userDetailsStatic = null;
				viewUserHBox.setVisible(false);
				signInHBox.setVisible(true);
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	@FXML
	public void refreshUserPage() {
		
		try {
			int row = 1;
			userGridPane.getChildren().clear();
			if(userDetailsStatic != null) {
				//display the user name on user scene
				userUsernameLabel.setText(userDetailsStatic.getUsername());
				postRequestUsernameLabel.setText(userDetailsStatic.getUsername());
				
				GetPostArrayList list = new GetPostArrayList();
				list.setPostsList(ClientSidePostService.retrieveRequest(50006, userDetailsStatic.getUsername()));
				viewUserHBox.setVisible(true);
				signInHBox.setVisible(false);
				
				if(list.getPostsList() != null) {
					usersTitledPane.setVisible(true);
					userTitledPane.setExpanded(true);
					userTitledPane.setCollapsible(true);
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
					userTitledPaneStatic.setExpanded(false);
					userTitledPaneStatic.setCollapsible(false);
				}
			} else {
				viewUserHBox.setVisible(false);
				signInHBox.setVisible(true);
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
			
			if(str != null) {
				if(!str.equals("{")) {
					
					userNameLabelForRefresh.setText(userDetailsStatic.getUsername());
					postRequestUsernameLabelForRefresh.setText(userDetailsStatic.getUsername());
					
					userTitledPaneStatic.setVisible(true);
					userTitledPaneStatic.setExpanded(true);
					userTitledPaneStatic.setCollapsible(true);
					
					GetPostArrayList list = new GetPostArrayList();
					list.setPostsList(ClientSidePostService.retrieveRequest(50006, userDetailsStatic.getUsername()));
					
					if(list.getPostsList() != null) {
						
						userTitledPaneStatic.setExpanded(true);
						userTitledPaneStatic.setCollapsible(true);
						
						viewUserHBoxStatic.setVisible(true);
						signInHBoxStatic.setVisible(false);
						for(int i = 0; i < list.getPostsList().size(); i++) {
							
							FXMLLoader fxmlLoader = new FXMLLoader();
							fxmlLoader.setLocation(MainController.class.getResource("ItemFXML.fxml"));
							AnchorPane anchorPane = fxmlLoader.load();
							
							ItemController itemController = fxmlLoader.getController();
							itemController.setData(list.getPostsList().get(i));
							
							userGridPaneStatic.add(anchorPane, 0, row++);
							userTitledPaneStatic.setVisible(true);
							viewUserHBoxStatic.setVisible(true);
							
						}
					} else {
						
						userTitledPaneStatic.setVisible(false);
						
						viewUserHBoxStatic.setVisible(true);
						
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
					viewUserHBoxStatic.setVisible(false);
					signInHBoxStatic.setVisible(true);
				} else {
					viewUserHBoxStatic.setVisible(false);
					signInHBoxStatic.setVisible(true);
				}
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	//general audi
// ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	public static void showSignInStatic() {
		
		viewUserHBoxStatic.setVisible(false);
		signInHBoxStatic.setVisible(true);
	}
	
	@FXML
	public void showSignIn(ActionEvent event) {
		
		viewUserHBox.setVisible(false);
		signInHBox.setVisible(true);
		
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
	public void checkCorrectCredentials(ActionEvent event) {
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
			MainController.reloadPageStatic();
		}
	}
	
	//other functions sign in
//------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	@FXML
	public void switchToSignUp(ActionEvent event) {
		
		signInHBox.setVisible(false);
		signUpHBox.setVisible(true);
	}
	
	@FXML
	public void showPassword(ActionEvent event) {
		
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
	public void cancelSignUpAction(ActionEvent event) {
		
		viewUserHBox.setVisible(true);
		signInHBox.setVisible(false);
		signUpHBox.setVisible(false);
		
	}
	
	//sign up action
// ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	
	@FXML
	private TextField passwordTextFieldSignUp, confirmPasswordTextFieldSignUp, usernameTextFieldSignUp, nameTextFieldSignUp;
	
	@FXML
	private PasswordField passwordHiddenFieldSignUp, confirmPasswordHiddenFieldSignUp;
	
	@FXML
	private CheckBox passwordCheckBoxToggleSignUp;
	
	@FXML
	private Label signUpWarningLabelSignUp;
	
	@FXML
	public void signUpActionSignUp(ActionEvent event) {
		
		boolean validEntry = false;
		UserRePassword userAdd = new UserRePassword();
		UserDetails newCopyObject = null;
		/*
		 * adding this code because when the user edits the password in view mode and hits sign up
		 * it doesn't get updated in hidden mode so this will help us to set it back to the normal
		 */
		if(passwordCheckBoxToggleSignUp.isSelected()) {
			passwordHiddenFieldSignUp.setText(passwordTextFieldSignUp.getText());
			confirmPasswordHiddenFieldSignUp.setText(confirmPasswordTextFieldSignUp.getText());
		}
		/*
		 * adding this code because when the user edits the password in hidden mode
		 */
		
		if(!passwordCheckBoxToggleSignUp.isSelected()) {
			passwordTextFieldSignUp.setText(passwordHiddenFieldSignUp.getText());
			confirmPasswordHiddenFieldSignUp.setText(confirmPasswordHiddenFieldSignUp.getText());
		}
		//assigning the entered values to the object
		userAdd.setName(nameTextFieldSignUp.getText());
		userAdd.setUsername(usernameTextFieldSignUp.getText().toLowerCase());
		userAdd.setPassword(passwordHiddenFieldSignUp.getText());
		userAdd.setConfirmPassword(confirmPasswordHiddenFieldSignUp.getText());
		
		if(checkNameSignUp(userAdd)) {
			if(checkUsernameSignUp(userAdd)) {
				if(checkPasswordSignUp(userAdd)) {
					userAdd.setPassword("");
					//refer: https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/crypto/bcrypt/BCrypt.html
					//there are two BCRYPT values the one used here is from spring boot
					userAdd.setPassword(BCrypt.hashpw(passwordHiddenFieldSignUp.getText(), CONSTANTS.PEPPER_PASSWORD));
					validEntry = true;
				}
			}
		}
		if(validEntry) {
			//sets UUID
			userAdd.setUniqueId(UUID.randomUUID().toString());//
			
			//time generator
			DateTimeFormatter dateTimeFormatterClientAdduser = DateTimeFormatter.ofPattern("yyyy:MM:dd::HH:mm:ss");
			LocalDateTime userCreatedTimeGenerator = LocalDateTime.now();
			
			//sets user creation time
			userAdd.setTime(dateTimeFormatterClientAdduser.format(userCreatedTimeGenerator));
			
			newCopyObject = new UserDetails(userAdd);
			
			validEntry = ClientSideUserService.addUser(50003, newCopyObject);
			
			if(validEntry) {
				
				newCopyObject.setPassword("");
				String jsonString = JsonServiceClient.userToJson(newCopyObject);
				try {
					BufferedWriter bw = new BufferedWriter(new FileWriter("src/pandemic/aider/client/json/log.json"));
					
					bw.write(jsonString);
					bw.close();
					
					MainController.userDetailsStatic = JsonServiceClient.jsonToUser(jsonString);
//					newUserSign = JsonServiceClient.jsonToUser(jsonString);
//					MainController.reloadPageStatic();
					signUpWarningLabelSignUp.setText("Successfully created the account");
					showAlertSignUp();
				} catch(IOException e) {
					e.printStackTrace();
				}
			} else {
				signUpWarningLabelSignUp.setText("Account was not created");
			}
		}
	}
	
	//to view password when toggled
	@FXML
	public void showPasswordSignUp(ActionEvent event) {
		
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
	
	private boolean checkNameSignUp(UserRePassword obj) {
		
		boolean checkBool;
		if(obj.getName().equals("") || obj.getName() == null) {
			signUpWarningLabelSignUp.setText("Name can't be empty");
			checkBool = false;
		} else {
			checkBool = true;
		}
		if(obj.getName().length() > 30) {
			signUpWarningLabelSignUp.setText("Name cannot exceed 30 characters");
			checkBool = false;
		}
		setExistingValuesSignUp();
		return checkBool;
	}
	
	private boolean checkUsernameSignUp(UserRePassword obj) {
		
		boolean checkBool = false;
		if(obj.getUsername().equals("") || obj.getUsername() == null) {
			signUpWarningLabelSignUp.setText("Username can't be empty");
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
					signUpWarningLabelSignUp.setText("'" + stringChar + "' Not allowed in Username");
					return false;
				}
			}
			if(obj.getUsername().length() > 30) {
				signUpWarningLabelSignUp.setText("Username cannot exceed 30 characters");
				checkBool = false;
			} else {
				//if the username doesn't exist it will return true
				//else if the username exists it will return false
				checkBool = ClientSideUserService.checkExistingUserName(50000, obj.getUsername());
				if(!checkBool) {
//check delete				System.out.println("IN sign up: " + checkBool);
					signUpWarningLabelSignUp.setText("Username already exists");
				}
			}
		}
		setExistingValuesSignUp();
		return checkBool;
	}
	
	private boolean checkPasswordSignUp(UserRePassword obj) {
		
		boolean checkBool;
		if(obj.getPassword() == null || obj.getPassword().equals("")) {
			signUpWarningLabelSignUp.setText("Password Cannot be empty");
			return false;
		} else if(obj.getConfirmPassword() == null || obj.getConfirmPassword().equals("")) {
			signUpWarningLabelSignUp.setText("Confirm Password can't be empty");
			return false;
		}
		
		if(obj.getPassword().equals(obj.getConfirmPassword())) {
			checkBool = true;
		} else {
			signUpWarningLabelSignUp.setText("Password doesn't match\nRe-enter password");
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
			MainController.reloadPageStatic();
		}
	}
}