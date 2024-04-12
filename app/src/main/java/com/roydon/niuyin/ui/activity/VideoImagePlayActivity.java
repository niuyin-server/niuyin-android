package com.roydon.niuyin.ui.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.flyco.tablayout.SlidingTabLayout;
import com.hjq.http.EasyHttp;
import com.hjq.http.listener.HttpCallback;
import com.hjq.widget.layout.RatioFrameLayout;
import com.hjq.widget.square.SquareImageView;
import com.roydon.niuyin.R;
import com.roydon.niuyin.aop.DebugLog;
import com.roydon.niuyin.common.MyActivity;
import com.roydon.niuyin.http.model.HttpData;
import com.roydon.niuyin.http.request.video.VideoInfoApi;
import com.roydon.niuyin.http.response.video.VideoInfoVO;
import com.roydon.niuyin.other.IntentKey;
import com.roydon.niuyin.ui.adapter.BannerAdapter;
import com.roydon.niuyin.ui.adapter.VideoPlayAdapter;
import com.roydon.niuyin.ui.fragment.videoplay.VideoCommentFragment;
import com.roydon.niuyin.ui.fragment.videoplay.VideoInfoFragment;
import com.zhpan.bannerview.BannerViewPager;
import com.zhpan.bannerview.constants.IndicatorGravity;
import com.zhpan.indicator.enums.IndicatorSlideMode;
import com.zhpan.indicator.enums.IndicatorStyle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;

public class VideoImagePlayActivity extends MyActivity {

    // handler
    private static final int HANDLER_WHAT_EMPTY = 0;
    private static final int HANDLER_VIDEO_INFO = 1;

    @DebugLog
    public static void start(Context context, String videoId) {
        Intent intent = new Intent(context, VideoImagePlayActivity.class);
        intent.putExtra(IntentKey.VIDEO_ID, videoId);
        context.startActivity(intent);
    }

    @BindView(R.id.rl_screen_scale)
    RatioFrameLayout mScreenScaleLayout;
    //    @BindView(R.id.banner)
//    Banner mBanner;
    @BindView(R.id.bannerViewPager)
    BannerViewPager bannerViewPager;

    @BindView(R.id.slidingTabLayout)
    SlidingTabLayout mSlidingTabLayout;
    @BindView(R.id.viewPager)
    ViewPager mViewPager;

    // menu
    @BindView(R.id.iv_menu_back)
    SquareImageView menuBackIV;

    private VideoInfoVO videoInfoVO;

    @Override
    public boolean isStatusBarEnabled() {
        // 使用沉浸式状态栏
        return !super.isStatusBarEnabled();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_video_image_play;
    }

    @Override
    protected void initView() {

        // 4:3竖屏视频
//        mScreenScaleLayout.setSizeRatio(0.75f);

        setOnClickListener(R.id.iv_menu_back);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_menu_back:
                finish();
                break;
            default:
                break;
        }
    }

    @Override
    protected void initData() {
        EasyHttp.get(this).api(new VideoInfoApi().setVideoId(getString(IntentKey.VIDEO_ID)))
                .request(new HttpCallback<HttpData<VideoInfoVO>>(this) {
                    @Override
                    public void onSucceed(HttpData<VideoInfoVO> data) {
                        videoInfoVO = data.getData();
                        // 获取视频成功
                        mHandler.sendEmptyMessage(HANDLER_VIDEO_INFO);
                    }
                });
    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @SuppressLint("NotifyDataSetChanged")
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case HANDLER_WHAT_EMPTY:
                    break;
                case HANDLER_VIDEO_INFO:
                    useBanner(videoInfoVO);
                    initSlideTab(videoInfoVO);
                    break;
                default:
                    break;
            }
        }
    };

    /**
     * 展示轮播图
     *
     * @param videoInfoVO
     */
    public void useBanner(VideoInfoVO videoInfoVO) {

//        BlurUtil.blurLayoutBackground(this, videoInfoVO.getCoverImage(), mScreenScaleLayout, getResources());

//        mBanner.setAdapter(new VideoImageBannerAdapter<String>(Arrays.asList(videoInfoVO.getImageList())) {
//                    @Override
//                    public void onBindView(BannerImageHolder holder, String data, int position, int size) {
//                        //图片加载
//                        Glide.with(holder.itemView).load(data).apply(RequestOptions.bitmapTransform(new RoundedCorners(10))).into(holder.imageView);
//                    }
//                }).addBannerLifecycleObserver(this)//添加生命周期观察者
//                .setIndicator(new SquareIndicator(this));
//        mBanner.setOnBannerListener((data, position) -> {
////            toast("点击了第" + position + "个");
//            String[] imageList = videoInfoVO.getImageList();
//            ArrayList<String> arrayList = new ArrayList<>(Arrays.asList(imageList));
//            ImageActivity.start(this, arrayList, position);
//        });

        List<String> imgs = Arrays.asList(videoInfoVO.getImageList());
        int size = imgs.size();

        int widthPixels = getResources().getDisplayMetrics().widthPixels;
        int ceil = (widthPixels - 40 * size) / size;

        bannerViewPager.setAdapter(new BannerAdapter())
                .setLifecycleRegistry(getLifecycle())
                .setIndicatorStyle(IndicatorStyle.ROUND_RECT)
                .setIndicatorSlideMode(IndicatorSlideMode.SMOOTH)
                .setIndicatorGravity(IndicatorGravity.CENTER)
                .setIndicatorSliderWidth(ceil)
                .setIndicatorHeight(10)
                .setIndicatorSliderColor(getResources().getColor(R.color.gray50), getResources().getColor(R.color.white))
                .setOnPageClickListener(new BannerViewPager.OnPageClickListener() {
                    @Override
                    public void onPageClick(View clickedView, int position) {
                        ArrayList<String> arrayList = new ArrayList<>(imgs);
                        ImageActivity.start(getActivity(), arrayList, position);
                    }
                })
                .create(imgs);

    }

    /**
     * 初始化tab
     *
     * @param videoInfoVO
     */
    private void initSlideTab(VideoInfoVO videoInfoVO) {
        // tab
        String[] mTitles = {"简介", "评论"};
        ArrayList<Fragment> mIndexFragments = new ArrayList<>();
        VideoInfoFragment videoInfoFragment = VideoInfoFragment.newInstance();
        videoInfoFragment.setVideoInfoVO(videoInfoVO);
        VideoCommentFragment videoCommentFragment = VideoCommentFragment.newInstance();
        videoCommentFragment.setVideoInfoVO(videoInfoVO);
        mIndexFragments.add(videoInfoFragment);
        mIndexFragments.add(videoCommentFragment);
        mViewPager.setOffscreenPageLimit(mIndexFragments.size());
        mViewPager.setAdapter(new VideoPlayAdapter(getSupportFragmentManager(), mTitles, mIndexFragments));
        mSlidingTabLayout.setViewPager(mViewPager);
        mSlidingTabLayout.setCurrentTab(0);
    }

}