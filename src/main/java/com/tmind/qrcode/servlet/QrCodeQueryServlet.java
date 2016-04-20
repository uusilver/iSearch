package com.tmind.qrcode.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.tmind.qrcode.util.DBUtil;
import com.tmind.qrcode.util.Ehcache;

public class QrCodeQueryServlet extends HttpServlet{
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        response.setContentType("text/html;charset=gbk");

		Map responseMap = new HashMap();

		//获取唯一标示码
        String unique = request.getParameter("uniqueKey");
//        System.out.println("unique:"+unique);
		PrintWriter pw = response.getWriter();
		//最终返回结果
		String result = "<a class='STYLE1'>错误</a>，数据不存在!";
		//TODO 未来缓存策略放开,
		//@see Ehcache.class
		if(Ehcache.getCache1(unique)==null){
//		if(true){
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
				String sql = "select id, query_times, product_id, query_date, product_batch from M_USER_QRCODE where query_match=?";
				ps = conn.prepareStatement(sql);
				String para = unique;
				ps.setString(1, para);
				rs = ps.executeQuery();
				if(rs.next())
					id = rs.getInt("id");
				queryTimes = rs.getInt("query_times");
				query_date = rs.getString("query_date");
				productId = rs.getString("product_id");
				batchNo = rs.getString("product_batch");
				//获得用户IP
				String vistorIP = getRemoteUserIpAddr(request);
				System.out.println(vistorIP);
				if(updateQueryTimes(queryTimes, id, vistorIP)){
					//鲜果防伪溯源身份证唯一编号：{{indentityCode}}<br><span>查询次数：{{queryTimes}}
					StringBuilder sb = new StringBuilder();
					sb.append("<font size='1'>防伪溯源身份证唯一编号：");
					sb.append(unique+"<br/>");
					sb.append("查询次数：");
					if(rs.getInt("query_times")==0){
						sb.append("初次查询");
					}else{
						String queryTimesFromDatabase = String.valueOf(rs.getInt("query_times"));
						sb.append(queryTimesFromDatabase);
						responseMap.put("queryTimes", queryTimesFromDatabase);
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
									if(query_date!=null && !query_date.equals("null") && query_date.length()>0)
										sb.append("上次查询时间:" + query_date + "<br/>");
									else
										sb.append("无上次查询时间<br/>");

								}
							}
						}catch(Exception e){

						}
					}
					sb.append("</font>");
					//最终结果

//					System.out.println(sb.toString());
					result = sb.toString();
					responseMap.put("queryContent",result);
					Ehcache.setCache1(unique,responseMap);
				}

			}catch(Exception e){
				System.out.println(e.getMessage());

			}finally{
				DBUtil.closeConnect(rs, ps, conn);
			}
		}else{
			responseMap = Ehcache.getCache1(unique);
		}

	//用Gson构建最后的返回内容
		Gson gson = new Gson();
		pw.print(gson.toJson(responseMap));
		pw.close();
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
