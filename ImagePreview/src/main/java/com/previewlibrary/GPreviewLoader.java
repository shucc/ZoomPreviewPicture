package com.previewlibrary;

import com.previewlibrary.loader.IGPreviewLoader;

/**
 * @author yangc
 *         date 2017/9/4
 *         E-Mail:yangchaojiang@outlook.com
 *         Deprecated: 图片加载管理器
 */
public class GPreviewLoader {

    private volatile IGPreviewLoader loader;

    public static GPreviewLoader getInstance() {
        return Holder.holder;
    }

    private GPreviewLoader() {

    }

    private static class Holder {
        static GPreviewLoader holder = new GPreviewLoader();
    }

    /****
     * 初始化加载图片类
     * @param  loader 自定义
     * **/
    public void init(IGPreviewLoader loader) {
        this.loader = loader;
    }

    public IGPreviewLoader getLoader() {
        if (loader == null) {
            throw new NullPointerException("loader no init");
        }
        return loader;
    }
}
