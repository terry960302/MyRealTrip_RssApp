package com.ritier.myrealtrip_rssapp.View

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.ritier.myrealtrip_rssapp.R
import com.ritier.myrealtrip_rssapp.Util.hasNetwork
import java.util.*


class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val intent = Intent(applicationContext, MainActivity::class.java)
        Timer().schedule(object : TimerTask() {
            override fun run() {
                if (hasNetwork(applicationContext)) {
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(
                        applicationContext,
                        "와이파이나 데이터를 킨 채 다시 시작해주십시오.",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }, 1300)
    }
}
