package pandemic.aider.client.model;

public class OtpSignUp extends UserDetails {
	private String otpGeneratedTime;
	
	public String getOtpGeneratedTime() {
		
		return otpGeneratedTime;
	}
	
	public void setOtpGeneratedTime(String otpGeneratedTime) {
		
		this.otpGeneratedTime = otpGeneratedTime;
	}
	
	public OtpSignUp(UserDetails userDetails) {
		
		super.setName(userDetails.getName());
		super.setUsername(userDetails.getUsername());
		super.setPhoneNo(userDetails.getPhoneNo());
		
	}
	
	public OtpSignUp() {
		
		otpGeneratedTime = null;
	}
}
