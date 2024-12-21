package com.example.projectcuoiky.FireBase

import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.projectcuoiky.Fragments.Home
import com.example.projectcuoiky.Model.Task
import com.google.firebase.database.*

object TaskRepository {

    private val databaseReference = FirebaseDatabase.getInstance().getReference("Tasks")

    fun getTasks(): LiveData<List<Task>> {
        val liveData = MutableLiveData<List<Task>>()

        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val tasks = mutableListOf<Task>()
                for (dataSnapshot in snapshot.children) {
                    val task = dataSnapshot.getValue(Task::class.java)
                    task?.let { tasks.add(it) }
                }
                liveData.value = tasks
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })

        return liveData
    }


    fun addTask(task: Task, callback: () -> Unit) {
        val key = databaseReference.push().key ?: return
        task.id = key
        databaseReference.child(key).setValue(task).addOnSuccessListener {
            callback()
        }
    }

    fun removeTask(taskId: String, callback: () -> Unit) {
        databaseReference.child(taskId).removeValue().addOnSuccessListener {
            callback()
        }
    }

    fun updateTask(task: Task, callback: () -> Unit) {
        task.id?.let {
            databaseReference.child(it).setValue(task).addOnSuccessListener {
                callback()
            }
        }
    }
}
