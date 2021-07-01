package pandemic.aider.client.service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.json.JSONException;
import pandemic.aider.client.model.OtpSignUp;
import pandemic.aider.client.model.PostDetails;
import pandemic.aider.client.model.UserDetails;

import java.util.ArrayList;

public class JsonServiceClient {
	
	public static UserDetails jsonToUser(String str) {
		
		Gson gson = new Gson();
		return gson.fromJson(str, UserDetails.class);
	}
	
	public static String userToJson(UserDetails user) throws JSONException {
		
		return new Gson().toJson(user);
	}
	
	public static String postToJson(PostDetails post) {
		
		return new Gson().toJson(post);
	}
	
	public static ArrayList<PostDetails> jsonToFullList(String string) {
		
		Gson gson = new Gson();
		return gson.fromJson(string, new TypeToken<ArrayList<PostDetails>>() {
		}.getType());
	}
	
	public static ArrayList<UserDetails> jsonToUserList(String string) {
		
		Gson gson = new Gson();
		return gson.fromJson(string, new TypeToken<ArrayList<UserDetails>>() {
		}.getType());
	}
	
	public static String otpSignUpTOJson(OtpSignUp otpSignUP) {
		
		return new Gson().toJson(otpSignUP);
	}
	
	public static OtpSignUp jsonToOtpSignUp(String str) {
		
		Gson gson = new Gson();
		return gson.fromJson(str, OtpSignUp.class);
	}
}

