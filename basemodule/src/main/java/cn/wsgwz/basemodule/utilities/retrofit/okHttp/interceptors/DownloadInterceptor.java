package cn.wsgwz.basemodule.utilities.retrofit.okHttp.interceptors;


import androidx.annotation.NonNull;
import cn.wsgwz.basemodule.utilities.retrofit.okHttp.ProgressListener;
import cn.wsgwz.basemodule.utilities.retrofit.okHttp.ProgressResponseBody;
import okhttp3.Interceptor;
import okhttp3.Response;

import java.io.IOException;

public class DownloadInterceptor implements Interceptor {

    private ProgressListener downloadProgressListener;

    public DownloadInterceptor(@NonNull ProgressListener downloadProgressListener) {
        this.downloadProgressListener = downloadProgressListener;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Response originalResponse = chain.proceed(chain.request());

        return originalResponse.newBuilder()
                .body(new ProgressResponseBody(originalResponse.body(), downloadProgressListener))
                .build();
    }
}

