package com.riquest.sistemmanajemenmenu

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity

class WelcomePage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.welcome_page)
    }

    fun klik(view: View?) {
        startActivity(Intent(this@WelcomePage, CustomerForm::class.java))
    }

}