package com.profile.collect.project.utils;

/**
 * Created by gouzhouping on 20-1-8.
 */

public class ButtonUtils {

    private static long lastClickTime;
    private static final long CRITICAL_VALUE = 1000;

    // 防止按钮连续点击
    public static boolean isFastClick() {
        long time = System.currentTimeMillis();
        if (time - lastClickTime < CRITICAL_VALUE) {
            return true;
        }
        lastClickTime = time;
        return false;
    }

}
