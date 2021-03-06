package com.tmind.qrcode.util;

import java.io.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.tmind.qrcode.resp.Article;
import com.tmind.qrcode.resp.MusicMessage;
import com.tmind.qrcode.resp.NewsMessage;
import com.tmind.qrcode.resp.TextMessage;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;


import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.core.util.QuickWriter;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;
import com.thoughtworks.xstream.io.xml.XppDriver;

/**
 * @author Vani Li
 * @Desc: 消息处理工具
 */
public class MessageUtil {

    private static Logger log = Logger.getLogger(MessageUtil.class);
    /**
     * 返回消息类型：文本
     */
    public static final String RESP_MESSAGE_TYPE_TEXT = "text";

    /**
     * 返回消息类型：音乐
     */
    public static final String RESP_MESSAGE_TYPE_MUSIC = "music";

    /**
     * 返回消息类型：图文
     */
    public static final String RESP_MESSAGE_TYPE_NEWS = "news";

    /**
     * 请求消息类型：文本
     */
    public static final String REQ_MESSAGE_TYPE_TEXT = "text";

    /**
     * 请求消息类型：图片
     */
    public static final String REQ_MESSAGE_TYPE_IMAGE = "image";

    /**
     * 请求消息类型：链接
     */
    public static final String REQ_MESSAGE_TYPE_LINK = "link";

    /**
     * 请求消息类型：地理位置
     */
    public static final String REQ_MESSAGE_TYPE_LOCATION = "location";

    /**
     * 请求消息类型：音频
     */
    public static final String REQ_MESSAGE_TYPE_VOICE = "voice";

    /**
     * 请求消息类型：推送
     */
    public static final String REQ_MESSAGE_TYPE_EVENT = "event";

    /**
     * 事件类型：subscribe(订阅)
     */
    public static final String EVENT_TYPE_SUBSCRIBE = "subscribe";

    /**
     * 事件类型：unsubscribe(取消订阅)
     */
    public static final String EVENT_TYPE_UNSUBSCRIBE = "unsubscribe";

    /**
     * 事件类型：CLICK(自定义菜单点击事件)
     */
    public static final String EVENT_TYPE_CLICK = "CLICK";

    /**
     * 事件类型：scancode_push
     */
    public static final String SCANCODE_PUSH  = "scancode_push";

    /**
     * 事件类型：scancode_waitmsg
     */
    public static final String SCANCODE_WAITMSG  = "scancode_waitmsg";

    /**
     * 解析微信发来的请求（XML）
     *
     * @param request
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public static Map<String, String> parseXml(HttpServletRequest request) throws Exception {
        // 将解析结果存储在HashMap中
        Map<String, String> map = new HashMap<String, String>();

        // 从request中取得输入流
        InputStream inputStream = request.getInputStream();

        // 读取输入流
        SAXReader reader = new SAXReader();
        Document document = reader.read(inputStream);
        // 得到xml根元素
        Element root = document.getRootElement();
        // 得到根元素的所有子节点
        List<Element> elementList = root.elements();

        // 遍历所有子节点
        for (Element e : elementList) {
            if ("ScanCodeInfo".equals(e.getName())) {
                Element root1 = e;
                List<Element> elementList1 = root1.elements();
                for (Element element : elementList1) {
                    map.put(element.getName(), element.getText());
//                    System.out.println(element.getName()+"===>"+element.getText());
                }
            }
            else {
                map.put(e.getName(), e.getText());
//                System.out.println(e.getName()+"===>"+ e.getText());
            }
        }

        // 释放资源
        inputStream.close();
        inputStream = null;

        return map;
    }

    /**
     * 解析微信的结果信息（XML）
     *
     * @param result
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public static Map<String, String> parseXml4Result(String result) {
        // 将解析结果存储在HashMap中
        Map<String, String> map = new HashMap<String, String>();

        // 从request中取得输入流
        InputStream inputStream = new ByteArrayInputStream(result.getBytes());

        try {
            // 读取输入流
            SAXReader reader = new SAXReader();
            Document document = reader.read(inputStream);
            // 得到xml根元素
            Element root = document.getRootElement();
            // 得到根元素的所有子节点
            List<Element> elementList = root.elements();

            // 遍历所有子节点
            for (Element e : elementList) {
                if ("ScanCodeInfo".equals(e.getName())) {
                    Element root1 = e;
                    List<Element> elementList1 = root1.elements();
                    for (Element element : elementList1) {
                        map.put(element.getName(), element.getText());
    //                    System.out.println(element.getName()+"===>"+element.getText());
                    }
                }
                else {
                    map.put(e.getName(), e.getText());
    //                System.out.println(e.getName()+"===>"+ e.getText());
                }
            }

            // 释放资源


        } catch (Exception e) {
            log.error(e.getMessage());
        }finally {
            try {
                inputStream.close();
            } catch (IOException ex) {
                log.error("关闭流出错:" + ex.getMessage());
            }
        }
        inputStream = null;

        return map;
    }

    /**
     * 文本消息对象转换成xml
     *
     * @param textMessage 文本消息对象
     * @return xml
     */
    public static String textMessageToXml(TextMessage textMessage) {
        xstream.alias("xml", textMessage.getClass());
        return xstream.toXML(textMessage);
    }

