package com.tmind.qrcode.hongbao;
/**
 * @COPYRIGHT (C) 2016 Schenker AG
 * <p>
 * All rights reserved
 */


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;

/**
 * TODO The class TestResource is supposed to be documented...
 *
 * @author Vani Li
 */
public class TestResource {
    // static class
    // static constants
    // static method
    public static void main(String args[]) throws IOException {
        InputStream instream = MoneyUtils.class.getResourceAsStream("/test.txt");
        if(instream !=null){
            System.out.println("Exist");
            byte b[]=new byte[10000];
            instream.read(b);    //读取文件中的内容到b[]数组
            instream.close();
            System.out.println(new String(b));
        }else{
            System.out.println("Not Exist");

        }

        String xml = "<xml><ToUserName><![CDATA[gh_e136c6e50636]]></ToUserName>\n" +
                "<FromUserName><![CDATA[ogD5KxAnA1BSDquFE5qrCiRXebJs]]></FromUserName>\n" +
                "<CreateTime>1408090606</CreateTime>\n" +
                "<MsgType><![CDATA[SUCCESS]]></MsgType>\n" +
                "<Event><![CDATA[scancode_waitmsg]]></Event>\n" +
                "<EventKey><![CDATA[6]]></EventKey>\n" +
                "<ScanCodeInfo><ScanType><![CDATA[http://a.315kc.com/m/r/a/i.htm?1461115255661k1gBF8WU]]></ScanType>\n" +
                "<ScanResult><![CDATA[http://a.315kc.com/m/r/a/i.htm?00120160903J02mB4RV]]></ScanResult>\n" +
                "</ScanCodeInfo>\n" +
                "</xml>";

        System.out.println(xml.indexOf("ssssssssssss")>2);

    }
    // instance data
    // constructor
    // public
    // private
    // protected
}
