package com.example.githubusers.util

import android.app.Service
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.githubusers.MainActivity

private val _isNeworkOff = MutableLiveData<Boolean>()
private val simNetworkRequest = NetworkRequest.Builder().addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET).build()
private val wifiNetworkRequest = NetworkRequest.Builder().addTransportType(NetworkCapabilities.TRANSPORT_WIFI).build()
private lateinit var connectivityManager: ConnectivityManager
private lateinit var simNetworkCallback: MyNetwork
private lateinit var wifiNetworkCallback: MyNetwork

object NetworkUtil {
    fun initConnectivityManager(context: Context) {
        connectivityManager = context.getSystemService(Service.CONNECTIVITY_SERVICE) as ConnectivityManager
        simNetworkCallback = MyNetwork()
        wifiNetworkCallback = MyNetwork()
    }

    fun registerNetworkCallbacks() {
        connectivityManager.registerNetworkCallback(simNetworkRequest, simNetworkCallback)
        connectivityManager.registerNetworkCallback(wifiNetworkRequest, wifiNetworkCallback)
    }

    fun unRegisterNetworkCallbacks() {
        connectivityManager.unregisterNetworkCallback(simNetworkCallback)
        connectivityManager.unregisterNetworkCallback(wifiNetworkCallback)
    }


    fun getNetWorkLiveData(): LiveData<Boolean> {
        return _isNeworkOff
    }

     fun restartApp(context: Context) {
        val intent = Intent(context, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(context, intent, null)
    }
}

private class MyNetwork: ConnectivityManager.NetworkCallback() {
    override fun onAvailable(network: Network) {
        super.onAvailable(network)
        if(_isNeworkOff.value == null || !_isNeworkOff.value!!) {
            _isNeworkOff.postValue(true)
        }
    }

    override fun onLosing(network: Network, maxMsToLive: Int) {
        super.onLosing(network, maxMsToLive)
    }

    override fun onLost(network: Network) {
        super.onLost(network)
        if(_isNeworkOff.value == null || _isNeworkOff.value!!) {
            _isNeworkOff.postValue(false)
        }
    }

    override fun onUnavailable() {
        super.onUnavailable()
    }
}