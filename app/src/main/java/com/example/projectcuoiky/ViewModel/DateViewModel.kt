package com.example.projectcuoiky.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.time.LocalDate

class DateViewModel : ViewModel() {
    private val _selectedDate = MutableLiveData<LocalDate>()
    val selectedDate: LiveData<LocalDate> get() = _selectedDate

    fun setSelectedDate(date: LocalDate) {
        _selectedDate.value = date
    }
}
