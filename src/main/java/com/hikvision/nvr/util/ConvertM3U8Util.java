package com.hikvision.nvr.util;


import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Slf4j
@Component
public class ConvertM3U8Util {

    private final String ffmpegPath="D:/otherEnvironment/ffmpeg-n5.0-latest-win64-gpl-shared-5.0/ffmpeg-n5.0-latest-win64-gpl-shared-5.0/bin/ffmpeg.exe";
    // 设置每一个ts的时间
    private String time="10";
    //设置视频分辨率
    private String resolution = "1280*720";

    public boolean convertOss(String folderUrl,String fileName){
        if (!checkfile(folderUrl + fileName)) {
            log.info("文件不存在");
            return false;
        }
        String suffix= StringUtils.substringAfter(fileName,".");
        String substringBefore = StringUtils.substringBefore(fileName, ".");
        if (!validFileType(suffix)){
            return false;
        }
        return processM3U8(folderUrl,fileName,substringBefore,resolution,time);
    }

    /*验证上传文件后缀*/
    private boolean validFileType(String type){
        if ("mp4".equals(type)){
            return true;
        }
        return false;
    }

    /*核对是否是文件格式*/
    private boolean checkfile(String path){
        File file = new File(path);
        if (!file.isFile()){
            return false;
        }
        else {
            return true;
        }
    }
/*ffmpeg程序转换m3u8*/
    private boolean processM3U8(String folderUrl,String fileName, String fileFullName) {
        //这里就写入执行语句就可以了
        List commend = new java.util.ArrayList();
        commend.add(ffmpegPath);
        commend.add("-i");
        commend.add(folderUrl+fileName);
        commend.add("-c:v");
        commend.add("libx264");
        commend.add("-hls_time");
        commend.add("20");
        commend.add("-hls_list_size");
        commend.add("0");
        commend.add("-c:a");
        commend.add("aac");
        commend.add("-strict");
        commend.add("-2");
        commend.add("-f");
        commend.add("hls");
        commend.add(folderUrl+ fileFullName +".m3u8");
        try {
            ProcessBuilder builder = new ProcessBuilder();//java
            builder.command(commend);
            Process p = builder.start();
            int i = doWaitFor(p);
            log.info("------>"+i);
            p.destroy();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    private boolean processM3U8(String folderUrl,String fileName, String fileFullName,String resolution,String time){
        //这里就写入执行语句就可以了
        List commend = new java.util.ArrayList();
        commend.add(ffmpegPath);
        commend.add("-i");
        commend.add(folderUrl+fileName);
        commend.add("-profile:v");
        commend.add("baseline");
        commend.add("-level");
        commend.add("3.0");
        commend.add("-s");
        commend.add(resolution);
        commend.add("-start_number");
        commend.add("0");
        commend.add("-hls_time");
        commend.add(time);
        commend.add("-hls_list_size");
        commend.add("0");
        commend.add("-f");
        commend.add("hls");
        commend.add(folderUrl+ fileFullName +".m3u8");
        try {
            //java0
            ProcessBuilder builder = new ProcessBuilder();
            builder.command(commend);
            Process p = builder.start();
            int i = doWaitFor(p);
            System.out.println("------>"+i);
            p.destroy();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    private boolean processM3U8(String ffmpegpath,String folderUrl,String fileName, String fileFullName,String resolution,String time){
        //这里就写入执行语句就可以了
        List commend = new java.util.ArrayList();
        commend.add(ffmpegpath);
        commend.add("-i");//指定输入文件
        commend.add(folderUrl+fileName);
        commend.add("-profile:v");//设置画质类型
        commend.add("baseline");//基本画质
        commend.add("-level");
        commend.add("3.0");
        commend.add("-s");
        commend.add(resolution);
        commend.add("-start_number");
        commend.add("0");
        commend.add("-hls_time");
        commend.add(time);
        commend.add("-hls_list_size");
        commend.add("0");
        commend.add("-f");
        commend.add("hls");
        commend.add(folderUrl+ fileFullName +".m3u8");
        try {
            //java
            ProcessBuilder builder = new ProcessBuilder();
            builder.command(commend);
            Process p = builder.start();
            int i = doWaitFor(p);
            log.info("------>"+i);
            p.destroy();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }



    /*监听ffmeg运行过程*/
    public int doWaitFor(Process p) {
        InputStream in = null;
        InputStream err = null;
        int exitValue = -1; // returned to caller when p is finished
        try {
            System.out.println("comeing");
            in = p.getInputStream();
            err = p.getErrorStream();
            boolean finished = false; // Set to true when p is finished

            while (!finished) {
                try {
                    while (in.available() > 0) {
                        Character c = new Character((char) in.read());
                        System.out.print(c);
                    }
                    while (err.available() > 0) {
                        Character c = new Character((char) err.read());
                        System.out.print(c);
                    }

                    exitValue = p.exitValue();
                    finished = true;

                } catch (IllegalThreadStateException e) {
                    Thread.currentThread().sleep(500);
                }
            }
        } catch (Exception e) {
//            System.err.println("doWaitFor();: unexpected exception - "
//                    + e.getMessage());
            log.error("doWaitFor();: unexpected exception - "
                    + e.getMessage());
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
            if (err != null) {
                try {
                    err.close();
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
        return exitValue;
    }

    public static void main(String[] args) {
        ConvertM3U8Util convertM3U8Util = new ConvertM3U8Util();
        boolean b = convertM3U8Util.processM3U8("D:/otherEnvironment/ffmpeg-n5.0-latest-win64-gpl-shared-5.0/ffmpeg-n5.0-latest-win64-gpl-shared-5.0/bin/ffmpeg.exe", "D:/huigong/", "fileName.mp4", "video_s", "1280*720", "10");
        log.info(String.valueOf(b));
    }


}
