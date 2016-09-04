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

    }
    // instance data
    // constructor
    // public
    // private
    // protected
}
