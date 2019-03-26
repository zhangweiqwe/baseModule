package cn.wsgwz.basemodule

import io.reactivex.observers.DisposableObserver

abstract class CustomDisposableObserver<T>: DisposableObserver<T>() {
    override fun onComplete() {

    }
}