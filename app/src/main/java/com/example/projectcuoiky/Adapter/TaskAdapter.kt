package com.example.projectcuoiky.Adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.projectcuoiky.MainActivity
import com.example.projectcuoiky.Model.Task
import com.example.projectcuoiky.R
import com.example.projectcuoiky.utils.dialog_CRUD
import com.example.projectcuoiky.ViewModel.TaskViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.projectcuoiky.utils.CalendarUtils

class TaskAdapter(private val listTask: MutableList<Task>, private val context: Context) : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    class TaskViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.title)
        val description: TextView = view.findViewById(R.id.DecriptionAdd)
        val lnItem: LinearLayout = view.findViewById(R.id.ln_item)
        val hourHolder:TextView = view.findViewById(R.id.hourTextView)
        val minutesHolder:TextView = view.findViewById(R.id.minutesText)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val layout = LayoutInflater.from(parent.context).inflate(R.layout.item_task_todo, parent, false)
        return TaskViewHolder(layout)
    }

    override fun getItemCount(): Int = listTask.size

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = listTask[position]
        holder.title.text = task.title
        holder.description.text = task.description
        holder.hourHolder.text = CalendarUtils.getHour(task.time).get(0)
        holder.minutesHolder.text = CalendarUtils.getHour(task.time).get(1)

        holder.lnItem.setOnClickListener {
            dialog_CRUD.dialogDetailTask(this,context,task,position)
        }
    }

    private fun checkTaskEmpty() {
        val contextHome = context as MainActivity
        val recyclerviewTask = contextHome.findViewById<RecyclerView>(R.id.recycle_view_task)

        if (itemCount == 0) {
            recyclerviewTask.setBackgroundResource(R.drawable.bocchiwaller)
        } else {
            recyclerviewTask.setBackgroundResource(0)
        }
    }


    fun setupSwipeActions(recyclerView: RecyclerView, adapter: TaskAdapter) {
        val swipeCallback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val task = listTask[position]
                when (direction) {
                    ItemTouchHelper.LEFT -> {
                        dialog_CRUD.dialogDeleteTask(adapter, task, recyclerView.context)
                        notifyItemRemoved(position)
                    }
                    ItemTouchHelper.RIGHT -> {
                        dialog_CRUD.dialogUpdateTask(adapter, recyclerView.context, task,position)
                        notifyItemChanged(position)
                    }
                }
            }
        }

        val itemTouchHelper = ItemTouchHelper(swipeCallback)
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }

    fun update(task: Task,position: Int){
        val taskViewModel = ViewModelProvider(context as MainActivity).get(TaskViewModel::class.java)
        taskViewModel.updateTask(task)
        listTask[position] = task
    }

    fun reload(position: Int){
        notifyItemChanged(position)
    }

    fun removeTask(task: Task){
        val taskViewModel = ViewModelProvider(context as MainActivity).get(TaskViewModel::class.java)
        taskViewModel.removeTask(task)
    }

    fun updateTaskList(list:List<Task>){
        listTask.clear()
        listTask.addAll(list)
        notifyDataSetChanged()
    }
}
