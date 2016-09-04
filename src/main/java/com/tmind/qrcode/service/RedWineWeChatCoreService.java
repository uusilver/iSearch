package com.tmind.qrcode.service;

import com.tmind.qrcode.hongbao.MoneyRunner;
import com.tmind.qrcode.model.UserProductModel;
import com.tmind.qrcode.model.UserQrCodeModel;
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
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by lijunying on 16/9/1.
 * 江苏华粮集团红酒 公共号服务
 *
 */
public class RedWineWeChatCoreService{

private static Logger log = Logger.getLogger(WeChatCoreService.class);

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
                        respContent = "感谢您关注315快查公共号\n点击下方扫一扫按钮\n重新扫码即可查询产品信息";
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
                        //将扫码账号保存在缓存里

                        try {
                            String url = requestMap.get("ScanResult");
                            String uniqueCode = null;

                            //http://weixin.qq.com/r/Vj-i_pDE0M2vrdSZ92pE/99 {99代表唯一码}{前面是微信公共号的关注链接}
                            //获取唯一识别码
                            uniqueCode = url.split("\\?")[1];
                            //获取唯一标示码
                            String result = "错误，数据不存在!";
                            String productResult = null;
                            //华粮红酒的公共号cache服务一律作废
                                Connection conn = null;
                                PreparedStatement ps = null;
                            try {
                                    conn = DBUtil.getConnection();
                                    //必须使用一个transaction来做这些服务
                                    conn.setAutoCommit(false);

                                    UserQrCodeModel userQrCodeModel = null;
                                    userQrCodeModel = QueryService.getInstance().findUserQrCodeByUniqueId(conn, uniqueCode);
                                    //N 表示该标签没有领过奖， 可以继续操作, 并且用户的lottery flag要为Y
                                    if("N".equals(userQrCodeModel.getLottery_flag())){
                                        respContent = "未中奖，感谢您的关注!";
                                    }
                                    else if("N".equals(userQrCodeModel.getGet_lottery_flag()) && "Y".equals(userQrCodeModel.getLottery_flag())){
                                        //获得真正的中奖信息
                                        String lotteryResult = sendRedPackage(userQrCodeModel.getLottery_desc(), uniqueCode);
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
                                            //李俊英的openid: ogD5KxAnA1BSDquFE5qrCiRXebJs
                                            //张信的openid: ogD5KxLT9J30V7kiijjkB_QKxrc8
                                            //发送一元红包
                                            if("1".equals(userQrCodeModel.getLottery_desc())){
                                                if(MoneyRunner.sendRealRedPackage(fromUserName, 100, uniqueCode)){
                                                    respContent = lotteryResult;
                                                }else{
                                                    respContent = "很遗憾，您未能中奖!";
                                                }
                                            }else if("99".equals(userQrCodeModel.getLottery_desc())){
                                                respContent = lotteryResult;
                                            }

                                            //-----------------------------------------------------------------------
                                            //commit
                                            conn.commit();


                                        }else{
                                            respContent = "很遗憾，您未能中奖!";
                                        }
                                        conn.commit();
                                    }else{
                                        //领过奖，返回推送消息
                                        respContent = "您已经参与过抽奖活动，感谢您的支持!";
                                    }

                                }
                                catch (Exception e) {
                                    //不再roll back
                                    respContent = "很遗憾，您未能中奖!";
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

                        if (eventKey.equals("11")) {
                            respContent = "点击事件";
                        }
                        else if (eventKey.equals("32")) {
                            respContent = "即将开放！";
                        }
                    }
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
         92: 泰国六日双飞游<br/>
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
        }else if("99".equals(lotteryDesc)){
            //发送两元红包
            result = "恭喜您获得了泰国双飞六日游，识别码:"+uniqueCode+"，请保留好此条消息，凭完好的酒瓶和中奖二维码标签实物联系官方对付，联系电话:025-XXXXXXX";
        }

        return  result;
    }

    //TODO 建立真正的发送红包程序
}
