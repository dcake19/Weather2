package com.example.ui_weather_2.today

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.presentation_weather_2.WeatherTodayDailyForecastView
import com.example.ui_weather_2.R
import com.example.ui_weather_2.mapToImageResource


class WeatherTodayDailyAdapter(private val placeId: String): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var items = listOf<WeatherTodayDailyForecastView>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.weather_today_day_item, parent, false)
        return DailyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){
            is DailyViewHolder -> holder.setData(items[position])
        }
    }

    inner class DailyViewHolder(itemView : View): RecyclerView.ViewHolder(itemView){
        fun setData(forecast: WeatherTodayDailyForecastView){
            itemView.findViewById<TextView>(R.id.text_day).text = forecast.day
            itemView.findViewById<TextView>(R.id.text_day_rain).text = forecast.rain
            itemView.findViewById<TextView>(R.id.text_day_temp_max).text = forecast.temperatureHigh
            itemView.findViewById<TextView>(R.id.text_day_temp_min).text = forecast.temperatureLow

            val d = mapToImageResource(forecast.weatherId)
            itemView.findViewById<ImageView>(R.id.image_weather_day_icon)
                .setImageResource(d)

            itemView.setOnClickListener {

                itemView.findNavController()
                    .navigate(FragmentWeatherTodayOverviewDirections
                        .actionWeatherTodayToDaily(placeId,adapterPosition))
            }
        }
    }
}