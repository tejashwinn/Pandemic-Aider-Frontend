package pandemic.aider.client.oldfx.model;


/* Class Credential used to store the credentials of the user
 * All the variable-members are private and can be accessed and modified using only the setter and getter methods
 * concept => Encapsulation */

import java.io.Serializable;

public class Credential implements Serializable {
	private String name;     //name entered by the user
	private String username; //username entered by the user
	private String password; //password entered by the user
	private String uniqueId; //generated while signing up refer: General.random();
	private String rePassword;
	
	public String getRePassword() {
		return rePassword;
	}
	
	public void setRePassword(String rePassword) {
		this.rePassword = rePassword;
	}
	
	/* Getter:
	 * * Since the variable-members are private it cannot be accessed anywhere else apart from this class so we use getter to access these values
	 * * The method begins with the keyword get (Not mandatory)
	 * * The method returns the value of the variable */
	
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
	
	/* Setter:
	 * * Since the variable-members are private it cannot be manipulated anywhere else apart from this class so we use setter to manipulate these values
	 * * The method begins with the keyword set (Not mandatory)
	 * * The value which needs to be stored in the variable will be passed as a parameter to the method */
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getUniqueId() {
		return uniqueId;
	}
	
	public void setUniqueId(String uniqueId) {
		this.uniqueId = uniqueId;
	}
}