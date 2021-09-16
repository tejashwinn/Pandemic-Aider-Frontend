package pandemic.aider.client.service;

import pandemic.aider.client.model.PostDetails;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class PostService {
	public static boolean postRequest(int port, PostDetails post) {
		try {
			ServiceError.netIsAvailable();
			Socket socket = new Socket("127.0.0.1", port);
			ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
			outputStream.writeObject(JsonService.postToJson(post));
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
	
	public static ArrayList<PostDetails> retrieveRequest(int port, String username) {
		try {
			ServiceError.netIsAvailable();
			Socket socket = new Socket("127.0.0.1", port);
			ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
			outputStream.writeObject(username);
			ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
			String str = (String) inputStream.readObject();
			ArrayList<PostDetails> list;
			list = JsonService.jsonToFullList(str);
			outputStream.close();
			inputStream.close();
			return list;
		} catch(Exception e) {
			e.printStackTrace();
			ServiceError.alert();
			return null;
		}
	}
	
	public static ArrayList<PostDetails> searchPostRequest(int port, String string) {
		try {
			ServiceError.netIsAvailable();
			Socket socket = new Socket("127.0.0.1", port);
			ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
			outputStream.writeObject(string);
			ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
			String str = (String) inputStream.readObject();
			ArrayList<PostDetails> list;
			list = JsonService.jsonToFullList(str);
			outputStream.close();inputStream.close();
			return list;
		} catch(Exception e) {
			e.printStackTrace();
			ServiceError.alert();
			return null;
		}
	}
	public static ArrayList<PostDetails> searchPincodeRequest(int port, String string) {
		try {
			ServiceError.netIsAvailable();
			Socket socket = new Socket("127.0.0.1", port);
			ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
			outputStream.writeObject(string);
			ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
			String str = (String) inputStream.readObject();
			ArrayList<PostDetails> list;
			list = JsonService.jsonToFullList(str);
			outputStream.close();inputStream.close();
			return list;
		} catch(Exception e) {
			e.printStackTrace();
			ServiceError.alert();
			return null;
		}
	}
	public static boolean deletePost(int port, String postId) {
		try {
			ServiceError.netIsAvailable();
			Socket socket = new Socket("127.0.0.1", port);
			ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
			outputStream.writeObject(postId);
			ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
			String str = (String) inputStream.readObject();
			outputStream.close();inputStream.close();
			return Boolean.parseBoolean(str);
		} catch(Exception e) {
			e.printStackTrace();
			ServiceError.alert();
			return false;
		}
	}
}