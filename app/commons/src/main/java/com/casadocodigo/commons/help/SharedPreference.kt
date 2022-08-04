package com.casadocodigo.commons.help

import android.content.Context
import android.content.Context.MODE_PRIVATE

class SharedPreference (context: Context){
    private val mSharedPreferences = context.getSharedPreferences("APP", MODE_PRIVATE)

    fun storeBoolean(key: String, Value: Boolean){
        mSharedPreferences.edit().putBoolean(key, Value).apply()
    }
    fun getBoolean(key: String): Boolean{
        return mSharedPreferences.getBoolean(key, false) ?: false
    }
}