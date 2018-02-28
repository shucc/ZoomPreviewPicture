package com.previewlibrary;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.previewlibrary.model.IThumbViewInfoModel;
import com.previewlibrary.widget.BezierBannerView;
import com.previewlibrary.widget.PhotoViewPager;
import com.previewlibrary.widget.SmoothImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yangc on 2017/4/26.
 * E-Mail:yangchaojiang@outlook.com
 * Deprecated:图片预览页面
 */
public class GPreviewActivity extends FragmentActivity {

    private boolean isTransformOut = false;
    //图片的地址
    private List<IThumbViewInfoModel> imgUrls;
    //当前图片的位置
    private int currentIndex;
    //图片的展示的Fragment
    private List<GPreviewFragment> fragments = new ArrayList<>();
    //展示图片的viewPager
    private PhotoViewPager viewPager;
    //显示图片数
    private TextView textCount;
    //指示器控件
    private BezierBannerView bezierBannerView;
    //指示器类型枚举
    private GPreviewBuilder.IndicatorType type;

    @CallSuper
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (setContentLayout() == 0) {
            setContentView(R.layout.activity_image_preview_photo);
        } else {
            setContentView(setContentLayout());
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //状态栏透明
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }

        initData();
        initView();
    }

    /**
     * 初始化数据
     */
    private void initData() {
        imgUrls = getIntent().getParcelableArrayListExtra("imagePaths");
        currentIndex = getIntent().getIntExtra("position", -1);
        type = (GPreviewBuilder.IndicatorType) getIntent().getSerializableExtra("type");
        try {
            Class<? extends GPreviewFragment> sss;
            sss = (Class<? extends GPreviewFragment>) getIntent().getSerializableExtra("className");
            iniFragment(imgUrls, currentIndex, sss);
        } catch (Exception e) {
            iniFragment(imgUrls, currentIndex, GPreviewFragment.class);
        }
    }

    /**
     * 初始化
     *
     * @param imgUrls      集合
     * @param currentIndex 选中索引
     * @param className    显示Fragment
     **/
    protected void iniFragment(List<IThumbViewInfoModel> imgUrls, int currentIndex, Class<? extends GPreviewFragment> className) {
        if (imgUrls != null) {
            int size = imgUrls.size();
            boolean s = getIntent().getBooleanExtra("isSingleFling", false);
            boolean isDrag = getIntent().getBooleanExtra("isDrag", false);
            for (int i = 0; i < size; i++) {
                fragments.add(GPreviewFragment.getInstance(className, imgUrls.get(i), currentIndex == i, s, isDrag));
            }
        } else {
            finish();
        }
    }

    /**
     * 初始化控件
     */
    @SuppressLint("StringFormatMatches")
    private void initView() {
        viewPager = findViewById(R.id.viewPager);
        //viewPager的适配器
        PhotoPagerAdapter adapter = new PhotoPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(currentIndex);
        viewPager.setOffscreenPageLimit(3);
        if (type == GPreviewBuilder.IndicatorType.Dot) {
            bezierBannerView = findViewById(R.id.bezierBannerView);
            bezierBannerView.setVisibility(View.VISIBLE);
            bezierBannerView.attachToViewpager(viewPager);
        } else {
            textCount = findViewById(R.id.text_count);
            textCount.setVisibility(View.VISIBLE);
            textCount.setText(getString(R.string.image_preview_count, (currentIndex + 1), imgUrls.size()));
            viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                    //当被选中的时候设置小圆点和当前位置
                    if (textCount != null) {
                        textCount.setText(getString(R.string.image_preview_count, (position + 1), imgUrls.size()));
                    }
                    currentIndex = position;
                    viewPager.setCurrentItem(currentIndex, true);
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });
        }
        viewPager.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                viewPager.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                GPreviewFragment fragment = fragments.get(currentIndex);
                fragment.transformIn();
            }
        });
    }

    /**
     * 退出预览的动画
     */
    public void transformOut() {
        if (isTransformOut) {
            return;
        }
        isTransformOut = true;
        int currentItem = viewPager.getCurrentItem();
        if (currentItem < imgUrls.size()) {
            GPreviewFragment fragment = fragments.get(currentItem);
            if (textCount != null) {
                textCount.setVisibility(View.GONE);
            } else {
                bezierBannerView.setVisibility(View.GONE);
            }
            fragment.changeBg(Color.TRANSPARENT);
            fragment.transformOut(new SmoothImageView.onTransformListener() {
                @Override
                public void onTransformCompleted(SmoothImageView.Status status) {
                    exit();
                }
            });
        } else {
            exit();
        }
    }

    /***
     * 得到PhotoFragment集合
     * @return List
     * **/
    public List<GPreviewFragment> getFragments() {
        return fragments;
    }

    /**
     * 关闭页面
     */
    private void exit() {
        finish();
        overridePendingTransition(0, 0);
    }

    /***
     * 得到PhotoViewPager
     * @return PhotoViewPager
     * **/
    public PhotoViewPager getViewPager() {
        return viewPager;
    }

    /***
     * 自定义布局内容
     * @return int
     ***/
    public int setContentLayout() {
        return 0;
    }

    @Override
    public void onBackPressed() {
        transformOut();
    }

    @Override
    protected void onDestroy() {
        GPreviewLoader.getInstance().getLoader().clearMemory(this);
        if (viewPager != null) {
            viewPager.setAdapter(null);
            viewPager.clearOnPageChangeListeners();
            viewPager.removeAllViews();
            viewPager = null;
        }
        if (fragments != null) {
            fragments.clear();
            fragments = null;
        }
        if (imgUrls != null) {
            imgUrls.clear();
            imgUrls = null;
        }
        super.onDestroy();
    }

    /**
     * pager的适配器
     */
    private class PhotoPagerAdapter extends FragmentPagerAdapter {

        PhotoPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments == null ? 0 : fragments.size();
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            super.destroyItem(container, position, object);
        }
    }
}
