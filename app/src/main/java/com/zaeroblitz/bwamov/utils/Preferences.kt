package com.zaeroblitz.bwamov.utils

import android.content.Context
import android.content.SharedPreferences

class Preferences (val context: Context){

    companion object{
        const val USER_PREF = "user_pref"
        const val USER_USERNAME = "username"
        const val USER_NAME = "name"
        const val USER_EMAIL = "email"
        const val USER_BALANCE = ""
        const val USER_URL = "url"
        const val USER_STATUS = "1"
    }

    private var sharedPreferences = context.getSharedPreferences(USER_PREF,0)

    fun setValues(key: String, value: String){
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.putString(key, value)
        editor.apply()
    }

    fun getValues(key:String) : String? {
        return sharedPreferences.getString(key, "")
    }

}