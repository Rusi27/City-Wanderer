package com.example.beproject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class JsonParser {
    private HashMap<String , String> parseJsonObject(JSONObject object){
        HashMap<String , String> dataList = new HashMap<>();
        try {
            String strName = object.getString("name");
            String strLatitude = object.getJSONObject("geometry").getJSONObject("location").getString("lat");
            String strLongitude = object.getJSONObject("geometry").getJSONObject("location").getString("lng");
            dataList.put("name" , strName);
            dataList.put("lat" , strLatitude);
            dataList.put("lng" ,strLongitude);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return dataList;
    }

    private List<HashMap<String , String>> parseJsonArray(JSONArray jsonArray){
        List<HashMap<String , String>> dataList = new ArrayList<>();
        for(int i = 0 ; i < jsonArray.length() ; i++){
            try {
                HashMap<String , String> data = parseJsonObject((JSONObject) jsonArray.get(i));
                dataList.add(data);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return dataList;
    }

    List<HashMap<String , String>> parseResult(JSONObject jsonObject){
        JSONArray jsonArray = null;
        try {
            jsonArray = jsonObject.getJSONArray("results");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return parseJsonArray(jsonArray);
    }
}
