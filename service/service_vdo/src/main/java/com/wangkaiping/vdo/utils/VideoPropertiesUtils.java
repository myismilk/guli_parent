package com.wangkaiping.vdo.utils;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class VideoPropertiesUtils implements InitializingBean {
    @Value("${aliyun.vdo.keyid}")
    private String keyId;
    @Value("${aliyun.vdo.keysecret}")
    private String keySecret;

    public static String KEYID;
    public static String KEYSECRET;

    @Override
    public void afterPropertiesSet() throws Exception {
        KEYID = this.keyId;
        KEYSECRET = this.keySecret;
    }
}
