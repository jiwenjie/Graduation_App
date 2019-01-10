package com.example.root.graduation_app.mvp.constract

import com.example.root.graduation_app.base.IBaseView
import com.example.root.graduation_app.bean.DoubanBookBean
import com.example.root.graduation_app.bean.DoubanBookItemDetail
import io.reactivex.Observable

/**
 *  author:Jiwenjie
 *  email:278630464@qq.com
 *  time:2019/01/10
 *  desc:douban 的契约类
 *  version:1.0
 */
interface DoubanContract {

   /**
    * book model 的接口
    */
   interface DoubanBookModel {
      fun getBookListWithTag(tag: String, start: Int, count: Int): Observable<DoubanBookBean>

      fun getBookDetail(id: String): Observable<DoubanBookItemDetail>
   }

   /**
    * book view 的接口
    */
   interface DoubanBookView: IBaseView {
      fun updateDoubanContentList(itemList: ArrayList<DoubanBookItemDetail>)
   }

   /**
    * book presenter 接口
    */
   interface DoubanBookPresenter {
      fun loadBookList(tag: String, start: Int, count: Int)
   }
}