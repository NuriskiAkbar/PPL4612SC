package com.riquest.sistemmanajemenmenu.utils

import android.content.Context
import android.content.SharedPreferences
import android.os.Parcelable
import java.util.*
import kotlin.collections.ArrayList

class Preferences (val context: Context) {
    public val stateMenu : ArrayList<Parcelable>? = ArrayList()

    companion object {
        const val MEETING_PREF = "USER_PREF"
    }

    val sharedPref = context.getSharedPreferences(MEETING_PREF, 0)

    fun setValues(key: String, value: String) {
        val editor: SharedPreferences.Editor = sharedPref.edit()
        editor.putString(key, value)
        editor.apply()
    }

    fun getValues(key: String): String? {
        return sharedPref.getString(key, "")
    }

    fun setStateMenu(value:Parcelable){
        stateMenu?.add(value)
    }
}