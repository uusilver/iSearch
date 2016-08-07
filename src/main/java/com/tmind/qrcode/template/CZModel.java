package com.tmind.qrcode.template;

/**
 * Created by lijunying on 16/8/3.
 */
public class CZModel {

    private String link;
    private String alt;

    public CZModel(String link, String alt) {
        this.link = link;
        this.alt = alt;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getAlt() {
        return alt;
    }

    public void setAlt(String alt) {
        this.alt = alt;
    }
}
