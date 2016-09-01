package com.tmind.qrcode.service;

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
                String respContent = "请求处理异常，请稍候尝试！";

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

                            //实际查询
                            //init
                            Map responseMap = new HashMap();
                            //获取唯一标示码
                            // 最终返回结果
                            String result = "错误，数据不存在!";
                            String productResult = null;
                            //华粮红酒的公共号cache服务一律作废
                                Connection conn = null;
                                try {
                                    conn = DBUtil.getConnection();
                                    UserQrCodeModel userQrCodeModel = null;
                                    userQrCodeModel = QueryService.getInstance().findUserQrCodeByUniqueId(conn, uniqueCode);
                                    //TODO 判断get_lottery_flag //查看有没有领过奖, 如果领过奖，直接返回结果

                                    /*
                                        领奖规则
                                        1:  1元现金红包
                                        2:  2元现金红包
                                        3:  3元现金红包
                                        5:  5元现金红包
                                        10: 10元现金红包
                                        50: 50元现金红包
                                        90: 山地自行车
                                        91: 手机
                                        92: 泰国六日双飞游
                                     */

                                    //将lottery_desc替换掉

                                    //TODO 调用发送红包接口

                                    //获得用户IP
                                    String vistorIP = CommonService.getInstance().getRemoteUserIpAddr(request);
                                    //TODO add logger

                                }
                                catch (Exception e) {
                                    System.out.println(e.getMessage());

                                }
                                finally {
                                    DBUtil.closeConnect(null, null, conn);
                                }


                            //
                            String finalResult = (String) responseMap.get("productResult") + responseMap.get("queryResult");
                            respContent = finalResult;
                        }
                        catch (Exception e) {
                            respContent = "非法数据，请检查";

                        }

                    }
                    // 自定义菜单点击事件
                    else if (eventType.equals(MessageUtil.EVENT_TYPE_CLICK)) {
                        // 事件KEY值，与创建自定义菜单时指定的KEY值对应
                        String eventKey = requestMap.get("EventKey");

                        if (eventKey.equals("11")) {
                            respContent = "点击事件";
                        }
                        else if (eventKey.equals("31")) {
                            respContent = "江西省\n江西省搭手网络科技有限公司\n" +
                                    "\n" +
                                    "河北省\n保定东信电子科技有限公司(服务地区:河北)\n" +
                                    "\n" +
                                    "江苏省\n苏州爱威尔信息科技有限公司(服务地区:苏州)\n无锡安可信信息技术有限公司(服务地区:无锡)";
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
}
