package com.hikvision.nvr;

import com.hikvision.nvr.util.ConvertM3U8Util;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan(basePackages="com.hikvision.nvr")
public class HikvisionApplication {
    public static void main(String[] args) {

//        ConvertM3U8Util convertM3U8Util = new ConvertM3U8Util();
//        convertM3U8Util.processM3U8
        SpringApplication.run(HikvisionApplication.class, args);
    }
}
