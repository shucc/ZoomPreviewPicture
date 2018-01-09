package com.previewlibrary.util;

import android.content.Context;
import android.os.Build;

import com.previewlibrary.R;

/**
 * Created by shucc on 17/12/12.
 * cc@cchao.org
 */
public class StatusBarUtil {

    /**
     * 取状态栏高度
     *
     * @param mApplicationContent mApplicationContent
     * @return int
     */
    public static int getStatusBarHeight(Context mApplicationContent) {
        //当为透明状态栏
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            return 0;
        }
        int result = mApplicationContent.getResources().getDimensionPixelSize(R.dimen.default_status_bar_height);
        int resourceId = mApplicationContent.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = mApplicationContent.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }
}
