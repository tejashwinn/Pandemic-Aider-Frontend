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
//todo delete
//    public static void main(String[] ags) throws JSONException, IOException {
//        UserDetails obj = new UserDetails();
//        obj.setUniqueId("fdsafdfdsada12321113232");
//        obj.setName("1");
//        obj.setUsername("fdsad121231321231");
//        obj.setPassword("dfd");
//        obj.setTime("fdsaf");
//
//        System.out.println("To string:\n"+obj.toString());
//        String temp = userToJson(obj);
//        System.out.println("Converted:\n" + temp);
//
//        UserDetails oj1 = jsonToUser(temp);
//
//        oj1.display();
//
//
//    }
}
