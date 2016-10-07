package com.tmind.qrcode.service;

import com.tmind.qrcode.hongbao.MoneyRunner;
import com.tmind.qrcode.model.UserProductModel;
import com.tmind.qrcode.model.UserQrCodeModel;
import com.tmind.qrcode.resp.Article;
import com.tmind.qrcode.resp.NewsMessage;
import com.tmind.qrcode.resp.TextMessage;
import com.tmind.qrcode.util.DBUtil;
import com.tmind.qrcode.util.Ehcache;
import com.tmind.qrcode.util.MessageUtil;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * Created by lijunying on 16/9/1.
 * 江苏华粮集团红酒 公共号服务
 *
 */
public class RedWineWeChatCoreService{

private static Logger log = Logger.getLogger(WeChatCoreService.class);

    private static final String NO_WIN = "谢谢惠顾，感谢您对华粮集团的支持与厚爱，我们将以您的名义捐助1元钱到华粮集团爱心基金会，到时会邀您一起参加爱心捐助活动！";
        /**
         * 处理微信发来的请求
         *
         * @param request
         * @return
         */
        public static String processRequest(HttpServletRequest request) throws SQLException {
            String respMessage = null;
            try {
                // 默认返回的文本消息内容
                String respContent = "收取消息！";

                // xml请求解析

//            Map<String, String> requestMap = MessageUtil.parseXmlByXPath(request);

                Map<String, String> requestMap = MessageUtil.parseXml(request);

                // 发送方帐号（open_id）
                String fromUserName = requestMap.get("FromUserName");
                // 公众帐号
                String toUserName = requestMap.get("ToUserName");
                // 消息类型
                String msgType = requestMap.get("MsgType");

                // 回复文本消息
                TextMessage textMessage = new TextMessage();
                textMessage.setToUserName(fromUserName);
                textMessage.setFromUserName(toUserName);
                textMessage.setCreateTime(new Date().getTime());
                textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
                textMessage.setFuncFlag(0);

                // 文本消息
                if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_TEXT)) {
                    respContent = "您发送的是文本消息！";
                }
                // 图片消息
                else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_IMAGE)) {
                    respContent = "您发送的是图片消息！";
                }
                // 地理位置消息
                else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LOCATION)) {
                    respContent = "您发送的是地理位置消息！";
                }
                // 链接消息
                else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LINK)) {
                    respContent = "您发送的是链接消息！";
                }
                // 音频消息
                else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_VOICE)) {
                    respContent = "您发送的是音频消息！";
                }
                // 事件推送
                else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_EVENT)) {
                    // 事件类型
                    String eventType = requestMap.get("Event");
                    // 订阅
                    if (eventType.equals(MessageUtil.EVENT_TYPE_SUBSCRIBE)) {
                        log.info("关注账号:"+fromUserName);
                        respContent = "感谢您关注江苏华粮集团公共号\n点击下方扫一扫按钮\n重新扫码即可在线抽奖";
                    }
                    // 取消订阅
                    else if (eventType.equals(MessageUtil.EVENT_TYPE_UNSUBSCRIBE)) {
                        //如果取消订阅 则移除缓存
                    }
                    else if (eventType.equals(MessageUtil.SCANCODE_PUSH)) {
                        log.info("获得scan_push信息");

                        respContent = requestMap.get("ScanResult");
                        textMessage.setContent(respContent);
                        respMessage = MessageUtil.textMessageToXml(textMessage);

                    }
                    else if (eventType.equals(MessageUtil.SCANCODE_WAITMSG)) {
                        //事件推送
                        log.info("江苏华粮红酒扫码账号:"+fromUserName);

                        //判断时间:
                        Calendar cal = Calendar.getInstance();// 当前日期
                        int hour = cal.get(Calendar.HOUR_OF_DAY);// 获取小时
                        int minute = cal.get(Calendar.MINUTE);// 获取分钟
                        int minuteOfDay = hour * 60 + minute;// 从0:00分开是到目前为止的分钟数
                        final int start = 0* 60;// 起始时间 00:00的分钟数
                        final int end = 8 * 60;// 结束时间 8:00的分钟数
                        if (minuteOfDay >= start && minuteOfDay <= end) {
                                respContent = "请您于早上八点到晚间12点参与兑奖活动！";
                                textMessage.setContent(respContent);
                                respMessage = MessageUtil.textMessageToXml(textMessage);
                            return respMessage;
                        }
                        //将扫码账号保存在缓存里

                        try {
                            String url = requestMap.get("ScanResult");
                            String uniqueCode = null;

                            //http://weixin.qq.com/r/Vj-i_pDE0M2vrdSZ92pE/99 {99代表唯一码}{前面是微信公共号的关注链接}
                            //获取唯一识别码
                            uniqueCode = url.split("\\?")[1].trim();
                            //获取唯一标示码
                            String result = "错误，数据不存在!";
                            String productResult = null;
                            //华粮红酒的公共号cache服务一律作废
                                Connection conn = null;
                                PreparedStatement ps = null;
                            try {
                                    //必须使用一个transaction来做这些服务
                                    UserQrCodeModel userQrCodeModel = null;
                                    userQrCodeModel = QueryService.getInstance().findUserQrCodeByUniqueId(uniqueCode);
                                    log.info(fromUserName+"中奖信息:"+userQrCodeModel.getGet_lottery_flag()+", "+userQrCodeModel.getLottery_flag());
                                    log.info("唯一识别码:"+uniqueCode +" 长度:"+uniqueCode.length());
                                    //N 表示该标签没有领过奖， 可以继续操作, 并且用户的lottery flag要为Y
                                    if("N".equals(userQrCodeModel.getLottery_flag())){
                                        respContent = NO_WIN;
                                    }
                                    else if("N".equals(userQrCodeModel.getGet_lottery_flag()) && "Y".equals(userQrCodeModel.getLottery_flag())){
                                        //获得真正的中奖信息
                                        conn = DBUtil.getConnection();
                                        String lotteryResult = sendRedPackage(userQrCodeModel.getLottery_desc(), uniqueCode);
                                        log.info("中奖结果:"+lotteryResult);
                                        //不为空说明真的中奖了
                                        if(lotteryResult!=null){
                                            //更新表
                                            String sql = "update M_USER_QRCODE set get_lottery_flag=?, lottery_desc=? where id=?";
                                            ps = conn.prepareStatement(sql);
                                            ps.setString(1, "Y");
                                            ps.setString(2, lotteryResult);
                                            ps.setInt(3, userQrCodeModel.getId());
                                            ps.executeUpdate();
                                            ps.clearParameters();

                                            //保存追踪信息
                                            sql = "insert into 315kc_log_trace (operation, opt_desc, remarks, open_id) values (?,?,?,?)";
                                            ps = conn.prepareStatement(sql);
                                            ps.setString(1, "江苏华粮集团微信公共号抽奖活动");
                                            ps.setString(2, lotteryResult);
                                            ps.setString(3, uniqueCode);
                                            ps.setString(4, fromUserName);
                                            ps.execute();

                                            //TODO 真正的发送红包数据
                                            //发送一元红包
                                            if("1".equals(userQrCodeModel.getLottery_desc())){
                                                if(MoneyRunner.sendRealRedPackage(fromUserName, 100, uniqueCode)){
                                                    respContent = lotteryResult;
                                                }else{
                                                    respContent = NO_WIN;
                                                }
                                            }else if("2".equals(userQrCodeModel.getLottery_desc())){
                                                if(MoneyRunner.sendRealRedPackage(fromUserName, 100*2, uniqueCode)){
                                                    respContent = lotteryResult;
                                                }else{
                                                    respContent = NO_WIN;
                                                }
                                            }else if("3".equals(userQrCodeModel.getLottery_desc())){
                                                if(MoneyRunner.sendRealRedPackage(fromUserName, 100*3, uniqueCode)){
                                                    respContent = lotteryResult;
                                                }else{
                                                    respContent = NO_WIN;
                                                }
                                            }else if("5".equals(userQrCodeModel.getLottery_desc())){
                                                if(MoneyRunner.sendRealRedPackage(fromUserName, 100*5, uniqueCode)){
                                                    respContent = lotteryResult;
                                                }else{
                                                    respContent = NO_WIN;
                                                }
                                            }else if("10".equals(userQrCodeModel.getLottery_desc())){
                                                if(MoneyRunner.sendRealRedPackage(fromUserName, 100*10, uniqueCode)){
                                                    respContent = lotteryResult;
                                                }else{
                                                    respContent = NO_WIN;
                                                }
                                            }else if("50".equals(userQrCodeModel.getLottery_desc())){
                                                if(MoneyRunner.sendRealRedPackage(fromUserName, 100*50, uniqueCode)){
                                                    respContent = lotteryResult;
                                                }else{
                                                    respContent = NO_WIN;
                                                }
                                            }else if("90".equals(userQrCodeModel.getLottery_desc())){
                                                respContent = lotteryResult;
                                            }else if("91".equals(userQrCodeModel.getLottery_desc())){
                                                respContent = lotteryResult;
                                            }else if("99".equals(userQrCodeModel.getLottery_desc())){
                                                respContent = lotteryResult;
                                            }else if("10000".equals(userQrCodeModel.getLottery_desc())){
                                                respContent = lotteryResult;
                                            }

                                            //-----------------------------------------------------------------------

                                        }else{
                                            respContent = NO_WIN;
                                        }
                                    }else{
                                        //领过奖，返回推送消息
                                        respContent = "谢谢惠顾，感谢你的支持!";
                                    }

                                }
                                catch (Exception e) {
                                    //不再roll back
                                    respContent = NO_WIN;
                                    log.error("红包Error:"+e.getMessage());
                                }
                                finally {
                                    //关闭数据库链接
                                    DBUtil.closeConnect(null, ps, conn);
                                }
                        }
                        catch (Exception e) {
                            respContent = "非法数据";
                        }
                    }
                    // 自定义菜单点击事件
                    else if (eventType.equals(MessageUtil.EVENT_TYPE_CLICK)) {
                        // 事件KEY值，与创建自定义菜单时指定的KEY值对应
                        String eventKey = requestMap.get("EventKey");
                        List<Article> articleList = new ArrayList<Article>();
                        if (eventKey.equals("11")) {
                            //关于我们
                            Article a = new Article();
                            a.setTitle("华粮集团简介");
                            a.setPicUrl("http://mmbiz.qpic.cn/mmbiz_jpg/jCTeKYNNgnep3icibUXJaIwnlUOIZEBjl5XwbstR2owMq7ohUxk7zMRq8EPRKpgHuLsyMTibS3KCSo0aOiaoDfS8Eg/640?wx_fmt=jpeg&tp=webp&wxfrom=5&wx_lazy=1");
                            a.setDescription("江苏华粮国际贸易（集团）有限公司（简称：华粮集团），是专业从事有机食品及相关产业开发");
                            a.setUrl("http://mp.weixin.qq.com/s?__biz=MzI2MzQzNTkyNQ==&mid=100000002&idx=1&sn=df1128233cb70343be98ad2923783fd0&scene=1&srcid=0910JIqhPjnjyiQllksShl6Q#rd");
                            articleList.add(a);
                        }
                        else if (eventKey.equals("12")) {
                            //合作加盟
                            Article a = new Article();
                            a.setTitle("合作加盟");
                            a.setPicUrl("http://mmbiz.qpic.cn/mmbiz_jpg/jCTeKYNNgnep3icibUXJaIwnlUOIZEBjl5XwbstR2owMq7ohUxk7zMRq8EPRKpgHuLsyMTibS3KCSo0aOiaoDfS8Eg/640?wx_fmt=jpeg&tp=webp&wxfrom=5&wx_lazy=1");
                            a.setDescription("1、有合法经营酒类产品相关手续，公司有一定规模；\n" +
                                             "2、有一定的酒类或食品饮料销售能力及经验");
                            a.setUrl("http://mp.weixin.qq.com/s?__biz=MzI2MzQzNTkyNQ==&mid=100000004&idx=1&sn=500b8a4747545b2aff944d396018cc7e&scene=18#wechat_redirect");
                            articleList.add(a);
                        }else if(eventKey.equals("13")){
                            //联系我们
                            Article a = new Article();
                            a.setTitle("联系我们");
                            a.setPicUrl("http://mmbiz.qpic.cn/mmbiz/DEELES0vbJn35gkPrHFFRhtnnRDicSBpkmQ7E7kM0jV2icgtxJDXngEt3Jficaz55rOibPm9tZy6uMeNaaMuefOMPA/640?wx_fmt=jpeg&tp=webp&wxfrom=5&wx_lazy=1");
                            a.setDescription("华粮微信公众号：hualiang_2005");
                            a.setUrl("http://mp.weixin.qq.com/s?__biz=MzI2MzQzNTkyNQ==&mid=100000006&idx=1&sn=51b50e3e751e4cb206c96e4bc1c5bcec&scene=1&srcid=0910D8WixyJokYsR8rFUCGte#rd");
                            articleList.add(a);
                        }else if(eventKey.equals("14")){
                            //华粮酒学堂
                            Article a = new Article();
                            a.setTitle("华粮酒课堂之葡萄酒该怎么品？");
                            a.setPicUrl("http://mmbiz.qpic.cn/mmbiz/DEELES0vbJn35gkPrHFFRhtnnRDicSBpkmQ7E7kM0jV2icgtxJDXngEt3Jficaz55rOibPm9tZy6uMeNaaMuefOMPA/640?wx_fmt=jpeg&tp=webp&wxfrom=5&wx_lazy=1");
                            a.setDescription("葡萄酒怎么品\n" +
                                    "（一）品酒\n" +
                                    "品酒可区分成五个基本步骤：颜色、摇晃、闻酒、品尝、回味\n" +
                                    "颜色\n" +
                                    "想要看出葡萄酒的颜色");
                            a.setUrl("http://mp.weixin.qq.com/s?__biz=MzI2MzQzNTkyNQ==&mid=100000012&idx=1&sn=3bd72218c087271de114fb1e815d417e&scene=1&srcid=09105g7YTKGoQVt7SuSJvuzX#rd");
                            articleList.add(a);
                        }else if(eventKey.equals("15")){
                            //华粮酒学堂
                            Article a = new Article();
                            a.setTitle("活动中心");
                            a.setPicUrl("http://mmbiz.qpic.cn/mmbiz/DEELES0vbJn35gkPrHFFRhtnnRDicSBpkmQ7E7kM0jV2icgtxJDXngEt3Jficaz55rOibPm9tZy6uMeNaaMuefOMPA/640?wx_fmt=jpeg&tp=webp&wxfrom=5&wx_lazy=1");
                            a.setDescription("葡萄酒怎么品\n" +
                                    "（一）品酒\n" +
                                    "品酒可区分成五个基本步骤：颜色、摇晃、闻酒、品尝、回味\n" +
                                    "颜色\n" +
                                    "想要看出葡萄酒的颜色");
                            a.setUrl("http://mp.weixin.qq.com/s?__biz=MzI2MzQzNTkyNQ==&mid=100000012&idx=1&sn=3bd72218c087271de114fb1e815d417e&scene=1&srcid=09105g7YTKGoQVt7SuSJvuzX#rd");
                            articleList.add(a);
                        }
                        NewsMessage newsMessage = new NewsMessage();
                        newsMessage.setToUserName(fromUserName);
                        newsMessage.setFromUserName(toUserName);
                        newsMessage.setCreateTime(new Date().getTime());
                        newsMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);
                        newsMessage.setFuncFlag(0);
                        newsMessage.setArticleCount(articleList.size());
                        newsMessage.setArticles(articleList);
                        respMessage = MessageUtil.newsMessageToXml(newsMessage);
                        return respMessage;

                    }//end of click function
                }
                textMessage.setContent(respContent);
                respMessage = MessageUtil.textMessageToXml(textMessage);
            }
            catch (Exception e) {
                e.printStackTrace();
            }

            return respMessage;
        }

        //获取商品信息
        private static StringBuilder getProductInfo(StringBuilder originalStringBuilder, Integer id, String productId, UserProductModel productModel){
            Connection conn = null;
            PreparedStatement ps = null;
            ResultSet rs = null;
            try{
                conn = DBUtil.getConnection();
                conn.setAutoCommit(false);
                String sql = "select product_name, product_address, tel_no, product_factory from m_user_product_meta where user_id=? and product_id=?";
                ps = conn.prepareStatement(sql);
                ps.setInt(1, id);
                ps.setString(2, productId);
                rs = ps.executeQuery();
                if(rs.next()){
                    originalStringBuilder.append("商品名称:"+rs.getString("product_name")+"\n");
                    originalStringBuilder.append("生产企业:"+rs.getString("product_factory")+"\n");
                    originalStringBuilder.append("原产（地）:"+rs.getString("product_address")+"\n");
                    originalStringBuilder.append("企业联系方式:"+rs.getString("tel_no")+"\n");
                }
                originalStringBuilder.append("商品批次号:" + productModel.getRelatedBatch() + "\n");
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


    /**
     *   领奖规则<br/>
         1:  1元现金红包<br/>
         2:  2元现金红包<br/>
         3:  3元现金红包<br/>
         5:  5元现金红包<br/>
         10: 10元现金红包<br/>
         50: 50元现金红包<br/>
         90: 山地自行车<br/>
         91: 手机<br/>
         99: 泰国六日双飞游<br/>
         10000: 无任何中奖信息<br/>
     * @param lotteryDesc
     * @return
     *
     */
    private static String sendRedPackage(String lotteryDesc, String uniqueCode){
        String result = null;
        if("1".equals(lotteryDesc)){
            //发送一元红包
            result = "恭喜您获得了一元现金红包奖励";
        }else if("2".equals(lotteryDesc)){
            //发送两元红包
            result = "恭喜您获得了两元现金红包奖励";
        }else if("3".equals(lotteryDesc)){
            //发送两元红包
            result = "恭喜您获得了三元现金红包奖励";
        }else if("5".equals(lotteryDesc)){
            //发送两元红包
            result = "恭喜您获得了五元现金红包奖励";
        }else if("10".equals(lotteryDesc)){
            //发送两元红包
            result = "恭喜您获得了十元现金红包奖励";
        }else if("50".equals(lotteryDesc)){
            //发送两元红包
            result = "恭喜您获得了五十元现金红包奖励";
        }else if("90".equals(lotteryDesc)){
            //发送两元红包
            result = "恭喜您获得了山地自行车一辆，识别码:"+uniqueCode+"，请保留好此条消息，凭完好的酒瓶和中奖二维码标签实物联系当地经销商，或者拨打客服热线电话:400-697-1799,025-87157039";
        }else if("91".equals(lotteryDesc)){
            //发送两元红包
            result = "恭喜您获得了华为手机一台，识别码:"+uniqueCode+"，请保留好此条消息，凭完好的酒瓶和中奖二维码标签实物联系当地经销商，或者拨打客服热线电话:400-697-1799,025-87157039";
        }else if("99".equals(lotteryDesc)){
            //发送两元红包
            result = "恭喜您获得了泰国双飞六日游，识别码:"+uniqueCode+"，请保留好此条消息，凭完好的酒瓶和中奖二维码标签实物联系当地经销商，或者拨打客服热线电话:400-697-1799,025-87157039";
        }else if("10000".equals(lotteryDesc)){
            //未中奖
            result = NO_WIN;
        }

        return  result;
    }

    //TODO 建立真正的发送红包程序
}
