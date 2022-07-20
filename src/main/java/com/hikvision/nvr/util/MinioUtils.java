package com.hikvision.nvr.util;


import com.hikvision.nvr.common.AjaxResult;
import com.hikvision.nvr.domain.MinioConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import io.minio.*;
import java.util.Date;
import java.util.UUID;

@Component
public class MinioUtils {
    @Autowired
    private MinioConfig minioConfig;

    public AjaxResult uploadFile(String projectCode, String path, MultipartFile file, String fileName) {

        path = projectCode + "/" + path;
        return this.uploadFile(path, file,fileName);
    }

    /**
     * 将文件保存到minio的指定路径下
     *
     * @param path 存放目录
     * @param file 文件
     */
    public AjaxResult uploadFile(String path, MultipartFile file,String fileName) {
        if (!file.isEmpty()) {
            try {
                MinioClient minioClient = MinioClient.builder().endpoint(minioConfig.getUrl())
                        .credentials(minioConfig.getAccessKey(), minioConfig.getSecretKey())
                        .build();
                if (fileName==null){
                    fileName = UUID.randomUUID().toString() + "-" + file.getOriginalFilename();
                }
                String contentType = file.getContentType(); // add by qinliang 20210710 as 返回文件类型给前端
                // 检查存储桶是否已经存在
                BucketExistsArgs bucketExistsArgs = BucketExistsArgs.builder().bucket(minioConfig.getBucket()).build();
                boolean isExist = minioClient.bucketExists(bucketExistsArgs);
                if (!isExist) {
                    // 创建一个存储桶，用于存储文件。
                    MakeBucketArgs makeBucketArgs = MakeBucketArgs.builder().bucket(minioConfig.getBucket()).build();
                    minioClient.makeBucket(makeBucketArgs);
                }
                String yyyymm = MinioDateUtils.parseDateToStr("yyyy-MM",new Date());
                String dd = MinioDateUtils.parseDateToStr("dd",new Date());
                path = path + "/" + yyyymm + "/" + dd + "/";

                // 使用putObject上传一个文件到存储桶中。
                PutObjectArgs putObjectArgs = PutObjectArgs.builder().bucket(minioConfig.getBucket()).object(path + fileName)
                        .stream(file.getInputStream(), file.getSize(), -1)
                        .contentType(contentType)
                        .build();

                minioClient.putObject(putObjectArgs);

                String miniPath = "/" + minioConfig.getNginxTag() + "/" + minioConfig.getBucket() + "/" + path + fileName; // add by qinliang 20211028 as 返回文件在minio存储路径

                AjaxResult ajax = AjaxResult.success();
                ajax.put("fileName", fileName);
                ajax.put("type", contentType); // add by qinliang 20210710 as 返回文件类型给前端
                ajax.put("realPath", minioConfig.getUrl() + miniPath); // add by qinliang 20211028 as 返回文件在minio存储路径
                ajax.put("url", miniPath);

                return ajax;
            } catch (Exception e) {
                return AjaxResult.error(e.getMessage());
            }
        }
        return AjaxResult.error("上传异常，请联系管理员");
    }

    /**
     * 删除文件
     *
     * @param filename 文件名(不含服务器地址、存储桶)
     */
    public AjaxResult delFile(String filename) {

        try {
            MinioClient minioClient = MinioClient.builder().endpoint(minioConfig.getUrl())
                    .credentials(minioConfig.getAccessKey(), minioConfig.getSecretKey())
                    .build();
            minioClient.removeObject(RemoveObjectArgs.builder().bucket(minioConfig.getBucket())
                    .object(filename).build());
            AjaxResult ajax = AjaxResult.success();

            return ajax;
        } catch (Exception e) {
            return AjaxResult.error(e.getMessage());
        }
    }

    /**
     * 复制文件
     *
     * @param filename 文件名(不含服务器地址、存储桶)
     */
    public AjaxResult copyFile(String filename,String newPath) {

        try {
            MinioClient minioClient = MinioClient.builder().endpoint(minioConfig.getUrl())
                    .credentials(minioConfig.getAccessKey(), minioConfig.getSecretKey())
                    .build();
            minioClient.copyObject(CopyObjectArgs.builder().bucket(minioConfig.getBucket())
                    .object(newPath).source(
                            CopySource.builder().bucket(minioConfig.getBucket())
                                    .object(filename)
                                    .build()
                    ).build());

            AjaxResult ajax = AjaxResult.success();

            return ajax;
        } catch (Exception e) {
            return AjaxResult.error(e.getMessage());
        }
    }

}
