package cn.wsgwz.basemodule.interfaces

import android.app.Person
import android.content.ComponentName
import android.content.Context
import cn.wsgwz.basemodule.BaseConst
import cn.wsgwz.basemodule.BaseNetworkActivity
import cn.wsgwz.basemodule.BaseNetworkFragment
import cn.wsgwz.basemodule.utilities.retrofit.BaseResult
import cn.wsgwz.basemodule.utilities.retrofit.ResponseTransformer
import cn.wsgwz.basemodule.utilities.retrofit.RxSchedulers
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

interface BaseRetrofitInterface {


    enum class InitType {
        NO_HINT
    }





    fun <T> create(service: Class<T>): T {
        return BaseConst.RETROFIT.create(service)
    }

    fun <Z> Observable<BaseResult<Z>>.init(initType: InitType? = null): Observable<Z> {
        var isToastHint = true
        when (initType) {
            InitType.NO_HINT -> {
                isToastHint = false
            }
        }
        return compose(RxSchedulers.applySchedulers()).compose(ResponseTransformer.handleResult(isToastHint))
    }


    /*fun Disposable.add(): Disposable {
        compositeDisposable.add(this)
        return this
    }*/

    private inline fun <T, R> T.lett(block: (T) -> R): R {

        return block(this)
    }




}