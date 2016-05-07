package com.tmind.qrcode.model;

/**
 * @author JunyingLi
 *
 * @Desc: 用来保存用户二维码表的查询数据的model
 */
public class UserQrCodeModel {

    private int queryTimes;
    private int id;
    private int userId;
    private String query_date = null;
    private String productId = null;
    private String batchNo = null;
    private String winLottery = null;
    private String cacheFlag = null;
    private String lottery_flag = null;
    private String lottery_desc = null;
    private String lottery_check_flag = null;

    public int getQueryTimes() {
        return queryTimes;
    }

    public void setQueryTimes(int queryTimes) {
        this.queryTimes = queryTimes;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getQuery_date() {
        return query_date;
    }

    public void setQuery_date(String query_date) {
        this.query_date = query_date;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public String getWinLottery() {
        return winLottery;
    }

    public void setWinLottery(String winLottery) {
        this.winLottery = winLottery;
    }

    public String getCacheFlag() {
        return cacheFlag;
    }

    public void setCacheFlag(String cacheFlag) {
        this.cacheFlag = cacheFlag;
    }

    public String getLottery_flag() {
        return lottery_flag;
    }

    public void setLottery_flag(String lottery_flag) {
        this.lottery_flag = lottery_flag;
    }

    public String getLottery_desc() {
        return lottery_desc;
    }

    public void setLottery_desc(String lottery_desc) {
        this.lottery_desc = lottery_desc;
    }

    public String getLottery_check_flag() {
        return lottery_check_flag;
    }

    public void setLottery_check_flag(String lottery_check_flag) {
        this.lottery_check_flag = lottery_check_flag;
    }

    @Override
    public String toString() {
        return "UserQrCodeModel{" +
                "queryTimes=" + queryTimes +
                ", id=" + id +
                ", userId=" + userId +
                ", query_date='" + query_date + '\'' +
                ", productId='" + productId + '\'' +
                ", batchNo='" + batchNo + '\'' +
                ", winLottery='" + winLottery + '\'' +
                ", cacheFlag='" + cacheFlag + '\'' +
                ", lottery_flag='" + lottery_flag + '\'' +
                ", lottery_desc='" + lottery_desc + '\'' +
                ", lottery_check_flag='" + lottery_check_flag + '\'' +
                '}';
    }
}
