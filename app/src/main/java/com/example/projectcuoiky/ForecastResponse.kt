data class ForecastResponse(
    val location: Location,
    val forecast: Forecast,
    val current: Current
)
data class Location(
    val name: String,
    val region: String,
    val country: String,
    val lat: Double,         // Latitude
    val lon: Double,         // Longitude
    val tz_id: String,       // Timezone ID
    val localtime: String    // Local time at the location
)
data class Current(
    val temp_c: Double,
    val temp_f: Double,
    val condition: Condition,
    val humidity: Int,
    val wind_kph: Double
)




data class Forecast(
    val forecastday: List<ForecastDay>
)

data class ForecastDay(
    val date: String,
    val day: Day,
    val hour: List<Hour>  // List of hourly data
)


data class Day(
    val maxtemp_c: Double,       // Maximum temperature
    val mintemp_c: Double,       // Minimum temperature
    val avgtemp_c: Double,       // Average temperature
    val maxwind_kph: Double,     // Maximum wind speed
    val avghumidity: Double,     // Average humidity percentage
    val condition: Condition     // Overall condition for the day
)


data class Condition(
    val text: String,  // Condition text (e.g., "Partly cloudy")
    val icon: String   // Icon URL for the condition
)

data class Hour(
    val time: String,        // Time of the forecast (e.g., "2024-11-29 01:00")
    val temp_c: Double,      // Temperature in Celsius
    val wind_kph: Double,    // Wind speed in kph
    val humidity: Int,       // Humidity percentage
    val condition: Condition // Hourly weather condition
)



