package pandemic.aider.client.model;

import org.apache.commons.codec.digest.DigestUtils;

import java.io.Serializable;

/*
 * Class Credential used to store the credentials of the user
 * All the variable-members are private and can be accessed and modified using only the setter and getter methods
 * concept => Encapsulation
 */

public class UserDetails implements Serializable {
	
	private String uniqueId; //generated while signing up
	private String name;     //name entered by the user
	private String username; //username entered by the user
	private String password; //password entered by the user
	private String time;     //date and time the user was created
	
	public UserDetails() {
		name = null;
	}
	
	public UserDetails(String[] arr) {
		uniqueId = arr[0];
		name = arr[1];
		username = arr[2];
		password = arr[3];
		time = arr[4];
	}
	
	public UserDetails(UserRePassword obj) {
		this.uniqueId = obj.getUniqueId();
		this.name = obj.getName();
		this.username = obj.getUsername();
		this.password = obj.getPassword();
		this.time = obj.getTime();
	}
	
	public void securePassword() {
		password = DigestUtils.sha256Hex(password);
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
	
	//displays the user on console
	public void display() {
		System.out.println("UniqueId:" + uniqueId);
		System.out.println("Name: " + name);
		System.out.println("Username: " + username);
		System.out.println("Password: " + password);
		System.out.println("Time: " + time);
		System.out.println();
	}
	
	public UserDetails returnUser() {
		return this;
	}
	
	/*
	 * Getter:
	 * * Since the variable-members are private it cannot be accessed anywhere else apart from this class so we use getter to access these values
	 * * The method begins with the keyword get (Not mandatory)
	 * * The method returns the value of the variable
	 */
	
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
	
	/*
	 * Setter:
	 * * Since the variable-members are private it cannot be manipulated anywhere else apart from this class so we use setter to manipulate these values
	 * * The method begins with the keyword set (Not mandatory)
	 * * The value which needs to be stored in the variable will be passed as a parameter to the method
	 */
	
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
	}
}
