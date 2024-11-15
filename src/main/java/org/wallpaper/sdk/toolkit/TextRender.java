package org.wallpaper.sdk.toolkit;

import org.wallpaper.sdk.Render;
import org.wallpaper.sdk.gdi.GDI;
import org.wallpaper.sdk.gdi.WindowMessages;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * 一个渲染文字的工具
 * <b>使用此类将指定文字全屏渲染到指定窗口。</b>
 *
 * @author tzdwindows 7
 */
public class TextRender {

    private final WindowMessages windowMessages;
    private final GDI gdi;
    private final Render renderer;
    private Font font;
    private Color color;
    private Color shadowColor;

    /**
     * 传输的Render会在每次绘制完成后执行 <br>
     * <b>Render在这里得到的是hdc句柄</b>
     *
     * @param renderer 渲染器，在每次绘制完成后执行
     * @param font 渲染字体，如果为 null 则使用默认字体
     * @param color 渲染颜色，如果为 null 则使用默认颜色
     * @param shadowColor 阴影颜色，如果为 null 则使用默认阴影颜色
     */
    public TextRender(Render renderer, Font font, Color color, Color shadowColor) {
        this.windowMessages = new WindowMessages();
        this.gdi = new GDI();
        this.renderer = renderer;
        this.font = (font != null) ? font : new Font("Arial", Font.BOLD, 36);
        this.color = (color != null) ? color : Color.BLACK;
        this.shadowColor = (shadowColor != null) ? shadowColor : Color.red;
    }

    /**
     * 在指定窗口上全屏渲染文字
     * @param hwnd 窗口句柄
     * @param hdc 设备上下文句柄
     * @param text 要渲染的文字
     * @param shadow 是否添加阴影效果
     */
    public void renderText(long hwnd, long hdc, String text, boolean shadow) {
        if (hdc == 0) {
            System.err.println("无法获取窗口的设备上下文");
            return;
        }
        int width = windowMessages.getWindowWidth(hwnd);
        int height = windowMessages.getWindowHeight(hwnd);

        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        drawText(image, text, width, height,shadow);
        new TransferRender().drawImageToWindow(gdi ,hdc, image, width, height);
        renderer.render(hdc);
        gdi.releaseDC(hwnd, hdc);
    }

    /**
     * 动态的设置渲染颜色
     * @param color 要设置的颜色
     */
    public void setColor(Color color) {
        this.color = color;
    }

    /**
     * 动态设置渲染字体
     * @param font 要设置的字体
     */
    public void setFont(Font font) {
        this.font = font;
    }

    public Font getFont() {
        return font;
    }

    /**
     * 动态设置阴影颜色
     * @param shadowColor 阴影颜色
     */
    public void setShadowColor(Color shadowColor) {
        this.shadowColor = shadowColor;
    }

    /**
     * 在指定窗口上渲染文字
     *
     * @param hwnd 窗口句柄
     * @param hdc 设备上下文句柄
     * @param width 渲染区域的宽度
     * @param height 渲染区域的高度
     * @param x 文字渲染的起始横坐标
     * @param y 文字渲染的起始纵坐标
     * @param text 要渲染的文字
     * @param shadow 是否添加阴影效果
     */
    public void renderText(long hwnd, long hdc, int width, int height, int x, int y, String text, boolean shadow) {
        if (hdc == 0) {
            System.err.println("无法获取窗口的设备上下文");
            return;
        }
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        drawText(image, text, x, y, shadow);
        new TransferRender().drawImageToWindow(gdi,hdc, image, width, height);
        renderer.render(hdc);
        gdi.releaseDC(hwnd, hdc);
    }

    /**
     * 在BufferedImage上绘制文字
     *
     * @param image 要绘制的BufferedImage对象
     * @param text 要渲染的文字
     * @param x 文字的起始横坐标
     * @param y 文字的起始纵坐标
     * @param shadow 是否启用阴影效果
     */
    private void drawText(BufferedImage image, String text, int x, int y, boolean shadow) {
        Graphics2D g2d = image.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setFont(font);
        if (shadow) {
            g2d.setColor(shadowColor);
            g2d.drawString(text, x + 2, y + 2);
        }
        g2d.setColor(color);
        g2d.drawString(text, x, y);
        g2d.dispose();
    }
}
