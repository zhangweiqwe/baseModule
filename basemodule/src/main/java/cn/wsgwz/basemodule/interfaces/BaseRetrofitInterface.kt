package cn.wsgwz.basemodule.interfaces

import cn.wsgwz.basemodule.BaseConst
import cn.wsgwz.basemodule.utilities.retrofit.BaseResult
import cn.wsgwz.basemodule.utilities.retrofit.ResponseTransformer
import cn.wsgwz.basemodule.utilities.retrofit.RxSchedulers
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

interface BaseRetrofitInterface {

    companion object {
        const val NO_HINT = 1
    }


    val requestCompositeDisposable: CompositeDisposable


    fun <T> BaseRetrofitInterface.create(service: Class<T>): T {
        return BaseConst.RETROFIT.create(service)
    }

    fun <Z> Observable<BaseResult<Z>>.init(type: Int? = null): Observable<Z> {
        var isToastHint = true
        when (type) {
            NO_HINT -> {
                isToastHint = false
            }
        }
        return compose(RxSchedulers.applySchedulers()).compose(ResponseTransformer.handleResult(isToastHint))
    }


    fun Disposable.add(): Disposable {
        requestCompositeDisposable.add(this)
        return this
    }

    private inline fun <T, R> T.lett(block: (T) -> R): R {

        return block(this)
    }


}