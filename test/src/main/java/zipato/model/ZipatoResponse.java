package zipato.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ZipatoResponse {

	@SerializedName("success")
	@Expose
	private Boolean success;
	@SerializedName("jsessionid")
	@Expose
	private String jsessionid;
	@SerializedName("nonce")
	@Expose
	private String nonce;

	public Boolean getSuccess() {
		return success;
	}

	public void setSuccess(Boolean success) {
		this.success = success;
	}

	public String getJsessionid() {
		return jsessionid;
	}

	public void setJsessionid(String jsessionid) {
		this.jsessionid = jsessionid;
	}

	public String getNonce() {
		return nonce;
	}

	public void setNonce(String nonce) {
		this.nonce = nonce;
	}

	@Override
	public String toString() {
		return "Init [success=" + success + ", jsessionid=" + jsessionid + ", nonce=" + nonce + "]";
	}
	
	

}