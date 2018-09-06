package kr.bacoder.dev.bean;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.json.simple.JSONObject;

public class AndroidVersionInfo {
	public static final String version_num = "version_num";
	public static final String year_key = "year";
	public static final String version_name = "version_name";
	public static final String version_name_kor = "version_name_kor";
	public static final String alphabet = "alphabet";
	
	private String alphaBet;
	private String versionNameEng;
	private String versionNameKor;
	private double version;
	private int year;
	
	public AndroidVersionInfo(){
		
	}
	
	public AndroidVersionInfo(String alphaBet, String versionNameEng, 
			String versionNameKor, double version, int year) {
		this.alphaBet = alphaBet;
		this.versionNameEng = versionNameEng;
		this.versionNameKor = versionNameKor;
		this.version = version;
		this.year = year;
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
		return "alphabet: " + this.alphaBet
				+"\n versionName : " + this.versionNameEng
				+"\n versionNameKor : " + this.versionNameKor
				+"\n version: " + this.version
				+"\n year: " + this.year;
	}
	
	
	
//	@Override
//	public String toString() {
//		StringBuilder builder = new StringBuilder();
//		builder.append(this.getAlphaBet()).append("::").append(this.getVersionNameEng())
//				.append("(").append(this.getVersionNameKor()).append(")");
//		return builder.toString();
//	}
}
