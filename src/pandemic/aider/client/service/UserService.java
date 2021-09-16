package pandemic.aider.client.service;

import pandemic.aider.client.model.UserDetails;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class UserService {
	
	public static boolean checkExistingUserName(int port, String username) {
		try {
			ServiceError.netIsAvailable();
			Socket socket = new Socket("127.0.0.1", port);
			ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
			outputStream.writeObject(username);
			ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
			String str = (String) inputStream.readObject();
			outputStream.close();
			inputStream.close();
			return Boolean.parseBoolean(str);
		} catch(Exception e) {
			e.printStackTrace();
			ServiceError.alert();
		}
		return false;
	}
	
	public static boolean checkPhoneNo(int port, String phNo) {
		try {
			ServiceError.netIsAvailable();
			Socket socket = new Socket("127.0.0.1", port);
			ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
			outputStream.writeObject(phNo);
			ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
			String str = (String) inputStream.readObject();
			outputStream.close();
			inputStream.close();
			return Boolean.parseBoolean(str);
		} catch(Exception e) {
			e.printStackTrace();
			ServiceError.alert();
			return false;
		}
	}
	
	public static boolean addUser(int port, UserDetails newUser) {
		try {
			ServiceError.netIsAvailable();
			Socket socket = new Socket("127.0.0.1", port);
			ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
			outputStream.writeObject(JsonService.userToJson(newUser));
			ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
			String str = (String) inputStream.readObject();
			outputStream.close();
			inputStream.close();
			return Boolean.parseBoolean(str);
		} catch(Exception e) {
			e.printStackTrace();
			ServiceError.alert();
			return false;
		}
	}
	
	public static UserDetails checkCredentials(int port, UserDetails userDetails) {
		try {
			ServiceError.netIsAvailable();
			Socket socket = new Socket("127.0.0.1", port);
			ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
			outputStream.writeObject(JsonService.userToJson(userDetails));
			ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
			String str = (String) inputStream.readObject();
			outputStream.close();
			inputStream.close();
			return JsonService.jsonToUser(str);
		} catch(Exception e) {
			e.printStackTrace();
			ServiceError.alert();
			return null;
		}
	}
	
	public static ArrayList<UserDetails> searchUsers(int port, String string) {
		try {
			ServiceError.netIsAvailable();
			Socket socket = new Socket("127.0.0.1", port);
			ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
			outputStream.writeObject(string);
			ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
			String str = (String) inputStream.readObject();
			outputStream.close();
			inputStream.close();
			return JsonService.jsonToUserList(str);
		} catch(Exception e) {
			e.printStackTrace();
			ServiceError.alert();
			return null;
		}
	}
	
	public static String generateOtp(int port, String phoneNo) {
		try {
			ServiceError.netIsAvailable();
			Socket socket = new Socket("127.0.0.1", port);
			ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
			outputStream.writeObject(phoneNo);
			ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
			String str = (String) inputStream.readObject();
			outputStream.close();
			inputStream.close();
			return str;
		} catch(Exception e) {
			e.printStackTrace();
			ServiceError.alert();
			return null;
		}
	}
	
	public static boolean changePassword(int port, String string) {
		try {
			ServiceError.netIsAvailable();
			Socket socket = new Socket("127.0.0.1", port);
			ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
			outputStream.writeObject(string);
			ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
			String str = (String) inputStream.readObject();
			outputStream.close();
			inputStream.close();
			return Boolean.parseBoolean(str);
		} catch(Exception e) {
			e.printStackTrace();
			ServiceError.alert();
			return false;
		}
	}
}

