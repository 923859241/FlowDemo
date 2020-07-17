package com.example.flowdemo.presenter

import com.example.flowdemo.ui.ActivityView

class MVPp<V : ActivityView>  {
    protected var view:V
}