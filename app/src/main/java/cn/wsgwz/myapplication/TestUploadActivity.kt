package cn.wsgwz.myapplication

import android.app.AlertDialog
import android.os.Bundle
import android.widget.FrameLayout
import cn.wsgwz.basemodule.utilities.DensityUtil
import cn.wsgwz.basemodule.utilities.retrofit.okHttp.ProgressListener
import cn.wsgwz.basemodule.utilities.retrofit.okHttp.ProgressRequestBody
import cn.wsgwz.basemodule.utilities.retrofit.service.CommonService
import cn.wsgwz.basemodule.widgets.ProgressView
import io.reactivex.disposables.Disposable
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.net.URLEncoder

class TestUploadActivity : AppBaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_upload)

        val files = ArrayList<File>()
        files.apply {
            add(File("/sdcard/aa/zr.jpg"))
        }

        val multipartBodyBuilder = MultipartBody.Builder()
        multipartBodyBuilder.setType(MultipartBody.FORM)
        for (file in files) {
            multipartBodyBuilder.addFormDataPart("file", URLEncoder.encode(file.name, "UTF-8"), RequestBody.create(MediaType.parse("image/jpeg"), file))
        }
        //multipartBodyBuilder.addFormDataPart("type", type)


        var disposable: Disposable? = null

        val progressView = ProgressView(this@TestUploadActivity)
        val alertDialog =
                AlertDialog.Builder(this@TestUploadActivity).setTitle(R.string.uploading).setView(FrameLayout(this@TestUploadActivity).apply {
                    addView(progressView)
                    setPadding(DensityUtil.dp2px(26f).toInt(), DensityUtil.dp2px(12f).toInt(), DensityUtil.dp2px(26f).toInt(), 0)
                })
                        .setCancelable(false)
                        .setOnDismissListener {
                            disposable?.also {
                                if (!it.isDisposed) {
                                    it.dispose()
                                }
                            }


                        }
                        .setPositiveButton(R.string.cancel, null).create()

        alertDialog.show()



        create(CommonService::class.java).upload("https://china185.com/uploadService/uploadFile", "1142271925825835010", ProgressRequestBody(multipartBodyBuilder.build(), ProgressListener {
            progressView.post{
                progressView.updateProgress(it)
            }
        }))
                .init()
                .subscribe({
                    alertDialog.dismiss()
                }, {
                    alertDialog.dismiss()
                }).add().also {
                    disposable = it
                }

    }
}
