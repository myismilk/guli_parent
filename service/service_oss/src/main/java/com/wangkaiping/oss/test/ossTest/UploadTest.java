package com.wangkaiping.oss.test.ossTest;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.PutObjectResult;

import java.io.*;

public class UploadTest {
    public static void main(String[] args) {
        // yourEndpoint填写Bucket所在地域对应的Endpoint。以华东1（杭州）为例，Endpoint填写为https://oss-cn-hangzhou.aliyuncs.com。
        String endpoint = "oss-cn-shenzhen.aliyuncs.com";
// 阿里云账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM用户进行API访问或日常运维，请登录RAM控制台创建RAM用户。
        String accessKeyId = "LTAI5tAh4vkdBoReBweL7kZS";
        String accessKeySecret = "q9GcvBeZj9y7FDekyjh5k1aXLBpgVo";
// 填写Bucket名称，例如examplebucket。
        String bucketName = "laowangedu";
// 填写文件名。文件名包含路径，不包含Bucket名称。例如exampledir/exampleobject.txt。
        String objectName = "2022/mm.jpg";

        OSS ossClient = null;
        try {
            // 创建OSSClient实例。
            ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
            File file = new File("C:\\1awangkaipingoffile\\test\\mm.jpg");
            InputStream in = new FileInputStream(file);
            ossClient.putObject(bucketName, objectName, in);
        } catch (OSSException | FileNotFoundException e){
            e.printStackTrace();
        } finally {
            // 关闭OSSClient。
            ossClient.shutdown();
        }
    }
}
