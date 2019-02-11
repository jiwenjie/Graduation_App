package com.example.root.graduation_app.ui.activity

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import com.example.base_library.base_mvp.BaseMvpActivity
import com.example.base_library.base_utils.ToastUtils
import com.example.root.graduation_app.R
import com.example.root.graduation_app.bean.TodoBean
import com.example.root.graduation_app.mvp.constract.TaskContract
import com.example.root.graduation_app.mvp.presenter.TaskPresenter
import kotlinx.android.synthetic.main.activity_task.*
import java.text.SimpleDateFormat

/**
 *  author:Jiwenjie
 *  email:278630464@qq.com
 *  time:2019/02/04
 *  desc:点击新建任务或者笔记
 *  version:1.0
 */
class TaskActivity : BaseMvpActivity<TaskContract.View, TaskPresenter>(), TaskContract.View {

   private var isEdit : Boolean? = null   // 标志位，判断是新建或是详情
   private var id : Int? = null  // id

   companion object {
      private const val KEY_ID = "key_id"
      private const val KEY_IS_EDIT = "is_edit"

      @JvmStatic
      fun runActivity(activity: Activity, id: Int?, isEdit: Boolean) {
         val intent = Intent(activity, TaskActivity::class.java)
         intent.putExtra(KEY_ID, id)
         intent.putExtra(KEY_IS_EDIT, isEdit)
         activity.startActivity(intent)
      }

      @JvmStatic
      fun runActivity(activity: Activity, id: Int?, reqCode: Int, isEdit: Boolean) {
         val intent = Intent(activity, TaskActivity::class.java)
         intent.putExtra(KEY_ID, id)
         intent.putExtra(KEY_IS_EDIT, isEdit)
         activity.startActivityForResult(intent, reqCode)
      }
   }

   override fun initActivity(savedInstanceState: Bundle?) {
      id = intent.getIntExtra(KEY_ID, -1)
      isEdit = intent.getBooleanExtra(KEY_IS_EDIT, false)
      if (isEdit!!) {
         activity_task_toolBar.title = "新建任务"
         activity_task_operation.text = "保存"
         activity_task_titleEditText.isEnabled = true
         activity_task_contentEditText.isEnabled = true
      } else {
         activity_task_toolBar.title = "任务详情"
         activity_task_operation.text = "状态变更"
         activity_task_titleEditText.isEnabled = false
         activity_task_contentEditText.isEnabled = false
      }

      // 点击把当前任务状态从未完成改为已完成
      activity_task_operation.setOnClickListener {
         if (isEdit!!) {   // 点击保存
            save()
         } else { // 点击变更状态
            changeStatus()
         }
      }
   }

   @SuppressLint("SimpleDateFormat")
   private fun save() {
      when {
         activity_task_titleEditText.text.toString().trim().isNullOrEmpty() -> {
            ToastUtils.showToast(this, "标题不能为空")
         }
         activity_task_contentEditText.text.toString().trim().isNullOrEmpty() -> {
            ToastUtils.showToast(this, "内容不能为空")
         }
         else -> {
            showProgress("正在保存数据...")
            SimpleDateFormat("yyyy-MM-hh")
            mPresenter.createNewTask(
                    activity_task_titleEditText.text.toString().trim(),
                    activity_task_contentEditText.text.toString().trim(), "")
         }
      }
   }

   private fun changeStatus() {
      if (id == -1) return
      mPresenter.changeStatus(id!!, true)
   }

   override fun loadData() {
      if (!isEdit!! && id != -1) {
         // 说明此时是显示详情并且传递了参数 id
         mPresenter.getTaskDetail(id!!)
      }
   }

   override fun initPresenter(): TaskPresenter = TaskPresenter(this)

   override fun createNewSuccess(msg: String) {
      if (TextUtils.equals(msg, "success")) {
         dismissProgress()
         ToastUtils.showToast(this, "新建成功")
         setResult(Activity.RESULT_OK)
         finish()
      } else {
         ToastUtils.showToast(this, "操作失败，请重试")
      }
   }

   override fun getDetailSuccess(bean: TodoBean) {
      activity_task_titleEditText.setText(bean.title)
      activity_task_contentEditText.setText(bean.content)
   }

   /**
    * 改变状态成功
    */
   override fun changeStatusSuccess(msg: String) {
      dismissProgress()
      ToastUtils.showToast(this@TaskActivity, "状态修改成功")
   }

   override fun error() {
      // 操作失败的时候显示失败界面
      
   }

   override fun getLayoutId(): Int = R.layout.activity_task
}