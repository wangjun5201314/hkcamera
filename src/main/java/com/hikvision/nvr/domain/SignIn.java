package com.hikvision.nvr.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

//@Component
//@ConfigurationProperties(prefix = "signin")
@Data
public class SignIn {

    private String ip;//192.168.110.211


    private int port;//8000


    private String userName;//admin


    private String password;//abc12345


    private String isLine;


    private String deviceId;//F759996491


    private String deviceIp;//192.168.110.211


    private String appId;//  用户注册app的id


    private int channelNumber;//33  1
}
