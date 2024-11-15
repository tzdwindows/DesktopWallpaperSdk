package org.wallpaper.sdk.gdi;

/**
 * 得到窗口的各种消息
 * @author tzdwindows 7
 */
public class WindowMessages {

    /**
     * 获取窗口的宽度
     * @param hwnd 窗口句柄
     * @return 窗口的宽度
     */
    public native int getWindowWidth(long hwnd);

    /**
     * 获取窗口的高度
     * @param hwnd 窗口句柄
     * @return 窗口的高度
     */
    public native int getWindowHeight(long hwnd);

    /**
     * 获取窗口的X坐标
     * @param hwnd 窗口句柄
     * @return 窗口的X坐标
     */
    public native int getWindowX(long hwnd);

    /**
     * 获取窗口的Y坐标
     * @param hwnd 窗口句柄
     * @return 窗口的Y坐标
     */
    public native int getWindowY(long hwnd);

    /**
     * 获取窗口的大小
     * @param hwnd 窗口句柄
     * @return 包含宽度和高度的数组
     */
    public native int[] getWindowSize(long hwnd);

    /**
     * 获取窗口的标题
     * @param hwnd 窗口句柄
     * @return 窗口的标题
     */
    public native String getWindowTitle(long hwnd);

    /**
     * 获取窗口的优先级
     * @param hwnd 窗口句柄
     * @return 窗口的优先级
     */
    public native int getWindowPriority(long hwnd);

    /**
     * 获取窗口的图标
     * @param hwnd 窗口句柄
     * @return 窗口的图标句柄
     */
    public native long getWindowIcon(long hwnd);

    /**
     * 枚举窗口
     * @param lpEnumFunc 回调函数
     * @param lParam 要传递给回调函数的应用程序定义值。
     * @return 如果该函数成功，则返回值为非零值。
     */
    public native boolean enumWindows(Lparam lpEnumFunc, long lParam);

    /**
     * 获取窗口的类名
     * @param hwnd 窗口句柄
     * @return 窗口的类名
     */
    public native String getWindowClass(long hwnd);

    /**
     * 调用原生方法以请求重绘指定窗口的矩形区域。
     *
     * @param hWnd 窗口句柄
     * @param rect 指定的矩形区域
     * @param bErase 是否擦除背景
     */
    public void invalidateRect(long hWnd,RECT rect, boolean bErase){
        if (rect == null){
            forceUpdateWindow(hWnd);
            return;
        }
        invalidateRect(hWnd, rect.left(), rect.top(), rect.right(), rect.bottom(), bErase);
    }

    /**
     * 声明的本地方法，用于调用 Windows API 进行重绘。
     *
     * @param hWnd 窗口句柄
     * @param left 矩形左边缘坐标
     * @param top 矩形上边缘坐标
     * @param right 矩形右边缘坐标
     * @param bottom 矩形下边缘坐标
     * @param bErase 是否擦除背景
     */
    @SuppressWarnings("unused")
    public native void invalidateRect(long hWnd, int left, int top, int right, int bottom, boolean bErase);

    /**
     * 强制更新窗口，确保窗口是最新的状态。
     *
     * @param hWnd 窗口句柄
     */
    public native void forceUpdateWindow(long hWnd);

    /**
     * 更新窗口，确保窗口是最新的状态。
     *
     * @param hWnd 窗口句柄
     */
    public native void updateWindow(long hWnd);
}
