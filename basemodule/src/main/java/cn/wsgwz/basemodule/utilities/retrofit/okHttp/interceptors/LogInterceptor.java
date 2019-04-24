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

import android.net.Uri;

import cn.wsgwz.basemodule.data.RequestData;
import cn.wsgwz.basemodule.data.ResponseData;
import cn.wsgwz.basemodule.utilities.manager.NetworkDataManager;
import com.orhanobut.logger.Logger;
import okhttp3.*;

import java.io.IOException;


public class LogInterceptor
        implements okhttp3.Interceptor {
    private static final String TAG = "LogInterceptor";

    private NetworkDataManager networkDataManager = NetworkDataManager.Companion.getInstance();

    @Override
    public Response intercept(Chain chain) throws IOException {

        Request request = chain.request();

        String id = chain.hashCode() + "";

        RequestBody requestBody = request.body();

        Uri uri = Uri.parse(request.url().toString());
        Uri.Builder builder = uri.buildUpon();
        /*StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(request.url());*/
        if (requestBody instanceof FormBody) {
            /*stringBuilder.append("?");
            FormBody formBody = (FormBody) requestBody;
            for (int i = 0; i < formBody.size(); i++) {
                if (i != 0) {
                    stringBuilder.append("&");
                }
                stringBuilder.append(formBody.encodedName(i) + "=" + formBody.encodedValue(i));
            }*/
            FormBody formBody = (FormBody) requestBody;
            for (int i = 0; i < formBody.size(); i++) {
                builder.appendQueryParameter(formBody.encodedName(i),  formBody.encodedValue(i));
            }
        }

        RequestData requestData = new RequestData(id, builder.build());
        networkDataManager.addRequestData(requestData);
        Logger.t(TAG).d(requestData.getId() + "-->" + requestData.getUri().toString());


        Response response = chain.proceed(request);
        ResponseBody responseBody = response.body();
        ResponseData responseData = new ResponseData(id, responseBody.bytes(), response.code());
        networkDataManager.addResponseData(responseData);
        networkDataManager.notifyOnResponseItemDataChange(id);
        if (responseData.getByteArray() != null) {
            Logger.t(TAG).d(id + "<---" + responseData.getCode() + "--" + new String(responseData.getByteArray()));
        }

        return response.newBuilder().body(ResponseBody.create(responseBody.contentType(), responseData.getByteArray())).build();

    }
}
