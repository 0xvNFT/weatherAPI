package com.thoitietsbtybinh.sbty.ui.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.thoitietsbtybinh.sbty.R
import kotlinx.android.synthetic.main.activity_home.*

class LoobAct : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        gioithieu!!.setOnClickListener { startActivity(Intent(this@LoobAct, ActStory::class.java)) }
        csbm!!.setOnClickListener { startActivity(Intent(this@LoobAct, KlimaAct::class.java)) }
        tc!!.setOnClickListener { startActivity(Intent(this@LoobAct, LashSPAct::class.java)) }
        ungdung!!.setOnClickListener {
            startActivity(
                Intent(
                    this@LoobAct,
                    MainActivity::class.java
                )
            )
        }
    }
}