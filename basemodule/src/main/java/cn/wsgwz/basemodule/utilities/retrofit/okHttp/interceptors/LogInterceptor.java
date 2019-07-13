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

import cn.wsgwz.baselibrary.retrofit.bean.RequestData;
import cn.wsgwz.baselibrary.retrofit.bean.ResponseData;
import cn.wsgwz.basemodule.utilities.LLog;
import cn.wsgwz.basemodule.utilities.manager.UserManager;
import cn.wsgwz.basemodule.widgets.suspension.NetworkDataManager;

import okhttp3.*;
import okio.Buffer;

import java.io.IOException;
import java.nio.charset.Charset;


public class LogInterceptor
        implements Interceptor {
    private static final String TAG = "LogInterceptor";
    private UserManager userManager = UserManager.getInstance();
    private NetworkDataManager networkDataManager = NetworkDataManager.getInstance();

    @Override
    public Response intercept(Chain chain) throws IOException {

        Request request = chain.request();

        String id = chain.hashCode() + "";

        RequestBody requestBody = request.body();


        Uri uri = Uri.parse(request.url().toString());
        Uri.Builder builder = uri.buildUpon();


        StringBuilder stringBuilder = new StringBuilder(id + "-->" + uri.toString());

        String token = userManager.getCurrentUserToken();
        if (token == null) {
            token = "";
        }
        if (requestBody != null) {


            builder.appendQueryParameter("token", token);
            if (requestBody instanceof FormBody) {
                FormBody formBody = (FormBody) requestBody;
                for (int i = 0; i < formBody.size(); i++) {
                    builder.appendQueryParameter(formBody.encodedName(i), formBody.encodedValue(i));
                }
            }


            String bufferStr = requestBody.contentLength() + "";
            if (requestBody.contentLength() <= 1024) {
                Buffer buffer = new Buffer();
                requestBody.writeTo(buffer);
                bufferStr = buffer.readString(Charset.forName("UTF-8"));
            }

            stringBuilder.append("\t\t" + bufferStr + "\t\t\t" + "token=" + token);
        }
        LLog.d(TAG, stringBuilder.toString());

        //Logger.t(TAG).d(URLDecoder.decode(stringBuilder.toString(),"utf-8"));


        RequestData requestData = new RequestData(id, builder.build(), request.method());
        networkDataManager.addRequestData(requestData);

        /*try {
            if (requestBody.contentLength() > 0) {
                requestBody.writeTo(buffer);
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }*/


        Response response = chain.proceed(request);
        ResponseBody responseBody = response.body();
        ResponseData responseData = new ResponseData(id, responseBody.bytes(), response.code(), response.isSuccessful());
        networkDataManager.addResponseData(responseData);
        networkDataManager.notifyOnResponseItemDataChange(id);
        if (responseData.getByteArray() != null) {
            LLog.d(TAG, id + "<---" + responseData.getCode() + "--" + new String(responseData.getByteArray()));
        }

        return response.newBuilder().body(ResponseBody.create(responseBody.contentType(), responseData.getByteArray())).build();
    }


}

