package cn.wsgwz.myapplication

import android.app.AlertDialog
import android.app.DownloadManager
import android.content.Context
import android.content.DialogInterface
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import cn.wsgwz.basemodule.BaseConst
import cn.wsgwz.basemodule.data.ProgressInfo
import cn.wsgwz.basemodule.utilities.ApkUtil
import cn.wsgwz.basemodule.utilities.DensityUtil
import cn.wsgwz.basemodule.utilities.DownloadsUtil
import cn.wsgwz.basemodule.utilities.LLog
import cn.wsgwz.basemodule.utilities.manager.FileManager
import cn.wsgwz.basemodule.widgets.ProgressView
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import org.apache.commons.codec.digest.DigestUtils
import java.io.File
import java.io.FileInputStream
import java.lang.Exception

class TestDownloadActivity : AppBaseActivity() {

    companion object {
        private const val TAG = "TestDownloadActivity"
    }

    private val updateFile by lazy {
        File(getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS), FileManager.UPDATE_FILE_NAME)

    }
    private var disposable: Disposable? = null
    private lateinit var alertDialog: AlertDialog
    private lateinit var mDownloadManager: DownloadManager
    private var downloadId = 0L

    private val progressView by lazy { ProgressView(this@TestDownloadActivity) }
    private val messageTV by lazy {
        TextView(this).apply {
            textSize = 16f
            setTextColor(Color.BLACK)
            text = "更新说明"
        }
    }

    private val customView by lazy {
        LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(DensityUtil.dp2px(26f).toInt(), DensityUtil.dp2px(12f).toInt(), DensityUtil.dp2px(26f).toInt(), 0)
            addView(messageTV)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_download)

        mDownloadManager = getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager


        alertDialog =
                AlertDialog.Builder(this@TestDownloadActivity).setTitle(R.string.update_prompt).setView(customView)
                        .setCancelable(false)
                        .setOnDismissListener {
                            disposable?.also {
                                if (!it.isDisposed) {
                                    it.dispose()
                                }
                            }

                            mDownloadManager.remove(downloadId)


                            LLog.d(TAG,"setOnDismissListener")
                        }
                        .setNegativeButton(R.string.cancel, null)
                        .create()
        alertDialog.show()

        alertDialog.apply {
            if (updateFile.exists() && DigestUtils.sha512Hex(FileInputStream(updateFile)).apply {
                        LLog.d(TAG, this)
                    } == "fdsa") {
                getButton(DialogInterface.BUTTON_POSITIVE).also {
                    it.visibility = View.VISIBLE
                    it.text = context.getString(R.string.install)
                    it.setOnClickListener {
                        install()
                        alertDialog.dismiss()
                    }
                }

            } else {
                getButton(DialogInterface.BUTTON_POSITIVE).also {
                    it.visibility = View.VISIBLE
                    it.text = context.getString(R.string.download)
                    it.setOnClickListener {
                        download()
                    }
                }
            }
        }


    }

    private fun install() {
        startActivity(ApkUtil.getInstallIntent(this, updateFile))
    }


    private fun download() {
        messageTV.visibility = View.GONE
        alertDialog.getButton(DialogInterface.BUTTON_POSITIVE).visibility = View.GONE
        customView.addView(progressView)

        val resource = Uri.parse(BaseConst.Url.APK_DOWNLOAD_URL)
        downloadId = mDownloadManager.enqueue(DownloadManager.Request(resource).apply {
            setDestinationInExternalFilesDir(this@TestDownloadActivity, Environment.DIRECTORY_DOWNLOADS, FileManager.UPDATE_FILE_NAME)
            setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE or DownloadManager.Request.NETWORK_WIFI)
            setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
            setVisibleInDownloadsUi(true)
            setTitle(getString(R.string.app_name))
        })


        Observable.create(ObservableOnSubscribe<DownloadsUtil.DownloadInfo> {
            while (true) {

                try {
                    Thread.sleep(100)
                } catch (e: Exception) {
                    e.printStackTrace()
                    break
                }

                val info = DownloadsUtil.getById(this@TestDownloadActivity, downloadId)
                if (info == null) {
                    it.onError(Throwable("info == null"))
                    //LLog.d(TAG, "-->1")
                    break

                } else if (info.status == DownloadManager.STATUS_FAILED) {
                    it.onError(Throwable(info.toString()))
                    //Logger.t(TAG).d(info)
                    break

                } else if (info.status == DownloadManager.STATUS_SUCCESSFUL) {
                    //Logger.t(TAG).d(info)
                    it.onComplete()
                    break
                } else {
                    it.onNext(info)
                    //LLog.d(TAG, "-->2")
                }

            }

        }).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(object : DisposableObserver<DownloadsUtil.DownloadInfo>() {
                    override fun onComplete() {
                        messageTV.visibility = View.VISIBLE
                        progressView.visibility = View.GONE
                        alertDialog.apply {
                            setTitle(R.string.download_success)
                            getButton(DialogInterface.BUTTON_POSITIVE).also {
                                it.visibility = View.VISIBLE
                                it.text = context.getString(R.string.install)
                                it.setOnClickListener {
                                    install()
                                }
                            }
                        }
                    }

                    override fun onNext(t: DownloadsUtil.DownloadInfo) {
                        if (t.totalSize <= 0 || t.status != DownloadManager.STATUS_RUNNING) {
                            alertDialog.setTitle(R.string.waiting_for_download)
                        } else {
                            alertDialog.setTitle(R.string.downloading)
                            progressView.updateProgress(ProgressInfo(t.bytesDownloaded.toLong(), t.totalSize.toLong()))
                        }
                    }

                    override fun onError(e: Throwable) {
                        alertDialog.setTitle(R.string.download_error)
                        progressView.visibility = View.GONE
                    }

                }.apply { disposable = this.add() })

    }
}
