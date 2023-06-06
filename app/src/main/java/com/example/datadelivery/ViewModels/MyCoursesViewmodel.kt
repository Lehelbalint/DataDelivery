package com.example.datadelivery

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.datadelivery.API.DateDeliveryRepository
import kotlinx.coroutines.launch
import java.lang.Exception

class MyCoursesViewModelFactory(
    private val repository: DateDeliveryRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MyCoursesViewModel( repository) as T
    }
}

class MyCoursesViewModel(val repository: DateDeliveryRepository) : ViewModel() {
    var courseList = MutableLiveData<Course>()

    fun getCourses() {
        viewModelScope.launch {
            viewModelScope.launch {
                try {
                    val response = repository.getCourses()
                    if (response.isSuccessful) {
                        // userList.value = response.body()?.data
                        courseList.value = response.body()
                        Log.i("xxx-res", courseList.value.toString())
                    } else {
                        Log.i("xxx-muvm", response.message())
                    }
                } catch (e: Exception) {
                    Log.i("xxx-err", e.toString())
                }
            }
        }
    }
}