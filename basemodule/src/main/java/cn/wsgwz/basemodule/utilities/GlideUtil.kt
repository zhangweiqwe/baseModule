package cn.wsgwz.basemodule.utilities

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.orhanobut.logger.Logger

object GlideUtil{
    private const val TAG = "GlideUtil"
    fun bindImageFromUrl(view: ImageView, imageUrl: String?) {

        Logger.t(TAG).d(imageUrl)
        if (!imageUrl.isNullOrEmpty()) {
            Glide.with(view.context)
                .load(imageUrl)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(view)
        }
    }
}