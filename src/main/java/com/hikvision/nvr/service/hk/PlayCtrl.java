package com.hikvision.nvr.service.hk;

import com.hikvision.nvr.util.CommonKit;
import com.sun.jna.Native;
import com.sun.jna.win32.StdCallLibrary;

public interface PlayCtrl extends StdCallLibrary {
    /*加载播放DLL*/
    PlayCtrl INSTANCE = (PlayCtrl) Native.loadLibrary(CommonKit.getWebPath()+"PlayCtrl.dll", PlayCtrl.class);
//    HCNetSDK INSTANCE = (HCNetSDK) Native.loadLibrary("HCNetSDK", HCNetSDK.class);
}
