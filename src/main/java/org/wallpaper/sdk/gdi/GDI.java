package org.wallpaper.sdk.gdi;

/**
 * 基于GDI的渲染类
 * 提供了常用的GDI渲染方法，支持位图、文本和几何图形的绘制
 * Native方法通过JNI调用Windows GDI实现
 *
 * @author tzdwindows 7
 */
public class GDI {

    public static final int BLACKNESS = 0x00000042;
    public static final int CAPTUREBLT = 0x40000000;
    public static final int DSTINVERT = 0x00550009;
    public static final int MERGECOPY = 0x00C000CA;
    public static final int MERGEPAINT = 0x00BB0226;
    public static final int NOMIRRORBITMAP = 0x80000000;
    public static final int NOTSRCCOPY = 0x00330008;
    public static final int NOTSRCERASE = 0x001100A6;
    public static final int PATCOPY = 0x00F00021;
    public static final int PATINVERT = 0x005A0049;
    public static final int PATPAINT = 0x00FB0A09;
    public static final int SRCAND = 0x008800C6;
    public static final int SRCCOPY = 0x00CC0020;
    public static final int SRCERASE = 0x00440328;
    public static final int SRCINVERT = 0x00660046;
    public static final int SRCPAINT = 0x00EE0086;
    public static final int WHITENESS = 0x00FF00FF;
    public static final int ROP_SRCCOPY = 0x00CC0020;
    public static final int ROP_DSTINVERT = 0x00550009;
    public static final int ROP_SRCAND = 0x00880006;
    public static final int ROP_SRCINVERT = 0x00660046;
    public static final int ROP_SRCPAINT = 0x00EE0086;


    /**
     * 表示位图信息头的结构体，包含有关位图的详细信息。
     */
    public static class BITMAPINFOHEADER {

        /**
         * 结构的大小（以字节为单位）。
         */
        public int biSize;

        /**
         * 位图的宽度（以像素为单位）。
         */
        public int biWidth;

        /**
         * 位图的高度（以像素为单位）。
         * 注意：如果为负值，则表示位图是由下到上排列的。
         */
        public int biHeight;

        /**
         * 颜色平面数，必须为 1。
         */
        public short biPlanes;

        /**
         * 每个像素的位数（例如，1、4、8、16、24、32）。
         */
        public short biBitCount;

        /**
         * 指定图像的压缩方式。
         * 例如，0 表示不压缩，1 表示 RLE 8 位压缩，2 表示 RLE 4 位压缩，等。
         */
        public int biCompression;

        /**
         * 位图的大小（以字节为单位）。
         * 如果该值为 0，则该值由系统计算。
         */
        public int biSizeImage;

        /**
         * 水平分辨率（像素/米）。
         */
        public int biXPelsPerMeter;

        /**
         * 垂直分辨率（像素/米）。
         */
        public int biYPelsPerMeter;

        /**
         * 位图中使用的颜色数。如果为 0，则表示使用所有可能的颜色。
         */
        public int biClrUsed;

        /**
         * 重要颜色的数目。如果为 0，则表示所有的颜色都是重要的。
         */
        public int biClrImportant;
    }

    
    /**
     * 获取设备上下文（DC）
     * @param hwnd 窗口句柄
     * @return 设备上下文的句柄
     * C++: GetDC(HWND hwnd);
     */
    public native long getDC(long hwnd);

    public native long beginPaint(long hwnd);

    public native void endPaint(long hwnd);

    /**
     * 获取窗口的设备上下文（DC）
     * @param hwnd 窗口句柄
     * @return 窗口的设备上下文的句柄
     * C++: GetWindowDC(HWND hwnd);
     */
    public native long getWindowDC(long hwnd);

    /**
     * 释放设备上下文（DC）
     * @param hwnd 窗口句柄
     * @param hdc 设备上下文的句柄
     * @return 是否成功释放设备上下文
     * C++: ReleaseDC(HWND hwnd, HDC hdc);
     */
    public native boolean releaseDC(long hwnd, long hdc);

    /**
     * 将源设备上下文的位图复制到目标设备上下文
     * @param hdcDest 目标设备上下文的句柄
     * @param xDest 目标矩形的X坐标
     * @param yDest 目标矩形的Y坐标
     * @param width 目标矩形的宽度
     * @param height 目标矩形的高度
     * @param hdcSrc 源设备上下文的句柄
     * @param xSrc 源矩形的X坐标
     * @param ySrc 源矩形的Y坐标
     * @param dwRop 指定图像的处理方式
     * @return 是否成功执行操作
     * C++: BitBlt(HDC hdcDest, int xDest, int yDest, int width, int height, HDC hdcSrc, int xSrc, int ySrc, DWORD dwRop);
     */
    public native boolean bitBlt(long hdcDest, int xDest, int yDest, int width, int height, long hdcSrc, int xSrc, int ySrc, long dwRop);

