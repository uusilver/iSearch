package com.tmind.qrcode.service;

import com.tmind.qrcode.model.UserQrCodeModel;
import com.tmind.qrcode.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Junying Li
 */
public class UpdateService {
    private static UpdateService ourInstance = new UpdateService();

    public static UpdateService getInstance() {
        return ourInstance;
    }

    private UpdateService() {
    }

    /**
     * 更新查询次数信息<br/>
     * @param currentQueryTimes
     * @param id
     * @param ipAddr
     * @return
     */
    public boolean updateQueryTimes(Integer currentQueryTimes, Integer id, String ipAddr){
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try{
            conn = DBUtil.getConnection();
            conn.setAutoCommit(false);
            String sql = "update M_USER_QRCODE set query_times=?, query_date=?, vistor_ip_addr=? where id=?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, currentQueryTimes + 1);
            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:SS");
            ps.setString(2, sdf.format(new Date()));
            ps.setString(3, ipAddr);
            ps.setInt(4, id);
            ps.executeUpdate();
            conn.commit();
            return true;
        }catch(Exception e){
            System.out.println(e.getMessage());
            try {
                conn.rollback();
            } catch (SQLException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
            return false;
        }finally{
            DBUtil.closeConnect(rs, ps, conn);
        }
    }


}
