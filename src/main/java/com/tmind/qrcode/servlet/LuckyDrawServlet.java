package com.tmind.qrcode.servlet;

import com.tmind.qrcode.util.DBUtil;

import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 * 抽奖程序（可设置奖项和中奖概率，加摘要验证）
 */
public class LuckyDrawServlet extends HttpServlet {

	//用于存放奖项设置信息
	private static HashMap<String, Object[][]> prizesMap = new HashMap<String, Object[][]>();

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String customerName = request.getParameter("cust");
		String requestType = request.getParameter("type");

		if(customerName==null || customerName.equals("") || customerName==null || customerName.equals("")){
			throw new ServletException("customerName or requestType 不能为空！");
		}else{
			String responseString = "";
			if("init".equals(requestType)){
				Object[][] prizes = initPrizes(customerName);
				String prizesNameString = "";
				if(prizes!=null && prizes.length>0){
					for(int i=1;i<prizes.length; i++){
						prizesNameString += prizes[i][1] + ",";
					}
				}
				prizesNameString = prizesNameString.length()>1?prizesNameString.substring(0,prizesNameString.length()-1):"";
				responseString = "{\"nameArr\":\"" + prizesNameString+"\"}";
				System.out.println("商家:" + customerName + "\t，奖项:" + prizesNameString);
			}else if("spin".equals(requestType)){
				Object[] award = getAward(customerName, request.getSession().getId());//抽奖后返回奖品名称和旋转角度
				if(award == null || award.length < 2){
					throw new ServletException("抽奖出错了！");
				}else{
					System.out.println("转动角度:" + award[1] + "\t提示信息:" + award[0]);
					responseString =  "{\"angle\":\""+award[1]+"\",\"msg\":\""+award[0]+"\"}";
				}
			}
			response.setContentType("text/html;charset=UTF-8");
			response.getWriter().write(responseString);
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	/**
	 * 从数据获取奖项信息
	 * {0,1,0}				//抽奖信息id
	 * {0,"一等奖",3},
	 * {1,"谢谢参与",231},	//未中奖次数均匀分布:(1000-3-5-10-30)/4=231
	 * {2,"二等奖",5},
	 * {3,"祝您好运",231},
	 * {4,"三等奖",10},
	 * {5,"谢谢参与",231},
	 * {6,"纪念奖",30},
	 * {7,"祝您好运",232}, //总数出去前面所有分配出去的次数，余下的次数都放在这里
	 */
	private Object[][] initPrizes(String customerName){
		Object[][] prizes = null;

		if(prizesMap.containsKey(customerName)){
			prizes = prizesMap.get(customerName);
		}else{
			Connection conn = null;
			PreparedStatement ps = null;
			ResultSet rs = null;
			try {
				conn = DBUtil.getConnection();
				String sql = "SELECT id, totals,item1,item1_count,item2,item2_count,item3,item3_count,item4,item4_count,item5,item5_count,item6,item6_count FROM m_luckydraw where cust_id=? and validate_dttm>SYSDATE() limit 1";
				ps = conn.prepareStatement(sql);
				ps.setString(1, customerName);
				rs = ps.executeQuery();
				if (rs.next()){
					Object[][] curPrizes = new Object[7][2];
					//存放抽奖id
					curPrizes[0] = new Object[]{0, rs.getInt("id"),0};
					//余下为奖项数据
					int totals = rs.getInt("totals");
					int validaCount = 0;
					int usedCount = 0;
					for(int i=1; i<=6; i++){
						if(!"".equals(rs.getString("item"+i))){
							curPrizes[i][0] = rs.getString("item"+i);
							curPrizes[i][1] = rs.getInt("item" + i + "_count");
							usedCount += rs.getInt("item" + i + "_count");
							validaCount = i;
						}else{
							break;
						}
					}
					prizes = new Object[validaCount*2+1][3];
					prizes[0] = curPrizes[0];
					int average = (totals - usedCount) / validaCount;
					for(int i=1; i<=validaCount; i++){
						prizes[i*2-1] = new Object[]{i*2-2, curPrizes[i][0], curPrizes[i][1]};

						prizes[i*2] = new Object[]{i*2-1, (i%2==0)?"谢谢参与":"祝您好运", average};
						if(i == curPrizes.length){
							prizes[i*2][2] = totals - usedCount;
						}else{
							usedCount += average;
						}
					}
				}
			}catch(Exception e){
				System.err.println(e.getMessage());
			}finally{
				DBUtil.closeConnect(rs, ps, conn);
			}
			prizesMap.put(customerName, prizes);
		}

		return prizes;
	}

	/**
	 * 抽奖并返回角度和奖项
	 */
	private Object[] getAward(String customerName, String sessionId){
		Object[][] prizes = initPrizes(customerName);
		Object[] award = null;
		int totals = 0;
		for(int i=1;i<prizes.length;i++){
			totals+=(Integer) prizes[i][2];
		}
		if(totals == 0){
			award = new Object[2];
			award[0] = "奖抽完了，等下次吧～";
			award[1] = -1;
		}else {
			int randomNum = new Random().nextInt(totals);//随机生成1到totals的整数
			for (int i = 1; i < prizes.length; i++) {
				if ((Integer) prizes[i][2]>0 && (randomNum-(Integer)prizes[i][2]) <= 0) {
					award = new Object[3];
					//抽中项目
					if (i % 2 == 1) {
						award[0] = "恭喜你，中了" + prizes[i][1] + "！";
					} else {
						award[0] = prizes[i][1] + "，再来一次！";
					}
					award[1] = (Integer)prizes[i][0] * (360 / (prizes.length-1));
					synchronized (this) {
						//可抽奖次数减1
						prizes[i][2] = (Integer) prizes[i][2] - 1;
						//更新到数据库
						Connection conn = null;
						PreparedStatement ps = null;
						try {
							conn = DBUtil.getConnection();
							String sql = null;
							//插入log数据，仅记录设定好的奖项，
							if (i % 2 == 1) {
								sql = "insert into m_luckydraw_log (sid, luckydraw_id, prize, kick_id,kick_dttm) values (?,?,?,?,SYSDATE())";
								ps = conn.prepareStatement(sql);
								ps.setString(1, sessionId);
								ps.setInt(2, (Integer) prizes[0][1]);
								ps.setString(3, prizes[i][1].toString());
								ps.setString(4, "DEFAULT");//需添加摘要验证
								ps.execute();
							}

							//更新奖项信息，仅更新设定好的奖项，“谢谢参与”和“祝您好运”仅更新总次数
							if (i % 2 == 1) {
								sql = "update m_luckydraw set totals=totals-1, item" + ((i + 1) / 2) + "_count=item" + ((i + 1) / 2) + "_count-1 where id=?";
							} else {
								sql = "update m_luckydraw set totals=totals-1 where id=?";
							}
							ps = conn.prepareStatement(sql);
							ps.setInt(1, (Integer) prizes[0][1]);
							ps.execute();
						} catch (Exception e) {
							System.err.println(e.getMessage());
						} finally {
							DBUtil.closeConnect(null, ps, conn);
						}
					}
					break;
				} else {
					randomNum = randomNum - (Integer) prizes[i][2];
				}
			}
		}
		return award;
	}
}
