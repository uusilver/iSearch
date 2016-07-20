package com.tmind.qrcode.model;

/**
 * Created by lijunying on 16/7/17.
 */
public class UploadResultModel {

    private String status;
    private String url;

    public UploadResultModel(String status, String url) {
        this.status = status;
        this.url = url;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
