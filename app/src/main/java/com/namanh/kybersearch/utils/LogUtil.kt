package com.namanh.kybersearch.utils

import android.util.Log
import com.namanh.kybersearch.BuildConfig

object LogUtil {
    private const val TAG = "LogUtil"

    fun d(msg: String) {
        if (BuildConfig.DEBUG) {
            Log.d(TAG, msg)
        }
    }

    fun i(msg: String) {
        if (BuildConfig.DEBUG) {
            Log.i(TAG, msg)
        }
    }

    fun w(msg: String) {
        if (BuildConfig.DEBUG) {
            Log.w(TAG, msg)
        }
    }

    fun e(msg: String) {
        Log.e(TAG, msg)
    }
}