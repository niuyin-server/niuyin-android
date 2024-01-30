package com.roydon.niuyin.ui.activity;

import android.text.TextUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.roydon.niuyin.R;
import com.roydon.niuyin.common.MyActivity;
import com.roydon.niuyin.widget.HintLayout;
import com.roydon.niuyin.widget.XCollapsingToolbarLayout;

import butterknife.BindView;

public class VideoSearchActivity extends MyActivity {

    @BindView(R.id.iv_back)
    ImageView mBack;
    @BindView(R.id.tv_hint_keyword)
    EditText mHintKeyword;
    @BindView(R.id.tv_search)
    TextView mSearch;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_video_search;
    }

    @Override
    protected void initView() {
        mBack.setOnClickListener(v -> {
            finish();
        });
        // 自动获取输入框焦点
        mHintKeyword.setFocusable(true);
        mHintKeyword.setFocusableInTouchMode(true);
        mHintKeyword.requestFocus();
        // 点击搜索
        mSearch.setOnClickListener(v -> {
            if (!TextUtils.isEmpty(mHintKeyword.getText().toString())) {
                toast("点击搜索：" + mHintKeyword.getText().toString());
            }
        });
    }

    @Override
    protected void initData() {

    }
}