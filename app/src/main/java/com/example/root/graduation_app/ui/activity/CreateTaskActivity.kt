@file:Suppress("RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")

package com.example.root.graduation_app.ui.activity

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AlertDialog
import android.text.TextUtils
import com.example.base_library.base_mvp.BaseMvpActivity
import com.example.base_library.base_utils.ToastUtils
import com.example.root.graduation_app.App
import com.example.root.graduation_app.R
import com.example.root.graduation_app.bean.TodoBean
import com.example.root.graduation_app.mvp.constract.TaskContract
import com.example.root.graduation_app.mvp.presenter.TaskPresenter
import com.example.root.graduation_app.utils.CommonUtils
import com.jaeger.library.StatusBarUtil
import kotlinx.android.synthetic.main.activity_task.*

/**
 *  author:Jiwenjie
 *  email:278630464@qq.com
 *  time:2019/02/04
 *  desc:点击新建任务或者笔记
 *  version:1.0
 */
class CreateTaskActivity : BaseMvpActivity<TaskContract.View, TaskPresenter>(), TaskContract.View {

   private var isEdit: Boolean? = null   // 标志位，判断是新建或是详情
   private var id: String? = null  // id

   companion object {
      private const val KEY_ID = "key_id"
      private const val KEY_IS_EDIT = "is_edit"

      @JvmStatic
      fun runActivity(activity: Activity, id: String?, isEdit: Boolean) {
         val intent = Intent(activity, CreateTaskActivity::class.java)
         intent.putExtra(KEY_ID, id)
         intent.putExtra(KEY_IS_EDIT, isEdit)
         activity.startActivity(intent)
      }

      @JvmStatic
      fun runActivity(activity: Activity, id: String?, reqCode: Int, isEdit: Boolean) {
         val intent = Intent(activity, CreateTaskActivity::class.java)
         intent.putExtra(KEY_ID, id)
         intent.putExtra(KEY_IS_EDIT, isEdit)
         activity.startActivityForResult(intent, reqCode)
      }
   }

   override fun initActivity(savedInstanceState: Bundle?) {

      StatusBarUtil.setColor(this@CreateTaskActivity,
              ContextCompat.getColor(this@CreateTaskActivity, R.color.colorPrimary), 0)

      id = intent.getStringExtra(KEY_ID)
      isEdit = intent.getBooleanExtra(KEY_IS_EDIT, false)
      if (isEdit!!) {
         activity_task_toolBar.title = "新建任务"
         activity_task_operation.text = "保存"
         activity_task_titleEditText.isEnabled = true
         activity_task_contentEditText.isEnabled = true
      } else {
         // 在任务详情状态需要把输入框关闭弹出
         CommonUtils.hideKeyboard(this, activity_task_titleEditText)
         CommonUtils.hideKeyboard(this, activity_task_contentEditText)
         activity_task_toolBar.title = "任务详情"
         activity_task_operation.text = "状态变更"
         activity_task_titleEditText.isEnabled = false
         activity_task_contentEditText.isEnabled = false
      }

      activity_task_toolBar.setOnClickListener {
         finish() // 点击返回
      }

      // 点击把当前任务状态从未完成改为已完成
      activity_task_operation.setOnClickListener {
         if (isEdit!!) {   // 点击保存
            save()
         } else { // 点击变更状态, 先弹出对话框进行二次确认
            confirm()
         }
      }
   }

   /**
    * 二次确认是否改变状态
    */
   private fun confirm() {
      AlertDialog.Builder(this@CreateTaskActivity)
              .setTitle("提示")
              .setMessage("确认变更操作吗？")
              .setNegativeButton("取消", null)
              .setPositiveButton("确定") { dialog, which ->
                 changeStatus()
              }
              .show()
   }


   @SuppressLint("SimpleDateFormat")
   private fun save() {
      when {
         activity_task_titleEditText.text.toString().trim().isEmpty() -> {
            ToastUtils.showToast(this, "标题不能为空")
         }
         activity_task_contentEditText.text.toString().trim().isEmpty() -> {
            ToastUtils.showToast(this, "内容不能为空")
         }
         else -> {
            showProgress("正在保存数据...")
            mPresenter.createNewTask(
                    App.getLoginUser()?.userid!!,
                    activity_task_titleEditText.text.toString().trim(),
                    activity_task_contentEditText.text.toString().trim())
         }
      }
   }

   private fun changeStatus() {
      if (id == null) return
      showProgress(null)
      mPresenter.changeStatus(App.getLoginUser()?.userid!!, id!!)
   }

   override fun loadData() {
      if (!isEdit!! && id != null) {
         // 说明此时是显示详情并且传递了参数 id
         mPresenter.getTaskDetail(App.getLoginUser()?.userid!!, id!!)   // 调用获取详情接口
      }
   }

   override fun initPresenter(): TaskPresenter = TaskPresenter(this)

   override fun createNewSuccess(msg: String) {
      dismissProgress()
      if (TextUtils.equals(msg, "succeed")) {
         ToastUtils.showToast(this, "新建成功")
         setResult(Activity.RESULT_OK)
         finish()
      } else {
         ToastUtils.showToast(this, "操作失败，请重试")
      }
   }

   /**
    * 获取详情成功之后把数据展示以及状态需要变化
    */
   override fun getDetailSuccess(bean: TodoBean) {
      activity_task_titleEditText.setText(bean.title)
      activity_task_contentEditText.setText(bean.content)
   }

   /**
    * 改变状态成功
    */
   override fun changeStatusSuccess(msg: String) {
      dismissProgress()
      if (msg == "succeed") {
         ToastUtils.showToast(this@CreateTaskActivity, "状态修改成功")
         setResult(Activity.RESULT_OK)
         finish()
      } else {
         ToastUtils.showToast(this@CreateTaskActivity, "操作失败")
      }
   }

   override fun error() {
      dismissProgress()
      // 操作失败的时候显示失败界面
      ToastUtils.showToast(this@CreateTaskActivity, "操作失败")
   }

   override fun getLayoutId(): Int = R.layout.activity_task
}