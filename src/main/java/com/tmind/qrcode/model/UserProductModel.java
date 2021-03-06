package com.tmind.qrcode.model;/**
 * @COPYRIGHT (C) 2016 Schenker AG
 * <p/>
 * All rights reserved
 */

/**
 * @author Vani Li
 */
public class UserProductModel {

    private String relatedBatch;
    private String batch_params;
    private String sellArthor;
    private String update_time;
    private String lottery_info;
    private String sellPrice;
    private String productAddress;
    private String level_desc;

    public String getRelatedBatch() {
        return relatedBatch;
    }

    public void setRelatedBatch(String relatedBatch) {
        this.relatedBatch = relatedBatch;
    }

    public String getBatch_params() {
        return batch_params;
    }

    public void setBatch_params(String batch_params) {
        this.batch_params = batch_params;
    }

    public String getSellArthor() {
        return sellArthor;
    }

    public void setSellArthor(String sellArthor) {
        this.sellArthor = sellArthor;
    }

    public String getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(String update_time) {
        this.update_time = update_time;
    }

    public String getLottery_info() {
        return lottery_info;
    }

    public void setLottery_info(String lottery_info) {
        this.lottery_info = lottery_info;
    }

    public String getSellPrice() {
        return sellPrice;
    }

    public void setSellPrice(String sellPrice) {
        this.sellPrice = sellPrice;
    }

    public String getProductAddress() {
        return productAddress;
    }

    public void setProductAddress(String productAddress) {
        this.productAddress = productAddress;
    }

    public String getLevel_desc() {
        return level_desc;
    }

    public void setLevel_desc(String level_desc) {
        this.level_desc = level_desc;
    }
}
