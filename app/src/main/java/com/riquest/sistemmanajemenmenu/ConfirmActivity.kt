package com.riquest.sistemmanajemenmenu

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.riquest.sistemmanajemenmenu.ConfirmActivity
import com.riquest.sistemmanajemenmenu.home.DashboardMenu

class ConfirmActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_confirm)
    }

    fun ya(view: View?) {
        startActivity(Intent(this@ConfirmActivity, SuccessActivity::class.java))
    }

    fun tidak(view: View?) {
        startActivity(Intent(this@ConfirmActivity, DashboardMenu::class.java))
    }
}