package pandemic.aider.client.service;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Optional;

public class Network {
	
	public static void alert() {
		
		Alert alert = new Alert(Alert.AlertType.ERROR);
		alert.setTitle("Network failure");
		alert.setHeaderText("Press Ok to exit");
		alert.setContentText("You need an internet connection to use this application");
		Optional<ButtonType> result = alert.showAndWait();
		if(result.isPresent() && result.get() == ButtonType.OK) {
//			System.exit(-1);
		
		}
//		System.exit(-2);
	}
	
	public static void netIsAvailable() {
		
		try {
			final URL url = new URL("http://www.google.com");
			final URLConnection conn = url.openConnection();
			conn.connect();
			conn.getInputStream().close();
			
		} catch(MalformedURLException e) {
			alert();
			throw new RuntimeException(e);
		} catch(IOException e) {
			alert();
			
		}
	}
}
