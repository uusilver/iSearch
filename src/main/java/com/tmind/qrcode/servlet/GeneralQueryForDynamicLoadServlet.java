package com.tmind.qrcode.servlet;

import com.google.gson.Gson;
import com.tmind.qrcode.model.GeneralResponseModel;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by lijunying on 17/1/15.
 */
public class GeneralQueryForDynamicLoadServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse res)
            throws ServletException, IOException {

        //取得表单数据


        //init
        List<GeneralResponseModel> responseList = new ArrayList<GeneralResponseModel>();
        //获取唯一标示码
        String unique=request.getParameter("unquieKey");
        //转向到一个界面c.jsp，取出list中的数据
        String url=request.getParameter("url");
        System.out.println("======>页面获取Unique:"+unique);
        System.out.println("======>页面获取Unique长度:"+unique.length());
        System.out.println("======>页面获取URL:"+url);

        int queryTimes = 0;
        String lotteryDesc = null;
            Connection conn = null;
            try{
                conn = DBUtil.getConnection();
                UserQrCodeModel userQrCodeModel = QueryService.getInstance().findUserQrCodeByUniqueId(unique.trim());
                System.out.println("======>页面获取userQrCodeModel:"+userQrCodeModel.toString());
                //获得用户IP
                String vistorIP = CommonService.getInstance().getRemoteUserIpAddr(request);
                //TODO add logger
                if(UpdateService.getInstance().updateQueryTimes(userQrCodeModel.getQueryTimes(), userQrCodeModel.getId(), vistorIP)){
                    //获得产品信息
                    StringBuilder productInformationBuilder = new StringBuilder();
                    //获得查询结果信息
                    GeneralResponseModel queryTimesModel = new GeneralResponseModel();
                    queryTimesModel.setKey("queryTimes");
                    queryTimes = userQrCodeModel.getQueryTimes();

                    if(userQrCodeModel.getUserId()==0){
                        queryTimes = -1;
                    }
                    //定制信息
                    GeneralResponseModel lotterDescModel = new GeneralResponseModel();
                    lotteryDesc = userQrCodeModel.getLottery_desc();
                    //拼接动态参数
//                    UserProductModel userProductModel = QueryService.getInstance().findUserProductByParams(conn, userQrCodeModel.getProductId(), userQrCodeModel.getBatchNo());
//                    List<GeneralResponseModel> list = getProductInfo(productInformationBuilder, userQrCodeModel.getUserId(), userQrCodeModel.getProductId(), userProductModel);
//
//                    GeneralResponseModel generalResponseModel = new GeneralResponseModel();
//                    generalResponseModel.setKey("productAddress");
//                    generalResponseModel.setResult(userProductModel.getProductAddress());
//                    //最终结果
//                    responseList.add(generalResponseModel);
//                    responseList.add(queryTimesModel);
//                    responseList.add(lotterDescModel);
//                    responseList.addAll(list);
//					System.out.println(queryResultStringBuilder.toString());

                    request.setAttribute("queryTimes",queryTimes);
                    request.setAttribute("uniqueKey",unique);
                    request.setAttribute("lotteryDesc",lotteryDesc);
                    System.out.println("======>页面获取queryTimes:"+queryTimes);
                    System.out.println("======>页面获取lotteryDesc:"+lotteryDesc);


                    if(userQrCodeModel.getCacheFlag().equals("Y")) //设置了缓存标志才可以进行缓存
                        Ehcache.setCache(unique, request);
                }

            }catch(Exception e){
                System.out.println("=====>服务端:"+e.getMessage());

            }finally{
                DBUtil.closeConnect(null, null, conn);
            }

        request.getRequestDispatcher(url).forward(request, res);//转发getRequestDispatcher

    }

    private List<GeneralResponseModel> getProductInfo(StringBuilder originalStringBuilder, Integer id, String productId, UserProductModel productModel){
        List<GeneralResponseModel> list = new ArrayList<GeneralResponseModel>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try{
            conn = DBUtil.getConnection();
            conn.setAutoCommit(false);
            String sql = "select product_name, product_address, tel_no, product_factory, show_desc, product_desc from m_user_product_meta where user_id=? and product_id=?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ps.setString(2, productId);
            rs = ps.executeQuery();
            String showFlag = null;
            String productDesc = null;
            if(rs.next()){
                //生产厂家
                GeneralResponseModel generalResponseModel = new GeneralResponseModel();
                generalResponseModel.setKey("productName");
                generalResponseModel.setResult(rs.getString("product_name"));

                //生产企业
                GeneralResponseModel generalResponseModel1 = new GeneralResponseModel();
                generalResponseModel1.setKey("productFactory");
                generalResponseModel1.setResult(rs.getString("product_factory"));

                //原产（地
                GeneralResponseModel generalResponseModel2 = new GeneralResponseModel();
                generalResponseModel2.setKey("productOriginalAddress");
                generalResponseModel2.setResult(rs.getString("product_address"));

                //企业联系方式
                GeneralResponseModel generalResponseModel3 = new GeneralResponseModel();
                generalResponseModel3.setKey("telNo");
                generalResponseModel3.setResult(rs.getString("tel_no"));

                showFlag = rs.getString("show_desc");
                productDesc = rs.getString("product_desc");
                list.add(generalResponseModel);
                list.add(generalResponseModel1);
                list.add(generalResponseModel2);
                list.add(generalResponseModel3);
            }

            return list;
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
