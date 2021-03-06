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

        String appId = "wxb3958b46e398b477";
        // 第三方用户唯一凭证密钥
        String appSecret = "beb85dd4b786ee5753b0a7ad794954ec";

        // 调用接口获取access_token
        AccessToken at = WeixinUtil.getAccessToken(appId, appSecret);

        if (null != at) {
            // 调用接口创建菜单
            int result = WeixinUtil.createMenu(getHlHjMenu(), at.getToken());

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
    private static Menu get315KCMenu() {
        CommonButton btn11 = new CommonButton();
        btn11.setName("\ue212扫一扫");
//        btn11.setType("scancode_push");
        btn11.setType("scancode_waitmsg");
        btn11.setKey("11");


//
        CommonButton btn21 = new CommonButton();
        btn21.setName("访问官网");
        btn21.setType("view");
        btn21.setUrl("http://www.315kc.com/mobile/index1.html");

        ViewButton btn22 = new ViewButton();
        btn22.setName("品牌码查询");
        btn22.setType("view");
        btn22.setUrl("http://www.315kc.com/mobile/index.html");
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

        CommonButton btn33 = new CommonButton();
        btn33.setName("微商城");
        btn33.setType("view");
        btn33.setUrl("http://mp.weixin.qq.com/bizmall/mallshelf?id=&t=mall/list&biz=MzI5MzIwMDAwMA==&shelf_id=1&showwxpaytitle=1#wechat_redirect");



        ComplexButton mainBtn2 = new ComplexButton();
        mainBtn2.setName("在线查询");
        mainBtn2.setSub_button(new Button[] { btn21, btn22});
//
        ComplexButton mainBtn3 = new ComplexButton();
        mainBtn3.setName("帮助");
        mainBtn3.setSub_button(new Button[] { btn31, btn32, btn33 });

        /**
         * 这是公众号xiaoqrobot目前的菜单结构，每个一级菜单都有二级菜单项<br>
         *
         * 在某个一级菜单下没有二级菜单的情况，menu该如何定义呢？<br>
         * 比如，第三个一级菜单项不是“更多体验”，而直接是“幽默笑话”，那么menu应该这样定义：<br>
         * menu.setButton(new Button[] { mainBtn1, mainBtn2, btn33 });
         */
        Menu menu = new Menu();
        menu.setButton(new Button[] { btn11, mainBtn2, mainBtn3 });
        return menu;
    }

    //华粮集团菜单创建
    private static Menu getHlHjMenu() {


        CommonButton btn11 = new CommonButton();
        btn11.setName("关于我们");
        btn11.setType("click");
        btn11.setKey("11");
//
        CommonButton btn12 = new CommonButton();
        btn12.setName("合作加盟");
        btn12.setType("click");
        btn12.setKey("12");

        CommonButton btn13 = new CommonButton();
        btn13.setName("产品中心");
        btn13.setType("view");
        btn13.setUrl("http://h.eqxiu.com/s/02Hr1Nhe");

        CommonButton btn14 = new CommonButton();
        btn14.setName("联系我们");
        btn14.setType("click");
        btn14.setKey("13");

        CommonButton btn15 = new CommonButton();
        btn15.setName("华粮酒学堂");
        btn15.setType("click");
        btn15.setKey("14");


        //联购商城
        CommonButton btn21 = new CommonButton();
        btn21.setName("联购商城");
        btn21.setType("view");
        btn21.setUrl("http://weidian.com/?userid=707090");


        //扫一扫按钮
        CommonButton btn31 = new CommonButton();
        btn31.setName("\ue212扫一扫");
//        btn11.setType("scancode_push");
        btn31.setType("scancode_waitmsg");
        btn31.setKey("31");


        ComplexButton mainBtn1 = new ComplexButton();
        mainBtn1.setName("@点我");
        mainBtn1.setSub_button(new Button[] { btn11, btn12, btn13, btn14, btn15});
//


        /**
         * 这是公众号xiaoqrobot目前的菜单结构，每个一级菜单都有二级菜单项<br>
         *
         * 在某个一级菜单下没有二级菜单的情况，menu该如何定义呢？<br>
         * 比如，第三个一级菜单项不是“更多体验”，而直接是“幽默笑话”，那么menu应该这样定义：<br>
         * menu.setButton(new Button[] { mainBtn1, mainBtn2, btn33 });
         */
        Menu menu = new Menu();
        menu.setButton(new Button[] { mainBtn1, btn21, btn31 });
        return menu;
    }
}
