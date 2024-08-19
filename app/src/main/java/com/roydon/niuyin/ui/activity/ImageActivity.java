package com.roydon.niuyin.ui.activity;


import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.viewpager.widget.ViewPager;

import com.gyf.immersionbar.BarHide;
import com.gyf.immersionbar.ImmersionBar;
import com.roydon.niuyin.R;
import com.roydon.niuyin.aop.CheckNet;
import com.roydon.niuyin.aop.DebugLog;
import com.roydon.niuyin.common.MyActivity;
import com.roydon.niuyin.other.IntentKey;
import com.roydon.niuyin.ui.pager.ImagePagerAdapter;
import com.rd.PageIndicatorView;
import com.roydon.niuyin.utils.PicSaveUtil;

import java.util.ArrayList;
import java.util.UUID;

import butterknife.BindView;

/**
 * desc   : 查看大图，图片轮播
 */
public final class ImageActivity extends MyActivity {

    @BindView(R.id.vp_image_pager)
    ViewPager mViewPager;
    @BindView(R.id.pv_image_indicator)
    PageIndicatorView mIndicatorView;

    @BindView(R.id.ll_close)
    LinearLayout mCloseLayout;
    @BindView(R.id.ll_play)
    LinearLayout mPlayLayout;
    @BindView(R.id.iv_play)
    ImageView mPlayIV;
    @BindView(R.id.ll_download)
    LinearLayout mDownloadLayout;

    private ArrayList<String> imageList;
    private boolean play = false;

    public static void start(Context context, String url) {
        ArrayList<String> images = new ArrayList<>(1);
        images.add(url);
        start(context, images);
    }

    public static void start(Context context, ArrayList<String> urls) {
        start(context, urls, 0);
    }

    @CheckNet
    @DebugLog
    public static void start(Context context, ArrayList<String> urls, int index) {
        Intent intent = new Intent(context, ImageActivity.class);
        intent.putExtra(IntentKey.PICTURE, urls);
        intent.putExtra(IntentKey.INDEX, index);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_image;
    }

    @Override
    protected void initView() {
        mIndicatorView.setViewPager(mViewPager);
        setOnClickListener(R.id.ll_close, R.id.ll_play, R.id.ll_download);
    }

    @Override
    protected ImmersionBar createStatusBarConfig() {
        return super.createStatusBarConfig()
                // 有导航栏的情况下，activity全屏显示，也就是activity最下面被导航栏覆盖，不写默认非全屏
                .fullScreen(true)
                // 隐藏状态栏
                .hideBar(BarHide.FLAG_HIDE_STATUS_BAR)
                // 透明导航栏，不写默认黑色(设置此方法，fullScreen()方法自动为true)
                .transparentNavigationBar();
    }

    @Override
    protected void initData() {
        imageList = getStringArrayList(IntentKey.PICTURE);
        int index = getInt(IntentKey.INDEX);
        if (imageList != null && imageList.size() > 0) {
            mViewPager.setAdapter(new ImagePagerAdapter(this, imageList));
            if (index != 0 && index <= imageList.size()) {
                mViewPager.setCurrentItem(index);
            }
        } else {
            finish();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_close:
                finish();
                break;
            case R.id.ll_play:
                play = !play;
                if (play) {
                    // 正在播放
                    mPlayIV.setImageResource(R.drawable.ic_pause_w);
                } else {
                    mPlayIV.setImageResource(R.drawable.ic_play_w);
                }
                break;
            case R.id.ll_download:
                int currentItem = mViewPager.getCurrentItem();
                PicSaveUtil.savePic(imageList.get(currentItem), this, PicSaveUtil.SAVE_PATH_TYPE_DCIM, UUID.randomUUID().toString());
//                toast("图片暂不支持下载 " + currentItem);
                toast("下载成功");
                break;
            default:
                break;
        }
    }

    @Override
    public boolean isStatusBarDarkFont() {
        return false;
    }

    @Override
    public boolean isSwipeEnable() {
        return false;
    }
}