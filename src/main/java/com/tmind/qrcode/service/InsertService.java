package com.tmind.qrcode.service;

import com.tmind.qrcode.model.UserProductModel;
import com.tmind.qrcode.util.DBUtil;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 插入数据库的跟踪日志信息表
 * Created by lijunying on 16/9/2.
 */
public class InsertService {

    private static Logger log = Logger.getLogger(InsertService.class);

    private static InsertService ourInstance = new InsertService();

    public static InsertService getInstance() {
        return ourInstance;
    }

    private InsertService() {
    }


    public void insertIntoLog(String result, String uniqueCode, String fromUserName){
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "insert into 315kc_log_trace (operation, opt_desc, remarks, open_id) values (?,?,?,?)";
            ps = conn.prepareStatement(sql);
            ps.setString(1, "江苏华粮集团微信公共号抽奖活动日志信息");
            ps.setString(2, result);
            ps.setString(3, uniqueCode);
            ps.setString(4, fromUserName);
            ps.execute();

        }catch (Exception e){
            log.error(e.getMessage());
        }finally {
            DBUtil.closeConnect(null,ps,conn);
        }
    }
}
