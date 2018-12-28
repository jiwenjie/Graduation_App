package com.example.root.graduation_app.test

import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.ImageView
import android.widget.Toast

import com.example.base_library.base_utils.ToastUtils
import com.example.root.graduation_app.R

import java.util.ArrayList

/**
 * author:Jiwenjie
 * email:278630464@qq.com
 * time:2018/12/17
 * desc:
 * version:1.0
 */
class TestOneActivity : AppCompatActivity() {


   //   @Override
   //   public void onClick(View v) {
   //      ToastUtils.showToast(getApplicationContext(), "点击", Toast.LENGTH_SHORT);
   //      switch (v.getId()) {
   //         case R.id.indexLyt: {
   //            ((ImageView) findViewById(R.id.indexLyt_img)).setImageResource(R.drawable.ic_home_selected);
   //            break;
   //         }
   //         case R.id.discoveryLyt: {
   //            ((ImageView) findViewById(R.id.discoveryLyt_img)).setImageResource(R.drawable.ic_discovery_selected);
   //            break;
   //         }
   //         case R.id.entertainmentLyt: {
   //            ((ImageView) findViewById(R.id.entertainmentLyt_img)).setImageResource(R.drawable.ic_hot_selected);
   //            break;
   //         }
   //         case R.id.mineLyt: {
   //            ((ImageView) findViewById(R.id.mineLyt_img)).setImageResource(R.drawable.ic_mine_selected);
   //            break;
   //         }
   //      }
   //   }

   private val REQUEST_PERMISSION = 100

   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
      setContentView(R.layout.activity_one)


   }

   fun reqPermissionResult(isAllGranted: Boolean, permission: Array<String>, reqCode: Int) {

   }

   /**
    * @param permissions
    * @param reqCode
    * @return true is indicate all permissions have been authorization(授权)
    * and false indicate some permissions have not been authorization
    */
   @JvmOverloads
   fun reqPermissions(permissions: Array<String>, reqCode: Int = REQUEST_PERMISSION): Boolean {
      val needReqPermissionList = ArrayList<String>()
      var hasNoAskPermission = false
      for (permission in needReqPermissionList) {
         if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
            needReqPermissionList.add(permission)
            /** if user check (don't ask again next time)  */
            if (!ActivityCompat.shouldShowRequestPermissionRationale(this, permission)) {
               hasNoAskPermission = true
            }
         }
      }
      if (needReqPermissionList.size == 0) {
         return true
      }

      if (hasNoAskPermission) {
         if (permissions.size == 1) {
            /** if only one permission, indication need execute an action  */
            AlertDialog.Builder(this)
                    .setTitle("提示")
                    .setMessage("当前操作所需权限已被禁止。\n\n请点击\"设置\"-\"权限\"-打开所需权限。")
                    .setNegativeButton("取消", null)
                    .setPositiveButton("设置") { dialog, which ->
                       val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                       intent.data = Uri.parse("package:$packageName")
                       startActivity(intent)
                    }
                    .show()
            return false
         }
      }

      ActivityCompat.requestPermissions(this,
              needReqPermissionList.toTypedArray(),
              reqCode)
      return false
   }

   override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
      super.onRequestPermissionsResult(requestCode, permissions, grantResults)
      var isAllGranted = true
      for (grantResult in grantResults) {
         if (grantResult != PackageManager.PERMISSION_GRANTED) {
            isAllGranted = false
         }
      }
      reqPermissionResult(isAllGranted, permissions, requestCode)
   }
}
