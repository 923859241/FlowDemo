package com.example.coolweather.util

import com.example.flowdemo.viewmodel.ApiDataBean
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject

object HttpUtil {
    fun sendOkHttpRequest(address: String, callback: okhttp3.Callback) {
        val client = OkHttpClient()
        val request: Request = Request.Builder().url(address).build()
        client.newCall(request).enqueue(callback)
    }
    fun handleCityResponse(response:String): List<ApiDataBean>{
        var dataBeanList = mutableListOf<ApiDataBean>()
        try {
            val JSONresponse = JSONObject(response)
            val resultsData = JSONresponse.getJSONArray("data")
            //从多个object获取数据
            for (iObject in 1 until resultsData.length()){
                val iObjectData = resultsData.getJSONObject(iObject).getJSONArray("data")
                //获取每个object的多个data
                for(iData in 0 until iObjectData.length()){
                    var targetBean = ApiDataBean()
                    val targetData = iObjectData.getJSONObject(iData)
                    targetBean.name = targetData.getString("name")
                    targetBean.describe = targetData.getString("desc")
                    targetBean.actionImage = targetData.getString("coverPic")

/*                    //取超清
                    targetBean.actionURL = targetData.getJSONObject("video")
                        .getJSONArray("flv")
                        .getJSONObject(0)
                        .getString("videoUrl")
                        .toString()*/
                    targetBean.actionURL = targetData.getString("avatar")
                    dataBeanList.add(targetBean)
                }
            }

            //String weatherContent = cityData.getJSONObject("now").toString()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return dataBeanList
    }

}