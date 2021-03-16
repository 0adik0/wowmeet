package com.aerocode.wowmeet.activities

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.aerocode.wowmeet.R
import kotlinx.android.synthetic.main.activity_sign_in.*


class SignInActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        loginButton.setOnClickListener {
            when {
                editTextEmail.text.isNullOrEmpty() -> {
                    editTextEmail.error = "enter email"
                }
                editTextPassword.text.isNullOrEmpty() -> {
                    editTextPassword.error = "enter password"
                }
                else -> {
                    Toast.makeText(this, "“You are not registered yet", Toast.LENGTH_LONG).show()
                    Handler(Looper.getMainLooper()).postDelayed({
                        startActivity(
                            Intent(
                                this,
                                SignUpActivity::class.java
                            )
                        )
                    }, 1500)

                }
            }
        }
    }

    fun singInClick(view: View) {
        startActivity(Intent(this, SignInActivity::class.java))
    }

    fun signUpClick(view: View) {
        startActivity(Intent(this, SignUpActivity::class.java))
    }

}