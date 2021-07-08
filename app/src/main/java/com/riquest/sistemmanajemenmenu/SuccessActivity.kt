package com.riquest.sistemmanajemenmenu

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.riquest.sistemmanajemenmenu.SuccessActivity

class SuccessActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_success)
    }

    fun Sukses(view: View?) {
        startActivity(Intent(this@SuccessActivity, WelcomePage::class.java))
    }
}