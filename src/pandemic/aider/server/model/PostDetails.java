package pandemic.aider.server.model;

import pandemic.aider.server.service.JsonServiceServer;

import java.io.Serializable;
import java.util.ArrayList;

public class PostDetails implements Serializable {
	private String pincode;
	private String content;
	private String postUniqueId;
	private String userUsername;
	private String time;
	
	private ArrayList<String> userTags;
	
	public PostDetails(String[] strArr) {
		postUniqueId = strArr[0];
		userUsername = strArr[1];
		content = strArr[2];
		userTags = JsonServiceServer.strToList(strArr[3]);
		pincode = strArr[4];
		time = strArr[5];
	}
	
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
	
	public void setPostUniqueId(String postUniqueId) {
		this.postUniqueId = postUniqueId;
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
	
	public void display() {
		System.out.println(userUsername);
		System.out.println(pincode);
		System.out.println(content);
		System.out.println(time);
		System.out.println(userTags);
		System.out.println(postUniqueId);
		System.out.println();
		System.out.println();
	}
}
