package pandemic.aider.client;

final public class CONSTANTS {
	
	public static final String LOWERCASE_ALPHA = "abcdefghijklmnopqrstuvwxyz";
	public static final String UPPERCASE_ALPHA = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	public static final String NUMBERS = "0123456789";
	public static final String ALLOWED_CHARS = "_.";
	
	public static final String ALLOWED_USERNAME_CHARS = LOWERCASE_ALPHA + NUMBERS + ALLOWED_CHARS;
	public static final String COMBINED_CHARS = LOWERCASE_ALPHA + UPPERCASE_ALPHA + NUMBERS + ALLOWED_CHARS;
	public static final String PEPPER_PASSWORD = "$2a$10$yubOW.BNOsuNXHfzo1M1xO";
}
