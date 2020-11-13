package com.example.ui_weather_2.today

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.presentation_weather_2.WeatherTodayDailyForecastView
import com.example.ui_weather_2.R


class WeatherTodayDailyAdapter(): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

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

        }
    }
}