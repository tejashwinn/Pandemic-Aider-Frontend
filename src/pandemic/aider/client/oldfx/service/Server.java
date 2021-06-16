package pandemic.aider.client.oldfx.service;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	
	public static void main(String[] arg) {
		
		Employee employee = null;
		
		try {
			
			ServerSocket socketConnection = new ServerSocket(11111);
			
			System.out.println("Server Waiting");
			
			Socket pipe = socketConnection.accept();
			
			ObjectInputStream serverInputStream = new
					ObjectInputStream(pipe.getInputStream());
			
			ObjectOutputStream serverOutputStream = new
					ObjectOutputStream(pipe.getOutputStream());
			
			employee = (Employee) serverInputStream.readObject();
			
			employee.setEmployeeNumber(256);
			employee.setEmployeeName("John");
			
			serverOutputStream.writeObject(employee);
			
			serverInputStream.close();
			serverOutputStream.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}