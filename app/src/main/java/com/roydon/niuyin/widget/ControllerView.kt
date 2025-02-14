package com.roydon.niuyin.widget

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.RenderEffect
import android.graphics.Shader
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Build
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.animation.Animation
import android.view.animation.LinearInterpolator
import android.view.animation.RotateAnimation
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import butterknife.BindView
import com.airbnb.lottie.LottieAnimationView
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.roydon.niuyin.R
import com.roydon.niuyin.databinding.ViewControllerBinding
import com.roydon.niuyin.helper.AutoLinkHrefManager
import com.roydon.niuyin.helper.OnVideoControllerListener
import com.roydon.niuyin.http.glide.GlideApp
import com.roydon.niuyin.http.response.video.VideoRecommendVO
import com.roydon.niuyin.utils.NumUtils
import com.roydon.niuyin.widget.autolinktextview.AutoLinkTextView

/**
 *
 */
class ControllerView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null) :
    RelativeLayout(context, attrs), View.OnClickListener {
    private var listener: OnVideoControllerListener? = null
    private var videoData: VideoRecommendVO? = null

    private var binding: ViewControllerBinding =
        ViewControllerBinding.inflate(LayoutInflater.from(context), this, true)

    init {
        init()
    }

    private fun init() {
        binding.ivHead.setOnClickListener(this)
        binding.tvCommentcount.setOnClickListener(this)
        binding.tvSharecount.setOnClickListener(this)
        binding.tvLikecount.setOnClickListener(this)
        binding.ivFocus.setOnClickListener(this)
        setRotateAnim()
    }

    @SuppressLint("SetTextI18n")
    fun setVideoData(videoData: VideoRecommendVO) {
        this.videoData = videoData
        // 设置 binding.rlContainerVideo 背景
//        val blurRadius = 10f // 模糊半径
//        GlideApp.with(this)
//            .asBitmap() // 加载为 Bitmap
//            .load(videoData.coverImage)
//            .into(object : CustomTarget<Bitmap>() {
//                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
//                    // 将 Bitmap 设置为 RelativeLayout 的背景
//                    binding.rlContainerVideo.background = BitmapDrawable(resources, resource)
//
//                    // 如果设备支持 RenderEffect，应用模糊效果
//                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
//                        val renderEffect = RenderEffect.createBlurEffect(
//                            blurRadius, // X 轴模糊半径
//                            blurRadius, // Y 轴模糊半径
//                            Shader.TileMode.CLAMP
//                        )
//                        binding.rlContainerVideo.setRenderEffect(renderEffect)
//                    } else {
//                        // 如果设备版本低于 Android 12，显示原图或提示用户
//                        binding.rlContainerVideo.setBackgroundColor(resources.getColor(android.R.color.darker_gray))
//                    }
//                }
//
//                override fun onLoadCleared(placeholder: Drawable?) {
//                    // 在图片加载被清除时调用，可以设置一个占位图
//                }
//            })
        GlideApp.with(context).load(videoData.author!!.avatar).circleCrop().into(binding.ivHead)
        binding.tvNickname.text = "@" + videoData.author!!.nickName
        AutoLinkHrefManager.setContent(videoData.videoTitle, binding.autoLinkTextView)
        GlideApp.with(context).load(videoData.author!!.avatar).into(binding.ivHeadAnim)
        binding.tvLikecount.text = NumUtils.numberFilter(videoData.likeNum)
        binding.tvCommentcount.text = NumUtils.numberFilter(videoData.commentNum)
        binding.tvSharecount.text = NumUtils.numberFilter(videoData.favoriteNum)
        binding.animationView.setAnimation("like.json")

        //点赞状态
//        if (videoData.isLiked) {
        // 随机数 》5
        if (Math.random() > 0.5) {
            binding.ivLike.setTextColor(resources.getColor(R.color.like_icon))
        } else {
            binding.ivLike.setTextColor(resources.getColor(R.color.white))
        }
        //关注状态
//        if (videoData.isFocused) {
        if (Math.random() > 0.5) {
            binding.ivFocus.visibility = GONE
        } else {
            binding.ivFocus.visibility = VISIBLE
        }
    }

    fun setListener(listener: OnVideoControllerListener?) {
        this.listener = listener
    }

    override fun onClick(v: View) {
        if (listener == null) {
            return
        }
        when (v.id) {
            R.id.ivHead -> listener!!.onHeadClick()
            R.id.rlLike -> {
                listener!!.onLikeClick()
                like()
            }

            R.id.ivComment -> listener!!.onCommentClick()
            R.id.ivShare -> listener!!.onShareClick()
//            R.id.ivFocus -> if (!videoData!!.isFocused) {
//                videoData!!.isLiked = true
//                binding.ivFocus!!.visibility = GONE
//            }
        }
    }

    /**
     * 点赞动作
     */
    fun like() {
//        if (!videoData!!.isLiked) {
//            //点赞
//            binding.animationView.visibility = VISIBLE
//            binding.animationView.playAnimation()
//            binding.ivLike.setTextColor(resources.getColor(R.color.like_icon))
//        } else {
//            //取消点赞
//            binding.animationView!!.visibility = INVISIBLE
//            binding.ivLike!!.setTextColor(resources.getColor(R.color.white))
//        }
//        videoData!!.isLiked = !videoData!!.isLiked
    }

    /**
     * 循环旋转动画
     */
    private fun setRotateAnim() {
        val rotateAnimation = RotateAnimation(
            0f, 359f,
            Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f
        )
        rotateAnimation.repeatCount = Animation.INFINITE
        rotateAnimation.duration = 8000
        rotateAnimation.interpolator = LinearInterpolator()
        binding.rlRecord.startAnimation(rotateAnimation)
    }
}