package com.example.forcezheng.loadingwebview.utils;

import android.os.Build;

/**
 * @author zhengwang
 * @email zhengwang043@foxmail.com
 * @date 2016/10/8
 */
public class AndroidUtil {

    /**
     * 手机sdk版本号
     *
     * @return
     */
    public static int getAndroidSDKVersion() {
        return Build.VERSION.SDK_INT;
    }
}
