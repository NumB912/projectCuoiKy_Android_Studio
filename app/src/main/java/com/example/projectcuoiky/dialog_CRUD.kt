package com.example.projectcuoiky

import android.content.Context
import android.view.LayoutInflater
import android.widget.Button
import androidx.appcompat.app.AlertDialog

object dialog_CRUD {
    fun dialogAddTask(context: Context){
        val layout = LayoutInflater.from(context).inflate(R.layout.dialog_add_task,null)

        val dialog = AlertDialog.Builder(context)
            .setView(layout)
            .setCancelable(true)
            .create()

        val cancelTaskButton = layout.findViewById<Button>(R.id.btnCancelAdd)
        val addTaskButton = layout.findViewById<Button>(R.id.btnOKAdd)
        dialog.window?.setBackgroundDrawableResource(R.drawable.border_radius)
        cancelTaskButton.setOnClickListener{
            dialog.dismiss()
        }
        addTaskButton.setOnClickListener {

        }
        dialog.show()
    }

    fun dialogDeleteTask(taskAdapter:TaskAdapter,position:Int,context: Context){
        val layout = LayoutInflater.from(context).inflate(R.layout.dialog_delete_task,null)

        val dialog = AlertDialog.Builder(context)
            .setView(layout)
            .setCancelable(true)
            .create()

        val cancelDeletebtn = layout.findViewById<Button>(R.id.btnCancelDelete)
        val okDeletebtn = layout.findViewById<Button>(R.id.btnOkDelete)
        dialog.window?.setBackgroundDrawableResource(R.drawable.border_radius)
        cancelDeletebtn.setOnClickListener{
            dialog.dismiss()
            taskAdapter.notifyDataSetChanged()
        }
        okDeletebtn.setOnClickListener {
            dialog.dismiss()
            taskAdapter.removeItem(position)
            taskAdapter.notifyDataSetChanged()
        }
        dialog.show()
    }

    fun dialogUpdateTask(taskAdapter:TaskAdapter,context: Context,position: Int){
        val layout = LayoutInflater.from(context).inflate(R.layout.dialog_edit_task,null)

        val dialog = AlertDialog.Builder(context)
            .setView(layout)
            .setCancelable(true)
            .create()

        val okEditButton = layout.findViewById<Button>(R.id.btnOkEdit)
        val cancelEditButton = layout.findViewById<Button>(R.id.btnCancelEdit)
        dialog.window?.setBackgroundDrawableResource(R.drawable.border_radius)
        cancelEditButton.setOnClickListener{
            dialog.dismiss()
            taskAdapter.notifyDataSetChanged()
        }

        okEditButton.setOnClickListener {
            dialog.dismiss()
            taskAdapter.notifyDataSetChanged()
        }


        dialog.show()
    }

    fun dialogDetailTask(context: Context){
        val layout = LayoutInflater.from(context).inflate(R.layout.dialog_detail_task,null)

        val dialog = AlertDialog.Builder(context)
            .setView(layout)
            .setCancelable(true)
            .create()

        val cancelEditButton = layout.findViewById<Button>(R.id.btnCancelEdit)
        dialog.window?.setBackgroundDrawableResource(R.drawable.border_radius)
        cancelEditButton.setOnClickListener{
            dialog.dismiss()
        }


        dialog.show()
    }
}