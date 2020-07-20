package com.example.flowdemo.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.coolweather.util.HttpUtil
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.ObservableOnSubscribe
import io.reactivex.rxjava3.schedulers.Schedulers
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import java.io.IOException

class ApiViewModel:ViewModel() {
    var counter = 0
    var liveData = MutableLiveData<List<ApiDataBean>>()

    private var dataURL = "http://w-oppo.myzhiniu.com/nav/yylive/idx"

    fun getData(){
        //加上观察者逻辑
        HttpUtil.sendOkHttpRequest(dataURL,object: Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
            }
            override fun onResponse(call: Call, response: Response) {
                getObservable(response.body!!.string())
            }
        })
    }

    fun getObservable(responseData:String){
        Observable.create(ObservableOnSubscribe<String> {
            it.onNext(responseData)
        }).flatMap {
            Observable.just(HttpUtil.handleCityResponse(it))
        }.subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread()).subscribe{
            liveData.value = it
        }

    }


}