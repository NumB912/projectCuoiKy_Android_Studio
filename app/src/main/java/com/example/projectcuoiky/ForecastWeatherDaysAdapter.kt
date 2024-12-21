package com.example.projectcuoiky

import ForecastDay
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.findViewTreeViewModelStoreOwner
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import java.time.LocalDate

class ForecastWeatherDaysAdapter(val listForecast:List<ForecastDay>,val context: Context):RecyclerView.Adapter<ForecastWeatherDaysAdapter.ForeCastHolder>() {

    class ForeCastHolder(view: View):RecyclerView.ViewHolder(view){
        val day = view.findViewById<TextView>(R.id.day)
        val status = view.findViewById<ImageView>(R.id.imgWeather_day)
        val temperature = view.findViewById<TextView>(R.id.temperture)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForeCastHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_forecastday,null)
        return ForeCastHolder(view)
    }

    override fun getItemCount(): Int {
       return listForecast.size
    }

    override fun onBindViewHolder(holder: ForeCastHolder, position: Int) {
        val forecast = listForecast[position]
        holder.day.text = LocalDate.parse(forecast.date).dayOfWeek.name.substring(0,3)
        Glide.with(context)
            .load("https:"+forecast.day.condition.icon)
            .placeholder(R.drawable.weather_sunday) // Optional placeholder
            .into(holder.status);

        holder.temperature.text = "${forecast.day.avgtemp_c}°C"

    }

}