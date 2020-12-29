package com.example.weather2

import androidx.test.platform.app.InstrumentationRegistry
import com.example.data_storage_location.db.LocationEntity
import java.io.File

object TestUtil {

    fun getJsonPath(path : String) : String {
        val file = InstrumentationRegistry.getInstrumentation().context.assets.open(path)
        return String(file.readBytes())
    }

    fun getLocation1() = LocationEntity(
        "ChIJZS7uUNHFd0gRwQo_7aS2HY8",
        "Eaton Ford","England","United Kingdom",
        52.2276314,-0.2832154,
        52.2289803802915,-0.281866419708498,
        52.2262824197085,-0.2845643802915021)

    fun getLocation2() = LocationEntity(
        "ChIJzXkHOdIyeEgRFdsZGSBjgBA",
        "Nottingham","England","United Kingdom",
        52.95478319999999,-1.1581086,
        53.019045,	-1.0918336,
        52.889,	-1.2482899)


}