package com.example.projectcuoiky.Fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.projectcuoiky.Adapter.TaskAdapter
import com.example.projectcuoiky.Adapter.WeekAdapter
import com.example.projectcuoiky.MainActivity
import com.example.projectcuoiky.Model.Status
import com.example.projectcuoiky.Model.Task
import com.example.projectcuoiky.ViewModel.TaskViewModel
import com.example.projectcuoiky.R
import com.example.projectcuoiky.ViewModel.DateViewModel
import com.example.projectcuoiky.utils.CalendarUtils
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.utils.ColorTemplate
import com.google.android.material.tabs.TabItem
import com.google.android.material.tabs.TabLayout
import java.time.LocalDate

class Home : Fragment() {

    lateinit var recyclerView: RecyclerView
    lateinit var weekAdapter: WeekAdapter
    lateinit var recyclerviewTask: RecyclerView
    lateinit var taskAdapter: TaskAdapter
    lateinit var tabsLayout: TabLayout
    private val taskViewModel: TaskViewModel by activityViewModels()
    private lateinit var datelive: DateViewModel


    private var currentTab = 0
    private var selectedDate: LocalDate = LocalDate.now()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val layoutHome = inflater.inflate(R.layout.fragment_home, container, false)
        datelive = ViewModelProvider(requireActivity()).get(DateViewModel::class.java)
        recyclerView = layoutHome.findViewById(R.id.recycle_view)
        recyclerviewTask = layoutHome.findViewById(R.id.recycle_view_task)
        tabsLayout = layoutHome.findViewById<TabLayout>(R.id.tabs_check)

        tabsLayout.addTab(setTab(tabsLayout, "In Progress", "0"))
        tabsLayout.addTab(setTab(tabsLayout, "Done", "0"))

        loadWeek(layoutHome.context)


        taskViewModel.tasks.observe(viewLifecycleOwner, Observer { tasks ->
            loadFilteredTasks(tasks)
        })


        datelive.selectedDate.observe(viewLifecycleOwner, Observer { date ->
            selectedDate = date
            (context as MainActivity).setDate(date)
            val position = CalendarUtils.dayOfWeek(date).indexOf(date)
            loadWeek(layoutHome.context, date)
            weekAdapter.updateSelection(position)
            loadFilteredTasks(taskViewModel.tasks.value ?: emptyList())
        })

        tabsLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                currentTab = tab.position
                taskViewModel.tasks.value?.let {
                    loadFilteredTasks(it)
                }
                Toast.makeText(requireContext(), "Tab selected: ${tab.text}", Toast.LENGTH_SHORT).show()
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })



        return layoutHome
    }

    fun loadFilteredTasks(tasks: List<Task>) {
        val filteredTasks = tasks.filter { CalendarUtils.FormatterStringToLocaleDate(it.date) == selectedDate }
        val tasksDone = filteredTasks.filter { it.taskStatus == Status.Done }
        val taskUndone = filteredTasks.filter { it.taskStatus == Status.Undone }
        updateTab(tabsLayout,0,taskUndone.count())
        updateTab(tabsLayout,1,tasksDone.count())
        if(filteredTasks.isEmpty()){
            recyclerviewTask.setBackgroundResource(R.drawable.bocchiwaller)
        }
        val filteredTasksByTab = when (currentTab) {
            0 -> taskUndone
            1 -> tasksDone
            else -> filteredTasks
        }


        val sortedTasks = CalendarUtils.sortTime(filteredTasksByTab).toMutableList()
        if (::taskAdapter.isInitialized) {
            taskAdapter.updateTaskList(sortedTasks)
        } else {
            recyclerviewTask.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            taskAdapter = TaskAdapter(sortedTasks, requireContext())
            recyclerviewTask.adapter = taskAdapter
            taskAdapter.setupSwipeActions(recyclerviewTask, taskAdapter)
        }
    }

    fun loadWeek(context: Context, localDate: LocalDate = LocalDate.now()) {
        val weekDays: ArrayList<LocalDate> = CalendarUtils.dayOfWeek(localDate)
        recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        weekAdapter = WeekAdapter(context, weekDays) { date -> onDateSelected(date) }
        recyclerView.adapter = weekAdapter
    }

    private fun onDateSelected(selectedDate: LocalDate) {
        this.selectedDate = selectedDate
        taskViewModel.tasks.observe(viewLifecycleOwner, Observer { tasks ->
            loadFilteredTasks(tasks)
        })
    }

    fun setTab(tabLayout: TabLayout, text: String, Number: String): TabLayout.Tab {
        val tab = tabLayout.newTab()
        val customTabView = LayoutInflater.from(requireContext()).inflate(R.layout.tab_layout, null)
        val textView = customTabView.findViewById<TextView>(R.id.tabText)
        val tabNumber = customTabView.findViewById<TextView>(R.id.tabNumber)
        textView.text = text
        tabNumber.text = Number
        tab.customView = customTabView

        return tab
    }

    fun updateTab(tabLayout: TabLayout, position: Int, count: Int) {
        val tab = tabLayout.getTabAt(position)
        val customTabView = tab?.customView
        val numberView = customTabView?.findViewById<TextView>(R.id.tabNumber)
        numberView?.text = count.toString()
    }


    override fun onResume() {
        super.onResume()
        (requireActivity() as MainActivity).checkFragment(this)
    }


}
