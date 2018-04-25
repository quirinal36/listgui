package kr.bacoder.dev.parse;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class JsonUtil {

	/**
	 * String 형식의 데이터를 받아와서
	 * JSON 으로 파싱하는 메소드
	 * 
	 * @param data
	 * @return
	 * @throws ParseException
	 */
	public JSONObject parseToJson(String data) throws ParseException{
		JSONObject result = new JSONObject();
		JSONParser parser = new JSONParser();
		result = (JSONObject) parser.parse(data);
		return result;
	}
}
