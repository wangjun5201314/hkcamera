package com.hikvision.nvr.util;

public class CommonKit {
    /**
     * 获取DLL文件路径
     * @return
     */
    public static String getWebPath(){
        String path = CommonKit.class.getClassLoader().getResource("").getPath().substring(1);
        System.out.println(path+"dll\\");
//        String path1 = path+"dll\\"+"HCNetSDK.dll";
//D:/360downloads/hikvision-master/hikvision-master/target/classes/dll\
//        System.out.println(path1);
        return path+"dll\\";
    }
}
