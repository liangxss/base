package com.yhm.wst.util;

import android.support.annotation.NonNull;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * {key1:[[k1,k2,k3][v1,v2,v3]],key2:[[k4,k5,k6][v4,v5,v6]]}
 * 结构中存在n个对象，用map存储
 * [[k1,k2,k3][v1,v2,v3][v4,v5,v6]]
 * list结构中第0个元素为key，其后为value
 *
 * 同一个接口返回数据类型不可多变
 *  eg:login接口，成功时data返回map，失败时返回String或其他类型
 *      {
 *          "error":"200",
 *          "err_msg":"成功",
 *          "data":{[userId, userName]["123", "admin"]}
 *      }
 *      正确：
 *      {
 *          "error":"201",
 *          "err_msg":"用户名或密码错误",
 *          "data":{}
 *      }
 *      错误：
 *      {
 *          "error":"201",
 *          "err_msg":"用户名或密码错误",
 *          "data":""
 *      }
 * 接口中字段只有改变类型时影响前端解析，增减不会受影响
 *
 * Created by liang_xs on 2017/11/27.
 */
public class HProseUtil {

    public static String mapToJson(Object object){
        if(object instanceof Map) {
            Map<Object, Object> map = (Map) object;
            for (Map.Entry<Object, Object> entry : map.entrySet()) {
                if(entry != null) {
                    Object v = entry.getValue();
                    if(v instanceof JSONArray) {
                        map.put(entry.getKey(), entry.getValue());
                    } else if(v instanceof JSONObject) {
                        map.put(entry.getKey(), entry.getValue());
                    } else if(v instanceof List) {
                        List l = (List) v;
                        map.put(entry.getKey(), listToJsonArray(l));
                    } else if(v instanceof HashMap) {
                        HashMap m = (HashMap) v;
                        map.put(entry.getKey(), mapToJsonObject(m));
                    } else {
                        map.put(entry.getKey(), entry.getValue().toString());
                    }
                }
            }
            return JSON.toJSONString(map);
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
    public static JSONArray listToJsonArray(List list) {
        JSONArray jsonArray = new JSONArray();
        try {
            if (list == null || list.size() < 2) { // 如果list为空则直接返回
                return jsonArray;
            }
            Object objKey = list.get(0);
            if(objKey instanceof List) {
                List key = (List) objKey;
                if(!ArrayUtil.isEmpty(key)) {
                    for (int i = 1; i < list.size(); i++) {
                        Object objValue = list.get(i);
                        if(objValue instanceof List) {
                            List value = (List) list.get(i);
                            if(value != null) {
                                JSONObject jsonObject = new JSONObject();
                                for (int j = 0; j < key.size(); j++) {
                                    Object v = value.get(j);
                                    if(v != null) {
                                        if(v instanceof List) {
                                            List l = (List) v;
                                            jsonObject.put(key.get(j).toString(), listToJsonArray(l));
                                        } else if(v instanceof Map) {
                                            HashMap m = (HashMap) v;
                                            jsonObject.put(key.get(j).toString(), mapToJsonObject(m));
                                        } else if(v instanceof String) {
                                            if (value.get(j).equals("null")) {
                                                jsonObject.put(key.get(j).toString(), "");
                                            } else {
                                                jsonObject.put(key.get(j).toString(), value.get(j));
                                            }
                                        } else {
                                            jsonObject.put(key.get(j).toString(), value.get(j));
                                        }
                                    } else {
                                        jsonObject.put(key.get(j).toString(), value.get(j));
                                    }
                                }
                                jsonArray.add(jsonObject);
                            }
                        }
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
    public static JSONObject mapToJsonObject(Map<Object, Object> map) {
        JSONObject jsonObject = new JSONObject();
        try {
            if (map == null || map.size() == 0) { // 如果list为空则直接返回
                return jsonObject;
            }
            for (Map.Entry<Object, Object> entry : map.entrySet()) {
                if(entry != null) {
                    Object v = entry.getValue();
                    if(entry.getValue() != null) {
                        if(v instanceof List) {
                            List l = (List) v;
                            jsonObject.put(entry.getKey().toString(), listToJsonArray(l));
                        } else if(v instanceof HashMap) {
                            Map m = (Map) v;
                            jsonObject.put(entry.getKey().toString(), mapToJsonObject(m));
                        } else if(v instanceof String) {
                            if (entry.getValue().equals("null")) {
                                jsonObject.put(entry.getKey().toString().toString(), "");
                            } else {
                                jsonObject.put(entry.getKey().toString(), entry.getValue());
                            }
                        } else {
                            jsonObject.put(entry.getKey().toString(), entry.getValue());
                        }
                    } else {
                        jsonObject.put(entry.getKey().toString(), entry.getValue());
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonObject;
    }
}
