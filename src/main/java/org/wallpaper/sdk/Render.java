package org.wallpaper.sdk;

/**
 * 渲染类，负责处理桌面壁纸渲染逻辑。
 *
 * @author tzdwindows 7
 */
public class Render {
    private long hwnd; // 窗口句柄

    /**
     * 在Desktop_Loop模式下，render方法得到的是hdc，此时hwnd会被赋值；
     * 在其他条件下，hwnd默认为0。
     *
     * @param hwnd 窗口句柄（有时可能是桌面的cd对象）
     * @see #setHwnd(long)
     * @see #getHwnd()
     */
    public void render(long hwnd) {
        // 渲染逻辑待实现
    }

    /**
     * 设置窗口句柄。
     *
     * @param hwnd 窗口句柄
     * @see #render(long)
     */
    public void setHwnd(long hwnd) {
        this.hwnd = hwnd;
    }

    /**
     * 获取当前的窗口句柄。
     *
     * @return 当前的窗口句柄
     * @see #setHwnd(long)
     */
    public long getHwnd() {
        return hwnd;
    }
}
