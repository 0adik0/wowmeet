package com.aerocode.wowmeet

import android.app.Application
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceManager
import com.aerocode.wowmeet.activities.SignInActivity
import com.appsflyer.AppsFlyerConversionListener
import com.appsflyer.AppsFlyerLib
import com.facebook.FacebookSdk
import com.facebook.applinks.AppLinkData
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.result.Result
import kotlinx.android.synthetic.main.activity_splash.*
import org.json.JSONObject


const val IS_FIRST = "IS_FIRST"
const val IS_SECOND = "IS_SECOND"
const val SHARED_PREFS_URL = "SHARED URL"

class SplashActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val isFirstTime =
            PreferenceManager.getDefaultSharedPreferences(this).getBoolean(IS_FIRST, false)
        val secondTime =
            PreferenceManager.getDefaultSharedPreferences(this).getBoolean(IS_SECOND, false)
        setContentView(R.layout.activity_splash)
//
        progress_bar.visibility = View.VISIBLE

        initAppsFlyer()
        getFBDeep()
        PreferenceManager.getDefaultSharedPreferences(this).edit()
            .putString("APPS_FLYER", AppsFlyerLib.getInstance().getAppsFlyerUID(this)).apply()
        if (!isFirstTime)
            callServer()
        else {
            if (secondTime) {
                startActivity(Intent(this, WebActivity::class.java))
                finish()
            } else {
                startActivity(Intent(this, SignInActivity::class.java))
                finish()
            }
        }


    }


    private fun callServer() {
        PreferenceManager.getDefaultSharedPreferences(this).edit().putBoolean(
            IS_FIRST, true
        ).apply()
        val httpAsync = "https://trk.wowmeet.xyz/KTsvp8QQ?x=1"
            .httpGet()
            .timeout(20000)
            .header("Content-Type", "application/json; utf-8")
            .responseString { _, _, result ->
                when (result) {
                    is Result.Failure -> {
                        Log.e("Error", "response error ${result.getException()}")
                        startActivity(Intent(this, SignInActivity::class.java))
                        finish()
                    }
                    is Result.Success -> {
                        val data = result.get()
                        println(data)
                        Log.e("Succses", "response success ${data}")
                        //fetchResult(data)
                        if (data == "done") {
                            PreferenceManager.getDefaultSharedPreferences(this).edit().putBoolean(
                                IS_SECOND, true
                            ).apply()
                            secondCall()
                            startActivity(Intent(this, WebActivity::class.java))
                            finish()
                        } else {
                            PreferenceManager.getDefaultSharedPreferences(this).edit().putBoolean(
                                IS_SECOND, false
                            ).apply()
                            startActivity(Intent(this, SignInActivity::class.java))
                            finish()
                        }
                    }
                }
            }

        httpAsync.join()


    }

    private fun initAppsFlyer() {
        val conversionDataListener = object : AppsFlyerConversionListener {

            override fun onAppOpenAttribution(data: MutableMap<String, String>?) {
                data?.map {
                    Log.d("TAG", "onAppOpen_attribute: ${it.key} = ${it.value}")

                }
            }

            override fun onConversionDataSuccess(p0: MutableMap<String, Any>?) {
                Log.e("TAG", "onConversionDataSuccess:campaignAf:")


            }

            override fun onConversionDataFail(p0: String?) {
                Log.e("TAG", "APPSFLYER:error onConversionDataFail :  $p0")
            }

            override fun onAttributionFailure(p0: String?) {
                Log.e("TAG", "APPSFLYER:error onAttributionFailure :  $p0")

            }
        }

        AppsFlyerLib.getInstance().init("2TKvHJFkZsTMTouMeAfJQF", conversionDataListener, this)
        AppsFlyerLib.getInstance().startTracking(applicationContext as Application)
        AppsFlyerLib.getInstance().setDebugLog(true)
    }

    private fun secondCall() {
        val httpAsync = "https://trk.wowmeet.xyz/KTsvp8QQ?y=1"
            .httpGet()
            .timeout(20000)
            .header("Content-Type", "application/json; utf-8")
            .responseString { _, _, result ->
                when (result) {
                    is Result.Failure -> {
                        val ex = result.getException()
                        Log.e("Error", "response error ${result.getException()}")
                        startActivity(Intent(this, SignInActivity::class.java))
                        finish()
                    }
                    is Result.Success -> {
                        val data = result.get()
                        println(data)
                        Log.e("Succses", "response success ${data}")
                        PreferenceManager.getDefaultSharedPreferences(this).edit()
                            .putString("URL_2_DATA", data).apply()
                        startActivity(Intent(this, WebActivity::class.java))
                        progress_bar.visibility = View.INVISIBLE
                        finish()
                    }
                }
            }

        httpAsync.join()

    }


    private fun getFBDeep() {
        AppLinkData.fetchDeferredAppLinkData(this) {
            if (it != null) {
                val getIt = it.targetUri.toString()
                Log.e("Facebook", "deep link $getIt")
                setDepL("&${getIt.substringAfter("wwmt://")}")

                Log.e("Success", "FB: $getIt")

            } else {
                Log.e("Facebook", "deep link NULL")

            }

        }
    }


    private fun setDepL(valu: String) {
        val editor = PreferenceManager.getDefaultSharedPreferences(this).edit()
        editor.putString("DEPL", valu)
        editor.apply()
    }
}