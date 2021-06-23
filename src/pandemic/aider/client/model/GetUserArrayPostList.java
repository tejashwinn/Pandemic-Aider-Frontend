package pandemic.aider.client.model;

import java.util.ArrayList;

public class GetUserArrayPostList {
	
	private ArrayList<UserDetails> usersList = new ArrayList<>();
	
	public ArrayList<UserDetails> getUsersList() {
		return usersList;
	}
	
	public void setUsersList(ArrayList<UserDetails> usersList) {
		this.usersList = usersList;
	}
}
