package com.example.projectcuoiky.Model

import com.example.projectcuoiky.utils.CalendarUtils
import com.example.projectcuoiky.utils.CalendarUtils.getDate
import com.example.projectcuoiky.utils.CalendarUtils.getHourMinuteNow
import java.time.LocalDate
import java.util.Calendar

data class Task(
    val title: String="",
    val description: String="",
    val date: String= getDate(),
    val time:String= getHourMinuteNow(),
    val taskStatus: Status = Status.Undone,
    var id: String? = null
)


