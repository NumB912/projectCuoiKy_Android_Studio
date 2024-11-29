package com.example.projectcuoiky

import ForecastResponse
import WeatherApi
import WeatherResponse
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

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
            //getWeather(view,textSearchWeather.text.toString())
            getWeatherDays(view,textSearchWeather.text.toString(),8)
        }
       // getWeather(view)
        getWeatherDays(view)
        return view
    }

    /*private fun getWeather( view: View,city: String="HaNoi") {
        val call = weatherApi.getCurrentWeather(apiKey, city)
        call.enqueue(object : Callback<WeatherResponse> {
            override fun onResponse(call: Call<WeatherResponse>, response: Response<WeatherResponse>) {


                if (response.isSuccessful) {
                    val weather = response.body()
                    if (weather != null) {
                        val temp = weather.current.temp_c
                        val condition = weather.current.condition.text
                        val cityName = weather.location.name
                        val humidty = weather.current.humidity
                        val wind = weather.current.wind_kph

                       val img= view.findViewById<ImageView>(R.id.imgWeather)
                        Glide.with(view)
                            .load("https:"+weather.current.condition.icon)
                            .placeholder(R.drawable.weather_sunday) // Optional placeholder
                            .into(img);
                        val temperature_value = view.findViewById<TextView>(R.id.value_Temperture)
                        val temperture = view.findViewById<TextView>(R.id.Temperture)
                        val position = view.findViewById<TextView>(R.id.Position)
                        val wind_value =view.findViewById<TextView>(R.id.value_wind)
                        val humidty_value = view.findViewById<TextView>(R.id.value_Humidty)
                        position.text = cityName
                        wind_value.text = wind.toString()+"km/h"
                        humidty_value.text = humidty.toString()+"%"
                        temperature_value.text = condition
                        temperture.text = "${temp}°C"


                    }
                } else {
                    view.findViewById<TextView>(R.id.Humidty).text = "Error"
                }
                    }

            override fun onFailure(call: Call<WeatherResponse>, t: Throwable) {
                Toast.makeText(requireContext(), "Failed to get data", Toast.LENGTH_SHORT).show()
            }
        })
    }*/
    private fun getWeatherDays( view: View,city: String="HaNoi",days:Int=1) {
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
                        val cityName = weatherCurrent.condition.
                        val humidty = weatherCurrent.humidity
                        val wind = weatherCurrent.wind_kph

                        val img= view.findViewById<ImageView>(R.id.imgWeather)
                        Glide.with(view)
                            .load("https:"+weather.current.condition.icon)
                            .placeholder(R.drawable.weather_sunday) // Optional placeholder
                            .into(img);
                        val temperature_value = view.findViewById<TextView>(R.id.value_Temperture)
                        val temperture = view.findViewById<TextView>(R.id.Temperture)
                        val position = view.findViewById<TextView>(R.id.Position)
                        val wind_value =view.findViewById<TextView>(R.id.value_wind)
                        val humidty_value = view.findViewById<TextView>(R.id.value_Humidty)
                        position.text = cityName
                        wind_value.text = wind.toString()+"km/h"
                        humidty_value.text = humidty.toString()+"%"
                        temperature_value.text = condition
                        temperture.text = "${temp}°C"
                        for(item in weatherDays){
                            Log.i("item_Weather","${item.date}")
                        }
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
