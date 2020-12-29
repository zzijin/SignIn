package com.example.administrator.myapp.client;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

/**
 * 合成Json以便于发送
 */
public class JsonByUTF8 {
        JSONObject json;

        public JsonByUTF8(){
                json=new JSONObject();
        }

        public JsonByUTF8(byte[] message){
                try {
                        String s=new String(message,0,message.length,"utf-8");
                        json=new JSONObject(s);
                } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                } catch (JSONException e) {
                        e.printStackTrace();
                }
        }

        public JSONObject getJson() {
                return json;
        }

        /**
         * 获取消息包
         * @return
         */
        public byte[] getMessage() {
                try {
                        return json.toString().getBytes("utf-8");
                } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                }
                return null;
        }

        /**
         * 向消息包中放入数据
         * @param DataName
         * @param Data
         * @return
         */
        public boolean putData(String DataName, String Data){
                try {
                        json.put(DataName,Data);
                        return true;
                } catch (JSONException e) {
                        e.printStackTrace();
                }
                return false;
        }
        public boolean putData(String DataName, int Data){
                try {
                        json.put(DataName,Data);
                        return true;
                } catch (JSONException e) {
                        e.printStackTrace();
                }
                return false;
        }
        public boolean putData(String DataName, long Data){
                try {
                        json.put(DataName,Data);
                        return true;
                } catch (JSONException e) {
                        e.printStackTrace();
                }
                return false;
        }
        public boolean putData(String DataName, Object Data){
                try {
                        json.put(DataName,Data);
                        return true;
                } catch (JSONException e) {
                        e.printStackTrace();
                }
                return false;
        }
        public boolean putData(String DataName, double Data){
                try {
                        json.put(DataName,Data);
                        return true;
                } catch (JSONException e) {
                        e.printStackTrace();
                }
                return false;
        }
        public boolean putData(String DataName, boolean Data){
                try {
                        json.put(DataName,Data);
                        return true;
                } catch (JSONException e) {
                        e.printStackTrace();
                }
                return false;
        }
}
