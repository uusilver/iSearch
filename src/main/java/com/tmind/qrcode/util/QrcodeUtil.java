package com.tmind.qrcode.util;


import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;


import java.util.Map;

import javax.imageio.ImageIO;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.Binarizer;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.EncodeHintType;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.Result;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;

/**
 * Created by lijunying on 16/7/17.
 */
public class QrcodeUtil {

    /**
     * 二维码的解析
     *
     * @param imageBytes
     */
    public static Result parseCode(byte[] imageBytes)
    {
        try
        {
            MultiFormatReader formatReader = new MultiFormatReader();

            ByteArrayInputStream in = new ByteArrayInputStream(imageBytes);

            BufferedImage image = ImageIO.read(in);

            LuminanceSource source = new BufferedImageLuminanceSource(image);
            Binarizer binarizer = new HybridBinarizer(source);
            BinaryBitmap binaryBitmap = new BinaryBitmap(binarizer);

            Map hints = new HashMap();
            hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");

            Result result = formatReader.decode(binaryBitmap, hints);

            return result;

//            System.out.println("解析结果 = " + result.toString());
//            System.out.println("二维码格式类型 = " + result.getBarcodeFormat());
//            System.out.println("二维码文本内容 = " + result.getText());
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }
}
