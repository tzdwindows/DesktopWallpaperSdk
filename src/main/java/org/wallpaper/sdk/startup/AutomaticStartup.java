package org.wallpaper.sdk.startup;

/**
 * 设置自动启动项
 * @author tzdwindows 7
 */
public class AutomaticStartup {
    /**
     * 设置开机启动（启动任务会在用户登录时执行 TASK_TRIGGER_LOGON）
     * @param path 启动项的绝对路径
     *             示例：C:\Program Files\QQ\QQ.exe
     * @param priority 优先级，0-10，数字越大优先级越高,10即无限制
     * @return 是否操作成功
     */
    public static native boolean setStartup(String path, int priority);

    /**
     * 删除开机启动
     * @param path 启动项的绝对路径
     *             示例：C:\Program Files\QQ\QQ.exe
     * @return 是否操作成功
     */
    public static native boolean removeStartup(String path);

    /**
     * 判断是否是开机启动
     * @param path 启动项的绝对路径
     *             示例：C:\Program Files\QQ\QQ.exe
     * @return 返回true表示是开机启动，false表示不是开机启动
     */
    public static native boolean isStartup(String path);

    ///**
    // * 获取开机启动的优先级
    // * @param path 启动项的绝对路径
    // *             示例：C:\Program Files\QQ\QQ.exe
    // * @return 返回优先级，0-9，数字越大优先级越高
    // *         -1表示获取失败
    // */
    //public static native int getPriority(String path);
}
