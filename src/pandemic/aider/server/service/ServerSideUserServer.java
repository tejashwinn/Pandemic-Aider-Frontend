package pandemic.aider.server.service;

import pandemic.aider.server.dao.UsersDao;
import pandemic.aider.server.model.UserDetails;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerSideUserServer {
	
	public static void runUserService() {
		
		UsernameVerification usernameVerification = new UsernameVerification();
		usernameVerification.start();
		
		AddUserToDatabase addUserToDatabase = new AddUserToDatabase();
		addUserToDatabase.start();
		
		CheckCredentials checkCredentials = new CheckCredentials();
		checkCredentials.start();
		
		SendUserInfo sendUsersInfo = new SendUserInfo();
		sendUsersInfo.start();
		
		GenerateOtp generateOtp = new GenerateOtp();
		generateOtp.start();
		
		CheckPhNO checkPhNO = new CheckPhNO();
		checkPhNO.start();
		
		ChangePassword changePassword = new ChangePassword();
		changePassword.start();
	}
}

class UsernameVerification extends Thread {
	public void run() {
		
		int port = 50000;
		try {
			
			ServerSocket socketConnection = new ServerSocket(port);
			do {
				System.out.println("Server Running 50000 for username check");
				
				Socket pipe = socketConnection.accept();
				ObjectInputStream inputStream = new ObjectInputStream(pipe.getInputStream());
				
				String str = (String) inputStream.readObject();
				
				ObjectOutputStream outputStream = new ObjectOutputStream(pipe.getOutputStream());
				UsersDao daoUsername = new UsersDao();
				outputStream.writeObject(Boolean.toString(daoUsername.checkUsername(str)));
				
				inputStream.close();
				outputStream.close();
				daoUsername.closeDbConnection();
			} while(true);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}

class AddUserToDatabase extends Thread {
	public void run() {
		
		int port = 50003;
		try {
			
			ServerSocket serverSideSocketConnection = new ServerSocket(port);
			do {
				UserDetails newUser;
				
				System.out.println("Server Running 50003 for adding user");
				
				Socket pipe = serverSideSocketConnection.accept();
				
				ObjectInputStream serverSideInputStream = new ObjectInputStream(pipe.getInputStream());
				
				String str = (String) serverSideInputStream.readObject();
				newUser = JsonServiceServer.jsonToUser(str);

//				System.out.println(str);
				
				ObjectOutputStream serverSideOutputStream = new ObjectOutputStream(pipe.getOutputStream());
				
				UsersDao daoUsername = new UsersDao();
				serverSideOutputStream.writeObject(Boolean.toString(daoUsername.addToUsers(newUser)));
				
				serverSideInputStream.close();
				serverSideOutputStream.close();
				daoUsername.closeDbConnection();
			} while(true);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}

class CheckCredentials extends Thread {
	public void run() {
		
		int port = 50004;
		try {
			
			ServerSocket serverSideSocketConnection = new ServerSocket(port);
			do {
				
				System.out.println("Server Running 50004 for logging in");
				
				Socket pipe = serverSideSocketConnection.accept();
				ObjectInputStream serverSideInputStream = new ObjectInputStream(pipe.getInputStream());
				
				String str = (String) serverSideInputStream.readObject();
//				System.out.println(str);
				
				ObjectOutputStream serverSideOutputStream = new ObjectOutputStream(pipe.getOutputStream());
				
				UsersDao daoUsername = new UsersDao();
				String[] rowValue = daoUsername.checkCredentials(JsonServiceServer.jsonToUser(str));
				
				UserDetails checkCredentialUser = new UserDetails(rowValue);
				
				checkCredentialUser.setPassword("");
				serverSideOutputStream.writeObject(JsonServiceServer.userToJson(checkCredentialUser));
//				checkCredentialUser.display();
//
////				System.out.println("sent data: " + );
				
				serverSideInputStream.close();
				serverSideOutputStream.close();
				daoUsername.closeDbConnection();
			} while(true);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}

class SendUserInfo extends Thread {
	public void run() {
		
		int port = 50007;
		try {
			
			ServerSocket serverSideSocketConnection = new ServerSocket(port);
			do {
				System.out.println("Server Running 50007 for searching users");
//
				Socket pipe = serverSideSocketConnection.accept();
				ObjectInputStream serverSideInputStream = new ObjectInputStream(pipe.getInputStream());
				
				String str = (String) serverSideInputStream.readObject();
//				System.out.println(str);
				
				ObjectOutputStream serverSideOutputStream = new ObjectOutputStream(pipe.getOutputStream());
				
				UsersDao searchUsers = new UsersDao();
				
				serverSideOutputStream.writeObject(JsonServiceServer.listToUsersJson(searchUsers.searchUsers(str)));
//				checkCredentialUser.display();
//
////				System.out.println("sent data: " + );
				
				serverSideInputStream.close();
				serverSideOutputStream.close();
				searchUsers.closeDbConnection();
			} while(true);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}

class GenerateOtp extends Thread {
	public void run() {
		
		int port = 50015;
		try {
			
			ServerSocket serverSideSocketConnection = new ServerSocket(port);
			do {
				System.out.println("Server Running 50015 for generating otp");
//
				Socket pipe = serverSideSocketConnection.accept();
				ObjectInputStream serverSideInputStream = new ObjectInputStream(pipe.getInputStream());
				
				String str = (String) serverSideInputStream.readObject();
//				System.out.println(str);
				
				ObjectOutputStream serverSideOutputStream = new ObjectOutputStream(pipe.getOutputStream());
				
				serverSideOutputStream.writeObject((OTP.send(str)));
				
				serverSideInputStream.close();
				serverSideOutputStream.close();
				
			} while(true);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}

class CheckPhNO extends Thread {
	public void run() {
		
		int port = 50016;
		try {
			
			ServerSocket serverSideSocketConnection = new ServerSocket(port);
			do {
				System.out.println("Server Running 50016 for Ph No check");
//
				Socket pipe = serverSideSocketConnection.accept();
				ObjectInputStream serverSideInputStream = new ObjectInputStream(pipe.getInputStream());
				
				String str = (String) serverSideInputStream.readObject();
//				System.out.println(str);
				
				ObjectOutputStream serverSideOutputStream = new ObjectOutputStream(pipe.getOutputStream());
				UsersDao usersDao = new UsersDao();
				
				serverSideOutputStream.writeObject(Boolean.toString(usersDao.checkPhNO(str)));
				
				serverSideInputStream.close();
				serverSideOutputStream.close();
				
			} while(true);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}

class ChangePassword extends Thread {
	
	public void run() {
		
		int port = 50017;
		try {
			
			ServerSocket serverSideSocketConnection = new ServerSocket(port);
			do {
				System.out.println("Server Running 50017 for changing password");
//
				Socket pipe = serverSideSocketConnection.accept();
				ObjectInputStream serverSideInputStream = new ObjectInputStream(pipe.getInputStream());
				
				String str = (String) serverSideInputStream.readObject();
//				System.out.println(str);
				
				ObjectOutputStream serverSideOutputStream = new ObjectOutputStream(pipe.getOutputStream());
				UsersDao usersDao = new UsersDao();
				
				serverSideOutputStream.writeObject(Boolean.toString(usersDao.changePassword(str)));
				
				serverSideInputStream.close();
				serverSideOutputStream.close();
				
			} while(true);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}

//---------------------------------------------------------------------------------------------------------------------------------------------
