package com.yhm.wst.util;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.alibaba.fastjson.JSONArray;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/11/27.
 */
public class HProseArrayToJson {

    public static String mapToJson(Object object){
        if(object instanceof HashMap) {
            Gson gson = new Gson();
            HashMap<Object, Object> map = (HashMap) object;
            for (Map.Entry<Object, Object> entry : map.entrySet()) {
                if(entry.getValue()== null || TextUtils.isEmpty(entry.getValue().toString())) {
                    map.put(entry.getKey().toString(), "");
                } else {
                    Object v = entry.getValue();
                    if(v instanceof JsonArray) {
                        map.put(entry.getKey(), entry.getValue());
                    } else if(v instanceof JsonObject) {
                        map.put(entry.getKey(), entry.getValue());
                    } else if(v instanceof List) {
                        List l = (List) v;
                        if(!ArrayUtil.isEmpty(l)) {
                            Object obj = l.get(0);
                            if(obj instanceof List) {
                                List list1 = (List) obj;
                                Object obj1 = list1.get(0);
                                if(obj1 instanceof List) {
                                    HashMap map1 = new HashMap();
                                    int key = 0;
                                    for(int i = 0; i < l.size(); i++) {
                                        key++;
                                        map1.put("result" + String.valueOf(key), getJsonElements((ArrayList) l.get(i)));
                                    }
                                    map.put(entry.getKey(), map1);
                                } else {
                                    map.put(entry.getKey(), HProseArrayToJson.getJsonElements((ArrayList) l));
                                }
                            } else if(v instanceof HashMap) {
                                HashMap m = (HashMap) v;
                                map.put(entry.getKey(), HProseArrayToJson.getJsonElements(m));
                            } else {
                                map.put(entry.getKey(), entry.getValue().toString());
                            }
                        }
                    } else if(v instanceof HashMap) {
                        HashMap m = (HashMap) v;
                        map.put(entry.getKey(), HProseArrayToJson.getJsonElements(m));
                    } else {
                        map.put(entry.getKey(), entry.getValue().toString());
                    }
                }
            }
            return gson.toJson(map);
        } else {
            return object.toString();
        }
    }
    /**
     * 二维数组的list转换成jsonArray
     *
     * @param list
     * @return
     */
    @NonNull
    public static JsonArray getJsonElements(ArrayList list) {
        JsonArray jsonArray = new JsonArray();
        try {
            if (list == null || list.size() == 0) { // 如果list为空则直接返回
                return jsonArray;
            }
            if (list.size() == 1) { // 如果list只有1个子元素，则把key添加value为""
                JsonObject obj = new JsonObject();
                if(list.get(0) instanceof ArrayList) {
                    ArrayList key = (ArrayList) list.get(0);
                    for (int i = 0; i < key.size(); i++) {
                        obj.addProperty(key.get(i).toString(), "");
                    }
                    jsonArray.add(obj);
                }
                return jsonArray;
            }
            Object obj = list.get(0);
            if(obj instanceof ArrayList) {
                ArrayList arrayList = (ArrayList) obj;
                if(!ArrayUtil.isEmpty(arrayList)) {
                    Object obj1 = arrayList.get(0);
                    if(obj1 instanceof String) {
                        for (int i = 1; i < list.size(); i++) {
                            ArrayList value = (ArrayList) list.get(i);
                            JsonObject jsonObject = new JsonObject();
                            for (int j = 0; j < arrayList.size(); j++) {
                                if (value.get(j) == null || TextUtils.isEmpty(value.get(j).toString())) {
                                    jsonObject.addProperty(arrayList.get(j).toString(), "");
                                } else {
                                    Object v = value.get(j);
                                    if(v instanceof List) {
                                        List l = (List) v;
                                        jsonObject.addProperty(arrayList.get(j).toString(), getJsonElements((ArrayList) l).toString());
                                    } else {
                                        jsonObject.addProperty(arrayList.get(j).toString(), value.get(j).toString());
                                    }
                                }
                            }
                            jsonArray.add(jsonObject);
                        }
                    } else if(obj1 instanceof ArrayList) {
//                    jsonArray.add(getJsonElements(arrayList));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonArray;
    }


    /**
     *
     * @param map
     * @return
     */
    @NonNull
    public static JsonObject getJsonElements(HashMap<Object, Object> map) {
        JsonObject jsonObject = new JsonObject();
        try {
            if (map == null || map.size() == 0) { // 如果list为空则直接返回
                return jsonObject;
            }
            for (Map.Entry<Object, Object> entry : map.entrySet()) {
//                System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
                if(entry.getValue()== null || TextUtils.isEmpty(entry.getValue().toString())) {
                    jsonObject.addProperty(entry.getKey().toString(), "");
                } else {
                    Object v = entry.getValue();
                    if(v instanceof List) {
                        List l = (List) v;
                        jsonObject.addProperty(entry.getKey().toString(), getJsonElements((ArrayList) l).toString());
                    } else if(v instanceof HashMap) {
                        HashMap m = (HashMap) v;
                        jsonObject.addProperty(entry.getKey().toString(), getJsonElements(m).toString());
                    } else {
                        jsonObject.addProperty(entry.getKey().toString(), entry.getValue().toString());
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonObject;
    }


    /**
     * 二维数组的jsonArray转换为JsonA
     *
     * @return
     */
    public static JsonArray convertJsonArray(JsonArray jsonArray) {
        JsonArray result = new JsonArray();
        JsonArray keyJson = (JsonArray) jsonArray.get(0);
        for (int j = 1; j < jsonArray.size(); j++) {
            JsonArray valuesJson = (JsonArray) jsonArray.get(j);
            JsonObject jsonObject = new JsonObject();
            for (int i = 0; i < keyJson.size(); i++) {
                if (TextUtils.isEmpty(valuesJson.get(i).toString())) {
                    jsonObject.addProperty(keyJson.get(i).toString(), "");
                } else {
                    jsonObject.addProperty(keyJson.get(i).toString(), valuesJson.get(i).toString());
                }
            }
            result.add(jsonObject);
        }
        return result;
    }

    /**
     * 二维数组的jsonArray转换为list
     *
     * @return
     */
    public static List<Map<String, Object>> jsonArrayToList(JSONArray jsonArray) {
        ArrayList<Map<String, Object>> reList = new ArrayList<Map<String, Object>>();
        JSONArray keyJson = jsonArray.getJSONArray(0);
        for (int j = 1; j < jsonArray.size(); j++) {
            JSONArray valuesJson = jsonArray.getJSONArray(j);
            Map<String, Object> param = new HashMap<String, Object>();
            for (int i = 0; i < keyJson.size(); i++) {
                if (valuesJson.get(i) == null || TextUtils.isEmpty(valuesJson.get(i).toString())) {
                    param.put(keyJson.get(i).toString(), "");
                } else {
                    param.put(keyJson.get(i).toString(), valuesJson.get(i));
                }
            }
            reList.add(param);
        }
        return reList;
    }
}