    /**
     * 音乐消息对象转换成xml
     *
     * @param musicMessage 音乐消息对象
     * @return xml
     */
    public static String musicMessageToXml(MusicMessage musicMessage) {
        xstream.alias("xml", musicMessage.getClass());
        return xstream.toXML(musicMessage);
    }

    /**
     * 图文消息对象转换成xml
     *
     * @param newsMessage 图文消息对象
     * @return xml
     */
    public static String newsMessageToXml(NewsMessage newsMessage) {
        xstream.alias("xml", newsMessage.getClass());
        xstream.alias("item", new Article().getClass());
        return xstream.toXML(newsMessage);
    }

    /**
     * 扩展xstream，使其支持CDATA块
     *
     * @date 2013-05-19
     */
    private static XStream xstream = new XStream(new XppDriver() {
        public HierarchicalStreamWriter createWriter(Writer out) {
            return new PrettyPrintWriter(out) {
                // 对所有xml节点的转换都增加CDATA标记
                boolean cdata = true;

                @SuppressWarnings("unchecked")
                public void startNode(String name, Class clazz) {
                    super.startNode(name, clazz);
                }

                protected void writeText(QuickWriter writer, String text) {
                    if (cdata) {
                        writer.write("<![CDATA[");
                        writer.write(text);
                        writer.write("]]>");
                    }
                    else {
                        writer.write(text);
                    }
                }
            };
        }
    });

    private static String convertStreamToString(InputStream is) {

        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            try {
                is.close();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();

    }

    public static HashMap<String, String> parseXmlByXPath(HttpServletRequest request) throws DocumentException, IOException {


        // 从request中取得输入流
        InputStream inputStream = request.getInputStream();

        String xml = convertStreamToString(inputStream).trim();

        String ToUserName  = "/xml/ToUserName";
        String FromUserName  = "/xml/FromUserName";
        String MsgType  = "/xml/MsgType";
        String Event  = "/xml/Event";
        String EventKey  = "/xml/EventKey";
        String ScanCodeInfo = "/xml/ScanCodeInfo";
        String ScanType  = "/xml/ScanCodeInfo/ScanType";
        String ScanResult  = "/xml/ScanCodeInfo/ScanResult";


        // 将解析结果存储在HashMap中
        HashMap<String, String> hashMap = new HashMap<String, String>();

        SAXReader saxReader = new SAXReader();
        Document document = saxReader.read(new ByteArrayInputStream(xml.getBytes("UTF-8")));

        //ToUserName
        String name = null;
        String text = null;
        Node toUserNode = document.selectSingleNode(ToUserName);
        if(toUserNode != null)
            name = toUserNode.getName();
            text = toUserNode.getText();
            hashMap.put(name, text);

        //FromUserName
        Node fromUserNode = document.selectSingleNode(FromUserName);
            if(fromUserNode != null)
            name = fromUserNode.getName();
            text = fromUserNode.getText();
            hashMap.put(name, text);

        //MsgType
        Node msgTypeEvent = document.selectSingleNode(Event);
        if(msgTypeEvent != null)
            name = msgTypeEvent.getName();
            text = msgTypeEvent.getText();
            hashMap.put(name, text);

        //Event
        Node nodeEvent = document.selectSingleNode(Event);
        if(nodeEvent != null){
            name = nodeEvent.getName();
            text = nodeEvent.getText();
            if(name!=null && text != null)
                hashMap.put(name, text);
        }


        //EventKey
        Node eventKeynode = document.selectSingleNode(EventKey);
        if(eventKeynode != null){
            name = eventKeynode.getName();
            text = eventKeynode.getText();
            if(name!=null && text != null)
                hashMap.put(name, text);
        }

        //ScanCodeInfo
        Node scanCodeInfonode = document.selectSingleNode(ScanCodeInfo);
        if(scanCodeInfonode!=null){
            //ScanType
            Node scanTypeNode = document.selectSingleNode(ScanType);
            name = scanTypeNode.getName();
            text = scanTypeNode.getText();
            if(name!=null && text != null)
                hashMap.put(name, text);

            //ScanResult
            Node scanResultNode = document.selectSingleNode(ScanResult);
            name = scanResultNode.getName();
            text = scanResultNode.getText();
            if(name!=null && text != null)
                hashMap.put(name, text);
        }


        return hashMap;
    }
}
