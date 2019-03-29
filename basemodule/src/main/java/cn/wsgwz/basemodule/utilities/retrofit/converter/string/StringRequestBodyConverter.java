package cn.wsgwz.basemodule.utilities.retrofit.converter.string;

import java.io.IOException;

import com.orhanobut.logger.Logger;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Converter;

public final class StringRequestBodyConverter<T> implements Converter<T, RequestBody> {
    public static final String TAG = "RequestBodyConverter";

    private static final MediaType MEDIA_TYPE = MediaType.parse("application/json; charset=UTF-8");

    StringRequestBodyConverter() {

    }

    public RequestBody convert(T value) throws IOException {
        String v = value.toString();
        Logger.t(TAG).i( "convert: " + v);
        return RequestBody.create(MEDIA_TYPE, v);
    }
}