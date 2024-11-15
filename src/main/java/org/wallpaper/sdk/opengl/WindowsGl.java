package org.wallpaper.sdk.opengl;

import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.wallpaper.sdk.ComputerEvents;
import org.wallpaper.sdk.Render;
import org.wallpaper.sdk.gdi.GDI;
import org.wallpaper.sdk.gdi.WindowMessages;

/**
 * 将window的窗口句柄转化为opengl句柄
 *
 * @author tzdwindows 7
 */
public class WindowsGl {

    /**
     * 将窗口句柄转化为 OpenGL 上下文句柄。
     * <p>
     * 此方法接受一个 Windows 窗口的句柄（`hwnd`），并通过 OpenGL 的库将其转换为可用于渲染的 OpenGL 上下文句柄。
     * 在 OpenGL 渲染中，`glfwWindow` 通常负责将窗口句柄转换为一个 OpenGL 上下文（`RC`，Rendering Context），
     * 这个上下文用于在指定的窗口中进行图形渲染。
     *
     * @param hwnd Windows 窗口句柄，指定目标窗口，用于渲染。
     *             该句柄通常通过 Windows API 创建并标识窗口的唯一实例。
     * @return 转换后的 OpenGL 上下文句柄（`hrc`），用于后续 OpenGL 渲染操作的上下文。
     *         返回值为 0 表示转换失败或上下文创建失败。
     */
    public static native long glfwWindow(long hwnd);

    /**
     * 设置当前线程的 OpenGL 渲染上下文（RC）。
     * <p>
     * 此方法将指定的设备上下文（`hdc`）和渲染上下文（`hrc`）关联到当前线程，
     * 使当前线程能够在该上下文中进行 OpenGL 渲染操作。
     * `glMakeCurrent` 通常是调用 OpenGL 函数的前提条件，确保所有的 OpenGL 操作在正确的上下文中执行。
     * 绑定成功后，OpenGL 指令将在指定的上下文中执行。
     *
     * @param hdc 窗口的设备上下文句柄，表示指定窗口的绘图表面。设备上下文（DC）用于管理和绘制窗口的内容。
     * @param hrc OpenGL 渲染上下文句柄，通过 `glfwWindow` 方法获得，包含与特定窗口关联的 OpenGL 状态。
     * @return `true` 表示上下文设置成功，`false` 表示设置失败（例如上下文无效或线程上下文已绑定其他上下文）。
     *         设置失败时，后续的 OpenGL 调用将无法生效。
     */
    public static native boolean glMakeCurrent(long hdc, long hrc);

    /**
     * 切换前后台缓冲区（双缓冲技术）。
     * <p>
     * 在双缓冲渲染中，通常使用前台缓冲区显示内容，后台缓冲区用于绘制新内容。
     * `swapBuffers` 方法将后台缓冲区的内容切换到前台缓冲区，刷新窗口显示，以避免绘制过程中的闪烁。
     * 此操作通常在每一帧渲染结束后调用，以显示完整的画面。
     *
     * @param hdc 窗口的设备上下文句柄（DC），表示执行缓冲区切换的目标窗口。
     * @return `true` 表示缓冲区切换成功，`false` 表示切换失败。
     *         切换失败时可能是由于设备上下文无效或上下文未与当前线程绑定。
     */
    public static native boolean swapBuffers(long hdc);
    
    /**
     * 测试
     */
    public static void main(String[] args) {
        System.loadLibrary("WallpaperOptimization");
        final long[] hwnd2 = new long[1];
        ComputerEvents.computerEvent(ComputerEvents.EventTyp.Desktop, new Render() {
            @Override
            public void render(long h) {
                hwnd2[0] = h;
            }
        });
        System.out.println(WindowsGl.glfwWindow(hwnd2[0]));
        long hwnd = hwnd2[0],radius = 100;
        long hdc = new GDI().getDC(hwnd);
        long hrc = WindowsGl.glfwWindow(hwnd);
        WindowMessages windowMessages = new WindowMessages();
        if (WindowsGl.glMakeCurrent(hdc, hrc)) {
            GL.createCapabilities();
            GL11.glViewport(0, 0, windowMessages.getWindowWidth(hwnd), windowMessages.getWindowHeight(hwnd));
            GL11.glMatrixMode(GL11.GL_PROJECTION);
            GL11.glLoadIdentity();
            GL11.glOrtho(-400, 400, -300, 300, -1, 1);
            GL11.glMatrixMode(GL11.GL_MODELVIEW);
            GL11.glLoadIdentity();
            GL11.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
            GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
            GL11.glPushMatrix();
            GL11.glColor3f(0.0f, 0.5f, 1.0f);
            GL11.glBegin(GL11.GL_POLYGON);
            int segments = 100;
            for (int i = 0; i < segments; i++) {
                double theta = 2.0 * Math.PI * i / segments;
                float x = radius * (float)Math.cos(theta);
                float y = radius * (float)Math.sin(theta);
                GL11.glVertex2f(x, y);
            }
            GL11.glEnd();
            GL11.glPopMatrix();
            WindowsGl.swapBuffers(hdc);
            WindowsGl.glMakeCurrent(0, 0);
        }
    }
}
