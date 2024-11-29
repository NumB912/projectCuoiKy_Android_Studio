package com.example.projectcuoiky

import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter


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

}