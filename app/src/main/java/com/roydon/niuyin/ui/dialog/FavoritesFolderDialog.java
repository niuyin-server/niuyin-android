package com.roydon.niuyin.ui.dialog;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hjq.base.BaseAdapter;
import com.hjq.base.BaseDialog;
import com.hjq.base.action.AnimAction;
import com.hjq.toast.ToastUtils;
import com.roydon.niuyin.R;
import com.roydon.niuyin.aop.SingleClick;
import com.roydon.niuyin.common.MyAdapter;
import com.roydon.niuyin.enums.FavoriteFolderShowStatusEnum;
import com.roydon.niuyin.http.response.behave.FavoriteFolderVO;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * @author roydon
 * @date 2024/4/23 22:50
 * @description 收藏夹弹窗
 */
public final class FavoritesFolderDialog {

    public static final class Builder extends BaseDialog.Builder<Builder> {

        private OnListener mListener;

        private final SelectAdapter mAdapter;

        public Builder(Context context) {
            super(context);

            setContentView(R.layout.dialog_favorites_folder);
            setAnimStyle(AnimAction.BOTTOM);
            setGravity(Gravity.BOTTOM);
            setHeight(getResources().getDisplayMetrics().heightPixels - (getResources().getDisplayMetrics().widthPixels * 10 / 16));
            setOnClickListener(R.id.tv_confirm);

            RecyclerView recyclerView = findViewById(R.id.rv_favorites_folder_list);
            recyclerView.setItemAnimator(null);

            mAdapter = new SelectAdapter(getContext());
            recyclerView.setAdapter(mAdapter);
        }

        public Builder setList(int... ids) {
            List<String> data = new ArrayList<>(ids.length);
            for (int id : ids) {
                data.add(getString(id));
            }
            return setList(data);
        }

        public Builder setList(FavoriteFolderVO... data) {
            return setList(Arrays.asList(data));
        }

        @SuppressWarnings("all")
        public Builder setList(List data) {
            mAdapter.setData(data);
            return this;
        }

        /**
         * 设置默认选中的位置
         */
        public Builder setSelect(List<Integer> positions) {
            mAdapter.setSelect(positions);
            return this;
        }

        /**
         * 设置最大选择数量
         */
        public Builder setMaxSelect(int count) {
            mAdapter.setMaxSelect(count);
            return this;
        }

        public Builder setListener(OnListener listener) {
            mListener = listener;
            return this;
        }

        @SingleClick
        @SuppressWarnings("all")
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.tv_confirm:
                    HashMap<Integer, FavoriteFolderVO> data = mAdapter.getSelectSet();
                    if (data.size() >= mAdapter.getMinSelect()) {
                        dismiss();
                        if (mListener != null) {
                            mListener.onSelected(getDialog(), data);
                        }
                    } else {
                        ToastUtils.show(String.format(getString(R.string.select_min_hint), mAdapter.getMinSelect()));
                    }
                    break;
                default:
                    break;
            }
        }
    }


    private static final class SelectAdapter extends MyAdapter<FavoriteFolderVO>
            implements BaseAdapter.OnItemClickListener {

        /**
         * 最小选择数量
         */
        private int mMinSelect = 1;
        /**
         * 最大选择数量
         */
        private int mMaxSelect = Integer.MAX_VALUE;

        /**
         * 选择对象集合
         */
        @SuppressLint("UseSparseArrays")
        private final HashMap<Integer, FavoriteFolderVO> mSelectSet = new HashMap<>();

        private SelectAdapter(Context context) {
            super(context);
            setOnItemClickListener(this);
        }

        @NonNull
        @Override
        public SelectAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new SelectAdapter.ViewHolder();
        }

        @SuppressLint("NotifyDataSetChanged")
        private void setSelect(List<Integer> positions) {
            for (int position : positions) {
                mSelectSet.put(position, getItem(position));
            }
            notifyDataSetChanged();
        }

        private void setMaxSelect(int count) {
            mMaxSelect = count;
        }

        private int getMinSelect() {
            return mMinSelect;
        }

        private HashMap<Integer, FavoriteFolderVO> getSelectSet() {
            return mSelectSet;
        }

        /**
         * {@link BaseAdapter.OnItemClickListener}
         */

        @SuppressLint("NotifyDataSetChanged")
        @Override
        public void onItemClick(RecyclerView recyclerView, View itemView, int position) {
            if (mSelectSet.containsKey(position)) {
                // 当前必须不是单选模式才能取消选中
//                if (!isSingleSelect()) {
                mSelectSet.remove(position);
                notifyItemChanged(position);
//                }
            } else {
                if (mMaxSelect == 1) {
                    mSelectSet.clear();
                    notifyDataSetChanged();
                }

                if (mSelectSet.size() < mMaxSelect) {
                    mSelectSet.put(position, getItem(position));
                    notifyItemChanged(position);
                } else {
                    ToastUtils.show(String.format(getString(R.string.select_max_hint), mMaxSelect));
                }
            }
        }

        final class ViewHolder extends MyAdapter.ViewHolder {

            private final TextView mTextView;
            private final TextView videoCountTV;
            private final TextView showStatusTV;
            private final CheckBox mCheckBox;

            ViewHolder() {
                super(R.layout.item_select_favorites_folder);
                mTextView = (TextView) findViewById(R.id.tv_title);
                videoCountTV = (TextView) findViewById(R.id.tv_video_count);
                showStatusTV = (TextView) findViewById(R.id.tv_show_status);
                mCheckBox = (CheckBox) findViewById(R.id.tv_select_checkbox);
            }

            @SuppressLint("SetTextI18n")
            @Override
            public void onBindView(int position) {
                FavoriteFolderVO item = getItem(position);
                mTextView.setText(item.getTitle());
                videoCountTV.setText(item.getVideoCount().toString());
                FavoriteFolderShowStatusEnum showStatusEnum = FavoriteFolderShowStatusEnum.findByCode(item.getShowStatus());
                showStatusTV.setText(showStatusEnum.getDesc());
                mCheckBox.setChecked(mSelectSet.containsKey(position));
                if (mMaxSelect == 1) {
                    mCheckBox.setClickable(false);
                } else {
                    mCheckBox.setEnabled(false);
                }
            }
        }
    }


    public interface OnListener<T> {

        /**
         * 选择回调
         *
         * @param data 选择的位置和数据
         */
        void onSelected(BaseDialog dialog, HashMap<Integer, T> data);

        /**
         * 取消回调
         */
        default void onCancel(BaseDialog dialog) {
        }
    }

}