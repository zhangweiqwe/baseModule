package cn.wsgwz.basemodule.utilities.retrofit.okHttp;

import androidx.annotation.NonNull;

import cn.wsgwz.basemodule.data.ProgressInfo;

public interface ProgressListener {
    void update(@NonNull ProgressInfo progressInfo);
}
