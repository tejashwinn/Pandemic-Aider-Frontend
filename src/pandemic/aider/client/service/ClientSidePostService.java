package pandemic.aider.client.service;

import pandemic.aider.client.model.PostDetails;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class ClientSidePostService {
	public static boolean postRequest(int port, PostDetails post) {
		
		try {
			Network.netIsAvailable();
			Socket clientSideSocketConnection = new Socket("127.0.0.1", port);
			
			ObjectOutputStream clientSideOutputStream = new ObjectOutputStream(clientSideSocketConnection.getOutputStream());
			clientSideOutputStream.writeObject(JsonServiceClient.postToJson(post));
			
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
	
	public static ArrayList<PostDetails> retrieveRequest(int port, String username) {
		
		try {
			Network.netIsAvailable();
			Socket clientSideSocketConnection = new Socket("127.0.0.1", port);
			
			ObjectOutputStream clientSideOutputStream = new ObjectOutputStream(clientSideSocketConnection.getOutputStream());
			clientSideOutputStream.writeObject(username);
			
			ObjectInputStream clientSideInputStream = new ObjectInputStream(clientSideSocketConnection.getInputStream());
			String str = (String) clientSideInputStream.readObject();
			
			ArrayList<PostDetails> list;
			list = JsonServiceClient.jsonToFullList(str);
			
			clientSideOutputStream.close();
			clientSideInputStream.close();
			return list;
		} catch(Exception e) {
			e.printStackTrace();
			Network.alert();
			
			return null;
		}
	}
	
	public static ArrayList<PostDetails> searchPostRequest(int port, String string) {
		
		try {
			Network.netIsAvailable();
			Socket clientSideSocketConnection = new Socket("127.0.0.1", port);
			
			ObjectOutputStream clientSideOutputStream = new ObjectOutputStream(clientSideSocketConnection.getOutputStream());
			
			clientSideOutputStream.writeObject(string);
			
			ObjectInputStream clientSideInputStream = new ObjectInputStream(clientSideSocketConnection.getInputStream());
			String str = (String) clientSideInputStream.readObject();
			
			ArrayList<PostDetails> list;
			list = JsonServiceClient.jsonToFullList(str);
			
			clientSideOutputStream.close();
			clientSideInputStream.close();
			return list;
		} catch(Exception e) {
			e.printStackTrace();
			Network.alert();
			
			return null;
		}
	}
	
	public static ArrayList<PostDetails> searchPincodeRequest(int port, String string) {
		
		try {
			Network.netIsAvailable();
			Socket clientSideSocketConnection = new Socket("127.0.0.1", port);
			
			ObjectOutputStream clientSideOutputStream = new ObjectOutputStream(clientSideSocketConnection.getOutputStream());
			clientSideOutputStream.writeObject(string);
			
			ObjectInputStream clientSideInputStream = new ObjectInputStream(clientSideSocketConnection.getInputStream());
			String str = (String) clientSideInputStream.readObject();
			
			ArrayList<PostDetails> list;
			list = JsonServiceClient.jsonToFullList(str);
			
			clientSideOutputStream.close();
			clientSideInputStream.close();
			return list;
		} catch(Exception e) {
			e.printStackTrace();
			Network.alert();
			return null;
		}
	}
	
	public static boolean deletePost(int port, String postId) {
		
		try {
			Network.netIsAvailable();
			Socket clientSideSocketConnection = new Socket("127.0.0.1", port);
			
			ObjectOutputStream clientSideOutputStream = new ObjectOutputStream(clientSideSocketConnection.getOutputStream());
			clientSideOutputStream.writeObject(postId);
			
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