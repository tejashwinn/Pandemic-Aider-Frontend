package pandemic.aider.client;

import javafx.scene.image.Image;

final public class CONSTANTS {
	
	public static final String LOWERCASE_ALPHA = "abcdefghijklmnopqrstuvwxyz";
	
	public static final String NUMBERS = "0123456789";
	
	public static final String ALLOWED_CHARS = "_.";
	
	public static final String ALLOWED_USERNAME_CHARS = LOWERCASE_ALPHA + NUMBERS + ALLOWED_CHARS;
	
	public static final String PEPPER_PASSWORD = "$2a$10$yubOW.BNOsuNXHfzo1M1xO";
	
	public static final Image MAIN_LOGO = new Image("pandemic/aider/client/res/icons8-calendar-19-60.png");
	
}