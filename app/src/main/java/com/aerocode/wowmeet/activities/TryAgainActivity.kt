package com.aerocode.wowmeet.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import com.aerocode.wowmeet.R
import kotlinx.android.synthetic.main.activity_try_again.*

class TryAgainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_try_again)
        Handler(Looper.getMainLooper()).postDelayed({
            progress.visibility = View.INVISIBLE
            layout.visibility = View.VISIBLE
        }, 5000)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finishAffinity()
    }
}