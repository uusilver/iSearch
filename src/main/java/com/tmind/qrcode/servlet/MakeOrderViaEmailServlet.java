package com.tmind.qrcode.servlet;

import com.tmind.qrcode.util.DBUtil;
import com.tmind.qrcode.util.Mail;

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

/**
 * Created by lijunying on 15/12/17.
 */
public class MakeOrderViaEmailServlet extends HttpServlet {

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        response.setContentType("text/html;charset=UTF-8");
        String numberA = request.getParameter("numberA");
        String numberB = request.getParameter("numberB");
        String receivePerson = request.getParameter("receivePerson");
        String telno = request.getParameter("telno");
        String receiveAddr = request.getParameter("receiveAddr");
        String comment = request.getParameter("comment");
        String productId =  request.getParameter("productId");

        StringBuilder sb = new StringBuilder();
        if(Integer.valueOf(numberA)>0){
            sb.append("客户:"+receivePerson);
            sb.append("订购价格为258的柚子:"+numberA+"箱,");
        }
        if(Integer.valueOf(numberB)>0){
            sb.append("价格为358柚子:"+numberB+"箱,");
        }
        sb.append("客户地址是:"+receiveAddr+",");
        sb.append("客户的电话是:"+telno+",");
        sb.append("客户备注内容:"+comment+"。");
        //把数据存入数据库
        Connection conn  = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try{
            String sql = "insert into m_factory_order (product_content, receive_person, telno, receive_address, comment, send_flag) values (?,?,?,?,?,?)";
            conn = DBUtil.getConnection();
            conn.setAutoCommit(false);
            ps =conn.prepareStatement(sql);
            ps.setString(1,sb.toString());
            ps.setString(2,receivePerson);
            ps.setString(3,telno);
            ps.setString(4,receiveAddr);
            ps.setString(5,comment);
            ps.setString(6,"Y");
            ps.execute();
            sql = "select user_id from m_user_product_meta where product_id=?";
            ps = conn.prepareStatement(sql);
            ps.setString(1,productId);
            rs = ps.executeQuery();
            Integer userId = 0;
            if(rs.next()){
                userId = rs.getInt("user_id");
            }
            sql = "select user_email, username from user where id=?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1,userId);
            rs = ps.executeQuery();
            if(rs.next()){
                System.out.println("找到厂家");
                String smtp = "smtp.163.com";
                String from = "13851483034@163.com";
                String to = rs.getString("user_email");
                String subject = "蜜柚订购信息";
                String content = sb.toString();
                String username="13851483034@163.com";
                String password="19850924";
                Mail.send(smtp, from, to, subject, content, username, password);
                System.out.println("邮件发送成功");
            }
            conn.commit();
        }catch (Exception e){
                System.out.println(e.getMessage());
            try {
                conn.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }finally {
            DBUtil.closeConnect(rs,ps, conn);
        }


    }
}
