package com.tmind.qrcode.service;/**
 * @COPYRIGHT (C) 2016 Schenker AG
 * <p/>
 * All rights reserved
 */

import com.tmind.qrcode.pojo.*;
import com.tmind.qrcode.util.WeixinUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author Vani Li
 */
public class MenuService {

    private static Logger log = LoggerFactory.getLogger(MenuService.class);

    public static void main(String[] args) {
        // 第三方用户唯一凭证
        String appId = "wx3f6a7a3ab9e7c065";
        // 第三方用户唯一凭证密钥
        String appSecret = "90ab422d5a2c61880fe5bd238b96522b";

        // 调用接口获取access_token
        AccessToken at = WeixinUtil.getAccessToken(appId, appSecret);

        if (null != at) {
            // 调用接口创建菜单
            int result = WeixinUtil.createMenu(getMenu(), at.getToken());

            // 判断菜单创建结果
            if (0 == result)
                System.out.println("菜单创建成功！");
//                log.info("菜单创建成功！");
            else
//                log.info("菜单创建失败，错误码：" + result);
                System.out.println("菜单创建失败，错误码：" + result);
        }
    }

    /**
     * 组装菜单数据
     *
     * @return
     */
    private static Menu getMenu() {
        CommonButton btn11 = new CommonButton();
        btn11.setName("扫码查询");
        btn11.setType("scancode_push");
//        btn11.setType("scancode_waitmsg");
        btn11.setKey("11");

        ViewButton btn12 = new ViewButton();
        btn12.setName("品牌码查询");
        btn12.setType("view");
        btn12.setUrl("http://www.315kc.com/mobile/index.html");
//
        CommonButton btn21 = new CommonButton();
        btn21.setName("访问官网查询");
        btn21.setType("view");
        btn21.setUrl("http://www.315kc.com/mobile/index1.html");
//
//
        CommonButton btn31 = new CommonButton();
        btn31.setName("渠道商");
        btn31.setType("click");
        btn31.setKey("31");
//
        CommonButton btn32 = new CommonButton();
        btn32.setName("员工信息");
        btn32.setType("click");
        btn32.setKey("32");


        ComplexButton mainBtn1 = new ComplexButton();
        mainBtn1.setName("在线查询");
        mainBtn1.setSub_button(new Button[] { btn11, btn12 });

        ComplexButton mainBtn2 = new ComplexButton();
        mainBtn2.setName("官方网站");
        mainBtn2.setSub_button(new CommonButton[] { btn21});
//
        ComplexButton mainBtn3 = new ComplexButton();
        mainBtn3.setName("信息验证");
        mainBtn3.setSub_button(new CommonButton[] { btn31, btn32 });

        /**
         * 这是公众号xiaoqrobot目前的菜单结构，每个一级菜单都有二级菜单项<br>
         *
         * 在某个一级菜单下没有二级菜单的情况，menu该如何定义呢？<br>
         * 比如，第三个一级菜单项不是“更多体验”，而直接是“幽默笑话”，那么menu应该这样定义：<br>
         * menu.setButton(new Button[] { mainBtn1, mainBtn2, btn33 });
         */
        Menu menu = new Menu();
        menu.setButton(new Button[] { mainBtn1, mainBtn2, mainBtn3 });
        return menu;
    }
}
