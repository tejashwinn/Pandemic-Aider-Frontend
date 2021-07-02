package pandemic.aider.client.service;

import pandemic.aider.client.model.UserDetails;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class ClientSideUserService {
	
	public static boolean checkExistingUserName(int port, String username) {
		
		try {
			Network.netIsAvailable();
			Socket clientSideSocketConnection = new Socket("127.0.0.1", port);
			
			ObjectOutputStream clientSideOutputStream = new ObjectOutputStream(clientSideSocketConnection.getOutputStream());
			clientSideOutputStream.writeObject(username);
			
			ObjectInputStream clientSideInputStream = new ObjectInputStream(clientSideSocketConnection.getInputStream());
			String str = (String) clientSideInputStream.readObject();
			
			clientSideOutputStream.close();
			clientSideInputStream.close();
			
			return Boolean.parseBoolean(str);
		} catch(Exception e) {
			e.printStackTrace();
			Network.alert();
		}
		return false;
	}
	
	public static boolean checkPhoneNo(int port, String phNo) {
		
		try {
			Network.netIsAvailable();
			Socket clientSideSocketConnection = new Socket("127.0.0.1", port);
			
			ObjectOutputStream clientSideOutputStream = new ObjectOutputStream(clientSideSocketConnection.getOutputStream());
			clientSideOutputStream.writeObject(phNo);
			
			ObjectInputStream clientSideInputStream = new ObjectInputStream(clientSideSocketConnection.getInputStream());
			String str = (String) clientSideInputStream.readObject();
			
			clientSideOutputStream.close();
			clientSideInputStream.close();
			
			return Boolean.parseBoolean(str);
		} catch(Exception e) {
			e.printStackTrace();
			Network.alert();
		}
		return false;
	}
	
	public static boolean addUser(int port, UserDetails newUser) {
		
		try {
			Network.netIsAvailable();
			Socket clientSideSocketConnection = new Socket("127.0.0.1", port);
			
			ObjectOutputStream clientSideOutputStream = new ObjectOutputStream(clientSideSocketConnection.getOutputStream());
			clientSideOutputStream.writeObject(JsonServiceClient.userToJson(newUser));
			
			ObjectInputStream clientSideInputStream = new ObjectInputStream(clientSideSocketConnection.getInputStream());
			String str = (String) clientSideInputStream.readObject();
			
			clientSideOutputStream.close();
			clientSideInputStream.close();
			
			return Boolean.parseBoolean(str);
		} catch(Exception e) {
			e.printStackTrace();
			Network.alert();
			return false;
		}
	}
	
	public static UserDetails checkCredentials(int port, UserDetails userDetails) {
		
		try {
			Network.netIsAvailable();
			Socket clientSideSocketConnection = new Socket("127.0.0.1", port);
			
			ObjectOutputStream clientSideOutputStream = new ObjectOutputStream(clientSideSocketConnection.getOutputStream());
			
			clientSideOutputStream.writeObject(JsonServiceClient.userToJson(userDetails));
			
			ObjectInputStream clientSideInputStream = new ObjectInputStream(clientSideSocketConnection.getInputStream());
			String str = (String) clientSideInputStream.readObject();
			
			clientSideOutputStream.close();
			clientSideInputStream.close();
			
			return JsonServiceClient.jsonToUser(str);
		} catch(Exception e) {
			e.printStackTrace();
			Network.alert();
			return null;
		}
	}
	
	public static ArrayList<UserDetails> searchUsers(int port, String string) {
		
		try {
			Network.netIsAvailable();
			Socket clientSideSocketConnection = new Socket("127.0.0.1", port);
			
			ObjectOutputStream clientSideOutputStream = new ObjectOutputStream(clientSideSocketConnection.getOutputStream());
			clientSideOutputStream.writeObject(string);
			
			ObjectInputStream clientSideInputStream = new ObjectInputStream(clientSideSocketConnection.getInputStream());
			String str = (String) clientSideInputStream.readObject();
			
			clientSideOutputStream.close();
			clientSideInputStream.close();
			
			return JsonServiceClient.jsonToUserList(str);
		} catch(Exception e) {
			e.printStackTrace();
			Network.alert();
			return null;
		}
	}
	
	public static String generateOtp(int port, String phoneNo) {
		
		try {
			Network.netIsAvailable();
			Socket clientSideSocketConnection = new Socket("127.0.0.1", port);
			
			ObjectOutputStream clientSideOutputStream = new ObjectOutputStream(clientSideSocketConnection.getOutputStream());
			clientSideOutputStream.writeObject(phoneNo);
			
			ObjectInputStream clientSideInputStream = new ObjectInputStream(clientSideSocketConnection.getInputStream());
			String str = (String) clientSideInputStream.readObject();
			
			clientSideOutputStream.close();
			clientSideInputStream.close();
			
			return str;
		} catch(Exception e) {
			e.printStackTrace();
			Network.alert();
			return null;
		}
	}
	
	public static boolean changePassword(int port, String string) {
		
		try {
			Network.netIsAvailable();
			Socket clientSideSocketConnection = new Socket("127.0.0.1", port);
			
			ObjectOutputStream clientSideOutputStream = new ObjectOutputStream(clientSideSocketConnection.getOutputStream());
			clientSideOutputStream.writeObject(string);
			
			ObjectInputStream clientSideInputStream = new ObjectInputStream(clientSideSocketConnection.getInputStream());
			String str = (String) clientSideInputStream.readObject();
			
			clientSideOutputStream.close();
			clientSideInputStream.close();
			
			return Boolean.parseBoolean(str);
		} catch(Exception e) {
			e.printStackTrace();
			Network.alert();
			return false;
		}
		
	}
}

