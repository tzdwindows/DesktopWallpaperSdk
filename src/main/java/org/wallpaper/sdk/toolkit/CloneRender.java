package org.wallpaper.sdk.toolkit;

import org.wallpaper.sdk.gdi.GDI;
import org.wallpaper.sdk.gdi.WindowMessages;

/**
 * 克隆指定窗口的渲染到指定窗口
 * @author tzdwindows 7
 */
public class CloneRender {
    private final long cloneHwnd;
    private final boolean stretch;

    public CloneRender(long cloneHwnd, boolean stretch) {
        this.cloneHwnd = cloneHwnd;
        this.stretch = stretch;
    }

    public void render(long hwnd) {
        GDI gdi = new GDI();
        WindowMessages windowMessages = new WindowMessages();
        long hdcSrc = gdi.getWindowDC(hwnd);
        long hdcDest = gdi.getWindowDC(cloneHwnd);
        if (hdcSrc != 0 && hdcDest != 0) {
            int srcWidth =  windowMessages.getWindowWidth(hwnd);
            int srcHeight = windowMessages.getWindowHeight(hwnd);
            int destWidth = windowMessages.getWindowWidth(cloneHwnd);
            int destHeight = windowMessages.getWindowHeight(cloneHwnd);
            boolean result;
            if (stretch) {
                result = gdi.stretchBlt(hdcDest, 0, 0, destWidth, destHeight, hdcSrc, 0, 0, srcWidth, srcHeight, GDI.SRCCOPY);
            } else {
                result = gdi.bitBlt(hdcDest, 0, 0, srcWidth, srcHeight, hdcSrc, 0, 0, GDI.SRCCOPY);
            }
            if (!result) {
                System.err.println("Failed to clone window rendering");
            }
            gdi.releaseDC(hwnd, hdcSrc);
            gdi.releaseDC(cloneHwnd, hdcDest);
        }
    }

}
