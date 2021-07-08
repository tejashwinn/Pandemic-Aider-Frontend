package pandemic.aider.client.model;

import java.io.Serializable;
import java.util.ArrayList;

public class PostDetails implements Serializable {
	
	private String pincode;
	private String content;
	private String postUniqueId;
	private String userUsername;
	private String time;
	
	private ArrayList<String> userTags = new ArrayList<>();
	
	public String getTime() {
		return time;
	}
	
	public void setTime(String time) {
		this.time = time;
	}
	
	public String getPincode() {
		return pincode;
	}
	
	public void setPincode(String pincode) {
		this.pincode = pincode;
	}
	
	public String getContent() {
		return content;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
	
	public String getPostUniqueId() {
		return postUniqueId;
	}
	
	
	public String getUserUsername() {
		return userUsername;
	}
	
	public void setUserUsername(String userUsername) {
		this.userUsername = userUsername;
	}
	
	public ArrayList<String> getUserTags() {
		return userTags;
	}
	
	public void setUserTags(ArrayList<String> userTags) {
		this.userTags = userTags;
	}
	
	
	public void setPostUniqueId(String postUniqueId) {
		
		this.postUniqueId = postUniqueId;
	}
}
