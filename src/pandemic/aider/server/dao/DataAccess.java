package pandemic.aider.server.dao;

import pandemic.aider.server.model.PostDetails;
import pandemic.aider.server.model.UserDetails;
import pandemic.aider.server.service.JsonServiceServer;

import java.sql.*;
import java.util.UUID;

//todo delete
public class DataAccess {
	
	private static Connection conn;
	
	private static final String DB_URL = "jdbc:mysql://sql6.freesqldatabase.com/sql6420691";
	
	private static final String USER = "sql6420691";
	
	private static final String PASS = "DAktNJhXAe";
	
	public DataAccess() {
		
		try {
			//creates a connection to the database
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			System.out.println("connected to online database");
		} catch(SQLException e) {
			e.printStackTrace();
			
		}
	}
	
	//to close the connection to the database
	public void connectionClose() {
		
		try {
			conn.close();
		} catch(SQLException throwables) {
			throwables.printStackTrace();
		}
	}
	
	public void addToUsers(UserDetails user) {
		//having prepared statement for insertion
		String sqlQuery = "INSERT INTO users VALUES(?,?,?,?,?)";
		try {
			PreparedStatement statement = conn.prepareStatement(sqlQuery);
			
			statement.setString(1, user.getUniqueId());
			statement.setString(2, user.getName());
			statement.setString(3, user.getUsername());
			statement.setString(4, user.getPassword());
			statement.setString(5, user.getTime());
			
			//Executes the prepared statement
			statement.executeUpdate();
			

		} catch(SQLException throwables) {
			throwables.printStackTrace();
//			return false;
		}
	}
	
	public boolean usersTable() {
		//created table
		String sqlQuery = """
				CREATE TABLE users (
					U_ID  varchar(64) PRIMARY KEY,
					U_Name varchar(64),
					U_Username varchar(32)not null unique,
					U_Password varchar(128) not null,
					U_Time varchar(32) not null
					)""";
		System.out.println(sqlQuery);
		try {
			Statement stmt = conn.createStatement();
			return stmt.execute(sqlQuery);
		} catch(SQLException e) {
			System.out.println(e.getMessage());
			return false;
		}
	}
	
	public boolean postsTable() {
		//created table
		String sqlQuery = """
				create table posts(
					P_ID varchar(64) primary key,
					P_UID varchar(64)not null,
					P_Request varchar(512) not null,
					P_Hastags varchar(512),
					P_PinCode varchar(16),
					P_Time varchar(32)not null
				);""";
		System.out.println(sqlQuery);
		try {
			Statement stmt = conn.createStatement();
			return stmt.execute(sqlQuery);
		} catch(SQLException e) {
			System.out.println(e.getMessage());
			return false;
		}
	}
	
	public String[] rowToArray(ResultSet resultSet) throws SQLException {
		
		String[] arrayOfResultSet = new String[5];
		int count = 0;
		for(int i = 1; i <= 5; i++) {
			arrayOfResultSet[count] = resultSet.getString(i);
			++count;
		}
		
		return arrayOfResultSet;
	}
	
	public void displayALlUsers() {
		//having prepared statement for insertion
		String sqlQuery = "SELECT * FROM users";
		try {
			Statement statement = conn.createStatement();
			
			ResultSet result = statement.executeQuery(sqlQuery);
			
			while(result.next()) {
				String[] rowArray = rowToArray(result);
				UserDetails users = new UserDetails(rowArray);
				users.display();
			}
		} catch(SQLException throwables) {
			throwables.printStackTrace();
		}
	}
//	public void transfer(){
//		UsersDao usersDao = new UsersDao();
//
//	}
	
	public void addToPosts(PostDetails post) {
//		post.setContent();
		String sqlQuery = "INSERT INTO posts VALUES(?,?,?,?,?,?)";
		post.setPostUniqueId(UUID.randomUUID().toString());
		try {
			PreparedStatement statement = conn.prepareStatement(sqlQuery);
			statement.setString(1, post.getPostUniqueId());
			statement.setString(2, post.getUserUsername());
			statement.setString(3, post.getContent());
			statement.setString(4, JsonServiceServer.listToJson(post.getUserTags()));
			statement.setString(5, post.getPincode());
			statement.setString(6, post.getTime());
			//Executes the prepared statement
			statement.executeUpdate();
		} catch(SQLException throwables) {
			throwables.printStackTrace();
		}
	}
	
	public String[] rowToArray2(ResultSet resultSet) throws SQLException {
		
		String[] arrayOfResultSet = new String[6];
		int count = 0;
		for(int i = 1; i <= 6; i++) {
			arrayOfResultSet[count] = resultSet.getString(i);
			++count;
		}
		
		return arrayOfResultSet;
	}
	
	public void displayALlPosts() {
		//having prepared statement for insertion
		String sqlQuery = "SELECT * FROM posts";
//		DataAccess obj= new DataAccess();
		try {
			Statement statement = conn.createStatement();
			
			ResultSet result = statement.executeQuery(sqlQuery);
			
			while(result.next()) {
				String[] rowArray = rowToArray2(result);
				PostDetails postDetails = new PostDetails(rowArray);
//				postDetails.display(); //to transfer to another database
				postDetails.display();
			}
		} catch(SQLException throwables) {
			throwables.printStackTrace();
		}
	}
	
	public static void main(String[] ags) {
		
		DataAccess obj = new DataAccess();
		obj.displayALlUsers();
		obj.displayALlPosts();
		obj.connectionClose();
	}
}