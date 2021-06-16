package pandemic.aider.client.service;

import com.google.gson.Gson;
import org.json.JSONException;
import pandemic.aider.client.model.UserDetails;

public class JsonServiceClient {
	
	public static UserDetails jsonToUser(String str) {
		Gson gson = new Gson();
		return gson.fromJson(str, UserDetails.class);
	}
	
	public static String userToJson(UserDetails user) throws JSONException {
		return new Gson().toJson(user);
	}
}
