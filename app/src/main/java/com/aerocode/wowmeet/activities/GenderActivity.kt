package com.aerocode.wowmeet.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.aerocode.wowmeet.R
import kotlinx.android.synthetic.main.activity_gender.*

class GenderActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gender)

        nextButton.setOnClickListener {
            startActivity(Intent(this, BirthdayActivity::class.java))
        }
        backButton.setOnClickListener {
            onBackPressed()
        }
    }

    fun singInClick(view: View) {
        startActivity(Intent(this, SignInActivity::class.java))
    }

    fun signUpClick(view: View) {
        startActivity(Intent(this, SignUpActivity::class.java))
    }
}