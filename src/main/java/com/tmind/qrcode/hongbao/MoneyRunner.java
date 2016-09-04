package com.tmind.qrcode.hongbao;

import com.tmind.qrcode.service.InsertService;
import com.tmind.qrcode.util.MessageUtil;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

public class MoneyRunner {

	private static Logger log = Logger.getLogger(MoneyRunner.class);

	final static  String url = "https://api.mch.weixin.qq.com/mmpaymkttransfers/sendredpack";
	public static void main(String[] args) {

	}

	/**
	 *
	 * @param openId 用户的openId
	 * @param money  用户的红包金额
	 * @param unquieKey 用户的唯一查询码
	 * @return 布尔值，true ＝ 成功，false = 失败
	 */
	public static boolean sendRealRedPackage(String openId, int money, String unquieKey){
		String orderNNo =  MoneyUtils.getOrderNo() ;
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("nonce_str", MoneyUtils.buildRandom());//随机字符串
		map.put("mch_billno", orderNNo);//商户订单
		map.put("mch_id", "1343381101");//商户号
		map.put("wxappid", "wx3f6a7a3ab9e7c065");//商户appid
		map.put("nick_name", "南京安查通信息技术有限公司");//提供方名称
		map.put("send_name", "南京安查通");//用户名
		map.put("re_openid", openId);//用户openid
		map.put("total_amount", money);//付款金额
		map.put("min_value", money);//最小红包
		map.put("max_value", money);//最大红包
		map.put("total_num", 1);//红包发送总人数
		map.put("wishing", "欢迎光临");//红包祝福语
		map.put("client_ip", "127.0.0.1");//ip地址
		map.put("act_name", "购物抽奖");//活动名称
		map.put("remark", "购物抽奖");//备注
		map.put("sign", MoneyUtils.createSign(map));//签名

		String result = "";
		try {
			result = MoneyUtils.doSendMoney(url, MoneyUtils.createXML(map));
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(result.indexOf("SUCCESS")>0){
			return true;
		}
		return false;
	}
}
