package org.wallpaper.sdk.gdi;

/**
 * @author tzdwindows 7
 */
public interface Lparam {
    /**
     * 枚举窗口回调函数
     * @param hwnd 窗口句柄
     * @param lparam 传输的参数
     * @return 当返回true继续枚举，返回false停止枚举
     */
    boolean enumWindowsProc(long hwnd, long lparam);
}
