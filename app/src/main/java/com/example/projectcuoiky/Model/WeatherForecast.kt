package com.example.projectcuoiky.Model

import ForecastResponse
import WeatherApi
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.projectcuoiky.ForecastWeatherDaysAdapter
import com.example.projectcuoiky.MainActivity
import com.example.projectcuoiky.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * A simple [Fragment] subclass.
 * Use the [WeatherForecast.newInstance] factory method to
 * create an instance of this fragment.
 */
class WeatherForecast : Fragment() {
    private lateinit var weatherApi: WeatherApi
    private val apiKey = "8a7ae1c96f944729ad414551242811"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)  // Inflate layout once
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.weatherapi.com/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        weatherApi = retrofit.create(WeatherApi::class.java)

        val buttonSearch = view.findViewById<ImageView>(R.id.searchView)
        buttonSearch.setOnClickListener {
            val textSearchWeather = view.findViewById<EditText>(R.id.searchEdit)
            getWeatherDays(view,textSearchWeather.text.toString(),8)
        }
       // getWeather(view)
        getWeatherDays(view)
        return view
    }

    private fun getWeatherDays(view: View, city: String="HaNoi", days:Int=8) {
        val call = weatherApi.getFutureWeather(apiKey, city,days)
        call.enqueue(object : Callback<ForecastResponse> {
            override fun onResponse(
                call: Call<ForecastResponse>,
                response: Response<ForecastResponse>
            ) {
                if(response.isSuccessful){
                    val response_forcastDays = response.body()
                    if(response_forcastDays!=null){
                        val weatherCurrent = response_forcastDays.current
                        val temp = weatherCurrent.temp_c
                        val condition = weatherCurrent.condition.text
                        val cityName = response_forcastDays.location.country
                        val humidty = weatherCurrent.humidity
                        val wind = weatherCurrent.wind_kph
                        val provide = response_forcastDays.location.name

                        val img= view.findViewById<ImageView>(R.id.imgWeather)
                        Glide.with(view)
                            .load("https:"+weatherCurrent.condition.icon)
                            .placeholder(R.drawable.weather_sunday) // Optional placeholder
                            .into(img);
                        val temperature_value = view.findViewById<TextView>(R.id.value_Temperture)
                        val temperture = view.findViewById<TextView>(R.id.Temperture)
                        val position = view.findViewById<TextView>(R.id.Position)
                        val wind_value =view.findViewById<TextView>(R.id.value_wind)
                        val humidty_value = view.findViewById<TextView>(R.id.value_Humidty)
                        val provideText = view.findViewById<TextView>(R.id.provideTextView)

                        position.text = cityName
                        wind_value.text = wind.toString()+"km/h"
                        humidty_value.text = humidty.toString()+"%"
                        temperature_value.text = condition
                        temperture.text = "${temp}°C"
                        provideText.text = provide


                        val weatherDays = response_forcastDays.forecast.forecastday
                        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView_weather)
                        recyclerView.layoutManager =
                            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                        val adapter = ForecastWeatherDaysAdapter(weatherDays, requireContext())
                        recyclerView.adapter = adapter

                    }
                }
            }

            override fun onFailure(call: Call<ForecastResponse>, t: Throwable) {

            }

        })
    }
    override fun onResume() {
        super.onResume()
        // Gọi checkFragment từ MainActivity để xử lý UI
        (requireActivity() as MainActivity).checkFragment(this)
    }
}