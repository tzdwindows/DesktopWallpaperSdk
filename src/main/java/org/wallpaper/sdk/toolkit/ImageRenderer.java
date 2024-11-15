package org.wallpaper.sdk.toolkit;

import org.wallpaper.sdk.Render;
import org.wallpaper.sdk.gdi.GDI;
import org.wallpaper.sdk.gdi.WindowMessages;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * 一个渲染普通图片的工具
 * <b>使用此类将图像全屏渲染到指定窗口。</b>
 *
 * @author tzdwindows 7
 */
public class ImageRenderer {

    private final WindowMessages windowMessages;
    private final GDI gdi;
    private final Render renderer;

    /**
     * 传输的Render会在每次绘制完成后执行 <br>
     * <b>Render在这里得到的是hdc句柄</b>
     * @param renderer 渲染器，在每次绘制完成后执行
     */
    public ImageRenderer(Render renderer) {
        windowMessages = new WindowMessages();
        gdi = new GDI();
        this.renderer = renderer;
    }

    /**
     * 在指定窗口上全屏渲染普通图片
     * @param hwnd 窗口句柄
     * @param imagePath 图片文件路径
     */
    public void renderImage(long hwnd, long hdc,String imagePath) {
        BufferedImage image = loadImage(imagePath);
        if (image == null) {
            System.err.println("无法加载图片");
            return;
        }

        if (hdc == 0) {
            System.err.println("无法获取窗口的设备上下文");
            return;
        }
        int width = windowMessages.getWindowWidth(hwnd);
        int height = windowMessages.getWindowHeight(hwnd);
        new TransferRender().drawImageToWindow(gdi,hdc, image, width, height);
        renderer.render(hdc);
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        gdi.releaseDC(hwnd, hdc);
    }

    /**
     * 加载图片
     * @param imagePath 图片文件路径
     * @return BufferedImage 对象
     */
    private BufferedImage loadImage(String imagePath) {
        try {
            return ImageIO.read(new File(imagePath));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    ///**
    // * 绘制图片到窗口
    // * @param hdc 设备上下文句柄
    // * @param image 当前的 BufferedImage
    // * @param width 窗口宽度
    // * @param height 窗口高度
    // */
    //private void drawImage(long hdc, BufferedImage image, int width, int height) {
    //    long hBitmap = gdi.createCompatibleBitmap(hdc, width, height);
    //    long hdcMem = gdi.createCompatibleDC(hdc);
    //    gdi.selectObject(hdcMem, hBitmap);
//
    //    for (int y = 0; y < height; y++) {
    //        for (int x = 0; x < width; x++) {
    //            int srcX = x * image.getWidth() / width;
    //            int srcY = y * image.getHeight() / height;
    //            if (srcX < image.getWidth() && srcY < image.getHeight()) {
    //                int rgb = image.getRGB(srcX, srcY);
    //                int red = (rgb >> 16) & 0xFF;
    //                int green = (rgb >> 8) & 0xFF;
    //                int blue = rgb & 0xFF;
    //                int color = (blue << 16) | (green << 8) | red;
    //                gdi.setPixel(hdcMem, x, y, color);
    //            }
    //        }
    //    }
//
    //    gdi.bitBlt(hdc, 0, 0, width, height, hdcMem, 0, 0, GDI.ROP_SRCCOPY);
    //    gdi.deleteObject(hBitmap);
    //    gdi.deleteDC(hdcMem);
    //}
}
