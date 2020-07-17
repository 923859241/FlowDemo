package com.example.flowdemo.ui

import com.example.flowdemo.viewmodel.ApiDataBean

interface ActivityView {
    fun replayFlowData(infoList: List<ApiDataBean>)
}