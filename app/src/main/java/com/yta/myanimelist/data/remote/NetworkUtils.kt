package com.yta.myanimelist.data.remote

import android.content.Context
import android.net.ConnectivityManager

class NetworkUtils(private val applicationContext: Context) {
    fun isNetworkAvailable(): Boolean {
        return try {
            val connectivityManager =
                applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager
            val networkInfo = connectivityManager?.activeNetworkInfo
            networkInfo?.isConnected ?: false
        } catch (_: Exception) {
            false
        }
    }
}