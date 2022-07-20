package com.hikvision.nvr.service;

import com.hikvision.nvr.common.AjaxResult;
import com.hikvision.nvr.domain.RequestVo;
import com.hikvision.nvr.domain.SignIn;
import com.hikvision.nvr.domain.VideoFile;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public interface FindVideoFileService {
//    List<VideoFile> playback(RequestVo requestVo) throws InterruptedException;

//    boolean downloadByFileName(String fileName,RequestVo requestVo);

    boolean downloadByFileTime(RequestVo requestVo);

    List<SignIn> getDeviceInformation(RequestVo requestVo);

//    Map getBackUrl(RequestVo requestVo);

    AjaxResult testMinio();


}
