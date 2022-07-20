package com.hikvision.nvr.domain;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "minio")
public class MinioConfig {
//    private String prjNo;
    private String url;
    private String accessKey;
    private String secretKey;
    private String bucket;
    private String nginxTag;
}
