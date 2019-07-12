package cn.wsgwz.basemodule.widgets

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ProgressBar
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import cn.wsgwz.basemodule.R
import cn.wsgwz.basemodule.data.ProgressInfo
import cn.wsgwz.basemodule.utilities.ProgressUtil

class ProgressView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {


    companion object {
        private const val BYTE_CONST = 1024
    }

    val progress_bar: ProgressBar
    private val progress_tv: TextView

    init {

        addView(LayoutInflater.from(context).inflate(R.layout.view_progress, this, false).apply {
            progress_bar = findViewById(R.id.progress_bar)
            progress_bar.tag = 100
            progress_tv = findViewById(R.id.progress_tv)
        })
    }


    fun updateProgress(progressInfo: ProgressInfo) {
        (progress_bar.tag as Int).also {
            progress_bar.max = (progressInfo.contentLength / it).toInt()
        }
        progress_tv.text = "${progressInfo.alreadySuccessLength / BYTE_CONST}/${progressInfo.contentLength / BYTE_CONST}kb"
        ProgressUtil.updateProgressToViewWithMark(progress_bar, progressInfo.alreadySuccessLength, true)

    }

}