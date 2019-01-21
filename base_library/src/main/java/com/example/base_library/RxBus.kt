package com.example.base_library

import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Action
import io.reactivex.functions.Consumer
import io.reactivex.internal.functions.Functions
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject

/**
 *  author:Jiwenjie
 *  email:278630464@qq.com
 *  time:2019/01/20
 *  desc:
 *  version:1.0
 */
class RxBus private constructor() {

   private var mSubscriptionMap = HashMap<String, CompositeDisposable>()
   private val mSubject = PublishSubject.create<Any>().toSerialized()

   fun post(event: Any) = mSubject.onNext(event)

   fun hasObservers(): Boolean = mSubject.hasObservers()

   fun <T> getObserver(type: Class<T>): Flowable<T> = mSubject
           .toFlowable(BackpressureStrategy.BUFFER)
           .ofType(type)

   private fun <T> doSubscribe(type: Class<T>, next: Consumer<T>? = null,
                               error: Consumer<Throwable>? = null,
                               complete: Action? = null): Disposable =
           getObserver(type)
                   .subscribeOn(Schedulers.io())
                   .observeOn(AndroidSchedulers.mainThread())
                   .subscribe(
                           next ?: Functions.emptyConsumer(),
                           error ?: Functions.ON_ERROR_MISSING,
                           complete ?: Functions.EMPTY_ACTION)

   private fun addSubscription(any: Any, disposable: Disposable) {
      val key = any.javaClass.simpleName

      if (mSubscriptionMap[key] != null)
         mSubscriptionMap[key]!!.add(disposable)
      else {
         val disposableList = CompositeDisposable()
         disposableList.add(disposable)
         mSubscriptionMap[key] = disposableList
      }
   }

   fun <T> register(any: Any, type: Class<T>, next: Consumer<T>? = null,
                    error: Consumer<Throwable>? = null, complete: Action? = null) =
           addSubscription(any, doSubscribe(type, next, error, complete))

   fun register(any: Any, disposable: Disposable) = this.addSubscription(any, disposable)

   fun unregister(any: Any) {
      val key = any.javaClass.simpleName

      if (!mSubscriptionMap.containsKey(key))
         throw IllegalStateException("register event before unregister")
      else {
         mSubscriptionMap[key]?.dispose()
         mSubscriptionMap.remove(key)
      }
   }

   companion object {
      val mBus: RxBus by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) { RxBus() }
      private const val TAG = "RxBus"
   }
}