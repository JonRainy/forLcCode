package com.direct.project.dto;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public class HttpResult {

    private Object data;
    private String message;
    private String resultCode;
    private Boolean result;


    public HttpResult(Boolean result, String message, Object data) {
        this.result = result;
        this.message = message;
        this.data = data;
        resultCode = "1";
    }

    public Object getData() {
        return data;
    }

    public void setData(JSONObject data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public Boolean getResult() {
        return result;
    }

    public void setResult(Boolean result) {
        this.result = result;
    }

    public static HttpResult ofSuccess(String message, Object data) {
        return new HttpResult(true, message, data);
    }



    public static HttpResult ofFail(String message) {
        return new HttpResult(false, message, null);
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
