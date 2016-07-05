package com.tmind.qrcode.servlet;

import com.google.gson.Gson;
import com.tmind.qrcode.model.UserProductModel;
import com.tmind.qrcode.model.UserQrCodeModel;
import com.tmind.qrcode.service.CommonService;
import com.tmind.qrcode.service.QueryService;
import com.tmind.qrcode.service.UpdateService;
import com.tmind.qrcode.util.DBUtil;
import com.tmind.qrcode.util.Ehcache;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lijunying on 16/7/5.
 */
//读取上海瓷砖页的HTML
@SuppressWarnings("ALL")
public class LoadImagieServlet  extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        response.setContentType("text/html;charset=gbk");

        //init
        PrintWriter pw = response.getWriter();
        //获取唯一标示码
        String key = request.getParameter("key");
        //最终返回结果
        String result = "<a class='STYLE1'>错误</a>，数据不存在!";

        if(Ehcache.getCache(key)==null){
            Connection conn = null;
            try{
                conn = DBUtil.getConnection();
                String sql = "select value_t from cust_tile_sh_table where key_t=?";
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setString(1, key);
                ResultSet rs = ps.executeQuery();
                if(rs.next()){
                    result = rs.getString("value_t");
                }

                Ehcache.setCache(key, result);
            }catch(Exception e){
                System.out.println(e.getMessage());

            }finally{
                DBUtil.closeConnect(null, null, conn);
            }
        }else{
            result = Ehcache.getCache(key);
        }

        //用Gson构建最后的返回内容
        pw.print(result);
        pw.close();
    }
}
