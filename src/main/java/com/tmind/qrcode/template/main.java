package com.tmind.qrcode.template;

import freemarker.template.Configuration;
import freemarker.template.Template;

import java.io.File;
import java.io.FileWriter;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lijunying on 16/8/3.
 */
public class main {

    private Configuration cfg;            //模版配置对象
    String path = Thread.currentThread().getContextClassLoader().getResource("").getPath();

    public void init() throws Exception {
        //初始化FreeMarker配置
        //创建一个Configuration实例
        cfg = new Configuration();
        //设置FreeMarker的模版文件夹位置
        cfg.setDirectoryForTemplateLoading(new File(path));
    }

    public void process() throws Exception {
        //构造填充数据的Map
        Map map = new HashMap();
        map.put("user", "lavasoft");
        map.put("url", "http://www.baidu.com/");
        map.put("name", "百度");

        CZModel c1 = new CZModel("1","name1");
        CZModel c2 = new CZModel("2","name2");
        List<CZModel> list = Arrays.asList(c1, c2);

        map.put("productList", list);

        //创建模版对象
        Template t = cfg.getTemplate("cz-template.ftl");
        //在模版上执行插值操作，并输出到制定的输出流中
        FileWriter out = null;
        out = new FileWriter(new File(path+"fe.html"));
        t.process(map, out);
        out.close();
        System.out.println("Done!");
    }

    public static void main(String[] args) throws Exception {
        main hf = new main();
        hf.init();
        hf.process();
    }
}
