package com.example.flowdemo.ui

import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent

open class ActivityObserver(val lifecycle:Lifecycle):LifecycleObserver {
    companion object{
        const val TAG = "ActivityObserver"
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun activityStart(){
        Log.d(TAG,"activityStart")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun activityStop(){
        Log.d(TAG,"activityStop")
    }

}