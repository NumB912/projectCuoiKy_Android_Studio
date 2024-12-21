package com.example.projectcuoiky.utils

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.constraintlayout.motion.utils.ViewTimeCycle
import com.example.projectcuoiky.R
import com.example.projectcuoiky.Model.Task
import com.example.projectcuoiky.Adapter.TaskAdapter
import com.example.projectcuoiky.Model.Status
import com.example.projectcuoiky.ViewModel.DateViewModel
import com.example.projectcuoiky.ViewModel.TaskViewModel
import com.example.projectcuoiky.utils.CalendarUtils.getDate
import com.example.projectcuoiky.utils.CalendarUtils.getHourMinuteNow
import java.time.LocalDate
import java.util.Calendar

object dialog_CRUD {

    fun dialogAddTask(context: Context, taskViewModel: TaskViewModel,date:LocalDate= LocalDate.now()) {
        val layout = LayoutInflater.from(context).inflate(R.layout.dialog_add_task, null)
        val dialog = AlertDialog.Builder(context)
            .setView(layout)
            .setCancelable(true)
            .create()

        val titleEditText = layout.findViewById<EditText>(R.id.titleTaskAdd)
        val descriptionEditText = layout.findViewById<EditText>(R.id.DecriptionAdd)
        val dateEditText = layout.findViewById<EditText>(R.id.DateAdd)
        val timeEditText = layout.findViewById<EditText>(R.id.timeAdd)

        val cancelTaskButton = layout.findViewById<Button>(R.id.btnCancelAdd)
        val addTaskButton = layout.findViewById<Button>(R.id.btnOKAdd)
        val dateButton = layout.findViewById<ImageView>(R.id.dateButton)
        val timeButton = layout.findViewById<ImageView>(R.id.timeButton)

        dateEditText.setText(getDate())
        timeEditText.setText(getHourMinuteNow())

        dialog.window?.setBackgroundDrawableResource(R.drawable.border_radius)

        cancelTaskButton.setOnClickListener {
            dialog.dismiss()
        }

        dateButton.setOnClickListener {
            datePicker(context, dateEditText)
        }

        timeButton.setOnClickListener {
            timePicker(context, timeEditText)
        }

        // Handle add task
        addTaskButton.setOnClickListener {
            val title = titleEditText.text.toString().trim()
            val description = descriptionEditText.text.toString().trim()
            val date = dateEditText.text.toString().trim()
            val time = timeEditText.text.toString().trim()

            if (title.isEmpty() || description.isEmpty() || date.isEmpty() || time.isEmpty()) {
                Toast.makeText(context, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            } else {
                if (!isValidDate(date)) {
                    Toast.makeText(context, "Invalid date format. Use dd/MM/yyyy", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }



                // Create a new Task object
                val newTask = Task(title, description, date,time, Status.Undone)
                taskViewModel.addTask(newTask)

                dialog.dismiss()
            }
        }

        dialog.show()
    }

    // Check format dd/MM/YYYY
    fun isValidDate(date: String): Boolean {
        val regex = "^\\d{1,2}/\\d{1,2}/\\d{4}$".toRegex()
        return date.matches(regex)
    }




    fun datePicker(context: Context, dateEditText: EditText, localDate: LocalDate = LocalDate.now()) {
        val calendar = Calendar.getInstance()

        val year = localDate.year
        val month = localDate.monthValue - 1  // Calendar months are 0-indexed (0 = January, 1 = February, etc.)
        val day = localDate.dayOfMonth

        // Set the date values to the calendar
        calendar.set(Calendar.YEAR, year)
        calendar.set(Calendar.MONTH, month)
        calendar.set(Calendar.DAY_OF_MONTH, day)

        // Create and display the DatePickerDialog
        val datePickerDialog = DatePickerDialog(
            context,
            { _, selectedYear, selectedMonth, selectedDay ->
                // Format the date in dd/MM/yyyy format
                val formattedDate = String.format("%02d/%02d/%d", selectedDay, selectedMonth + 1, selectedYear)
                dateEditText.setText(formattedDate)
            },
            year, month, day
        )

        // Show the DatePickerDialog
        datePickerDialog.show()
    }

    fun timePicker(context: Context, timeEditText: EditText) {
        // Get current time
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)

        // Create and show the TimePickerDialog
        val timePickerDialog = TimePickerDialog(
            context,
            { _, selectedHour, selectedMinute ->
                // Format the time as HH:mm
                val formattedTime = String.format("%02d:%02d", selectedHour, selectedMinute)
                timeEditText.setText(formattedTime)
            },
            hour, minute, true
        )

        timePickerDialog.show()
    }

    fun dialogDeleteTask(taskAdapter: TaskAdapter, task: Task, context: Context) {
        val layout = LayoutInflater.from(context).inflate(R.layout.dialog_delete_task, null)

        val dialog = AlertDialog.Builder(context)
            .setView(layout)
            .setCancelable(true)
            .create()

        val cancelDeleteBtn = layout.findViewById<Button>(R.id.btnCancelDelete)
        val okDeleteBtn = layout.findViewById<Button>(R.id.btnOkDelete)

        dialog.window?.setBackgroundDrawableResource(R.drawable.border_radius)

        cancelDeleteBtn.setOnClickListener {
            dialog.dismiss()
        }

        okDeleteBtn.setOnClickListener {
            taskAdapter.removeTask(task)
            dialog.dismiss()
        }

        dialog.show()
    }
    fun dialogUpdateTask(taskAdapter: TaskAdapter, context: Context, task: Task,position:Int) {
        val layout = LayoutInflater.from(context).inflate(R.layout.dialog_edit_task, null)

        val dialog = AlertDialog.Builder(context)
            .setView(layout)
            .setCancelable(true)
            .create()

        val originalTask = Task(task.title, task.description, task.date,task.time, task.taskStatus, task.id)

        val titleEditText = layout.findViewById<EditText>(R.id.updateTitle)
        val descriptionEditText = layout.findViewById<EditText>(R.id.updateDecription)
        val dateEditText = layout.findViewById<EditText>(R.id.updateDate)
        val timeEditText = layout.findViewById<EditText>(R.id.timeUpdate)

        // Hiển thị dữ liệu cũ từ task
        titleEditText.setText(task.title)
        descriptionEditText.setText(task.description)
        dateEditText.setText(task.date)
        timeEditText.setText(task.time)

        val okEditButton = layout.findViewById<Button>(R.id.btnUpdate)
        val cancelEditButton = layout.findViewById<Button>(R.id.btnCancelEdit)
        val dateButton = layout.findViewById<ImageView>(R.id.dateUpdateButotn)
        val timeButton = layout.findViewById<ImageView>(R.id.TimeButton)

        // Khi click vào nút chọn ngày
        dateButton.setOnClickListener {
            // Gọi hàm datePicker để người dùng chọn ngày
            datePicker(context, dateEditText, CalendarUtils.FormatterStringToLocaleDate(dateEditText.text.toString()))
        }

        timeButton.setOnClickListener {
            timePicker(context, timeEditText)
        }

        // Đặt background cho dialog
        dialog.window?.setBackgroundDrawableResource(R.drawable.border_radius)

        // Handle cancel when user cliked on cancel Button
        cancelEditButton.setOnClickListener {
            titleEditText.setText(originalTask.title)
            descriptionEditText.setText(originalTask.description)
            dateEditText.setText(originalTask.date)
            taskAdapter.reload(position)
            dialog.dismiss()
        }

        // Handle
        okEditButton.setOnClickListener {
            val updatedTitle = titleEditText.text.toString().trim()
            val updatedDescription = descriptionEditText.text.toString().trim()
            val updatedDate = dateEditText.text.toString().trim()
            val updateTime = timeEditText.text.toString().trim()

            if (updatedTitle.isNotEmpty() && updatedDescription.isNotEmpty() && updatedDate.isNotEmpty()) {
                val updatedTask = Task(updatedTitle, updatedDescription, updatedDate,updateTime,task.taskStatus,task.id)
                taskAdapter.update(updatedTask,position)
                dialog.dismiss()
            } else {
                if (!isValidDate(updatedDate)) {
                    Toast.makeText(context, "Invalid date format. Use dd/MM/yyyy", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
                Toast.makeText(context, "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show()
            }
        }

        dialog.show()
    }


    fun dialogDetailTask(taskAdapter: TaskAdapter,context: Context, task: Task,position: Int) {
        val layout = LayoutInflater.from(context).inflate(R.layout.dialog_detail_task, null)

        val dialog = AlertDialog.Builder(context)
            .setView(layout)
            .setCancelable(true)
            .create()

        val cancelButton = layout.findViewById<Button>(R.id.btnCancelDetail)
        val doneButton = layout.findViewById<Button>(R.id.btnDone)

        doneButton.setText(if (task.taskStatus == Status.Done) "Undone" else "Done")

        dialog.window?.setBackgroundDrawableResource(R.drawable.border_radius)

        val titleEditText = layout.findViewById<EditText>(R.id.titleDetail)
        val descriptionEditText = layout.findViewById<EditText>(R.id.decriptionDetail)
        val dateEditText = layout.findViewById<EditText>(R.id.dateDetail)
        val timeEditText = layout.findViewById<EditText>(R.id.timeDetail)


        titleEditText.setText(task.title)
        descriptionEditText.setText(task.description)
        dateEditText.setText(task.date)
        timeEditText.setText(task.time)

        doneButton.setOnClickListener {
            var statusCurrent = task.taskStatus

            if(statusCurrent == Status.Done){
                statusCurrent = Status.Undone
            }else{
                statusCurrent = Status.Done
            }

            val updatedTask = Task(task.title, task.description, task.date,task.time,statusCurrent,task.id)
            taskAdapter.update(updatedTask,position)
            dialog.dismiss()
        }

        cancelButton.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }
}
