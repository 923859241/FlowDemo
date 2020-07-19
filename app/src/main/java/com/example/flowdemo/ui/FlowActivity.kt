package com.example.flowdemo.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.bumptech.glide.Glide
import com.example.flowdemo.R
import com.example.flowdemo.presenter.ApiFlowPresenter
import com.example.flowdemo.viewmodel.ApiDataBean

class FlowActivity : AppCompatActivity(),ActivityView{

    lateinit var presenter:ApiFlowPresenter

    var DataInfolist: MutableList<ApiDataBean> = mutableListOf<ApiDataBean>()
    lateinit var targetAdapter:DataAdapter
    init {
        //双向观察
        presenter= ApiFlowPresenter.createPresenter(lifecycle)
        lifecycle.addObserver(presenter)
        presenter.onTakeView(this)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.flow_view)


        val targetRecyclerView = findViewById<RecyclerView>(R.id.FlowRecyclerview)
        targetAdapter = DataAdapter(this,DataInfolist)
        val targetManager = StaggeredGridLayoutManager(3,
            StaggeredGridLayoutManager.VERTICAL)
        targetRecyclerView.adapter = targetAdapter
        targetRecyclerView.layoutManager = targetManager
    }


    class DataAdapter(private val context: Context, private var dataList:MutableList<ApiDataBean>) :
        RecyclerView.Adapter<DataViewHolder>(){

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
            val view = LayoutInflater.from(context).inflate(R.layout.flow_view_item,parent,false)
            return DataViewHolder(view)
        }

        override fun getItemCount(): Int {
            return dataList.size
        }

        override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
            val targetData = dataList[position]
            Glide.with(context)
                .load(targetData.actionImage)
                .into(holder.ImageView)
            holder.ImageView.setOnClickListener{
                val intent = Intent(context,WebViewActivity::class.java)
                intent.putExtra("deatilurl",targetData.actionURL)
                context.startActivity(intent)
            }
            holder.describeText.text = targetData.describe
            holder.nameText.text = targetData.name
        }
        fun dataFresh(infoList: List<ApiDataBean>){
            dataList.clear()
            dataList.addAll(infoList)
            notifyDataSetChanged()
        }

    }

    class DataViewHolder(itemView:View): RecyclerView.ViewHolder(itemView){
        var ImageView = itemView.findViewById<ImageView>(R.id.imageView)
        var nameText = itemView.findViewById<TextView>(R.id.nameText)
        var describeText = itemView.findViewById<TextView>(R.id.describeText)
    }


    override fun replayFlowData(infoList: List<ApiDataBean>) {
        //runOnUiThread{
            DataInfolist.clear()
            DataInfolist.addAll(infoList)
            targetAdapter.dataFresh(infoList)
        //}
    }

}