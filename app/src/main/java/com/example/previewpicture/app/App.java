package com.example.previewpicture.app;

import android.app.Application;

import com.example.previewpicture.imp.TestImageLoader;
import com.previewlibrary.GPreviewLoader;

/**
 * Created by yangc on 2017/9/4.
 * E-Mail:yangchaojiang@outlook.com
 * Deprecated:
 */
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        GPreviewLoader.getInstance().init(new TestImageLoader());
    }
}
