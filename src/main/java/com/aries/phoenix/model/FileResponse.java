package com.aries.phoenix.model;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;

@Data
public class FileResponse<T> {
    private int code;
    private String type;
    private String message;
    private T data;

    public FileResponse() {
    }

    public FileResponse(int code, String msg) {
        this.code = code;
        this.message = msg;
    }

    public FileResponse(int code, String type, String msg) {
        this.code = code;
        this.type = type;
        this.message = msg;
    }

    public static FileResponse ok() {
        return new FileResponse(0, "ok", "ok");
    }

    public static <T> FileResponse ok(T data) {
        FileResponse response = new FileResponse(0, "ok", "ok");
        response.setData(data);
        return response;
    }

    public static FileResponse success() {
        FileResponse response = new FileResponse();
        JSONObject res = new JSONObject();
        res.put("success", 0);
        response.setData(res);
        return response;
    }

    public static FileResponse error(int code, String msg) {
        FileResponse response = new FileResponse(code, "error", msg);
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
