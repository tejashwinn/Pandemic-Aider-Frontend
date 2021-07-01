package pandemic.aider.client.model;

public class UserRePassword extends UserDetails {
	private String confirmPassword;
	
	public String getConfirmPassword() {
		return confirmPassword;
	}
	
	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}
	
	public UserDetails returnUser() {
		return super.returnUser();
	}
	
	public void display() {
		super.display();
		System.out.println("Confirm Password: " + confirmPassword);
	}
	
	
}
