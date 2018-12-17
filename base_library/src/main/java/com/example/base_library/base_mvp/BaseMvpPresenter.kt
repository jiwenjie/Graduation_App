package com.example.base_library.base_mvp

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.OnLifecycleEvent
import android.util.Log
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

/**
 *  author:Jiwenjie
 *  email:278630464@qq.com
 *  time:2018/12/14
 *  desc:mvp presenter 基类，实现 lifecycle 用于绑定 Activity 和 Fragment 生命周期
 *  version:1.0
 */
abstract class BaseMvpPresenter<V : BaseMvpViewImpl>(view: V) : LifecycleObserver {

    /** 防止内存泄漏调用  */
    protected var compositeDisposable = CompositeDisposable()
    private val tag = BaseMvpPresenter::class.java.simpleName
    protected var mView: V? = view

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    protected open fun onCreate() {
        Log.d(tag, "onCreate")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    protected open fun onStart() {
        Log.d(tag, "onStart")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    protected open fun onResume() {
        Log.d(tag, "onResume")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    protected open fun onPause() {
        Log.d(tag, "onPause")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    protected open fun onStop() {
        Log.d(tag, "onStop")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    protected open fun onDestroy() {
        this.mView = null
        // 保证 activity 结束的时候取消所有正在执行的订阅
        if (!compositeDisposable.isDisposed) {
            compositeDisposable.clear()
        }
        Log.d(tag, "onDestroy")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_ANY)
    protected open fun onLifeChange(owner: LifecycleOwner, event: Lifecycle.Event) {
        Log.d(tag, "onLifeChange: ($owner, $event)")
    }

    /**
     * 添加 disposable, 主要防止 RxJava 内存泄漏
     */
    protected fun addSubscription(disposable: Disposable) {
        compositeDisposable.add(disposable)
    }
}