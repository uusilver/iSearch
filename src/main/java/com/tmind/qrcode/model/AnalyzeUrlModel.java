package com.tmind.qrcode.model;

/**
 * Created by lijunying on 16/8/14.
 */
public class AnalyzeUrlModel {

    private int errorCode;
    private String message;

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
