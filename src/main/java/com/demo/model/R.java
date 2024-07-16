package com.demo.model;

public class R<T> {

    private Integer code;

    private T data;

    private String message;

    private Boolean success;


    public static R success() {
        R result = new R();
        result.setCode(200);
        result.setSuccess(true);
        return result;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public static R success(Object data) {
        R result = new R();
        result.setCode(200);
        result.setSuccess(true);
        result.setData(data);
        return result;
    }

    public static R fail(String message) {
        R result = new R();
        result.setCode(500);
        result.setSuccess(false);
        result.setMessage(message);
        return result;
    }

}

