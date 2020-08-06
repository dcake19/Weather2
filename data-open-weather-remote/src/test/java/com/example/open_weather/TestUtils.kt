package com.example.open_weather

import java.io.File

object TestUtils {
    fun getJsonPath(path : String) : String {
        val uri = this.javaClass.classLoader!!.getResource(path)
        val file = File(uri.path)
        return String(file.readBytes())
    }
}