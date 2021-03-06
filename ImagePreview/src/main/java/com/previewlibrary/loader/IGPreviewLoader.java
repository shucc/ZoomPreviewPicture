package com.previewlibrary.loader;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

/**
 * @author yangc
 *         date 2017/9/4
 *         E-Mail:yangchaojiang@outlook.com
 *         Deprecated: 加载器接口
 */
public interface IGPreviewLoader {

    /**
     * 图片加载
     *
     * @param context      容器
     * @param path         图片路径
     * @param simpleTarget 图片加载回调
     */
    void displayImage(@NonNull Fragment context, @NonNull String path, @NonNull GPreviewTarget<Bitmap> simpleTarget);

    /**
     * 停止
     *
     * @param context 容器
     **/
    void onStop(@NonNull Fragment context);

    /**
     * 停止
     *
     * @param c 容器
     **/
    void clearMemory(@NonNull Context c);
}
