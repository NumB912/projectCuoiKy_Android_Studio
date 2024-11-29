package com.example.projectcuoiky

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import kotlin.contracts.contract

class TaskAdapter(val listTask:MutableList<Task>,val context: Context):RecyclerView.Adapter<TaskAdapter.taskViewHolder>() {

    class taskViewHolder(view:View):RecyclerView.ViewHolder(view){
        val title = view.findViewById<TextView>(R.id.title)
        val decription = view.findViewById<TextView>(R.id.decription)
        val ln_item = view.findViewById<LinearLayout>(R.id.ln_item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): taskViewHolder {
        val layout = LayoutInflater.from(parent.context).inflate(R.layout.item_task_todo,parent,false)
        return taskViewHolder(layout)
    }

    override fun getItemCount(): Int {
        return listTask.size
    }

    override fun onBindViewHolder(holder: taskViewHolder, position: Int) {
        var task = listTask[position]

        holder.title.text = task.title
        holder.decription.text = task.Decription
        holder.ln_item.setOnClickListener{

            Log.d("TaskAdapter", "Item clicked at position: $position")
            dialog_CRUD.dialogDetailTask(holder.itemView.context)
            Toast.makeText(holder.itemView.context, "Task Clicked", Toast.LENGTH_SHORT).show()   }
    }


     fun removeItem(position:Int){
         listTask.removeAt(position)
         notifyDataSetChanged()
         checkTaskEmpty()
    }



    fun checkTaskEmpty() {
        // Access the RecyclerView from the parent activity or fragment
        val contextHome = (context as MainActivity)
        val layout = contextHome.layoutInflater.inflate(R.layout.fragment_home,null)
        val recyclerviewTask = layout.findViewById<RecyclerView>(R.id.recycle_view_task)

        // Check if the item count is 0
        if (itemCount == 0) {
            recyclerviewTask.setBackgroundResource(R.drawable.bocchiwaller)
        } else {
            recyclerviewTask.setBackgroundResource(0)

        }

        // Show a toast with the item count (for debugging)
        Toast.makeText(context, "Item count: $itemCount", Toast.LENGTH_SHORT).show()
    }

    fun setupSwipeActions(recyclerView: RecyclerView, adapter: TaskAdapter) {
        val swipeCallback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                when (direction) {
                    ItemTouchHelper.LEFT -> {
                        dialog_CRUD.dialogDeleteTask(adapter, position, recyclerView.context)
                    }
                    ItemTouchHelper.RIGHT -> {
                        dialog_CRUD.dialogUpdateTask(adapter,recyclerView.context,position)
                    }
                }
            }
        }

        val itemTouchHelper = ItemTouchHelper(swipeCallback)
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }

}