package cn.wsgwz.myapplication.data;
import io.reactivex.Observable;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface BlogService {
    @POST("product/list")
    Observable<TR> getTr(@Body RequestBody requestBody);
         /* 「20160608补充」如果需要Header的值，可以把返回值替换为
            Observable<Response<Result<List<Blog>>>>
            Observable<retrofit2.adapter.rxjava.Result<Result<List<Blog>>>> //都叫Result，真是失策
    */

    @POST("product/list")
    Call<String> getTr2(@Body RequestBody requestBody);

}

