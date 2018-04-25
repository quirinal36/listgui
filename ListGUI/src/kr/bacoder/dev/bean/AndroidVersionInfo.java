package kr.bacoder.dev.bean;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.json.simple.JSONObject;

public class AndroidVersionInfo {
	private String alphaBet;
	private String versionNameEng;
	private String versionNameKor;
	private double version;
	
	public AndroidVersionInfo(){
		
	}
	
	public AndroidVersionInfo(String alphaBet, String versionNameEng, 
			String versionNameKor, double version, int year) {
		this.alphaBet = alphaBet;
		this.versionNameEng = versionNameEng;
		this.versionNameKor = versionNameKor;
		this.version = version;
	}
	
	public String getAlphaBet() {
		return alphaBet;
	}
	public void setAlphaBet(String alphaBet) {
		this.alphaBet = alphaBet;
	}
	public String getVersionNameEng() {
		return versionNameEng;
	}
	public void setVersionNameEng(String versionNameEng) {
		this.versionNameEng = versionNameEng;
	}
	public String getVersionNameKor() {
		return versionNameKor;
	}
	public void setVersionNameKor(String versionNameKor) {
		this.versionNameKor = versionNameKor;
	}
	public double getVersion() {
		return version;
	}
	public void setVersion(double version) {
		this.version = version;
	}

	public static AndroidVersionInfo makeObj(String alphabet, String verEng, String verKor, double ver, int year){
		return new AndroidVersionInfo(alphabet, verEng, verKor, ver, year);
	}
	public static AndroidVersionInfo toObject(JSONObject input){
		AndroidVersionInfo result = new AndroidVersionInfo();
		result.setVersionNameEng((String)input.get("version_name"));
		result.setVersionNameKor((String)input.get("version_name_kor"));
		result.setVersion((double)input.get("version_num"));
		result.setAlphaBet((String)input.get("alphabet"));
		
		return result;
	}
	
	@Override
	public String toString() {
//		return ToStringBuilder.reflectionToString(this, 
//				ToStringStyle.SHORT_PREFIX_STYLE);
//		return this.versionNameEng;
		StringBuilder builder = new StringBuilder();
		builder.append(this.getAlphaBet()).append("::").append(this.getVersionNameEng())
				.append("(").append(this.getVersionNameKor()).append(")");
		return builder.toString();
	}
}
