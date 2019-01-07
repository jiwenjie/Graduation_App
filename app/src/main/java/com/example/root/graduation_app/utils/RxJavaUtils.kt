package com.example.root.graduation_app.utils

import io.reactivex.FlowableTransformer
import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


/**
 *  author:Jiwenjie
 *  email:278630464@qq.com
 *  time:2019/01/07
 *  desc:
 *  version:1.0
 */
object RxJavaUtils {

//   @JvmStatic
//   fun  observableToMain(): ObservableTransformer {
//
//      return ObservableTransformer{
//         upstream ->
//         upstream.subscribeOn(Schedulers.io())
//                 .observeOn(AndroidSchedulers.mainThread())
//      }
//   }
//
//   @JvmStatic
//   fun  flowableToMain(): FlowableTransformer {
//
//      return FlowableTransformer{
//         upstream ->
//         upstream.subscribeOn(Schedulers.io())
//                 .observeOn(AndroidSchedulers.mainThread())
//      }
//   }

   @JvmStatic
   fun <T> applyObservableAsync(): ObservableTransformer<T, T> {
      return ObservableTransformer { upstream ->
         upstream.subscribeOn(Schedulers.io())
                 .unsubscribeOn(Schedulers.io())
                 .observeOn(AndroidSchedulers.mainThread())
      }
   }
}
