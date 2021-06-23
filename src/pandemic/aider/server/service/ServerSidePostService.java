package pandemic.aider.server.service;

import pandemic.aider.server.dao.PostsDao;
import pandemic.aider.server.model.PostDetails;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerSidePostService {
	
	public static void runServerPost() {
		
		AddPostToDatabase newPost = new AddPostToDatabase();
		newPost.start();
		
		SendPostToClient sendPostToClient = new SendPostToClient();
		sendPostToClient.start();
		
		SearchPostToClient searchSendPostToClient = new SearchPostToClient();
		searchSendPostToClient.start();
		
		SearchPinCodeToClient searchPinCodeToClient = new SearchPinCodeToClient();
		searchPinCodeToClient.start();
		
		DeletePost deletePost = new DeletePost();
		deletePost.start();
	}
}

class AddPostToDatabase extends Thread {
	public void run() {
		
		int port = 50005;
		try {
			
			ServerSocket serverSideSocketConnection = new ServerSocket(port);
			do {
				PostDetails newPost;
				
				System.out.println("Server Running 50005 for adding request");
				
				Socket pipe = serverSideSocketConnection.accept();
				
				ObjectInputStream serverSideInputStream = new ObjectInputStream(pipe.getInputStream());
				
				String str = (String) serverSideInputStream.readObject();
				newPost = JsonServiceServer.jsonToPost(str);

//				System.out.println(str);
				
				ObjectOutputStream serverSideOutputStream = new ObjectOutputStream(pipe.getOutputStream());
				
				PostsDao postNewRequest = new PostsDao();
				
				serverSideOutputStream.writeObject(Boolean.toString(postNewRequest.addToPosts(newPost)));
				
				serverSideInputStream.close();
				serverSideOutputStream.close();
				postNewRequest.closeDbConnection();
			} while(true);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}

class SendPostToClient extends Thread {
	public void run() {
		
		int port = 50006;
		try {
			
			ServerSocket serverSideSocketConnection = new ServerSocket(port);
			do {
				
				System.out.println("Server Running 50006 for sending request");
				
				Socket pipe = serverSideSocketConnection.accept();
				
				ObjectInputStream serverSideInputStream = new ObjectInputStream(pipe.getInputStream());
				
				String str = (String) serverSideInputStream.readObject();
				
				ObjectOutputStream serverSideOutputStream = new ObjectOutputStream(pipe.getOutputStream());
				
				PostsDao postNewRequest = new PostsDao();
				
				serverSideOutputStream.writeObject(JsonServiceServer.fullListToJson(postNewRequest.retrievePosts(str)));
//				System.out.println(JsonServiceServer.fullListToJson());
				
				serverSideInputStream.close();
				serverSideOutputStream.close();
				postNewRequest.closeDbConnection();
			} while(true);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}

class SearchPostToClient extends Thread {
	public void run() {
		
		int port = 50008;
		try {
			
			ServerSocket serverSideSocketConnection = new ServerSocket(port);
			do {
				PostDetails newPost;
				
				System.out.println("Server Running 50008 for searching request");
				
				Socket pipe = serverSideSocketConnection.accept();
				
				ObjectInputStream serverSideInputStream = new ObjectInputStream(pipe.getInputStream());
				
				String str = (String) serverSideInputStream.readObject();
				
				ObjectOutputStream serverSideOutputStream = new ObjectOutputStream(pipe.getOutputStream());
				
				PostsDao postNewRequest = new PostsDao();
//				System.out.println("Received: " + str);
				serverSideOutputStream.writeObject(JsonServiceServer.fullListToJson(postNewRequest.searchRetrievedPosts(str)));
//				System.out.println(JsonServiceServer.fullListToJson(postNewRequest.searchRetrievedPosts(str)));
				serverSideInputStream.close();
				serverSideOutputStream.close();
				postNewRequest.closeDbConnection();
			} while(true);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}

class SearchPinCodeToClient extends Thread {
	public void run() {
		
		int port = 50009;
		try {
			
			ServerSocket serverSideSocketConnection = new ServerSocket(port);
			do {
				
				System.out.println("Server Running 50009 for searching request on pincode");
				
				Socket pipe = serverSideSocketConnection.accept();
				
				ObjectInputStream serverSideInputStream = new ObjectInputStream(pipe.getInputStream());
				
				String str = (String) serverSideInputStream.readObject();
				
				ObjectOutputStream serverSideOutputStream = new ObjectOutputStream(pipe.getOutputStream());
				
				PostsDao postNewRequest = new PostsDao();
				
				serverSideOutputStream.writeObject(JsonServiceServer.fullListToJson(postNewRequest.searchAndRetrievePostsOnPincode(str)));
//				System.out.println(JsonServiceServer.fullListToJson(postNewRequest.searchRetrievedPosts(str)));
				serverSideInputStream.close();
				serverSideOutputStream.close();
				postNewRequest.closeDbConnection();
			} while(true);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}

class DeletePost extends Thread {
	public void run() {
		
		int port = 50010;
		try {
			
			ServerSocket serverSideSocketConnection = new ServerSocket(port);
			do {
				
				System.out.println("Server Running 500010 to delete post");
				
				Socket pipe = serverSideSocketConnection.accept();
				
				ObjectInputStream serverSideInputStream = new ObjectInputStream(pipe.getInputStream());
				
				String str = (String) serverSideInputStream.readObject();
				
				ObjectOutputStream serverSideOutputStream = new ObjectOutputStream(pipe.getOutputStream());
				
				PostsDao deletePostDAO = new PostsDao();
				
				serverSideOutputStream.writeObject(Boolean.toString(deletePostDAO.deletePost(str)));
//				System.out.println(JsonServiceServer.fullListToJson(postNewRequest.searchRetrievedPosts(str)));
				serverSideInputStream.close();
				serverSideOutputStream.close();
				deletePostDAO.closeDbConnection();
			} while(true);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
