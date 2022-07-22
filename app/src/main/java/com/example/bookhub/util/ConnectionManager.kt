package com.example.bookhub.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import java.net.NetworkInterface

class ConnectionManager {

    fun checkConnectivity(conext: Context) : Boolean {

        val connectivityManager = conext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val activeNetwork: NetworkInfo? = connectivityManager.activeNetworkInfo

        if(activeNetwork?.isConnected != null){

            return activeNetwork.isConnected
        }
        else{

            return false
        }
    }
}