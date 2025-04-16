package com.raj.greendzine_assignment.Util

import android.content.Context
import android.net.ConnectivityManager

class ConnectionManager {
    @Suppress("DEPRECATION")
    fun checkConnection( context :Context):Boolean{

        val connectManager =  context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
         val activeNetwork = connectManager.activeNetworkInfo
        if (activeNetwork?.isConnected != null){
            return activeNetwork.isConnected
        }else{
            return false
        }
    }
}