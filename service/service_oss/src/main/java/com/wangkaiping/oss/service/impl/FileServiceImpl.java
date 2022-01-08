package com.wangkaiping.oss.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.wangkaiping.oss.service.FileService;
import com.wangkaiping.oss.utils.ConstantPropertiesUtils;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {
    @Override
    public String updateLoadTest(MultipartFile file) {
        // yourEndpoint填写Bucket所在地域对应的Endpoint。以华东1（杭州）为例，Endpoint填写为https://oss-cn-hangzhou.aliyuncs.com。
        String endpoint = ConstantPropertiesUtils.END_POINT;
        // 阿里云账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM用户进行API访问或日常运维，请登录RAM控制台创建RAM用户。
        String accessKeyId = ConstantPropertiesUtils.KRY_ID;
        String accessKeySecret = ConstantPropertiesUtils.KEY_SECRET;
        String bucketName = ConstantPropertiesUtils.BUCKET_NAME;

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        try {
            // 填写本地文件的完整路径。如果未指定本地路径，则默认从示例程序所属项目对应本地路径中上传文件流。
            InputStream inputStream = file.getInputStream();
            //文件名称可能相同，为了保证唯一性，通过UUID实现
            String fileName = UUID.randomUUID().toString().replaceAll("-","")+file.getOriginalFilename();
            //将文件按照日期分类 年/月/日/文件名

            /*

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
            sdf.format(new Date());
            //形成路径加文件名
            String fileUrl = sdf.format(new Date())+"/"+fileName;
            */
            //通过工具获取当前时间
            String now = new DateTime().toString("yyyy/MM/dd");
            String fileUrl = now+"/"+fileName;

            // 依次填写Bucket名称（例如examplebucket）和Object完整路径（例如exampledir/exampleobject.txt）。Object完整路径中不能包含Bucket名称。
            ossClient.putObject(ConstantPropertiesUtils.BUCKET_NAME, fileUrl, inputStream);
            //https://laowangedu.oss-cn-shenzhen.aliyuncs.com/%E5%A4%B4%E5%83%8F.PNG
            //这个是存放在根目录下的
            String url = "https://"+bucketName+"."+endpoint+"/"+fileUrl;
            return url;
        }catch (Exception e){
            e.printStackTrace();
            return "有问题";
        }finally {
            // 关闭OSSClient。
            ossClient.shutdown();
        }
    }
}