    /****
     * 在指定的设备上下文中填充一个矩形。
     *
     * @param hDC    设备上下文的句柄。
     * @param left   矩形的左坐标。
     * @param top    矩形的上坐标。
     * @param right  矩形的右坐标。
     * @param bottom 矩形的下坐标。
     */
    public native void fillRectangle(long hDC, int left, int top, int right, int bottom);

    /**
     * 在指定的设备上下文中将位图数据绘制到设备上。
     *
     * @param hdc       设备上下文的句柄。
     * @param xDest    目标矩形的左上角的 x 坐标。
     * @param yDest    目标矩形的左上角的 y 坐标。
     * @param width     目标矩形的宽度。
     * @param height    目标矩形的高度。
     * @param xSrc     源位图的左上角的 x 坐标。
     * @param ySrc     源位图的左上角的 y 坐标。
     * @param startScan 开始扫描行，通常为 0。
     * @param numScan   要绘制的扫描行的数量，通常为 height。
     * @param bits      包含位图像素数据的整数数组。
     * @param bmi       描述位图信息的 BITMAPINFOHEADER 对象。
     * @param colorUse  指定颜色使用的方式，通常为 0，表示使用 DIB 颜色。
     * @return 成功时返回非零值；如果失败，则返回零。
     */
    public native int setDIBitsToDevice(long hdc, int xDest, int yDest, int width, int height,
                                        int xSrc, int ySrc, int startScan, int numScan,
                                        int[] bits, BITMAPINFOHEADER bmi, int colorUse);

    /**
     * 按比例缩放并复制位图
     * @param hdcDest 目标设备上下文的句柄
     * @param xDest 目标矩形的X坐标
     * @param yDest 目标矩形的Y坐标
     * @param wDest 目标矩形的宽度
     * @param hDest 目标矩形的高度
     * @param hdcSrc 源设备上下文的句柄
     * @param xSrc 源矩形的X坐标
     * @param ySrc 源矩形的Y坐标
     * @param wSrc 源矩形的宽度
     * @param hSrc 源矩形的高度
     * @param rop 指定图像的处理方式
     * @return 是否成功执行操作
     * C++: StretchBlt(HDC hdcDest, int xDest, int yDest, int wDest, int hDest, HDC hdcSrc, int xSrc, int ySrc, int wSrc, int hSrc, DWORD rop);
     */
    public native boolean stretchBlt(long hdcDest, int xDest, int yDest, int wDest, int hDest, long hdcSrc, int xSrc, int ySrc, int wSrc, int hSrc, long rop);

    /**
     * 在设备上下文中输出文本
     * @param hdc 设备上下文的句柄
     * @param x X坐标
     * @param y Y坐标
     * @param text 要输出的文本
     * @param length 文本长度
     * @return 是否成功执行操作
     * C++: TextOut(HDC hdc, int x, int y, LPCSTR lpString, int nCount);
     */
    public native boolean textOut(long hdc, int x, int y, String text, int length);

    /**
     * 绘制一条从当前位置到指定坐标的线
     * @param hdc 设备上下文的句柄
     * @param xEnd 线的结束X坐标
     * @param yEnd 线的结束Y坐标
     * @return 是否成功执行操作
     * C++: LineTo(HDC hdc, int xEnd, int yEnd);
     */
    public native boolean lineTo(long hdc, int xEnd, int yEnd);

    /**
     * 移动当前的画笔位置
     * @param hdc 设备上下文的句柄
     * @param x X坐标
     * @param y Y坐标
     * @param lpPoint 如果不为null，保存旧的画笔位置
     * @return 是否成功执行操作
     * C++: MoveToEx(HDC hdc, int x, int y, LPPOINT lpPoint);
     */
    public native boolean moveToEx(long hdc, int x, int y, long lpPoint);

    /**
     * 绘制一个矩形
     * @param hdc 设备上下文的句柄
     * @param left 矩形左边界
     * @param top 矩形上边界
     * @param right 矩形右边界
     * @param bottom 矩形下边界
     * @return 是否成功执行操作
     * C++: Rectangle(HDC hdc, int left, int top, int right, int bottom);
     */
    public native boolean drawRectangle(long hdc, int left, int top, int right, int bottom);

    /**
     * 绘制一个椭圆
     * @param hdc 设备上下文的句柄
     * @param left 椭圆左边界
     * @param top 椭圆上边界
     * @param right 椭圆右边界
     * @param bottom 椭圆下边界
     * @return 是否成功执行操作
     * C++: Ellipse(HDC hdc, int left, int top, int right, int bottom);
     */
    public native boolean drawEllipse(long hdc, int left, int top, int right, int bottom);

