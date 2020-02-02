package test;

import static test.Main.LOGGER;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.util.List;
import java.util.logging.Level;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.google.gson.Gson;

import zipato.model.ZipatoResponse;

public class Token {

	public static String BASE_URL = "https://my.zipato.com/zipato-web/v2/";
	private static String PASSWORD ="XXX";

	public static void main(String[] args) throws Exception {

		test.HttpResponse rawResponse = httpGET("https://my.zipato.com/zipato-web/v2/user/init");
		System.out.println(rawResponse);

		Gson gson = new Gson();
		ZipatoResponse init = gson.fromJson(rawResponse.getResponse(), ZipatoResponse.class);
		String token = calculateToken(init.getNonce(), PASSWORD );

		test.HttpResponse rawResponse2 = httpGET("https://my.zipato.com/zipato-web/v2/user/login"
				+ "?username=csanad.farkas90@gmail.com"
				+ "&token=" + token,
				rawResponse.getCookies());
		System.out.println("FINAL" + rawResponse2.getResponse());
		System.out.println(init.getJsessionid());
		System.out.println(httpGET("https://my.zipato.com/zipato-web/v2/thermostats",rawResponse2.getCookies()));
		ZipatoResponse login = gson.fromJson(rawResponse2.getResponse(), ZipatoResponse.class);
		System.out.println(login.getJsessionid());
		test(login.getJsessionid());

//		test("D469835C3D341A2C893B05491BF0D9E0-n1.frontend3");
		httpPUT("https://my.zipato.com/zipato-web/v2/attributes/d0fbf5f6-b391-48b9-8d80-141182f8d83c/value",
				rawResponse2.getCookies(), "{\"value\":90}");
//		Token token = new Token();
//		token.generateToken();
	}

	public static void test(String jsessionID) throws IOException {
		LOGGER.log(Level.INFO, "Start " + jsessionID);

		URL url = new URL("https://my.zipato.com/zipato-web/v2/attributes/d0fbf5f6-b391-48b9-8d80-141182f8d83c/value");
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();

		String myCookie = "JSESSIONID=" + jsessionID;
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

	private static test.HttpResponse httpGET(String url) {
		return httpGET(url, null);
	}

	private static test.HttpResponse httpGET(String url, List<Cookie> cookies) {
		/* init client */
		HttpClient http = null;
		CookieStore httpCookieStore = new BasicCookieStore();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				httpCookieStore.addCookie(cookie);
			}
		}
		HttpClientBuilder builder = HttpClientBuilder.create().setDefaultCookieStore(httpCookieStore);
		http = builder.build();
		/* do stuff */
		HttpGet httpRequest = new HttpGet(url);
		httpRequest.setHeader("Accept", "application/json");
		httpRequest.setHeader("Content-type", "application/json");
		HttpResponse httpResponse = null;
		ResponseHandler<String> responseHandler = new BasicResponseHandler();
		String response = "";
		try {
			response = http.execute(httpRequest, responseHandler);
			System.out.println(response);
		} catch (Throwable error) {
			throw new RuntimeException(error);
		}

		/* check cookies */
		List<Cookie> responseCookies = httpCookieStore.getCookies();
		return new test.HttpResponse(response, responseCookies);
	}

	private static test.HttpResponse httpPUT(String url, List<Cookie> cookies, String json)
			throws ClientProtocolException, IOException {

		CookieStore httpCookieStore = new BasicCookieStore();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				httpCookieStore.addCookie(cookie);
			}
		}
		CloseableHttpClient httpclient = null;
		HttpClientBuilder builder = HttpClientBuilder.create().setDefaultCookieStore(httpCookieStore);
		httpclient = builder.build();

		HttpPut httpPut = new HttpPut(url);
		httpPut.setHeader("Accept", "application/json");
		httpPut.setHeader("Content-type", "application/json");
		StringEntity stringEntity = new StringEntity(json);
		httpPut.setEntity(stringEntity);

		System.out.println("Executing request " + httpPut.getRequestLine());

		// Create a custom response handler
		ResponseHandler<String> responseHandler = response -> {
			int status = response.getStatusLine().getStatusCode();
			if (status >= 200 && status < 300) {
				HttpEntity entity = response.getEntity();
				return entity != null ? EntityUtils.toString(entity) : null;
			} else {
				throw new ClientProtocolException("Unexpected response status: " + status);
			}
		};
		String responseBody = httpclient.execute(httpPut, responseHandler);
		System.out.println("----------------------------------------");
		System.out.println(responseBody);

		return new test.HttpResponse(responseBody, null);
	}

//	public String generateToken() throws Exception {
//		LOGGER.log(Level.INFO, "Token Start");
//		ZipatoResponse init = init();
//		LOGGER.log(Level.INFO, "Init nonce: " + init.getNonce());
//		String token = calculateToken(init.getNonce(), "");
//		LOGGER.log(Level.INFO, "Token: " + token);
//
//		String finalURL = BASE_URL + "user/login?username=" + URLEncoder.encode("csanad.farkas90@gmail.com", "UTF-8")
//				+ "&token=" + token;
////		finalURL = BASE_URL+"user/login?username=" + "csanad.farkas90@gmail.com" + "&token="
////				+ token;
//		System.out.println(finalURL);
//		HttpRequest httpRequest = new HttpRequest();
////		String sendGet = httpRequest.sendGet(finalURL, "JSESSIONID=" + init.getJsessionid());
////		LOGGER.log(Level.INFO, sendGet);
//		return token;
//
//	}

//	public ZipatoResponse init() throws Exception {
//		HttpRequest httpRequest = new HttpRequest();
//		Gson gson = new Gson();
////		String sendGet = httpRequest.sendGet(BASE_URL + "user/init");
//		ZipatoResponse init = gson.fromJson("", ZipatoResponse.class);
//		return init;
//
//	}

	public static String calculateToken(String nonce, String password) {
		LOGGER.log(Level.INFO, "Init nonce: " + nonce);
		String sha1Password = sha1(password);
		LOGGER.log(Level.INFO, "sha1Password: " + sha1Password);
		System.err.println(sha1Password);
		String token = sha1(nonce + sha1Password);
		return token;
	}

	public static String sha1(String value) {
		String sha1 = "";
//
		// With the java libraries
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-1");
			digest.reset();
			digest.update(value.getBytes("utf8"));
			sha1 = String.format("%040x", new BigInteger(1, digest.digest()));
		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println("The sha1 of \"" + value + "\" is:");
		System.out.println(sha1);
		System.out.println();
		return sha1;
	}

}
