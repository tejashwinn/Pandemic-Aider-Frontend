package pandemic.aider.client.oldfx.service;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Client {
	
	public static void main(String[] arg) {
		try {
			Employee joe = new Employee(150, "Joe");
			
			System.out.println("employeeNumber= "
					+ joe.getEmployeeNumber());
			System.out.println("employeeName= "
					+ joe.getEmployeeName());
			
			Socket socketConnection = new Socket("127.0.0.1", 11111);
			
			ObjectOutputStream clientOutputStream = new
					ObjectOutputStream(socketConnection.getOutputStream());
			ObjectInputStream clientInputStream = new
					ObjectInputStream(socketConnection.getInputStream());
			
			clientOutputStream.writeObject(joe);
			
			joe = (Employee) clientInputStream.readObject();
			
			System.out.println("employeeNumber= "
					+ joe.getEmployeeNumber());
			System.out.println("employeeName= "
					+ joe.getEmployeeName());
			
			clientOutputStream.close();
			clientInputStream.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}