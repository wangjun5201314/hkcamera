package com.hikvision.nvr.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hikvision.nvr.common.AjaxResult;
import com.hikvision.nvr.domain.RequestVo;
import com.hikvision.nvr.service.FindVideoFileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RestController
@RequestMapping(value = "/nvr")
public class NvrControcller {

    @Autowired
    private ObjectMapper objectMapper;


    @Qualifier("findVideoFileServiceImpl")
    @Autowired
    private FindVideoFileService downloadVideoService;

//    @RequestMapping(value = "/playback")
//    public AjaxResult playback(@RequestBody RequestVo requestVo) throws InterruptedException {
//        return AjaxResult.success(downloadVideoService.playback(requestVo));
//    }

//    @RequestMapping(value = "/downloadByFileName")
//    public AjaxResult downloadByFileName(@RequestParam String fileName, @RequestBody RequestVo requestVo) {
//        return AjaxResult.success(downloadVideoService.downloadByFileName(fileName, requestVo));
//    }

    @RequestMapping(value = "/downloadByFileTime")
    public AjaxResult downloadByFileTime(@RequestBody RequestVo requestVo) {
        return AjaxResult.success(downloadVideoService.downloadByFileTime(requestVo));
    }

    @RequestMapping(value = "/getDeviceInformation")
    public AjaxResult getDeviceInformation(@RequestBody RequestVo requestVo)  {
        return AjaxResult.success(downloadVideoService.getDeviceInformation(requestVo));
    }

//    @RequestMapping(value = "/getBackUrl")
//    public AjaxResult getBackUrl(@RequestBody RequestVo requestVo) {
//        requestVo.getPlayBack().setUrlType(1);
//        Map map = downloadVideoService.getBackUrl(requestVo);
//        String value = (String) map.get("msg");
//        if (value.startsWith("rtsp:")) {
//            return AjaxResult.success(map);
//        }
//        return AjaxResult.error();
//    }

//    @RequestMapping(value = "/getLiveUrl")
//    public AjaxResult getLiveUrl(@RequestBody RequestVo requestVo) throws Exception {
//        requestVo.getPlayBack().setUrlType(0);
//        Map map = downloadVideoService.getBackUrl(requestVo);
//        String value = (String) map.get("msg");
//        if (value.startsWith("rtsp:")) {
//            return AjaxResult.success(map);
//        }
//        return AjaxResult.error(objectMapper.writeValueAsString(map));
//    }


    @RequestMapping(value = "/testMinio")
    public AjaxResult testMinio(){
        return  downloadVideoService.testMinio();
    }


}