package cn.wsgwz.basemodule.utilities.retrofit;


import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.functions.Function;

public class ResponseTransformer {
    private static final String TAG = "ResponseTransformer";

    public static <T> ObservableTransformer<BaseResult<T>, T> handleResult(boolean isToastHint) {

        return upstream -> {
            ObservableSource observable = upstream
                    .onErrorResumeNext(new ErrorResumeFunction(isToastHint))
                    .flatMap(new ResponseFunction<T>(isToastHint));
            return observable;
        };

    }


    public static <T> ObservableTransformer<BaseResult<T>, T> handleResult() {
        return handleResult(true);
    }






    /**
     * 非服务器产生的异常，比如本地无无网络请求，Json数据解析错误等等。
     *
     * @param <T>
     */
    private static class ErrorResumeFunction<T> implements Function<Throwable, ObservableSource<T>> {
        private boolean isToastHint;

        public ErrorResumeFunction(boolean isToastHint) {
            this.isToastHint = isToastHint;
        }

        @Override
        public ObservableSource<T> apply(Throwable throwable) throws Exception {


            ObservableSource<T> observableSource = Observable.error(throwable);

            if (isToastHint) {
                RetrofitUtil.INSTANCE.onErrorToast(throwable);
            }


            return observableSource;
        }


    }


    /**
     * 服务其返回的数据解析
     * 正常服务器返回数据和服务器可能返回的exception
     *
     * @param <T>
     */
    private static class ResponseFunction<T> implements Function<BaseResult<T>, ObservableSource<T>> {
        private boolean isToastHint;

        public ResponseFunction(boolean isToastHint) {
            this.isToastHint = isToastHint;
        }

        @Override
        public ObservableSource<T> apply(BaseResult<T> t) throws Exception {
            if (t.getCode() == Code.SUCCESS.getCode()) {
                if(isToastHint){
                    RetrofitUtil.INSTANCE.onSuccessToast(t.getMsg());
                }
                return Observable.just(t.getData());
            } else {
                RetrofitUtil.INSTANCE.checkLogin(t.getCode());
                return Observable.error(new ServerException(t.getCode(), t.getMsg()));
            }
        }
    }


}

