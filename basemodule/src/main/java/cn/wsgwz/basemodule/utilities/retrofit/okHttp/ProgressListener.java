package cn.wsgwz.basemodule.utilities.retrofit.okHttp;

public interface ProgressListener {
    void update(long bytesRead, long contentLength, boolean done);
}