    /**
     * 绘制多边形
     * @param hdc 设备上下文的句柄
     * @param points 多边形顶点坐标数组
     * @param count 顶点数量
     * @return 是否成功执行操作
     * C++: Polygon(HDC hdc, const POINT *lppt, int nCount);
     */
    public native boolean drawPolygon(long hdc, int[] points, int count);

    /**
     * 设置指定坐标的像素颜色
     * @param hdc 设备上下文的句柄
     * @param x X坐标
     * @param y Y坐标
     * @param color 像素颜色
     * @return 像素的颜色值
     * C++: SetPixel(HDC hdc, int x, int y, COLORREF crColor);
     */
    public native int setPixel(long hdc, int x, int y, int color);

    /**
     * 获取指定坐标的像素颜色
     * @param hdc 设备上下文的句柄
     * @param x X坐标
     * @param y Y坐标
     * @return 像素的颜色值
     * C++: GetPixel(HDC hdc, int x, int y);
     */
    public native int getPixel(long hdc, int x, int y);

    /**
     * 填充矩形区域
     * @param hdc 设备上下文的句柄
     * @param rect 矩形区域的句柄
     * @param brush 画刷的句柄
     * @return 是否成功执行操作
     * C++: FillRect(HDC hdc, const RECT *lprc, HBRUSH hbr);
     */
    public native boolean fillRect(long hdc, long rect, long brush);

    /**
     * 渐变填充
     * @param hdc 设备上下文的句柄
     * @param vertex 顶点数组的句柄
     * @param vertexCount 顶点数量
     * @param mesh 网格的句柄
     * @param meshCount 网格数量
     * @param mode 渐变填充模式
     * @return 是否成功执行操作
     * C++: GradientFill(HDC hdc, PVOID pVertex, DWORD dwVertexCount, PVOID pMesh, DWORD dwMeshCount, DWORD dwMode);
     */
    public native boolean gradientFill(long hdc, long vertex, int vertexCount, long mesh, int meshCount, long mode);

    /**
     * 创建兼容的设备上下文（DC）
     * @param hdc 设备上下文的句柄
     * @return 新创建的兼容DC的句柄
     * C++: CreateCompatibleDC(HDC hdc);
     */
    public native long createCompatibleDC(long hdc);

    /**
     * 删除设备上下文（DC）
     * @param hdc 设备上下文的句柄
     * @return 是否成功执行操作
     * C++: DeleteDC(HDC hdc);
     */
    public native boolean deleteDC(long hdc);

    /**
     * 创建兼容位图
     * @param hdc 设备上下文的句柄
     * @param width 位图的宽度
     * @param height 位图的高度
     * @return 新创建的位图的句柄
     * C++: CreateCompatibleBitmap(HDC hdc, int nWidth, int nHeight);
     */
    public native long createCompatibleBitmap(long hdc, int width, int height);

    /**
     * 删除图形对象
     * @param hObject 图形对象的句柄
     * @return 是否成功执行操作
     * C++: DeleteObject(HGDIOBJ hObject);
     */
    public native boolean deleteObject(long hObject);

    /**
     * 选择对象到设备上下文
     * @param hdc 设备上下文的句柄
     * @param hObject 要选择的对象的句柄
     * @return 先前选定对象的句柄
     * C++: SelectObject(HDC hdc, HGDIOBJ hgdiobj);
     */
    public native long selectObject(long hdc, long hObject);

    /**
     * 设置背景颜色
     * @param hdc 设备上下文的句柄
     * @param color 背景颜色
     * @return 是否成功执行操作
     * C++: SetBkColor(HDC hdc, COLORREF crColor);
     */
    public native boolean setBackgroundColor(long hdc, int color);

    /**
     * 设置文本颜色
     * @param hdc 设备上下文的句柄
     * @param color 文本颜色
     * @return 是否成功执行操作
     * C++: SetTextColor(HDC hdc, COLORREF crColor);
     */
    public native boolean setTextColor(long hdc, int color);

    /**
     * 绘制多段线
     * @param hdc 设备上下文的句柄
     * @param points 顶点坐标数组
     * @param count 顶点数量
     * @return 是否成功执行操作
     * C++: Polyline(HDC hdc, const POINT *lppt, int nCount);
     */
    public native boolean polyline(long hdc, int[] points, int count);

    /**
     * 填充多边形
     * @param hdc 设备上下文的句柄
     * @param points 多边形顶点坐标数组
     * @param count 顶点数量
     * @return 是否成功执行操作
     * C++: Polygon(HDC hdc, const POINT *lppt, int nCount);
     */
    public native boolean polygonFill(long hdc, int[] points, int count);

    /**
     * 释放窗口设备上下文(所有事件的释放，运行后当前线程结束并且对应所有地方的渲染将被删除和停止)
     * @param hwnd 窗口句柄
     * @param hdc 设备上下文句柄
     * @return 是否成功执行操作
     */
    public native boolean exit(long hwnd,long hdc);
}
