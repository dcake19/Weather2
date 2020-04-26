package com.example.utils_ui

import android.annotation.TargetApi
import android.content.Context
import android.os.Build

fun getColor(context: Context?, colorResource: Int):Int{
    if (context!=null) {
        if (Build.VERSION.SDK_INT <= 22) {
            return context.resources.getColor(colorResource)
        } else {
            return getApi23Color(context, colorResource)
        }
    }else{
        return 0
    }
}

@TargetApi(23)
fun getApi23Color(context: Context, colorResource: Int):Int{
    return context.getColor(colorResource)
}