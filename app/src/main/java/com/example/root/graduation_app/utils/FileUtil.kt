package com.example.root.graduation_app.utils

import android.annotation.SuppressLint
import android.os.Environment
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

/**
 *  author:Jiwenjie
 *  email:278630464@qq.com
 *  time:2019/01/30
 *  desc:
 *  version:1.0
 */
object FileUtil {
    private val rootFolderPath = Environment.getExternalStorageDirectory().absolutePath + File.separator + "Graduation"

    @SuppressLint("SimpleDateFormat")
    fun createImageFile(isCrop: Boolean = false): File? {
        return try {
            val rootFile = File(rootFolderPath + File.separator + "capture")
            if (!rootFile.exists()) {
                rootFile.mkdirs()
            }
            val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
            val fileName = if (isCrop) "IMG_${timeStamp}_CROP.jpg" else "IMG_$timeStamp.jpg"
            File(rootFile.absolutePath + File.separator + fileName)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    @SuppressLint("SimpleDateFormat")
    fun createCameraFile(folderName: String = "camera1"): File? {
        return try {
            val rootFile = File(rootFolderPath + File.separator + folderName)
            if (!rootFile.exists()) {
                rootFile.mkdirs()
            }
            val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
            val fileName = "IMG_$timeStamp.jpg"
            File(rootFile.absolutePath + File.separator + fileName)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    @SuppressLint("SimpleDateFormat")
    fun createVideoFile(): File? {
        return try {
            val rootFile = File(rootFolderPath + File.separator + "video")
            if (!rootFile.exists()) {
                rootFile.mkdirs()
            }
            val timeStamp =  SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
            val fileName = "VIDEO_$timeStamp.mp4"
            File(rootFile.absolutePath + File.separator + fileName)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    // 把头像文件存储到硬盘
    fun saveAvatarInDisk() {

    }

    // 把背景图片存储到硬盘
    fun saveBgInDisk() {

    }
}



















