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
        //增加一个web来区分web查询和App查询的KEY
        String webUniqueKey = unique+"WEB";
//        System.out.println("unique:"+unique);
        PrintWriter pw = response.getWriter();
        String result = " <a class='STYLE1'>错误</a>，数据不存在!";
        if(Ehcache.getCache(webUniqueKey)==null) {
            //
            Connection conn = null;
            PreparedStatement ps = null;
            ResultSet rs = null;
            Integer queryTimes = null;
            Integer id = null;
            String query_date = null;
            String productId = null;
            String batchNo = null;
            try {
                conn = DBUtil.getConnection();
                String sql = "select id, website_query_times, product_id, website_query_date, product_batch from M_USER_QRCODE where query_match=?";
                ps = conn.prepareStatement(sql);
                String para = unique;
                ps.setString(1, para);
                rs = ps.executeQuery();
                if (rs.next())
                    id = rs.getInt("id");
                queryTimes = rs.getInt("website_query_times");
                query_date = rs.getString("website_query_date");
                productId = rs.getString("product_id");
                batchNo = rs.getString("product_batch");
                //获得用户IP
                String vistorIP = CommonService.getInstance().getRemoteUserIpAddr(request);
//            System.out.println(vistorIP);
                if (UpdateService.getInstance().updateQueryTimes(queryTimes, id, vistorIP)) {
                    //鲜果防伪溯源身份证唯一编号：{{indentityCode}}<br><span>查询次数：{{queryTimes}}
                    StringBuilder sb = new StringBuilder();
                    sb.append("<font size='1'>防伪溯源身份证唯一编号：");
                    sb.append(unique + "<br/>");
                    sb.append("查询次数：");
                    if (rs.getInt("website_query_times") == 0) {
                        sb.append("初次查询");
                    } else {
                        sb.append(String.valueOf(rs.getInt("website_query_times")));
                    }
                    sb.append("<br/>");
                    //拼接动态参数
                    sql = "select batch_params, sellArthor, update_time from m_user_product where product_id=? and relate_batch=?";
                    ps = conn.prepareStatement(sql);
                    ps.setString(1, productId);
                    ps.setString(2, batchNo);
                    rs = ps.executeQuery();
                    if (rs.next()) {
                        try {
                            String paraContainer = rs.getString("batch_params").replaceAll("\\[", "").replaceAll("\\]", "").replaceAll("\"", "");
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
                                    if(query_date!=null && !query_date.equals("null") && query_date.length()>0)
                                        sb.append("上次查询时间:" + query_date + "<br/>");
                                    else
                                        sb.append("无上次查询时间<br/>");

                                }
                            }
                        } catch (Exception e) {

                        }
                    }
                    sb.append("</font>");
                    //最终结果
//                    System.out.println(sb.toString());
                    result = sb.toString();
                    Ehcache.setCache(webUniqueKey,result);

                }

            } catch (Exception e) {
                System.out.println(e.getMessage());

            } finally {
                DBUtil.closeConnect(rs, ps, conn);
            }
        }else{
            result = Ehcache.getCache(webUniqueKey);
        }
        pw.print(result);
        pw.close();
    }


}
