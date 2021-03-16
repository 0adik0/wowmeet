package com.aerocode.wowmeet.activities

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.aerocode.wowmeet.R
import kotlinx.android.synthetic.main.activity_birthday.*

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


class BirthdayActivity : AppCompatActivity() {

    private lateinit var myCalendar: Calendar
    private var errorMessage = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_birthday)

        myCalendar = Calendar.getInstance()

        val date =
            OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
                myCalendar.set(Calendar.YEAR, year)
                myCalendar.set(Calendar.MONTH, monthOfYear)
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                updateLabel()
            }

        birthday.setOnClickListener {

            DatePickerDialog(
                this, date, myCalendar
                    .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }
        nextButton.setOnClickListener {
            if (isValidDate(birthday.text.toString())) {
                startActivity(Intent(this, TryAgainActivity::class.java))
            } else {
                birthday.error = errorMessage
            }
        }
        backButton.setOnClickListener {
            onBackPressed()
        }
    }

    private fun isValidDate(date: String): Boolean {
        val sdf = SimpleDateFormat("MM/dd/yyyy")
        var testDate: Date? = null
        try {
            testDate = sdf.parse(date)
        } catch (e: ParseException) {
            errorMessage = "the date you provided is in an invalid date format."
            return false
        }
        if (sdf.format(testDate) != date) {
            errorMessage = "The date that you provided is invalid."
            return false
        }
        return true
    }

    private fun updateLabel() {
        val myFormat = "MM/dd/yyyy"
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        birthday.setText(sdf.format(myCalendar.time))

    }

    fun singInClick(view: View) {
        startActivity(Intent(this, SignInActivity::class.java))
    }

    fun signUpClick(view: View) {
        startActivity(Intent(this, SignUpActivity::class.java))
    }
}