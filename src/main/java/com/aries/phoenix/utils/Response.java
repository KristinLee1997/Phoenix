package com.aries.phoenix.utils;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;

@Data
public class Response<T> {
    private int code;
    private String type;
    private String message;
    private T data;

    public Response() {
    }

    public Response(int code, String msg) {
        this.code = code;
        this.message = msg;
    }

    public Response(int code, String type, String msg) {
        this.code = code;
        this.type = type;
        this.message = msg;
    }

    public static Response ok() {
        return new Response(0, "ok", "ok");
    }

    public static <T> Response ok(T data) {
        Response response = new Response(0, "ok", "ok");
        response.setData(data);
        return response;
    }

    public static Response success() {
        Response response = new Response();
        JSONObject res = new JSONObject();
        res.put("success", 0);
        response.setData(res);
        return response;
    }

    public static Response error(int code, String msg) {
        Response response = new Response(code, "error", msg);
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
