package cn.wsgwz.basemodule.utilities.retrofit.converter.string;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import com.orhanobut.logger.Logger;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * author: wusongyuan
 * date: 2016.12.02
 * desc:
 */

public final class StringConverterFactory extends Converter.Factory {

    public static final String TAG = "StringConverterFactory";

    public static StringConverterFactory create() {
        return new StringConverterFactory();
    }

    private StringConverterFactory() {
    }

    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        Logger.t(TAG).i( "responseBodyConverter: ");

        if(String.class.equals(type))
        return new StringResponseBodyConverter<Object>();
        return null;
    }

    @Override
    public Converter<?, RequestBody> requestBodyConverter(Type type, Annotation[] parameterAnnotations,
                                                          Annotation[] methodAnnotations, Retrofit retrofit) {
        Logger.t(TAG).d( "requestBodyConverter: ");
        return new StringRequestBodyConverter<RequestBody>();
    }
}
