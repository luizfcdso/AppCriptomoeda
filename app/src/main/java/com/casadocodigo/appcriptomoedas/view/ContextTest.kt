package com.casadocodigo.appcriptomoedas.view

import android.content.Context
import com.casadocodigo.appcriptomoedas.R

class ContextTest (private val context: Context){
    fun getTitle(): String{
        return context.getString(R.string.moeda_digital)
    }
}