package com.kvep.core.util

import android.content.Context

sealed class UiText{
    data class DynamicString(val text:String):UiText()
    data class StringResource(val resID:Int):UiText()

    fun asString(context: Context):String{
        return when(this){
            is DynamicString-> text
            is StringResource-> context.getString(resID)
        }
    }
}
