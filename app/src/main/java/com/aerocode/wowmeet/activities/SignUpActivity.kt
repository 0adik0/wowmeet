package com.aerocode.wowmeet.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.aerocode.wowmeet.R
import kotlinx.android.synthetic.main.activity_gender.nextButton
import kotlinx.android.synthetic.main.activity_sign_in.editTextEmail
import kotlinx.android.synthetic.main.activity_sign_in.editTextPassword
import kotlinx.android.synthetic.main.activity_sign_up.*
import java.util.regex.Matcher
import java.util.regex.Pattern

class SignUpActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        nextButton.setOnClickListener {
            when {
                editTextEmail.text.isNullOrEmpty() -> {
                    editTextEmail.error = "enter email"
                }
                editTextPassword.text.isNullOrEmpty() -> {
                    editTextPassword.error = "enter password"
                }
                editTextPassword.text.length<8 -> {
                    editTextPassword.error = "Password must be at least 8 characters"
                }
                editTextPasswordRep.text.isNullOrEmpty() -> {
                    editTextPassword.error = "enter password"
                }
                editTextPassword.text.toString() != editTextPasswordRep.text.toString() -> {
                    editTextPasswordRep.error = "passwords do not match"
                }
                !isEmailValid(editTextEmail.text.toString()) -> {
                    editTextEmail.error = "mail format is incorrect"
                }
                else -> {
                    startActivity(Intent(this, GenderActivity::class.java))
                }
            }
        }
    }

    private fun isEmailValid(email: String?): Boolean {
        val expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$"
        val pattern: Pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE)
        val matcher: Matcher = pattern.matcher(email)
        return matcher.matches()
    }

    fun singInClick(view: View) {
        startActivity(Intent(this, SignInActivity::class.java))
    }

    fun signUpClick(view: View) {
        startActivity(Intent(this, SignUpActivity::class.java))
    }

}