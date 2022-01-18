package com.wangkaiping.ucenter.config;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class WeiXin implements InitializingBean {
    @Value("${weixin.id}")
    private String appId;
    @Value("${weixin.secret}")
    private String appSecret;
    @Value("${weixin.redirecturl}")
    private String redirectUrl;

    public static String APPID;
    public static String APPSECRET;
    public static String REDIRECTURl;

    @Override
    public void afterPropertiesSet() throws Exception {
        APPID = this.appId;
        APPSECRET = this.appSecret;
        REDIRECTURl = this.redirectUrl;
    }
}
