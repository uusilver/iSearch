package com.tmind.qrcode.servlet;

import com.tmind.qrcode.util.DBUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by lijunying on 16/1/17.
 */
public class WebQueryServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        response.setContentType("text/html;charset=gbk");
        String unique = request.getParameter("uniqueKey");
        System.out.println("unique:"+unique);
        //
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Integer queryTimes = null;
        Integer id = null;
        String query_date = null;
        String productId = null;
        String batchNo = null;
        try{
            conn = DBUtil.getConnection();
            String sql = "select id, website_query_times, product_id, website_query_date, product_batch from M_USER_QRCODE where query_match=?";
            ps = conn.prepareStatement(sql);
            String para = unique;
            ps.setString(1, para);
            rs = ps.executeQuery();
            if(rs.next())
                id = rs.getInt("id");
            queryTimes = rs.getInt("website_query_times");
            query_date = rs.getString("website_query_date");
            productId = rs.getString("product_id");
            batchNo = rs.getString("product_batch");
            //获得用户IP
            String vistorIP = getRemoteUserIpAddr(request);
            System.out.println(vistorIP);
            if(updateQueryTimes(queryTimes, id, vistorIP)){
                PrintWriter pw = response.getWriter();
                //鲜果防伪溯源身份证唯一编号：{{indentityCode}}<br><span>查询次数：{{queryTimes}}
                StringBuilder sb = new StringBuilder();
                sb.append("<font size='1'>防伪溯源身份证唯一编号：");
                sb.append(unique+"<br/>");
                sb.append("查询次数：");
                if(rs.getInt("website_query_times")==0){
                    sb.append("初次查询");
                }else{
                    sb.append(String.valueOf(rs.getInt("website_query_times")));
                }
                sb.append("<br/>");
                //拼接动态参数
                sql = "select batch_params, sellArthor, update_time from m_user_product where product_id=? and relate_batch=?";
                ps = conn.prepareStatement(sql);
                ps.setString(1,productId);
                ps.setString(2,batchNo);
                rs = ps.executeQuery();
                if(rs.next()){
                    try {
                        String paraContainer = rs.getString("batch_params").replaceAll("\\[","").replaceAll("\\]", "").replaceAll("\"","");
                        String paramsPool[] = paraContainer.split(",");
                        for (String s : paramsPool) {
                            //添加更多标签的时候这里需要修改
                            //TODO 改成从数据库动态查询
                            if (s.equals("ud")) {
                                sb.append("产品生产时间:" + rs.getString("update_time") + "<br/>");
                            }
                            if (s.equals("sl")) {
                                sb.append("产品指定经销商:" + rs.getString("sellArthor") + "<br/>");
                            }
                            if (s.equals("lqd")) {
                                sb.append("上次查询时间:" + query_date + "<br/>");

                            }
                        }
                    }catch(Exception e){

                    }
                }
                sb.append("</font>");
                //最终结果
                System.out.println(sb.toString());
                pw.print(sb.toString());
                pw.close();
            }

        }catch(Exception e){
            System.out.println(e.getMessage());

        }finally{
            DBUtil.closeConnect(rs, ps, conn);
        }

    }

    private boolean updateQueryTimes(Integer currentQueryTimes, Integer id, String ipAddr){
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try{
            conn = DBUtil.getConnection();
            conn.setAutoCommit(false);
            String sql = "update M_USER_QRCODE set website_query_times=?, website_query_date=?, vistor_ip_addr=? where id=?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, currentQueryTimes+1);
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

    private String getRemoteUserIpAddr(HttpServletRequest request){
        String remoteAddr = request.getRemoteAddr();
        String forwarded = request.getHeader("X-Forwarded-For");
        String realIp = request.getHeader("X-Real-IP");

        String ip = null;
        if (realIp == null) {
            if (forwarded == null) {
                ip = remoteAddr;
            } else {
                ip = remoteAddr + "/" + forwarded.split(",")[0];
            }
        } else {
            if (realIp.equals(forwarded)) {
                ip = realIp;
            } else {
                if(forwarded != null){
                    forwarded = forwarded.split(",")[0];
                }
                ip = realIp + "/" + forwarded;
            }
        }
        return ip;
    }
}
