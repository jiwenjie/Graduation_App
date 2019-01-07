package com.example.root.graduation_app.mvp.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.root.graduation_app.R

/**
 *  author:Jiwenjie
 *  email:278630464@qq.com
 *  time:2019/01/07
 *  desc:
 *  version:1.0
 */
class WeixinFragment : Fragment() {

   companion object {
      fun getInstance(): WeixinFragment {
         val fragment = WeixinFragment()
         val bundle = Bundle()
         fragment.arguments = bundle
         return fragment
      }
   }

   override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
      return LayoutInflater.from(activity!!).inflate(R.layout.fragment_weixin, container, false)

   }
}