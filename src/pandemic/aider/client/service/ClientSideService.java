package pandemic.aider.client.service;

import pandemic.aider.client.model.UserDetails;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientSideService {
	
	public UserDetails clientReceiveUserDetails(int port) {
		try {
			UserDetails passedObj;
			
			Socket clientSideSocketConnection = new Socket("127.0.0.1", port);
			
			ObjectInputStream clientSideInputStream = new ObjectInputStream(clientSideSocketConnection.getInputStream());
			
			String str = (String) clientSideInputStream.readObject();
			passedObj = JsonServiceClient.jsonToUser(str);
			
			clientSideInputStream.close();
			
			return passedObj;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public void clientToServerSendUserDetails(int port, UserDetails obj) {
		try {
			Socket clientSideSocketConnection = new Socket("127.0.0.1", port);
			System.out.println("Passed the object");
			
			ObjectOutputStream clientSideOutputStream = new ObjectOutputStream(clientSideSocketConnection.getOutputStream());
			clientSideOutputStream.writeObject(JsonServiceClient.userToJson(obj));
			
			clientSideOutputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
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
//			System.out.println("client closed");
			return Boolean.parseBoolean(str);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public static void main(String[] args) throws IOException, ClassNotFoundException {
		ClientSideService objj = new ClientSideService();
		UserDetails obj = new UserDetails();
		obj.setUniqueId("tej");
		obj.setName("1");
		obj.setUsername("1");
		obj.setPassword("1");
		obj.setTime("1");
		obj = objj.clientReceiveUserDetails(50000);
		obj.display();
		objj.clientToServerSendUserDetails(50001, obj);
		objj.clientToServerSendUserDetails(50001, obj);
	}
}

/*
 
 
 */
