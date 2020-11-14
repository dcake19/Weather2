package com.example.ui_weather_2.today

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.presentation_weather_2.WeatherTodayHourlyForecastView
import com.example.ui_weather_2.R
import com.example.ui_weather_2.mapToImageResource

class WeatherTodayHourlyAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var items = listOf<WeatherTodayHourlyForecastView>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.weather_today_hour_item, parent, false)
        return HourlyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){
            is HourlyViewHolder -> holder.setData(items[position])
        }
    }

    inner class HourlyViewHolder(itemView : View): RecyclerView.ViewHolder(itemView){
        fun setData(forecast: WeatherTodayHourlyForecastView){
            itemView.findViewById<TextView>(R.id.text_hour_time).text = forecast.time
            itemView.findViewById<TextView>(R.id.text_hour_rain).text = forecast.rain
            itemView.findViewById<TextView>(R.id.text_hour_temp).text = forecast.temperature
            val d = mapToImageResource(forecast.weatherId)
            itemView.findViewById<ImageView>(R.id.image_weather_hour_icon)
                .setImageResource(d)
        }
    }

}
