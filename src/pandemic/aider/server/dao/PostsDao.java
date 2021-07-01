package pandemic.aider.server.dao;

import pandemic.aider.server.model.PostDetails;
import pandemic.aider.server.service.JsonServiceServer;

import java.sql.*;
import java.util.ArrayList;
import java.util.Locale;
import java.util.UUID;

public class PostsDao {
	
	private Connection connection;
	
	public PostsDao() {
		
		try {
			//we don't need to add forName for microsoft sql
			connection = DriverManager.getConnection(CONNECTION_SQL.URL, CONNECTION_SQL.USERNAME, CONNECTION_SQL.PASSWORD);
//			System.out.println("Connected to database");
		} catch(Exception e) {
//			System.out.println("Database not connected");
			e.printStackTrace();
		}
	}
	
	public void closeDbConnection() {
		
		try {
			connection.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/*
	create table posts(
	P_ID varchar(64) primary key,
	P_UID varchar(64)not null,
	P_Request varchar(512) not null,
	P_Hastags varchar(512),
	P_PinCode varchar(16),
	P_Time varchar(32)not null
			);
	*/
	public boolean checkPostId(String str) {
		
		String sqlQuery = "SELECT * FROM posts WHERE P_ID=" + "'" + str + "'";
		try {
			Statement statement = connection.createStatement();
			ResultSet result = statement.executeQuery(sqlQuery);
			
			//returns boolean value
			return result.next();
		} catch(SQLException throwables) {
			throwables.printStackTrace();
			return false;
		}
	}
	
	public boolean addToPosts(PostDetails post) {
//		post.setContent();
		String sqlQuery = "INSERT INTO posts VALUES(?,?,?,?,?,?)";
		post.setPostUniqueId(UUID.randomUUID().toString());
		try {
			PreparedStatement statement = connection.prepareStatement(sqlQuery);
			
			statement.setString(1, post.getPostUniqueId());
			statement.setString(2, post.getUserUsername());
			statement.setString(3, post.getContent());
			statement.setString(4, ReplaceWithSpace(JsonServiceServer.listToJson(post.getUserTags())));
			statement.setString(5, post.getPincode());
			statement.setString(6, post.getTime());
			//Executes the prepared statement
			statement.executeUpdate();
			
			return checkPostId(post.getPostUniqueId());
		} catch(SQLException throwables) {
			throwables.printStackTrace();
			return false;
		}
	}
	
	private String ReplaceWithSpace(String str) {
		
		str = str.replace("#", " #");
		str = str.replace(",", " ,");
		str = str.replace("]", " ]");
		str = str.replace("\"", " \" ");
		System.out.println(str);
		return str;
		
	}
	
	public ArrayList<PostDetails> retrievePosts(String str) {
		
		String sqlQuery = "SELECT * FROM posts WHERE P_UID=" + "'" + str + "'";
		try {
			Statement statement = connection.createStatement();
			ResultSet result = statement.executeQuery(sqlQuery);
			ArrayList<PostDetails> list = new ArrayList<>();
//			while (result.next()) {
//				list.add(resultToList(result));
//			}
			if(result.next()) {
				list.add(resultToList(result));
				while(result.next()) {
					list.add(resultToList(result));
				}
				return list;
			} else {
				return null;
			}
		} catch(SQLException throwables) {
			throwables.printStackTrace();
			return null;
		}
	}
	
	public ArrayList<PostDetails> searchRetrievedPosts(String str) {
		
		str = str.strip();
		str = str.toLowerCase(Locale.ROOT);
		String sqlQuery;
		if(str.charAt(0) != '#') {
			str = str.replace(" ", "%");
			str = "%" + str + "%";
			sqlQuery = "SELECT * FROM posts WHERE lower(P_Request) LIKE " + "'" + str + "' OR P_UID LIKE '" + str + "'";
		} else {
			sqlQuery = "SELECT * FROM posts WHERE lower(P_Hastags) LIKE " + "'%" + str + "%'";
		}
		//added __ not checked
		
		try {
			Statement statement = connection.createStatement();
			ResultSet result = statement.executeQuery(sqlQuery);
			ArrayList<PostDetails> list = new ArrayList<>();
			if(result.next()) {
				list.add(resultToList(result));
				while(result.next()) {
					list.add(resultToList(result));
				}
				return list;
			} else {
				return null;
			}
		} catch(SQLException throwables) {
			throwables.printStackTrace();
			return null;
		}
	}
	
	public ArrayList<PostDetails> searchAndRetrievePostsOnPincode(String str) {
		
		str = str.toLowerCase(Locale.ROOT);
		String sqlQuery = "SELECT * FROM posts WHERE lower(P_PinCode) LIKE '%" + str + "%'";
		try {
			Statement statement = connection.createStatement();
			ResultSet result = statement.executeQuery(sqlQuery);
			ArrayList<PostDetails> list = new ArrayList<>();
			
			if(result.next()) {
				list.add(resultToList(result));
				while(result.next()) {
					list.add(resultToList(result));
				}
				return list;
			} else {
				return null;
			}
		} catch(SQLException throwables) {
			throwables.printStackTrace();
			return null;
		}
	}
	
	public PostDetails resultToList(ResultSet resultSet) throws SQLException {
		
		String[] strArr = new String[6];
		int i = 0;
		strArr[i++] = resultSet.getString(i);
		strArr[i++] = resultSet.getString(i);
		strArr[i++] = resultSet.getString(i);
		strArr[i++] = resultSet.getString(i);
		strArr[i++] = resultSet.getString(i);
		strArr[i++] = resultSet.getString(i);
		return new PostDetails(strArr);
	}
	
	public boolean deletePost(String uniqueId) {
		
		String sqlQuery = "DELETE FROM posts WHERE P_ID= ?";
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
			preparedStatement.setString(1, uniqueId);
			preparedStatement.execute();
			return checkForPost(uniqueId);
		} catch(SQLException throwables) {
			throwables.printStackTrace();
			return false;
		}
	}
	
	private boolean checkForPost(String postId) {
		
		String sqlQuery = "Select * FROM posts WHERE P_ID= " + "'" + postId + "'";
		try {
			Statement statement = connection.createStatement();
			ResultSet result = statement.executeQuery(sqlQuery);
			
			//if it exists the value will be false if it is true to indicate that the post doesn't exist
			return !result.next();
		} catch(SQLException throwables) {
			throwables.printStackTrace();
			return false; //to indicate query failed
		}
	}
	
	public String[] rowToArray(ResultSet resultSet) throws SQLException {
		
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
		DataAccess obj = new DataAccess();
		try {
			Statement statement = connection.createStatement();
			
			ResultSet result = statement.executeQuery(sqlQuery);
			
			while(result.next()) {
				String[] rowArray = rowToArray(result);
				PostDetails postDetails = new PostDetails(rowArray);
//				postDetails.display();
//				obj.addToPosts(postDetails);
			}
			obj.connectionClose();
		} catch(SQLException throwables) {
			throwables.printStackTrace();
		}
	}
	
}

