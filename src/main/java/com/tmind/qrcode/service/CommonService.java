package com.tmind.qrcode.service;


import javax.servlet.http.HttpServletRequest;

/**
 * @author Vani Li
 */
public class CommonService {

    private static CommonService ourInstance = new CommonService();

    public static CommonService getInstance() {
        return ourInstance;
    }

    private CommonService() {
    }

    public String getRemoteUserIpAddr(HttpServletRequest request){
        String remoteAddr = request.getRemoteAddr();
        String forwarded = request.getHeader("X-Forwarded-For");
        String realIp = request.getHeader("X-Real-IP");
        String ip = null;
        if (realIp == null) {
            if (forwarded == null) {
                ip = remoteAddr;
            } else {
                ip = remoteAddr + "/" + forwarded.split(",")[0];
            }
        } else {
            if (realIp.equals(forwarded)) {
                ip = realIp;
            } else {
                if(forwarded != null){
                    forwarded = forwarded.split(",")[0];
                }
                ip = realIp + "/" + forwarded;
            }
        }
        return ip;
    }
}
