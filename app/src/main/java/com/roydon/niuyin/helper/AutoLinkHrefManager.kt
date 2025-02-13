package com.roydon.niuyin.helper

import android.text.TextUtils
import android.util.Log
import android.view.View
import com.roydon.niuyin.widget.autolinktextview.AutoLinkMode
import com.roydon.niuyin.widget.autolinktextview.AutoLinkTextView

object AutoLinkHrefManager {
    /**
     * 设置正文内容
     *
     * @param content
     */
    fun setContent(content: String?, autoLinkTextView: AutoLinkTextView) {
        if (TextUtils.isEmpty(content)) return
        autoLinkTextView.visibility = View.VISIBLE
        autoLinkTextView.addAutoLinkMode(
            AutoLinkMode.MODE_HASHTAG,
            AutoLinkMode.MODE_MENTION,
            AutoLinkMode.MODE_URL
        ) //设置需要富文本的模式
        autoLinkTextView.text = content
        autoLinkTextView.setAutoLinkOnClickListener { autoLinkMode: AutoLinkMode?, matchedText: String ->
            when (autoLinkMode) {
                AutoLinkMode.MODE_HASHTAG -> Log.i("minfo", "话题 $matchedText")
                AutoLinkMode.MODE_MENTION -> Log.i("minfo", "at $matchedText")
                else -> {}
            }
        }
    }
}