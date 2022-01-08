package com.wangkaiping.oss.utils;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ConstantPropertiesUtils implements InitializingBean {

    @Value("${aliyun.oss.file.endpoint}")
    private String endpoint;

    @Value("${aliyun.oss.file.keyid}")
    private String keyId;

    @Value("${aliyun.oss.file.keysecret}")
    private String keySecret;

    @Value("${aliyun.oss.file.bucketname}")
    private String bucketName;

    public static String END_POINT;
    public static String KRY_ID;
    public static String KEY_SECRET;
    public static String BUCKET_NAME;

    //这个方法实现InitializingBean接口重写的方法。
    //用于在将bean放入到容器后会执行的操作
    @Override
    public void afterPropertiesSet(){
        END_POINT = endpoint;
        KRY_ID = keyId;
        KEY_SECRET = keySecret;
        BUCKET_NAME = bucketName;
    }
}
