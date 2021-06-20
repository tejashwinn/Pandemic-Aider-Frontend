package pandemic.aider.client.service;

import pandemic.aider.client.model.PostDetails;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class ClientSidePostService {
	public static boolean postRequest(int port, PostDetails post) {
		try {
			Socket clientSideSocketConnection = new Socket("127.0.0.1", port);
			
			ObjectOutputStream clientSideOutputStream = new ObjectOutputStream(clientSideSocketConnection.getOutputStream());
//			System.out.println("Passed the object");
			clientSideOutputStream.writeObject(JsonServiceClient.postToJson(post));
			
			ObjectInputStream clientSideInputStream = new ObjectInputStream(clientSideSocketConnection.getInputStream());
			String str = (String) clientSideInputStream.readObject();
//			System.out.println("Received object: " + str);
			
			clientSideOutputStream.close();
			clientSideInputStream.close();
//			System.out.println("client closed");
			return Boolean.parseBoolean(str);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public static ArrayList<PostDetails> retrieveRequest(int port, String username) {
		try {
			Socket clientSideSocketConnection = new Socket("127.0.0.1", port);
			
			ObjectOutputStream clientSideOutputStream = new ObjectOutputStream(clientSideSocketConnection.getOutputStream());
//			System.out.println("Passed the object");
			clientSideOutputStream.writeObject(username);
			
			ObjectInputStream clientSideInputStream = new ObjectInputStream(clientSideSocketConnection.getInputStream());
			String str = (String) clientSideInputStream.readObject();
//			System.out.println(str);
			ArrayList<PostDetails> list;
			list = JsonServiceClient.jsonToFullList(str);
			
			clientSideOutputStream.close();
			clientSideInputStream.close();
//			System.out.println("client closed");
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static ArrayList<PostDetails> searchPostRequest(int port, String string) {
		try {
			Socket clientSideSocketConnection = new Socket("127.0.0.1", port);
			
			ObjectOutputStream clientSideOutputStream = new ObjectOutputStream(clientSideSocketConnection.getOutputStream());
//			System.out.println("Passed the object");
			clientSideOutputStream.writeObject(string);
			
			ObjectInputStream clientSideInputStream = new ObjectInputStream(clientSideSocketConnection.getInputStream());
			String str = (String) clientSideInputStream.readObject();
//			System.out.println(str);
			ArrayList<PostDetails> list;
			list = JsonServiceClient.jsonToFullList(str);
			
			clientSideOutputStream.close();
			clientSideInputStream.close();
//			System.out.println("client closed");
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static ArrayList<PostDetails> searchPincodeRequest(int port, String string) {
		try {
			Socket clientSideSocketConnection = new Socket("127.0.0.1", port);
			
			ObjectOutputStream clientSideOutputStream = new ObjectOutputStream(clientSideSocketConnection.getOutputStream());
//			System.out.println("Passed the object");
			clientSideOutputStream.writeObject(string);
			
			ObjectInputStream clientSideInputStream = new ObjectInputStream(clientSideSocketConnection.getInputStream());
			String str = (String) clientSideInputStream.readObject();
//			System.out.println(str);
			ArrayList<PostDetails> list;
			list = JsonServiceClient.jsonToFullList(str);
			
			clientSideOutputStream.close();
			clientSideInputStream.close();
//			System.out.println("client closed");
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
