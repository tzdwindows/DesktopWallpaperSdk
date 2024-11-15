package org.wallpaper.sdk.toolkit;

import com.madgag.gif.fmsware.GifDecoder;
import org.wallpaper.sdk.Render;
import org.wallpaper.sdk.gdi.GDI;
import org.wallpaper.sdk.gdi.WindowMessages;

import java.awt.image.BufferedImage;

/**
 * 一个渲染动图的工具
 * @author tzdwindows 7
 */
public class AnimatedGifRenderer {

    private final WindowMessages windowMessages;
    private final GDI gdi;
    private final Render renderer;
    BufferedImage[] frames = null;
    private int width = -1;
    private int height = -1;
    /**
     * 传输的Render会在每一帧gif渲染完后执行 <br>
     * <b/>Render在这里得到的是hdc句柄
     * @param renderer 渲染器，在每一帧渲染完后执行
     */
    public AnimatedGifRenderer(Render renderer) {
        windowMessages = new WindowMessages();
        gdi = new GDI();
        this.renderer = renderer;
    }

    public void renderGif(long hwnd, long hdc, String gifPath) {
        try {
            if (frames == null) {
                frames = loadGifFrames(gifPath);
                if (frames.length == 0) {
                    System.err.println("无法加载动图");
                    return;
                }
            }
            width = windowMessages.getWindowWidth(hwnd);
            height = windowMessages.getWindowHeight(hwnd);
            for (BufferedImage frame : frames) {
                new TransferRender().drawImageToWindow(gdi,hdc, frame, width, height);
                renderer.render(hdc);
                Thread.sleep(100);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 在指定窗口上全屏渲染动图
     * @param hwnd 窗口句柄
     * @param gifPath 动图文件路径
     */
    public void renderGif(long hwnd, String gifPath) {
        try {
            BufferedImage[] frames = loadGifFrames(gifPath);
            if (frames == null || frames.length == 0) {
                System.err.println("无法加载动图");
                return;
            }
            long hdc = gdi.getWindowDC(hwnd);
            if (hdc == 0) {
                System.err.println("无法获取窗口的设备上下文");
                return;
            }
            int width = windowMessages.getWindowWidth(hwnd);
            int height = windowMessages.getWindowHeight(hwnd);
            for (BufferedImage frame : frames) {
                new TransferRender().drawImageToWindow(gdi,hdc, frame, width, height);
                renderer.render(hdc);
                Thread.sleep(100);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 加载GIF帧
     * @param gifPath 动图文件路径
     * @return BufferedImage数组，包含GIF的每一帧
     */
    public static BufferedImage[] loadGifFrames(String gifPath) {
        GifDecoder decoder = new GifDecoder();
        decoder.read(gifPath);

        int frameCount = decoder.getFrameCount();
        BufferedImage[] frames = new BufferedImage[frameCount];

        for (int i = 0; i < frameCount; i++) {
            frames[i] = decoder.getFrame(i);
        }

        return frames;
    }

    ///**
    // * 绘制帧到窗口
    // * @param hdc 设备上下文句柄
    // * @param frame 当前帧的BufferedImage
    // * @param width 窗口宽度
    // * @param height 窗口高度
    // */
    //private void drawFrame(long hdc, BufferedImage frame, int width, int height) {
    //    long hBitmap = gdi.createCompatibleBitmap(hdc, width, height);
    //    long hdcMem = gdi.createCompatibleDC(hdc);
    //    gdi.selectObject(hdcMem, hBitmap);
//
    //    int frameWidth = frame.getWidth();
    //    int frameHeight = frame.getHeight();
//
    //    for (int y = 0; y < frameHeight; y++) {
    //        for (int x = 0; x < frameWidth; x++) {
    //            int rgb = frame.getRGB(x, y);
    //            int color = (rgb & 0x00FFFFFF);
    //            gdi.setPixel(hdcMem, x, y, color);
    //        }
    //    }
//
    //    gdi.bitBlt(hdc, 0, 0, frameWidth, frameHeight, hdcMem, 0, 0, GDI.ROP_SRCCOPY);
    //    gdi.deleteObject(hBitmap);
    //    gdi.deleteDC(hdcMem);
    //}

    //public static void main(String[] args) {
    //    AnimatedGifRenderer renderer = new AnimatedGifRenderer();
    //    long hwnd = 123456; // 替换为目标窗口的句柄
    //    String gifPath = "path/to/your/animated.gif";
    //    renderer.renderGif(hwnd, gifPath);
    //}
}
