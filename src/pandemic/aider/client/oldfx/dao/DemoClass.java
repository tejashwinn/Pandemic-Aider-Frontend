//package pandemic.aider.client.oldfx.dao;
//
//import com.mysql.jdbc.Driver;
//
//import java.sql.Connection;
//import java.sql.DriverManager;
//
//public class DemoClass {
//	public static void main(String[] args) throws Exception {
//
//		String url = "jdbc:sqlserver://TEJ\\SQLEXPRESS;databaseName=pandemic_aider_db;";
//		String uname = "sa";
//		String pass = "";
//
//		try {
//			Driver d = (Driver) Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver").newInstance();
//			Connection connection = DriverManager.getConnection(url, uname, pass);
//
//			System.out.println("Connected");
//
////            String sql = "INSERT INTO USERS (u_name,u_userName,u_password,u_uniqueId)" + "VALUES('tej1','tej1','11','11')";
////
////            Statement statement = connection.createStatement();
////            int rows = statement.executeUpdate(sql);
////            if (rows > 1) {
////                System.out.println("inserted");
////            }
////            else
////            {
////                System.out.println("not");
////            }
//		} catch (Exception e) {
//			System.out.println("Sql not connected\n" + e);
//		}
//
//        /* dont add integratedSecurity=true;
//        Can't start SQL Server Browser?
//In Windows 10 : Go to Control Panel->Administrative Tools->Services, and look for the SQL Server Browser. Right-click, and select Properties From the Startup Type dropdown, change from Disabled to Automatic.19-Feb-2019
//
//
//To query database SQL Server with JDBC
//
//Firstly, You need to find out the IP of your SQL Server and Enable TCP/IP and set TCP/IP port either. To do that, do these steps:
//
//Open SQL Server Configuration Manager
//
//Choose SQL Server Network Configuration -> Protocols for -> Right click on TCP/IP -> select Enable (if already enabled then move to next step) -> Right click to TCP/IP again -> Properties -> Choose tab IP Address -> In IPAll group: Clear value of TCP Dynamic Ports and set 1433 to TCP Port -> Click OK
//
//Note: Copy one of the IPs in the Properties table to do config later
//
//Double click to SQL Server Services - > Right click to SQL Server (USER) ** -> Choose **Restart
//
//Now, you already have the IP, config server as below:
//
//<bean id="dataSource" class="org.springframework.jdbc.datasource.DriverManagerDataSource">
//        <property name="driverClassName" value="com.microsoft.sqlserver.jdbc.SQLServerDriver" />
//        <property name="url" value="jdbc:sqlserver://put.the.ip.here:1433;databaseName=<DB-Name>" />
//        <property name="username" value="sa" />
//        <property name="password" value="p@ssW0rd" />
//</bean>
//Note: Remove destroy-method="close" out of the bean*/
//
//	}
//}
