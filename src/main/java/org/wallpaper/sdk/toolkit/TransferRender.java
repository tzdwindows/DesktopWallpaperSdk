package org.wallpaper.sdk.toolkit;

import org.wallpaper.sdk.gdi.GDI;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * 将渲染转换到Windows屏幕
 * @author tzdwindows 7
 */
public class TransferRender {
    private final boolean stretch;

    public TransferRender(){
        stretch = true;
    }

    /**
     * 控制拉伸启动
     * @param stretch 是否拉伸
     */
    public TransferRender(boolean stretch) {
        this.stretch = stretch;
    }

    /**
     * 将BufferedImage渲染到窗口(会将图片拉伸到width，和height大小)
     *
     * @param gdi    GDI工具类
     * @param hdc    设备上下文句柄
     * @param image  包含文字的BufferedImage
     * @param width  窗口宽度
     * @param height 窗口高度
     */
    public void drawImageToWindow(GDI gdi, long hdc, BufferedImage image, int width, int height) {
        long hBitmap = gdi.createCompatibleBitmap(hdc, width, height);
        long hdcMem = gdi.createCompatibleDC(hdc);
        gdi.selectObject(hdcMem, hBitmap);

        int[] rgbArray;
        if (stretch) {
            BufferedImage resizedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2d = resizedImage.createGraphics();
            g2d.drawImage(image, 0, 0, width, height, null);
            g2d.dispose();
            rgbArray = new int[width * height];
            resizedImage.getRGB(0, 0, width, height, rgbArray, 0, width);
        } else {
            int drawWidth = image.getWidth();
            int drawHeight = image.getHeight();
            rgbArray = new int[width * height];
            BufferedImage centeredImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2d = centeredImage.createGraphics();
            g2d.setColor(new Color(0, 0, 0, 0));
            g2d.fillRect(0, 0, width, height);
            int x = (width - drawWidth) / 2;
            int y = (height - drawHeight) / 2;
            g2d.drawImage(image, x, y, null);
            g2d.dispose();
            centeredImage.getRGB(0, 0, width, height, rgbArray, 0, width);
        }
        setDIBits(gdi, hdcMem, rgbArray, width, height);
        gdi.bitBlt(hdc, 0, 0, width, height, hdcMem, 0, 0, GDI.ROP_SRCCOPY);
        gdi.deleteObject(hBitmap);
        gdi.deleteDC(hdcMem);
    }


    /**
     * 在指定的 (x, y) 坐标处绘制图像，并根据 stretch 判断是否拉伸图像
     *
     * @param gdi    GDI工具类
     * @param hdc    设备上下文句柄
     * @param image  包含文字的BufferedImage
     * @param width  绘制的宽度
     * @param height 绘制的高度
     * @param x      X坐标
     * @param y      Y坐标
     */
    public void drawImageAtXYZ(GDI gdi, long hdc, BufferedImage image, int width, int height, int x, int y) {
        long hBitmap = gdi.createCompatibleBitmap(hdc, width, height);
        long hdcMem = gdi.createCompatibleDC(hdc);
        gdi.selectObject(hdcMem, hBitmap);

        int[] rgbArray;
        if (stretch) {
            BufferedImage resizedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2d = resizedImage.createGraphics();
            g2d.drawImage(image, 0, 0, width, height, null);
            g2d.dispose();
            rgbArray = new int[width * height];
            resizedImage.getRGB(0, 0, width, height, rgbArray, 0, width);
        } else {
            int drawWidth = image.getWidth();
            int drawHeight = image.getHeight();
            rgbArray = new int[width * height];
            BufferedImage centeredImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2d = centeredImage.createGraphics();
            g2d.setColor(new Color(0, 0, 0, 0));
            g2d.fillRect(0, 0, width, height);
            int xOffset = (width - drawWidth) / 2;
            int yOffset = (height - drawHeight) / 2;
            g2d.drawImage(image, xOffset, yOffset, null);
            g2d.dispose();
            centeredImage.getRGB(0, 0, width, height, rgbArray, 0, width);
        }
        setDIBits(gdi, hdcMem, rgbArray, width, height);
        gdi.bitBlt(hdc, x, y, width, height, hdcMem, 0, 0, GDI.ROP_SRCCOPY);
        gdi.deleteObject(hBitmap);
        gdi.deleteDC(hdcMem);
    }


    /**
     * 将RGB数组数据设置到DIB中
     *
     * @param gdi      GDI工具类
     * @param hdcMem   内存设备上下文句柄
     * @param rgbArray 要绘制的RGB数组
     * @param width    图像宽度
     * @param height   图像高度
     */
    private static void setDIBits(GDI gdi, long hdcMem, int[] rgbArray, int width, int height) {
        GDI.BITMAPINFOHEADER bmiHeader = new GDI.BITMAPINFOHEADER();
        bmiHeader.biSize = 40;
        bmiHeader.biWidth = width;
        bmiHeader.biHeight = -height;
        bmiHeader.biPlanes = 1;
        bmiHeader.biBitCount = 32;
        bmiHeader.biCompression = 0;
        bmiHeader.biSizeImage = width * height * 4;
        bmiHeader.biXPelsPerMeter = 0;
        bmiHeader.biYPelsPerMeter = 0;
        bmiHeader.biClrUsed = 0;
        bmiHeader.biClrImportant = 0;
        gdi.setDIBitsToDevice(hdcMem, 0, 0, width, height, 0, 0, 0, height, rgbArray, bmiHeader, 0);
    }
}
