package com.example.projectcuoiky.Fragments

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.example.projectcuoiky.MainActivity
import com.example.projectcuoiky.Model.Status
import com.example.projectcuoiky.Model.Task
import com.example.projectcuoiky.R
import com.example.projectcuoiky.ViewModel.DateViewModel
import com.example.projectcuoiky.ViewModel.TaskViewModel
import com.example.projectcuoiky.utils.CalendarUtils
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.utils.ColorTemplate
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.Locale

class Manager : Fragment() {

    private val taskViewModel: TaskViewModel by activityViewModels()
    private val datelive: DateViewModel by activityViewModels()
    private var dateCurrent = LocalDate.now()

    private lateinit var barChart: BarChart
    private lateinit var lineChart: LineChart
    private lateinit var quatity: TextView
    private lateinit var percent: TextView
    private lateinit var complete: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val layoutHistory = inflater.inflate(R.layout.fragment_history, container, false)

        barChart = layoutHistory.findViewById(R.id.barChart)
        quatity = layoutHistory.findViewById(R.id.Quatity_TextView)
        complete = layoutHistory.findViewById(R.id.Complete_TextView)
        percent = layoutHistory.findViewById(R.id.Percent_TextView)
        lineChart = layoutHistory.findViewById<LineChart>(R.id.lineChart)

        datelive.selectedDate.observe(viewLifecycleOwner, Observer { date ->
            dateCurrent = date
            val yearText = layoutHistory.findViewById<TextView>(R.id.Year_View)
            val monthText = layoutHistory.findViewById<TextView>(R.id.Month_View)
            yearText.setText(dateCurrent.year)
            monthText.setText(dateCurrent.month.name)
            updateUIWithFilteredTasks()
            updateLineChart()
        })

        taskViewModel.tasks.observe(viewLifecycleOwner, Observer { tasks ->
            updateUIWithFilteredTasks()
            updateLineChart()
        })

        return layoutHistory
    }

    fun updateUIWithFilteredTasks() {
        val filteredTasks = taskViewModel.tasks.value?.filter { task ->
            val taskDate = CalendarUtils.FormatterStringToLocaleDate(task.date)
            taskDate.isAfter(dateCurrent.minusWeeks(1).with(java.time.DayOfWeek.MONDAY)) && taskDate.isBefore(dateCurrent.with(java.time.DayOfWeek.SUNDAY).plusDays(1))
        } ?: emptyList()

        val barEntries = ArrayList<BarEntry>()
        var start = dateCurrent.with(java.time.DayOfWeek.MONDAY)
        val end = dateCurrent.with(java.time.DayOfWeek.SUNDAY)
        val taskGroupedByDay = filteredTasks.groupBy { CalendarUtils.FormatterStringToLocaleDate(it.date) }
        var index = 0
        var currentDay = start
        while (!currentDay.isAfter(end)) {
            val taskCount = taskGroupedByDay[currentDay]?.size ?: 0
            barEntries.add(BarEntry(index.toFloat(), taskCount.toFloat()))
            index++
            currentDay = currentDay.plusDays(1)
        }
        drawBar(barChart, barEntries)
        val countQuantity = filteredTasks.size
        val countComplete = filteredTasks.count { it.taskStatus == Status.Done }

        quatity.text = countQuantity.toString()
        complete.text = countComplete.toString()

        val percentValue = if (countQuantity > 0) {
            (countComplete.toFloat() * 100 / countQuantity.toFloat())
        } else {
            0f
        }

        val roundedPercent = String.format("%.2f", percentValue)+"%"
        percent.text = roundedPercent
    }

    fun updateLineChart() {
        Toast.makeText(context,"${dateCurrent}",Toast.LENGTH_LONG).show()
        val filteredTasks = taskViewModel.tasks.value?.filter { task ->
            val taskDate = CalendarUtils.FormatterStringToLocaleDate(task.date)
            taskDate.month == dateCurrent.month && taskDate.year == dateCurrent.year
        } ?: emptyList()

        val taskGroupedByDay = filteredTasks.groupBy { CalendarUtils.FormatterStringToLocaleDate(it.date).dayOfMonth }

        val entries = ArrayList<Entry>()
        var currentDay = 1
        val lastDayOfMonth = dateCurrent.lengthOfMonth() // Get the last day of the current month

        // Create line chart entries for each day of the month
        while (currentDay <= lastDayOfMonth) {
            val taskCount = taskGroupedByDay[currentDay]?.size ?: 0
            entries.add(Entry(currentDay.toFloat(), taskCount.toFloat()))
            currentDay++
        }

        drawLine(lineChart, entries)
    }

    fun drawLine(lineChart: LineChart, entries: List<Entry>) {
        val dataSet = LineDataSet(entries, "Tasks")
        dataSet.color = ContextCompat.getColor(requireContext(), R.color.Bocchi)
        dataSet.lineWidth = 2f
        dataSet.setDrawValues(false)

        val data = LineData(dataSet)
        lineChart.data = data


        lineChart.xAxis.valueFormatter = object : ValueFormatter() {
            override fun getFormattedValue(value: Float): String {
                val day = value.toInt()
                return day.toString()
            }
        }

        lineChart.xAxis.position = XAxis.XAxisPosition.BOTTOM
        lineChart.xAxis.textColor = Color.BLACK
        lineChart.axisLeft.textColor = Color.BLACK
        lineChart.axisRight.isEnabled = false
        lineChart.description.isEnabled = false
        val yAsis = lineChart.axisLeft
        yAsis.axisMinimum = 0f


        lineChart.xAxis.granularity = 1f

        lineChart.xAxis.setLabelCount(entries.size / 3, true)


        lineChart.setTouchEnabled(true)
        lineChart.setDragEnabled(true)
        lineChart.setScaleEnabled(true)
        lineChart.setPinchZoom(true)


        lineChart.setExtraOffsets(20f, 0f, 20f, 0f)


        lineChart.invalidate()
    }

    fun drawBar(barChart: BarChart, entries: List<BarEntry>) {
        val daysOfWeek = listOf("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun")

        val barDataSet = BarDataSet(entries, "Tasks").apply {
            color = ContextCompat.getColor(requireContext(), R.color.Bocchi)
            valueTextColor = Color.BLACK
            valueTextSize = 13f
        }

        val barData = BarData(barDataSet).apply {
            barWidth = 0.9f
        }

        barChart.data = barData

        val yAsis = barChart.axisLeft
        yAsis.axisMinimum = 0f

        val xAxis = barChart.xAxis
        xAxis.valueFormatter = IndexAxisValueFormatter(daysOfWeek)
        xAxis.granularity = 1f
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.textColor = Color.BLACK
        barChart.axisLeft.textColor = Color.BLACK
        barChart.axisRight.isEnabled = false
        barChart.description.isEnabled = false
        lineChart.setExtraOffsets(20f, 0f, 20f, 0f)
        barChart.animateY(1000)
        barChart.invalidate()
    }


    override fun onResume() {
        super.onResume()
        (requireActivity() as MainActivity).checkFragment(this)
    }
}
