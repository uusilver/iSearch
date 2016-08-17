package com.tmind.qrcode.util;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import com.tmind.qrcode.model.AnalyzeUrlModel;
import net.sf.json.JSONObject;


/**
 * Created by lijunying on 16/8/11.
 */
public class JuheDemo {

    public static final String DEF_CHATSET = "UTF-8";
    public static final int DEF_CONN_TIMEOUT = 30000;
    public static final int DEF_READ_TIMEOUT = 30000;
    public static String userAgent = "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/29.0.1547.66 Safari/537.36";

    //配置您申请的KEY
    public static final String APPKEY = "6edf44123cbb090a0f319d410dc01760";

    //1.网站安全检测

    /**
     * @param anaUrl 待解析URL
     */
    public static AnalyzeUrlModel getRequestResult(String anaUrl) throws Exception {
        String result = null;
        String url = "http://apis.juhe.cn/webscan/";//请求接口地址
        Map params = new HashMap();//请求参数
        params.put("domain", anaUrl);//域名如:juhe.cn ,1jing.com
        params.put("dtype", "");//返回类型,xml/json/jsonp可选
        params.put("callback", "");//如返回的为JSONP，则须传递此参数
        params.put("key", APPKEY);//APP KEY
        AnalyzeUrlModel model = new AnalyzeUrlModel();
        result = net(url, params, "GET");
        JSONObject object = JSONObject.fromObject(result);
        if (object.getInt("error_code") == 0) {
            model.setErrorCode(0);
            model.setMessage(object.getString("result"));
        } else {
            model.setErrorCode(object.getInt("error_code"));
            model.setMessage(object.getString("reason"));
        }
        return model;

    }


    /**
     * {
     * "state": 1,
     * "webstate": 4,
     * "msg": "未知",
     * "scantime": null,
     * "data": {
     * "loudong": {
     * "high": null,
     * "mid": null,
     * "low": null,
     * "info": null
     * },
     * "guama": {
     * "level": 0,
     * "msg": "没有挂马或恶意内容"
     * },
     * "xujia": {
     * "level": 0,
     * "msg": "不是虚假或欺诈网站"
     * },
     * "cuangai": {
     * "level": 0,
     * "msg": "未篡改"
     * },
     * "pangzhu": {
     * "level": 0,
     * "msg": "没有旁注"
     * },
     * "score": {
     * "score": "未知",
     * "msg": "未知"
     * },
     * "violation": {
     * "level": 0,
     * "msg": "未存在违规内容"
     * },
     * "google": {
     * "level": 0,
     * "msg": "没有google搜索屏蔽"
     * }
     * }
     * }
     *
     * @param args
     */

    public static void main(String[] args) {
        try {
            AnalyzeUrlModel resultModel = getRequestResult("www.baidu.com");
            if(resultModel.getErrorCode() == 0){

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @param strUrl 请求地址
     * @param params 请求参数
     * @param method 请求方法
     * @return 网络请求字符串
     * @throws Exception
     */
    public static String net(String strUrl, Map params, String method) throws Exception {
        HttpURLConnection conn = null;
        BufferedReader reader = null;
        String rs = null;
        try {
            StringBuffer sb = new StringBuffer();
            if (method == null || method.equals("GET")) {
                strUrl = strUrl + "?" + urlencode(params);
            }
            URL url = new URL(strUrl);
            conn = (HttpURLConnection) url.openConnection();
            if (method == null || method.equals("GET")) {
                conn.setRequestMethod("GET");
            } else {
                conn.setRequestMethod("POST");
                conn.setDoOutput(true);
            }
            conn.setRequestProperty("User-agent", userAgent);
            conn.setUseCaches(false);
            conn.setConnectTimeout(DEF_CONN_TIMEOUT);
            conn.setReadTimeout(DEF_READ_TIMEOUT);
            conn.setInstanceFollowRedirects(false);
            conn.connect();
            if (params != null && method.equals("POST")) {
                try (DataOutputStream out = new DataOutputStream(conn.getOutputStream())) {
                    out.writeBytes(urlencode(params));
                }
            }
            InputStream is = conn.getInputStream();
            reader = new BufferedReader(new InputStreamReader(is, DEF_CHATSET));
            String strRead = null;
            while ((strRead = reader.readLine()) != null) {
                sb.append(strRead);
            }
            rs = sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                reader.close();
            }
            if (conn != null) {
                conn.disconnect();
            }
        }
        return rs;
    }

    //将map型转为请求参数型
    public static String urlencode(Map<String, Object> data) {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry i : data.entrySet()) {
            try {
                sb.append(i.getKey()).append("=").append(URLEncoder.encode(i.getValue() + "", "UTF-8")).append("&");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }
}
