package com.example.flowdemo.presenter

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import com.example.coolweather.util.HttpUtil
import com.example.flowdemo.ui.FlowActivity
import com.example.flowdemo.viewmodel.ApiDataBean
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.annotations.NonNull
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.ObservableOnSubscribe
import io.reactivex.rxjava3.schedulers.Schedulers
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import java.io.IOException


class ApiFlowPresenter(lifecycle:Lifecycle):IPresenter{

    lateinit var activity: FlowActivity

    private val dataURL = "http://w-oppo.myzhiniu.com/nav/yylive/idx"
    companion object {
        const val TAG = "GestureSelectPresenter"

        fun createPresenter(lifecycle:Lifecycle):ApiFlowPresenter{
            return ApiFlowPresenter(lifecycle)
        }
    }

    /**
     * 创建资源等 获取信息
     * @param owner
     */
    override fun onCreate(owner: LifecycleOwner) {
        getObservable().subscribe {
            replayFlowData(it)
        }
    }

    override fun onResume(owner: LifecycleOwner) {
    }

    override fun onPause(owner: LifecycleOwner) {
    }

    override fun onStop(owner: LifecycleOwner) {
    }

    //处理可能网络出现异常等情况的问题，不再回调
    override fun onDestroy(owner: LifecycleOwner) {

    }

    fun getObservable():@NonNull Observable<List<ApiDataBean>> {
        return Observable.create(ObservableOnSubscribe<String> {
            //加上观察者逻辑
            HttpUtil.sendOkHttpRequest(dataURL,object:Callback{
                override fun onFailure(call: Call, e: IOException) {
                    e.printStackTrace()
                }
                override fun onResponse(call: Call, response: Response) {
                    return
                }
            })
        }).flatMap {
            Observable.just(handleCityResponse(it))
        }.subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread())
    }

    fun handleCityResponse(response:String): List<ApiDataBean>{

    }

}