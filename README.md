# DesktopWallpaperSdk

> **动态桌面渲染工具，支持多种渲染方式，适配最新 Windows 平台。**

---

## 项目简介

**DesktopWallpaperSdk** 是一款高效的桌面壁纸动态渲染工具，支持以下特性：

- **渲染方式**：
    - OpenGL 渲染
    - GDI 渲染
- **测试平台**：Windows 11 23H2
- **功能特点**：
    - 支持动态壁纸实时渲染
    - 提供灵活的 API 接口，方便开发者集成
    - 可扩展性强，轻松添加自定义渲染逻辑

---

## 项目资源

您可以在我的哔哩哔哩主页查看项目使用示例：
- 哔哩哔哩：[b23.tv/BkZEhRz](https://b23.tv/BkZEhRz)

您也可以在QQ群852270618中得到最新更新和报告错误。

项目源代码需自行下载并构建

---

## 构建指南

1. **建议构建工具**：使用 IntelliJ IDEA 开发环境。
2. **运行环境**：
    - **Java 虚拟机（JVM）**：运行时需要加载适配架构的动态链接库 (`.dll`)。
    - **动态链接库路径**：`build/libs/` 文件夹中提供对应的 `.dll` 文件。
3. **旧版本支持**：
    - `build/libs/` 文件夹中包含一些旧版本的 JAR 文件，可以根据需求进行回溯使用。

---

## 使用说明

### 构建项目
1. 将项目导入到 IntelliJ IDEA。
2. 配置 `build.gradle` 文件，确保所有依赖正确加载。
3. 构建项目并运行。

*注意：本项目自带的 `build.gradle` 已经配置好了，无需手动修改。*

---

### 动态链接库加载
确保在 Java 运行时能够找到适配架构的 `.dll` 文件：
```java
class NativeLibraryLoader {
    public static void main(String[] args) {
        // 加载动态链接库
        System.loadLibrary("DesktopWallpaperSdk");

        // 得到桌面窗口句柄
        final long[] hwnd = new long[1];
        ComputerEvents.computerEvent(ComputerEvents.EventTyp.Desktop, new Render() {
            @Override
            public void render(long h) {
                hwnd[0] = h;
            }
        });
        
        // 调用对应函数实现动态壁纸渲染
        // while (...) {
        //      ...
        // }
        
        // 设置开机启动
        System.out.println(
                AutomaticStartup.setStartup("/自启动.bat")
        ); 
    }
}
```
以上代码中，使用的 `System.loadLibrary` 方法加载的动态链接库，请根据实际情况修改。

---

## 协议声明

**DesktopWallpaperSdk 遵循 [GNU General Public License (GPL) ](https://www.gnu.org/licenses/gpl-3.0.en.html ) 开源协议。这意味着：**
- 您可以自由地运行、研究、修改和分发本项目的源代码。
- 修改后发布的版本需继承本项目的 GPL 协议并开源。

**注意：如果您的使用场景有特殊需求，请确保符合协议条款。**

---

## 联系作者
如有疑问或建议，欢迎通过以下方式联系：
- 邮箱：[3076584115@qq.com](mailto:3076584115@qq.com)
- 哔哩哔哩：[Finbov_Dushb](https://b23.tv/BkZEhRz)
- GitHub：[tzdwindows 7](https://github.com/tzdwindows)
- QQ群：[余胜军小卖部](http://qm.qq.com/cgi-bin/qm/qr?_wv=1027&k=UqHG8X393i8PEovz6BxgLdL7La5O4Mmw&authKey=I5DVBI%2Byep1COym6dpL956py9RhKkxg7wFJmeam6woQOhciAMY%2Bhhw7%2F5ffdm6L4&noverify=0&group_code=852270618)