package com.previewlibrary.model;

import android.graphics.Rect;
import android.os.Parcel;

/**
 * Created by yangc on 2017/4/26.
 * E-Mail:yangchaojiang@outlook.com
 * Deprecated: 图片预览实体类
 * <p>
 * {@link IThumbViewInfoModel}.
 **/
public class ThumbViewInfoModel implements IThumbViewInfoModel {

    //图片地址
    private String url;
    // 记录坐标
    private Rect mBounds;

    public ThumbViewInfoModel(String url) {
        this.url = url;
    }

    @Override
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public Rect getBounds() {
        return mBounds;
    }

    public void setBounds(Rect bounds) {
        mBounds = bounds;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.url);
        dest.writeParcelable(this.mBounds, 0);
    }

    protected ThumbViewInfoModel(Parcel in) {
        this.url = in.readString();
        this.mBounds = in.readParcelable(Rect.class.getClassLoader());
    }

    public static final Creator<ThumbViewInfoModel> CREATOR = new Creator<ThumbViewInfoModel>() {
        @Override
        public ThumbViewInfoModel createFromParcel(Parcel source) {
            return new ThumbViewInfoModel(source);
        }

        @Override
        public ThumbViewInfoModel[] newArray(int size) {
            return new ThumbViewInfoModel[size];
        }
    };
}
