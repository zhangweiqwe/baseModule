package cn.wsgwz.basemodule.utilities.manager

import cn.wsgwz.basemodule.data.ProgressInfo
import cn.wsgwz.basemodule.utilities.retrofit.okHttp.ProgressListener
import cn.wsgwz.basemodule.utilities.retrofit.okHttp.ProgressRequestBody
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import okhttp3.*
import java.lang.Exception

/*

MultipartBody.Builder()
multipartBodyBuilder.addFormDataPart(PIC_KEY, f.name, RequestBody.create( MediaType.parse("image/jpeg"), f))
.addFormDataPart("type", type.type)
 */

class UploadManager private constructor() {


    fun upload(
        request: Request
        , disposableObserver: DisposableObserver<ProgressInfo>
    ) {

        Observable.create(ObservableOnSubscribe<ProgressInfo> {
            val downloadProgressListener =
                ProgressListener { bytesRead, contentLength, done ->
                    it.onNext(
                        ProgressInfo(
                            bytesRead,
                            contentLength
                        )
                    )
                }

            val result = OkHttpClient.Builder()
                .build().newCall(request.newBuilder().apply {
                    post(
                        ProgressRequestBody(
                            request.body(),
                            downloadProgressListener
                        )
                    )
                }.build()).execute()
            if (result.isSuccessful) {
                it.onComplete()
            } else {
                it.onError(Exception("!result.isSuccessful"))
            }


        }).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(disposableObserver)


    }

    companion object {
        // For Singleton instantiation
        @Volatile
        private var instance: UploadManager? = null

        fun getInstance() =
            instance ?: synchronized(this) {
                instance ?: UploadManager().also { instance = it }
            }
    }
}