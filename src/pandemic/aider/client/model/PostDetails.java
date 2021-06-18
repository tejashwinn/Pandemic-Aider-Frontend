package pandemic.aider.client.model;

import java.util.ArrayList;
import java.util.List;

public class PostDetails {
	
	private String pincode;
	private String content;
	private String postUniqueId;
	private String userUniqueId;
	private String time;
	
	public String getTime() {
		return time;
	}
	
	public void setTime(String time) {
		this.time = time;
	}
	
	private List<String> userTags = new ArrayList<>();
	
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
	
	public String getUserUniqueId() {
		return userUniqueId;
	}
	
	public void setUserUniqueId(String userUniqueId) {
		this.userUniqueId = userUniqueId;
	}
	
	public List<String> getUserTags() {
		return userTags;
	}
	
	public void setUserTags(List<String> userTags) {
		this.userTags = userTags;
	}
	
	public void display() {
		
		System.out.println(userUniqueId);
		System.out.println(pincode);
		System.out.println(content);
		System.out.println(time);
		System.out.println();
	}
}
