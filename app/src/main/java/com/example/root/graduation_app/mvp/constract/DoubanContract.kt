package com.example.root.graduation_app.mvp.constract

import com.example.root.graduation_app.base.IBaseView
import com.example.root.graduation_app.bean.*
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
    * douban movie -> model
    */
   interface DoubanMovieModel {
      fun getDoubanHotMovie(): Observable<DoubanMovieBean>  // get hot movie

      fun getDoubanMovieDetail(id: String): Observable<DoubanMovieDetail>  // get movie detail

      fun getDoubanMovieTop250(start: Int, count: Int): Observable<DoubanMovieBean> // get movie top 250
   }

   /**
    * douban movie -> view
    */
   interface DoubanMovieView: IBaseView {
      fun updateDoubanContentList(subjectList: ArrayList<DoubanSubjectBean>)

      fun showDetail(bean: DoubanMovieDetail)
   }

   /**
    * douban movie -> presenter
    */
   interface DoubanMoviePresenter {
      fun getDoubanHotMovie()

      fun getDoubanMovieDetail(id: String)

      fun getDoubanMovieTop250(start: Int, count: Int)
   }


   /*********************************************************************************************/

   /**
    * douban book model
    */
   interface DoubanBookModel {
      fun getBookListWithTag(tag: String, start: Int, count: Int): Observable<DoubanBookBean>

      fun getBookDetail(id: String): Observable<DoubanBookItemDetail>
   }

   /**
    * book view
    */
   interface DoubanBookView: IBaseView {
      fun updateDoubanContentList(itemList: ArrayList<DoubanBookItemDetail>)
   }

   /**
    * book presenter
    */
   interface DoubanBookPresenter {
      fun loadBookList(tag: String, start: Int, count: Int)
   }
}