package com.tmind.qrcode.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tmind.qrcode.util.DBUtil;

public class QrCodeQueryServlet extends HttpServlet{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)  
            throws IOException, ServletException {  
        response.setContentType("text/html;charset=gbk");  
        String queryId = request.getParameter("queryId");  
        String unique = request.getParameter("uniqueKey");  
        System.out.println("queryId:"+queryId);
        System.out.println("unique:"+unique);
        //
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Integer queryTimes = null;
        Integer id = null;
        try{
        	conn = DBUtil.getConnection();
        	String sql = "select id, query_times, product_id, product_batch from M_USER_QRCODE where query_match=?";
        	ps = conn.prepareStatement(sql);
        	String para = "q="+queryId+"&u="+unique;
        	ps.setString(1, para);
        	rs = ps.executeQuery();
        	if(rs.next())
        		id = rs.getInt("id");
        		queryTimes = rs.getInt("query_times");
	        	System.out.println(rs.getString("product_id"));
	        	System.out.println(rs.getString("product_batch"));
	        	//获得用户IP
	        	String vistorIP = getRemoteUserIpAddr(request);
	        	System.out.println(vistorIP);
	        	if(updateQueryTimes(queryTimes, id, vistorIP)){
	            	PrintWriter pw = response.getWriter();   
	                pw.print(String.valueOf(queryTimes));  
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
	        String sql = "update M_USER_QRCODE set query_times=?, query_date=?, vistor_ip_addr=? where id=?";
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
