package com.ritier.myrealtrip_rssapp.View

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import java.util.*


class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Timer().schedule(object : TimerTask() {
            override fun run() {
                val intent  = Intent(applicationContext, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }, 1300)
    }
}
