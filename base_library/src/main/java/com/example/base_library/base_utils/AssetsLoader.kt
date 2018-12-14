package com.example.base_library.base_utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader

/**
 *  author:Jiwenjie
 *  email:278630464@qq.com
 *  time:2018/12/14
 *  desc:文件加载工具类
 *  version:1.0
 */
object AssetsLoader {

    @JvmStatic
    fun getTextFromAssets(context: Context, file: String): String {
        var inputStream: InputStream? = null
        try {
            inputStream = context.resources.assets.open(file)
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return getText(inputStream)
    }

    private fun getText(inputStream: InputStream?): String {
        var inputReader: InputStreamReader? = null
        var bufferedReader: BufferedReader? = null
        val result = StringBuilder()
        try {
            inputReader = InputStreamReader(inputStream!!)
            bufferedReader = BufferedReader(inputReader)

            var line: String?
            do {
                line = bufferedReader.readLine()
                if (line != null) result.append(line) else break
            } while (true)

        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            inputStream?.close()

            bufferedReader?.close()

            inputReader?.close()
        }
        return result.toString()
    }


    @JvmStatic
    fun getImageFromAssets(context: Context, file: String): Bitmap? {
        var image: Bitmap? = null
        val am = context.resources.assets
        try {
            val inputStream = am.open(file)
            image = BitmapFactory.decodeStream(inputStream)
            inputStream.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return image
    }

}