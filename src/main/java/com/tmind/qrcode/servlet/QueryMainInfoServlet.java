package com.tmind.qrcode.servlet;

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

/**
 * Created by lijunying on 16/7/30.
 */
public class QueryMainInfoServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        response.setContentType("text/html;charset=gbk");

        //init
        PrintWriter pw = response.getWriter();
        //获取唯一标示码
        String key = "SYS_QUERY_STAC";
        //最终返回结果
        String result = null;
        result = Ehcache.getCache("SYS_QUERY_STAC");
        if(result == null){
            Connection conn = null;
            try{
                conn = DBUtil.getConnection();
                String sql = "select system_message from system_meta_table where id=99";
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery();
                if(rs.next()){
                    result = rs.getString("system_message");
                }

                Ehcache.setCache(key, result);
            }catch(Exception e){
                System.out.println(e.getMessage());

            }finally{
                DBUtil.closeConnect(null, null, conn);
            }
        }

        pw.print(result);
        pw.close();
    }
}
