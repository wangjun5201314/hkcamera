package com.hikvision.nvr;

import com.hikvision.nvr.domain.RequestVo;
import com.hikvision.nvr.service.FindVideoFileService;
import com.sun.jna.NativeLong;
import com.sun.jna.ptr.IntByReference;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class HikvisionApplicationTests {
    private FindVideoFileService findVideoFileService;
    private RequestVo RequestVo;

    @Test
    void contextLoads() {
//
//        System.out.println(findVideoFileService.getDeviceInformation());

    }
    /**
     * 按照文件下载
     *
     * @param fileName
     * @param requestVo
     * @return
     */
//    @Override
//    public boolean downloadByFileNmae(String fileName, RequestVo requestVo) {
//        /*初始化用户*/
//        NativeLong nativeLong = userId(requestVo.getSignIn());
//        /*初始化下载值*/
//        m_lDownloadHandle = new NativeLong(-1);
//        if (m_lDownloadHandle.intValue() == -1) {
//            /*根据输入的文件名查找文件*/
//            m_lDownloadHandle = hCNetSDK.NET_DVR_GetFileByName(nativeLong, fileName, "D:\\fileNme.3GP");
//            if (m_lDownloadHandle.intValue() >= 0) {
//                /*下载文件*/
//                boolean downloadFlag = hCNetSDK.NET_DVR_PlayBackControl(m_lDownloadHandle, NET_DVR_PLAYSTART, 0, null);
//                int tmp = -1;
//                IntByReference pos = new IntByReference();
//                while (true) {
//                    boolean backFlag = hCNetSDK.NET_DVR_PlayBackControl(m_lDownloadHandle, NET_DVR_PLAYGETPOS, 0, pos);
//                    /*防止单个线程死循环*/
//                    if (!backFlag) {
//                        return downloadFlag;
//                    }
//                    int produce = pos.getValue();
//                    /*获取下载进度*/
//                    if ((produce % 10) == 0 && tmp != produce) {
//                        tmp = produce;
//                        log.info("视频下载进度：{}", produce + "%");
//                    }
//                    /*下载成功*/
//                    if (produce == 100) {
//                        hCNetSDK.NET_DVR_StopGetFile(m_lDownloadHandle);
//                        m_lDownloadHandle.setValue(-1);
//                        /*退出录像机*/
//                        hCNetSDK.NET_DVR_Logout(lUserID);
//                        log.info("退出状态：{}", hCNetSDK.NET_DVR_GetLastError());
//                        return true;
//                    }
//                    /*下载失败*/
//                    if (produce > 100) {
//                        hCNetSDK.NET_DVR_StopGetFile(m_lDownloadHandle);
//                        m_lDownloadHandle.setValue(-1);
//                        log.warn("由于网络原因或NVR较忙,下载异常终止!错误原因：{}", hCNetSDK.NET_DVR_GetLastError());
//                        hCNetSDK.NET_DVR_Logout(lUserID);
//                        return false;
//                    }
//                }
//            } else {
//                log.info("视频下载失败！失败原因：{}", hCNetSDK.NET_DVR_GetLastError());
//                return false;
//            }
//        }
//        return true;
//    }

}
