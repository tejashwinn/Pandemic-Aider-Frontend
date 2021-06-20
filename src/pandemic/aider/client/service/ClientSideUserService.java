package pandemic.aider.client.service;

import pandemic.aider.client.model.UserDetails;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class ClientSideUserService {
	
	public static boolean checkExistingUserName(int port, String username) {
		try {
			Socket clientSideSocketConnection = new Socket("127.0.0.1", port);
			
			ObjectOutputStream clientSideOutputStream = new ObjectOutputStream(clientSideSocketConnection.getOutputStream());
			clientSideOutputStream.writeObject(username);
//			System.out.println("Passed the object");
			ObjectInputStream clientSideInputStream = new ObjectInputStream(clientSideSocketConnection.getInputStream());
			String str = (String) clientSideInputStream.readObject();
//			System.out.println("Received object: " + str);
			
			clientSideOutputStream.close();
			clientSideInputStream.close();
//			System.out.println("client closed");
			return Boolean.parseBoolean(str);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public static boolean addUser(int port, UserDetails newUser) {
		try {
			Socket clientSideSocketConnection = new Socket("127.0.0.1", port);
			
			ObjectOutputStream clientSideOutputStream = new ObjectOutputStream(clientSideSocketConnection.getOutputStream());
//			System.out.println("Passed the object");
			clientSideOutputStream.writeObject(JsonServiceClient.userToJson(newUser));
//			System.out.println(JsonServiceClient.userToJson(newUser));
			ObjectInputStream clientSideInputStream = new ObjectInputStream(clientSideSocketConnection.getInputStream());
			String str = (String) clientSideInputStream.readObject();
//			System.out.println("Received object: " + str);
			
			clientSideOutputStream.close();
			clientSideInputStream.close();
//			System.out.println("client closed");
			return Boolean.parseBoolean(str);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public static UserDetails checkCredentials(int port, UserDetails userDetails) {
		try {
			Socket clientSideSocketConnection = new Socket("127.0.0.1", port);
			
			ObjectOutputStream clientSideOutputStream = new ObjectOutputStream(clientSideSocketConnection.getOutputStream());
//			System.out.println("Passed the object");
			clientSideOutputStream.writeObject(JsonServiceClient.userToJson(userDetails));
			
			ObjectInputStream clientSideInputStream = new ObjectInputStream(clientSideSocketConnection.getInputStream());
			String str = (String) clientSideInputStream.readObject();
//			System.out.println("Received object: " + str);
			
			clientSideOutputStream.close();
			clientSideInputStream.close();
//			System.out.println("client closed");
			return JsonServiceClient.jsonToUser(str);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static ArrayList<UserDetails> searchUsers(int port, String string) {
		try {
			Socket clientSideSocketConnection = new Socket("127.0.0.1", port);
			
			ObjectOutputStream clientSideOutputStream = new ObjectOutputStream(clientSideSocketConnection.getOutputStream());
//			System.out.println("Passed the object: "+string);
			clientSideOutputStream.writeObject(string);
			
			ObjectInputStream clientSideInputStream = new ObjectInputStream(clientSideSocketConnection.getInputStream());
			String str = (String) clientSideInputStream.readObject();
//			System.out.println("Received object: " + str);
			
			clientSideOutputStream.close();
			clientSideInputStream.close();
//			System.out.println("client closed");
			return JsonServiceClient.jsonToUserList(str);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}

