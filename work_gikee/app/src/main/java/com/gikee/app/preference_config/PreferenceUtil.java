package com.gikee.app.preference_config;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.gikee.app.beans.CollectTrandBean;
import com.gikee.app.resp.SummaryBean;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * description:存取数据类
 */

public class PreferenceUtil {

    private static SharedPreferences mSharedPreferences = null;
    private static SharedPreferences.Editor mEditor = null;

    public PreferenceUtil() {

    }

    public static void init(Context context) {
            mSharedPreferences = context.getSharedPreferences(context.getPackageName(), Context.MODE_MULTI_PROCESS);
            mEditor = mSharedPreferences.edit();


    }

    /**
     * 移除指定key
     *
     * @param key
     */
    public static void removeKey(String key) {

        mEditor.remove(key);
        mEditor.commit();
    }

    /**
     * 移除所有
     */
    public static void removeAll() {
        mEditor.clear();
        mEditor.commit();
    }

    /**
     * 保存字符串
     *
     * @param key
     * @param value
     */
    public static void commitString(String key, String value) {
        mEditor.putString(key, value);
        mEditor.commit();
    }

    /**
     * 获取字符串
     *
     * @param key
     * @param defaultValue
     * @return
     */
    public static String getString(String key, String defaultValue) {
        return mSharedPreferences.getString(key, defaultValue) != null ? mSharedPreferences.getString(key, defaultValue) : "";
    }

    /**
     * 保存Int
     *
     * @param key
     * @param value
     */
    public static void commitInt(String key, int value) {
        mEditor.putInt(key, value);
        mEditor.commit();
    }

    public static int getInt(String key, int defaultValue) {
        return mSharedPreferences.getInt(key, defaultValue);
    }

    public static void commitLong(String key, long value) {
        mEditor.putLong(key, value);
        mEditor.commit();
    }

    public static long getLong(String key, long defaultValue) {
        return mSharedPreferences.getLong(key, defaultValue);
    }

    public static void commitBoolean(String key, boolean value) {

        mEditor.putBoolean(key, value);
        mEditor.commit();
    }

    public static Boolean getBoolean(String key, boolean defaultValue) {
        return mSharedPreferences.getBoolean(key, defaultValue);
    }



    /**
     * 保存List
     * @param tag
     * @param datalist
     */
    public static void setDataList(String tag, List<CollectTrandBean> datalist) {

        if (null == datalist || datalist.size() <= 0){
            mEditor.remove(tag);
            mEditor.apply();
            return;
        }


        Gson gson = new Gson();
        //转换成json数据，再保存
        String strJson = gson.toJson(datalist);
        mEditor.clear();
        mEditor.putString(tag, strJson);
        mEditor.apply();

    }

    /**
     * 获取List
     * @param tag
     * @return
     */
    public static  List<CollectTrandBean> getDataList(String tag) {
        List<CollectTrandBean> datalist=new ArrayList<>();
        String strJson = mSharedPreferences.getString(tag, null);
        if (null == strJson) {
            return datalist;
        }
        Gson gson = new Gson();
        datalist = gson.fromJson(strJson, new TypeToken<List<CollectTrandBean>>() {
        }.getType());
        return datalist;
    }



    /**
     * 保存List
     * @param tag
     * @param datalist
     */
    public static void setStringList(String tag, List<String> datalist) {
        mEditor.remove(tag);
        if (null == datalist || datalist.size() <= 0){
            mEditor.commit();
            return;
        }


        Gson gson = new Gson();
        //转换成json数据，再保存
        String strJson = gson.toJson(datalist);
        mEditor.clear();
        mEditor.putString(tag, strJson);
        mEditor.commit();

    }

    /**
     * 获取List
     * @param tag
     * @return
     */
    public static  List<String> getStringList(String tag) {
        List<String> datalist=new ArrayList<>();
        String strJson = mSharedPreferences.getString(tag, null);
        if (null == strJson) {
            return datalist;
        }
        Gson gson = new Gson();
        datalist = gson.fromJson(strJson, new TypeToken<List<String>>() {
        }.getType());
        return datalist;
    }











    /**
     * 用于保存集合
     *
     * @param key  key
     * @param list 集合数据
     * @return 保存结果
     */
    public static <T> boolean putListData(String key, List<T> list) {
        boolean result=false;
        if(list.size()!=0){
        String type = list.get(0).getClass().getSimpleName();
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        JsonArray array = new JsonArray();
        try {
            switch (type) {
                case "Boolean":
                    for (int i = 0; i < list.size(); i++) {
                        array.add((Boolean) list.get(i));
                    }
                    break;
                case "Long":
                    for (int i = 0; i < list.size(); i++) {
                        array.add((Long) list.get(i));
                    }
                    break;
                case "Float":
                    for (int i = 0; i < list.size(); i++) {
                        array.add((Float) list.get(i));
                    }
                    break;
                case "String":
                    for (int i = 0; i < list.size(); i++) {
                        array.add((String) list.get(i));
                    }
                    break;
                case "Integer":
                    for (int i = 0; i < list.size(); i++) {
                        array.add((Integer) list.get(i));
                    }
                    break;
                default:
                    Gson gson = new Gson();
                    for (int i = 0; i < list.size(); i++) {
                        JsonElement obj = gson.toJsonTree(list.get(i));
                        array.add(obj);
                    }
                    break;
            }
            editor.putString(key, array.toString());
            result = true;
        } catch (Exception e) {
            result = false;
            e.printStackTrace();
        }
        editor.apply();
        }
        return result;
    }


    /**
     * 获取保存的List
     *
     * @param key key
     * @return 对应的Lis集合
     */
    public static <T> List<T> getListData(String key, Class<T> cls) {
        List<T> list = new ArrayList<>();
        String json = mSharedPreferences.getString(key, "");
        if (!json.equals("") && json.length() > 0) {
            Gson gson = new Gson();
            JsonArray array = new JsonParser().parse(json).getAsJsonArray();
            for (JsonElement elem : array) {
                list.add(gson.fromJson(elem, cls));
            }
        }
        return list;
    }


    /**
     * 用于保存集合
     *
     * @param key key
     * @param map map数据
     * @return 保存结果
     */
    public static <K, V> boolean putHashMapData(String key, Map<K, V> map) {
        boolean result;
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        try {
            Gson gson = new Gson();
            String json = gson.toJson(map);
            editor.putString(key, json);
            result = true;
        } catch (Exception e) {
            result = false;
            e.printStackTrace();
        }
        editor.apply();
        return result;
    }

    /**
     * 用于保存集合
     *
     * @param key key
     * @return HashMap
     */
    public static <V> HashMap<String, V> getHashMapData(String key, Class<V> clsV) {
        String json = mSharedPreferences.getString(key, "");
        HashMap<String, V> map = new HashMap<>();
        Gson gson = new Gson();
        JsonObject obj = new JsonParser().parse(json).getAsJsonObject();
        Set<Map.Entry<String, JsonElement>> entrySet = obj.entrySet();
        for (Map.Entry<String, JsonElement> entry : entrySet) {
            String entryKey = entry.getKey();
            JsonObject value = (JsonObject) entry.getValue();
            map.put(entryKey, gson.fromJson(value, clsV));
        }
        Log.e("SharedPreferencesUtil", obj.toString());
        return map;
    }






}
