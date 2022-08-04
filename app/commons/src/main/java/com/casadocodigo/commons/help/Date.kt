package com.casadocodigo.commons.help

import java.text.SimpleDateFormat
import java.util.*

//dentro dessa classe implementei uma função que irá mostrar a data atual
class Date {

    fun callDate(): String{
        val date = Calendar.getInstance().time
        var dateTimeFormat = SimpleDateFormat("dd M yyy")
        return dateTimeFormat.format(date)

    }
}