package test;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {

//	public static final boolean CONSOLOE_LOG = true;
	static final Logger LOGGER = Logger.getLogger(Main.class.getName());

	public static void main(String[] args) throws IOException {
		LOGGER.log(Level.INFO, "Start");

		URL url = new URL("https://my.zipato.com/zipato-web/v2/attributes/d0fbf5f6-b391-48b9-8d80-141182f8d83c/value");
//		url = new URL("https://my.zipato.com/zipato-web/v2/thermostats");
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//		                                 8C4FF4266C0C61C555BCAA573202D0E0-n1.frontend3
//		                              s3~D469835C3D341A2C893B05491BF0D9E0-n1.frontend3
		String myCookie = "JSESSIONID=s3~-n1.frontend3";    // HA ide másolom az jó
//		Add the cookie to a request: Using the setRequestProperty(String name, String value); method, we will add a property named "Cookie", passing the cookie string created in the previous step as the property value.

		connection.setRequestProperty("Cookie", myCookie);

		connection.setRequestMethod("PUT");
		connection.setDoOutput(true);
		connection.setRequestProperty("Content-Type", "application/json");
		connection.setRequestProperty("Accept", "application/json");
		OutputStreamWriter osw = new OutputStreamWriter(connection.getOutputStream());
		osw.write("{\"value\":90}");
//		osw.write("{\"value\":90}");
		osw.flush();
		osw.close();
		System.err.println(connection.getResponseCode());
		System.err.println(connection.getResponseMessage());
	}

}
