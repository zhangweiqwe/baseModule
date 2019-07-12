package cn.wsgwz.basemodule.utilities.retrofit.service;

import java.util.ArrayList;

import cn.wsgwz.basemodule.utilities.retrofit.BaseResult;
import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Url;

/**
 * https://square.github.io/retrofit/
 */
public interface CommonService {
    @POST
    Observable<BaseResult<ArrayList<String>>> upload(
            @Url String url,
            @Header("token") String token,
            @Body() RequestBody requestBody);
}
