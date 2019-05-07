package com.aries.phoenix.model;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;

@Data
public class PhoenixHttpResponse<T> {
    private int code;
    private String message;
    private T data;

    public PhoenixHttpResponse() {
    }

    public PhoenixHttpResponse(int code, String msg) {
        this.code = code;
        this.message = msg;
    }

    public static PhoenixHttpResponse ok() {
        return new PhoenixHttpResponse(0, "ok");
    }

    public static <T> PhoenixHttpResponse ok(T data) {
        PhoenixHttpResponse response = new PhoenixHttpResponse(0, "ok");
        response.setData(data);
        return response;
    }

    public static PhoenixHttpResponse success() {
        PhoenixHttpResponse response = new PhoenixHttpResponse();
        JSONObject res = new JSONObject();
        res.put("success", 0);
        response.setData(res);
        return response;
    }

    public static PhoenixHttpResponse error(int code, String msg) {
        PhoenixHttpResponse response = new PhoenixHttpResponse(code, msg);
        return response;
    }

    public static JSONObject wrapData(Object obj) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("data", obj);
        return jsonObject;
    }

    public JSONObject wrapData() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("data", this.data);
        return jsonObject;
    }
}
