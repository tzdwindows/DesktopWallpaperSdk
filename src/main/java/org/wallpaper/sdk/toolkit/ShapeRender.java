package org.wallpaper.sdk.toolkit;


import org.wallpaper.sdk.Render;
import org.wallpaper.sdk.gdi.GDI;

import java.awt.*;
import java.awt.image.BufferedImage;


/**
 * 各种基本形状的绘制
 * <b>使用此类可以在指定窗口上绘制基本的几何形状</b>
 *
 * 支持绘制矩形、圆形和线段等基本形状。
 * 可设置颜色、线条粗细以及阴影效果。
 *
 * @author tzdwindows 7
 */
public class ShapeRender {

    private final GDI gdi;
    private final Render renderer;
    private final Color shadowColor;

    /**
     * 创建 ShapeRender 实例
     *
     * @param renderer 渲染器，在每次绘制完成后执行
     * @param shadowColor 阴影颜色
     */
    public ShapeRender(Render renderer, Color shadowColor) {
        this.gdi = new GDI();
        this.renderer = renderer;
        this.shadowColor = shadowColor;
    }

    /**
     * 在指定窗口上绘制矩形
     *
     * @param hwnd 窗口句柄
     * @param hdc 设备上下文句柄
     * @param x 矩形左上角的横坐标
     * @param y 矩形左上角的纵坐标
     * @param width 矩形的宽度
     * @param height 矩形的高度
     * @param color 矩形的颜色
     * @param shadow 是否添加阴影效果
     */
    public void drawRectangle(long hwnd, long hdc, int x, int y, int width, int height, Color color, boolean shadow) {
        if (hdc == 0) {
            System.err.println("无法获取窗口的设备上下文");
            return;
        }
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        drawRectangleOnImage(image, x, y, width, height, color, shadow);
        new TransferRender().drawImageToWindow(gdi,hdc, image, width, height);
        renderer.render(hdc);
        gdi.releaseDC(hwnd, hdc);
    }

    /**
     * 在指定窗口上绘制圆形
     *
     * @param hwnd 窗口句柄
     * @param hdc 设备上下文句柄
     * @param centerX 圆心的横坐标
     * @param centerY 圆心的纵坐标
     * @param radius 圆的半径
     * @param color 圆的颜色
     * @param shadow 是否添加阴影效果
     */
    public void drawCircle(long hwnd, long hdc, int centerX, int centerY, int radius, Color color, boolean shadow) {
        int diameter = 2 * radius;
        BufferedImage image = new BufferedImage(diameter, diameter, BufferedImage.TYPE_INT_ARGB);
        drawCircleOnImage(image, centerX, centerY, radius, color, shadow);
        new TransferRender().drawImageToWindow(gdi,hdc, image, diameter, diameter);
        renderer.render(hdc);
        gdi.releaseDC(hwnd, hdc);
    }

    /**
     * 在指定窗口上绘制线段
     *
     * @param hwnd 窗口句柄
     * @param hdc 设备上下文句柄
     * @param x1 线段起点的横坐标
     * @param y1 线段起点的纵坐标
     * @param x2 线段终点的横坐标
     * @param y2 线段终点的纵坐标
     * @param color 线段的颜色
     * @param shadow 是否添加阴影效果
     */
    public void drawLine(long hwnd, long hdc, int x1, int y1, int x2, int y2, Color color, boolean shadow) {
        int width = Math.abs(x2 - x1);
        int height = Math.abs(y2 - y1);
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        drawLineOnImage(image, x1, y1, x2, y2, color, shadow);
        new TransferRender().drawImageToWindow(gdi,hdc, image, width, height);
        renderer.render(hdc);
        gdi.releaseDC(hwnd, hdc);
    }

    private void drawRectangleOnImage(BufferedImage image, int x, int y, int width, int height, Color color, boolean shadow) {
        Graphics2D g2d = image.createGraphics();
        g2d.setColor(shadow ? shadowColor : color);
        if (shadow) {
            g2d.fillRect(x + 2, y + 2, width, height);
        }
        g2d.setColor(color);
        g2d.fillRect(x, y, width, height);
        g2d.dispose();
    }

    private void drawCircleOnImage(BufferedImage image, int centerX, int centerY, int radius, Color color, boolean shadow) {
        Graphics2D g2d = image.createGraphics();
        int diameter = 2 * radius;
        g2d.setColor(shadow ? shadowColor : color);
        if (shadow) {
            g2d.fillOval(centerX - radius + 2, centerY - radius + 2, diameter, diameter);
        }
        g2d.setColor(color);
        g2d.fillOval(centerX - radius, centerY - radius, diameter, diameter);
        g2d.dispose();
    }

    private void drawLineOnImage(BufferedImage image, int x1, int y1, int x2, int y2, Color color, boolean shadow) {
        Graphics2D g2d = image.createGraphics();
        g2d.setColor(shadow ? shadowColor : color);
        if (shadow) {
            g2d.drawLine(x1 + 2, y1 + 2, x2 + 2, y2 + 2);
        }
        g2d.setColor(color);
        g2d.drawLine(x1, y1, x2, y2);
        g2d.dispose();
    }


}
