import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {
    @GET("current.json")
    fun getCurrentWeather(
        @Query("key") apiKey: String,
        @Query("q") city: String,
        @Query("aqi") aqi: String = "no "  // Không lấy thông tin chất lượng không khí (optional)
    ): Call<WeatherResponse>

    @GET("forecast.json")
    fun getFutureWeather(
        @Query("key") apiKey: String,
        @Query("q") city: String,
        @Query("days") day:Int,
    ): Call<ForecastResponse>
}
