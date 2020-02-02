package test;

public class HttpRequest {

//	OkHttpClient httpClient = new OkHttpClient();
//
//	public String sendGet(String url) throws Exception {
//		Request request = new Request.Builder().url(url)// .addHeader("custom-key", "mkyong") // add request headers
//				// .addHeader("User-Agent", "OkHttp Bot")
//				.build();
//
//		try (Response response = httpClient.newCall(request).execute()) {
//
//			if (!response.isSuccessful())
//				throw new IOException("Unexpected code " + response);
//
//			// Get response body
//			String responseMessage = response.body().string();
//			System.out.println(responseMessage);
//			return responseMessage;
//		}
//
//	}
//	
//	public String sendGet(String url, String cookies) throws Exception {
//		Request request = new Request.Builder()
//				 .addHeader("Cookie", cookies)
//				.url(url)// .addHeader("custom-key", "mkyong") // add request headers
//				// .addHeader("User-Agent", "OkHttp Bot")
//				.build();
//
//		try (Response response = httpClient.newCall(request).execute()) {
//
//			if (!response.isSuccessful())
//				throw new IOException("Unexpected code " + response);
//
//			// Get response body
//			String responseMessage = response.body().string();
//			System.out.println(responseMessage);
//			return responseMessage;
//		}
//
//	}
//
//	public String sendPost() throws Exception {
//
//		// form parameters
//		RequestBody formBody = new FormBody.Builder().add("username", "abc").add("password", "123")
//				.add("custom", "secret").build();
//
//		Request request = new Request.Builder().url("https://httpbin.org/post").addHeader("User-Agent", "OkHttp Bot")
//				.post(formBody).build();
//
//		try (Response response = httpClient.newCall(request).execute()) {
//
//			if (!response.isSuccessful())
//				throw new IOException("Unexpected code " + response);
//
//			// Get response body
//			if (Main.CONSOLOE_LOG)
//				System.out.println(response.body().string());
//			return response.body().string();
//		}
//
//	}

}
