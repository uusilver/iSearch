package com.tmind.qrcode.servlet;

import com.tmind.qrcode.service.CommonService;
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

/**
 * Created by lijunying on 16/10/21.
 */
public class HotTraceServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        response.setContentType("text/html;charset=gbk");
        String traceInfo = request.getParameter("t");
                Connection conn = null;
            PreparedStatement ps = null;

            try {
                conn = DBUtil.getConnection();
                String sql = "insert into hot_trace_user_click_on_page (trace_info) values (?)";
                ps = conn.prepareStatement(sql);
                ps.setString(1, traceInfo);
                ps.execute();

            } catch (Exception e) {
                System.out.println(e.getMessage());

            } finally {
                DBUtil.closeConnect(null, ps, conn);
            }

    }
}
