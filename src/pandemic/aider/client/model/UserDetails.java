package pandemic.aider.client.model;

import java.io.Serializable;

public class UserDetails implements Serializable {
	
	private String uniqueId; //generated while signing up
	
	private String name;     //name entered by the user
	
	private String username; //username entered by the user
	
	private String password; //password entered by the user
	
	private String time;     //date and time the user was created
	
	private String phoneNo; //to store the phone no
	
	public String getPhoneNo() {
		
		return phoneNo;
	}
	
	public void setPhoneNo(String phoneNo) {
		
		this.phoneNo = phoneNo;
	}
	
	public String getName() {
		
		return name;
	}
	
	public void setName(String name) {
		
		this.name = name;
	}
	
	public String getUsername() {
		
		return username;
	}
	
	public void setUsername(String username) {
		
		this.username = username;
	}
	
	public String getPassword() {
		
		return password;
	}
	
	public void setPassword(String password) {
		
		this.password = password;
	}
	
	public String getTime() {
		
		return time;
	}
	
	public void setTime(String time) {
		
		this.time = time;
	}
	
	public String getUniqueId() {
		
		return uniqueId;
	}
	
	public void setUniqueId(String uniqueId) {
		
		this.uniqueId = uniqueId;
	}
	
	public void setToNull() {
		
		uniqueId = "";
		username = "";
		name = "";
		time = "";
		password = "";
		phoneNo = "";
	}
	
	public UserDetails(String[] arr) {
		
		uniqueId = arr[0];
		name = arr[1];
		username = arr[2];
		password = arr[3];
		time = arr[4];
		phoneNo = arr[5];
	}
	
	public UserDetails(UserRePassword obj) {
		
		this.uniqueId = obj.getUniqueId();
		this.name = obj.getName();
		this.username = obj.getUsername();
		this.password = obj.getPassword();
		this.time = obj.getTime();
		this.phoneNo = obj.getPhoneNo();
	}
	
	//converts to string
	@Override
	public String toString() {
		
		return "UserDetails{" +
				"uniqueId='" + uniqueId + '\'' +
				", name='" + name + '\'' +
				", username='" + username + '\'' +
				", password='" + password + '\'' +
				", time='" + time + '\'' +
				'}';
	}
	
	
	public UserDetails() {
		
		name = null;
	}
}
