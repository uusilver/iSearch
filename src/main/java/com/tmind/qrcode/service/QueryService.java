package com.tmind.qrcode.service;

import com.tmind.qrcode.model.UserProductModel;
import com.tmind.qrcode.model.UserQrCodeModel;
import com.tmind.qrcode.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Junying Li
 * @Desc: 搜索产品内容
 *
 */
public class QueryService {

    private static QueryService ourInstance = new QueryService();

    public static QueryService getInstance() {
        return ourInstance;
    }

    private QueryService() {
    }

    public UserQrCodeModel findUserQrCodeByUniqueId(String uniqueId) throws SQLException {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        UserQrCodeModel userQrCodeModel = new UserQrCodeModel();
        try{
            conn = DBUtil.getConnection();
            String sql = "select id, user_id, query_times, product_id, query_date, product_batch, cache_flag, get_lottery_flag ,lottery_flag, lottery_desc, lottery_check_flag from M_USER_QRCODE where query_match=?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, uniqueId);
            rs = ps.executeQuery();
            if(rs.next()){
                userQrCodeModel.setId(rs.getInt("id"));
                userQrCodeModel.setQueryTimes(rs.getInt("query_times"));
                userQrCodeModel.setQuery_date(rs.getString("query_date"));
                userQrCodeModel.setProductId(rs.getString("product_id"));
                userQrCodeModel.setBatchNo(rs.getString("product_batch"));
                userQrCodeModel.setCacheFlag(rs.getString("cache_flag"));
                userQrCodeModel.setLottery_flag(rs.getString("lottery_flag"));
                userQrCodeModel.setLottery_desc(rs.getString("lottery_desc"));
                userQrCodeModel.setLottery_check_flag(rs.getString("lottery_check_flag"));
                userQrCodeModel.setUserId(rs.getInt("user_id"));
                userQrCodeModel.setGet_lottery_flag(rs.getString("get_lottery_flag"));
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }finally {
            DBUtil.closeConnect(rs, ps, null);
        }
        return userQrCodeModel;
    }

    public UserQrCodeModel findUserQrCodeByPass(Connection conn, String passCode) throws SQLException {
        UserQrCodeModel userQrCodeModel = new UserQrCodeModel();
        String sql = "select id, user_id, query_times, product_id, query_date, product_batch, cache_flag, lottery_flag, lottery_desc, lottery_check_flag from M_USER_QRCODE where pass_code=?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, passCode);
        ResultSet rs = ps.executeQuery();
        try{
            if(rs.next()){
                userQrCodeModel.setId(rs.getInt("id"));
                userQrCodeModel.setQueryTimes(rs.getInt("query_times"));
                userQrCodeModel.setQuery_date(rs.getString("query_date"));
                userQrCodeModel.setProductId(rs.getString("product_id"));
                userQrCodeModel.setBatchNo(rs.getString("product_batch"));
                userQrCodeModel.setCacheFlag(rs.getString("cache_flag"));
                userQrCodeModel.setLottery_flag(rs.getString("lottery_flag"));
                userQrCodeModel.setLottery_desc(rs.getString("lottery_desc"));
                userQrCodeModel.setLottery_check_flag(rs.getString("lottery_check_flag"));
                userQrCodeModel.setUserId(rs.getInt("user_id"));
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }finally {
            DBUtil.closeConnect(rs, ps, null);
        }
        return userQrCodeModel;
    }


    public UserProductModel findUserProductByParams(Connection conn, String productId, String relateBatchNo) throws SQLException {
        UserProductModel userProductModel = new UserProductModel();
        String sql = "select relate_batch, batch_params, sellArthor, update_time, lottery_info, sellPrice, productAddress, level_desc from m_user_product where product_id=? and relate_batch=?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1,productId);
        ps.setString(2,relateBatchNo);
        ResultSet rs = ps.executeQuery();
        try{
            if(rs.next()){
                userProductModel.setBatch_params(rs.getString("batch_params"));
                userProductModel.setSellArthor(rs.getString("sellArthor"));
                userProductModel.setUpdate_time(rs.getString("update_time"));
                userProductModel.setLottery_info(rs.getString("lottery_info"));
                userProductModel.setSellPrice(rs.getString("sellPrice"));
                userProductModel.setProductAddress(rs.getString("productAddress"));
                userProductModel.setLevel_desc(rs.getString("level_desc"));
                userProductModel.setRelatedBatch(rs.getString("relate_batch"));
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }finally {
            DBUtil.closeConnect(rs, ps, null);
        }
        return userProductModel;
    }



}
