package com.saugat.services;

/**
 *
 * @author saugat
 */
public class ResponseMessage<T> {

    private String status;
    private String code;
    private String message;
    private T result;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    public ResponseMessage(String status, String code, String message, T result) {
        this.status = status;
        this.code = code;
        this.message = message;
        this.result = result;
    }

}
