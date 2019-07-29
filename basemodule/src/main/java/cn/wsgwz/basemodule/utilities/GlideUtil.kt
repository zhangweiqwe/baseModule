package cn.wsgwz.basemodule.utilities

import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target

object GlideUtil {
    private const val TAG = "GlideUtil"
    fun bindImageFromUrl(view: ImageView, imageUrl: String?) {
        LLog.d(TAG, "${imageUrl.hashCode()} $imageUrl")
        val requestOptions = RequestOptions()
        Glide.with(view.context)
                .load(imageUrl)
                .listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
                        LLog.d(TAG,"${imageUrl.hashCode()} onLoadFailed" )
                        return false
                    }

                    override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                        LLog.d(TAG,"${imageUrl.hashCode()} onResourceReady")
                        return false
                    }

                })
                .transition(DrawableTransitionOptions.withCrossFade())
                /*.apply(requestOptions).apply {
                    if (view !is CircleImageView) {
                        transition(DrawableTransitionOptions.withCrossFade())
                    }
                }*/
                .apply(requestOptions)
                .into(view)

    }
}