package com.hikvision.nvr.util;

import com.hikvision.nvr.common.AjaxResult;
import com.hikvision.nvr.domain.MinioConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;
import ws.schild.jave.*;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;

import com.hikvision.nvr.util.ConvertM3U8Util;

@Slf4j
public class test {
    @Autowired
    private static MinioUtils minioUtils;
    @Autowired
    private MinioConfig minioConfig;

    /**
     * 传视频File对象，返回压缩后File对象信息
     * @param source
     */
    public static File compressionVideo(File source, String newPath) {
        if(source == null){
            return null;
        }
//        String newPath = source.getAbsolutePath().substring(0, source.getAbsolutePath().lastIndexOf("/")).concat(picName);
        File target = new File(newPath);
        try {
            MultimediaObject object = new MultimediaObject(source);
            AudioInfo audioInfo = object.getInfo().getAudio();
            // 根据视频大小来判断是否需要进行压缩,
            int maxSize = 10;
            double mb = Math.ceil(source.length()/ 1048576);
            int second = (int)object.getInfo().getDuration()/1000;
            BigDecimal bd = new BigDecimal(String.format("%.4f", mb/second));
            System.out.println("开始压缩视频了--> 视频每秒平均 "+ bd +" MB ");
            // 视频 > 100MB, 或者每秒 > 0.5 MB 才做压缩， 不需要的话可以把判断去掉
            boolean temp = mb > maxSize || bd.compareTo(new BigDecimal(0.5)) > 0;
            if(temp){
                long time = System.currentTimeMillis();
                //TODO 视频属性设置
                int maxBitRate = 128000;
                int maxSamplingRate = 44100;
                int bitRate = 500000;
                int maxFrameRate = 20;
                int maxWidth = 1280;

                AudioAttributes audio = new AudioAttributes();
                // 设置通用编码格式
                audio.setCodec("aac");

                if (audioInfo!=null){
                    // 设置最大值：比特率越高，清晰度/音质越好
                    // 设置音频比特率,单位:b (比特率越高，清晰度/音质越好，当然文件也就越大 128000 = 182kb)
                    if(audioInfo.getBitRate() > maxBitRate){
                        audio.setBitRate(new Integer(maxBitRate));
                    }

                    // 设置重新编码的音频流中使用的声道数（1 =单声道，2 = 双声道（立体声））。如果未设置任何声道值，则编码器将选择默认值 0。
                    audio.setChannels(audioInfo.getChannels());
                    // 采样率越高声音的还原度越好，文件越大
                    // 设置音频采样率，单位：赫兹 hz
                    // 设置编码时候的音量值，未设置为0,如果256，则音量值不会改变
                    // audio.setVolume(256);
                    if(audioInfo.getSamplingRate() > maxSamplingRate){
                        audio.setSamplingRate(maxSamplingRate);
                    }
                }else {
                    audio.setBitRate(new Integer(maxBitRate));
                    audio.setSamplingRate(maxSamplingRate);
                }

                //TODO 视频编码属性配置
                VideoInfo videoInfo = object.getInfo().getVideo();
                VideoAttributes video = new VideoAttributes();
                video.setCodec("h264");
                //设置音频比特率,单位:b (比特率越高，清晰度/音质越好，当然文件也就越大 800000 = 800kb)
                if(videoInfo.getBitRate() > bitRate){
                    video.setBitRate(bitRate);
                }

                // 视频帧率：15 f / s  帧率越低，效果越差
                // 设置视频帧率（帧率越低，视频会出现断层，越高让人感觉越连续），视频帧率（Frame rate）是用于测量显示帧数的量度。所谓的测量单位为每秒显示帧数(Frames per Second，简：FPS）或“赫兹”（Hz）。
                if(videoInfo.getFrameRate() > maxFrameRate){
                    video.setFrameRate(maxFrameRate);
                }

                // 限制视频宽高
                int width = videoInfo.getSize().getWidth();
                int height = videoInfo.getSize().getHeight();
                if(width > maxWidth){
                    float rat = (float) width / maxWidth;
                    video.setSize(new VideoSize(maxWidth,(int)(height/rat)));
                }

                EncodingAttributes attr = new EncodingAttributes();
                attr.setFormat("mp4");
                attr.setAudioAttributes(audio);
                attr.setVideoAttributes(video);

                // 速度最快的压缩方式， 压缩速度 从快到慢： ultrafast, superfast, veryfast, faster, fast, medium,  slow, slower, veryslow and placebo.
//                attr.setPreset(PresetUtil.VERYFAST);
//                attr.setCrf(27);
//                // 设置线程数
//                attr.setEncodingThreads(Runtime.getRuntime().availableProcessors()/2);

                Encoder encoder = new Encoder();
                encoder.encode(new MultimediaObject(source), target, attr);
                System.out.println("压缩总耗时：" + (System.currentTimeMillis() - time)/1000);
                return target;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
//            if(target.length() > 0){
//                source.delete();
//            }
        }
        return source;
    }

//    public static void main(String[] args) {
//        test vedioTest = new test();
////        下载视频->（是否需要解码）压缩视频->视频转m3u8->上传到minio
//        /*压缩视频*/
//        File source = new File("D:\\huigong\\fileName.mp4");
//        File test1 = new File("D:\\huigong\\test1.mp4");
//        vedioTest.compressionVideo(source,"D:\\huigong\\test1.mp4");
//        /*视频转m3u8*/
////        ConvertM3U8Util convertM3U8Util = new ConvertM3U8Util();
////        convertM3U8Util.
//        /*上传minio*/
//
//
//    }


    /**
     * 文件转base64
     *
     * @param filePath
     * @return
     */
    public static String encryptToBase64(String filePath) {
        if (filePath == null) {
            return null;
        }
        try {
            byte[] b = Files.readAllBytes(Paths.get(filePath));
            return Base64.getEncoder().encodeToString(b);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    /*上传到minio*/

    public static void main(String[] args) {
        String filePath = "D:\\huigong";
        File file = new File(filePath);
        File[] files = file.listFiles(pathname -> {
            if (pathname.isFile())
                return true;
            else
                return false;
        });
        log.info(String.valueOf(files));
        for (int i = 0;i< files.length;i++){
            String f = files[i].toString();
            log.info("文件全路径名:"+f);
            String fileNameShort = f.substring(f.lastIndexOf("\\") + 1);
            String base64 = encryptToBase64(f);
            if (f.contains(".m3u8")){
                base64= "data:text/m3u8;base64,"+base64;
            }else if (f.contains(".ts")){
                base64= "data:video/mp2t;base64,"+base64;
            }
            MultipartFile mfile = BASE64DecodedMultipartFile.base64ToMultipart(base64);
            AjaxResult ajax =  minioUtils.uploadFile("P00012", "m3u8", mfile,fileNameShort);
            System.out.println("ajax==>"+ajax);


        }

    }
}
