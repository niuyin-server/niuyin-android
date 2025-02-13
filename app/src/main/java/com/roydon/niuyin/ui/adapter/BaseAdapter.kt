package com.roydon.niuyin.ui.adapter

import android.view.View
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.ListAdapter

abstract class BaseAdapter <VH : RecyclerView.ViewHolder, T> constructor(diffUtil: DiffUtil.ItemCallback<T>) : ListAdapter<T, VH>(diffUtil) {
    protected var mList: ArrayList<T> = ArrayList()
    private var itemClickListener: OnItemClickListener?= null

    fun setOnClickListener(itemClickListener: OnItemClickListener) {
        this.itemClickListener = itemClickListener
    }

    fun View.setOnItemClick(position: Int) {
        setOnClickListener {
            itemClickListener?.let {
                it.onItemClick(mList[position])
            }
        }
    }

    fun setList(list: List<T>) {
        mList.clear()
        mList.addAll(list)
        notifyDataSetChanged()
    }

    fun appendList(list: List<T>) {
        mList.addAll(list)
        notifyDataSetChanged()
    }

    fun getDatas(): ArrayList<T> {
        return mList
    }

    override fun getItemCount() = mList.size

    interface OnItemClickListener {
        fun <E> onItemClick(e: E)
    }
}