package com.example.flowdemo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.flowdemo.R
import com.example.flowdemo.ui.FlowActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        showFlowButton.setOnClickListener {
            val intent = Intent(this@MainActivity,FlowActivity::class.java)
            startActivity(intent)
        }

    }
}
