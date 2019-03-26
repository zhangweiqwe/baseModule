package cn.wsgwz.basemodule.utilities.manager

import android.content.Context
import android.os.Environment
import cn.wsgwz.basemodule.data.ProgressInfo
import cn.wsgwz.basemodule.utilities.retrofit.okHttp.interceptors.DownloadInterceptor
import cn.wsgwz.basemodule.utilities.retrofit.okHttp.ProgressListener
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import okhttp3.*
import java.io.File
import java.lang.Exception

class DownloadManager private constructor() {


    fun download(
        context: Context,
        request: Request,
        fileName: String,
        disposableObserver: DisposableObserver<ProgressInfo>
    ) {
        return download(
            File(context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS), fileName),
            request,
            disposableObserver
        )
    }

    fun download(
        outputFile: File,
        request: Request
        , disposableObserver: DisposableObserver<ProgressInfo>
    ) {

        val observable = Observable.create(ObservableOnSubscribe<ProgressInfo> {
            val progressListener =
                ProgressListener { bytesRead, contentLength, done ->
                    it.onNext(
                        ProgressInfo(
                            bytesRead,
                            contentLength
                        )
                    )
                }

            val result = OkHttpClient.Builder().addInterceptor(DownloadInterceptor(progressListener))
                .build().newCall(request).execute()
            if (result.isSuccessful) {
                result.body().also { responseBody ->
                    if (responseBody == null) {
                        it.onError(Exception("responseBody == null"))
                    } else {
                        try {
                            FileManager.getInstance()
                                .saveFile(responseBody.byteStream(), outputFile)
                            it.onComplete()
                        } catch (e: Exception) {
                            e.printStackTrace()
                            if (!it.isDisposed) {
                                it.onError(e)
                            }
                        }

                    }
                }
            } else {
                //Logger.d(result.message())
                it.onError(Exception("!result.isSuccessful"))
            }


        })



        observable.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(disposableObserver)


    }

    companion object {
        // For Singleton instantiation
        @Volatile
        private var instance: DownloadManager? = null

        fun getFile(context: Context, fileName: String): File? {
            return File(context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS), fileName).let {
                if(!it.exists()){
                    null
                }else{
                    it
                }
            }
        }

        fun getInstance() =
            instance ?: synchronized(this) {
                instance ?: DownloadManager().also { instance = it }
            }
    }
}