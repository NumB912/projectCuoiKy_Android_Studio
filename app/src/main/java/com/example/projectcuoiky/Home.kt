package com.example.projectcuoiky
import android.app.DatePickerDialog
import android.content.Context
import android.graphics.Color
import android.icu.util.Calendar
import android.media.RouteListingPreference.Item
import java.time.Month
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.Visibility
import java.time.LocalDate

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Home.newInstance] factory method to
 * create an instance of this fragment.
 */
class Home : Fragment() {
    lateinit var recyclerView: RecyclerView
    lateinit var weekAdapter: WeekAdapter
    lateinit var recyclerviewTask: RecyclerView
    lateinit var taskAdapter: TaskAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val layoutHome = inflater.inflate(R.layout.fragment_home, container, false)
        val today = LocalDate.now()
        val weekDays = CalendarUtils.dayOfWeek(today)
        val tasks: List<Task> = listOf(
            Task("Làm bài tập về nhà 1", "Không có gì hết", LocalDate.now(),Status.Done),
            Task("Làm bài tập về nhà 2", "Không có gì hết", LocalDate.now(),Status.IsNotDone),
            Task("Làm bài tập về nhà 3", "Không có gì hết", LocalDate.now(),Status.Done),
            Task("Làm bài tập về nhà 4", "Không có gì hết", LocalDate.now(),Status.InProgress)
        )



        recyclerView = layoutHome.findViewById(R.id.recycle_view)
        recyclerviewTask = layoutHome.findViewById(R.id.recycle_view_task)

        loadWeek(layoutHome.context, weekDays)
        loadTask(layoutHome.context, tasks)



        return layoutHome
    }

    fun loadWeek(context: Context, weekDays: ArrayList<LocalDate>) {
        recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        weekAdapter = WeekAdapter(context, weekDays)
        recyclerView.adapter = weekAdapter
    }

    fun loadTask(context: Context, tasks: List<Task>) {
        recyclerviewTask.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        taskAdapter = TaskAdapter(tasks.toMutableList(), context)
        recyclerviewTask.adapter = taskAdapter
        taskAdapter.setupSwipeActions(recyclerviewTask,taskAdapter)
    }




    override fun onResume() {
        super.onResume()
        (requireActivity() as MainActivity).checkFragment(this)
    }
}
