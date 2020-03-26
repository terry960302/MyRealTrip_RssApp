package com.ritier.myrealtrip_rssapp.View

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.ritier.myrealtrip_rssapp.R
import com.ritier.myrealtrip_rssapp.Util.hasNetwork
import com.ritier.myrealtrip_rssapp.databinding.ActivitySplashBinding
import java.util.*


class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_splash)
        binding.lifecycleOwner = this
        binding.pgLoading.visibility = View.VISIBLE

        val intent = Intent(applicationContext, MainActivity::class.java)
        Timer().schedule(object : TimerTask() {
            override fun run() {
                if (hasNetwork(applicationContext)) {
                    startActivity(intent)
                    finish()
                } else {
                    runOnUiThread {
                        Toast.makeText(
                            applicationContext,
                            "네트워크 상태를 확인 후 다시 시작해주십시오.",
                            Toast.LENGTH_LONG

                        ).show()
                        binding.pgLoading.visibility = View.GONE
                    }
                }
            }
        }, 1300)
    }
}
