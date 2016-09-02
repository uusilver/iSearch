package com.tmind.qrcode.service;

import com.tmind.qrcode.model.UserProductModel;
import com.tmind.qrcode.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 插入数据库的跟踪日志信息表
 * Created by lijunying on 16/9/2.
 */
public class InsertService {

    private static InsertService ourInstance = new InsertService();

    public static InsertService getInstance() {
        return ourInstance;
    }

    private InsertService() {
    }



}
