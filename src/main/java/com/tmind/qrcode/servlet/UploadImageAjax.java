package com.tmind.qrcode.servlet;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.zxing.Result;
import com.tmind.qrcode.model.UploadResultModel;
import com.tmind.qrcode.util.QrcodeUtil;
import sun.misc.BASE64Decoder;

/**
 * Created by lijunying on 16/7/17.
 */

/**
 * 上传图片。
 */
public class UploadImageAjax extends HttpServlet {
    private static final long serialVersionUID = 1L;
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String image = req.getParameter("image");
        // 只允许jpg
        String header = "data:image/jpeg;base64,";
        if(image.indexOf(header) != 0){
            resp.getWriter().print(new Gson().toJson(wrapJSON("fail", "")));
            System.out.println("非法格式");
            return;
        }
        // 去掉头部
        image = image.substring(header.length());
        // 写入磁盘
        boolean success = false;

        Result result = null;

        BASE64Decoder decoder = new BASE64Decoder();
        try {
            byte[] decodedBytes = decoder.decodeBuffer(image);
            //解析二维码
            result =  QrcodeUtil.parseCode(decodedBytes);
            //保存二维码图片到本地
            String imgFilePath = "uploadimage"+ UUID.randomUUID().toString()+".jpg";
            FileOutputStream out = new FileOutputStream(imgFilePath);
            out.write(decodedBytes);

            out.close();
            success = true;

        } catch (Exception e) {
            success = false;
            e.printStackTrace();
        }
        System.out.println(result.getText());
        if(result.getText().indexOf("315kc")>0) {
            resp.getWriter().print(new Gson().toJson(wrapJSON("success",result.getText())));
        }
        else{
            resp.getWriter().print(new Gson().toJson(wrapJSON("fail", "")));
        }

    }
    private UploadResultModel wrapJSON(String success, String text){
        return new UploadResultModel(success, text);

    }

}
