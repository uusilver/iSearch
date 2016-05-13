package iSearch;/**
 * @COPYRIGHT (C) 2016 Schenker AG
 * <p/>
 * All rights reserved
 */

import com.tmind.qrcode.util.MessageUtil;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;
import org.jdom.Element;

import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Vani Li
 */
public class MessageUtilTest {

    /**
     *
     **

         <xml>
         <ToUserName><![CDATA[gh_8a8df536f622]]></ToUserName>/n
         <FromUserName><![CDATA[ogD5KxAnA1BSDquFE5qrCiRXebJs]]></FromUserName>/n
         <CreateTime>1463122235</CreateTime>/n
         <MsgType><![CDATA[event]]></MsgType>/n
         <Event><![CDATA[scancode_waitmsg]]></Event>/n
         <EventKey><![CDATA[11]]></EventKey>/n
         <ScanCodeInfo>
         <ScanType><![CDATA[qrcode]]></ScanType>/n
         <ScanResult><![CDATA[123]]></ScanResult>/n
         </ScanCodeInfo>/n
         </xml>

     * @param args
     * @throws Exception
     */
    public static void main(String args[]) throws Exception {
        String xml = "<xml><ToUserName><![CDATA[gh_8a8df536f622]]></ToUserName>/n<FromUserName><![CDATA[ogD5KxAnA1BSDquFE5qrCiRXebJs]]></FromUserName>/n<CreateTime>1463122235</CreateTime>/n<MsgType><![CDATA[event]]></MsgType>/n<Event><![CDATA[scancode_waitmsg]]></Event>/n<EventKey><![CDATA[11]]></EventKey>/n<ScanCodeInfo><ScanType><![CDATA[qrcode]]></ScanType>/n<ScanResult><![CDATA[123]]></ScanResult>/n</ScanCodeInfo>/n</xml>\n";
        parseXmlByXPath(xml);
    }


    public static HashMap<String, String> parseXmlByXPath(String xml) throws DocumentException, UnsupportedEncodingException {

        String ToUserName  = "/xml/ToUserName";
        String FromUserName  = "/xml/FromUserName";
        String MsgType  = "/xml/MsgType";
        String Event  = "/xml/Event";
        String EventKey  = "/xml/EventKey";
        String ScanCodeInfo = "/xml/ScanCodeInfo";
        String ScanType  = "/xml/ScanCodeInfo/ScanType";
        String ScanResult  = "/xml/ScanCodeInfo/ScanResult";



        HashMap<String, String> hashMap = new HashMap<String, String>();

        SAXReader saxReader = new SAXReader();
        Document document = saxReader.read(new ByteArrayInputStream(xml.getBytes("UTF-8")));

        //ToUserName
        String name = document.selectSingleNode(ToUserName).getName();
        String text = document.selectSingleNode(ToUserName).getText();
        hashMap.put(name, text);

        //FromUserName
        name = document.selectSingleNode(FromUserName).getName();
        text = document.selectSingleNode(FromUserName).getText();
        hashMap.put(name, text);

        //MsgType
        name = document.selectSingleNode(MsgType).getName();
        text = document.selectSingleNode(MsgType).getText();
        hashMap.put(name, text);

        //Event
        name = document.selectSingleNode(Event).getName();
        text = document.selectSingleNode(Event).getText();
        hashMap.put(name, text);

        //EventKey
        name = document.selectSingleNode(EventKey).getName();
        text = document.selectSingleNode(EventKey).getText();
        hashMap.put(name, text);

        name = document.selectSingleNode(ScanCodeInfo).getName();
        if(name!=null){
            //ScanType
            name = document.selectSingleNode(ScanType).getName();
            text = document.selectSingleNode(ScanType).getText();
            hashMap.put(name, text);

            //ScanResult
            name = document.selectSingleNode(ScanResult).getName();
            text = document.selectSingleNode(ScanResult).getText();
            hashMap.put(name, text);
        }


        for (Iterator<Map.Entry<String, String>> it = hashMap.entrySet().iterator(); it.hasNext();) {
            Map.Entry<String, String> entry = (Map.Entry<String, String>) it.next();
            System.out.println(entry.getKey() + "--->" + entry.getValue());
        }

        return hashMap;
    }


}
