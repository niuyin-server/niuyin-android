package com.roydon.niuyin.ui.dialog;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.recyclerview.widget.RecyclerView;

import com.hjq.base.BaseAdapter;
import com.hjq.base.BaseDialog;
import com.hjq.base.action.AnimAction;
import com.roydon.niuyin.R;
import com.roydon.niuyin.aop.SingleClick;
import com.roydon.niuyin.common.MyAdapter;
import com.roydon.niuyin.domain.NoticeBehaveShowTypeBean;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * desc   : 消息类型选择框
 */
public final class NoticeBehaveTypeDialog {

    public static final class Builder extends BaseDialog.Builder<Builder> implements BaseAdapter.OnItemClickListener {

        private OnListener mListener;
        private boolean mAutoDismiss = true;

        private final NoticeBehaveTypeAdapter mAdapter;
        private final TextView mCancelView;

        public Builder(Context context) {
            super(context);
            setContentView(R.layout.dialog_notice_behave_type);
            setAnimStyle(AnimAction.BOTTOM);

            RecyclerView recyclerView = findViewById(R.id.rv_type_list);
            mCancelView = findViewById(R.id.tv_type_cancel);

            mAdapter = new NoticeBehaveTypeAdapter(getContext());
            mAdapter.setOnItemClickListener(this);
            recyclerView.setAdapter(mAdapter);

            setOnClickListener(R.id.tv_type_cancel);
        }

        @Override
        public Builder setGravity(int gravity) {
            switch (gravity) {
                // 如果这个是在中间显示的
                case Gravity.CENTER:
                case Gravity.CENTER_VERTICAL:
                    // 不显示取消按钮
                    setCancel(null);
                    // 重新设置动画
                    setAnimStyle(AnimAction.SCALE);
                    break;
                default:
                    break;
            }
            return super.setGravity(gravity);
        }

        public Builder setList(int... ids) {
            List<String> data = new ArrayList<>(ids.length);
            for (int id : ids) {
                data.add(getString(id));
            }
            return setList(data);
        }

        public Builder setList(String... data) {
            return setList(Arrays.asList(data));
        }

        public Builder setList(NoticeBehaveShowTypeBean... data) {
            return setList(Arrays.asList(data));
        }

        @SuppressWarnings("all")
        public Builder setList(List data) {
            mAdapter.setData(data);
            return this;
        }

        public Builder setCancel(@StringRes int id) {
            return setCancel(getString(id));
        }

        public Builder setCancel(CharSequence text) {
            mCancelView.setText(text);
            return this;
        }

        public Builder setAutoDismiss(boolean dismiss) {
            mAutoDismiss = dismiss;
            return this;
        }

        public Builder setListener(OnListener listener) {
            mListener = listener;
            return this;
        }

        @SingleClick
        @Override
        public void onClick(View v) {
            if (mAutoDismiss) {
                dismiss();
            }

            if (v == mCancelView) {
                if (mListener != null) {
                    mListener.onCancel(getDialog());
                }
            }
        }

        /**
         * {@link BaseAdapter.OnItemClickListener}
         */
        @SuppressWarnings("all")
        @Override
        public void onItemClick(RecyclerView recyclerView, View itemView, int position) {
            if (mAutoDismiss) {
                dismiss();
            }

            if (mListener != null) {
                mListener.onSelected(getDialog(), position, mAdapter.getItem(position));
            }
        }
    }

    private static final class NoticeBehaveTypeAdapter extends MyAdapter<NoticeBehaveShowTypeBean> {

        private NoticeBehaveTypeAdapter(Context context) {
            super(context);
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ViewHolder();
        }

        final class ViewHolder extends MyAdapter.ViewHolder {

            private final ImageView mIconIV;
            private final TextView mTextView;
            private final View mLineView;

            ViewHolder() {
                super(R.layout.item_notice_behave_type);
                mIconIV = (ImageView) findViewById(R.id.iv_type_icon);
                mTextView = (TextView) findViewById(R.id.tv_type_name);
                mLineView = findViewById(R.id.v_type_line);
            }

            @Override
            public void onBindView(int position) {
                NoticeBehaveShowTypeBean item = getItem(position);
                mIconIV.setImageResource(item.getNoticeBehaveType().getIcon());
                mTextView.setText(item.getNoticeBehaveType().getInfo());

                if (position == 0) {
                    // 当前是否只有一个条目
                    if (getItemCount() == 1) {
                        mLineView.setVisibility(View.GONE);
                    } else {
                        mLineView.setVisibility(View.VISIBLE);
                    }
                } else if (position == getItemCount() - 1) {
                    mLineView.setVisibility(View.GONE);
                } else {
                    mLineView.setVisibility(View.VISIBLE);
                }
            }
        }
    }

    public interface OnListener<T> {

        /**
         * 选择条目时回调
         */
        void onSelected(BaseDialog dialog, int position, T t);

        /**
         * 点击取消时回调
         */
        default void onCancel(BaseDialog dialog) {
        }
    }
}