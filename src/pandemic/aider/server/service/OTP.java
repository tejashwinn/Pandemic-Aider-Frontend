package pandemic.aider.server.service;

import pandemic.aider.server.dao.UsersDao;

import javax.net.ssl.HttpsURLConnection;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Random;

public class OTP {
	public static String send(String number) throws MalformedURLException {
		
		String apiKey = "nmkcBMQWvzxLbCZl91DgJEd4eH7NRusO6pyqGPf3woUiT8IjXaiS7mJV9FL3Zc0TQ2r6KHENRMqgUBWy";
		
		Random random = new Random();
		int otp = random.nextInt(999999);
		
		String myUrl =
				"https://www.fast2sms.com/dev/bulkV2?authorization=" + apiKey +
						"&variables_values=" + otp + "&route=otp" + "&numbers=" + number;
		
		URL url = new URL(myUrl);
		
		HttpsURLConnection con;
		try {
			con = (HttpsURLConnection) url.openConnection();
			con.setRequestMethod("GET");
			con.setRequestProperty("User-Agent", "Mozilla/5.0");
			con.setRequestProperty("cache-control", "no-cache");
			int code = con.getResponseCode();
			
			UsersDao usersDao = new UsersDao();
			
			if(usersDao.setOtp(number, otp)) {
				return String.valueOf(otp);
			} else {
				return null;
			}
			
		} catch(IOException e) {
			e.printStackTrace();
			return null;
		}
		
	}
	
}
