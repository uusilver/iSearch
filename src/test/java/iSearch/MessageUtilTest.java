package iSearch;/**
 * @COPYRIGHT (C) 2016 Schenker AG
 * <p/>
 * All rights reserved
 */

import com.tmind.qrcode.util.MessageUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Vani Li
 */
public class MessageUtilTest {

    public static void main(String args[]) throws Exception {
        String xml = "<xml>\n" +
                "    <ToUserName>123</ToUserName>\n" +
                "    <FromUserName>321</FromUserName>\n" +
                "    <CreateTime>1412075435</CreateTime>\n" +
                "    <MsgType>MSG</MsgType>\n" +
                "    <Event>E</Event>\n" +
                "    <EventKey>11</EventKey>\n" +
                "    <ScanCodeInfo>\n" +
                "        <ScanType>S</ScanType>\n" +
                "        <ScanResult>sfsd</ScanResult>\n" +
                "        <EventKey>1321</EventKey>\n" +
                "    </ScanCodeInfo>\n" +
                "</xml>";
        Map<String, String> map = MessageUtil.parseXmlByString(xml);
        System.out.println(map.get("EventKey"));
    }


}
