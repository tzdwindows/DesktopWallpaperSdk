package org.wallpaper.sdk;

import java.util.ArrayList;
import java.util.List;

/**
 * @author tzdwindows 7
 */
public class RenderList {
    private final List<Render> renderList = new ArrayList<>();

    public void add(Render render) {
        renderList.add(render);
    }

    public void runList(Runnable run) {
        for (Render render : renderList) {
            run.run(render);
        }
    }

    public void render(long[] time)  {
        for (int i = 0; i < renderList.size(); i++) {
            Render render = renderList.get(i);
            render.render(time[i]);
        }
    }

    public interface Runnable {
        void run(Render render);
    }
}
