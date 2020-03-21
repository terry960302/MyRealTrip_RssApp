package com.ritier.myrealtrip_rssapp.View

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.ritier.myrealtrip_rssapp.R
import com.ritier.myrealtrip_rssapp.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {

    private lateinit var binding : ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail)
        binding.lifecycleOwner = this


        val intent= intent
        val url = intent.getStringExtra("url") as String

        binding.tvCenter.text = url
    }
}
