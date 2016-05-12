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
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * @author lijunying
 * @desc 二维码扫码查询servlet
 */
public class GeneralQrCodeQueryServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    /**
     * @see com.tmind.qrcode.util.Ehcache
     * @see com.tmind.qrcode.model.UserQrCodeModel
     *
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        response.setContentType("text/html;charset=gbk");

        //init
        Map responseMap = new HashMap();
        PrintWriter pw = response.getWriter();
        //获取唯一标示码
        String unique = request.getParameter("uniqueKey");
        //最终返回结果
        String result = "<a class='STYLE1'>错误</a>，数据不存在!";
        String productResult = "<a class='STYLE1'>错误</a>，数据不存在!";

        if(Ehcache.getCache(unique)==null){
            Connection conn = null;
            try{
                conn = DBUtil.getConnection();
                UserQrCodeModel userQrCodeModel = QueryService.getInstance().findUserQrCodeByUniqueId(conn, unique);
                //获得用户IP
                String vistorIP = CommonService.getInstance().getRemoteUserIpAddr(request);
                //TODO add logger
                if(UpdateService.getInstance().updateQueryTimes(userQrCodeModel.getQueryTimes(), userQrCodeModel.getId(), vistorIP)){
                    //获得产品信息
                    StringBuilder productInformationBuilder = new StringBuilder();
                    productInformationBuilder = getProductInfo(productInformationBuilder, userQrCodeModel.getUserId(), userQrCodeModel.getProductId());
                    //获得查询结果信息
                    StringBuilder queryResultStringBuilder = new StringBuilder();
                    queryResultStringBuilder.append("<font size='1'>防伪溯源身份证唯一编号：");
                    queryResultStringBuilder.append(unique + "<br/>");
                    queryResultStringBuilder.append("查询次数：");
                    if(userQrCodeModel.getQueryTimes()==0){
                        queryResultStringBuilder.append("初次查询");
                    }else{
                        String queryTimesFromDatabase = String.valueOf(userQrCodeModel.getQueryTimes());
                        queryResultStringBuilder.append(queryTimesFromDatabase);
                        responseMap.put("queryTimes", queryTimesFromDatabase);
                    }
                    queryResultStringBuilder.append("<br/>");
                    //拼接动态参数
                    UserProductModel userProductModel = QueryService.getInstance().findUserProductByParams(conn, userQrCodeModel.getProductId(), userQrCodeModel.getBatchNo());
                    if(userProductModel!=null){
                        String winLottery = null;
                        //判断用户是否有中奖信息
                        if(userQrCodeModel.getLottery_flag().equals("N") && userQrCodeModel.getLottery_check_flag().equals("N")){
                            String lotteryInfo = userProductModel.getLottery_info();
                            if(lotteryInfo!=null && lotteryInfo.length()>0)
                                winLottery = luckDrawForUser(lotteryInfo);
                            if(winLottery!=null && winLottery!=null){
                                responseMap.put("winLottery", winLottery); //中奖
                                updateQrcodeLotteryInfo(userQrCodeModel.getId(), winLottery, "Y"); //更新中奖信息进表
                            }
                            else
                                responseMap.put("winLottery", ""); //没中奖
                                updateQrcodeLotteryInfo(userQrCodeModel.getId(), winLottery, "N"); //更新中奖信息进表


                        }else{ //已经参与过抽奖
                            if(userQrCodeModel.getLottery_desc()!=null && userQrCodeModel.getLottery_desc().length()>0){
                                responseMap.put("winLottery", userQrCodeModel.getLottery_desc()); //中奖
                            }else{
                                responseMap.put("winLottery", ""); //没中奖
                            }
                        }
                        //具体内容
                        try {
                            String paraContainer = userProductModel.getBatch_params().replaceAll("\\[", "").replaceAll("\\]", "").replaceAll("\"","");
                            String paramsPool[] = paraContainer.split(",");
                            for (String s : paramsPool) {
                                //添加更多标签的时候这里需要修改
                                if (s.equals("ud")) {
                                    queryResultStringBuilder.append("产品生产时间:" + userProductModel.getUpdate_time() + "<br/>");
                                }
                                if (s.equals("sl")) {
                                    //在通用模板中，经销商信息是放在产品信息栏目中的
                                    productInformationBuilder.append("产品指定经销商:" + userProductModel.getSellArthor() + "<br/>");
                                }
                                if (s.equals("lqd")) {
                                    if(userQrCodeModel.getQuery_date()!=null && !userQrCodeModel.getQuery_date().equals("null") && userQrCodeModel.getQuery_date().length()>0)
                                        queryResultStringBuilder.append("上次查询时间:" + userQrCodeModel.getQuery_date() + "<br/>");
                                    else
                                        queryResultStringBuilder.append("无上次查询时间<br/>");

                                }
                            }
                            queryResultStringBuilder.append("产品价格:" +userProductModel.getSellPrice());

                        }catch(Exception e){

                        }
                    }
                    queryResultStringBuilder.append("</font>");
                    //最终结果

//					System.out.println(queryResultStringBuilder.toString());
                    result = queryResultStringBuilder.toString();
                    productResult = productInformationBuilder.toString();
                    responseMap.put("queryResult",result);
                    responseMap.put("productResult", productResult);
                    responseMap.put("productAddress", userProductModel.getProductAddress());

                    if(userQrCodeModel.getCacheFlag().equals("Y")) //设置了缓存标志才可以进行缓存
                        Ehcache.setCache(unique, responseMap);
                }

            }catch(Exception e){
                System.out.println(e.getMessage());

            }finally{
                DBUtil.closeConnect(null, null, conn);
            }
        }else{
            responseMap = Ehcache.getCache(unique);
        }

        //用Gson构建最后的返回内容
        Gson gson = new Gson();
        pw.print(gson.toJson(responseMap));
        pw.close();
    }



    private StringBuilder getProductInfo(StringBuilder originalStringBuilder, Integer id, String productId){
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try{
            conn = DBUtil.getConnection();
            conn.setAutoCommit(false);
            String sql = "select product_name from m_user_product_meta where user_id=? and product_id=?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ps.setString(2, productId);
            rs = ps.executeQuery();
            if(rs.next()){
                originalStringBuilder.append("商品名称:"+rs.getString("product_name")+"<br/>");
            }
            //clean ps
            ps.clearParameters();

            sql = "select user_factory_name, user_factory_address, user_telno  from user where id=?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if(rs.next()){
                originalStringBuilder.append("生产企业:"+rs.getString("user_factory_name")+"<br/>");
                originalStringBuilder.append("原产（地）:"+rs.getString("user_factory_address")+"<br/>");
                originalStringBuilder.append("企业联系方式:"+rs.getString("user_telno")+"<br/>");
            }
            originalStringBuilder.append("商品批次号:" + productId + "<br/>");
            return originalStringBuilder;
        }catch(Exception e){
            System.out.println(e.getMessage());
            try {
                conn.rollback();
            } catch (SQLException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
            return null;
        }finally{
            DBUtil.closeConnect(rs, ps, conn);
        }
    }

    private boolean updateQrcodeLotteryInfo(Integer id, String lotteryDesc, String lottery_flag){
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try{
            conn = DBUtil.getConnection();
            conn.setAutoCommit(false);
            String sql = "update M_USER_QRCODE set lottery_flag=?, lottery_check_flag='Y', lottery_desc=? where id=?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, lottery_flag);
            ps.setString(2, lotteryDesc);
            ps.setInt(3, id);
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


    //获得中奖信息
    private String luckDrawForUser(String lotteryInfo){
        //一等奖:0-1&二等奖:1-40&三等奖:40-50|100000
        int baseRandomNumber = Integer.valueOf(lotteryInfo.split("\\|")[1]);
        int luckNumber = new Random().nextInt(baseRandomNumber);
        //解析中奖信息
        String[] lotteryPool = lotteryInfo.split("\\|")[0].split("\\&");
        String lotteryDesc = null;
        //判断是否中奖
        for(int i=0; i<lotteryPool.length; i++){
            lotteryDesc = lotteryPool[i].split("\\:")[0];
            int preFix = Integer.valueOf(lotteryPool[i].split("\\:")[1].split("\\-")[0]);
            int postFix = Integer.valueOf(lotteryPool[i].split("\\:")[1].split("\\-")[1]);
            if(luckNumber>preFix && luckNumber<=postFix){
                return "恭喜你中奖:"+lotteryDesc;
            }

        }
        return null;
    }
}
