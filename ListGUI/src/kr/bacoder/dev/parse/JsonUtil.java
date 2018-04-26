package kr.bacoder.dev.parse;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import kr.bacoder.dev.bean.AndroidVersionInfo;

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
		JSONParser parser = new JSONParser();
		
		JSONObject dataJSON = new JSONObject();
		
		dataJSON = (JSONObject) parser.parse(data);
		return dataJSON;
	}
	
	public HashMap<String, AndroidVersionInfo> transferToHashMap(JSONObject json){
		HashMap<String, AndroidVersionInfo> map = new HashMap<>();
		JSONArray array = (JSONArray) json.get("list");
		for(int i=0; i<array.size(); i++){
			AndroidVersionInfo item = AndroidVersionInfo.toObject((JSONObject)array.get(i));
			map.put(item.getAlphaBet(), item);
		}
		return map;
	}
	/**
	 * JSONArray 형태로 저장된 배열을
	 * ArrayList 에 저장한다.
	 * 
	 * @param json
	 * @return
	 */
	public ArrayList<AndroidVersionInfo> transferToArrayList(JSONObject json){
		ArrayList<AndroidVersionInfo> result = new ArrayList<>();
		JSONArray array = (JSONArray) json.get("list");
		for(int i=0; i<array.size(); i++){
			AndroidVersionInfo item = AndroidVersionInfo.toObject((JSONObject)array.get(i));
			result.add(item);
		}
		return result;
	}
}
