package pandemic.aider.server.dao;

import pandemic.aider.server.model.UserDetails;

import java.sql.*;
import java.util.ArrayList;
import java.util.Locale;

public class UsersDao {
	
	private Connection connection;
	
	public UsersDao() {
		
		try {
			connection = DriverManager.getConnection(CONNECTION_SQL.URL, CONNECTION_SQL.USERNAME, CONNECTION_SQL.PASSWORD);
		} catch(Exception e) {
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
	
	//will return true if the username doesn't exist
	public boolean checkUsername(String username) {
		
		String sqlQuery = "SELECT U_Username FROM users WHERE U_Username=" + "'" + username + "'";
		
		try {
			Statement statement = connection.createStatement();
			
			ResultSet result = statement.executeQuery(sqlQuery);
			return !result.next();
		} catch(SQLException throwables) {
			throwables.printStackTrace();
			return false;
		}
	}
	
	public String[] checkCredentials(UserDetails user) {
		
		String[] temp = new String[6];
		String sqlQuery = "SELECT * FROM users WHERE U_Username=" + "'" + user.getUsername() + "'" + " AND U_Password=" + "'" + user.getPassword() + "'";
		try {
			Statement statement = connection.createStatement();
			ResultSet result = statement.executeQuery(sqlQuery);
			if(result.next()) {
				return rowToArray(result);
			} else {
				return temp;
			}
		} catch(SQLException throwables) {
			throwables.printStackTrace();
			return temp;
		}
	}
	
	public boolean addToUsers(UserDetails user) {
		//having prepared statement for insertion
		String sqlQuery = "INSERT INTO users VALUES(?,?,?,?,?,?,?)";
		try {
			PreparedStatement statement = connection.prepareStatement(sqlQuery);
			
			statement.setString(1, user.getUniqueId());
			statement.setString(2, user.getName());
			statement.setString(3, user.getUsername());
			statement.setString(4, user.getPassword());
			statement.setString(5, user.getTime());
			statement.setString(6, user.getPhoneNo());
			statement.setString(7, "");
			//Executes the prepared statement
			statement.executeUpdate();
			
			//sends the opposite of previous value to the client so that client can understand that the user has been added
			return !checkUsername(user.getUsername());
		} catch(SQLException throwables) {
			throwables.printStackTrace();
			return false;
		}
	}
	
	public ArrayList<UserDetails> searchUsers(String string) {
		
		string = string.toLowerCase(Locale.ROOT);
		string = string.replace(" ", "%");
		string = '%' + string + '%';
		
		String sqlQuery =
				"SELECT * FROM users WHERE lower(U_Name) LIKE '" + string + "' OR lower " +
						"(U_Username) LIKE '" + string + "'";
		ArrayList<UserDetails> list = new ArrayList<>();
		
		try {
			Statement statement = connection.createStatement();
			ResultSet result = statement.executeQuery(sqlQuery);
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
	
	public boolean setOtp(String number, int otp) {
		
		String sqlQuery = "UPDATE users SET U_OTP='" + otp + "' WHERE U_PhNO='" + number + "'";
		try {
			Statement statement = connection.createStatement();
			statement.executeUpdate(sqlQuery);
			return true;
		} catch(SQLException throwables) {
			throwables.printStackTrace();
			return false;
		}
		
	}
	
	//will return true if the username doesnt exist
	public boolean checkPhNO(String number) {
		
		String sqlQuery = "SELECT * FROM users WHERE U_PhNO=" + "'" + number + "'";
		try {
			Statement statement = connection.createStatement();
			
			ResultSet result = statement.executeQuery(sqlQuery);
			return !result.next();
		} catch(SQLException throwables) {
			throwables.printStackTrace();
			return false;
		}
	}

//utility--------------------------------------------------------------------------------------------------------------------------------------------
	
	public UserDetails resultToList(ResultSet resultSet) throws SQLException {
		
		String[] strArr = new String[6];
		int i = 0;
		strArr[i++] = resultSet.getString(i);
		strArr[i++] = resultSet.getString(i);
		strArr[i++] = resultSet.getString(i);
		strArr[i++] = "";
		strArr[i++] = resultSet.getString(i);
		strArr[i++] = resultSet.getString(i);
		
		return new UserDetails(strArr);
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

//todo---------------------------------------------------------------------------------------------------------------------------------------------
	
	public boolean delete(UserDetails user) {
		
		String sqlQuery =
				"DELETE FROM users WHERE U_Username='" + user.getUsername() + "' AND U_Password='" + user.getPassword() + "'";
		try {
			
			Statement statement = connection.createStatement();
			statement.executeQuery(sqlQuery);
			
			return checkUsername(user.getUsername());
		} catch(Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
}

//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//

/*
 * the structure for the user sql
 *
 * create table users(
 *       U_ID  varchar(64) PRIMARY KEY,
 *       U_Name varchar(50),
 *       U_Username varchar(32)not null unique,
 *       U_Password varchar(64) not null,
 *       U_Time varchar(32) not null,
 *       U_PhNO varchar(512)
 *       U_OTP
 * );
 * public void displayALlUsers() {
		//having prepared statement for insertion
		String sqlQuery = "SELECT * FROM users";
		DataAccess obj = new DataAccess();
		try {
			Statement statement = connection.createStatement();
			
			ResultSet result = statement.executeQuery(sqlQuery);
			
			while(result.next()) {
				String[] rowArray = rowToArray(result);
				UserDetails users = new UserDetails(rowArray);
				users.display();
				obj.addToUsers(users);
				
			}
			obj.connectionClose();
		} catch(SQLException throwables) {
			throwables.printStackTrace();
		}
	}
	* public static void main(String[] ags) {
		
		UsersDao usersDao = new UsersDao();
		usersDao.displayALlUsers();
		
		DataAccess obj = new DataAccess();
		
		obj.displayALlUsers();
	}
	*
use LIKE instead of =

 %	Represents zero or more characters	bl% finds bl, black, blue, and blob
 _	Represents a single character	h_t finds hot, hat, and hit
 []	Represents any single character within the brackets	h[oa]t finds hot and hat, but not hit
 ^	Represents any character not in the brackets	h[^oa]t finds hit, but not hot and hat
 -	Represents a range of characters	c[a-b]t finds cat and cbt

 */
	
