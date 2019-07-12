package cn.wsgwz.basemodule.utilities

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions

object GlideUtil{
    private const val TAG = "GlideUtil"
    fun bindImageFromUrl(view: ImageView, imageUrl: String?) {
        LLog.d(TAG,imageUrl)
        if (!imageUrl.isNullOrEmpty()) {
            Glide.with(view.context)
                .load(imageUrl)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(view)
        }
    }
}