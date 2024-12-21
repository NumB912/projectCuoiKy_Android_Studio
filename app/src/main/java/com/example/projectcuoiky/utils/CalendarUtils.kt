package com.example.projectcuoiky.utils

import androidx.lifecycle.viewmodel.CreationExtras
import com.example.projectcuoiky.Model.Task
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.Calendar


object CalendarUtils {
    lateinit var selectorDay: LocalDate

    fun formatDate(date: LocalDate): String {
        val formatter = DateTimeFormatter.ofPattern("dd/MMMM/yyyy")
        val formattedDate = date.format(formatter)
        return formattedDate
    }

    fun formattedTime(time: LocalTime): String {
        val formatter = DateTimeFormatter.ofPattern("hh:mm:ss a")
        return time.format(formatter)
    }

    fun monthYearFromDate(date: LocalDate): String {
        val formatter = DateTimeFormatter.ofPattern("MMMM yyyy")
        return date.format(formatter)
    }

   fun dayOfWeek(date: LocalDate):ArrayList<LocalDate>{
        val days=ArrayList<LocalDate>()

       var current = date.with(java.time.DayOfWeek.SUNDAY)
       var endDate = current.minusWeeks(1).plusDays(1)

       while (endDate.isBefore(current) || endDate == current)
       {
           days.add(endDate);
           endDate = endDate.plusDays(1);
       }
       return days;
   }

    fun dayOfYear(date: LocalDate):ArrayList<LocalDate>{
        val daysOfPrevAndNextYear=ArrayList<LocalDate>()
        val current = date
        val nextYear = current.plusYears(1)
        var previousYear = current.minusYears(1)

        while(previousYear.isBefore(nextYear) || previousYear == nextYear){
            daysOfPrevAndNextYear.add(previousYear)
            previousYear=previousYear.plusDays(1)
        }

        return daysOfPrevAndNextYear
    }

    fun previousWeek(date: LocalDate):ArrayList<LocalDate>{
        val previousWeek = date.minusWeeks(1)
        return dayOfWeek(previousWeek)
    }

    fun nextWeek(date:LocalDate):ArrayList<LocalDate>{
        val previousWeek = date.plusWeeks(1)
        return dayOfWeek(previousWeek)
    }

    fun getWeekStartEnd(date: LocalDate): Boolean {
        var current = date.with(java.time.DayOfWeek.SUNDAY)
        var endDate = current.minusWeeks(1).plusDays(1)

        return current >= date && endDate <= date
    }

    fun FormatterStringToLocaleDate(dateString: String): LocalDate {
        val formatter = DateTimeFormatter.ofPattern("d/M/yyyy")  // Định dạng ngày theo kiểu dd/MM/yyyy
        return LocalDate.parse(dateString, formatter)  // Phân tích chuỗi ngày theo định dạng đã chỉ định
    }

    fun formatLocalDateToString(date: LocalDate): String {
        val formatter = DateTimeFormatter.ofPattern("d/M/yyyy")
        return date.format(formatter)
    }
    fun getHour(dataString:String):List<String>{
        if(dataString.isEmpty()){
            return listOf("00","00")
        }
        return dataString.split(":")
    }

    fun getHourMinuteNow():String{
        val current = Calendar.getInstance()
        val hour = current.get(Calendar.HOUR_OF_DAY)
        val minute = current.get(Calendar.MINUTE)

        return String.format("%02d:%02d", hour, minute)
    }

    fun getDate():String{
        val current = Calendar.getInstance()
        val day = current.get(Calendar.DAY_OF_MONTH)
        val month = current.get(Calendar.MONTH)+1
        val year = current.get(Calendar.YEAR)

        return String.format("%02d/%02d/%04d",day,month,year)

    }

    fun sortTime(list: List<Task>): List<Task> {
        return list.sortedBy { task -> LocalTime.parse(task.time) }
    }
}