package com.roydon.niuyin.ui.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;

import com.flyco.tablayout.SlidingTabLayout;
import com.google.gson.Gson;
import com.hjq.bar.TitleBar;
import com.hjq.base.BaseActivity;
import com.hjq.base.BaseAdapter;
import com.hjq.http.EasyHttp;
import com.hjq.http.listener.HttpCallback;
import com.hjq.permissions.Permission;
import com.hjq.widget.layout.WrapRecyclerView;
import com.roydon.niuyin.R;
import com.roydon.niuyin.action.StatusAction;
import com.roydon.niuyin.aop.CheckNet;
import com.roydon.niuyin.aop.DebugLog;
import com.roydon.niuyin.aop.Permissions;
import com.roydon.niuyin.common.MyActivity;
import com.roydon.niuyin.http.model.HttpData;
import com.roydon.niuyin.http.request.video.ChildrenVideoCategoryApi;
import com.roydon.niuyin.http.request.video.ParentVideoCategoryApi;
import com.roydon.niuyin.http.response.video.AppVideoCategoryVo;
import com.roydon.niuyin.other.IntentKey;
import com.roydon.niuyin.ui.adapter.HomeAdapter;
import com.roydon.niuyin.ui.adapter.VideoCategoryAdapter;
import com.roydon.niuyin.ui.fragment.FragmentCategoryVideoData;
import com.roydon.niuyin.ui.fragment.index.IndexFollowFragment;
import com.roydon.niuyin.ui.fragment.index.IndexHotFragment;
import com.roydon.niuyin.ui.fragment.index.IndexRecommendFragment;
import com.roydon.niuyin.widget.HintLayout;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class CategoryVideoActivity extends MyActivity {

    // handler
    private static final int HANDLER_WHAT_EMPTY = 0;
    private static final int HANDLER_VIDEO_CATEGORY_CHILDREN = 1;
    private static final int HANDLER_CATEGORY_VIDEO_DATA = 2;

    @BindView(R.id.titleBar)
    TitleBar mTitleBar;

    @BindView(R.id.slidingTabLayout)
    SlidingTabLayout mSlidingTabLayout;
    @BindView(R.id.viewPager)
    ViewPager mViewPager;

    private AppVideoCategoryVo parentVideoCategory;
    private List<AppVideoCategoryVo> childrenVideoCategoryList;

    @CheckNet
    @DebugLog
    public static void start(Context context, AppVideoCategoryVo appVideoCategoryVo) {
        Intent intent = new Intent(context, CategoryVideoActivity.class);
        intent.putExtra(IntentKey.APP_VIDEO_CATEGORY_VO, new Gson().toJson(appVideoCategoryVo));
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_category_video;
    }

    @Override
    protected void initView() {
        // 设置title标题
        String appVideoCategory = getString(IntentKey.APP_VIDEO_CATEGORY_VO);
        parentVideoCategory = new Gson().fromJson(appVideoCategory, AppVideoCategoryVo.class);
        mTitleBar.setTitle(parentVideoCategory.getName());

    }

    @Override
    protected void initData() {
        getChildrenVideoCategory(parentVideoCategory.getId());
    }

    /**
     * 获取子分类
     *
     * @param id 父分类id
     */
    private void getChildrenVideoCategory(Long id) {
        EasyHttp.get(this)
                .api(new ChildrenVideoCategoryApi().setId(id))
                .request(new HttpCallback<HttpData<List<AppVideoCategoryVo>>>(this) {
                    @Override
                    public void onSucceed(HttpData<List<AppVideoCategoryVo>> data) {
                        childrenVideoCategoryList = data.getData();
                        // 更新ui
                        mHandler.sendEmptyMessage(HANDLER_VIDEO_CATEGORY_CHILDREN);
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
                case HANDLER_VIDEO_CATEGORY_CHILDREN:
                    showSlidingTabLayout(childrenVideoCategoryList);
                    break;
                case HANDLER_CATEGORY_VIDEO_DATA:
                    break;
                default:
                    break;
            }
        }
    };

    /**
     * 动态渲染SlidingTabLayout
     *
     * @param list List<AppVideoCategoryVo>
     */
    private void showSlidingTabLayout(List<AppVideoCategoryVo> list) {
        // tab
        String[] mTitles = new String[list.size()];
        ArrayList<Fragment> mFragments = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            mTitles[i] = list.get(i).getName();
            mFragments.add(FragmentCategoryVideoData.newInstance(list.get(i).getId()));
        }
        mViewPager.setOffscreenPageLimit(mFragments.size());
        mViewPager.setAdapter(new HomeAdapter(getSupportFragmentManager(), mTitles, mFragments));
        mSlidingTabLayout.setViewPager(mViewPager);
        mSlidingTabLayout.setCurrentTab(0);
    }

}