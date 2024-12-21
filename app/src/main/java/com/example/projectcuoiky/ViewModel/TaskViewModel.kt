package com.example.projectcuoiky.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.projectcuoiky.FireBase.TaskRepository
import com.example.projectcuoiky.Model.Task

class TaskViewModel : ViewModel() {


    val tasks: LiveData<List<Task>> = TaskRepository.getTasks()

    fun addTask(newTask: Task) {
        TaskRepository.addTask(newTask) {

        }
    }

    fun removeTask(Task:Task) {
        Task.id?.let {
            TaskRepository.removeTask(it) {

            }
        }
    }


    fun updateTask( task: Task) {
        TaskRepository.updateTask(task) {

        }
    }
}
