package org.wallpaper.sdk;

/**
 * 计算机事件类，负责处理与计算机相关的渲染事件。
 *
 * @author tzdwindows 7
 */
public class ComputerEvents {

    /**
     * 事件类型枚举，描述不同的渲染事件。
     */
    public enum EventTyp {
        /**
         * 桌面渲染
         */
        Desktop,

        /**
         * 屏幕渲染
         */
        Screen,

        /**
         * 任务栏渲染
         */
        Taskbar,

        /**
         * 文件管理器窗口渲染
         */
        ExplorerWindow,

        /**
         * 桌面渲染，但在底层循环执行Render.render方法。
         * 此时render方法得到的是cd句柄，
         * （会单独创建一个窗口，覆盖原有的壁纸）
         */
        Desktop_Loop
    }

    /**
     * 处理计算机事件，并将渲染实例添加到渲染列表中。
     *
     * @param event 事件类型
     * @param render 渲染实例
     * @see #setComputerEvent(EventTyp, Render)
     */
    public static void computerEvent(EventTyp event, Render render) {
        StaticData.RENDER_LIST.add(render);
        setComputerEvent(event, render);
    }

    /**
     * 设置获取窗口句柄事件。
     *
     * @param event 窗口类型
     * @param render 句柄存放的渲染方法（<b/>Render在这里得到的是windows窗口句柄）
     * @see EventTyp
     */
    private static native void setComputerEvent(EventTyp event, Render render);


    //public static void main(String[] args) throws InterruptedException {
    //    System.load("C:\\Users\\Administrator\\source\\repos\\WallpaperOptimization\\x64\\Release\\WallpaperOptimization.dll");
    //    GDI gdi = new GDI();
    //    final long[] a = new long[1];
    //    final long[] b = new long[1];
    //    setComputerEvent(EventTyp.Desktop, new Render(){
    //        @Override
    //        public void render(long hwnd) {
    //            ImageRenderer imageRenderer = new ImageRenderer(new Render());
    //            a[0] = hwnd;
    //            b[0] = gdi.getDC(hwnd);
    //            imageRenderer.renderImage(hwnd, b[0], "G:\\鬼畜素材\\川普\\图片\\a6dc5dd08e8a2a7b9e891d4d7d2cb86d.jpeg");
    //        }
    //    });
    //    gdi.exit(a[0], b[0]);
    //}
}
