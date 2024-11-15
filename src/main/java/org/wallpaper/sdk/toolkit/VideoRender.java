package org.wallpaper.sdk.toolkit;

import org.bytedeco.javacv.FrameGrabber;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.OpenCVFrameConverter;
import org.wallpaper.sdk.gdi.GDI;
import org.wallpaper.sdk.gdi.WindowMessages;

import javax.sound.sampled.*;
import java.awt.image.BufferedImage;
import java.io.File;

/**
 * 对视频帧解码并渲染到指定窗口
 *
 * @author tzdwindows 7
 */
public class VideoRender {
    private final TransferRender transferRender;
    private FFmpegFrameGrabber grabber;
    private final OpenCVFrameConverter.ToMat converter;
    private Clip audioClip;

    public VideoRender(boolean stretch) {
        this.transferRender = new TransferRender(stretch);
        this.converter = new OpenCVFrameConverter.ToMat();
    }

    /**
     * 开始渲染指定视频文件
     *
     * @param videoFile   视频文件
     * @param hwnd        窗口句柄
     * @param hdc         设备上下文句柄
     * @param enableAudio 是否启用音频
     */
    public void renderVideo(File videoFile, long hwnd, long hdc, boolean enableAudio) {
        try {
            grabber = new FFmpegFrameGrabber(videoFile);
            grabber.start();
            WindowMessages windowMessages = new WindowMessages();
            int width = windowMessages.getWindowWidth(hwnd);
            int height = windowMessages.getWindowHeight(hwnd);
            GDI gdi = new GDI();

            if (enableAudio) {
                System.out.println("This feature is not currently supported ");
            }

            renderFrames(gdi, hdc, width, height);
            grabber.stop();

            if (audioClip != null) {
                audioClip.stop();
                audioClip.close();
            }
        } catch (Exception e) {
            handleException(e);
        }
    }

    private void renderFrames(GDI gdi, long hdc, int width, int height) throws FrameGrabber.Exception {
        long lastTime = System.currentTimeMillis();
        while (true) {
            org.bytedeco.javacv.Frame frame = grabber.grabImage();
            if (frame == null) {
                break;
            }

            var mat = converter.convert(frame);
            BufferedImage bufferedImage = new BufferedImage(mat.cols(), mat.rows(), BufferedImage.TYPE_INT_RGB);
            mat.data().position(0);
            for (int i = 0; i < mat.rows(); i++) {
                int[] rgbArray = new int[mat.cols()];
                for (int j = 0; j < mat.cols(); j++) {
                    int b = (mat.data().get((long) i * mat.cols() * 3 + j * 3L) & 0xff);
                    int g = (mat.data().get((long) i * mat.cols() * 3 + j * 3L + 1) & 0xff);
                    int r = (mat.data().get((long) i * mat.cols() * 3 + j * 3L + 2) & 0xff);
                    rgbArray[j] = (r << 16) | (g << 8) | b;
                }
                bufferedImage.setRGB(0, i, mat.cols(), 1, rgbArray, 0, mat.cols());
            }
            transferRender.drawImageToWindow(gdi, hdc, bufferedImage, width, height);
            long currentTime = System.currentTimeMillis();
            long elapsedTime = currentTime - lastTime;
            lastTime = currentTime;
            int delay = (int) (1000 / grabber.getFrameRate());
            if (delay > elapsedTime) {
                try {
                    Thread.sleep(delay - elapsedTime);
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }


    private void handleException(Exception e) {
        e.printStackTrace();
        if (grabber != null) {
            try {
                grabber.stop();
            } catch (FrameGrabber.Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}
