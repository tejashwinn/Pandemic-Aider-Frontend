package pandemic.aider.server.service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.json.JSONException;
import pandemic.aider.server.model.PostDetails;
import pandemic.aider.server.model.UserDetails;

import java.util.ArrayList;
import java.util.List;

public class JsonServiceServer {
	
	public static UserDetails jsonToUser(String str) {
		Gson gson = new Gson();
		return gson.fromJson(str, UserDetails.class);
	}
	
	public static String userToJson(UserDetails user) throws JSONException {
		return new Gson().toJson(user);
	}
	
	public static PostDetails jsonToPost(String str) {
		Gson gson = new Gson();
		return gson.fromJson(str, PostDetails.class);
	}
	
	public static String postToJson(PostDetails post) {
		return new Gson().toJson(post);
	}
	
	public static ArrayList<String> strToList(String string) {
		Gson gson = new Gson();
		return gson.fromJson(string, new TypeToken<List<String>>() {
		}.getType());
	}
	
	public static String listToJson(ArrayList<String> list) {
		return new Gson().toJson(list);
	}
	
	public static String fullListToJson(ArrayList<PostDetails> list) {
		return new Gson().toJson(list);
	}
	
	public static ArrayList<PostDetails> jsonToFullList(String string) {
		Gson gson = new Gson();
		return gson.fromJson(string, new TypeToken<ArrayList<PostDetails>>() {
		}.getType());
	}
	
	public static String listToUsersJson(ArrayList<UserDetails> list) {
		return new Gson().toJson(list);
	}
}
