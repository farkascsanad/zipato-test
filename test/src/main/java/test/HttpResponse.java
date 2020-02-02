package test;

import java.util.List;

import org.apache.http.cookie.Cookie;

public class HttpResponse {

	private String response;
	private List<Cookie> cookies;
	
	
	

	public HttpResponse(String response, List<Cookie> cookies) {
		super();
		this.response = response;
		this.cookies = cookies;
	}

	@Override
	public String toString() {
		return "HttpResponse [response=" + response + ", cookies=" + cookies + "]";
	}

	public String getResponse() {
		return response;
	}

	public void setResponse(String response) {
		this.response = response;
	}

	public List<Cookie> getCookies() {
		return cookies;
	}

	public void setCookies(List<Cookie> cookies) {
		this.cookies = cookies;
	}

}
