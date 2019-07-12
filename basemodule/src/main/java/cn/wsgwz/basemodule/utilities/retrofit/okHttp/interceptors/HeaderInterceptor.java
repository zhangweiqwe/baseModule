/*
 * Copyright 2019 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package cn.wsgwz.basemodule.utilities.retrofit.okHttp.interceptors;

import android.text.TextUtils;

import cn.wsgwz.basemodule.data.User;
import cn.wsgwz.basemodule.utilities.LLog;
import cn.wsgwz.basemodule.utilities.manager.UserManager;
import okhttp3.*;

import java.io.IOException;

public class HeaderInterceptor
        implements Interceptor {
    private static final String TAG = "HeaderInterceptor";




    @Override
    public Response intercept(Chain chain) throws IOException {
        String token = UserManager.getCurrentUserToken();
        LLog.d(TAG, "token=" + token);

        Request request;
        if (token == null) {
            request = chain.request();
        } else {
            request = chain.request().newBuilder().addHeader("token", token).build();
        }
        return chain.proceed(request);

    }

}
