package com.atguigu.oss.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.internal.OSSUtils;
import com.aliyun.oss.model.PutObjectResult;
import com.atguigu.oss.service.OssService;
import com.atguigu.oss.utils.ConstantPropertiesUtils;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author
 * @date 2020/7/17-9:07
 */
@Service
public class OssServiceImpl implements OssService {
    @Override
    public String uploadAvatar(MultipartFile file) {
        // Endpoint以杭州为例，其它Region请按实际情况填写。
        String endpoint = ConstantPropertiesUtils.END_POINT;
        String accessKeyId = ConstantPropertiesUtils.KEY_ID;
        String accessKeySecret = ConstantPropertiesUtils.KEY_SECRET;
        String bucketName = ConstantPropertiesUtils.BUCKET_NAME;
        String fileName = "";
        String filePath = "";

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        // 上传文件流。
        InputStream inputStream = null;
        try {
            inputStream = file.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }

        fileName = UUID.randomUUID().toString().replaceAll("-","").substring(0,5)+file.getOriginalFilename();
        String date = new DateTime().toString("yyyy/MM/dd");
        filePath = date;

        ossClient.putObject(bucketName, filePath+"/"+fileName, inputStream);
        //把上传到阿里云的路径手动拼接出来
        String url = "https://"+bucketName+"."+endpoint+"/"+"teacher/avatar/"+file.getOriginalFilename();

        // 关闭OSSClient。
        ossClient.shutdown();
        return url;
    }
}
