package com.tmind.qrcode.service;

import com.tmind.qrcode.model.UserQrCodeModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Junying Li
 * @Desc: ²éÑ¯·þÎñ
 *
 */
public class QueryService {

    private static QueryService ourInstance = new QueryService();

    public static QueryService getInstance() {
        return ourInstance;
    }

    private QueryService() {
    }

    public UserQrCodeModel findUserQrCodeByUniqueId(Connection conn, PreparedStatement ps, ResultSet rs, String uniqueId) throws SQLException {
        UserQrCodeModel userQrCodeModel = new UserQrCodeModel();
        String sql = "select id, user_id, query_times, product_id, query_date, product_batch, cache_flag, lottery_flag, lottery_desc, lottery_check_flag from M_USER_QRCODE where query_match=?";
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
        }
        return userQrCodeModel;
    }

}
