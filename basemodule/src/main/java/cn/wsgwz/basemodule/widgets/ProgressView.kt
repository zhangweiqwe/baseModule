package cn.wsgwz.basemodule.widgets

import android.app.DownloadManager
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ProgressBar
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import cn.wsgwz.basemodule.R
import cn.wsgwz.basemodule.data.ProgressInfo
import cn.wsgwz.basemodule.utilities.DownloadsUtil
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

        addView(LayoutInflater.from(context).inflate(R.layout.view_progress, this,false).apply {
            progress_bar = findViewById(R.id.progress_bar)
            progress_bar.tag = 100
            progress_tv = findViewById(R.id.progress_tv)
        })
    }


    fun updateProgress(downloadInfo: DownloadsUtil.DownloadInfo) {
        (progress_bar.tag as Int).also {
            progress_bar.max = downloadInfo.totalSize / it
        }
        progress_tv.text = "${downloadInfo.bytesDownloaded / BYTE_CONST}/${downloadInfo.totalSize / BYTE_CONST}"
        ProgressUtil.updateProgressToViewWithMark(progress_bar, downloadInfo.bytesDownloaded.toLong(), true)

    }

}